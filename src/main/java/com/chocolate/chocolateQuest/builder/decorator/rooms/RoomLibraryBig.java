package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomLibraryBig extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5 && random.nextInt(3) == 0) {
            return;
        }
        for (int i = 0; i < this.properties.floorHeight; ++i) {
            world.setBlock(x, y + i, z, Blocks.bookshelf);
        }
    }
    
    @Override
    public int getType() {
        return 200;
    }
}
