package com.chocolate.chocolateQuest.utils;

import net.minecraft.util.MathHelper;
import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;

public class HelperPlayer
{
    public static Entity getTarget(final EntityLivingBase ep, final World world, final double dist) {
        final MovingObjectPosition mop = getMovingObjectPositionFromPlayer(ep, world, dist);
        if (mop != null) {
            return mop.entityHit;
        }
        return null;
    }
    
    public static MovingObjectPosition getMovingObjectPositionFromPlayer(final EntityLivingBase ep, final World world, final double dist) {
        return getMovingObjectPositionFromPlayer(ep, world, dist, 0.0);
    }
    
    public static MovingObjectPosition getMovingObjectPositionFromPlayer(final EntityLivingBase ep, final World world, double dist, final double bbExpand) {
        MovingObjectPosition mop = null;
        final float yOffset = ep.getEyeHeight();
        final Vec3 playerPos = Vec3.createVectorHelper(ep.posX, ep.posY + yOffset, ep.posZ);
        final Vec3 look = ep.getLookVec();
        if (ep instanceof EntityPlayer) {
            mop = getBlockMovingObjectPositionFromPlayer(world, ep, dist, true);
            if (mop != null) {
                final Vec3 v = Vec3.createVectorHelper(ep.posX - mop.blockX, ep.posY - mop.blockY, ep.posZ - mop.blockZ);
                dist = v.lengthVector();
            }
        }
        else {
            mop = world.rayTraceBlocks(playerPos, look, true);
        }
        final Vec3 playerView = playerPos.addVector(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
        final List list = world.getEntitiesWithinAABBExcludingEntity((Entity)ep, ep.boundingBox.addCoord(ep.getLookVec().xCoord * dist, ep.getLookVec().yCoord * dist, ep.getLookVec().zCoord * dist).expand(1.0, 1.0, 1.0));
        MovingObjectPosition tempMop = null;
        double prevDist = dist * dist;
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity1 = list.get(j);
            if (entity1.canBeCollidedWith() && entity1 != ep.ridingEntity) {
                if (ep != ep.ridingEntity) {
                    if (entity1 instanceof EntityLivingBase) {
                        final float f2 = 0.4f;
                        final AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(bbExpand, bbExpand, bbExpand);
                        final MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(playerPos, playerView);
                        if (movingobjectposition1 != null) {
                            movingobjectposition1.entityHit = entity1;
                            final double entityDist = entity1.getDistanceSqToEntity((Entity)ep);
                            if (entityDist < prevDist) {
                                tempMop = movingobjectposition1;
                                prevDist = entityDist;
                            }
                        }
                    }
                }
            }
        }
        if (tempMop != null) {
            return tempMop;
        }
        return mop;
    }
    
    public static MovingObjectPosition getBlockMovingObjectPositionFromPlayer(final World par1World, final EntityLivingBase par2EntityPlayer, final double reachDistance, final boolean par3) {
        final float f = 1.0f;
        final float rotPitch = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
        final float rotYaw = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
        final double posX = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * f;
        final double posY = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * f + 1.62 - par2EntityPlayer.yOffset;
        final double posZ = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * f;
        final Vec3 entityPos = Vec3.createVectorHelper(posX, posY, posZ);
        final float zDesp = MathHelper.cos(-rotYaw * 0.017453292f - 3.1415927f);
        final float xDesp = MathHelper.sin(-rotYaw * 0.017453292f - 3.1415927f);
        final float yScale = -MathHelper.cos(-rotPitch * 0.017453292f);
        final float lookY = MathHelper.sin(-rotPitch * 0.017453292f);
        final float lookX = xDesp * yScale;
        final float lookZ = zDesp * yScale;
        final Vec3 look = entityPos.addVector(lookX * reachDistance, lookY * reachDistance, lookZ * reachDistance);
        return par1World.rayTraceBlocks(entityPos, look, par3);
    }
}
