package com.chocolate.chocolateQuest.builder;

import net.minecraft.block.Block;

class BlockData
{
    public int x;
    public int y;
    public int z;
    public Block block;
    public int blockMetadata;
    
    public BlockData(final int x, final int y, final int z, final Block block, final int blockMetada) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.block = block;
        this.blockMetadata = blockMetada;
    }
}
