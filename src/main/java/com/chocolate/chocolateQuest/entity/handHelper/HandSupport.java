package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandSupport extends HandHelper
{
    public HandSupport(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
    }
    
    @Override
    public void onUpdate() {
        if (this.owner.ticksExisted % 100 == 0) {
            final List list = this.owner.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this.owner, this.owner.boundingBox.expand(10.0, 4.0, 10.0));
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity1 = list.get(j);
                if (entity1 instanceof EntityLivingBase && this.owner.isOnSameTeam((EntityLivingBase)entity1)) {
                    ((EntityLivingBase)entity1).addPotionEffect(this.getPotionEffect());
                }
            }
        }
        super.onUpdate();
    }
    
    public PotionEffect getPotionEffect() {
        return new PotionEffect(Potion.resistance.id, 101, 0);
    }
}
