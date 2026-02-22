package com.chocolate.chocolateQuest.magic;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;

public class ElementDamageSourceBlast extends ElementDamageSource
{
    @Override
    public DamageSource getIndirectDamage(final Entity projectile, final Entity shooter, final String name) {
        return super.getIndirectDamage(projectile, shooter, name).setExplosion();
    }
    
    @Override
    public DamageSource getDamageSource(final Entity shooter, final String name) {
        return super.getDamageSource(shooter, name).setExplosion();
    }
}
