package com.chocolate.chocolateQuest.magic;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class SpellProjectile extends SpellBase
{
    @Override
    public void onShoot(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int chargeTime) {
        final World world = shooter.worldObj;
        if (!world.isRemote) {
            final int type = this.getType();
            final EntityBaseBall ball = new EntityBaseBall(world, shooter, type, this.getExpansion(is), element);
            ball.setDamageMultiplier(1.0f + this.getDamage(is) * 0.25f);
            world.spawnEntityInWorld((Entity)ball);
        }
    }
    
    public int getType() {
        return 100;
    }
    
    @Override
    public boolean isProjectile() {
        return true;
    }
}
