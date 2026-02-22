package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITarget;

public class AITargetOwner extends EntityAITarget
{
    EntityLiving theEntity;
    EntityLivingBase theTarget;
    
    public AITargetOwner(final EntityCreature par1EntityTameable) {
        super(par1EntityTameable, false);
        this.theEntity = (EntityLiving)par1EntityTameable;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        this.theTarget = this.getTarget();
        return this.theTarget != null && this.theTarget != this.theEntity.ridingEntity && this.isSuitableTarget(this.theTarget, false);
    }
    
    public EntityLivingBase getTarget() {
        final EntityLivingBase entityliving = (EntityLivingBase)((IEntityOwnable)this.theEntity).getOwner();
        if (entityliving == null) {
            return null;
        }
        final boolean flag = false;
        if (entityliving.getAITarget() != null) {
            return entityliving.getAITarget();
        }
        if (entityliving.getLastAttacker() != null) {
            return entityliving.getLastAttacker();
        }
        return null;
    }
    
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theTarget);
        super.startExecuting();
    }
}
