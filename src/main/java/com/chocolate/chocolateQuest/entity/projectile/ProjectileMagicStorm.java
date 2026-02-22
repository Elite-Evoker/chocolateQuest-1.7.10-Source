package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileMagicStorm extends ProjectileBase
{
    int deathTime;
    Random rand;
    
    public ProjectileMagicStorm(final EntityBaseBall entity) {
        super(entity);
        this.deathTime = 300;
        this.rand = new Random();
    }
    
    @Override
    public int getTextureIndex() {
        return -3;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
    }
    
    @Override
    public void onUpdateInAir() {
        final EntityBaseBall entity = this.entity;
        final EntityBaseBall entity2 = this.entity;
        final EntityBaseBall entity3 = this.entity;
        final double motionX = 0.0;
        entity3.motionY = motionX;
        entity2.motionZ = motionX;
        entity.motionX = motionX;
        final int level = this.entity.getlvl() + 1;
        final int dist = 10 + 5 * level;
        final int height = 5 + dist / 2;
        final double posX = this.entity.posX;
        final double posY = this.entity.posY + height;
        final double posZ = this.entity.posZ;
        if (this.entity.worldObj.isRemote) {
            final Elements element = this.entity.getElement();
            for (int i = 0; i < level; ++i) {
                EffectManager.spawnParticle(5, this.entity.worldObj, posX + (this.rand.nextFloat() - 0.5f) * dist, posY + (this.rand.nextFloat() - 0.5f) * 2.0f, posZ + (this.rand.nextFloat() - 0.5f) * dist, element.getColorX(), element.getColorY(), element.getColorZ());
            }
        }
        else if (this.entity.ticksExisted >= this.deathTime) {
            this.entity.setDead();
        }
        for (int j = 0; j < level; ++j) {
            if (!this.entity.worldObj.isRemote && this.entity.ticksExisted % 3 == 0) {
                final EntityBaseBall ball = new EntityBaseBall(this.entity.worldObj, this.entity.getThrower(), 105, 0, this.entity.getElement());
                ball.setDamageMultiplier(this.entity.getDamageMultiplier());
                ball.setPosition(posX + (this.rand.nextFloat() - 0.5f) * dist, posY, posZ + (this.rand.nextFloat() - 0.5f) * dist);
                ball.setThrowableHeading((double)(this.rand.nextFloat() / 10.0f), -1.0, (double)(this.rand.nextFloat() / 10.0f), 1.0f, 1.0f);
                this.entity.worldObj.spawnEntityInWorld((Entity)ball);
            }
        }
    }
    
    @Override
    public float getSize() {
        return 0.1f;
    }
    
    @Override
    public void onSpawn() {
        final EntityBaseBall entity = this.entity;
        final EntityBaseBall entity2 = this.entity;
        final EntityBaseBall entity3 = this.entity;
        final double motionX = 0.0;
        entity3.motionY = motionX;
        entity2.motionZ = motionX;
        entity.motionX = motionX;
        this.deathTime = 300 + this.entity.getlvl() * 100;
        this.entity.posY = this.entity.shootingEntity.boundingBox.minY + 0.5;
        this.entity.setInmuneToFire(true);
    }
}
