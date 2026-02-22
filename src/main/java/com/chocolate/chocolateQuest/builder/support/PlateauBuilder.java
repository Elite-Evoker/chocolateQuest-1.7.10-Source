package com.chocolate.chocolateQuest.builder.support;

import com.chocolate.chocolateQuest.builder.Perlin3D;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import java.util.Properties;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.API.BuilderBlockData;

public class PlateauBuilder
{
    BuilderBlockData structureBlock;
    BuilderBlockData structureTopBlock;
    
    public PlateauBuilder() {
        this.structureBlock = new BuilderBlockData(Blocks.dirt);
        this.structureTopBlock = new BuilderBlockData((Block)Blocks.grass);
    }
    
    public void load(final Properties prop) {
        this.structureBlock = HelperReadConfig.getBlock(prop, "structureBlock", new BuilderBlockData(Blocks.dirt));
        this.structureTopBlock = HelperReadConfig.getBlock(prop, "structureTopBlock", new BuilderBlockData((Block)Blocks.grass));
    }
    
    public void generate(final Random random, final World world, int i, final int j, int k, int sizeX, int sizeZ) {
        final Perlin3D p = new Perlin3D(world.getSeed(), 8, random);
        final Perlin3D p2 = new Perlin3D(world.getSeed(), 32, random);
        final int wallSize = 8;
        sizeX += wallSize * 2;
        sizeZ += wallSize * 2;
        final int height = 32;
        i -= wallSize;
        k -= wallSize;
        for (int x = 0; x < sizeX; ++x) {
            for (int z = 0; z < sizeZ; ++z) {
                int maxHeight = j - 1 - world.getTopSolidOrLiquidBlock(x + i, z + k);
                final int posY = world.getTopSolidOrLiquidBlock(x + i, z + k);
                for (int y = 0; y <= maxHeight; ++y) {
                    if (x > wallSize && z > wallSize && x < sizeX - wallSize && z < sizeZ - wallSize) {
                        this.structureBlock.placeBlock(world, i + x, posY + y, k + z, random);
                    }
                    else {
                        float noiseVar = (y - maxHeight) / (Math.max(1, maxHeight) * 1.5f);
                        final int tWallSize = wallSize;
                        noiseVar += Math.max(0.0f, (tWallSize - x) / 8.0f);
                        noiseVar += Math.max(0.0f, (tWallSize - (sizeX - x)) / 8.0f);
                        noiseVar += Math.max(0.0f, (tWallSize - z) / 8.0f);
                        noiseVar += Math.max(0.0f, (tWallSize - (sizeZ - z)) / 8.0f);
                        final double value = (p.getNoiseAt(x + i, y, z + k) + p2.getNoiseAt(x + i, y, z + k) + noiseVar) / 3.0;
                        if (value < 0.5) {
                            this.structureBlock.placeBlock(world, i + x, posY + y, k + z, random);
                        }
                    }
                }
                maxHeight = world.getTopSolidOrLiquidBlock(x + i, z + k);
                if (maxHeight <= j) {
                    this.structureTopBlock.placeBlock(world, i + x, maxHeight - 1, k + z, random);
                }
            }
        }
    }
}
