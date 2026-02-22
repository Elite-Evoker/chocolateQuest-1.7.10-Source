package com.chocolate.chocolateQuest.entity.projectile;

public class ProjectileBulletGolem extends ProjectileBulletPistol
{
    public ProjectileBulletGolem(final EntityBaseBall entity) {
        super(entity);
    }
    
    @Override
    protected int getBulletBaseDamage() {
        return 8;
    }
    
    @Override
    public float getSize() {
        if (this.entity.getlvl() >= 4) {
            return 0.6f;
        }
        return 0.3f;
    }
    
    @Override
    public boolean canBounce() {
        return false;
    }
    
    @Override
    public float getBulletPitch() {
        return 0.8f;
    }
}
