package com.chocolate.chocolateQuest.items;

import net.minecraft.item.ItemStack;

public interface IHookLauncher
{
    int getHookID(final ItemStack p0);
    
    void setHookID(final ItemStack p0, final int p1);
}
