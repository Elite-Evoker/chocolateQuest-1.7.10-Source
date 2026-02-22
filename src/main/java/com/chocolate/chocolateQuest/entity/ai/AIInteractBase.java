package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.Entity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityLivingBase;

public class AIInteractBase extends AIControlledBase
{
    protected EntityLivingBase entityTarget;
    
    public AIInteractBase(final EntityHumanBase par1EntityLiving) {
        super(par1EntityLiving);
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase entityliving = this.owner.getAttackTarget();
        if (entityliving == null) {
            return false;
        }
        if (!entityliving.isEntityAlive()) {
            this.owner.setAttackTarget(null);
            return false;
        }
        if (this.owner.isSuitableTargetAlly(entityliving)) {
            return false;
        }
        this.entityTarget = entityliving;
        return true;
    }
    
    public void resetTask() {
        this.entityTarget = null;
    }
    
    public void updateTask() {
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        final EntityLivingBase leader = this.owner.getLeader();
        final Vec3 absPosition = leader.getLookVec();
        final float angle = this.owner.partyPositionAngle * 3.1416f / 180.0f;
        final double cos = MathHelper.cos(angle);
        final double sin = MathHelper.sin(angle);
        final int dist = this.owner.partyDistanceToLeader;
        x = leader.posX + (absPosition.xCoord * cos - absPosition.zCoord * sin) * dist;
        y = leader.posY;
        z = leader.posZ + (absPosition.zCoord * cos + absPosition.xCoord * sin) * dist;
        this.tryMoveToXYZ(x, y, z, 1.2f);
    }
    
    public boolean attackTarget(final double distance) {
        this.owner.attackTime = Math.max(this.owner.attackTime - 1, 0);
        final double sumLengthBB = this.getMinDistanceToInteract() + this.owner.getAttackRangeBonus();
        if (this.owner.haveShied()) {
            double distToStopDefending = sumLengthBB;
            if (this.entityTarget instanceof EntityPlayer) {
                distToStopDefending *= 2.0;
            }
            else {
                distToStopDefending = 0.0;
            }
            if (this.owner.isDefending() && distance <= distToStopDefending && this.owner.attackTime <= 10) {
                this.owner.setDefending(false);
            }
        }
        if (distance <= sumLengthBB) {
            this.owner.attackEntity(this.entityTarget);
        }
        else if (this.owner.isRanged()) {
            final EntityLivingBase target = this.getFrontTarget();
            if (target != null && this.owner.isSuitableTargetAlly(target) && target != this.owner.ridingEntity) {
                final float moveStrafing = -MathHelper.sin((float)this.owner.partyPositionAngle) / 4.0f;
                final double ry = -Math.toRadians((this.owner.rotationYaw + this.owner.moveStrafing > 0.0f) ? 90.0 : -90.0);
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
                    this.owner.moveStrafing = moveStrafing;
                }
                else {
                    this.owner.moveStrafing = 0.0f;
                }
            }
            else {
                this.owner.moveStrafing = 0.0f;
                this.owner.getLookHelper().setLookPositionWithEntity((Entity)this.entityTarget, 30.0f, 30.0f);
                if (!this.owner.attackEntityWithRangedAttack((Entity)this.entityTarget, (float)distance)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public double getMinDistanceToInteract() {
        double attackerBB = this.owner.width * 2.2f;
        if (this.owner.ridingEntity != null) {
            attackerBB += this.owner.ridingEntity.width * 2.0f;
        }
        double targetBB = this.entityTarget.width * 2.2f;
        if (this.entityTarget.ridingEntity != null) {
            targetBB += this.entityTarget.ridingEntity.width;
        }
        return attackerBB * targetBB + this.owner.rightHand.getAttackRangeBonus();
    }
    
    public EntityLivingBase getFrontTarget() {
        EntityLivingBase target = null;
        final double arrowMotionX = this.entityTarget.posX - this.owner.posX;
        final double arrowMotionZ = this.entityTarget.posZ - this.owner.posZ;
        final float tRot = this.owner.rotationYaw;
        this.owner.rotationYaw = (float)(Math.atan2(arrowMotionZ, arrowMotionX) * 180.0 / 3.141592653589793) - 90.0f;
        final MovingObjectPosition mop = null;
        double dist = 30.0;
        final float yOffset = 0.0f;
        final Vec3 playerPos = Vec3.createVectorHelper(this.owner.posX, this.owner.posY - yOffset, this.owner.posZ);
        final Vec3 look = this.owner.getLookVec();
        final Vec3 playerView = playerPos.addVector(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
        final List list = this.owner.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this.owner, this.owner.boundingBox.addCoord(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist).expand(1.0, 1.0, 1.0));
        dist *= dist;
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity1 = list.get(j);
            if (entity1 instanceof EntityLivingBase) {
                if (entity1.canBeCollidedWith()) {
                    final float f2 = 0.4f;
                    final AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)entity1.width, (double)entity1.height, (double)entity1.width);
                    final MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(playerPos, playerView);
                    if (movingobjectposition1 != null) {
                        final double tDist = this.owner.getDistanceSqToEntity(entity1);
                        if (tDist < dist) {
                            dist = tDist;
                            target = (EntityLivingBase)entity1;
                        }
                    }
                }
            }
        }
        return target;
    }
}
