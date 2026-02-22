package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.utils.Vec3I;

public class AIHumanReturnHome extends AIControlledBase
{
    Vec3I position;
    
    public AIHumanReturnHome(final EntityHumanBase owner) {
        super(owner);
    }
    
    @Override
    public boolean shouldExecute() {
        return (this.owner.getAttackTarget() == null || !this.owner.getAttackTarget().isEntityAlive()) && this.owner.getOwner() == null && !this.owner.isWithinHomeDistanceCurrentPosition();
    }
    
    public void startExecuting() {
    }
    
    public void resetTask() {
        this.owner.getNavigator().clearPathEntity();
    }
    
    public void updateTask() {
        this.tryMoveToXYZ(this.owner.getHomePosition().posX, this.owner.getHomePosition().posY, this.owner.getHomePosition().posZ, 1.0f);
    }
}
