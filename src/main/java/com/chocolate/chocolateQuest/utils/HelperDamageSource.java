package com.chocolate.chocolateQuest.utils;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;

public class HelperDamageSource
{
    public static DamageSource getBulletDamage(final Entity entityBullet, final Entity entity) {
        return new EntityDamageSourceIndirect("arrow", entityBullet, entity).setProjectile();
    }
    
    public static DamageSource causeProjectilePhysicalDamage(final Entity projectile, final Entity shooter) {
        return new EntityDamageSourceIndirect("generic", projectile, shooter).setMagicDamage().setProjectile();
    }
    
    public static DamageSource causeIndirectMagicDamage(final Entity projectile, final Entity shooter) {
        return new EntityDamageSourceIndirect("magic", projectile, shooter).setMagicDamage().setProjectile();
    }
    
    public static DamageSource causeFireProjectileDamage(final Entity projectile, final Entity shooter) {
        return new EntityDamageSourceIndirect("fire", projectile, shooter).setProjectile().setFireDamage();
    }
    
    public static DamageSource causeFireDamage(final Entity shooter) {
        return new EntityDamageSource(DamageSource.inFire.damageType, shooter).setFireDamage();
    }
    
    public static boolean attackEntityWithoutKnockBack(final Entity entity, final DamageSource ds, final float damage) {
        boolean damaged;
        if (entity instanceof EntityLivingBase) {
            final AttributeModifier kbMod = new AttributeModifier("TemKBResist", 1.0, 0);
            ((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(kbMod);
            damaged = entity.attackEntityFrom(ds, damage);
            ((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(kbMod);
        }
        else {
            damaged = entity.attackEntityFrom(ds, damage);
        }
        return damaged;
    }
}
