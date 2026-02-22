package com.chocolate.chocolateQuest.magic;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;

public class ElementDamageSourceLight extends ElementDamageSourceMagic
{
    @Override
    public float onHitEntity(final Entity source, final Entity entityHit, final float damage) {
        if (entityHit instanceof EntityLivingBase && ((EntityLivingBase)entityHit).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            return damage * 1.8f;
        }
        return super.onHitEntity(source, entityHit, damage);
    }
}
