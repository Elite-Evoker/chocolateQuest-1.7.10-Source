package com.chocolate.chocolateQuest.API;

import net.minecraft.world.biome.BiomeGenMushroomIsland;
import net.minecraft.world.biome.BiomeGenHell;
import net.minecraft.world.biome.BiomeGenDesert;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBeach;
import net.minecraft.world.biome.BiomeGenSwamp;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;

public class BuilderBlockData
{
    public Block id;
    public int metadata;
    
    public BuilderBlockData(final Block block) {
        this.metadata = 0;
        this.id = block;
        this.metadata = 0;
    }
    
    public BuilderBlockData(final Block id, final int metadata) {
        this.metadata = 0;
        this.id = id;
        this.metadata = metadata;
    }
    
    public void placeBlock(final World world, final int x, final int y, final int z, final Random random) {
        world.setBlock(x, y, z, this.id, this.metadata, 3);
    }
    
    public static BuilderBlockData getRoadBlockForBiome(final BiomeGenBase biome) {
        if (biome instanceof BiomeGenOcean || biome instanceof BiomeGenRiver || biome instanceof BiomeGenSwamp || biome instanceof BiomeGenBeach) {
            return new BuilderBlockData(Blocks.planks);
        }
        return new BuilderBlockData(Blocks.gravel);
    }
    
    public static BuilderBlockData getGroundBlockForBiome(final BiomeGenBase biome) {
        if (biome instanceof BiomeGenDesert) {
            return new BuilderBlockData((Block)Blocks.sand);
        }
        if (biome instanceof BiomeGenHell) {
            return new BuilderBlockData(Blocks.netherrack);
        }
        if (biome instanceof BiomeGenMushroomIsland) {
            return new BuilderBlockData((Block)Blocks.mycelium);
        }
        return new BuilderBlockData((Block)Blocks.grass);
    }
    
    public static BuilderBlockData getWallBlockForBiome(final BiomeGenBase biome) {
        if (biome instanceof BiomeGenDesert) {
            return new BuilderBlockData(Blocks.sandstone);
        }
        if (biome instanceof BiomeGenHell) {
            return new BuilderBlockData(Blocks.nether_brick);
        }
        return new BuilderBlockData(Blocks.stonebrick);
    }
    
    public static BuilderBlockData getRandomWallBlock(final Random random) {
        Block ret = Blocks.stonebrick;
        if (random.nextInt(500) == 0) {
            ret = Blocks.quartz_block;
        }
        if (random.nextInt(20) == 0) {
            ret = Blocks.nether_brick;
        }
        else if (random.nextInt(10) == 0) {
            ret = Blocks.sandstone;
        }
        else if (random.nextInt(8) == 0) {
            ret = Blocks.brick_block;
        }
        if (random.nextInt(10) == 0) {
            ret = Blocks.cobblestone;
        }
        if (random.nextInt(10) == 0) {
            ret = Blocks.log;
        }
        return new BuilderBlockData(ret, 0);
    }
}
