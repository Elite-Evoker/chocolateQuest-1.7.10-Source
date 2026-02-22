package com.chocolate.chocolateQuest.entity.mob;

import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.monster.IMob;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class EntityHumanMob extends EntityHumanBase implements IMob
{
    DungeonMonstersBase monsterType;
    public boolean saveToSpawner;
    
    public EntityHumanMob(final World world) {
        super(world);
        this.saveToSpawner = true;
        this.updateEntityAttributes();
        this.potionCount = this.rand.nextInt(3);
        if (this.isBoss()) {
            this.shouldDespawn = false;
        }
    }
    
    public DungeonMonstersBase getMonsterType() {
        return null;
    }
    
    protected void updateEntityAttributes() {
        this.monsterType = this.getMonsterType();
        if (!this.isBoss()) {
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(this.monsterType.getAttack());
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.monsterType.getHealth());
            this.setHealth((float)this.monsterType.getHealth());
            this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(this.monsterType.getRange());
        }
        this.entityTeam = this.monsterType.getTeamName();
        this.shieldID = this.monsterType.getFlagId();
    }
    
    public boolean isBoss() {
        return false;
    }
    
    @Override
    public void readEntityFromSpawnerNBT(final NBTTagCompound nbttagcompound, final int spawnerX, final int spawnerY, final int spawnerZ) {
        super.readEntityFromSpawnerNBT(nbttagcompound, spawnerX, spawnerY, spawnerZ);
        this.setHomeArea(spawnerX, spawnerY, spawnerZ, 30);
        final ItemStack rightHandItem = this.getEquipmentInSlot(0);
        if (rightHandItem != null && rightHandItem.getItem() == ChocolateQuest.banner) {
            rightHandItem.setMetadata(this.getTeamID());
        }
        if (this.leftHandItem != null && this.leftHandItem.getItem() == ChocolateQuest.shield) {
            this.leftHandItem.setMetadata(this.getTeamID());
        }
    }
    
    @Override
    protected boolean interact(final EntityPlayer player) {
        return this.isOnSameTeam((EntityLivingBase)player) && super.interact(player);
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.party != null) {
                if (this.party.getLeader().equals(this) && !this.shouldDespawn && this.saveToSpawner) {
                    final EntityPlayer p = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, (double)ChocolateQuest.config.distToDespawn);
                    if (p == null) {
                        int x;
                        int y;
                        int z;
                        if (this.hasHome()) {
                            final ChunkCoordinates coord = this.getHomePosition();
                            x = coord.posX;
                            y = coord.posY;
                            z = coord.posZ;
                        }
                        else {
                            x = MathHelper.floor_double(this.posX);
                            y = MathHelper.floor_double(this.posY);
                            z = MathHelper.floor_double(this.posZ);
                        }
                        if (ItemMobToSpawner.saveToSpawner(x, y, z, this)) {
                            if (this.party != null) {
                                for (int i = 0; i < this.party.getMembersLength(); ++i) {
                                    final Entity e = (Entity)this.party.getMember(i);
                                    if (e != null) {
                                        if (e.ridingEntity != null) {
                                            e.ridingEntity.setDead();
                                        }
                                        e.setDead();
                                    }
                                }
                            }
                            if (this.ridingEntity != null) {
                                this.ridingEntity.setDead();
                            }
                            this.setDead();
                        }
                    }
                }
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void onSpawn() {
        super.onSpawn();
        if (this.party == null) {
            final double targetDistance = 10.0;
            final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(targetDistance, 1.2, targetDistance));
            for (final Entity entity : list) {
                if (entity instanceof EntityHumanBase) {
                    final EntityHumanBase human = (EntityHumanBase)entity;
                    if (!this.isOnSameTeam((EntityLivingBase)human)) {
                        continue;
                    }
                    if (human.party == null) {
                        human.tryPutIntoPArty(human);
                    }
                    if (human.party.tryToAddNewMember(this, false)) {
                        return;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public boolean canSprint() {
        return this.worldObj.difficultySetting.getDifficultyId() >= EnumDifficulty.HARD.getDifficultyId();
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        final float healthValue = this.getMaxHealth() / 4.0f;
        float armorValue = 0.0f;
        for (int i = 0; i < 4; ++i) {
            if (this.getEquipmentInSlot(1) != null) {
                armorValue += ((ItemArmor)this.getEquipmentInSlot(1).getItem()).getArmorMaterial().getDamageReductionAmount(2);
            }
        }
        armorValue /= 3.0f;
        this.experienceValue = (int)(healthValue + armorValue);
        if (this.isCaptain()) {
            ++this.experienceValue;
        }
        super.onDeath(damageSource);
    }
    
    protected void dropFewItems(final boolean par1, final int looting) {
        super.dropFewItems(par1, looting);
        int j = this.rand.nextInt(3);
        if (looting > 0) {
            j += this.rand.nextInt(looting + 1);
        }
        if (this.rand.nextInt(3) == 0) {
            for (int k = 0; k < j; ++k) {
                this.entityDropItem(new ItemStack(Items.emerald), 0.0f);
            }
        }
        if (this.rand.nextInt(3) == 0) {
            for (int k = 0; k < j; ++k) {
                this.entityDropItem(new ItemStack(Items.gold_nugget), 0.0f);
            }
        }
        else if (this.rand.nextInt(4) == 0) {
            for (int k = 0; k < j; ++k) {
                this.entityDropItem(new ItemStack(Items.dye, 1, 4), 0.0f);
            }
        }
    }
    
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.worldObj.difficultySetting.getDifficultyId() > EnumDifficulty.PEACEFUL.getDifficultyId();
    }
}
