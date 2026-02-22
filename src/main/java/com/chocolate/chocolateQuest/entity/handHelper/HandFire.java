package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandFire extends HandHelper
{
    public HandFire(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
    }
    
    @Override
    public void attackEntity(final Entity entity) {
        entity.setFire(4);
        super.attackEntity(entity);
    }
}
