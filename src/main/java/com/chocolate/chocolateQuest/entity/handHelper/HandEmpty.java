package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandEmpty extends HandHelper
{
    public HandEmpty(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
    }
    
    @Override
    public void onUpdate() {
    }
    
    @Override
    public void attackEntity(final Entity entity) {
    }
    
    @Override
    public boolean attackWithRange(final Entity target, final float f) {
        return false;
    }
}
