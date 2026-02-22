package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.util.DamageSource;
import com.chocolate.chocolateQuest.magic.Elements;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileMagicArea extends ProjectileMagic
{
    int lifeTime;
    int deathTime;
    Random rand;
    
    public ProjectileMagicArea(final EntityBaseBall entity) {
        super(entity);
        this.lifeTime = 10;
        this.deathTime = 100;
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
        ++this.lifeTime;
        if (this.lifeTime >= this.deathTime) {
            this.entity.setDead();
        }
        final double dist = this.lifeTime / 10.0f;
        final AxisAlignedBB var3 = this.entity.boundingBox.expand(dist, 0.1, dist);
        final List<Entity> list = this.entity.worldObj.getEntitiesWithinAABB((Class)EntityLivingBase.class, var3);
        for (final Entity e : list) {
            if (e instanceof EntityLivingBase && e != this.entity.getThrower() && (int)e.getDistanceToEntity((Entity)this.entity) == this.lifeTime / 10 && e.onGround && !this.entity.worldObj.isRemote) {
                final Elements element = this.entity.getElement();
                final DamageSource ds = this.getDamageSource();
                float damage = 3.0f * this.entity.getDamageMultiplier();
                damage = element.onHitEntity((Entity)this.entity.getThrower(), e, damage);
                e.attackEntityFrom(ds, damage);
            }
        }
        if (this.entity.worldObj.isRemote) {
            double x = Math.sin(this.lifeTime / 10.0f);
            double z = Math.cos(this.lifeTime / 10.0f);
            final double step = 0.39269908169872414;
            for (int i = 0; i < 16; ++i) {
                x = Math.sin(this.lifeTime / 10.0f + step * i) * (this.lifeTime / 10.0f);
                z = Math.cos(this.lifeTime / 10.0f + step * i) * (this.lifeTime / 10.0f);
                EffectManager.spawnElementParticle(0, this.entity.worldObj, this.entity.posX + x, this.entity.posY - 0.5 + this.rand.nextFloat() - 0.5, this.entity.posZ + z, 0.0, 0.1, 0.0, this.entity.getElement());
            }
        }
        final EntityBaseBall entity = this.entity;
        final EntityBaseBall entity2 = this.entity;
        final EntityBaseBall entity3 = this.entity;
        final double motionX = 0.0;
        entity3.motionY = motionX;
        entity2.motionZ = motionX;
        entity.motionX = motionX;
        if (!this.entity.onGround) {
            if (this.entity.worldObj.getBlock((int)this.entity.posX, (int)this.entity.posY, (int)this.entity.posZ) != Blocks.air) {
                this.entity.onGround = true;
            }
            final EntityBaseBall entity4 = this.entity;
            entity4.motionY -= 0.1;
        }
    }
    
    @Override
    public float getSize() {
        return 0.1f;
    }
    
    @Override
    public boolean canBounce() {
        return false;
    }
    
    @Override
    public void onSpawn() {
        this.deathTime = 40 + this.entity.getlvl() * 20;
        this.entity.posY = this.entity.shootingEntity.boundingBox.minY + 0.5;
        this.entity.setInmuneToFire(true);
        final EntityBaseBall entity = this.entity;
        final EntityBaseBall entity2 = this.entity;
        final EntityBaseBall entity3 = this.entity;
        final double motionX = 0.0;
        entity3.motionY = motionX;
        entity2.motionZ = motionX;
        entity.motionX = motionX;
    }
    
    @Override
    public boolean longRange() {
        return false;
    }
}
