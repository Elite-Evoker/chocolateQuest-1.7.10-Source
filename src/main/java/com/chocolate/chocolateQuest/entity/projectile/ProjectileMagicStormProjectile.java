package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.util.DamageSource;
import com.chocolate.chocolateQuest.magic.Elements;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import java.util.Random;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.particles.EffectManager;

public class ProjectileMagicStormProjectile extends ProjectileMagic
{
    public double x;
    public double y;
    public double z;
    boolean isFirstTick;
    
    public ProjectileMagicStormProjectile(final EntityBaseBall entity) {
        super(entity);
        this.z = 0.0;
        this.isFirstTick = true;
    }
    
    @Override
    public int getTextureIndex() {
        if (this.type == 2) {
            return -2;
        }
        return super.getTextureIndex();
    }
    
    @Override
    public void onUpdateInAir() {
        if (this.isFirstTick) {
            this.isFirstTick = false;
            this.x = this.entity.posX;
            this.y = this.entity.posY;
            this.z = this.entity.posZ;
        }
        super.onUpdateInAir();
        final EntityBaseBall entity = this.entity;
        entity.motionY -= 0.01;
    }
    
    @Override
    public void onDead() {
        if (this.entity.worldObj.isRemote) {
            final Random rand = this.entity.getRNG();
            final float desp = 0.3f;
            for (int i = 0; i < 5; ++i) {
                EffectManager.spawnElementParticle(0, this.entity.worldObj, this.entity.posX, this.entity.posY + rand.nextFloat() - 0.5, this.entity.posZ, (rand.nextFloat() - 0.5f) * desp, 0.10000000149011612, (rand.nextFloat() - 0.5f) * desp, this.entity.getElement());
            }
        }
        else {
            final double dist = 2.0;
            final AxisAlignedBB var3 = this.entity.boundingBox.expand(dist, 0.0, dist);
            final List<Entity> list = this.entity.worldObj.getEntitiesWithinAABB((Class)EntityLivingBase.class, var3);
            for (final Entity e : list) {
                if (e instanceof EntityLivingBase && e != this.entity.getThrower()) {
                    final Elements element = this.entity.getElement();
                    final DamageSource ds = this.getDamageSource().setProjectile();
                    float damage = 4.0f * this.entity.getDamageMultiplier();
                    damage = element.onHitEntity((Entity)this.entity.getThrower(), e, damage);
                    HelperDamageSource.attackEntityWithoutKnockBack(e, ds, damage);
                }
            }
        }
    }
    
    @Override
    public float getSize() {
        return 0.8f;
    }
}
