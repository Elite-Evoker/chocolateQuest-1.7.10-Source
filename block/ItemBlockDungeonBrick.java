package com.chocolate.chocolateQuest.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockDungeonBrick extends ItemBlock
{
    public ItemBlockDungeonBrick(final Block block) {
        super(block);
        this.setHasSubtypes(true);
    }
    
    public int getMetadata(final int damageValue) {
        return damageValue;
    }
}
