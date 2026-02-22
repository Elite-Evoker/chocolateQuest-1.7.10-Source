package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;

public class EntitySlimePart extends EntitySlime
{
    EntityLivingBase owner;
    
    public EntitySlimePart(final World par1World) {
        super(par1World);
    }
    
    public EntitySlimePart(final World par1World, final EntityLivingBase entity, final int size) {
        super(par1World);
        this.owner = entity;
        this.setSlimeSize(size);
        final float health = 3.0f * size;
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)health);
        this.setHealth(health);
        this.experienceValue = 0;
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            if (this.owner != null) {
                this.rotationYaw = (float)(-Math.atan2(this.owner.posX - this.posX, this.owner.posZ - this.posZ) * 180.0 / 3.141592653589793);
                if (!this.owner.isEntityAlive()) {
                    this.setDead();
                }
            }
            else {
                this.setDead();
            }
        }
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.4);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(15.0);
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        return super.attackEntityAsMob(par1Entity);
    }
    
    public void applyEntityCollision(final Entity par1Entity) {
        if (par1Entity instanceof EntitySlimeBoss || par1Entity == this.owner) {
            if (this.ticksExisted > 10) {
                float healScale = 1.0f;
                if (this.owner instanceof EntitySlimeBoss) {
                    healScale = (float)(2 + this.getSlimeSize() / 2);
                }
                ((EntityLivingBase)par1Entity).heal(this.getSlimeSize() * healScale);
                this.setDead();
            }
        }
        else if (par1Entity instanceof EntityLivingBase && par1Entity.getClass() != this.getClass()) {
            final EntityLivingBase el = (EntityLivingBase)par1Entity;
            if (!el.isOnSameTeam((EntityLivingBase)this)) {
                ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 120, 1));
            }
        }
        super.applyEntityCollision(par1Entity);
    }
    
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        return !(par1DamageSource.getEntity() instanceof EntitySlimeBoss) && super.attackEntityFrom(par1DamageSource, par2);
    }
    
    public boolean canAttackClass(final Class par1Class) {
        return super.canAttackClass(par1Class) && par1Class != this.getClass() && par1Class != EntitySlimePart.class;
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
    }
    
    public void setDead() {
        if (!this.worldObj.isRemote && !this.isDead && this.getHealth() <= 0.0f && this.getSlimeSize() > 1) {
            final int size = Math.max(1, this.getSlimeSize() / 2);
            for (int a = 0; a < 2; ++a) {
                final EntitySlimePart part = new EntitySlimePart(this.worldObj, this.owner, size);
                part.setPosition(this.posX, this.posY + 1.0, this.posZ);
                part.motionX = this.rand.nextGaussian();
                part.motionZ = this.rand.nextGaussian();
                this.worldObj.spawnEntityInWorld((Entity)part);
            }
        }
        this.isDead = true;
    }
    
    public Team getTeam() {
        if (this.owner != null) {
            return this.owner.getTeam();
        }
        return super.getTeam();
    }
    
    protected int getAttackStrength() {
        return 0;
    }
}
