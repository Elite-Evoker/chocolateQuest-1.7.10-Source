package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomNetherPortal extends RoomBase
{
    @Override
    public void addRoomDecoration(final Random random, final World world, final int posX, final int posY, final int posZ) {
        final int x = posX + this.sizeX / 2;
        int y = posY;
        int z = posZ + this.sizeZ / 2;
        --z;
        --y;
        final Block block = Blocks.obsidian;
        world.setBlock(x, y, z, block);
        world.setBlock(x + 1, y, z, block);
        world.setBlock(x, y + 4, z, block);
        world.setBlock(x + 1, y + 4, z, block);
        for (int i = 0; i <= 4; ++i) {
            world.setBlock(x - 1, y + i, z, block);
            world.setBlock(x + 2, y + i, z, block);
        }
        if (random.nextInt(3) == 0) {
            for (int i = 1; i <= 3; ++i) {
                world.setBlock(x, y + i, z, (Block)Blocks.portal);
                world.setBlock(x + 1, y + i, z, (Block)Blocks.portal);
            }
        }
    }
    
    @Override
    public int getType() {
        return 208;
    }
}
