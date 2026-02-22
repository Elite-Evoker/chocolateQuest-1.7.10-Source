package com.chocolate.chocolateQuest.builder;

import java.util.Random;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import java.util.Properties;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.API.BuilderBase;

public class BuilderEmptyCave extends BuilderBase
{
    BuilderBlockData caveBlock;
    int size;
    int height;
    int posY;
    int boss;
    String spawnBoss;
    boolean inverted;
    int borderWidth;
    World world;
    
    public BuilderEmptyCave() {
        this.caveBlock = new BuilderBlockData(Blocks.air);
        this.size = 48;
        this.height = 28;
        this.boss = 0;
        this.spawnBoss = "no";
        this.inverted = false;
        this.borderWidth = 0;
    }
    
    @Override
    public BuilderBase load(final Properties prop) {
        this.caveBlock = HelperReadConfig.getBlock(prop, "caveBlock", this.caveBlock);
        this.size = HelperReadConfig.getIntegerProperty(prop, "caveSize", 32);
        this.height = HelperReadConfig.getIntegerProperty(prop, "caveHeight", 28);
        this.posY = HelperReadConfig.getIntegerProperty(prop, "caveY", 20);
        this.inverted = HelperReadConfig.getBooleanProperty(prop, "inverted", this.inverted);
        return this;
    }
    
    @Override
    public void generate(final Random random, final World world, final int i, final int j, final int mob) {
        this.generate(random, world, i, this.posY, j, mob);
    }
    
    @Override
    public String getName() {
        return "emptyCave";
    }
    
    @Override
    public void generate(final Random random, final World world, final int i, final int j, final int k, final int idMob) {
        this.generate(random, world, i, j, k, this.size, this.size, this.height);
    }
    
    public void generate(final Random random, final World world, int i, final int j, int k, int sizeX, int sizeZ, final int height) {
        i -= this.borderWidth;
        k -= this.borderWidth;
        sizeX += this.borderWidth * 2;
        sizeZ += this.borderWidth * 2;
        final int width = 32;
        final int rooms = Math.max(1, width / 8);
        final Perlin3D p = new Perlin3D(world.getSeed(), 8, random);
        final Perlin3D p2 = new Perlin3D(world.getSeed(), 32, random);
        final int wallSize = 3 + this.borderWidth;
        final int freq = 1;
        for (int x = 0; x < sizeX; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < sizeZ; ++z) {
                    float noiseVar = Math.max(0.0f, 3.0f - y / 2.0f);
                    noiseVar += Math.max(0.0f, 4.0f - (height - y + 1) / 2.0f);
                    noiseVar += Math.max(0, wallSize - x / freq);
                    noiseVar += Math.max(0, wallSize - (sizeX - x - 1) / freq);
                    noiseVar += Math.max(0, wallSize - z / freq);
                    noiseVar += Math.max(0, wallSize - (sizeZ - z - 1) / freq);
                    final double noiseValue = p.getNoiseAt(x + i, y + j, z + k) * p2.getNoiseAt(x + i, y + j, z + k) * noiseVar;
                    if (this.inverted) {
                        if (noiseValue > 0.5) {
                            this.caveBlock.placeBlock(world, i + x, j + y, k + z, random);
                        }
                    }
                    else if (noiseValue < 0.5) {
                        this.caveBlock.placeBlock(world, i + x, j + y, k + z, random);
                    }
                }
            }
        }
    }
}
