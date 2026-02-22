package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import net.minecraft.item.ItemStack;

public class AwakementSpear extends Awakements
{
    public AwakementSpear(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public boolean canBeUsedOnItem(final ItemStack is) {
        return is.getItem() instanceof ItemBaseSpear;
    }
}
