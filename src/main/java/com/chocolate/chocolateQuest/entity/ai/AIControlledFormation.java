package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.world.World;

public class AIControlledFormation extends AIControlledBase
{
    protected World worldObj;
    protected int attackCooldown;
    double x;
    double y;
    double z;
    
    public AIControlledFormation(final EntityHumanBase par1EntityLiving) {
        super(par1EntityLiving);
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.worldObj = par1EntityLiving.worldObj;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.owner.getLeader() == null) {
            return false;
        }
        final EntityLivingBase leader = this.owner.getLeader();
        final Vec3 absPosition = leader.getLookVec();
        final float angle = this.owner.partyPositionAngle * 3.1416f / 180.0f;
        final double cos = MathHelper.cos(angle);
        final double sin = MathHelper.sin(angle);
        final int dist = this.owner.partyDistanceToLeader;
        this.x = leader.posX + (absPosition.xCoord * cos - absPosition.zCoord * sin) * dist;
        this.y = leader.posY;
        this.z = leader.posZ + (absPosition.zCoord * cos + absPosition.xCoord * sin) * dist;
        final Entity distCheckEntity = (Entity)((this.owner.ridingEntity != null) ? this.owner.ridingEntity : this.owner);
        return distCheckEntity.getDistanceSq(this.x, this.y, this.z) > 1.0;
    }
    
    public void startExecuting() {
        this.owner.getNavigator().clearPathEntity();
    }
    
    public void resetTask() {
        if (this.owner.getLeader() != null) {
            this.owner.rotationYaw = this.owner.getLeader().rotationYawHead;
            this.owner.rotationYawHead = this.owner.getLeader().rotationYawHead;
        }
        this.owner.getNavigator().clearPathEntity();
    }
    
    public void updateTask() {
        this.tryMoveToXYZ(this.x, this.y, this.z, 1.15f);
    }
    
    @Override
    public boolean stayInFormation() {
        return false;
    }
}
