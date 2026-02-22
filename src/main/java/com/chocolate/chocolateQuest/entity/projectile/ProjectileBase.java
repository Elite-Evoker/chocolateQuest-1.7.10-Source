package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;

public abstract class ProjectileBase
{
    EntityBaseBall entity;
    public static final byte POISONBALL = 0;
    public static final byte BULLETPISTOL = 1;
    public static final byte BULLETPISTOLGOLEM = 2;
    public static final byte QUAKE = 3;
    public static final byte QUAKEAREA = 4;
    public static final byte FIREBALL = 5;
    public static final byte FIREFALLING = 6;
    public static final byte BUBBLE = 7;
    public static final byte VAMPIRIC = 8;
    public static final byte HEALBALL = 9;
    public static final byte TORNADO = 10;
    public static final byte ROCKET = 11;
    public static final byte MAGIC = 100;
    public static final byte MAGIC_AIM = 101;
    public static final byte MAGIC_AREA = 102;
    public static final byte MAGIC_SHIELD = 103;
    public static final byte MAGIC_STORM = 104;
    public static final byte MAGIC_STORM_PROJECTILE = 105;
    
    public ProjectileBase(final EntityBaseBall entity) {
        this.entity = entity;
    }
    
    public void onUpdateInAir() {
    }
    
    public abstract int getTextureIndex();
    
    public abstract void onImpact(final MovingObjectPosition p0);
    
    public boolean canBounce() {
        return false;
    }
    
    public float getSize() {
        return 1.0f;
    }
    
    public float getSizeBB() {
        return this.getSize();
    }
    
    public void onSpawn() {
    }
    
    public void onDead() {
    }
    
    public float getGravityVelocity() {
        return 0.0f;
    }
    
    public int getMaxLifeTime() {
        return 200;
    }
    
    public int getRopeColor() {
        return -1;
    }
    
    public void attackFrom(final DamageSource d, final float damage) {
    }
    
    public double getYOffset() {
        return 0.0;
    }
    
    public boolean longRange() {
        return true;
    }
    
    public float getZOffset() {
        return 0.0f;
    }
    
    public static ProjectileBase getBallData(final EntityBaseBall entity) {
        final int type = entity.getType();
        switch (type) {
            case 0: {
                return new ProjectilePoisonBall(entity);
            }
            case 1: {
                return new ProjectileBulletPistol(entity);
            }
            case 2: {
                return new ProjectileBulletGolem(entity);
            }
            case 3: {
                return new ProjectileEarthQuake(entity);
            }
            case 4: {
                return new ProjectileEarthQuakeArea(entity);
            }
            case 5: {
                return new ProjectileFireBall(entity);
            }
            case 6: {
                return new ProjectileFireFalling(entity);
            }
            case 7: {
                return new ProjectileBubble(entity);
            }
            case 8: {
                return new ProjectileVapiricBall(entity);
            }
            case 9: {
                return new ProjectileHealBall(entity);
            }
            case 10: {
                return new ProjectileWindBall(entity);
            }
            case 11: {
                return new ProjectileRocket(entity);
            }
            case 100: {
                return new ProjectileMagic(entity);
            }
            case 101: {
                return new ProjectileMagicAimed(entity);
            }
            case 102: {
                return new ProjectileMagicArea(entity);
            }
            case 103: {
                return new ProjectileMagicShield(entity);
            }
            case 104: {
                return new ProjectileMagicStorm(entity);
            }
            case 105: {
                return new ProjectileMagicStormProjectile(entity);
            }
            default: {
                return new ProjectileDummy(entity);
            }
        }
    }
}
