package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomAlchemy extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5 || random.nextInt(5) == 0) {
            world.setBlock(x, y, z, Blocks.brewing_stand, 0, 3);
        }
        else if (random.nextInt(5) == 0) {
            world.setBlock(x, y, z, (Block)Blocks.cauldron, random.nextInt(3), 3);
        }
        else {
            this.decorateFullMonsterRoom(random, world, x, y, z, side);
        }
    }
    
    @Override
    public int getType() {
        return 201;
    }
}
