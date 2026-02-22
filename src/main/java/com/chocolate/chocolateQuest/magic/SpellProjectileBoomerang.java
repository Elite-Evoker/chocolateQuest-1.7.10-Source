package com.chocolate.chocolateQuest.magic;

import net.minecraft.world.World;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicAimed;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class SpellProjectileBoomerang extends SpellProjectile
{
    @Override
    public int getCoolDown() {
        return 40;
    }
    
    @Override
    public void onShoot(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int chargeTime) {
        final World world = shooter.worldObj;
        if (!world.isRemote) {
            final int type = this.getType();
            final EntityBaseBall ball = new EntityBaseBall(world, shooter, type, this.getExpansion(is), element);
            ball.setBallData(new ProjectileMagicAimed(ball, (Entity)shooter, 10));
            ball.setDamageMultiplier(1.0f + this.getDamage(is) * 0.25f);
            world.spawnEntityInWorld((Entity)ball);
        }
    }
    
    @Override
    public int getType() {
        return 101;
    }
}
