package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import com.chocolate.chocolateQuest.items.ItemStaffBase;
import net.minecraft.item.ItemStack;

public class AwakementStaff extends Awakements
{
    public AwakementStaff(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public boolean canBeUsedOnItem(final ItemStack is) {
        return is.getItem() instanceof ItemStaffBase;
    }
    
    @Override
    public boolean canBeAddedByNPC(final int type) {
        return type == EnumEnchantType.STAVES.ordinal();
    }
}
