package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class AIHumanFlee extends AIInteractBase
{
    private int attackCooldown;
    float moveSpeed;
    int x;
    int y;
    int z;
    int pathCD;
    
    public AIHumanFlee(final EntityHumanBase par1EntityLiving, final float speed) {
        super(par1EntityLiving);
        this.pathCD = 0;
        this.moveSpeed = speed;
    }
    
    @Override
    public boolean shouldExecute() {
        return super.shouldExecute();
    }
    
    public void startExecuting() {
        super.startExecuting();
        this.attackCooldown = 0;
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
    }
    
    @Override
    public void updateTask() {
        final double dist = this.owner.getDistanceSqToEntity((Entity)this.entityTarget);
        if (this.pathCD > 0) {
            --this.pathCD;
        }
        if (dist < 128.0) {
            if (this.pathCD == 0) {
                final Vec3 direction = Vec3.createVectorHelper(this.owner.posX + (this.owner.posX - this.entityTarget.posX), this.owner.posY + (this.owner.posY - this.entityTarget.posY), this.owner.posZ + (this.owner.posZ - this.entityTarget.posZ));
                final Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards((EntityCreature)this.owner, 10, 10, direction);
                if (vec3 != null) {
                    this.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 1.2);
                    this.pathCD = 10;
                }
            }
        }
        else {
            this.stayInFormation();
        }
    }
}
