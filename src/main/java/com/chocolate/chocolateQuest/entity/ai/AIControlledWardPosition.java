package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.utils.Vec4I;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class AIControlledWardPosition extends AIControlledBase
{
    public AIControlledWardPosition(final EntityHumanBase owner) {
        super(owner);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.owner.standingPosition == null) {
            return false;
        }
        final Vec4I p = this.owner.standingPosition;
        return this.owner.getDistanceSq(p.xCoord + 0.5, p.yCoord + 0.5, p.zCoord + 0.5) > this.owner.width || this.owner.rotationYaw != p.rot || this.owner.getRNG().nextInt(50) == 0;
    }
    
    public void resetTask() {
        this.owner.getNavigator().clearPathEntity();
        this.owner.rotationYaw = (float)this.owner.standingPosition.rot;
        this.owner.rotationYawHead = (float)this.owner.standingPosition.rot;
    }
    
    public void updateTask() {
        final Vec4I p = this.owner.standingPosition;
        final Entity distCheckEntity = (Entity)((this.owner.ridingEntity != null) ? this.owner.ridingEntity : this.owner);
        final double dist = distCheckEntity.getDistanceSq(p.xCoord + 0.5, (double)p.yCoord, p.zCoord + 0.5);
        if (dist > this.owner.width + 1.0f) {
            this.tryMoveToXYZ(p.xCoord + 0.5, p.yCoord + 0.5, p.zCoord + 0.5, 1.0f);
        }
        else {
            this.owner.getNavigator().clearPathEntity();
            this.owner.rotationYaw = (float)p.rot;
            this.owner.rotationYawHead = (float)p.rot;
        }
        super.updateTask();
    }
}
