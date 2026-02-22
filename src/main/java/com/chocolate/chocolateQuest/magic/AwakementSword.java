package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.swords.ItemBaseSwordDefensive;
import net.minecraft.item.ItemStack;

public class AwakementSword extends Awakements
{
    public AwakementSword(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public boolean canBeUsedOnItem(final ItemStack is) {
        return is.getItem() instanceof ItemBaseSwordDefensive;
    }
}
