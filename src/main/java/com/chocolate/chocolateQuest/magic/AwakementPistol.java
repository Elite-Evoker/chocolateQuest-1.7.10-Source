package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import com.chocolate.chocolateQuest.items.gun.ItemPistol;
import net.minecraft.item.ItemStack;

public class AwakementPistol extends Awakements
{
    int maxLevel;
    
    public AwakementPistol(final String name, final int icon) {
        this(name, icon, 4);
    }
    
    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }
    
    public AwakementPistol(final String name, final int icon, final int maxLevel) {
        super(name, icon);
        this.maxLevel = 4;
        this.maxLevel = maxLevel;
    }
    
    @Override
    public boolean canBeUsedOnItem(final ItemStack is) {
        return is.getItem() instanceof ItemPistol;
    }
    
    @Override
    public boolean canBeAddedByNPC(final int type) {
        return type == EnumEnchantType.GUNSMITH.ordinal();
    }
}
