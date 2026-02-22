package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.util.DamageSource;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileBubble extends ProjectileBase
{
    Random rand;
    int mountTime;
    
    public ProjectileBubble(final EntityBaseBall entity) {
        super(entity);
        this.mountTime = 0;
        this.rand = new Random();
    }
    
    @Override
    public int getTextureIndex() {
        return 240;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        this.entity.worldObj.playSoundEffect((double)(int)this.entity.posX, (double)(int)this.entity.posY, (double)(int)this.entity.posZ, "sounds.bubble", 4.0f, (1.0f + (this.entity.worldObj.rand.nextFloat() - this.entity.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (!this.entity.worldObj.isRemote) {
            if (par1MovingObjectPosition.entityHit instanceof EntityLivingBase) {
                if (par1MovingObjectPosition.entityHit.ridingEntity == null) {
                    par1MovingObjectPosition.entityHit.attackEntityFrom(HelperDamageSource.getBulletDamage((Entity)this.entity, (Entity)this.entity.getThrower()), 4.0f);
                    par1MovingObjectPosition.entityHit.mountEntity((Entity)this.entity);
                }
            }
            else {
                this.entity.setDead();
            }
        }
    }
    
    @Override
    public void onUpdateInAir() {
        if (this.entity.riddenByEntity != null) {
            this.entity.motionX = 0.0;
            this.entity.motionZ = 0.0;
            this.entity.motionY = 0.1;
            ++this.mountTime;
            if (this.mountTime >= 70 || (this.entity.riddenByEntity.isCollidedVertically && !this.entity.riddenByEntity.onGround)) {
                this.entity.setDead();
            }
        }
        else {
            final Material mat = this.entity.worldObj.getBlock(MathHelper.floor_double(this.entity.posX), MathHelper.floor_double(this.entity.posY), MathHelper.floor_double(this.entity.posZ)).getMaterial();
            if (mat == Material.fire) {
                this.entity.worldObj.setBlockToAir(MathHelper.floor_double(this.entity.posX), MathHelper.floor_double(this.entity.posY), MathHelper.floor_double(this.entity.posZ));
                this.entity.setDead();
            }
        }
        for (int i = 0; i < 1 + this.entity.getlvl(); ++i) {
            this.entity.worldObj.spawnParticle("splash", this.entity.posX + this.rand.nextFloat() - 0.5, this.entity.posY + this.rand.nextFloat() - 0.5, this.entity.posZ + this.rand.nextFloat() - 0.5, 0.0, -1.0, 0.0);
        }
    }
    
    @Override
    public float getSize() {
        if (this.entity.riddenByEntity != null) {
            return this.entity.riddenByEntity.width + this.entity.riddenByEntity.height;
        }
        return 1.5f;
    }
    
    @Override
    public boolean canBounce() {
        return false;
    }
    
    @Override
    public void onSpawn() {
        this.entity.worldObj.playSoundEffect((double)(int)this.entity.posX, (double)(int)this.entity.posY, (double)(int)this.entity.posZ, "random.swim", 4.0f, (1.0f + (this.entity.worldObj.rand.nextFloat() - this.entity.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
    }
    
    @Override
    public void attackFrom(final DamageSource d, final float damage) {
        this.mountTime += 10;
    }
    
    @Override
    public boolean longRange() {
        return false;
    }
    
    @Override
    public int getMaxLifeTime() {
        return 150;
    }
}
