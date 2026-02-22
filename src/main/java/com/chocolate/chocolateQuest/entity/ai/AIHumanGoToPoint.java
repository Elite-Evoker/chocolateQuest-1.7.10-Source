package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.utils.Vec4I;

public class AIHumanGoToPoint extends AIControlledBase
{
    public Vec4I currentPos;
    
    public AIHumanGoToPoint(final EntityHumanBase owner) {
        super(owner);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.owner.currentPos != null) {}
        this.currentPos = this.owner.currentPos;
        return this.currentPos != null;
    }
    
    public boolean continueExecuting() {
        return this.owner.currentPos == this.currentPos;
    }
    
    public void resetTask() {
        this.currentPos = null;
        super.resetTask();
    }
    
    public void updateTask() {
        final Vec4I p = this.currentPos;
        double dist = 0.0;
        float width = 0.0f;
        if (this.owner.ridingEntity == null) {
            dist = this.owner.getDistanceSq((double)p.xCoord, (double)p.yCoord, (double)p.zCoord);
            width = this.owner.width + 1.0f;
        }
        else {
            dist = this.owner.ridingEntity.getDistanceSq((double)p.xCoord, (double)p.yCoord, (double)p.zCoord);
            width = this.owner.ridingEntity.width;
        }
        width *= width;
        if (dist > width + 1.0f) {
            this.tryMoveToXYZ(p.xCoord, p.yCoord, p.zCoord, 1.0f);
        }
        else if (this.owner.currentPos == this.currentPos) {
            this.owner.currentPos = null;
        }
        super.updateTask();
    }
}
