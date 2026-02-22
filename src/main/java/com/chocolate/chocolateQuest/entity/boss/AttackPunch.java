package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.util.MathHelper;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;

public class AttackPunch
{
    public double[] swingDest;
    public double[] swingStart;
    public int attackTime;
    public boolean isAttacking;
    byte handFlag;
    EntityBaseBoss owner;
    int attackSpeed;
    private float shoulderHeight;
    private float armLength;
    private float distanceToBody;
    private int angleOffset;
    private int heightOffset;
    public double posX;
    public double posY;
    public double posZ;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    
    public AttackPunch(final EntityBaseBoss owner, final byte handFlag) {
        this.swingDest = new double[3];
        this.swingStart = new double[3];
        this.attackTime = 0;
        this.isAttacking = false;
        this.attackSpeed = 20;
        this.shoulderHeight = 1.0f;
        this.armLength = 1.0f;
        this.distanceToBody = 0.4f;
        this.heightOffset = -1;
        this.owner = owner;
        this.handFlag = handFlag;
    }
    
    public AttackPunch(final EntityBaseBoss owner, final byte handFlag, final float shoulderHeight, final float armScale) {
        this(owner, handFlag);
        this.shoulderHeight = shoulderHeight;
        this.armLength = armScale;
    }
    
    public int getSpeed() {
        return this.attackSpeed;
    }
    
    public void setAngle(final int angle, final int height, final float distanceToBody) {
        this.angleOffset = angle;
        this.heightOffset = height;
        this.distanceToBody = distanceToBody;
    }
    
    public void swingArmTo(final double x, final double y, final double z) {
        this.attackTime = this.getSpeed();
        this.swingStart[0] = this.swingDest[0];
        this.swingStart[1] = this.swingDest[1];
        this.swingStart[2] = this.swingDest[2];
        this.swingDest[0] = x;
        this.swingDest[1] = y;
        this.swingDest[2] = z;
        this.isAttacking = true;
    }
    
    public void attackTarget(final Entity target) {
        final double posY = this.owner.posY + this.getShoulderHeight();
        final double targetY = target.posY + target.height / 2.0f;
        double dx = target.posX - this.owner.posX;
        double dy = targetY - posY;
        double dz = target.posZ - this.owner.posZ;
        Vec3 v = Vec3.createVectorHelper(dx, dy, dz);
        v = v.normalize();
        final double distToHead = this.owner.getDistance(target.posX, targetY - this.getShoulderHeight(), target.posZ);
        final double scale = Math.min(this.getArmLength(), distToHead);
        final float armSpeed = 5.0f;
        dx = v.xCoord * scale;
        dy = v.yCoord * scale;
        dz = v.zCoord * scale;
        this.owner.attackToXYZ(this.handFlag, dx, dy, dz);
    }
    
    public void doPunchDamage() {
        final double d = this.owner.size / 10.0f;
        final double posX = this.owner.posX + this.posX;
        final double posY = this.owner.posY + this.getShoulderHeight() + this.posY;
        final double posZ = this.owner.posZ + this.posZ;
        final List list = this.owner.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this.owner, AxisAlignedBB.getBoundingBox(posX - d, posY - d, posZ - d, posX + d, posY + d, posZ + d));
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity1 = list.get(j);
            if (entity1.canBeCollidedWith()) {
                if (!this.owner.isEntityEqual(entity1)) {
                    if (this.owner.attackEntityAsMob(entity1)) {
                        final Entity entity2 = entity1;
                        entity2.motionX += this.swingDest[0] / 4.0;
                        final Entity entity3 = entity1;
                        entity3.motionY += this.swingDest[1] / 4.0;
                        final Entity entity4 = entity1;
                        entity4.motionZ += this.swingDest[2] / 4.0;
                    }
                }
            }
        }
    }
    
    public void onUpdate() {
        this.moveHands(this.swingDest, this.swingStart, this.attackTime);
        boolean forceDefensive = false;
        if (this.attackTime > 0) {
            --this.attackTime;
            this.doPunchDamage();
            if (this.isAttacking) {
                if (this.attackTime == 0) {
                    final Vec3 v = this.getDefaultPosition();
                    final float scale = (float)(this.getArmLength() * this.distanceToBody);
                    this.swingArmTo(v.xCoord * scale, v.yCoord * scale, v.zCoord * scale);
                    this.isAttacking = false;
                }
            }
            else {
                forceDefensive = true;
            }
        }
        else {
            forceDefensive = true;
        }
        if (forceDefensive) {
            final Vec3 v = this.getDefaultPosition();
            final float scale = (float)(this.getArmLength() * this.distanceToBody);
            this.swingDest[0] = v.xCoord * scale;
            this.swingDest[1] = v.yCoord * scale;
            this.swingDest[2] = v.zCoord * scale;
        }
    }
    
    public void moveHands(final double[] dest, final double[] start, final int armSwing) {
        this.posX = this.owner.posX;
        this.posY = this.owner.posY + this.getShoulderHeight();
        this.posZ = this.owner.posZ;
        final int swing = this.getSpeed() - armSwing;
        final double dx = start[0] + (dest[0] - start[0]) / this.getSpeed() * swing;
        final double dy = start[1] + (dest[1] - start[1]) / this.getSpeed() * swing;
        final double dz = start[2] + (dest[2] - start[2]) / this.getSpeed() * swing;
        this.setPosition(dx, dy, dz);
    }
    
    public float getShoulderHeight() {
        return this.owner.size * this.shoulderHeight;
    }
    
    public double getArmLength() {
        return this.owner.size * this.armLength;
    }
    
    public void setPosition(final double x, final double y, final double z) {
        this.prevPosX = this.posX;
        this.posX = x;
        this.prevPosY = this.posY;
        this.posY = y;
        this.prevPosZ = this.posZ;
        this.posZ = z;
    }
    
    public boolean attackInProgress() {
        return this.attackTime > 0;
    }
    
    protected Vec3 getDefaultPosition() {
        final double posX = -Math.sin(Math.toRadians(this.owner.rotationYawHead + this.angleOffset));
        final double posY = this.heightOffset + MathHelper.cos(this.owner.ticksExisted / 20.0f) / 4.0f;
        final double posZ = Math.cos(Math.toRadians(this.owner.rotationYawHead + this.angleOffset));
        return Vec3.createVectorHelper(posX, posY, posZ).normalize();
    }
}
