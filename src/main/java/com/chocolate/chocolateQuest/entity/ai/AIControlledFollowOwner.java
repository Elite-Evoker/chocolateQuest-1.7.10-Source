package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.IEntityOwnable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;

public class AIControlledFollowOwner extends AIControlledBase
{
    protected EntityLivingBase target;
    World theWorld;
    private PathNavigate ownerPathfinder;
    float maxDist;
    float minDist;
    private boolean teleports;
    private int pathFindingCooldown;
    
    public AIControlledFollowOwner(final EntityHumanBase par1EntityTameable, final float minDist, final float maxDist) {
        super(par1EntityTameable);
        this.teleports = false;
        this.theWorld = par1EntityTameable.worldObj;
        this.ownerPathfinder = par1EntityTameable.getNavigator();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.setMutexBits(4);
        this.teleports = false;
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase entityliving = this.getOwner();
        if (entityliving == null) {
            return false;
        }
        final double dist = this.owner.getDistanceSqToEntity((Entity)entityliving);
        final double minDist = this.minDist * this.minDist;
        Label_0065: {
            if (this.owner.getAttackTarget() == null) {
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
        return (EntityLivingBase)((IEntityOwnable)this.owner).getOwner();
    }
    
    public boolean continueExecuting() {
        return !this.ownerPathfinder.noPath() && this.owner.getDistanceSqToEntity((Entity)this.target) > this.maxDist * this.maxDist;
    }
    
    public void startExecuting() {
        this.pathFindingCooldown = 0;
        this.owner.getNavigator().setAvoidsWater(false);
    }
    
    public void resetTask() {
        this.target = null;
        this.ownerPathfinder.clearPathEntity();
    }
    
    public void updateTask() {
        this.owner.getLookHelper().setLookPositionWithEntity((Entity)this.target, 10.0f, (float)this.owner.getVerticalFaceSpeed());
        final int pathFindingCooldown = this.pathFindingCooldown - 1;
        this.pathFindingCooldown = pathFindingCooldown;
        if (pathFindingCooldown <= 0) {
            this.pathFindingCooldown = 10;
            if (!this.tryMoveToXYZ(this.target.posX, this.target.posY, this.target.posZ, 1.15f) && this.owner.getDistanceSqToEntity((Entity)this.target) >= 288.0 && this.teleports) {
                final int i = MathHelper.floor_double(this.target.posX) - 2;
                final int j = MathHelper.floor_double(this.target.posZ) - 2;
                final int k = MathHelper.floor_double(this.target.boundingBox.minY);
                for (int l = 0; l <= 4; ++l) {
                    for (int i2 = 0; i2 <= 4; ++i2) {
                        if ((l < 1 || i2 < 1 || l > 3 || i2 > 3) && World.doesBlockHaveSolidTopSurface((IBlockAccess)this.theWorld, i + l, k - 1, j + i2) && !this.theWorld.isBlockNormalCubeDefault(i + l, k, j + i2, true) && !this.theWorld.isBlockNormalCubeDefault(i + l, k + 1, j + i2, true)) {
                            if (this.owner.ridingEntity != null) {
                                this.owner.ridingEntity.setLocationAndAngles((double)(i + l + 0.5f), (double)k, (double)(j + i2 + 0.5f), this.owner.rotationYaw, this.owner.rotationPitch);
                            }
                            this.owner.setLocationAndAngles((double)(i + l + 0.5f), (double)k, (double)(j + i2 + 0.5f), this.owner.rotationYaw, this.owner.rotationPitch);
                            this.ownerPathfinder.clearPathEntity();
                            return;
                        }
                    }
                }
            }
        }
    }
}
