package com.chocolate.chocolateQuest.magic;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class SpellRepel extends SpellBase
{
    @Override
    public void onShoot(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int chargeTime) {
        System.out.println(shooter);
        final World world = shooter.worldObj;
        final float rotationYaw = (float)MathHelper.wrapAngleTo180_double((double)shooter.rotationYawHead);
        final double armDist = 0.5;
        double offsetY = 0.2;
        if (shooter instanceof EntityHumanBase) {
            offsetY = 1.4;
        }
        final double posX = shooter.posX - Math.sin(Math.toRadians(rotationYaw)) * armDist;
        final double posY = shooter.posY + offsetY;
        final double posZ = shooter.posZ + Math.cos(Math.toRadians(rotationYaw)) * armDist;
        double x;
        double y;
        double z;
        if (shooter instanceof EntityPlayer) {
            final Vec3 v = shooter.getLookVec();
            x = v.xCoord;
            y = v.yCoord;
            z = v.zCoord;
        }
        else {
            x = (float)(-Math.sin(Math.toRadians(rotationYaw)));
            y = -Math.sin(Math.toRadians(shooter.rotationPitch));
            z = (float)Math.cos(Math.toRadians(rotationYaw));
        }
        x *= 1.0 - Math.abs(y);
        z *= 1.0 - Math.abs(y);
        final int velocity = 4;
        if (world.isRemote) {
            final Random random = shooter.getRNG();
            for (int i = 0; i < 8; ++i) {
                EffectManager.spawnElementParticle(0, world, posX, posY, posZ, (x * velocity + random.nextFloat() - 0.5) / 3.0, (y + random.nextFloat() - 0.5) / 8.0, (z * velocity + random.nextFloat() - 0.5) / 3.0, element);
            }
        }
        else {
            final int dist = 5;
            final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)shooter, shooter.boundingBox.addCoord(shooter.getLookVec().xCoord * dist, shooter.getLookVec().yCoord * dist, shooter.getLookVec().zCoord * dist).expand(1.0, 1.0, 1.0));
            for (final Entity e : list) {
                if (e instanceof EntityLivingBase && e != shooter.riddenByEntity) {
                    e.addVelocity(x * velocity, y * velocity / 2.0, z * velocity);
                }
                else {
                    if (!(e instanceof EntityBaseBall)) {
                        continue;
                    }
                    e.addVelocity(x * velocity, y * velocity, z * velocity);
                    ((EntityBaseBall)e).setThrower((Entity)shooter);
                }
            }
        }
    }
    
    @Override
    public int getRange(final ItemStack itemstack) {
        return 5;
    }
    
    @Override
    public boolean isProjectile() {
        return true;
    }
    
    @Override
    public int getCastingTime() {
        return 4;
    }
    
    @Override
    public int getCoolDown() {
        return 5;
    }
}
