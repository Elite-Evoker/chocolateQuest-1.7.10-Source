package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandGolem extends HandRanged
{
    public HandGolem(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
    }
    
    @Override
    public void doRangeAttack(final Entity target, final float f) {
        if (this.currentItem.getItem() instanceof ItemGolemWeapon) {
            ((ItemGolemWeapon)this.currentItem.getItem()).shootFromGolem((EntityLivingBase)this.owner, this.currentItem, this.owner.getHandAngle(this), target);
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.isAiming() && !this.owner.worldObj.isRemote && this.owner.getAttackTarget() == null && this.owner.riddenByEntity == null) {
            this.owner.setAiming(this, false);
        }
        super.onUpdate();
    }
    
    @Override
    public boolean attackWithRange(final Entity target, final float f) {
        return this.owner.riddenByEntity != null || super.attackWithRange(target, f);
    }
    
    @Override
    public void attackEntity(final Entity entity) {
        this.attackTime = 10;
    }
    
    @Override
    public boolean isRanged() {
        return true;
    }
    
    public void onClick() {
        if (this.aimDelayTime <= 0) {
            if (this.currentItem.getItem() instanceof ItemGolemWeapon) {
                ((ItemGolemWeapon)this.currentItem.getItem()).shootFromGolem((EntityLivingBase)this.owner, this.currentItem, this.owner.getHandAngle(this), null);
            }
            this.aiming = true;
            this.aimingTime = this.owner.getAttackSpeed() + 20;
            this.aimDelayTime = ((ItemGolemWeapon)this.currentItem.getItem()).getCooldown(this.currentItem);
        }
    }
}
