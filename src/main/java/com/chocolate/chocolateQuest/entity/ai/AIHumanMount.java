package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class AIHumanMount extends AIInteractBase
{
    boolean requireSight;
    
    public AIHumanMount(final EntityHumanBase par1EntityLiving, final float moveSpeed, final boolean par3) {
        super(par1EntityLiving);
        this.setMutexBits(3);
        this.requireSight = par3;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.owner.entityToMount != null) {
            this.entityTarget = this.owner.entityToMount;
            return true;
        }
        final EntityLivingBase var1 = this.owner.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        final boolean suitableMount = this.owner.isSuitableMount((Entity)var1);
        if (!this.owner.isSuitableMount((Entity)var1) || this.owner.ridingEntity != null || var1.riddenByEntity != null) {
            if (suitableMount) {
                this.owner.setAttackTarget(null);
            }
            return false;
        }
        this.entityTarget = var1;
        return true;
    }
    
    public boolean continueExecuting() {
        return this.owner.ridingEntity == null && this.entityTarget.riddenByEntity == null && this.entityTarget != null && this.entityTarget.isEntityAlive() && (this.requireSight || !this.owner.getNavigator().noPath());
    }
    
    @Override
    public void resetTask() {
        this.owner.setAttackTarget(null);
        this.entityTarget = null;
        this.owner.getNavigator().clearPathEntity();
        this.owner.entityToMount = null;
    }
    
    @Override
    public void updateTask() {
        this.owner.getLookHelper().setLookPositionWithEntity((Entity)this.entityTarget, 30.0f, 30.0f);
        if (this.requireSight || this.owner.getEntitySenses().canSee((Entity)this.entityTarget)) {
            this.owner.getNavigator().tryMoveToEntityLiving((Entity)this.entityTarget, 1.0);
        }
        this.owner.attackTime = Math.max(this.owner.attackTime - 1, 0);
        final double bounds = this.owner.width * 2.0f * this.entityTarget.width * 2.0f;
        final double dist = this.owner.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ);
        if (dist <= bounds) {
            this.owner.mountEntity((Entity)this.entityTarget);
            this.owner.setMountAI();
            this.owner.setAttackTarget(null);
        }
    }
}
