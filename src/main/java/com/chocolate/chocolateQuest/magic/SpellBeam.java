package com.chocolate.chocolateQuest.magic;

import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.projectile.EntityProjectileBeam;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class SpellBeam extends SpellBase
{
    @Override
    public void onUpdate(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int angle) {
    }
    
    @Override
    public void onCastStart(final EntityLivingBase shooter, final Elements element, final ItemStack is) {
        final float dist = 0.3f;
        float height = -0.1f;
        if (!(shooter instanceof EntityPlayer)) {
            height = shooter.height;
        }
        final EntityProjectileBeam e = new EntityProjectileBeam(shooter.worldObj, shooter, 90.0f, dist, height, element);
        shooter.worldObj.spawnEntityInWorld((Entity)e);
    }
    
    @Override
    public void onShoot(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int chargeTime) {
    }
    
    @Override
    public boolean isProjectile() {
        return true;
    }
    
    @Override
    public int getRange(final ItemStack itemstack) {
        return 16;
    }
    
    @Override
    public boolean shouldUpdate() {
        return true;
    }
    
    @Override
    public int getCastingTime() {
        return 30;
    }
    
    @Override
    public int getCoolDown() {
        return 60;
    }
}
