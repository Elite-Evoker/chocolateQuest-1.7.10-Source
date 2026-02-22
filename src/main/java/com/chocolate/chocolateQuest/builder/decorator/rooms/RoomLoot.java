package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.BuilderHelper;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomLoot extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5) {
            if (random.nextInt(10) == 0) {
                BuilderHelper.addTreasure(random, world, x, y, z);
            }
            else if (random.nextInt(3) == 0) {
                BuilderHelper.addWeaponChest(random, world, x, y, z);
            }
            else if (random.nextInt(3) == 0) {
                BuilderHelper.addChest(random, world, x, y, z);
            }
            else {
                BuilderHelper.addMineralChest(random, world, x, y, z);
            }
        }
        else if (random.nextInt(30) == 0) {
            BuilderHelper.addTreasure(random, world, x, y, z);
            BuilderHelper.addWeaponChest(random, world, x, y, z);
            BuilderHelper.addChest(random, world, x, y, z);
            BuilderHelper.addMineralChest(random, world, x, y, z);
        }
        else {
            this.decorateFullMonsterRoom(random, world, x, y, z, side);
        }
    }
    
    @Override
    public int getType() {
        return 403;
    }
}
