package com.chocolate.chocolateQuest.magic;

import net.minecraft.util.DamageSource;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class SpellSpray extends SpellBase
{
    @Override
    public void onUpdate(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int angle) {
        final World world = shooter.worldObj;
        final float rotationYaw = (float)MathHelper.wrapAngleTo180_double((double)shooter.rotationYawHead);
        final double armDist = 0.5;
        double offsetY = 0.2;
        if (shooter instanceof EntityHumanBase) {
            offsetY = 1.4;
        }
        final double posX = shooter.posX - Math.sin(Math.toRadians(rotationYaw + angle)) * armDist;
        final double posY = shooter.posY + offsetY;
        final double posZ = shooter.posZ + Math.cos(Math.toRadians(rotationYaw + angle)) * armDist;
        float x = (float)(-Math.sin(Math.toRadians(rotationYaw)));
        float z = (float)Math.cos(Math.toRadians(rotationYaw));
        final double y = -Math.sin(Math.toRadians(shooter.rotationPitch));
        x *= (float)(1.0 - Math.abs(y));
        z *= (float)(1.0 - Math.abs(y));
        if (world.isRemote) {
            final Random random = shooter.getRNG();
            for (int i = 0; i < 4; ++i) {
                EffectManager.spawnElementParticle(0, world, posX, posY, posZ, (x + random.nextFloat() - 0.5) / 3.0, (y + random.nextFloat() - 0.5) / 8.0, (z + random.nextFloat() - 0.5) / 3.0, element);
            }
        }
        else {
            final int dist = 5;
            final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)shooter, shooter.boundingBox.addCoord(shooter.getLookVec().xCoord * dist, shooter.getLookVec().yCoord * dist, shooter.getLookVec().zCoord * dist).expand(1.0, 1.0, 1.0));
            for (final Entity e : list) {
                if (e instanceof EntityLivingBase && e != shooter.riddenByEntity) {
                    final double d = posX - e.posX;
                    final double d2 = posZ - e.posZ;
                    double rotDiff = Math.atan2(d, d2);
                    rotDiff = rotDiff * 180.0 / 3.141592;
                    rotDiff = -MathHelper.wrapAngleTo180_double(rotDiff - 180.0);
                    rotDiff -= rotationYaw;
                    if (Math.abs(rotDiff) >= 30.0) {
                        continue;
                    }
                    float damage = 1.0f + this.getDamage(is) * 0.25f;
                    damage = element.onHitEntity((Entity)shooter, e, damage);
                    final DamageSource ds = element.getDamageSource((Entity)shooter);
                    e.attackEntityFrom(ds, damage);
                }
            }
        }
    }
    
    @Override
    public int getRange(final ItemStack itemstack) {
        return 5;
    }
    
    @Override
    public boolean shouldUpdate() {
        return true;
    }
    
    @Override
    public int getCastingTime() {
        return 25;
    }
    
    @Override
    public int getCoolDown() {
        return 60;
    }
}
