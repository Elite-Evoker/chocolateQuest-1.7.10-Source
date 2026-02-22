package com.chocolate.chocolateQuest.builder.decorator;

import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomStairs;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import java.util.Random;

public class TowerSquare
{
    BuildingProperties properties;
    int radio;
    int floors;
    
    public TowerSquare(final BuildingProperties properties) {
        this.radio = 6;
        this.floors = 4;
        this.properties = properties;
    }
    
    public void configure(final int floors, final int width) {
        this.floors = floors;
        this.radio = Math.max(3, width / 2);
    }
    
    public void buildTower(final Random random, final World world, int x, final int y, int z, final ForgeDirection direction) {
        final int floors = Math.max(1, this.floors - 1 + random.nextInt(5));
        final int width = this.radio * 2;
        final int originX = x;
        final int originZ = z;
        x -= Math.abs(this.radio * direction.offsetZ);
        z -= Math.abs(this.radio * direction.offsetX);
        if (direction.offsetX < 0) {
            x -= width;
        }
        if (direction.offsetZ < 0) {
            z -= width;
        }
        final RoomStairs stairs = new RoomStairs();
        stairs.configure(this.radio * 2, this.radio * 2, this.properties);
        for (int f = floors - 1; f >= 0; --f) {
            final int posY = y + f * (this.properties.floorHeight + 1);
            for (int sx = 1; sx < width; ++sx) {
                final int posX = x + sx;
                for (int sz = 1; sz < width; ++sz) {
                    final int posZ = z + sz;
                    this.properties.floor.generateFloor(world, posX, posY, posZ);
                    for (int h = 1; h < this.properties.floorHeight; ++h) {
                        world.setBlockToAir(posX, posY + h, posZ);
                    }
                    this.properties.setWallBlock(world, posX, posY + this.properties.floorHeight, posZ);
                }
            }
            stairs.decorate(random, world, x, posY + 1, z);
            for (int sx = 0; sx <= width; ++sx) {
                int posX = x + sx;
                int posZ2 = z;
                this.properties.setWallBlock(world, posX, posY, posZ2);
                this.properties.window.generateWindowX(world, posX, posY + 1, posZ2);
                posZ2 = z + width;
                this.properties.setWallBlock(world, posX, posY, posZ2);
                this.properties.window.generateWindowX(world, posX, posY + 1, posZ2);
                posX = x;
                posZ2 = z + sx;
                this.properties.setWallBlock(world, posX, posY, posZ2);
                this.properties.window.generateWindowZ(world, posX, posY + 1, posZ2);
                posX = x + width;
                this.properties.setWallBlock(world, posX, posY, posZ2);
                this.properties.window.generateWindowZ(world, posX, posY + 1, posZ2);
            }
        }
        this.properties.roof.generateRoof(world, x, y + floors * (this.properties.floorHeight + 1), z, width, width, false);
        for (int h2 = 0; h2 < this.floors + 1; ++h2) {
            final int posY = y + 1 + h2 * (this.properties.floorHeight + 1);
            this.properties.doors.generate(random, world, originX, posY, originZ, direction);
        }
    }
}
