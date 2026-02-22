package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandBow extends HandRanged
{
    public HandBow(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
        this.range = 4096.0f;
        this.aimDelay = 10;
        this.isMeleWeapon = false;
    }
    
    @Override
    public void doRangeAttack(final Entity target, final float f) {
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
                hFact *= 0.28f;
            }
            this.owner.worldObj.playSoundAtEntity((Entity)this.owner, "random.bow", 1.0f, 1.0f / (this.owner.getRNG().nextFloat() * 0.4f + 0.8f));
            final float damage = 3.0f + (float)this.owner.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            final int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.currentItem);
            if (k > 0) {
                arrow.setDamage(damage + k * 0.5 + 0.5);
            }
            final int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.currentItem);
            if (l > 0) {
                arrow.setKnockbackStrength(l);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.currentItem) > 0) {
                arrow.setFire(100);
            }
            arrow.setThrowableHeading(arrowMotionX, d2 + hFact, arrowMotionZ, 1.0f, this.owner.accuracy);
            arrow.setDamage((double)damage);
            final EntityArrow entityArrow = arrow;
            entityArrow.motionX *= Math.max(1.0f, hFact / 18.0f);
            final EntityArrow entityArrow2 = arrow;
            entityArrow2.motionZ *= Math.max(1.0f, hFact / 18.0f);
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
}
