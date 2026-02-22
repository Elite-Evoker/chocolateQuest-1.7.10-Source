package com.chocolate.chocolateQuest.magic;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicAimed;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.entity.EntityLiving;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class SpellProjectileAimed extends SpellProjectile
{
    @Override
    public int getRange(final ItemStack itemstack) {
        return 32;
    }
    
    @Override
    public int getCoolDown() {
        return 12;
    }
    
    @Override
    public void onShoot(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int chargeTime) {
        final World world = shooter.worldObj;
        Entity target = null;
        if (shooter instanceof EntityPlayer) {
            final MovingObjectPosition mop = HelperPlayer.getMovingObjectPositionFromPlayer(shooter, world, 60.0, 2.0);
            if (mop != null) {
                target = mop.entityHit;
            }
        }
        else {
            target = (Entity)((EntityLiving)shooter).getAttackTarget();
        }
        if (!world.isRemote) {
            final int type = this.getType();
            final EntityBaseBall ball = new EntityBaseBall(world, shooter, type, this.getExpansion(is), element);
            if (target != null) {
                ball.setBallData(new ProjectileMagicAimed(ball, target));
            }
            ball.setDamageMultiplier(1.0f + this.getDamage(is) * 0.25f);
            world.spawnEntityInWorld((Entity)ball);
        }
    }
    
    @Override
    public int getType() {
        return 101;
    }
}
