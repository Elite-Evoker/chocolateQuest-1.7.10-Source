package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class AIHumanAttackAggressive extends AIInteractBase
{
    protected World worldObj;
    protected int attackTick;
    protected float moveSpeed;
    protected boolean requireSight;
    protected boolean isPlayerTarget;
    PathEntity entityPathEntity;
    Class classTarget;
    private int pathFindingCooldown;
    
    public AIHumanAttackAggressive(final EntityHumanBase par1EntityLiving, final Class par2Class, final float speed, final boolean requiresSight) {
        this(par1EntityLiving, speed, requiresSight);
        this.classTarget = par2Class;
    }
    
    public AIHumanAttackAggressive(final EntityHumanBase par1EntityLiving, final float speed, final boolean requireSight) {
        super(par1EntityLiving);
        this.isPlayerTarget = false;
        this.owner = par1EntityLiving;
        this.worldObj = par1EntityLiving.worldObj;
        this.moveSpeed = speed;
        this.requireSight = requireSight;
    }
    
    @Override
    public boolean shouldExecute() {
        if (super.shouldExecute()) {
            final EntityLivingBase var1 = this.owner.getAttackTarget();
            this.entityTarget = var1;
            return true;
        }
        return false;
    }
    
    public boolean continueExecuting() {
        final EntityLivingBase var1 = this.owner.getAttackTarget();
        return var1 != null && var1 == this.entityTarget && this.entityTarget.isEntityAlive();
    }
    
    public void startExecuting() {
        if (this.entityTarget instanceof EntityPlayer) {
            this.isPlayerTarget = true;
        }
        this.pathFindingCooldown = 0;
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.owner.setDefending(false);
        this.entityPathEntity = null;
    }
    
    @Override
    public void updateTask() {
        final double dist = this.owner.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ);
        this.owner.getLookHelper().setLookPositionWithEntity((Entity)this.entityTarget, 30.0f, 30.0f);
        final boolean canSeeTarget = this.owner.getEntitySenses().canSee((Entity)this.entityTarget);
        boolean shouldStayAway = false;
        final boolean havePath = this.owner.onGround;
        if (dist > this.owner.getDistanceToAttack() + this.getMinDistanceToInteract() || !canSeeTarget) {
            if (--this.pathFindingCooldown <= 0) {
                final int inteligenceLevel = this.owner.getInteligence();
                this.pathFindingCooldown = inteligenceLevel + this.owner.getRNG().nextInt(inteligenceLevel + 2);
                if (this.owner.canSprint() && dist > 16.0) {
                    this.owner.startSprinting();
                }
                if (!this.tryToMoveToEntity()) {
                    shouldStayAway = true;
                }
                if (shouldStayAway) {}
            }
        }
        else {
            this.getNavigator().clearPathEntity();
        }
        this.attackTarget(dist);
    }
    
    public boolean tryToMoveToEntity() {
        return this.tryMoveToXYZ(this.entityTarget.posX, this.entityTarget.posY, this.entityTarget.posZ, 1.0f);
    }
}
