package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;

public class ProjectilePoisonBall extends ProjectileBase
{
    int particleEffect;
    
    public ProjectilePoisonBall(final EntityBaseBall entity) {
        super(entity);
        this.particleEffect = 4;
    }
    
    @Override
    public int getTextureIndex() {
        return 246;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.entity.worldObj.isRemote) {
            final Entity e = par1MovingObjectPosition.entityHit;
            if (e != null) {
                if (e instanceof EntityLivingBase && e != this.entity.shootingEntity) {
                    if (e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.disableDamage) {
                        return;
                    }
                    e.attackEntityFrom(HelperDamageSource.causeProjectilePhysicalDamage((Entity)this.entity, (Entity)this.entity.getThrower()), 4.0f);
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.poison.id, 120, 1));
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
            EffectManager.spawnParticle(2, this.entity.worldObj, this.entity.posX, this.entity.posY + 1.0, this.entity.posZ, 0.0, 0.4, 0.0);
        }
        super.onUpdateInAir();
    }
    
    @Override
    public float getGravityVelocity() {
        return 0.0f;
    }
    
    @Override
    public float getSize() {
        return 0.8f;
    }
    
    @Override
    public boolean longRange() {
        return false;
    }
}
