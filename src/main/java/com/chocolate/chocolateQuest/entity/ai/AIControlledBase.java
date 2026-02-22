package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.Vec4I;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.ai.EntityAIBase;

public class AIControlledBase extends EntityAIBase
{
    protected EntityHumanBase owner;
    private Vec4I position;
    private int pathFindingCooldown;
    public int pathBlockedTime;
    
    public AIControlledBase(final EntityHumanBase owner) {
        this.pathBlockedTime = 0;
        this.owner = owner;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        return true;
    }
    
    public boolean stayInFormation() {
        if (this.owner.getLeader() != null) {
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
            this.tryMoveToXYZ(x, y, z, 1.15f);
            return true;
        }
        return false;
    }
    
    public boolean tryMoveToXYZ(final double x, final double y, final double z, float moveSpeed) {
        if (this.owner.ridingEntity != null) {
            ++moveSpeed;
        }
        --this.pathFindingCooldown;
        if (this.pathFindingCooldown > 0 && !this.getNavigator().noPath()) {
            return true;
        }
        this.pathFindingCooldown = 5 + this.owner.getRNG().nextInt(10);
        if (this.getNavigator().tryMoveToXYZ(x, y, z, (double)moveSpeed) && !this.getNavigator().noPath()) {
            this.position = null;
            return true;
        }
        if (this.position == null) {
            Vec3 vec3 = Vec3.createVectorHelper(x - this.owner.posX, y - this.owner.posY, z - this.owner.posZ);
            vec3 = vec3.normalize();
            vec3 = Vec3.createVectorHelper(vec3.xCoord * 10.0 + this.owner.posX, vec3.yCoord * 10.0 + this.owner.posY, vec3.zCoord * 10.0 + this.owner.posZ);
            if (this.owner.worldObj.isAirBlock(MathHelper.floor_double(vec3.xCoord), MathHelper.floor_double(vec3.yCoord - 1.0), MathHelper.floor_double(vec3.zCoord))) {
                final Vec3 direction = Vec3.createVectorHelper(x, y, z);
                vec3 = RandomPositionGenerator.findRandomTargetBlockTowards((EntityCreature)this.owner, 10, 3, direction);
                if (vec3 == null) {
                    return false;
                }
            }
            this.position = new Vec4I(MathHelper.floor_double(vec3.xCoord), MathHelper.floor_double(vec3.yCoord), MathHelper.floor_double(vec3.zCoord), 0);
            return true;
        }
        if (this.getNavigator().tryMoveToXYZ((double)this.position.xCoord, (double)this.position.yCoord, (double)this.position.zCoord, (double)moveSpeed)) {
            final PathPoint path = this.getNavigator().getPath().getFinalPathPoint();
            if (this.owner.getDistanceSq((double)path.xCoord, (double)path.yCoord, (double)path.zCoord) <= 1.0) {
                this.pathFindingCooldown = 10;
                this.position = null;
            }
            return true;
        }
        if (this.owner.canTeleport() && (this.owner.onGround || this.owner.isInWater())) {
            this.owner.setPosition((double)this.position.xCoord, (double)(this.position.yCoord + 1), (double)this.position.zCoord);
        }
        this.position = null;
        return false;
    }
    
    public PathNavigate getNavigator() {
        if (this.owner.ridingEntity != null && this.owner.ridingEntity instanceof EntityLiving && ((EntityLiving)this.owner.ridingEntity).getNavigator() != null) {
            return ((EntityLiving)this.owner.ridingEntity).getNavigator();
        }
        return this.owner.getNavigator();
    }
}
