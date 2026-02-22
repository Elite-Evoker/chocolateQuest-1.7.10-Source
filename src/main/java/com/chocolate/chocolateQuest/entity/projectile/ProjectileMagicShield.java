package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.util.MovingObjectPosition;
import java.util.Random;
import com.chocolate.chocolateQuest.magic.Elements;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;

public class ProjectileMagicShield extends ProjectileMagicAimed
{
    int deathTime;
    final int MAX_HEALTH = 600;
    final int HEALTH_PER_LEVEL = 200;
    
    public ProjectileMagicShield(final EntityBaseBall entity) {
        super(entity);
        this.deathTime = 0;
    }
    
    public ProjectileMagicShield(final EntityBaseBall entity, final Entity aimedTo) {
        this(entity, aimedTo, 0);
    }
    
    public ProjectileMagicShield(final EntityBaseBall entity, final Entity aimedTo, final int ticksToStartAim) {
        super(entity, aimedTo);
        this.deathTime = 0;
    }
    
    @Override
    public void onUpdateInAir() {
        ++this.deathTime;
        final EntityBaseBall entity = this.entity;
        final EntityBaseBall entity2 = this.entity;
        final EntityBaseBall entity3 = this.entity;
        final double motionX = 0.0;
        entity3.motionY = motionX;
        entity2.motionZ = motionX;
        entity.motionX = motionX;
        if (this.aimedTo != null && this.entity.ticksExisted > this.ticksToStartAim && this.aimedTo != null && !this.entity.worldObj.isRemote) {
            final int lifeTime = this.entity.ticksExisted;
            final double x = this.aimedTo.posX + Math.cos(lifeTime / 20.0f) * 2.0;
            final double z = this.aimedTo.posZ + Math.sin(lifeTime / 20.0f) * 2.0;
            this.entity.setPosition(x, this.aimedTo.posY, z);
            final double dist = 0.3;
            final AxisAlignedBB var3 = this.entity.boundingBox.expand(dist, dist, dist);
            final List<Entity> list = this.entity.worldObj.getEntitiesWithinAABB((Class)Entity.class, var3);
            for (final Entity et : list) {
                if (et instanceof EntityLivingBase) {
                    final EntityLivingBase e = (EntityLivingBase)et;
                    if (e == this.entity.getThrower()) {
                        continue;
                    }
                    if (this.entity.getThrower() != null && this.entity.getThrower().getTeam() != null && this.entity.getThrower().isOnSameTeam(e)) {
                        return;
                    }
                    final Elements element = this.entity.getElement();
                    float damage = 4.0f * this.entity.getDamageMultiplier();
                    damage = element.onHitEntity((Entity)this.entity.getThrower(), (Entity)e, damage);
                    e.attackEntityFrom(element.getDamageSourceIndirect((Entity)this.entity, (Entity)this.entity.getThrower()), damage);
                    this.deathTime += 30;
                }
            }
        }
        if (this.deathTime > this.getMaxLifeTime()) {
            this.entity.setDead();
        }
        if (this.entity.worldObj.isRemote) {
            final Random rand = this.entity.worldObj.rand;
            EffectManager.spawnElementParticle(0, this.entity.worldObj, this.entity.posX + rand.nextFloat() - 0.5, this.entity.posY + rand.nextFloat() - 0.5, this.entity.posZ + rand.nextFloat() - 0.5, 0.0, 0.0, 0.0, this.entity.getElement());
        }
    }
    
    @Override
    public int getMaxLifeTime() {
        return 600 + 200 * this.entity.getlvl();
    }
    
    @Override
    public void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
    }
}
