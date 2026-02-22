package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileVapiricBall extends ProjectileBase
{
    Random rand;
    
    public ProjectileVapiricBall(final EntityBaseBall entity) {
        super(entity);
        this.rand = new Random();
    }
    
    @Override
    public int getTextureIndex() {
        return 228;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition mop) {
        if (!this.entity.worldObj.isRemote) {
            if (mop.entityHit != null) {
                if (mop.entityHit instanceof EntityLivingBase && !mop.entityHit.isEntityEqual((Entity)this.entity.shootingEntity) && mop.entityHit != this.entity.shootingEntity.riddenByEntity && mop.entityHit != this.entity.shootingEntity.ridingEntity) {
                    final float damage = (2 + this.entity.getlvl()) * this.entity.getDamageMultiplier();
                    if (mop.entityHit.attackEntityFrom(HelperDamageSource.getBulletDamage((Entity)this.entity, (Entity)this.entity.getThrower()), damage)) {
                        if (this.entity.getThrower() != null && !this.entity.worldObj.isRemote) {
                            final EntityBaseBall ball = new EntityBaseBall(this.entity.worldObj, this.entity.getThrower(), 9, 1 + this.entity.getlvl() / 2);
                            ball.setPosition(this.entity.posX, this.entity.posY, this.entity.posZ);
                            this.entity.worldObj.spawnEntityInWorld((Entity)ball);
                        }
                        this.entity.setDead();
                    }
                }
            }
            else {
                this.entity.setDead();
            }
        }
    }
    
    @Override
    public void onUpdateInAir() {
        for (int i = 0; i < 1 + this.entity.getlvl(); ++i) {
            this.entity.worldObj.spawnParticle("portal", this.entity.posX + this.rand.nextFloat() - 0.5, this.entity.posY + this.rand.nextFloat() - 0.5, this.entity.posZ + this.rand.nextFloat() - 0.5, (double)(this.rand.nextFloat() - 0.5f), (double)(this.rand.nextFloat() - 0.5f), (double)(this.rand.nextFloat() - 0.5f));
        }
    }
    
    @Override
    public float getSize() {
        return 0.5f;
    }
    
    @Override
    public boolean canBounce() {
        return false;
    }
    
    @Override
    public void onSpawn() {
        this.entity.worldObj.playSoundEffect((double)(int)this.entity.posX, (double)(int)this.entity.posY, (double)(int)this.entity.posZ, "random.bow", 4.0f, (1.0f + (this.entity.worldObj.rand.nextFloat() - this.entity.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
    }
}
