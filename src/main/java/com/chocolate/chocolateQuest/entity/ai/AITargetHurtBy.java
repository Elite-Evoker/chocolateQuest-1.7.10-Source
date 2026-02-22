package com.chocolate.chocolateQuest.entity.ai;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;

public class AITargetHurtBy extends EntityAITarget
{
    boolean alertCompanions;
    
    public AITargetHurtBy(final EntityCreature par1EntityLiving, final boolean par2) {
        super(par1EntityLiving, false);
        this.alertCompanions = par2;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        return this.isSuitableTarget(this.taskOwner.getAITarget(), true);
    }
    
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        if (this.alertCompanions) {
            final double targetDistance = this.getTargetDistance();
            final List list = this.taskOwner.worldObj.getEntitiesWithinAABB((Class)this.taskOwner.getClass(), AxisAlignedBB.getBoundingBox(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0, this.taskOwner.posY + 1.0, this.taskOwner.posZ + 1.0).expand(targetDistance, 4.0, targetDistance));
            for (final Entity entity : list) {
                final EntityLiving entityliving = (EntityLiving)entity;
                if (this.taskOwner != entityliving && entityliving.getAttackTarget() == null) {
                    entityliving.setAttackTarget(this.taskOwner.getAITarget());
                }
            }
        }
        super.startExecuting();
    }
}
