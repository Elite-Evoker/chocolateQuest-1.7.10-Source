package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.API.RegisterChestItem;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomBlackSmith extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5) {
            world.setBlock(x, y, z, Blocks.lava);
            world.setBlock(x, y - 1, z, Blocks.obsidian);
            world.setBlock(x + 1, y, z, Blocks.obsidian);
            world.setBlock(x - 1, y, z, Blocks.obsidian);
            world.setBlock(x, y, z + 1, Blocks.obsidian);
            world.setBlock(x, y, z - 1, Blocks.obsidian);
        }
        else {
            if (random.nextInt(100) == 0) {
                this.placePainting(random, world, x, y + 1, z, side);
            }
            if (random.nextInt(5) == 0) {
                world.setBlock(x, y, z, Blocks.anvil, side, 3);
            }
            else if (random.nextInt(5) == 0) {
                this.placeFurnace(random, world, x, y, z, side);
            }
            else if (random.nextInt(20) == 0) {
                final ItemStack is = RegisterChestItem.getRandomItemStack(RegisterChestItem.mineralList, random);
                this.placeTable(random, world, x, y, z, is);
            }
            else if (random.nextInt(40) == 0) {
                BuilderHelper.addWeaponChest(random, world, x, y, z);
            }
            else if (random.nextInt(40) == 0) {
                BuilderHelper.addMineralChest(random, world, x, y, z);
            }
            else {
                this.decorateFullMonsterRoom(random, world, x, y, z, side);
            }
        }
    }
    
    @Override
    public int getType() {
        return 202;
    }
}
