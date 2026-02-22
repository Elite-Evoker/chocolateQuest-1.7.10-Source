package com.chocolate.chocolateQuest.magic;

import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;

public class ElementDamageSource
{
    public DamageSource getIndirectDamage(final Entity projectile, final Entity shooter, final String name) {
        return (DamageSource)new EntityDamageSourceIndirect(name, projectile, shooter);
    }
    
    public DamageSource getDamageSource(final Entity shooter, final String name) {
        return (DamageSource)new EntityDamageSource(name, shooter);
    }
    
    public float onHitEntity(final Entity source, final Entity entityHit, final float damage) {
        return damage;
    }
    
    public void onBlockHit(final Entity shooter, final World world, final int x, final int y, final int z) {
        final List<EntityLivingBase> list = world.getEntitiesWithinAABB((Class)EntityLivingBase.class, AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1)));
        for (final EntityLivingBase e : list) {
            e.attackEntityFrom(DamageSource.generic, 1.0f);
        }
    }
    
    public DamageSource getDamageSource(final String name) {
        return new DamageSource(name);
    }
}
