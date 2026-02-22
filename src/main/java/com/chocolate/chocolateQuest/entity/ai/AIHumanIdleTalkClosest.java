package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.ai.EntityAIWatchClosest;

public class AIHumanIdleTalkClosest extends EntityAIWatchClosest
{
    EntityHumanBase owner;
    
    public AIHumanIdleTalkClosest(final EntityHumanBase par1EntityLiving, final Class par2Class, final float par3) {
        super((EntityLiving)par1EntityLiving, par2Class, par3);
        this.owner = par1EntityLiving;
        this.setMutexBits(2);
    }
    
    public boolean shouldExecute() {
        final boolean flag = super.shouldExecute();
        return this.owner.getNavigator().getPath() == null && this.owner.getAttackTarget() == null && flag && this.owner.AIMode != EnumAiState.WARD.ordinal() && (this.owner.isSuitableTargetAlly((EntityLivingBase)this.closestEntity) && this.owner.getDistanceToEntity(this.closestEntity) < 5.0f);
    }
    
    public void startExecuting() {
        super.startExecuting();
        this.owner.setSpeaking(true);
        this.handShake(60);
    }
    
    public void handShake(final int chance) {
        if (this.closestEntity == null) {
            return;
        }
        final int rnd = this.owner.getRNG().nextInt(chance);
        if (rnd == 0) {
            this.owner.swingItem();
        }
        else if (rnd == 1) {
            this.owner.swingItem();
        }
        else if (rnd > chance - 10 && this.owner.getDistanceToEntity(this.closestEntity) < 3.0f) {
            this.owner.swingItem();
            ((EntityLivingBase)this.closestEntity).swingItem();
        }
    }
    
    public void resetTask() {
        super.resetTask();
        this.owner.setSpeaking(false);
        this.handShake(40);
    }
}
