package com.chocolate.chocolateQuest.block;

import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockDecoration extends ItemBlock
{
    public ItemBlockDecoration(final Block block) {
        super(block);
        this.setHasSubtypes(true);
    }
    
    public int getMetadata(final int damageValue) {
        return damageValue;
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        return ((BlockDecoration)this.blockInstance).getBlockName(itemstack.getMetadata());
    }
}
