package com.chocolate.chocolateQuest.API;

import net.minecraft.item.ItemStack;

class WeightedItemStack
{
    public ItemStack stack;
    public int weight;
    
    public WeightedItemStack(final ItemStack is, final int weight) {
        this.stack = is;
        this.weight = weight;
    }
}
