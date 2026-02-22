package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.command.IEntitySelector;

class SelectorHurtAlly implements IEntitySelector
{
    EntityHumanBase taskOwner;
    
    public SelectorHurtAlly(final EntityHumanBase owner) {
        this.taskOwner = owner;
    }
    
    public boolean isEntityApplicable(final Entity parEntity) {
        if (!(parEntity instanceof EntityLivingBase) || parEntity == this.taskOwner) {
            return false;
        }
        final EntityLivingBase entity = (EntityLivingBase)parEntity;
        return this.taskOwner.isSuitableTargetAlly(entity) && entity.getHealth() < entity.getMaxHealth();
    }
}
