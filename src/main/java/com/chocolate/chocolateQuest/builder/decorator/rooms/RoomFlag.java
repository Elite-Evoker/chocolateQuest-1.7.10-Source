package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomFlag extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5) {
            world.setBlock(x, y, z, ChocolateQuest.bannerStand);
            final BlockBannerStandTileEntity stand = (BlockBannerStandTileEntity)ChocolateQuest.bannerStand.createTileEntity(world, 0);
            if (stand != null) {
                DungeonMonstersBase mobType = null;
                stand.hasFlag = true;
                mobType = RegisterDungeonMobs.mobList.get(this.properties.mobID);
                if (mobType != null) {
                    stand.item = new ItemStack(ChocolateQuest.banner, 1, mobType.getFlagId());
                }
                this.setTileEntity(world, x, y, z, stand);
            }
        }
        else if (random.nextInt(5) == 0) {
            int rotation = 0;
            switch (side) {
                case 1: {
                    rotation = -90;
                    break;
                }
                case 2: {
                    rotation = 90;
                    break;
                }
                case 4: {
                    rotation = 180;
                    break;
                }
            }
            world.setBlock(x, y, z, ChocolateQuest.bannerStand);
            final BlockBannerStandTileEntity stand2 = (BlockBannerStandTileEntity)ChocolateQuest.bannerStand.createTileEntity(world, 0);
            if (stand2 != null) {
                DungeonMonstersBase mobType2 = null;
                stand2.hasFlag = true;
                stand2.rotation = rotation;
                mobType2 = RegisterDungeonMobs.mobList.get(this.properties.mobID);
                if (mobType2 != null) {
                    stand2.item = new ItemStack(ChocolateQuest.banner, 1, mobType2.getFlagId());
                }
                this.setTileEntity(world, x, y, z, stand2);
            }
        }
        else if (random.nextInt(10) == 0) {
            this.placeShied(random, world, x, y + 1, z, side);
        }
        else {
            this.decorateFullMonsterRoom(random, world, x, y, z, side);
        }
    }
    
    @Override
    public int getType() {
        return 204;
    }
}
