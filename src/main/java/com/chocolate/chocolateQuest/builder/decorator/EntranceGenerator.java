package com.chocolate.chocolateQuest.builder.decorator;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;

public class EntranceGenerator
{
    private BuildingProperties properties;
    
    public EntranceGenerator(final BuildingProperties properties) {
        this.properties = properties;
    }
    
    public void generateEntance(final World world, int xCenter, final int yCenter, int zCenter, final ForgeDirection direction) {
        final int dirX = direction.offsetX;
        final int dirZ = direction.offsetZ;
        final int width = 6;
        final int height = 5;
        final int length = 2;
        xCenter -= width / 2 * dirZ;
        zCenter -= width / 2 * dirX;
        for (int w = 0; w < width; ++w) {
            for (int l = -2; l < length; ++l) {
                for (int h = 0; h < height; ++h) {
                    final int x = xCenter + w * dirZ + l * dirX;
                    final int y = yCenter + h;
                    final int z = zCenter + w * dirX + l * dirZ;
                    if (h == 0) {
                        this.properties.floor.generateFloor(world, x, y, z);
                    }
                    else if (l < 0) {
                        world.setBlockToAir(x, y, z);
                    }
                    else if (h == height - 1) {
                        this.properties.setWallBlock(world, x, y, z);
                    }
                    else if (w == 0 || w == width - 1) {
                        world.setBlock(x, y, z, Blocks.fence);
                    }
                    else {
                        world.setBlockToAir(x, y, z);
                    }
                }
            }
        }
    }
}
