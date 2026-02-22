package com.chocolate.chocolateQuest.builder.decorator;

import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomStairs;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import java.util.Random;

public class TowerRound extends TowerSquare
{
    public TowerRound(final BuildingProperties properties) {
        super(properties);
    }
    
    @Override
    public void buildTower(final Random random, final World world, int x, final int y, int z, final ForgeDirection direction) {
        final int floors = Math.max(1, this.floors - 1 + random.nextInt(5));
        final int radioExtended = this.radio + 1;
        final int radioDecreased = this.radio - 1;
        final RoomStairs stairs = new RoomStairs();
        stairs.configure(this.radio * 2 - 2, this.radio * 2 - 2, this.properties);
        stairs.clearWalls();
        int currentY = y + floors * (this.properties.floorHeight + 1);
        x += this.radio * direction.offsetX;
        z += this.radio * direction.offsetZ;
        this.buildRoof(random, world, x, y, z, floors);
        for (int h = 0; h <= floors; ++h) {
            for (int px = -radioExtended; px < radioExtended; ++px) {
                for (int pz = -radioExtended; pz < radioExtended; ++pz) {
                    final int dist = px * px + pz * pz;
                    final int blockX = x + px;
                    final int blockY = currentY;
                    final int blockZ = z + pz;
                    if (dist < this.radio * this.radio) {
                        this.properties.floor.generateFloor(world, blockX, blockY, blockZ);
                        this.properties.wallBlock.placeBlock(world, blockX, blockY + this.properties.floorHeight, blockZ, random);
                        for (int i = 1; i < this.properties.floorHeight - 1; ++i) {
                            world.setBlock(blockX, blockY + i, blockZ, Blocks.air);
                        }
                        if (dist >= radioDecreased * radioDecreased) {
                            stairs.placeRandomDecorationBlock(random, world, blockX, blockY + 1, blockZ, 0);
                        }
                    }
                    else if (dist < radioExtended * radioExtended) {
                        for (int i = 0; i <= this.properties.floorHeight; ++i) {
                            this.properties.wallBlock.placeBlock(world, blockX, blockY + i, blockZ, random);
                        }
                        this.properties.window.generateWindowX(world, blockX, blockY + 1, blockZ);
                    }
                }
            }
            stairs.addRoomDecoration(random, world, x - this.radio, currentY + 1, z - this.radio + 1);
            currentY -= this.properties.floorHeight + 1;
        }
        x -= this.radio * direction.offsetX;
        z -= this.radio * direction.offsetZ;
        for (int h = 0; h < this.floors + 1; ++h) {
            final int posY = y + 1 + h * (this.properties.floorHeight + 1);
            this.properties.doors.generate(random, world, x, posY, z, direction);
        }
    }
    
    public void buildRoof(final Random random, final World world, final int x, final int y, final int z, final int floors) {
        final int currentY = y + floors * this.properties.floorHeight + 1;
        final int radioExtended = this.radio + 1;
        final int floorType = this.properties.roof.floorType;
        this.properties.roof.getClass();
        if (floorType != 0) {
            final int floorType2 = this.properties.roof.floorType;
            this.properties.roof.getClass();
            if (floorType2 != 5) {
                for (int px = -radioExtended; px < radioExtended; ++px) {
                    for (int pz = -radioExtended; pz < radioExtended; ++pz) {
                        final int dist = px * px + pz * pz;
                        final int blockX = x + px;
                        final int blockY = currentY + (this.properties.floorHeight + 1);
                        final int blockZ = z + pz;
                        if (dist < this.radio * this.radio) {
                            this.properties.wallBlock.placeBlock(world, blockX, blockY, blockZ, random);
                        }
                        else if (dist < radioExtended * radioExtended) {
                            this.properties.wallBlock.placeBlock(world, blockX, blockY, blockZ, random);
                            this.properties.roof.setRoofBlock(world, blockX, blockY + 1, blockZ);
                        }
                    }
                }
                return;
            }
        }
        int texasRadio = this.radio;
        int texasRadioExtended = texasRadio + 1;
        for (int steps = 2, h = 0; h <= this.radio * steps; ++h) {
            for (int px2 = -texasRadioExtended; px2 < texasRadioExtended; ++px2) {
                for (int pz2 = -texasRadioExtended; pz2 < texasRadioExtended; ++pz2) {
                    final int dist2 = px2 * px2 + pz2 * pz2;
                    final int blockX2 = x + px2;
                    final int blockY2 = currentY + (this.properties.floorHeight + 1) + h;
                    final int blockZ2 = z + pz2;
                    if (dist2 >= texasRadio * texasRadio) {
                        if (dist2 < texasRadioExtended * texasRadioExtended) {
                            this.properties.setWallBlock(world, blockX2, blockY2, blockZ2);
                        }
                    }
                }
            }
            if (h % steps == 0) {
                texasRadioExtended = --texasRadio + 1;
            }
        }
    }
}
