package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileHealBall extends ProjectileBase
{
    Random rand;
    
    public ProjectileHealBall(final EntityBaseBall entity) {
        super(entity);
        this.rand = new Random();
    }
    
    @Override
    public int getTextureIndex() {
        return 229;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.entity.worldObj.isRemote && par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit == this.entity.getThrower()) {
            this.entity.setDead();
            this.entity.getThrower().heal((float)this.entity.getlvl());
        }
    }
    
    @Override
    public void onUpdateInAir() {
        final Entity shooting = (Entity)this.entity.getThrower();
        if (shooting != null) {
            Vec3 fc = Vec3.createVectorHelper(shooting.posX - this.entity.posX, shooting.posY + 0.4 - this.entity.posY, shooting.posZ - this.entity.posZ);
            if (fc.lengthVector() < 1.0) {
                this.entity.setDead();
                this.entity.getThrower().heal((float)this.entity.getlvl());
            }
            fc = fc.normalize();
            final double s = 0.2;
            this.entity.motionX = fc.xCoord * s;
            this.entity.motionY = fc.yCoord * s;
            this.entity.motionZ = fc.zCoord * s;
            if (shooting.isDead) {
                this.entity.setDead();
            }
        }
        else {
            this.entity.setDead();
        }
    }
    
    @Override
    public float getSize() {
        return 0.4f;
    }
    
    @Override
    public boolean canBounce() {
        return false;
    }
}
