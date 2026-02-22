package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import net.minecraft.item.ItemStack;

public class AwakementDagger extends Awakements
{
    public AwakementDagger(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public boolean canBeUsedOnItem(final ItemStack is) {
        return is.getItem() instanceof ItemBaseDagger;
    }
}
