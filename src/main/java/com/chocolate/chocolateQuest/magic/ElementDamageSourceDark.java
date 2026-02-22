package com.chocolate.chocolateQuest.magic;

import net.minecraft.util.DamageSource;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;

public class ElementDamageSourceDark extends ElementDamageSource
{
    @Override
    public float onHitEntity(final Entity source, final Entity entityHit, float damage) {
        final int fireDamage = (int)(damage / 4.0f);
        if (fireDamage >= 1) {
            damage -= fireDamage;
            ((EntityLivingBase)entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, fireDamage * 60, 0));
        }
        return damage;
    }
    
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
