package com.chocolate.chocolateQuest.magic;

import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLiving;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class SpellTeleport extends SpellProjectile
{
    @Override
    public void onShoot(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int chargeTime) {
        final World world = shooter.worldObj;
        final double startX = shooter.posX;
        final double startY = shooter.posY;
        final double startZ = shooter.posZ;
        if (world.isRemote) {
            final Random random = shooter.getRNG();
            for (int i = 0; i < 8; ++i) {
                shooter.worldObj.spawnParticle("portal", shooter.posX + (random.nextFloat() - 0.5), shooter.posY + (random.nextFloat() - 0.5), shooter.posZ + (random.nextFloat() - 0.5), (random.nextFloat() - 0.5) / 2.0, (random.nextFloat() - 0.5) / 2.0, (random.nextFloat() - 0.5) / 2.0);
            }
        }
        else if (!world.isRemote) {
            final PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround(PacketSpawnParticlesAround.getParticleFromName(element.getParticle()), shooter.posX, shooter.posY, shooter.posZ);
            ChocolateQuest.channel.sendToAllAround((Entity)shooter, (IMessage)packet, 64);
        }
        if (shooter instanceof EntityPlayer) {
            final int dist = 60;
            final MovingObjectPosition mop = HelperPlayer.getBlockMovingObjectPositionFromPlayer(shooter.worldObj, shooter, dist, true);
            if (mop != null) {
                double y = 1.0;
                double x = 0.0;
                double z = 0.0;
                switch (mop.sideHit) {
                    case 0: {
                        y = 0.0;
                        break;
                    }
                    case 2: {
                        z = -1.0;
                        break;
                    }
                    case 3: {
                        z = 1.5;
                        break;
                    }
                    case 4: {
                        x = -0.5;
                        break;
                    }
                    case 5: {
                        x = 1.5;
                        break;
                    }
                }
                shooter.setPosition(mop.blockX + x, mop.blockY + y, mop.blockZ + z);
            }
        }
        else {
            Entity target = (Entity)shooter;
            final EntityLiving shooterLiving = (EntityLiving)shooter;
            if (shooterLiving.getAttackTarget() != null) {
                target = (Entity)shooterLiving.getAttackTarget();
            }
            this.damageNearby(world, (Entity)shooter, element);
            for (int j = 0; j < 6 && !this.castTeleport(shooter, target); ++j) {}
        }
        shooter.worldObj.playSoundEffect(startX, startY, startZ, "mob.endermen.portal", 1.0f, 1.0f);
        shooter.worldObj.playSoundAtEntity((Entity)shooter, "mob.endermen.portal", 1.0f, 1.0f);
        if (world.isRemote) {
            final Random random = shooter.getRNG();
            for (int i = 0; i < 16; ++i) {
                shooter.worldObj.spawnParticle("portal", shooter.posX + (random.nextFloat() - 0.5), shooter.posY + random.nextFloat(), shooter.posZ + (random.nextFloat() - 0.5), (random.nextFloat() - 0.5) / 2.0, (random.nextFloat() - 0.5) / 2.0, (random.nextFloat() - 0.5) / 2.0);
            }
        }
    }
    
    @Override
    public int getCastingTime() {
        return 3;
    }
    
    protected void damageNearby(final World world, final Entity shooter, final Elements element) {
        final int dist = 5;
        final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, shooter.boundingBox.expand((double)dist, 1.0, (double)dist));
        for (final Entity e : list) {
            if (e instanceof EntityLivingBase && e != shooter.riddenByEntity) {
                final DamageSource ds = element.getDamageSource(shooter);
                e.attackEntityFrom(ds, 1.0f);
            }
        }
    }
    
    protected boolean castTeleport(final EntityLivingBase shooter, final Entity target) {
        final World worldObj = shooter.worldObj;
        final float dist = 16.0f;
        final Random rand = shooter.getRNG();
        final double dX = target.posX + (rand.nextDouble() - 0.5) * dist;
        double dY = target.posY;
        final double dZ = target.posZ + (rand.nextDouble() - 0.5) * dist;
        final double startX = shooter.posX;
        final double startY = shooter.posY;
        final double startZ = shooter.posZ;
        boolean flag = false;
        final int i = MathHelper.floor_double(dX);
        int j = MathHelper.floor_double(dY);
        final int k = MathHelper.floor_double(dZ);
        if (worldObj.blockExists(i, j, k)) {
            boolean isNonSolidBlock = false;
            while (!isNonSolidBlock && j < 255) {
                final Material mat = worldObj.getBlock(i, j - 1, k).getMaterial();
                if (!mat.isSolid()) {
                    ++dY;
                    ++j;
                }
                else {
                    isNonSolidBlock = true;
                }
            }
            if (isNonSolidBlock) {
                shooter.setPosition(dX, dY, dZ);
                if (worldObj.getCollidingBoundingBoxes((Entity)shooter, shooter.boundingBox).size() == 0 && !worldObj.isAnyLiquid(shooter.boundingBox)) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            shooter.setPosition(startX, startY, startZ);
            return false;
        }
        return true;
    }
    
    @Override
    public int getCoolDown() {
        return 50;
    }
    
    @Override
    public int getRange(final ItemStack itemstack) {
        return 2;
    }
}
