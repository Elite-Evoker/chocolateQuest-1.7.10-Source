package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.item.ItemStack;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.init.Blocks;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;

public class ProjectileWindBall extends ProjectileBase
{
    int maxLifeTime;
    Random rand;
    
    public ProjectileWindBall(final EntityBaseBall entity) {
        super(entity);
        this.maxLifeTime = 80;
        this.rand = new Random();
    }
    
    @Override
    public int getTextureIndex() {
        return (this.entity.ticksExisted % 2 == 0) ? 235 : 236;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (this.entity.worldObj.isRemote || par1MovingObjectPosition.entityHit instanceof EntityLivingBase) {}
    }
    
    @Override
    public void onUpdateInAir() {
        if (this.entity.ticksExisted % 30 == 0) {
            this.entity.worldObj.playSoundAtEntity((Entity)this.entity, "chocolatequest:wind", 1.0f, 1.0f);
        }
        if (this.entity.ticksExisted >= this.maxLifeTime) {
            this.entity.setDead();
        }
        final EntityBaseBall entity = this.entity;
        entity.motionX *= 0.9;
        final EntityBaseBall entity2 = this.entity;
        entity2.motionY *= 0.9;
        final EntityBaseBall entity3 = this.entity;
        entity3.motionZ *= 0.9;
        final double dist = 5 + this.entity.getlvl();
        final AxisAlignedBB var3 = this.entity.boundingBox.expand(dist - 1.0, 2.0 + dist / 10.0, dist - 1.0);
        final List<Entity> list = this.entity.worldObj.getEntitiesWithinAABB((Class)Entity.class, var3);
        for (final Entity e : list) {
            if (e != this.entity && e != this.entity.getThrower()) {
                final Vec3 d = Vec3.createVectorHelper(this.entity.posX - e.posX, this.entity.posY - e.posY, this.entity.posZ - e.posZ);
                final double distToEntity = Math.max(15.0, (d.xCoord * d.xCoord + d.zCoord * d.zCoord) * 10.0);
                d.normalize();
                final double x = d.xCoord / distToEntity * dist / 5.0;
                double y = d.yCoord / Math.max(15.0, d.yCoord * d.yCoord * 10.0);
                final double z = d.zCoord / distToEntity * dist / 5.0;
                if (this.rand.nextInt(10) == 0) {
                    y += (dist - distToEntity) / 40.0;
                }
                if (e instanceof EntityLivingBase) {
                    final EntityLivingBase eLiving = (EntityLivingBase)e;
                    final ItemStack boots = eLiving.getEquipmentInSlot(1);
                    final boolean canBePushed = true;
                    if (boots == null || boots.getItem() != null) {}
                    if (e.canBePushed() && canBePushed) {
                        e.addVelocity(x, y, z);
                    }
                    if (this.entity.getDistanceSqToEntity(e) <= 3.0 && !this.entity.worldObj.isRemote) {
                        float damage = 1.0f * this.entity.getDamageMultiplier();
                        damage = this.entity.getElement().onHitEntity((Entity)this.entity.getThrower(), e, damage);
                        e.attackEntityFrom(this.entity.getElement().getDamageSourceIndirect((Entity)this.entity, (Entity)this.entity.getThrower()), damage);
                    }
                    e.fallDistance = 0.0f;
                }
                else if (e instanceof EntityBaseBall && e != this.entity && ((EntityBaseBall)e).getType() == 10) {
                    if (((EntityBaseBall)e).getThrower() != this.entity.getThrower()) {
                        if (this.entity.worldObj.isRemote || this.rand.nextInt(30) == 0) {}
                        final EntityLightningBolt lightning = new EntityLightningBolt(this.entity.worldObj, this.entity.posX, this.entity.posY - 1.0, this.entity.posZ);
                        this.entity.worldObj.spawnEntityInWorld((Entity)lightning);
                        e.addVelocity(x, y, z);
                    }
                    else if (this.entity.ticksExisted > e.ticksExisted) {
                        this.entity.setDead();
                    }
                    else {
                        final EntityBaseBall entity4 = this.entity;
                        entity4.ticksExisted -= e.ticksExisted / 2;
                    }
                }
                else {
                    e.addVelocity(x, y, z);
                }
            }
        }
        if (this.entity.worldObj.isRemote) {
            Block id = this.entity.worldObj.getBlock((int)this.entity.posX, (int)this.entity.posY - 2, (int)this.entity.posZ);
            if (id == Blocks.air) {
                id = Blocks.glass;
            }
            for (int i = 0; i < 4; ++i) {
                EffectManager.spawnElementParticle(1, this.entity.worldObj, this.entity.posX + this.rand.nextFloat() - 0.5, this.entity.posY + this.rand.nextFloat() - 0.5, this.entity.posZ + this.rand.nextFloat() - 0.5, dist, 0.0, 0.0, this.entity.getElement());
                this.entity.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(id) + "_" + 0, this.entity.posX + this.rand.nextFloat() - 0.5, this.entity.posY + this.rand.nextFloat() - 0.5, this.entity.posZ + this.rand.nextFloat() - 0.5, (double)(this.rand.nextFloat() - 0.5f), (double)(2.0f + this.rand.nextFloat() * 2.0f), (double)(this.rand.nextFloat() - 0.5f));
            }
        }
    }
    
    @Override
    public float getSize() {
        return 0.6f;
    }
    
    @Override
    public float getSizeBB() {
        return 0.01f;
    }
    
    @Override
    public boolean canBounce() {
        return false;
    }
    
    @Override
    public void onSpawn() {
        this.entity.setInmuneToFire(false);
    }
    
    @Override
    public boolean longRange() {
        return false;
    }
}
