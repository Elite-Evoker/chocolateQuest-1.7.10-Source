package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.API.IRangedWeapon;

public class HandRanged extends HandHelper
{
    boolean aiming;
    boolean isMeleWeapon;
    protected float range;
    protected int aimDelay;
    protected int aimingTime;
    protected int aimDelayTime;
    IRangedWeapon rangedWeapon;
    
    public HandRanged(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
        this.aiming = false;
        if (this.currentItem.getItem() instanceof IRangedWeapon) {
            this.rangedWeapon = (IRangedWeapon)this.currentItem.getItem();
            this.range = this.rangedWeapon.getRange((EntityLivingBase)owner, itemStack);
            this.aimDelay = this.rangedWeapon.getCooldown((EntityLivingBase)owner, itemStack);
            this.isMeleWeapon = this.rangedWeapon.isMeleeWeapon((EntityLivingBase)owner, itemStack);
        }
        else {
            this.range = 4096.0f;
            this.aimDelay = 10;
            this.isMeleWeapon = false;
        }
    }
    
    @Override
    public boolean attackWithRange(final Entity target, final float f) {
        if (f < this.getRange()) {
            if (this.isAiming()) {
                if (this.aimingTime <= 0) {
                    this.doRangeAttack(target, f);
                    this.owner.setAiming(this, false);
                    this.aimDelayTime = this.aimDelay;
                }
            }
            else if (this.aimDelayTime <= 0) {
                final int aimTime = this.getAimTime(target);
                if (aimTime >= 0) {
                    this.owner.setAiming(this, true);
                    this.aimingTime = aimTime;
                }
            }
            return true;
        }
        return false;
    }
    
    public int getAimTime(final Entity target) {
        if (this.rangedWeapon != null) {
            return this.rangedWeapon.startAiming(this.currentItem, (EntityLivingBase)this.owner, target);
        }
        return this.owner.getAttackSpeed();
    }
    
    public void doRangeAttack(final Entity target, final float f) {
        if (this.currentItem.getItem() instanceof IRangedWeapon) {
            ((IRangedWeapon)this.currentItem.getItem()).shootFromEntity((EntityLivingBase)this.owner, this.currentItem, this.owner.getHandAngle(this), target);
        }
        else {
            final EntityArrow arrow = new EntityArrow(this.owner.worldObj, (EntityLivingBase)this.owner, 0.0f);
            arrow.setPosition(this.owner.posX, this.owner.posY + this.owner.getEyeHeight(), this.owner.posZ);
            if (!this.owner.worldObj.isRemote) {
                if (target.posY < this.owner.posY) {
                    arrow.setIsCritical(true);
                }
                final float distFactor = f / 10.0f;
                final double arrowMotionX = target.posX - this.owner.posX + target.motionX * distFactor;
                final double arrowMotionZ = target.posZ - this.owner.posZ + target.motionZ * distFactor;
                final double d2 = target.posY + target.getEyeHeight() - 0.699999988079071 - arrow.posY;
                float hFact;
                final float dist = hFact = MathHelper.sqrt_float(f);
                if (dist < 16.0f) {
                    hFact *= 0.38f;
                }
                this.owner.worldObj.playSoundAtEntity((Entity)this.owner, "random.bow", 1.0f, 1.0f / (this.owner.getRNG().nextFloat() * 0.4f + 0.8f));
                final float damage = (float)this.owner.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 2.0f;
                arrow.setThrowableHeading(arrowMotionX, d2 + hFact, arrowMotionZ, 1.0f, this.owner.accuracy);
                arrow.setDamage((double)damage);
                final EntityArrow entityArrow = arrow;
                entityArrow.motionX *= Math.max(1.0f, hFact / 18.0f);
                final EntityArrow entityArrow2 = arrow;
                entityArrow2.motionZ *= Math.max(1.0f, hFact / 18.0f);
                this.owner.worldObj.spawnEntityInWorld((Entity)arrow);
            }
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.isAiming() && this.rangedWeapon != null && this.rangedWeapon.shouldUpdate((EntityLivingBase)this.owner)) {
            this.currentItem.getItem().onUpdate(this.currentItem, this.owner.worldObj, (Entity)this.owner, this.owner.getHandAngle(this), false);
        }
        if (this.aimDelayTime > 0) {
            --this.aimDelayTime;
        }
        if (this.aimingTime >= -10) {
            --this.aimingTime;
        }
        else if (this.isAiming() && !this.owner.worldObj.isRemote) {
            this.owner.setAiming(this, false);
        }
        super.onUpdate();
    }
    
    @Override
    public void setAiming(final boolean aim) {
        this.aiming = aim;
        this.aimingTime = 0;
    }
    
    @Override
    public boolean isAiming() {
        return this.aiming;
    }
    
    @Override
    public boolean isRanged() {
        return true;
    }
    
    @Override
    public double getDistanceToStopAdvancing() {
        return this.isMeleWeapon ? super.getDistanceToStopAdvancing() : this.getRange();
    }
    
    @Override
    public double getMaxRangeForAttack() {
        return this.getRange();
    }
    
    public double getRange() {
        if (this.rangedWeapon != null) {
            return this.rangedWeapon.getRange((EntityLivingBase)this.owner, this.currentItem);
        }
        return this.range;
    }
}
