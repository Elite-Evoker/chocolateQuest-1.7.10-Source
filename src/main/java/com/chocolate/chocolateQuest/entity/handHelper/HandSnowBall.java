package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandSnowBall extends HandRanged
{
    public HandSnowBall(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
        this.range = 4096.0f;
        this.aimDelay = 4;
        this.isMeleWeapon = false;
    }
    
    @Override
    public void doRangeAttack(final Entity target, final float f) {
        final EntitySnowball arrow = new EntitySnowball(this.owner.worldObj, (EntityLivingBase)this.owner);
        arrow.setPosition(this.owner.posX, this.owner.posY + this.owner.getEyeHeight(), this.owner.posZ);
        if (!this.owner.worldObj.isRemote) {
            final float distFactor = f / 10.0f;
            final double arrowMotionX = target.posX - this.owner.posX + target.motionX * distFactor;
            final double arrowMotionZ = target.posZ - this.owner.posZ + target.motionZ * distFactor;
            final double d2 = target.posY + target.getEyeHeight() - 0.699999988079071 - arrow.posY;
            float hFact;
            final float dist = hFact = MathHelper.sqrt_float(f);
            if (dist < 16.0f) {
                hFact *= 0.28f;
            }
            this.owner.worldObj.playSoundAtEntity((Entity)this.owner, "random.bow", 1.0f, 1.0f / (this.owner.getRNG().nextFloat() * 0.4f + 0.8f));
            final float damage = (float)this.owner.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 2.0f;
            arrow.setThrowableHeading(arrowMotionX, d2 + hFact, arrowMotionZ, 1.0f, this.owner.accuracy);
            final EntitySnowball entitySnowball = arrow;
            entitySnowball.motionX *= Math.max(1.0f, hFact / 18.0f);
            final EntitySnowball entitySnowball2 = arrow;
            entitySnowball2.motionZ *= Math.max(1.0f, hFact / 18.0f);
            this.owner.worldObj.spawnEntityInWorld((Entity)arrow);
        }
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
        return 10;
    }
}
