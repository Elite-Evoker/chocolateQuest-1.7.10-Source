package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.world.EnumDifficulty;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class AIHumanAttackDefensive extends AIInteractBase
{
    private int attackCooldown;
    float moveSpeed;
    
    public AIHumanAttackDefensive(final EntityHumanBase par1EntityLiving, final float speed) {
        super(par1EntityLiving);
        this.moveSpeed = speed;
    }
    
    @Override
    public boolean shouldExecute() {
        return super.shouldExecute();
    }
    
    public void startExecuting() {
        super.startExecuting();
        this.attackCooldown = 0;
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
    }
    
    @Override
    public void updateTask() {
        final boolean canSeeTarget = this.owner.getEntitySenses().canSee((Entity)this.entityTarget);
        this.owner.getLookHelper().setLookPositionWithEntity((Entity)this.entityTarget, 30.0f, 30.0f);
        final double dist = this.owner.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ);
        boolean goToTarget = true;
        if (this.owner.getLeader() != null) {
            if (this.owner.getLeader().getDistanceToEntity((Entity)this.entityTarget) > this.owner.partyDistanceToLeader * this.owner.partyDistanceToLeader) {
                this.stayInFormation();
                goToTarget = false;
            }
        }
        else if ((this.owner.AIMode == EnumAiState.WARD.ordinal() || this.owner.AIMode == EnumAiState.PATH.ordinal()) && this.owner.hasHome() && !this.owner.isWithinHomeDistanceCurrentPosition()) {
            goToTarget = false;
            this.owner.setAttackTarget(null);
        }
        if (goToTarget) {
            final boolean havePath = this.owner.onGround;
            if (canSeeTarget && --this.attackCooldown <= 0) {
                if (this.owner.worldObj.difficultySetting.getDifficultyId() >= EnumDifficulty.HARD.getDifficultyId() && dist > 16.0) {
                    this.owner.startSprinting();
                }
                this.getNavigator().tryMoveToEntityLiving((Entity)this.entityTarget, (double)this.moveSpeed);
            }
        }
        if (canSeeTarget) {
            this.attackTarget(dist);
        }
    }
}
