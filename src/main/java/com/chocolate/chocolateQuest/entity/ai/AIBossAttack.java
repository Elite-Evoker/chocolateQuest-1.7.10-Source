package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class AIBossAttack extends EntityAIBase
{
    World worldObj;
    EntityBaseBoss attacker;
    EntityLivingBase entityTarget;
    float moveSpeed;
    boolean needsVision;
    PathEntity pathEntity;
    private int attackTimer;
    
    public AIBossAttack(final EntityBaseBoss par1EntityLiving, final float speed, final boolean par3) {
        this.attacker = par1EntityLiving;
        this.worldObj = par1EntityLiving.worldObj;
        this.moveSpeed = speed;
        this.needsVision = par3;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        final EntityLivingBase entityliving = this.attacker.getAttackTarget();
        if (entityliving == null) {
            return false;
        }
        this.entityTarget = entityliving;
        return true;
    }
    
    public boolean continueExecuting() {
        final EntityLivingBase entityliving = this.attacker.getAttackTarget();
        return entityliving != null && (entityliving.isEntityEqual((Entity)this.entityTarget) || this.attacker.getDistanceSqToEntity((Entity)entityliving) >= this.attacker.getDistanceSqToEntity((Entity)this.entityTarget)) && this.entityTarget.isEntityAlive() && this.attacker.isWithinHomeDistance(MathHelper.floor_double(this.entityTarget.posX), MathHelper.floor_double(this.entityTarget.posY), MathHelper.floor_double(this.entityTarget.posZ));
    }
    
    public void startExecuting() {
        if (!this.attacker.isAttacking()) {
            this.attacker.getNavigator().setPath(this.pathEntity, (double)this.moveSpeed);
            this.attackTimer = 0;
        }
    }
    
    public void resetTask() {
        this.entityTarget = null;
        this.attacker.getNavigator().clearPathEntity();
    }
    
    public void updateTask() {
        if (!this.attacker.attackInProgress()) {
            this.attacker.getLookHelper().setLookPositionWithEntity((Entity)this.entityTarget, 30.0f, 30.0f);
        }
        final int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities((Entity)this.attacker, (Entity)this.entityTarget) - this.attacker.rotationYaw);
        final double d1 = this.attacker.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ);
        if (this.attacker.shouldMoveToEntity(d1, (Entity)this.entityTarget)) {
            float speed = this.moveSpeed;
            if (angle != 0) {
                speed = 0.0f;
            }
            this.attacker.getMoveHelper().setMoveTo(this.entityTarget.posX, this.entityTarget.posY, this.entityTarget.posZ, (double)speed);
            if (this.attacker.isCollidedHorizontally && this.attacker.onGround) {
                this.attacker.getJumpHelper().setJumping();
                this.attacker.motionY = this.attacker.getScaleSize() / 20.0f;
            }
        }
        else {
            this.attacker.getNavigator().clearPathEntity();
        }
        this.attacker.attackEntity((Entity)this.entityTarget, (float)d1);
    }
}
