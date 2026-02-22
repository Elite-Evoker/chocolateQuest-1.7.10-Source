package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;

public class ProjectileMagicAimed extends ProjectileMagic
{
    protected Entity aimedTo;
    int ticksToStartAim;
    
    public ProjectileMagicAimed(final EntityBaseBall entity) {
        super(entity);
    }
    
    public ProjectileMagicAimed(final EntityBaseBall entity, final Entity aimedTo) {
        this(entity, aimedTo, 0);
    }
    
    public ProjectileMagicAimed(final EntityBaseBall entity, final Entity aimedTo, final int ticksToStartAim) {
        super(entity);
        this.aimedTo = aimedTo;
        this.ticksToStartAim = ticksToStartAim;
    }
    
    @Override
    public int getTextureIndex() {
        return 247 + this.type - ((this.entity.ticksExisted / 4 % 2 == 0) ? 0 : 16);
    }
    
    @Override
    public void onUpdateInAir() {
        super.onUpdateInAir();
        if (this.aimedTo != null && this.entity.ticksExisted >= this.ticksToStartAim) {
            final double x = this.aimedTo.posX - this.entity.posX;
            final double y = this.aimedTo.posY + this.aimedTo.height - 0.4 - this.entity.posY;
            final double z = this.aimedTo.posZ - this.entity.posZ;
            Vec3 v = Vec3.createVectorHelper(x, y, z);
            v = v.normalize();
            final double xM = v.xCoord;
            final double yM = v.yCoord;
            final double zM = v.zCoord;
            float desp = 0.1f;
            final EntityBaseBall entity = this.entity;
            entity.motionX += xM * desp;
            final EntityBaseBall entity2 = this.entity;
            entity2.motionY += yM * desp;
            final EntityBaseBall entity3 = this.entity;
            entity3.motionZ += zM * desp;
            desp *= 40.0f;
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
            EffectManager.spawnElementParticle(2, this.entity.worldObj, this.entity.posX, this.entity.posY, this.entity.posZ, 0.0, 0.4, 0.0, this.entity.getElement());
        }
    }
    
    @Override
    public float getSize() {
        return 0.8f;
    }
}
