package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.command.IEntitySelector;

class SelectorLiving implements IEntitySelector
{
    EntityCreature taskOwner;
    AITargetNearestAttackableForDragon ownerAI;
    IEntitySelector selector;
    
    public SelectorLiving(final EntityCreature par1EntityCreature, final AITargetNearestAttackableForDragon par2, final IEntitySelector parSelector) {
        this.taskOwner = par1EntityCreature;
        this.ownerAI = par2;
        this.selector = parSelector;
    }
    
    public boolean isEntityApplicable(final Entity entity) {
        boolean flag = true;
        if (this.selector != null) {
            flag = this.selector.isEntityApplicable(entity);
        }
        return entity instanceof EntityLivingBase && flag && this.ownerAI.isSuitableTarget((EntityLivingBase)entity, false);
    }
}
