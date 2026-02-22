package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandPotion extends HandRanged
{
    public HandPotion(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
        this.range = 64.0f;
        this.aimDelay = 50;
        this.aimingTime = 30;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
    }
    
    @Override
    public void doRangeAttack(final Entity target, final float f) {
        if (!this.owner.worldObj.isRemote) {
            final EntityPotion entityPotion;
            final EntityPotion e = entityPotion = new EntityPotion(this.owner.worldObj, (EntityLivingBase)this.owner, this.currentItem);
            entityPotion.motionX *= 1.2;
            final EntityPotion entityPotion2 = e;
            entityPotion2.motionZ *= 1.2;
            this.owner.worldObj.spawnEntityInWorld((Entity)e);
            this.owner.swingHand(this);
        }
    }
    
    @Override
    public double getRange() {
        return this.range;
    }
    
    @Override
    public ItemStack getItem() {
        if (!this.owner.isAiming()) {
            return null;
        }
        return super.getItem();
    }
}
