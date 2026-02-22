package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.BuilderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomKitchen extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (random.nextInt(100) == 0) {
            this.placePainting(random, world, x, y + 1, z, side);
        }
        if (random.nextInt(250) == 0) {
            world.setBlock(x, y, z, Blocks.brewing_stand, 0, 3);
        }
        else if (random.nextInt(30) == 0) {
            this.placeCake(random, world, x, y, z);
        }
        else if (random.nextInt(50) == 0) {
            world.setBlock(x, y, z, (Block)Blocks.cauldron);
        }
        else if (random.nextInt(50) == 0) {
            world.setBlock(x, y, z, Blocks.melon_block);
        }
        else if (random.nextInt(50) == 0) {
            world.setBlock(x, y, z, Blocks.pumpkin);
        }
        else if (random.nextInt(8) == 0) {
            this.placeFoodFurnace(random, world, x, y, z, side);
        }
        else if (random.nextInt(10) == 0) {
            world.setBlock(x, y, z, Blocks.crafting_table);
        }
        else if (random.nextInt(8) == 0) {
            ItemStack is = null;
            if (random.nextInt(3) == 0) {
                is = new ItemStack(Items.cooked_fish);
            }
            else if (random.nextInt(3) == 0) {
                is = new ItemStack(Items.cooked_beef);
            }
            else if (random.nextInt(3) == 0) {
                is = new ItemStack(Items.milk_bucket);
            }
            else if (random.nextInt(3) == 0) {
                is = new ItemStack(Items.cookie);
            }
            else if (random.nextInt(3) == 0) {
                is = new ItemStack(Items.cooked_chicken);
            }
            else if (random.nextInt(3) == 0) {
                is = new ItemStack(Items.egg);
            }
            else if (random.nextInt(3) == 0) {
                is = new ItemStack(Items.golden_apple);
            }
            this.placeTable(random, world, x, y, z, is);
        }
        else if (random.nextInt(20) == 0) {
            BuilderHelper.addFoodChest(random, world, x, y, z);
        }
    }
    
    @Override
    public int getType() {
        return 0;
    }
}
