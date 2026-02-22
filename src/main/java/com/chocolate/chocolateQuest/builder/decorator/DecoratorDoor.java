package com.chocolate.chocolateQuest.builder.decorator;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;

public class DecoratorDoor
{
    public static final int WEST = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int NORTH = 3;
    public int width;
    public int height;
    int type;
    final Block BLOCK_FRAME;
    
    public DecoratorDoor(final int width, final int height) {
        this.width = 2;
        this.height = 3;
        this.type = 0;
        this.BLOCK_FRAME = Blocks.brick_block;
        this.width = width;
        this.height = height;
    }
    
    public void setRandomType(final Random random) {
        this.type = random.nextInt(4);
    }
    
    public void generate(final Random random, final World world, final int x, final int y, final int z, final ForgeDirection side) {
        if (this.width == 1) {
            this.generateSquared(random, world, x, y, z, side);
            return;
        }
        switch (this.type) {
            case 0: {
                this.generateSquared(random, world, x, y, z, side);
                break;
            }
            case 1: {
                this.generateArch(random, world, x, y, z, side);
                break;
            }
            case 2: {
                this.generateTriangle(random, world, x, y, z, side);
                break;
            }
            case 3: {
                this.generateFramed(random, world, x, y, z, side);
                break;
            }
            default: {
                this.generateSquared(random, world, x, y, z, side);
                break;
            }
        }
    }
    
    public void generateArch(final Random random, final World world, final int x, final int y, final int z, final ForgeDirection side) {
        final int width = this.width / 2;
        final int uwidth = width - 1 + this.width % 2;
        int archHeight = Math.max(1, this.height - width);
        for (int k = -width; k <= uwidth; ++k) {
            for (int e = -1; e <= 1; ++e) {
                if (k < 0 && k % 2 != 0) {
                    ++archHeight;
                }
                for (int j = 0; j <= this.height; ++j) {
                    int xPos = 0;
                    int zPos = 0;
                    if (side == ForgeDirection.WEST || side == ForgeDirection.EAST) {
                        xPos = x + e;
                        zPos = z + k;
                    }
                    else {
                        xPos = x + k;
                        zPos = z + e;
                    }
                    if (j <= archHeight) {
                        this.setBlockToAir(world, xPos, y + j, zPos);
                    }
                }
                if (k > 0 && k % 2 != 0) {
                    --archHeight;
                }
            }
        }
    }
    
    public void generateTriangle(final Random random, final World world, final int x, final int y, final int z, final ForgeDirection side) {
        final int width = this.width / 2;
        final int uwidth = width + this.width % 2;
        int archHeight = Math.max(1, this.height - width);
        for (int e = -1; e <= 1; ++e) {
            for (int k = -width; k <= uwidth; ++k) {
                for (int j = 0; j <= this.height; ++j) {
                    int xPos = 0;
                    int zPos = 0;
                    if (side == ForgeDirection.WEST || side == ForgeDirection.EAST) {
                        xPos = x + e;
                        zPos = z + k;
                    }
                    else {
                        xPos = x + k;
                        zPos = z + e;
                    }
                    if (j <= archHeight) {
                        this.setBlockToAir(world, xPos, y + j, zPos);
                    }
                }
                if (k < 0) {
                    ++archHeight;
                }
                else {
                    --archHeight;
                }
            }
        }
    }
    
    public void generateSquared(final Random random, final World world, final int x, final int y, final int z, final ForgeDirection side) {
        final int width = this.width / 2;
        final int uwidth = width - 1 + this.width % 2;
        for (int e = -1; e <= 1; ++e) {
            for (int k = -width; k <= uwidth; ++k) {
                for (int j = 0; j <= this.height; ++j) {
                    int xPos = 0;
                    int zPos = 0;
                    if (side == ForgeDirection.WEST || side == ForgeDirection.EAST) {
                        xPos = x + e;
                        zPos = z + k;
                    }
                    else {
                        xPos = x + k;
                        zPos = z + e;
                    }
                    this.setBlockToAir(world, xPos, y + j, zPos);
                }
            }
        }
        if (this.width == 1) {
            int doorSide = 0;
            if (side == ForgeDirection.SOUTH) {
                doorSide = 1;
            }
            else if (side == ForgeDirection.EAST) {
                doorSide = 2;
            }
            else if (side == ForgeDirection.NORTH) {
                doorSide = 3;
            }
            final int orientation = doorSide - 1;
            final boolean open = false;
            if (open) {
                world.setBlock(x, y, z, Blocks.wooden_door, orientation, 2);
                world.setBlock(x, y + 1, z, Blocks.wooden_door, orientation ^ 0x8, 2);
            }
            else {
                world.setBlock(x, y, z, Blocks.wooden_door, orientation ^ 0x4, 2);
                world.setBlock(x, y + 1, z, Blocks.wooden_door, orientation ^ 0x4 ^ 0x8, 2);
            }
        }
    }
    
    public void generateFramed(final Random random, final World world, final int x, final int y, final int z, final ForgeDirection side) {
        final int width = this.width / 2;
        final int uwidth = width + this.width % 2;
        for (int e = -1; e <= 1; ++e) {
            for (int k = -width - 1; k <= uwidth + 1; ++k) {
                for (int j = 0; j <= this.height; ++j) {
                    int xPos = 0;
                    int zPos = 0;
                    if (side == ForgeDirection.WEST || side == ForgeDirection.EAST) {
                        xPos = x + e;
                        zPos = z + k;
                    }
                    else {
                        xPos = x + k;
                        zPos = z + e;
                    }
                    if (j == this.height || k < -width || k > width) {
                        if (e == 0) {
                            world.setBlock(xPos, y + j, zPos, this.BLOCK_FRAME);
                        }
                    }
                    else {
                        this.setBlockToAir(world, xPos, y + j, zPos);
                    }
                }
            }
        }
    }
    
    private void setBlockToAir(final World world, final int x, final int y, final int z) {
        world.setBlock(x, y, z, Blocks.air);
    }
}
