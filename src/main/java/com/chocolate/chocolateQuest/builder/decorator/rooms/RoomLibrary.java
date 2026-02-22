package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomLibrary extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5 || random.nextInt(3) == 0) {
            world.setBlock(x, y, z, Blocks.bookshelf);
        }
        else if (random.nextInt(50) == 0) {
            this.placeFrame(random, world, x, y + 1, z, side, new ItemStack(Items.clock));
        }
        else if (random.nextInt(100) == 0) {
            this.placePainting(random, world, x, y + 1, z, side);
        }
        else {
            this.decorateFullMonsterRoom(random, world, x, y, z, side);
        }
    }
    
    @Override
    public int getType() {
        return 2;
    }
}
