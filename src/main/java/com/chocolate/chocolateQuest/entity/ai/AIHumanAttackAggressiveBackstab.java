package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class AIHumanAttackAggressiveBackstab extends AIHumanAttackAggressive
{
    public AIHumanAttackAggressiveBackstab(final EntityHumanBase par1EntityLiving, final float speed, final boolean requireSight) {
        super(par1EntityLiving, speed, requireSight);
    }
    
    @Override
    public boolean tryToMoveToEntity() {
        final float targetAngle = (this.entityTarget.rotationYawHead - 180.0f) * 3.1416f / 180.0f;
        final double cos = MathHelper.cos(targetAngle);
        final double sin = MathHelper.sin(targetAngle);
        float angle;
        for (angle = this.owner.rotationYawHead - this.entityTarget.rotationYawHead; angle > 360.0f; angle -= 360.0f) {}
        while (angle < 0.0f) {
            angle += 360.0f;
        }
        angle = 180.0f - Math.abs(angle - 180.0f);
        final double dist = Math.min(2.5, Math.abs(angle) / 60.0f);
        final double x = this.entityTarget.posX + -sin * dist;
        final double y = this.entityTarget.posY;
        final double z = this.entityTarget.posZ + cos * dist;
        return this.tryMoveToXYZ(x, y, z, 1.0f);
    }
}
