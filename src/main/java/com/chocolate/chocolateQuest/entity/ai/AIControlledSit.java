package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class AIControlledSit extends AIControlledBase
{
    public AIControlledSit(final EntityHumanBase owner) {
        super(owner);
    }
    
    @Override
    public boolean shouldExecute() {
        return true;
    }
    
    public void updateTask() {
        if (!this.owner.isSitting()) {
            this.owner.setSitting(true);
        }
        this.owner.getNavigator().clearPathEntity();
    }
    
    public void resetTask() {
        this.owner.setSitting(false);
        super.resetTask();
    }
    
    public void startExecuting() {
        this.owner.setSitting(true);
        super.startExecuting();
    }
}
