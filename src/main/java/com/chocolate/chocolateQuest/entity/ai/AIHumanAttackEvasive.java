package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.world.World;

public class AIHumanAttackEvasive extends AIInteractBase
{
    World worldObj;
    
    public AIHumanAttackEvasive(final EntityHumanBase par1EntityLiving, final float par2) {
        super(par1EntityLiving);
        this.owner = par1EntityLiving;
        this.worldObj = par1EntityLiving.worldObj;
    }
    
    @Override
    public boolean shouldExecute() {
        if (super.shouldExecute()) {
            final EntityLivingBase entityliving = this.owner.getAttackTarget();
            if (this.owner.party != null && this.owner.party.getLeader() != this.owner) {
                final double distToleader = this.owner.getDistanceSqToEntity((Entity)this.owner.party.getLeader());
                if (distToleader > this.owner.partyDistanceToLeader * this.owner.partyDistanceToLeader * Math.max(1, 16 - this.owner.partyDistanceToLeader)) {
                    return false;
                }
            }
            this.entityTarget = entityliving;
            return true;
        }
        return false;
    }
    
    public boolean continueExecuting() {
        return this.shouldExecute() && super.continueExecuting();
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.owner.getNavigator().clearPathEntity();
        this.owner.moveForwardHuman = 0.0f;
    }
    
    @Override
    public void updateTask() {
        final double distance = this.owner.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ);
        final boolean canSee = this.owner.getEntitySenses().canSee((Entity)this.entityTarget);
        this.owner.getLookHelper().setLookPositionWithEntity((Entity)this.entityTarget, 0.0f, 0.0f);
        this.owner.rotationYaw = this.owner.rotationYawHead;
        this.owner.moveForwardHuman = -1.0E-4f;
        boolean stayInFormation = false;
        if (canSee && distance < 64.0) {
            final double ry = Math.toRadians(this.owner.rotationYaw - 180.0f);
            final int x = MathHelper.floor_double(this.owner.posX - Math.sin(ry) * 6.0);
            final int z = MathHelper.floor_double(this.owner.posZ + Math.cos(ry) * 6.0);
            Material mat = this.owner.worldObj.getBlock(x, MathHelper.floor_double(this.owner.posY) - 1, z).getMaterial();
            boolean move = false;
            if (mat != Material.air && mat != Material.lava && mat.isSolid()) {
                move = true;
            }
            else {
                mat = this.owner.worldObj.getBlock(x, MathHelper.floor_double(this.owner.posY) - 2, z).getMaterial();
                if (mat.isSolid()) {
                    move = true;
                }
            }
            if (move) {
                this.owner.moveForwardHuman = -0.1f;
            }
        }
        else if (this.owner.party != null) {
            this.stayInFormation();
            stayInFormation = true;
        }
        if (!this.attackTarget(distance)) {
            if (!stayInFormation) {
                this.tryMoveToXYZ(this.entityTarget.posX, this.entityTarget.posY, this.entityTarget.posZ, 1.0f);
            }
            else if (!stayInFormation) {
                this.getNavigator().clearPathEntity();
            }
        }
    }
}
