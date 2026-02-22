package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.ai.EntityAIBase;

public class AIHumanIdleSit extends EntityAIBase
{
    int iddleTime;
    EntityHumanBase owner;
    
    public AIHumanIdleSit(final EntityHumanBase owner) {
        this.iddleTime = 0;
        this.owner = owner;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        return this.owner.getAttackTarget() == null && this.owner.ridingEntity == null && (this.owner.getOwner() == null || (this.owner.getDistanceToEntity((Entity)this.owner.getOwner()) <= 15.0f && this.owner.getAttackTarget() == null)) && this.owner.getNavigator().getPath() == null && this.owner.getAttackTarget() == null;
    }
    
    public boolean continueExecuting() {
        return super.continueExecuting() && this.iddleTime > 0;
    }
    
    public void startExecuting() {
        boolean flag = false;
        if (this.owner.getOwner() instanceof EntityHumanBase && ((EntityHumanBase)this.owner.getOwner()).isSitting()) {
            flag = true;
        }
        if (this.owner.getRNG().nextInt(50) == 0 || flag) {
            this.iddleTime = 500 + this.owner.getRNG().nextInt(1000);
            this.owner.setSitting(true);
        }
    }
    
    public void resetTask() {
        this.owner.setSitting(false);
    }
    
    public void updateTask() {
        if (this.iddleTime > 0) {
            --this.iddleTime;
        }
    }
}
