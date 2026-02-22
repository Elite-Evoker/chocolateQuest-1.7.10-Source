package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.command.IEntitySelector;

public class HumanSelector implements IEntitySelector
{
    EntityHumanBase taskOwner;
    
    public HumanSelector(final EntityHumanBase owner) {
        this.taskOwner = owner;
    }
    
    public boolean isEntityApplicable(final Entity parEntity) {
        if (!(parEntity instanceof EntityLivingBase)) {
            return false;
        }
        final EntityLivingBase entity = (EntityLivingBase)parEntity;
        return !this.taskOwner.isSuitableTargetAlly(entity) && this.taskOwner.canSee(entity);
    }
}
