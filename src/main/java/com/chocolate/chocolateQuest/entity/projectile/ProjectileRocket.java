package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;

public class ProjectileRocket extends ProjectileBulletPistol
{
    protected Entity aimedTo;
    int ticksToStartAim;
    
    public ProjectileRocket(final EntityBaseBall entity) {
        super(entity);
    }
    
    public ProjectileRocket(final EntityBaseBall entity, final Entity aimedTo) {
        this(entity, aimedTo, 0);
    }
    
    public ProjectileRocket(final EntityBaseBall entity, final Entity aimedTo, final int ticksToStartAim) {
        super(entity);
        this.aimedTo = aimedTo;
        this.ticksToStartAim = ticksToStartAim;
    }
    
    @Override
    public void onUpdateInAir() {
        if (this.aimedTo != null && this.entity.ticksExisted > this.ticksToStartAim) {
            final double x = this.aimedTo.posX - this.entity.posX;
            final double y = this.aimedTo.posY - this.entity.posY;
            final double z = this.aimedTo.posZ - this.entity.posZ;
            Vec3 v = Vec3.createVectorHelper(x, y, z);
            v = v.normalize();
            final double xM = v.xCoord;
            final double yM = v.yCoord;
            final double zM = v.zCoord;
            float desp = 0.2f;
            final EntityBaseBall entity = this.entity;
            entity.motionX += xM * desp;
            final EntityBaseBall entity2 = this.entity;
            entity2.motionY += yM * desp;
            final EntityBaseBall entity3 = this.entity;
            entity3.motionZ += zM * desp;
            desp *= 20.0f;
            if (yM > 0.0) {
                this.entity.motionY = Math.min(this.entity.motionY, yM * desp);
            }
            else {
                this.entity.motionY = Math.max(this.entity.motionY, yM * desp);
            }
            if (xM > 0.0) {
                this.entity.motionX = Math.min(this.entity.motionX, xM * desp);
            }
            else {
                this.entity.motionX = Math.max(this.entity.motionX, xM * desp);
            }
            if (zM > 0.0) {
                this.entity.motionZ = Math.min(this.entity.motionZ, zM * desp);
            }
            else {
                this.entity.motionZ = Math.max(this.entity.motionZ, zM * desp);
            }
        }
        if (this.entity.worldObj.isRemote) {
            final float s = 0.2f;
            float m = 0.0f;
            for (int maxCount = 2, i = 0; i <= maxCount; ++i) {
                m = i / (float)maxCount;
                EffectManager.spawnParticle(6, this.entity.worldObj, this.entity.posX + (this.rand.nextFloat() - 0.5f) * s - this.entity.motionX * m, this.entity.posY + (this.rand.nextFloat() - 0.5f) * s - this.entity.motionY * m, this.entity.posZ + (this.rand.nextFloat() - 0.5f) * s - this.entity.motionZ * m, 1.0, 1.0, 1.0);
            }
        }
    }
    
    @Override
    protected int getBulletBaseDamage() {
        return 10;
    }
    
    @Override
    public float getBulletPitch() {
        return 0.4f;
    }
    
    @Override
    public float getSize() {
        return 0.2f;
    }
}
