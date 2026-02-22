package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomDinning extends RoomBase
{
    @Override
    public void addRoomDecoration(final Random random, final World world, final int posX, final int posY, final int posZ) {
        final int sx = Math.max(1, this.sizeX - 3);
        final int sz = Math.max(1, this.sizeZ - 3);
        for (int i = 2; i < sx; ++i) {
            for (int k = 2; k < sz; ++k) {
                world.setBlock(posX + i, posY, posZ + k, ChocolateQuest.table);
            }
        }
    }
    
    @Override
    public int getType() {
        return 400;
    }
}
