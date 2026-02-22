package com.chocolate.chocolateQuest.block;

import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntitySkeleton;
import com.chocolate.chocolateQuest.misc.EquipementHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTTagList;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BlockMobSpawnerTileEntity extends TileEntity
{
    public int delay;
    public int mob;
    public int metadata;
    public double yaw;
    public double yaw2;
    public NBTTagCompound mobNBT;
    
    public BlockMobSpawnerTileEntity() {
        this.mobNBT = null;
        this.delay = -1;
        this.yaw2 = 0.0;
        this.delay = 20;
    }
    
    public boolean anyPlayerInRange() {
        final EntityPlayer player = this.worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 16.0);
        return player != null && !player.capabilities.isCreativeMode;
    }
    
    public void updateEntity() {
        if (!this.anyPlayerInRange()) {
            return;
        }
        if (!this.worldObj.isRemote) {
            if (this.delay == -1) {
                this.updateDelay();
            }
            if (this.delay > 0) {
                --this.delay;
                return;
            }
            this.spawnEntity();
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
        }
        super.updateEntity();
    }
    
    public void spawnEntity() {
        final int tmob = this.mob;
        Entity entity = getEntity(this.metadata, tmob, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        if (entity == null) {
            entity = (Entity)new EntityZombie(this.worldObj);
        }
        if (this.mobNBT != null) {
            this.editEntityStats(this.mobNBT);
            final Entity e = EntityList.createEntityFromNBT(this.mobNBT, this.worldObj);
            if (e != null) {
                entity = e;
            }
            if (entity instanceof EntityHumanBase) {
                final EntityHumanBase human = (EntityHumanBase)entity;
                human.readEntityFromSpawnerNBT(this.mobNBT, this.xCoord, this.yCoord, this.zCoord);
                if (this.mobNBT.getTag("Riding") != null) {
                    final NBTTagCompound ridingNBT = (NBTTagCompound)this.mobNBT.getTag("Riding");
                    final Entity riding = EntityList.createEntityFromNBT(ridingNBT, this.worldObj);
                    if (riding != null) {
                        riding.setPosition(this.xCoord + 0.5, (double)this.yCoord, this.zCoord + 0.5);
                        this.worldObj.spawnEntityInWorld(riding);
                        human.mountEntity(riding);
                        if (riding instanceof EntityHumanBase) {
                            ((EntityHumanBase)riding).entityTeam = human.entityTeam;
                        }
                    }
                }
            }
        }
        entity.setLocationAndAngles(this.xCoord + 0.5, (double)this.yCoord, this.zCoord + 0.5, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(entity);
        this.worldObj.playAuxSFX(2004, this.xCoord, this.yCoord, this.zCoord, 0);
        if (this.worldObj.isRemote && entity instanceof EntityLiving) {
            ((EntityLiving)entity).spawnExplosionParticle();
        }
    }
    
    private void updateDelay() {
        this.delay = 200 + this.worldObj.rand.nextInt(600);
    }
    
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.mob = par1NBTTagCompound.getInteger("mob");
        this.metadata = par1NBTTagCompound.getInteger("typeMob");
        this.mobNBT = (NBTTagCompound)par1NBTTagCompound.getTag("mobData");
        if (this.mobNBT != null && this.mobNBT.hasNoTags()) {
            this.mobNBT = null;
        }
    }
    
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("mob", this.mob);
        par1NBTTagCompound.setInteger("typeMob", this.metadata);
        if (this.mobNBT != null) {
            par1NBTTagCompound.setTag("mobData", (NBTBase)this.mobNBT);
        }
    }
    
    public void editEntityStats(final NBTTagCompound eTag) {
        DungeonMonstersBase mobType = null;
        if (this.mob >= 0) {
            mobType = RegisterDungeonMobs.mobList.get(this.mob);
        }
        if (mobType != null && (eTag.getString("id").equals("chocolateQuest.dummy") || eTag.getString("id").equals(""))) {
            final NBTTagList list = eTag.getTagList("Party", (int)eTag.getId());
            for (int i = 0; i < list.tagCount(); ++i) {
                this.editEntityStats(list.getCompoundTagAt(i));
            }
            eTag.setString("id", mobType.getRegisteredEntityName());
            eTag.setString("team", mobType.getTeamName());
            final NBTTagList attributes = eTag.getTagList("Attributes", (int)eTag.getId());
            for (int a = 0; a < attributes.tagCount(); ++a) {
                final NBTTagCompound tag = attributes.getCompoundTagAt(a);
                final String name = tag.getString("Name");
                if (name.equals("generic.maxHealth")) {
                    final double base = tag.getDouble("Base");
                    tag.setDouble("Base", base * mobType.getHealth());
                    eTag.setShort("Health", (short)(base * mobType.getHealth()));
                    eTag.setFloat("HealF", (float)(base * mobType.getHealth()));
                }
                if (name.equals("generic.attackDamage")) {
                    final double base = tag.getDouble("Base");
                    tag.setDouble("generic.attackDamage", mobType.getAttack());
                }
                if (name.equals("generic.followRange")) {
                    final double base = tag.getDouble("Base");
                    tag.setDouble("generic.followRange", mobType.getRange());
                }
            }
        }
    }
    
    public static Entity getEntity(final int metadata, final int mob, final World world, final int x, final int y, final int z) {
        Entity e = null;
        DungeonMonstersBase mobType = null;
        if (mob >= 0) {
            mobType = RegisterDungeonMobs.mobList.get(mob);
        }
        if (mobType != null) {
            if (metadata <= 14) {
                e = mobType.getEntity(world, x, y, z);
            }
            else {
                e = mobType.getBoss(world, x, y, z);
            }
            if (e instanceof EntityHumanBase) {
                EquipementHelper.equipEntity((EntityLivingBase)e, metadata % 5);
            }
        }
        else if (metadata < 5) {
            e = (Entity)new EntityZombie(world);
        }
        else if (metadata <= 9) {
            e = (Entity)new EntitySkeleton(world);
        }
        else {
            e = (Entity)new EntitySpider(world);
        }
        return e;
    }
}
