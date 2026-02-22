package com.chocolate.chocolateQuest.magic;

import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;

public class ElementsHelper
{
    public static float getAmmountDecreased(final IElementWeak entity, float damage, final DamageSource ds) {
        if (ds.isFireDamage() && entity.getFireDefense() != 0) {
            final int level = entity.getFireDefense();
            final float modifier = 1.25f;
            final int elementResist = getResistance(level, modifier);
            damage -= (float)calculateReduction(damage, elementResist);
        }
        if (ds.isMagicDamage() && entity.getMagicDefense() != 0) {
            final int level = entity.getMagicDefense();
            final float modifier = 1.5f;
            final int elementResist = getResistance(level, modifier);
            damage -= (float)calculateReduction(damage, elementResist);
        }
        if (ds.isExplosion() && entity.getBlastDefense() != 0) {
            final int level = entity.getBlastDefense();
            final float modifier = 1.5f;
            final int elementResist = getResistance(level, modifier);
            damage -= (float)calculateReduction(damage, elementResist);
        }
        if (!ds.isFireDamage() && !ds.isMagicDamage() && !ds.isExplosion() && entity.getPhysicDefense() != 0) {
            final int level = entity.getPhysicDefense();
            final float modifier = 1.25f;
            final int elementResist = getResistance(level, modifier);
            damage -= (float)calculateReduction(damage, elementResist);
        }
        if (ds.isProjectile() && entity.getProjectileDefense() != 0) {
            final int level = entity.getProjectileDefense();
            final float modifier = 1.5f;
            final int elementResist = getResistance(level, modifier);
            damage -= (float)calculateReduction(damage, elementResist);
        }
        return damage;
    }
    
    public static double calculateReduction(final double damage, int elementResist) {
        elementResist = Math.min(elementResist, 25) * 4;
        return damage * elementResist / 100.0;
    }
    
    public static int getResistance(final int level, final float modifier) {
        return MathHelper.floor_float((6 + level ^ 0x2) * modifier / 3.0f);
    }
}
