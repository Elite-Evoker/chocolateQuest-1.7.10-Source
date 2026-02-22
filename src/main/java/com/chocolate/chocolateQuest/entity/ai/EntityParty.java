package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.world.World;
import net.minecraft.nbt.NBTBase;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import com.chocolate.chocolateQuest.items.swords.ItemBDSword;
import net.minecraft.item.ItemArmor;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class EntityParty
{
    public static final int FRONT = 0;
    public static final int RIGHT = 90;
    public static final int BACK = 180;
    public static final int LEFT = -90;
    public static final int FRONT_RIGHT = 45;
    public static final int FRONT_LEFT = -45;
    public static final int BACK_RIGHT = 135;
    public static final int BACK_LEFT = -135;
    final int distanceToCaptain = 2;
    EntityHumanBase[] members;
    EntityHumanBase leader;
    EntityLivingBase[] potentialTargets;
    int[] targetsAggro;
    NBTTagCompound partyTag;
    
    public EntityParty() {
        this.members = new EntityHumanBase[8];
        this.potentialTargets = new EntityLivingBase[4];
        this.targetsAggro = new int[4];
        this.partyTag = null;
    }
    
    public void update() {
        if (this.partyTag != null) {
            this.loadFromNBT(this.partyTag);
            this.partyTag = null;
        }
        for (int i = 0; i < this.getMembersLength(); ++i) {
            final EntityHumanBase e = this.getMember(i);
            if (e != null && e.isDead) {
                this.removeMember(e);
            }
        }
        for (int i = 0; i < this.targetsAggro.length; ++i) {
            if (this.potentialTargets[i] != null) {
                if (this.targetsAggro[i] > 1) {
                    final int[] targetsAggro = this.targetsAggro;
                    final int n = i;
                    --targetsAggro[n];
                }
                if (!this.potentialTargets[i].isEntityAlive()) {
                    this.setAggro(null, 0, i);
                    this.sortTargets();
                }
            }
        }
    }
    
    public void addAggroToTarget(final EntityLivingBase e, final int aggro) {
        for (int i = 0; i < this.potentialTargets.length; ++i) {
            if (this.potentialTargets[i] == e) {
                final int[] targetsAggro = this.targetsAggro;
                final int n = i;
                targetsAggro[n] += aggro;
                this.sortTargets();
                return;
            }
        }
        for (int i = 0; i < this.potentialTargets.length; ++i) {
            if (this.potentialTargets[i] == null) {
                this.setAggro(e, aggro, i);
                this.sortTargets();
                return;
            }
        }
        for (int i = this.potentialTargets.length - 1; i >= 0; --i) {
            if (this.potentialTargets[i] != null) {
                if (aggro < this.targetsAggro[i]) {
                    this.setAggro(e, aggro, i);
                    this.sortTargets();
                }
                return;
            }
        }
        this.sortTargets();
    }
    
    public void setAggro(final EntityLivingBase e, final int aggro, final int i) {
        this.potentialTargets[i] = e;
        this.targetsAggro[i] = aggro;
    }
    
    public void sortTargets() {
        final double[] targetsAggro = new double[this.potentialTargets.length];
        for (int i = 0; i < this.potentialTargets.length; ++i) {
            if (this.potentialTargets[i] != null) {
                targetsAggro[i] = this.getLeader().getDistanceSqToEntity((Entity)this.potentialTargets[i]);
            }
        }
        for (int i = 0; i < this.potentialTargets.length; ++i) {
            double max = 0.0;
            int index = i;
            for (int c = i; c < this.potentialTargets.length; ++c) {
                if (targetsAggro[c] > max && this.potentialTargets[c] != null) {
                    max = targetsAggro[c];
                    index = c;
                }
            }
            final EntityLivingBase currentEntity = this.potentialTargets[index];
            final double currentAggro = targetsAggro[index];
            this.potentialTargets[index] = this.potentialTargets[i];
            targetsAggro[index] = targetsAggro[i];
            this.potentialTargets[i] = currentEntity;
            targetsAggro[i] = currentAggro;
        }
    }
    
    public EntityLivingBase getTarget() {
        if (this.potentialTargets[0] == null || !this.potentialTargets[0].isEntityAlive()) {
            this.sortTargets();
        }
        if (this.potentialTargets[0] == null) {
            return this.leader.getAttackTarget();
        }
        return this.potentialTargets[0];
    }
    
    public void removeMember(final EntityHumanBase entity) {
        entity.party = null;
        if (entity == this.getLeader()) {
            this.leader = null;
            entity.setCaptain(false);
            final int newPos = this.getNewLeaderFromParty();
            final EntityHumanBase e = this.getMember(newPos);
            if (e != null) {
                this.members[newPos] = null;
                this.setLeader(e);
            }
        }
        else {
            for (int i = 0; i < this.getMembersLength(); ++i) {
                if (entity.isEntityEqual((Entity)this.getMember(i))) {
                    this.removeMember(i);
                    break;
                }
            }
        }
        if (!this.hasMembers()) {
            final EntityHumanBase leader = this.getLeader();
            if (leader != null) {
                leader.setOutOfParty();
                leader.setCaptain(false);
            }
        }
    }
    
    public void cleanParty() {
        if (!this.leader.isEntityAlive()) {
            this.leader = null;
            final EntityHumanBase newLeader = this.getMember(this.getNewLeaderFromParty());
            if (newLeader != null) {
                this.setLeader(newLeader);
            }
        }
    }
    
    protected int getNewLeaderFromParty() {
        int bestEntityIndex = 0;
        int maxLevel = -9999;
        for (int i = 0; i < this.getMembersLength(); ++i) {
            if (this.getMember(i) != null) {
                final int currentLevel = this.getEntityLevel(this.getMember(i));
                if (currentLevel > maxLevel) {
                    maxLevel = currentLevel;
                    bestEntityIndex = i;
                }
            }
        }
        return bestEntityIndex;
    }
    
    public boolean tryToAddNewMember(final EntityHumanBase newMember) {
        return this.tryToAddNewMember(newMember, false);
    }
    
    public boolean tryToAddNewMember(final EntityHumanBase newMember, final boolean replaceLeader) {
        if (!replaceLeader && this.isFull()) {
            return false;
        }
        if (this.getLeader() == null) {
            this.setLeader(newMember);
            return true;
        }
        if (newMember == this.getLeader()) {
            return false;
        }
        for (int i = 0; i < this.getMembersLength(); ++i) {
            if (newMember.isEntityEqual((Entity)this.getMember(i))) {
                return false;
            }
        }
        if (newMember.party != null) {
            newMember.party.removeMember(newMember);
        }
        final int newLevel = this.getEntityLevel(newMember);
        final int leaderLevel = this.getEntityLevel(this.leader);
        if (replaceLeader && newLevel > leaderLevel) {
            this.setLeader(newMember);
            return true;
        }
        final int skipThisOne = this.getSuggestedPosition(newMember);
        if (this.tryAddToSlot(skipThisOne, newMember, newLevel)) {
            return true;
        }
        for (int j = 0; j < this.getMembersLength(); ++j) {
            if (j != skipThisOne) {
                if (this.tryAddToSlot(j, newMember, newLevel)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isFull() {
        for (int i = 0; i < this.getMembersLength(); ++i) {
            if (this.getMember(i) == null) {
                return false;
            }
        }
        return true;
    }
    
    private boolean hasMembers() {
        for (int i = 0; i < this.getMembersLength(); ++i) {
            if (this.getMember(i) != null) {
                return true;
            }
        }
        return false;
    }
    
    protected int getSuggestedPosition(final EntityHumanBase e) {
        if (e.isHealer() || e.isRanged()) {
            if (this.getMember(6) == null) {
                return 6;
            }
            if (this.getMember(5) == null) {
                return 5;
            }
            if (this.getMember(7) == null) {
                return 7;
            }
            if (this.getMember(3) == null) {
                return 3;
            }
            if (this.getMember(4) == null) {
                return 4;
            }
            return 6;
        }
        else {
            if (this.getMember(1) == null) {
                return 1;
            }
            if (this.getMember(3) == null) {
                return 3;
            }
            if (this.getMember(4) == null) {
                return 4;
            }
            if (this.getMember(0) == null) {
                return 0;
            }
            if (this.getMember(2) == null) {
                return 2;
            }
            if (this.getMember(6) == null) {
                return 6;
            }
            if (this.getMember(5) == null) {
                return 5;
            }
            if (this.getMember(7) == null) {
                return 7;
            }
            return 7;
        }
    }
    
    public int getAngleForSlot(final int i) {
        switch (i) {
            case 0: {
                return -45;
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 45;
            }
            case 3: {
                return -90;
            }
            case 4: {
                return 90;
            }
            case 5: {
                return 135;
            }
            case 6: {
                return 180;
            }
            case 7: {
                return -135;
            }
            default: {
                return 0;
            }
        }
    }
    
    public int getMembersLength() {
        return this.members.length;
    }
    
    protected void setMember(final int index, final EntityHumanBase newMember) {
        newMember.setInParty(this, this.getAngleForSlot(index), 2);
        this.members[index] = newMember;
    }
    
    public EntityHumanBase getMember(final int i) {
        return this.members[i];
    }
    
    protected void removeMember(final int i) {
        this.members[i].setOutOfParty();
        this.members[i] = null;
    }
    
    protected void setLeader(final EntityHumanBase newLeader) {
        final EntityHumanBase oldLeader = this.leader;
        (this.leader = newLeader).setInParty(this, 0, 2);
        newLeader.setCaptain(true);
        if (oldLeader != null) {
            oldLeader.setCaptain(false);
            oldLeader.setOutOfParty();
            this.tryToAddNewMember(oldLeader);
        }
    }
    
    public EntityHumanBase getLeader() {
        if (this.leader == null) {
            this.leader = this.getMember(this.getNewLeaderFromParty());
        }
        return this.leader;
    }
    
    protected boolean tryAddToSlot(final int index, final EntityHumanBase newMember, final int newLevel) {
        final EntityHumanBase current = this.getMember(index);
        if (current == null) {
            this.setMember(index, newMember);
            return true;
        }
        final int memberLevel = this.getEntityLevel(current);
        if (newLevel > memberLevel) {
            current.setOutOfParty();
            this.setMember(index, newMember);
            this.tryToAddNewMember(current);
            return true;
        }
        return false;
    }
    
    public int getEntityLevel(final EntityHumanBase entity) {
        int value = getItemValue(entity.leftHandItem);
        for (int i = 0; i < 5; ++i) {
            value += getItemValue(entity.getEquipmentInSlot(i));
        }
        value += entity.getLeadershipValue();
        return value;
    }
    
    public static int getItemValue(final ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        }
        final Item item = itemstack.getItem();
        if (item == ChocolateQuest.staffHeal) {
            return -20;
        }
        if (item instanceof ItemArmor) {
            return ((ItemArmor)item).getArmorMaterial().getDamageReductionAmount(2);
        }
        if (item instanceof ItemBDSword) {
            return 4;
        }
        if (item instanceof ItemSword) {
            return 3;
        }
        if (item == ChocolateQuest.banner) {
            return 10;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        String name = "Leader: ";
        if (this.leader != null) {
            name += this.leader.getEntityId();
        }
        else {
            name += "null";
        }
        for (int i = 0; i < this.getMembersLength(); ++i) {
            name = name + ", " + i + ": ";
            if (this.getMember(i) != null) {
                name += this.getMember(i).getEntityId();
            }
            else {
                name += "null";
            }
        }
        return name;
    }
    
    public void saveToNBT(final NBTTagCompound par1nbtTagCompound) {
        final int x = MathHelper.floor_double(this.getLeader().posX);
        final int y = MathHelper.floor_double(this.getLeader().posY);
        final int z = MathHelper.floor_double(this.getLeader().posZ);
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.getMembersLength(); ++i) {
            final EntityHumanBase e = this.getMember(i);
            if (e != null && !e.isDead) {
                final NBTTagCompound eTag = new NBTTagCompound();
                eTag.setString("id", EntityList.getEntityString((Entity)e));
                e.writeEntityToSpawnerNBT(eTag, x, y, z);
                list.appendTag((NBTBase)eTag);
            }
        }
        par1nbtTagCompound.setTag("Party", (NBTBase)list);
    }
    
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        this.partyTag = nbttagcompound;
    }
    
    private void loadFromNBT(final NBTTagCompound nbttagcompound) {
        final int x = MathHelper.floor_double(this.getLeader().posX);
        final int y = MathHelper.floor_double(this.getLeader().posY);
        final int z = MathHelper.floor_double(this.getLeader().posZ);
        final NBTTagList list = nbttagcompound.getTagList("Party", (int)nbttagcompound.getId());
        final World world = this.getLeader().worldObj;
        for (int i = 0; i < list.tagCount(); ++i) {
            final NBTTagCompound eTag = list.getCompoundTagAt(i);
            final Entity e = EntityList.createEntityFromNBT(eTag, world);
            if (e instanceof EntityHumanBase) {
                final EntityHumanBase human = (EntityHumanBase)EntityList.createEntityFromNBT(eTag, world);
                human.readEntityFromSpawnerNBT(eTag, x, y, z);
                human.setPosition(human.posX, human.posY, human.posZ);
                this.getLeader().tryPutIntoPArty(human);
                world.spawnEntityInWorld((Entity)human);
                if (eTag.getTag("Riding") != null) {
                    final NBTTagCompound ridingNBT = (NBTTagCompound)eTag.getTag("Riding");
                    final Entity riding = EntityList.createEntityFromNBT(ridingNBT, world);
                    if (riding != null) {
                        if (riding instanceof EntityHumanBase) {
                            ((EntityHumanBase)riding).entityTeam = human.entityTeam;
                        }
                        riding.setPosition(human.posX, human.posY, human.posZ);
                        world.spawnEntityInWorld(riding);
                        human.mountEntity(riding);
                    }
                }
            }
        }
    }
}
