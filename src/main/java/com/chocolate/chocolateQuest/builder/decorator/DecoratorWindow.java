package com.chocolate.chocolateQuest.builder.decorator;

import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.block.Block;

public class DecoratorWindow
{
    int floorType;
    Block window;
    public int height;
    public int width;
    int separation;
    BuildingProperties properties;
    
    public DecoratorWindow(final Random random, final BuildingProperties properties) {
        this.floorType = 0;
        this.window = Blocks.glass_pane;
        this.height = 1;
        this.width = 1;
        this.separation = 3;
        this.floorType = random.nextInt(4);
        if (random.nextInt(4) == 0) {
            this.window = Blocks.glass;
        }
        else if (random.nextInt(4) == 0) {
            this.window = Blocks.iron_bars;
        }
        this.width = 1 + random.nextInt(4);
        this.separation = 1 + random.nextInt(5);
        this.height = 1 + random.nextInt(3);
        this.properties = properties;
    }
    
    public void generateWindowX(final World world, final int x, final int y, final int z) {
        this.generateWindow(world, x, y, z, true);
    }
    
    public void generateWindowZ(final World world, final int x, final int y, final int z) {
        this.generateWindow(world, x, y, z, false);
    }
    
    public void generateWindow(final World world, final int x, final int y, final int z, final boolean aroundX) {
        switch (this.floorType) {
            case 1: {
                if (this.width < 2) {
                    this.simple(world, x, y, z, aroundX);
                    break;
                }
                this.framed(world, x, y, z, aroundX);
                break;
            }
            case 2: {
                this.open(world, x, y, z, aroundX);
                break;
            }
            default: {
                this.simple(world, x, y, z, aroundX);
                break;
            }
        }
    }
    
    public void simple(final World world, final int x, final int y, final int z, final boolean aroundX) {
        int pos = Math.abs(aroundX ? x : z);
        final int posX = 0;
        final int posZ = 0;
        pos %= this.separation + this.width;
        for (int i = 1; i < 1 + this.height; ++i) {
            if (pos < this.width) {
                world.setBlock(x, y + i, z, this.window);
            }
        }
    }
    
    public void open(final World world, final int x, final int y, final int z, final boolean aroundX) {
        int pos = Math.abs(aroundX ? x : z);
        final int posX = 0;
        final int posZ = 0;
        pos %= this.separation + this.width;
        if (pos < this.width) {
            world.setBlockToAir(x, y + 1, z);
            world.setBlock(x, y + 2, z, this.window);
        }
    }
    
    public void framed(final World world, final int x, final int y, final int z, final boolean aroundX) {
        int pos = Math.abs(aroundX ? x : z);
        final int posX = 0;
        final int posZ = 0;
        pos %= this.separation + this.width;
        for (int i = 1; i < 1 + this.height; ++i) {
            if (pos == this.width || pos == 0) {
                this.properties.setWallBlock(world, x, y + i, z);
            }
            else if (pos < this.width) {
                world.setBlock(x, y + i, z, this.window);
            }
        }
    }
}
