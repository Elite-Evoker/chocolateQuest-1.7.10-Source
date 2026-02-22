package com.chocolate.chocolateQuest.entity.handHelper;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandHealer extends HandHelper
{
    public HandHealer(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
    }
    
    @Override
    public void onUpdate() {
        final List<Entity> list = this.owner.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this.owner, this.owner.boundingBox.expand(16.0, 1.0, 16.0));
        EntityLivingBase closest = null;
        double dist = 324.0;
        for (final Entity e : list) {
            if (e instanceof EntityLivingBase && this.isEntityApplicable(e)) {
                final double tDist = this.owner.getDistanceSqToEntity(e);
                if (tDist >= dist) {
                    continue;
                }
                closest = (EntityLivingBase)e;
                dist = tDist;
            }
        }
        if (closest != null) {
            this.owner.setAttackTarget(closest);
        }
    }
    
    @Override
    public boolean isHealer() {
        return true;
    }
    
    public boolean isEntityApplicable(final Entity parEntity) {
        if (!(parEntity instanceof EntityLivingBase) || parEntity == this.owner) {
            return false;
        }
        final EntityLivingBase entity = (EntityLivingBase)parEntity;
        return this.owner.isSuitableTargetAlly(entity) && entity.getHealth() < entity.getMaxHealth();
    }
}
