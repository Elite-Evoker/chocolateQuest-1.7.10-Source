package com.chocolate.chocolateQuest.builder;

import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import java.util.Properties;
import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.API.BuilderBase;

public class BuilderNether extends BuilderBase
{
    String folder;
    int rows;
    int posY;
    BuilderBlockData floorBlock;
    BuilderBlockData lavaBlock;
    boolean replaceBanners;
    
    public BuilderNether() {
        this.folder = "pigcity";
        this.rows = 4;
        this.posY = 31;
        this.lavaBlock = new BuilderBlockData(Blocks.lava);
        this.replaceBanners = true;
    }
    
    @Override
    public BuilderBase load(final Properties prop) {
        this.rows = HelperReadConfig.getIntegerProperty(prop, "rows", 4);
        if (this.rows < 1) {
            this.rows = 1;
        }
        if (this.rows > 20) {
            this.rows = 20;
        }
        this.posY = HelperReadConfig.getIntegerProperty(prop, "posY", 31);
        this.floorBlock = HelperReadConfig.getBlock(prop, "floorBlock", new BuilderBlockData(Blocks.nether_brick));
        this.lavaBlock = HelperReadConfig.getBlock(prop, "lavaBlock", this.lavaBlock);
        this.folder = prop.getProperty("folder");
        if (this.folder == null) {
            this.folder = "pigcity";
        }
        this.replaceBanners = HelperReadConfig.getBooleanProperty(prop, "replaceBanners", this.replaceBanners);
        return super.load(prop);
    }
    
    @Override
    public String getName() {
        return "lavaCity";
    }
    
    @Override
    public void generate(final Random random, final World world, final int x, final int z, final int mob) {
        this.generate(random, world, x, this.posY, z, mob);
    }
    
    @Override
    public void generate(final Random random, final World world, final int i, final int j, final int k, final int mob) {
        final Perlin3D p = new Perlin3D(world.getSeed(), 8, random);
        final Perlin3D p2 = new Perlin3D(world.getSeed(), 32, random);
        final int rowSize = 21;
        final int size = this.rows * rowSize;
        final int height = 32;
        final int wallSize = 4;
        for (int x = 0; x < size; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < size; ++z) {
                    float noiseVar = Math.max(0.0f, 2.0f - (height - y) / 4.0f);
                    noiseVar += Math.max(0.0f, wallSize - x / 2.0f);
                    noiseVar += Math.max(0.0f, wallSize - (size - x) / 2.0f);
                    noiseVar += Math.max(0.0f, wallSize - z / 2.0f);
                    noiseVar += Math.max(0.0f, wallSize - (size - z) / 2.0f);
                    if (p.getNoiseAt(x + i, y + j, z + k) * p2.getNoiseAt(x + i, y + j, z + k) * noiseVar < 0.5) {
                        world.setBlockToAir(i + x, j + y, k + z);
                    }
                }
            }
        }
        final BuilderHelper b = BuilderHelper.builderHelper;
        final int[][][] map = new int[size][1][size];
        for (int x2 = 0; x2 < map.length; ++x2) {
            for (int z2 = 0; z2 < map.length; ++z2) {
                if ((x2 % 21 <= 2 || x2 % 21 >= 18 || z2 % 21 <= 2 || z2 % 21 >= 18) && (x2 % 21 <= 8 || x2 % 21 >= 12) && (z2 % 21 <= 8 || z2 % 21 >= 12)) {
                    this.lavaBlock.placeBlock(world, i + x2, j, k + z2, random);
                }
                else {
                    this.floorBlock.placeBlock(world, i + x2, j, k + z2, random);
                }
            }
        }
        for (int x2 = 0; x2 < map.length; x2 += 21) {
            for (int z2 = 0; z2 < map.length; z2 += 21) {
                b.putSchematicInWorld(random, world, BuilderHelper.getRandomNBTMap(this.folder, random), x2 + 3 + i, j + 1, z2 + 3 + k, mob, this.replaceBanners);
            }
        }
    }
}
