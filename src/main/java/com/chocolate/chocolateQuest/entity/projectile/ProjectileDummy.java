package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.util.MovingObjectPosition;

public class ProjectileDummy extends ProjectileBase
{
    EntityBaseBall entity;
    
    public ProjectileDummy(final EntityBaseBall entity) {
        super(entity);
    }
    
    @Override
    public int getTextureIndex() {
        return 0;
    }
    
    @Override
    public void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
    }
}
