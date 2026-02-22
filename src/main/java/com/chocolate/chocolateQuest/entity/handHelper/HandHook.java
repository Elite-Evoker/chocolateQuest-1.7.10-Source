package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;

public class HandHook extends HandRanged
{
    EntityHookShoot web;
    ItemHookShoot hook;
    
    public HandHook(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
        if (itemStack.getItem() == ChocolateQuest.hookSword) {
            this.hook = (ItemHookShoot)ChocolateQuest.manualShoot;
        }
        else {
            this.hook = (ItemHookShoot)itemStack.getItem();
        }
    }
    
    @Override
    public void doRangeAttack(final Entity target, final float f) {
        if (this.web == null) {
            final int pos = 0;
            if (this.web != null) {
                this.web.setDead();
                this.web = null;
            }
            (this.web = new EntityHookShoot(this.owner.worldObj, (EntityLivingBase)this.owner, this.hook.getHookType())).setThrowableHeading(target.posX - this.owner.posX, target.posY - this.owner.posY, target.posZ - this.owner.posZ, 1.0f, 0.0f);
            this.owner.worldObj.spawnEntityInWorld((Entity)this.web);
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.web != null) {
            if (this.web.isDead) {
                this.web = null;
            }
            else if (this.web.isReeling()) {
                boolean setHookDead = false;
                final double webDist = this.owner.getDistanceSqToEntity((Entity)this.web);
                float width = this.owner.width + 1.0f;
                if (this.web.hookedEntity != null) {
                    width += this.web.hookedEntity.width;
                }
                else if (this.owner.getAttackTarget() != null && webDist < this.owner.getDistanceSqToEntity((Entity)this.owner.getAttackTarget())) {
                    setHookDead = true;
                }
                if (webDist < width * width) {
                    setHookDead = true;
                }
                if (this.web.ticksExisted > 100 || setHookDead) {
                    this.web.setDead();
                    this.web = null;
                }
            }
        }
        super.onUpdate();
    }
}
