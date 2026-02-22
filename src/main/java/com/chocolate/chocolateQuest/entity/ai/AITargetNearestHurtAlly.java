package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class AITargetNearestHurtAlly extends EntityAINearestAttackableTarget
{
    public AITargetNearestHurtAlly(final EntityHumanBase owner, final Class targetClass) {
        super((EntityCreature)owner, targetClass, 0, true, false, (IEntitySelector)new SelectorHurtAlly(owner));
    }
    
    public boolean isSuitableTarget(final EntityLivingBase par1EntityLivingBase, final boolean par2) {
        return true;
    }
}
