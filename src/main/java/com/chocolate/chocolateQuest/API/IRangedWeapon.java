package com.chocolate.chocolateQuest.API;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public interface IRangedWeapon
{
    float getRange(final EntityLivingBase p0, final ItemStack p1);
    
    int getCooldown(final EntityLivingBase p0, final ItemStack p1);
    
    void shootFromEntity(final EntityLivingBase p0, final ItemStack p1, final int p2, final Entity p3);
    
    boolean canBeUsedByEntity(final Entity p0);
    
    boolean isMeleeWeapon(final EntityLivingBase p0, final ItemStack p1);
    
    boolean shouldUpdate(final EntityLivingBase p0);
    
    int startAiming(final ItemStack p0, final EntityLivingBase p1, final Entity p2);
}
