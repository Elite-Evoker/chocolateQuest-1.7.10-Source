package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.util.DamageSource;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileBulletPistol extends ProjectileBase
{
    public static final int IRON = 0;
    public static final int GOLD = 1;
    public static final int MAGIC = 2;
    public static final int FIRE = 3;
    public static final int CANNON = 4;
    Random rand;
    
    public ProjectileBulletPistol(final EntityBaseBall entity) {
        super(entity);
        this.rand = new Random();
    }
    
    @Override
    public int getTextureIndex() {
        if (this.entity.getlvl() == 1) {
            return 225;
        }
        if (this.entity.getlvl() == 2) {
            return 226;
        }
        if (this.entity.getlvl() == 3) {
            return 227;
        }
        return 224;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition mop) {
        if (!this.entity.worldObj.isRemote) {
            if (mop.entityHit != null) {
                if (mop.entityHit != this.entity.shootingEntity.riddenByEntity && mop.entityHit != this.entity.shootingEntity.ridingEntity) {
                    float damage = (float)this.getBulletBaseDamage();
                    final int bulletType = this.entity.getlvl();
                    if (bulletType == 1) {
                        damage += damage * 0.66f;
                    }
                    if (bulletType == 4) {
                        damage *= 2.0f;
                    }
                    damage *= this.entity.damageMultiplier;
                    DamageSource ds;
                    if (bulletType == 3) {
                        ds = Elements.fire.getDamageSourceIndirect((Entity)this.entity.getThrower(), (Entity)this.entity);
                    }
                    else if (bulletType == 2) {
                        ds = Elements.magic.getDamageSourceIndirect((Entity)this.entity.getThrower(), (Entity)this.entity);
                    }
                    else {
                        ds = Elements.blast.getDamageSourceIndirect((Entity)this.entity.getThrower(), (Entity)this.entity);
                    }
                    ds.setProjectile();
                    if (HelperDamageSource.attackEntityWithoutKnockBack(mop.entityHit, ds, damage)) {
                        if (bulletType != 4) {
                            this.entity.setDead();
                        }
                        if (mop.entityHit instanceof EntityLivingBase) {
                            ((EntityLivingBase)mop.entityHit).hurtResistantTime = 1;
                        }
                        final Entity entityHit = mop.entityHit;
                        final Entity entityHit2 = mop.entityHit;
                        final double n = 0.0;
                        entityHit2.motionZ = n;
                        entityHit.motionX = n;
                    }
                }
            }
            else {
                final Material mat = this.entity.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getMaterial();
                if (mat != Material.air && mat != Material.fire && mat != Material.plants && mat != Material.vine && mat != Material.web) {
                    this.entity.setDead();
                }
            }
        }
    }
    
    protected int getBulletBaseDamage() {
        return 6;
    }
    
    @Override
    public void onSpawn() {
        this.entity.worldObj.playSoundAtEntity((Entity)this.entity, "chocolateQuest:handgun", 0.2f, this.getBulletPitch());
    }
    
    public float getBulletPitch() {
        return 1.0f;
    }
    
    @Override
    public void onUpdateInAir() {
        if (this.entity.worldObj.isRemote) {
            this.entity.worldObj.spawnParticle("smoke", this.entity.posX, this.entity.posY, this.entity.posZ, 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public float getSize() {
        if (this.entity.getlvl() >= 4) {
            return 0.4f;
        }
        return 0.1f;
    }
    
    @Override
    public boolean canBounce() {
        return false;
    }
}
