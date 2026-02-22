package com.chocolate.chocolateQuest.builder.decorator;

import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.block.Block;
import com.chocolate.chocolateQuest.API.BuilderBlockData;

public class DecoratorRoof
{
    public final int PYRAMID = 0;
    public final int FENCE_IRON_FLOOR = 2;
    public final int TRIANGLE = 5;
    public int floorType;
    public BuilderBlockData fence;
    public Block stairs;
    int texasType;
    int walkableType;
    Random random;
    BuildingProperties properties;
    
    public DecoratorRoof(final Random random, final BuildingProperties properties) {
        this.floorType = 0;
        this.fence = new BuilderBlockData(Blocks.fence);
        this.stairs = Blocks.birch_stairs;
        this.texasType = 0;
        this.walkableType = 0;
        this.random = random;
        this.floorType = random.nextInt(6);
        this.texasType = random.nextInt(2);
        this.walkableType = random.nextInt(4);
        this.properties = properties;
        final int i = random.nextInt(9);
        switch (i) {
            case 0: {
                this.stairs = Blocks.birch_stairs;
                break;
            }
            case 1: {
                this.stairs = Blocks.jungle_stairs;
                break;
            }
            case 2: {
                this.stairs = Blocks.oak_stairs;
                break;
            }
            case 3: {
                this.stairs = Blocks.birch_stairs;
                break;
            }
            case 4: {
                this.stairs = Blocks.spruce_stairs;
                break;
            }
            case 5: {
                this.stairs = Blocks.stone_stairs;
                break;
            }
            case 6: {
                this.stairs = Blocks.brick_stairs;
                break;
            }
            case 7: {
                this.stairs = Blocks.sandstone_stairs;
                break;
            }
            case 8: {
                this.stairs = Blocks.stone_brick_stairs;
                break;
            }
        }
        if (random.nextInt(100) == 0) {
            this.stairs = Blocks.quartz_stairs;
        }
        if (this.floorType == 2) {
            this.fence = new BuilderBlockData(Blocks.iron_bars);
        }
    }
    
    public void generateRoof(final World world, final int x, final int y, final int z, final int sizeX, final int sizeZ, final boolean walkable) {
        if (!walkable && this.random.nextBoolean()) {
            if (this.texasType == 0) {
                this.roofPyramid(world, x, y, z, sizeX, sizeZ, 1000);
            }
            else {
                this.roofTriangle(world, x, y, z, sizeX, sizeZ);
            }
            return;
        }
        switch (this.floorType) {
            case 1: {
                this.fence(world, x, y, z, sizeX, sizeZ, Blocks.fence);
                break;
            }
            case 2: {
                this.fence(world, x, y, z, sizeX, sizeZ, Blocks.iron_bars);
                break;
            }
            case 3: {
                this.roofDoubleSlabsDecorator(world, x, y, z, sizeX, sizeZ, 2);
                break;
            }
            case 4: {
                this.roofDoubleSlabsDecorator(world, x, y, z, sizeX, sizeZ, 1);
                break;
            }
            case 5: {
                if (!walkable) {
                    this.roofTriangle(world, x, y, z, sizeX, sizeZ);
                    break;
                }
                this.addClearRoof(world, x, y, z, sizeX, sizeZ);
                break;
            }
            default: {
                if (!walkable) {
                    this.roofPyramid(world, x, y, z, sizeX, sizeZ, 1000);
                    break;
                }
                this.addClearRoof(world, x, y, z, sizeX, sizeZ);
                break;
            }
        }
    }
    
    public void setRoofBlock(final World world, final int x, final int y, final int z) {
        if (this.floorType == 1 || this.floorType == 2) {
            world.setBlock(x, y, z, this.fence.id);
        }
        else {
            final int ax = Math.abs(x);
            final int az = Math.abs(z);
            if (ax % 2 == az % 2) {
                world.setBlock(x, y, z, this.properties.wallBlock.id);
            }
        }
    }
    
    private void addClearRoof(final World world, final int x, final int y, final int z, final int sizeX, final int sizeZ) {
        switch (this.walkableType) {
            case 1: {
                this.fence(world, x, y, z, sizeX, sizeZ, Blocks.fence);
                break;
            }
            case 2: {
                this.fence(world, x, y, z, sizeX, sizeZ, Blocks.iron_bars);
                break;
            }
            case 3: {
                this.roofDoubleSlabsDecorator(world, x, y, z, sizeX, sizeZ, 2);
                break;
            }
            default: {
                this.roofDoubleSlabsDecorator(world, x, y, z, sizeX, sizeZ, 1);
                break;
            }
        }
    }
    
