package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandSpear extends HandHelper
{
    public HandSpear(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
    }
    
    @Override
    public double getAttackRangeBonus() {
        return 1.0;
    }
}
