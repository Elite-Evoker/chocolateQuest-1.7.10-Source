package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import java.util.Comparator;

class NearestAttackableTargetSorter implements Comparator<Entity>
{
    private EntityLivingBase targetEntity;
    
    public NearestAttackableTargetSorter(final EntityLivingBase owner) {
        this.targetEntity = owner;
    }
    
    @Override
    public int compare(final Entity o1, final Entity o2) {
        final double dist1 = o1.getDistanceSqToEntity((Entity)this.targetEntity);
        final double dist2 = o2.getDistanceSqToEntity((Entity)this.targetEntity);
        return (dist1 < dist2) ? -1 : ((dist1 == dist2) ? 0 : 1);
    }
}
