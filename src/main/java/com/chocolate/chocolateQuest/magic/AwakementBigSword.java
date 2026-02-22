package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.swords.ItemBaseBroadSword;
import net.minecraft.item.ItemStack;

public class AwakementBigSword extends Awakements
{
    public AwakementBigSword(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public boolean canBeUsedOnItem(final ItemStack is) {
        return is.getItem() instanceof ItemBaseBroadSword;
    }
}
