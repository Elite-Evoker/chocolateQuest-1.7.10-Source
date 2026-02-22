package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.world.IBlockAccess;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class AIFollowOwner extends EntityAIBase
{
    protected EntityLiving theEntity;
    protected EntityLivingBase target;
    World theWorld;
    private float moveSpeed;
    private PathNavigate ownerPathfinder;
    private int field_75343_h;
    float maxDist;
    float minDist;
    private boolean avoidsWater;
    private boolean teleports;
    
    public AIFollowOwner(final EntityLiving par1EntityTameable, final float speed, final float minDist, final float maxDist, final boolean teleport) {
        this.teleports = false;
        this.theEntity = par1EntityTameable;
        this.theWorld = par1EntityTameable.worldObj;
        this.moveSpeed = speed;
        this.ownerPathfinder = par1EntityTameable.getNavigator();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.setMutexBits(4);
        this.teleports = teleport;
    }
    
    public boolean shouldExecute() {
        final EntityLivingBase entityliving = this.getOwner();
        if (entityliving == null) {
            return false;
        }
        final double dist = this.theEntity.getDistanceSqToEntity((Entity)entityliving);
        final double minDist = this.minDist * this.minDist;
        Label_0065: {
            if (this.theEntity.getAttackTarget() == null) {
                if (dist >= minDist) {
                    break Label_0065;
                }
            }
            else if (dist >= minDist * 3.0) {
                break Label_0065;
            }
            return false;
        }
        this.target = entityliving;
        return true;
    }
    
    public EntityLivingBase getOwner() {
        return (EntityLivingBase)((IEntityOwnable)this.theEntity).getOwner();
    }
    
    public boolean continueExecuting() {
        return !this.ownerPathfinder.noPath() && this.theEntity.getDistanceSqToEntity((Entity)this.target) > this.maxDist * this.maxDist;
    }
    
    public void startExecuting() {
        this.field_75343_h = 0;
        this.avoidsWater = this.theEntity.getNavigator().getAvoidsWater();
        this.theEntity.getNavigator().setAvoidsWater(false);
    }
    
    public void resetTask() {
        this.target = null;
        this.ownerPathfinder.clearPathEntity();
        this.theEntity.getNavigator().setAvoidsWater(this.avoidsWater);
    }
    
    public void updateTask() {
        this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)this.target, 10.0f, (float)this.theEntity.getVerticalFaceSpeed());
        final int field_75343_h = this.field_75343_h - 1;
        this.field_75343_h = field_75343_h;
        if (field_75343_h <= 0) {
            this.field_75343_h = 10;
            if (this.theEntity.ridingEntity instanceof EntityLiving) {
                ((EntityLiving)this.theEntity.ridingEntity).getNavigator().tryMoveToEntityLiving((Entity)this.target, (double)this.moveSpeed);
            }
            else if (!this.ownerPathfinder.tryMoveToEntityLiving((Entity)this.target, 2.0) && this.theEntity.getDistanceSqToEntity((Entity)this.target) >= 288.0 && this.teleports) {
                final int i = MathHelper.floor_double(this.target.posX) - 2;
                final int j = MathHelper.floor_double(this.target.posZ) - 2;
                final int k = MathHelper.floor_double(this.target.boundingBox.minY);
                for (int l = 0; l <= 4; ++l) {
                    for (int i2 = 0; i2 <= 4; ++i2) {
                        if ((l < 1 || i2 < 1 || l > 3 || i2 > 3) && World.doesBlockHaveSolidTopSurface((IBlockAccess)this.theWorld, i + l, k - 1, j + i2) && this.theWorld.isAirBlock(i + l, k, j + i2) && this.theWorld.isAirBlock(i + l, k + 1, j + i2)) {
                            if (this.theEntity.ridingEntity != null) {
                                this.theEntity.ridingEntity.setLocationAndAngles((double)(i + l + 0.5f), (double)k, (double)(j + i2 + 0.5f), this.theEntity.rotationYaw, this.theEntity.rotationPitch);
                            }
                            this.theEntity.setLocationAndAngles((double)(i + l + 0.5f), (double)k, (double)(j + i2 + 0.5f), this.theEntity.rotationYaw, this.theEntity.rotationPitch);
                            this.ownerPathfinder.clearPathEntity();
                            return;
                        }
                    }
                }
            }
        }
    }
}
