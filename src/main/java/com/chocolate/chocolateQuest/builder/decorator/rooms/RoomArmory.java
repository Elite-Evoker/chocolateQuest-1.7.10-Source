package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomArmory extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5) {
            this.placeArmorStand(random, world, x, y, z, side);
        }
        else if (random.nextInt(20) == 0) {
            this.placeArmorStand(random, world, x, y, z, side);
        }
        else if (random.nextInt(8) == 0) {
            this.placeShied(random, world, x, y + 1, z, side);
        }
        else if (random.nextInt(16) == 0) {
            this.addWeaponChest(random, world, x, y, z, side);
        }
        else {
            this.decorateFullMonsterRoom(random, world, x, y, z, side);
        }
    }
    
    @Override
    public int getType() {
        return 202;
    }
}
