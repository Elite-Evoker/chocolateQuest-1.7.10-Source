package com.chocolate.chocolateQuest.magic;

import net.minecraft.util.DamageSource;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;

public class ElementDamageSourceFire extends ElementDamageSource
{
    @Override
    public float onHitEntity(final Entity source, final Entity entityHit, float damage) {
        final float fireDamage = damage / 4.0f;
        if (fireDamage >= 1.0f) {
            damage -= fireDamage;
            entityHit.setFire((int)fireDamage + 1);
        }
        return damage;
    }
    
    @Override
    public void onBlockHit(final Entity shooter, final World world, final int x, final int y, final int z) {
        if (world.getBlock(x, y, z) == Blocks.air) {
            world.setBlock(x, y, z, (Block)Blocks.fire);
        }
    }
    
    @Override
    public DamageSource getIndirectDamage(final Entity projectile, final Entity shooter, final String name) {
        return super.getIndirectDamage(projectile, shooter, name).setFireDamage();
    }
    
    @Override
    public DamageSource getDamageSource(final Entity shooter, final String name) {
        return super.getDamageSource(shooter, name).setFireDamage();
    }
}
