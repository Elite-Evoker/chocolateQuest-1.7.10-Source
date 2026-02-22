package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.util.Vec3;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandFireChange extends HandRanged
{
    public HandFireChange(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
        this.range = 4096.0f;
        this.aimDelay = 3;
        this.isMeleWeapon = false;
    }
    
    @Override
    public void doRangeAttack(final Entity target, final float f) {
        final Vec3 vec = this.owner.getLookVec();
        final EntitySmallFireball arrow = new EntitySmallFireball(this.owner.worldObj, (EntityLivingBase)this.owner, vec.xCoord, vec.yCoord, vec.zCoord);
        arrow.setPosition(this.owner.posX, this.owner.posY + this.owner.getEyeHeight(), this.owner.posZ);
        arrow.motionX = vec.xCoord;
        arrow.motionY = vec.yCoord;
        arrow.motionZ = vec.zCoord;
        arrow.accelerationX = vec.xCoord * 0.01;
        arrow.accelerationY = vec.yCoord * 0.01;
        arrow.accelerationZ = vec.zCoord * 0.01;
        this.owner.worldObj.spawnEntityInWorld((Entity)arrow);
    }
    
    @Override
    public double getDistanceToStopAdvancing() {
        return this.getRange();
    }
    
    @Override
    public double getRange() {
        return this.range;
    }
    
    @Override
    public int getAimTime(final Entity target) {
        if (this.rangedWeapon != null) {
            return this.rangedWeapon.startAiming(this.currentItem, (EntityLivingBase)this.owner, target);
        }
        return this.owner.getAttackSpeed();
    }
}
