package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.Block;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;

public class AIAnimalMountedByEntity extends EntityAIControlledByPlayer
{
    private EntityLiving entityHost;
    private final float maxSpeed;
    private float currentSpeed;
    float acceleration;
    EntityHumanBase rider;
    float prevRotation;
    
    public AIAnimalMountedByEntity(final EntityLiving par1EntityLiving, final float maxSpeed) {
        super(par1EntityLiving, maxSpeed);
        this.currentSpeed = 0.0f;
        this.acceleration = 0.02f;
        this.prevRotation = 0.0f;
        this.entityHost = par1EntityLiving;
        this.maxSpeed = maxSpeed;
    }
    
    public boolean shouldExecute() {
        if (!(this.entityHost.riddenByEntity instanceof EntityHumanBase)) {
            return false;
        }
        final EntityHumanBase e = (EntityHumanBase)this.entityHost.riddenByEntity;
        if (e.currentPos != null) {
            return false;
        }
        this.rider = e;
        return false;
    }
    
    public void resetTask() {
        this.currentSpeed = 0.0f;
    }
    
    public void updateTask() {
        if (this.entityHost.getNavigator().getPath() != null) {
            this.entityHost.getNavigator().clearPathEntity();
        }
        float rotVariation = MathHelper.wrapAngleTo180_float(this.rider.rotationYawHead - this.prevRotation);
        final float minVariation = 5.0f;
        if (rotVariation > minVariation) {
            rotVariation = minVariation;
        }
        if (rotVariation < -minVariation) {
            rotVariation = -minVariation;
        }
        this.entityHost.rotationYaw = MathHelper.wrapAngleTo180_float(this.prevRotation + rotVariation);
        this.rider.rotationYaw = this.entityHost.rotationYaw;
        this.prevRotation = this.entityHost.rotationYaw;
        this.updateAcceleration();
        this.currentSpeed += this.acceleration;
        this.currentSpeed = Math.max(this.currentSpeed, 0.01f);
        this.checkToJump(this.currentSpeed = Math.min(this.currentSpeed, this.maxSpeed));
        this.entityHost.moveEntityWithHeading(0.0f, this.currentSpeed);
    }
    
    public void updateAcceleration() {
        final EntityHumanBase rider = (EntityHumanBase)this.entityHost.riddenByEntity;
        if (rider.getAttackTarget() != null) {
            final EntityLivingBase target = rider.getAttackTarget();
            final double rotDiff = BDHelper.getAngleBetweenEntities((Entity)this.entityHost, (Entity)target);
            double rot = MathHelper.wrapAngleTo180_double(rotDiff - this.entityHost.rotationYaw);
            rot = Math.abs(rot);
            if (rot > 20.0) {
                this.acceleration = -0.01f;
            }
            else {
                this.acceleration = 0.025f;
                this.boostSpeed();
            }
        }
    }
    
    public void checkToJump(float currentSpeedTemp) {
        final int i = MathHelper.floor_double(this.entityHost.posX);
        final int j = MathHelper.floor_double(this.entityHost.posY);
        final int k = MathHelper.floor_double(this.entityHost.posZ);
        float despX = MathHelper.sin(this.entityHost.rotationYaw * 3.1415927f / 180.0f);
        float despZ = MathHelper.cos(this.entityHost.rotationYaw * 3.1415927f / 180.0f);
        final float f7 = this.entityHost.getAIMoveSpeed() * currentSpeedTemp / Math.max(currentSpeedTemp, 1.0f);
        currentSpeedTemp *= f7;
        despX = -(currentSpeedTemp * despX);
        despZ *= currentSpeedTemp;
        if (MathHelper.abs(despX) > MathHelper.abs(despZ)) {
            if (despX < 0.0f) {
                despX -= this.entityHost.width / 2.0f;
            }
            if (despX > 0.0f) {
                despX += this.entityHost.width / 2.0f;
            }
            despZ = 0.0f;
        }
        else {
            despX = 0.0f;
            if (despZ < 0.0f) {
                despZ -= this.entityHost.width / 2.0f;
            }
            if (despZ > 0.0f) {
                despZ += this.entityHost.width / 2.0f;
            }
        }
        final int xNext = MathHelper.floor_double(this.entityHost.posX + despX);
        final int zNext = MathHelper.floor_double(this.entityHost.posZ + despZ);
        final PathPoint pathpoint = new PathPoint(MathHelper.floor_float(this.entityHost.width + 1.0f), MathHelper.floor_float(this.entityHost.height + this.rider.height + 1.0f), MathHelper.floor_float(this.entityHost.width + 1.0f));
        if (i != xNext || k != zNext) {
            final Block block = this.entityHost.worldObj.getBlock(i, j, k);
            final Block nextBlockID = this.entityHost.worldObj.getBlock(i, j - 1, k);
            final boolean flag = this.isBlockHalf(block) && this.isBlockHalf(nextBlockID);
            if (!flag && PathFinder.canEntityStandAt((Entity)this.entityHost, xNext, j, zNext, pathpoint, false, false, true) == 0 && PathFinder.canEntityStandAt((Entity)this.entityHost, i, j + 1, k, pathpoint, false, false, true) == 1 && PathFinder.canEntityStandAt((Entity)this.entityHost, xNext, j + 1, zNext, pathpoint, false, false, true) == 1) {
                this.entityHost.getJumpHelper().setJumping();
            }
        }
    }
    
    private boolean isBlockHalf(final Block par1) {
        return par1.getRenderType() == 10 || par1 instanceof BlockSlab;
    }
}
