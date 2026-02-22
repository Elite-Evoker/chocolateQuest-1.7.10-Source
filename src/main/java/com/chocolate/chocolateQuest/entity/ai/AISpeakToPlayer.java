package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.ai.EntityAIBase;

public class AISpeakToPlayer extends EntityAIBase
{
    EntityHumanBase human;
    Entity playerSpeakingTo;
    
    public AISpeakToPlayer(final EntityHumanBase human) {
        this.human = human;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        return this.human.playerSpeakingTo != null;
    }
    
    public void startExecuting() {
        this.playerSpeakingTo = (Entity)this.human.playerSpeakingTo;
        this.human.getNavigator().clearPathEntity();
        if (this.human.isSitting()) {
            this.human.setSitting(false);
        }
    }
    
    public boolean continueExecuting() {
        if (this.human.playerSpeakingTo == null) {
            return false;
        }
        if (this.human.playerSpeakingTo != this.playerSpeakingTo) {
            return false;
        }
        if (this.human.getDistanceSqToEntity(this.playerSpeakingTo) > 36.0) {
            this.human.playerSpeakingTo = null;
            return false;
        }
        return true;
    }
    
    public void resetTask() {
    }
    
    public void updateTask() {
        this.human.getLookHelper().setLookPosition(this.playerSpeakingTo.posX, this.playerSpeakingTo.posY + this.playerSpeakingTo.getEyeHeight(), this.playerSpeakingTo.posZ, 10.0f, (float)this.human.getVerticalFaceSpeed());
    }
}
