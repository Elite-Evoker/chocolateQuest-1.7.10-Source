package com.chocolate.chocolateQuest.entity.projectile;

import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileEarthQuake extends ProjectileBase
{
    int lifeTime;
    Random rand;
    
    public ProjectileEarthQuake(final EntityBaseBall entity) {
        super(entity);
        this.lifeTime = 60;
        this.rand = new Random();
    }
    
    @Override
    public int getTextureIndex() {
        return -3;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.entity.worldObj.isRemote) {
            if (!(par1MovingObjectPosition.entityHit instanceof EntityLiving)) {
                this.entity.motionY = 0.0;
            }
        }
    }
    
    @Override
    public void onUpdateInAir() {
        --this.lifeTime;
        if (this.lifeTime <= 0) {
            this.entity.setDead();
        }
        Block id = this.entity.worldObj.getBlock((int)this.entity.posX, (int)this.entity.posY - 1, (int)this.entity.posZ);
        if (id == null || id == Blocks.air) {
            id = Blocks.glass;
        }
        final double dist = 1.0;
        final AxisAlignedBB var3 = this.entity.boundingBox.expand(dist, 2.0, dist);
        final List<Entity> list = this.entity.worldObj.getEntitiesWithinAABB((Class)EntityLivingBase.class, var3);
        for (final Entity e : list) {
            if (e instanceof EntityLivingBase && e != this.entity.getThrower() && !this.entity.worldObj.isRemote && e.onGround) {
                e.motionY = 0.3;
                e.attackEntityFrom(HelperDamageSource.causeIndirectMagicDamage((Entity)this.entity, (Entity)this.entity.getThrower()), 1.0f);
            }
        }
        if (this.entity.worldObj.isRemote) {
            for (int i = 0; i < 8; ++i) {
                this.entity.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(id) + "_" + 0, this.entity.posX + this.rand.nextFloat() - 0.5, this.entity.posY + this.rand.nextFloat() - 0.5, this.entity.posZ + this.rand.nextFloat() - 0.5, (double)(this.rand.nextFloat() - 0.5f), (double)this.rand.nextFloat(), (double)(this.rand.nextFloat() - 0.5f));
            }
        }
    }
    
    @Override
    public float getSize() {
        return 1.5f;
    }
    
    @Override
    public boolean canBounce() {
        return false;
    }
    
    @Override
    public void onSpawn() {
        final EntityBaseBall entity = this.entity;
        --entity.posY;
        this.entity.motionX /= 2.5;
        this.entity.motionY = -1.0;
        this.entity.motionZ /= 2.5;
        this.entity.setInmuneToFire(true);
    }
    
    @Override
    public boolean longRange() {
        return false;
    }
}
