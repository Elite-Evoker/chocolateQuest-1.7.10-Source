package com.chocolate.chocolateQuest.builder;

import net.minecraft.world.biome.BiomeGenBase;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.API.BuilderBlockData;

public class BuilderBlockDataBiomeTop extends BuilderBlockData
{
    public BuilderBlockDataBiomeTop() {
        super((Block)Blocks.grass);
    }
    
    @Override
    public void placeBlock(final World world, final int x, final int y, final int z, final Random random) {
        final BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
        world.setBlock(x, y, z, biome.topBlock, biome.topBlockMetadata, 3);
    }
}
