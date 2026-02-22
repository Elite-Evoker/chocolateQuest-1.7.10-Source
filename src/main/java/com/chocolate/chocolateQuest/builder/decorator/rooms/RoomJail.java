package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.world.World;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomJail extends RoomBase
{
    final Block BARS_BLOCK;
    
    public RoomJail() {
        this.BARS_BLOCK = Blocks.iron_bars;
    }
    
    @Override
    public int getType() {
        return 401;
    }
    
    @Override
    public void addRoomDecoration(final Random random, final World world, final int posX, final int posY, final int posZ) {
        final int roomCenterX = posX + this.sizeX / 2;
        final int roomCenterZ = posZ + this.sizeZ / 2;
        this.decorateFullMonsterRoom(random, world, roomCenterX, posY, roomCenterZ, 5);
        if (this.sizeX > 1) {
            if (!this.doorNorth && this.decorateNorth) {
                for (int i = 1; i < this.sizeX; ++i) {
                    for (int h = 0; h < this.properties.floorHeight - 1; ++h) {
                        world.setBlock(posX + i, posY + h, posZ + 3, this.BARS_BLOCK);
                    }
                }
                for (int villagers = Math.max(1, this.sizeX / 6), v = 0; v < villagers; ++v) {
                    if (random.nextBoolean()) {
                        final int villagerOffset = random.nextInt(this.sizeX - 1) + 1;
                        this.spawnPrisoner(world, random, posX + villagerOffset, posY, posZ + 1);
                    }
                }
            }
            if (!this.doorSouth && this.decorateSouth) {
                for (int i = 1; i < this.sizeX; ++i) {
                    for (int h = 0; h < this.properties.floorHeight - 1; ++h) {
                        world.setBlock(posX + i, posY + h, posZ + this.sizeZ - 3, this.BARS_BLOCK);
                    }
                }
                for (int villagers = Math.max(1, this.sizeX / 6), v = 0; v < villagers; ++v) {
                    if (random.nextBoolean()) {
                        final int villagerOffset = random.nextInt(this.sizeX - 1) + 1;
                        this.spawnPrisoner(world, random, posX + villagerOffset, posY, posZ + this.sizeZ - 1);
                    }
                }
            }
        }
        if (this.sizeZ > 1) {
            if (!this.doorEast && this.decorateEast) {
                for (int i = 1; i < this.sizeZ; ++i) {
                    for (int h = 0; h < this.properties.floorHeight - 1; ++h) {
                        world.setBlock(posX + 3, posY + h, posZ + i, this.BARS_BLOCK);
                    }
                }
                for (int villagers = Math.max(1, this.sizeZ / 3), v = 0; v < villagers; ++v) {
                    if (random.nextBoolean()) {
                        final int villagerOffset = random.nextInt(this.sizeZ - 1) + 1;
                        this.spawnPrisoner(world, random, posX + 1, posY, posZ + villagerOffset);
                    }
                }
            }
            if (!this.doorWest && this.decorateWest) {
                for (int i = 1; i < this.sizeZ; ++i) {
                    for (int h = 0; h < this.properties.floorHeight - 1; ++h) {
                        world.setBlock(posX + this.sizeX - 3, posY + h, posZ + i, this.BARS_BLOCK);
                    }
                }
                for (int villagers = Math.max(1, this.sizeZ / 3), v = 0; v < villagers; ++v) {
                    if (random.nextBoolean()) {
                        final int villagerOffset = random.nextInt(this.sizeZ - 1) + 1;
                        this.spawnPrisoner(world, random, posX + this.sizeX - 1, posY, posZ + villagerOffset);
                    }
                }
            }
        }
    }
    
    public void spawnPrisoner(final World world, final Random random, final int posX, final int posY, final int posZ) {
    }
}
