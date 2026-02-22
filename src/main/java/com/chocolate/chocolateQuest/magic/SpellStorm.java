package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLiving;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class SpellStorm extends SpellBase
{
    @Override
    public void onShoot(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int chargeTime) {
        final World world = shooter.worldObj;
        if (!world.isRemote) {
            if (shooter instanceof EntityPlayer) {
                final MovingObjectPosition mop = HelperPlayer.getBlockMovingObjectPositionFromPlayer(shooter.worldObj, shooter, 60.0, true);
                if (mop != null) {
                    this.shootBallAt(shooter, element, mop.blockX, mop.blockY, mop.blockZ, is);
                }
                else {
                    final Vec3 look = shooter.getLookVec();
                    final double dist = 10.0;
                    this.shootBallAt(shooter, element, shooter.posX + look.xCoord * dist, shooter.posY, shooter.posZ + look.zCoord * dist, is);
                }
            }
            else {
                final Vec3 look2 = shooter.getLookVec();
                double dist2 = 10.0;
                final Entity target = (Entity)((EntityLiving)shooter).getAttackTarget();
                if (target != null) {
                    dist2 = shooter.getDistanceToEntity(target);
                }
                this.shootBallAt(shooter, element, shooter.posX + look2.xCoord * dist2, shooter.posY, shooter.posZ + look2.zCoord * dist2, is);
            }
        }
    }
    
    public void shootBallAt(final EntityLivingBase shooter, final Elements element, final double x, final double y, final double z, final ItemStack is) {
        final World world = shooter.worldObj;
        final int type = this.getType();
        final EntityBaseBall ball = new EntityBaseBall(world, shooter, type, this.getExpansion(is), element);
        ball.posX = x;
        ball.posY = y;
        ball.posZ = z;
        ball.setDamageMultiplier(1.0f + this.getDamage(is) * 0.25f);
        world.spawnEntityInWorld((Entity)ball);
    }
    
    @Override
    public int getCoolDown() {
        return 300;
    }
    
    @Override
    public int getRange(final ItemStack itemstack) {
        return 32;
    }
    
    @Override
    public int getCastingTime() {
        return 40;
    }
    
    public int getType() {
        return 104;
    }
    
    @Override
    public boolean isProjectile() {
        return true;
    }
}
