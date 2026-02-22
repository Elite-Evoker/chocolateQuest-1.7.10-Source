package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileFireBall extends ProjectileBase
{
    Random rand;
    
    public ProjectileFireBall(final EntityBaseBall entity) {
        super(entity);
        this.rand = new Random();
    }
    
    @Override
    public int getTextureIndex() {
        return (this.entity.ticksExisted * 0.33 % 2.0 == 0.0) ? 234 : 250;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition mop) {
        if (!this.entity.worldObj.isRemote) {
            final Entity e = mop.entityHit;
            if (e != null) {
                if (e instanceof EntityLivingBase && !e.isEntityEqual((Entity)this.entity.shootingEntity) && mop.entityHit != this.entity.shootingEntity.riddenByEntity && mop.entityHit != this.entity.shootingEntity.ridingEntity) {
                    e.attackEntityFrom(HelperDamageSource.causeFireProjectileDamage((Entity)this.entity, (Entity)this.entity.shootingEntity), 3.0f);
                    e.setFire(8);
                    this.entity.worldObj.playSoundEffect((double)(int)e.posX, (double)(int)e.posY, (double)(int)e.posZ, "fire.fire", 4.0f, (1.0f + (this.entity.worldObj.rand.nextFloat() - this.entity.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
                    this.entity.setDead();
                }
            }
            else {
                this.entity.setDead();
            }
        }
    }
    
    @Override
    public void onUpdateInAir() {
        if (this.entity.worldObj.isRemote) {
            for (int i = 0; i < 1 + this.entity.getlvl(); ++i) {
                this.entity.worldObj.spawnParticle("fire", this.entity.posX + this.rand.nextFloat() - 0.5, this.entity.posY + this.rand.nextFloat() - 0.5, this.entity.posZ + this.rand.nextFloat() - 0.5, (double)(this.rand.nextFloat() - 0.5f), (double)(this.rand.nextFloat() - 0.5f), (double)(this.rand.nextFloat() - 0.5f));
            }
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
}
