package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.entity.Entity;
import java.util.Random;
import net.minecraft.util.Vec3;
import net.minecraft.util.DamageSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.IThrowableEntity;
import net.minecraft.entity.projectile.EntityThrowable;

public class EntityBaseBall extends EntityThrowable implements IThrowableEntity
{
    private ProjectileBase ballData;
    public ItemStack item;
    int ticksInAir;
    EntityLivingBase shootingEntity;
    float damageMultiplier;
    final int TYPE = 10;
    final int LEVEL = 11;
    final int ELEMENT = 12;
    
    public EntityBaseBall(final World par1World) {
        super(par1World);
        this.item = null;
        this.ticksInAir = 0;
        this.damageMultiplier = 1.0f;
    }
    
    public EntityBaseBall(final World world, final EntityLivingBase entityliving) {
        this(world, entityliving, 0);
    }
    
    public EntityBaseBall(final World world, final EntityLivingBase entityliving, final int type) {
        this(world, entityliving, type, 0);
    }
    
    public EntityBaseBall(final World world, final EntityLivingBase entityliving, final int type, final int lvl) {
        this(world, entityliving, type, lvl, Elements.physic);
    }
    
    public EntityBaseBall(final World world, final EntityLivingBase entityliving, final int type, final int lvl, final Elements element) {
        super(world, entityliving);
        this.item = null;
        this.ticksInAir = 0;
        this.damageMultiplier = 1.0f;
        this.setlvl(lvl);
        this.setElement(element);
        this.shootingEntity = entityliving;
        this.setType(type);
        final float s = this.getBallData().getSizeBB();
        this.setSize(s, s);
        this.playShootSound();
        this.getBallData().onSpawn();
    }
    
    public EntityBaseBall(final World world, final EntityLivingBase entityliving, final int type, final ItemStack item, final int lvl) {
        this(world, entityliving, type, lvl);
        this.item = item;
    }
    
    public EntityBaseBall(final World world, final EntityLivingBase entityliving, final double x, final double y, final double z, final int type, final int lvl) {
        this(world, entityliving, x, y, z, type, lvl, 0.0f);
    }
    
    public EntityBaseBall(final World world, final EntityLivingBase entityliving, final double x, final double y, final double z, final int type, final int lvl, final float accuracy) {
        this(world, entityliving, type, lvl);
        this.setThrowableHeading(x, y, z, 1.0f, accuracy);
    }
    
    protected void entityInit() {
        this.dataWatcher.addObject(10, (Object)0);
        this.dataWatcher.addObject(11, (Object)0);
        this.dataWatcher.addObject(12, (Object)0);
    }
    
    public void playShootSound() {
        if (!this.worldObj.isRemote) {
            this.getBallData().onSpawn();
        }
    }
    
    public void setType(final int par) {
        this.dataWatcher.updateObject(10, (Object)(byte)par);
    }
    
    public byte getType() {
        return this.dataWatcher.getWatchableObjectByte(10);
    }
    
    public void setlvl(final int par) {
        this.dataWatcher.updateObject(11, (Object)(byte)par);
    }
    
    public byte getlvl() {
        return this.dataWatcher.getWatchableObjectByte(11);
    }
    
    public void setElement(final Elements par) {
        this.dataWatcher.updateObject(12, (Object)(byte)par.ordinal());
    }
    
    public Elements getElement() {
        return Elements.values()[this.dataWatcher.getWatchableObjectByte(12)];
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(final double par1) {
        double d1 = this.boundingBox.getAverageEdgeLength() * 4.0;
        d1 *= 64.0;
        return par1 < d1 * d1;
    }
    
    public void onUpdate() {
        if (this.getBallData().longRange()) {
            this.motionX *= 1.01;
            this.motionZ *= 1.01;
        }
        if (this.getThrower() != null && this.getThrower().isDead) {
            this.setDead();
        }
        else {
            if (this.ticksInAir++ > this.getBallData().getMaxLifeTime()) {
                this.setDead();
            }
            super.onUpdate();
            this.getBallData().onUpdateInAir();
        }
    }
    
    protected float getGravityVelocity() {
        return this.getBallData().getGravityVelocity();
    }
    
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        this.getBallData().onImpact(par1MovingObjectPosition);
    }
    
    public double getMountedYOffset() {
        return -0.40000001192092893;
    }
    
    public double getYOffset() {
        return this.ballData.getYOffset();
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setByte("Type", this.getType());
        par1NBTTagCompound.setByte("lvl", this.getlvl());
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.setType(par1NBTTagCompound.getByte("Type"));
        this.setlvl(par1NBTTagCompound.getByte("lvl"));
        this.setDead();
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float damage) {
        this.getBallData().attackFrom(source, damage);
        this.setBeenAttacked();
        if (source.getEntity() != null && this.getBallData().canBounce()) {
            final Vec3 var3 = source.getEntity().getLookVec();
            if (var3 != null) {
                this.motionX = var3.xCoord;
                this.motionY = var3.yCoord;
                this.motionZ = var3.zCoord;
            }
            return true;
        }
        return false;
    }
    
    public Random getRNG() {
        return this.rand;
    }
    
    public void setDead() {
        this.getBallData().onDead();
        super.setDead();
    }
    
    public void setDamageMultiplier(final float damage) {
        this.damageMultiplier = damage;
    }
    
    public float getDamageMultiplier() {
        return this.damageMultiplier;
    }
    
    public int getTextureIndex() {
        return this.getBallData().getTextureIndex();
    }
    
    public float getBallSize() {
        return this.getBallData().getSize();
    }
    
    public ProjectileBase getBallData() {
        if (this.ballData == null || this.ballData instanceof ProjectileDummy) {
            this.ballData = ProjectileBase.getBallData(this);
            this.setSize(this.ballData.getSizeBB(), this.ballData.getSizeBB());
        }
        return this.ballData;
    }
    
    public void setBallData(final ProjectileBase data) {
        this.ballData = data;
    }
    
    public void setInmuneToFire(final boolean fire) {
        this.isImmuneToFire = fire;
    }
    
    public EntityLivingBase getThrower() {
        return this.shootingEntity;
    }
    
    public void setThrower(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.shootingEntity = (EntityLivingBase)entity;
        }
    }
}
