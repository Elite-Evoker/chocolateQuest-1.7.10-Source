package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.BuilderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomBedRoom extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side != 5) {
            if (random.nextInt(75) == 0) {
                this.placePainting(random, world, x, y + 1, z, side);
            }
            if (random.nextInt(30) == 0) {
                this.placeFrame(random, world, x, y + 1, z, side, new ItemStack(Items.clock));
            }
            if (random.nextInt(5) == 0) {
                this.placeBed(random, world, x, y, z, side);
            }
            else if (random.nextInt(10) == 0) {
                world.setBlock(x, y, z, Blocks.bookshelf);
            }
            else if (random.nextInt(10) == 0) {
                world.setBlock(x, y, z, Blocks.crafting_table);
            }
            else if (random.nextInt(10) == 0) {
                this.placeFlowerPot(random, world, x, y, z);
            }
            else if (random.nextInt(15) == 0) {
                world.setBlock(x, y, z, Blocks.redstone_lamp);
                world.setBlock(x, y + 1, z, Blocks.lever, 5, 3);
            }
            else if (random.nextInt(30) == 0) {
                this.addWeaponChest(random, world, x, y, z, side);
            }
            else if (random.nextInt(25) == 0) {
                BuilderHelper.addChest(random, world, x, y, z);
            }
            else {
                this.decorateFullMonsterRoom(random, world, x, y, z, side);
            }
        }
    }
    
    @Override
    public int getType() {
        return 0;
    }
}
