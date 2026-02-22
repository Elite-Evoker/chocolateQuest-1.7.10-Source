package com.chocolate.chocolateQuest.entity.projectile;

import java.util.Iterator;
import java.util.List;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileFireFalling extends ProjectileBase
{
    Random rand;
    
    public ProjectileFireFalling(final EntityBaseBall entity) {
        super(entity);
        this.rand = new Random();
    }
    
    @Override
    public int getTextureIndex() {
        int i = (int)(this.entity.ticksExisted * 0.2 % 8.0);
        if (i >= 4) {
            i = 7 - i;
        }
        return (this.entity.ticksExisted * 0.2 % 2.0 == 0.0) ? 242 : 243;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition mop) {
        if (!this.entity.worldObj.isRemote) {
            final Entity e = mop.entityHit;
            if (e != null) {
                if (e instanceof EntityLivingBase) {}
            }
            else {
                if (this.entity.getlvl() >= 1 && this.entity.worldObj.isAirBlock(mop.blockX, mop.blockY + 1, mop.blockZ) && this.entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    this.entity.worldObj.setBlock(mop.blockX, mop.blockY + 1, mop.blockZ, (Block)Blocks.fire);
                }
                this.entity.setDead();
            }
        }
    }
    
    @Override
    public float getGravityVelocity() {
        return 0.03f;
    }
    
    @Override
    public void onUpdateInAir() {
        final EntityBaseBall entity = this.entity;
        entity.motionX *= 0.8;
        final EntityBaseBall entity2 = this.entity;
        entity2.motionZ *= 0.8;
        final float x = (float)Math.sin(Math.toRadians(this.entity.rotationYaw));
        final float z = (float)Math.cos(Math.toRadians(this.entity.rotationYaw));
        final double y = -Math.sin(Math.toRadians(this.entity.rotationPitch));
        if (this.entity.shootingEntity != null) {
            final int dist = 2;
            final List<Entity> list = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this.entity.shootingEntity, this.entity.boundingBox.expand((double)dist, (double)dist, (double)dist));
            for (final Entity e : list) {
                if (e instanceof EntityLivingBase) {
                    e.setFire(4);
                    e.attackEntityFrom(HelperDamageSource.causeFireProjectileDamage((Entity)this.entity, (Entity)this.entity.shootingEntity), 1.0f);
                }
            }
        }
        if (this.entity.worldObj.isRemote) {
            EffectManager.spawnParticle(3, this.entity.worldObj, this.entity.posX, this.entity.posY + 1.0, this.entity.posZ, this.entity.motionX + (this.rand.nextFloat() - 0.5) / 8.0, this.entity.motionY + (this.rand.nextFloat() - 0.5) / 8.0 + 0.4, this.entity.motionZ + (this.rand.nextFloat() - 0.5) / 8.0);
        }
    }
    
    @Override
    public float getSize() {
        return 1.2f;
    }
    
    @Override
    public boolean canBounce() {
        return false;
    }
    
    @Override
    public boolean longRange() {
        return false;
    }
}
