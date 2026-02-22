package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.ai.EntityAITarget;

public class AITargetParty extends EntityAITarget
{
    EntityHumanBase owner;
    EntityLivingBase theTarget;
    
    public AITargetParty(final EntityHumanBase human) {
        super((EntityCreature)human, false);
        this.owner = human;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        final EntityLivingBase var1 = this.getTarget();
        if (this.owner.isSuitableMount((Entity)var1)) {
            return false;
        }
        this.theTarget = var1;
        return this.theTarget != null;
    }
    
    public EntityLivingBase getTarget() {
        if (this.owner.party != null) {
            return this.owner.party.getTarget();
        }
        return null;
    }
    
    public void startExecuting() {
        this.owner.setAttackTarget(this.theTarget);
        super.startExecuting();
    }
}
