package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.util.DamageSource;
import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileMagic extends ProjectileBase
{
    public static final int PHYSIC = 0;
    public static final int MAGIC = 1;
    public static final int BLAST = 2;
    public static final int FIRE = 3;
    int type;
    int damageCounter;
    
    public ProjectileMagic(final EntityBaseBall entity) {
        super(entity);
        this.damageCounter = 0;
        this.type = entity.getElement().ordinal();
    }
    
    @Override
    public int getTextureIndex() {
        if (this.type == 2) {
            return -1;
        }
        return 247 + this.type - ((this.entity.ticksExisted / 4 % 2 == 0) ? 0 : 16);
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
                    final Elements element = this.entity.getElement();
                    final DamageSource ds = this.getDamageSource().setProjectile();
                    float damage = 4.0f * this.entity.getDamageMultiplier();
                    damage = element.onHitEntity((Entity)this.entity.getThrower(), e, damage);
                    if (e.attackEntityFrom(ds, damage)) {
                        ++this.damageCounter;
                    }
                }
            }
            else {
                this.entity.setDead();
            }
        }
    }
    
    @Override
    public void onUpdateInAir() {
        if (this.damageCounter > this.entity.getlvl()) {
            this.entity.setDead();
        }
        super.onUpdateInAir();
    }
    
    protected DamageSource getDamageSource() {
        Elements element = null;
        switch (this.type) {
            case 1: {
                element = Elements.magic;
                break;
            }
            case 2: {
                element = Elements.blast;
                break;
            }
            case 3: {
                element = Elements.fire;
                break;
            }
            default: {
                element = Elements.physic;
                break;
            }
        }
        return element.getDamageSourceIndirect((Entity)this.entity.shootingEntity, (Entity)this.entity);
    }
    
    @Override
    public float getGravityVelocity() {
        return 0.0f;
    }
    
    @Override
    public float getSize() {
        return 0.8f;
    }
}
