package com.chocolate.chocolateQuest.magic;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;

public class ElementDamageSourceMagic extends ElementDamageSource
{
    @Override
    public DamageSource getIndirectDamage(final Entity projectile, final Entity shooter, final String name) {
        return super.getIndirectDamage(projectile, shooter, name).setMagicDamage();
    }
    
    @Override
    public DamageSource getDamageSource(final Entity shooter, final String name) {
        return super.getDamageSource(shooter, name).setMagicDamage();
    }
    
    @Override
    public DamageSource getDamageSource(final String name) {
        return super.getDamageSource(name).setMagicDamage();
    }
}
