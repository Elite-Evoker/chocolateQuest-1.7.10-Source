package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomStorage extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5 || random.nextInt(8) == 0) {
            this.decorateFullMonsterRoom(random, world, x, y, z, side);
        }
        else {
            Block id = Blocks.pumpkin;
            int md = 0;
            if (side == 1) {
                id = Blocks.melon_block;
            }
            if (side == 2) {
                id = Blocks.hay_block;
            }
            if (side == 3) {
                id = Blocks.red_mushroom_block;
                md = 15;
            }
            for (int roomHeight = random.nextInt(this.properties.floorHeight), i = 0; i < roomHeight; ++i) {
                world.setBlock(x, y + i, z, id, md, 3);
            }
        }
    }
    
    @Override
    public int getType() {
        return 206;
    }
}
