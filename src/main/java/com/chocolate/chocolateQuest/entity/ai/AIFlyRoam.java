package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.entity.ai.EntityAIBase;

public class AIFlyRoam extends EntityAIBase
{
    private EntityBaseBoss owner;
    protected int defaultFlyHeight;
    int roamDistance;
    float distanceMoved;
    int destX;
    int destY;
    int destZ;
    
    public AIFlyRoam(final EntityBaseBoss par1EntityLiving, final int flyHeight) {
        this.roamDistance = 60;
        this.owner = par1EntityLiving;
        this.setMutexBits(4);
        this.defaultFlyHeight = flyHeight;
    }
    
    public boolean shouldExecute() {
        return this.owner.getAttackTarget() == null;
    }
    
    public boolean continueExecuting() {
        return null == this.owner.getAttackTarget();
    }
    
    public void startExecuting() {
        this.changeDest();
        super.startExecuting();
    }
    
    public void updateTask() {
        final double px = this.owner.posX - this.destX;
        final double pz = this.owner.posZ - this.destZ;
        final double py = this.owner.posY - 90.0;
        final double angleEntityHome = -MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(px, pz)) - 180.0);
        final double rotDiff = angleEntityHome - MathHelper.wrapAngleTo180_double((double)this.owner.rotationYaw);
        final float rotAngle = 4.0f;
        final float rotSpeed = 4.1f;
        if (rotDiff > rotAngle || rotDiff < -rotAngle) {
            this.owner.rotationYaw = this.owner.prevRotationYaw + rotSpeed;
        }
        final double moveSpeed = this.owner.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
        final double ry = Math.toRadians(this.owner.rotationYaw - 180.0f);
        this.owner.motionX = Math.sin(ry) * moveSpeed;
        this.owner.motionZ = -Math.cos(ry) * moveSpeed;
        this.handleMotionY();
        if (this.owner.posX <= this.destX + 5 && this.owner.posX >= this.destX - 5 && this.owner.posZ <= this.destZ + 5 && this.owner.posZ >= this.destZ - 5) {
            this.changeDest();
        }
    }
    
    protected void handleMotionY() {
        final float d = MathHelper.cos(this.distanceMoved / 5.0f) * 2.0f;
        final int blockX = MathHelper.floor_double(this.owner.posX + this.owner.motionX * 10.0);
        final int blockZ = MathHelper.floor_double(this.owner.posZ + this.owner.motionZ * 10.0);
        int flyHeight = this.defaultFlyHeight;
        if (this.owner.posY > this.owner.worldObj.getTopSolidOrLiquidBlock(MathHelper.floor_double(this.owner.posX), MathHelper.floor_double(this.owner.posZ))) {
            flyHeight = this.owner.worldObj.getTopSolidOrLiquidBlock(blockX, blockZ) + 8;
        }
        double extraMotionY = 0.0;
        if (this.owner.posY < flyHeight) {
            if (this.owner.rotationPitch > -0.3) {
                final EntityBaseBoss owner = this.owner;
                owner.rotationPitch += 0.01f;
            }
            extraMotionY += 0.4000000059604645;
            final EntityBaseBoss owner2 = this.owner;
            owner2.motionX /= 2.0;
            final EntityBaseBoss owner3 = this.owner;
            owner3.motionZ /= 2.0;
        }
        else if (this.owner.posY > flyHeight + 5) {
            if (this.owner.rotationPitch < 0.3) {
                final EntityBaseBoss owner4 = this.owner;
                owner4.rotationPitch -= 0.01f;
            }
            extraMotionY -= 0.4000000059604645;
            final EntityBaseBoss owner5 = this.owner;
            owner5.motionX /= 2.0;
            final EntityBaseBoss owner6 = this.owner;
            owner6.motionZ /= 2.0;
        }
        else {
            this.owner.rotationPitch = (float)Math.toDegrees(d);
        }
        final double moveSpeed = this.owner.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
        this.owner.motionY = -Math.sin(this.owner.rotationPitch) * moveSpeed / 5.0 + extraMotionY;
        this.distanceMoved += (float)(Math.abs(this.owner.motionX) + Math.abs(this.owner.motionZ));
    }
    
    protected void changeDest() {
        if (this.owner.hasHome()) {
            this.destX = this.owner.getHomePosition().posX + this.owner.getRNG().nextInt(this.roamDistance * 2) - this.roamDistance;
            this.destZ = this.owner.getHomePosition().posZ + this.owner.getRNG().nextInt(this.roamDistance * 2) - this.roamDistance;
        }
        else {
            this.destX = MathHelper.floor_double(this.owner.posX) + this.owner.getRNG().nextInt(this.roamDistance * 2) - this.roamDistance;
            this.destZ = MathHelper.floor_double(this.owner.posZ) + this.owner.getRNG().nextInt(this.roamDistance * 2) - this.roamDistance;
        }
    }
    
    public void resetTask() {
        this.destX = this.owner.getHomePosition().posX;
        this.destZ = this.owner.getHomePosition().posZ;
        super.resetTask();
    }
}
