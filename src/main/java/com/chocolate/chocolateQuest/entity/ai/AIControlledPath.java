package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class AIControlledPath extends AIControlledBase
{
    boolean pathDirection;
    int currentPathPoint;
    
    public AIControlledPath(final EntityHumanBase owner) {
        super(owner);
        this.pathDirection = false;
        this.currentPathPoint = -1;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.owner.path != null;
    }
    
    public void updateTask() {
        if (this.owner.path != null) {
            if (this.currentPathPoint == -1) {
                this.setNearestPathPoint();
            }
            else if (this.currentPathPoint < this.owner.path.length) {
                int x = MathHelper.floor_double(this.owner.posX);
                int y = MathHelper.floor_double(this.owner.posY);
                int z = MathHelper.floor_double(this.owner.posZ);
                x -= this.owner.path[this.currentPathPoint].xCoord;
                y -= this.owner.path[this.currentPathPoint].yCoord;
                z -= this.owner.path[this.currentPathPoint].zCoord;
                double dist = x * x + y * y + z * z;
                if (this.owner.ridingEntity != null) {
                    dist -= this.owner.ridingEntity.height * 2.0f + this.owner.ridingEntity.width * 2.0f;
                }
                if (dist < 4.0) {
                    this.nextPathPoint();
                }
                else {
                    this.tryMoveToXYZ(this.owner.path[this.currentPathPoint].xCoord, this.owner.path[this.currentPathPoint].yCoord, this.owner.path[this.currentPathPoint].zCoord, 0.7f);
                }
            }
            else {
                this.setNearestPathPoint();
            }
        }
        super.updateTask();
    }
    
    @Override
    public PathNavigate getNavigator() {
        if (this.owner.ridingEntity != null && this.owner.ridingEntity instanceof EntityLiving && ((EntityLiving)this.owner.ridingEntity).getNavigator() != null) {
            return ((EntityLiving)this.owner.ridingEntity).getNavigator();
        }
        return this.owner.getNavigator();
    }
    
    public void nextPathPoint() {
        if (this.currentPathPoint >= this.owner.path.length - 1) {
            this.pathDirection = false;
            this.currentPathPoint = this.owner.path.length - 1;
        }
        if (this.currentPathPoint == 0) {
            this.pathDirection = true;
            ++this.currentPathPoint;
        }
        else if (this.pathDirection) {
            ++this.currentPathPoint;
        }
        else {
            --this.currentPathPoint;
        }
    }
    
    public void setNearestPathPoint() {
        int closestPoint = -1;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < this.owner.path.length; ++i) {
            int x = MathHelper.floor_double(this.owner.posX);
            int y = MathHelper.floor_double(this.owner.posY);
            int z = MathHelper.floor_double(this.owner.posZ);
            x -= this.owner.path[i].xCoord;
            y -= this.owner.path[i].yCoord;
            z -= this.owner.path[i].zCoord;
            final double dist = x * x + y * y + z * z;
            if (dist < minDistance) {
                closestPoint = i;
                minDistance = dist;
            }
        }
        this.currentPathPoint = closestPoint;
    }
}
