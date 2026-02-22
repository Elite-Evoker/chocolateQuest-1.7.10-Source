package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomEndPortal extends RoomBase
{
    @Override
    public void addRoomDecoration(final Random random, final World world, final int posX, final int posY, final int posZ) {
        int x = posX + this.sizeX / 2;
        int y = posY;
        int z = posZ + this.sizeZ / 2;
        x -= 3;
        z -= 3;
        ++y;
        final Block block = Blocks.end_portal_frame;
        for (int i = 1; i < 4; ++i) {
            world.setBlock(x + i, y, z + 4, block, 0, 3);
            world.setBlock(x + i, y, z, block, 2, 3);
            world.setBlock(x, y, z + i, block, 3, 3);
            world.setBlock(x + 4, y, z + i, block, 1, 3);
        }
    }
    
    @Override
    public int getType() {
        return 208;
    }
}
