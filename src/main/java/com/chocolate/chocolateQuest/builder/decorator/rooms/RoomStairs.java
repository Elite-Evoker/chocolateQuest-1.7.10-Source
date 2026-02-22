package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomStairs extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        final int widthStairs = Math.max(2, Math.min(this.sizeX, this.sizeZ) / 4);
        final int roomHeight = this.properties.floorHeight;
        if (side == 5) {
            for (int j = 0; j <= roomHeight; ++j) {
                for (int i = -widthStairs + 1; i < widthStairs; ++i) {
                    for (int k = -widthStairs + 1; k < widthStairs; ++k) {
                        world.setBlockToAir(x + i, y + j, z + k);
                    }
                }
                this.properties.wallBlock.placeBlock(world, x, y + j, z, random);
            }
            final Block stairBlock = Blocks.stone_brick_stairs;
            for (int l = 0; l <= roomHeight; ++l) {
                int step = 1;
                final int direction = (y + l) % 4;
                if (direction == 0) {
                    for (step = 1; step < widthStairs; ++step) {
                        world.setBlock(x, y + l, z + step, stairBlock, 0, 3);
                        for (int step2 = 1; step2 < widthStairs; ++step2) {
                            this.properties.wallBlock.placeBlock(world, x + step, y + l, z + step2, random);
                        }
                    }
                }
                if (direction == 1) {
                    for (step = 1; step < widthStairs; ++step) {
                        world.setBlock(x + step, y + l, z, stairBlock, 3, 3);
                        for (int step2 = 1; step2 < widthStairs; ++step2) {
                            this.properties.wallBlock.placeBlock(world, x + step2, y + l, z - step, random);
                        }
                    }
                }
                if (direction == 2) {
                    for (step = 1; step < widthStairs; ++step) {
                        world.setBlock(x, y + l, z - step, stairBlock, 1, 3);
                        for (int step2 = 1; step2 < widthStairs; ++step2) {
                            this.properties.wallBlock.placeBlock(world, x - step, y + l, z - step2, random);
                        }
                    }
                }
                if (direction == 3) {
                    for (step = 1; step < widthStairs; ++step) {
                        world.setBlock(x - step, y + l, z, stairBlock, 2, 3);
                        for (int step2 = 1; step2 < widthStairs; ++step2) {
                            this.properties.wallBlock.placeBlock(world, x - step2, y + l, z + step, random);
                        }
                    }
                }
            }
        }
        else {
            this.decorateFullMonsterRoom(random, world, x, y, z, side);
        }
    }
    
    @Override
    public int getType() {
        return 202;
    }
}