    private void fence(final World world, final int x, final int y, final int z, final int sizeX, final int sizeZ, final Block block) {
        for (int i = 0; i <= sizeX; ++i) {
            world.setBlock(x + i, y, z, block);
            world.setBlock(x + i, y, z + sizeZ, block);
        }
        for (int k = 0; k <= sizeZ; ++k) {
            world.setBlock(x, y, z + k, block);
            world.setBlock(x + sizeX, y, z + k, block);
        }
    }
    
    public void roofPyramid(final World world, final int x, final int y, final int z, final int sizeX, final int sizeZ, final int height) {
        int minSize = Math.min(sizeX, sizeZ) / 2 + 1;
        minSize = Math.min(height, minSize);
        minSize -= 1 - minSize % 2;
        for (int j = 0; j <= minSize; ++j) {
            world.setBlock(x + j - 1, y + j, z + j - 1, this.stairs, 2, 3);
            world.setBlock(x + sizeX - j + 1, y + j, z + j - 1, this.stairs, 2, 3);
            world.setBlock(x + j - 1, y + j, z + sizeZ - j + 1, this.stairs, 3, 3);
            world.setBlock(x + sizeX - j + 1, y + j, z + sizeZ - j + 1, this.stairs, 3, 3);
            for (int i = j; i <= sizeX - j; ++i) {
                this.properties.setWallBlock(world, x + i, y + j, z + j);
                world.setBlock(x + i, y + j, z + j - 1, this.stairs, 2, 3);
                this.properties.setWallBlock(world, x + i, y + j, z + sizeZ - j);
                world.setBlock(x + i, y + j, z + sizeZ - j + 1, this.stairs, 3, 3);
            }
            for (int k = j; k <= sizeZ - j; ++k) {
                this.properties.setWallBlock(world, x + j, y + j, z + k);
                world.setBlock(x + j - 1, y + j, z + k, this.stairs);
                this.properties.setWallBlock(world, x + sizeX - j, y + j, z + k);
                world.setBlock(x + sizeX - j + 1, y + j, z + k, this.stairs, 1, 3);
            }
        }
    }
    
    private void roofTriangle(final World world, final int x, final int y, final int z, final int sizeX, final int sizeZ) {
        if (sizeX < sizeZ) {
            for (int minSize = sizeX / 2, j = 0; j <= minSize; ++j) {
                for (int k = 0; k <= sizeZ; ++k) {
                    world.setBlock(x + j, y + j, z + k, this.stairs, 0, 3);
                    world.setBlock(x + sizeX - j, y + j, z + k, this.stairs, 1, 3);
                }
                for (int k = j + 1; k < sizeX - j; ++k) {
                    this.properties.setWallBlock(world, x + k, y + j, z);
                    this.properties.setWallBlock(world, x + k, y + j, z + sizeZ);
                }
            }
        }
        else {
            for (int minSize = sizeZ / 2, j = 0; j <= minSize; ++j) {
                for (int k = 0; k <= sizeX; ++k) {
                    world.setBlock(x + k, y + j, z + j, this.stairs, 2, 3);
                    world.setBlock(x + k, y + j, z + sizeZ - j, this.stairs, 3, 3);
                }
                for (int k = j + 1; k < sizeZ - j; ++k) {
                    this.properties.setWallBlock(world, x, y + j, z + k);
                    this.properties.setWallBlock(world, x + sizeX, y + j, z + k);
                }
            }
        }
    }
    
    private void roofDoubleSlabsDecorator(final World world, final int x, final int y, final int z, final int sizeX, final int sizeZ, final int width) {
        for (int i = 0; i < sizeX; i += width * 2) {
            for (int w = 0; w < width; ++w) {
                this.properties.setWallBlock(world, x + i + w, y, z);
                this.properties.setWallBlock(world, x + i + w, y, z + sizeZ);
            }
        }
        for (int k = 0; k <= sizeZ; k += width * 2) {
            for (int w = 0; w < width; ++w) {
                this.properties.setWallBlock(world, x, y, z + k + w);
                this.properties.setWallBlock(world, x + sizeX, y, z + k + w);
            }
        }
    }
}
