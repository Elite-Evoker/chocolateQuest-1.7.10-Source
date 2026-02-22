package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityCreature;

public class HandLead extends HandHelper
{
    EntityCreature wolf;
    
    public HandLead(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.owner.worldObj.isRemote && this.wolf == null && this.owner.ticksExisted % 100 == 0) {
            EntityWolf closestWolf = null;
            double dist = 256.0;
            final List<Entity> list = this.owner.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this.owner, this.owner.boundingBox.expand(5.0, 1.0, 5.0));
            for (final Entity e : list) {
                if (e instanceof EntityWolf) {
                    final EntityWolf f = (EntityWolf)e;
                    if (f.getLeashed()) {
                        if (f.getLeashedToEntity() != this.owner) {
                            continue;
                        }
                        closestWolf = f;
                        dist = 0.0;
                    }
                    else {
                        if (f.func_152113_b() != "" && !f.func_152113_b().equals(this.owner.func_152113_b())) {
                            continue;
                        }
                        final double currentDist = this.owner.getDistanceSqToEntity((Entity)f);
                        if (currentDist >= dist) {
                            continue;
                        }
                        closestWolf = f;
                        dist = currentDist;
                    }
                }
            }
            if (closestWolf != null) {
                (this.wolf = (EntityCreature)closestWolf).setLeashedToEntity((Entity)this.owner, true);
                if (!closestWolf.isSitting()) {
                    closestWolf.setSitting(false);
                }
            }
        }
        if (this.wolf != null) {
            final EntityLivingBase target = this.owner.getAttackTarget();
            if (target != null) {
                this.wolf.setAttackTarget(target);
            }
            if (!this.wolf.isEntityAlive()) {
                this.wolf = null;
            }
            else if (this.wolf.getLeashedToEntity() != this.owner) {
                this.wolf = null;
            }
        }
    }
    
    @Override
    public void attackEntity(final Entity entity) {
        super.attackEntity(entity);
    }
    
    @Override
    public boolean isTwoHanded() {
        return false;
    }
}
