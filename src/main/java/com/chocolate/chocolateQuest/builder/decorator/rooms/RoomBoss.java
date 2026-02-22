package com.chocolate.chocolateQuest.builder.decorator.rooms;

import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;

public class RoomBoss extends RoomBase
{
    @Override
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5) {
            final DungeonMonstersBase mob = RegisterDungeonMobs.mobList.get(this.properties.mobID);
            if (mob != null) {
                final Entity e = mob.getBoss(world, x, y, z);
                if (e != null) {
                    e.setPosition((double)x, (double)y, (double)z);
                    world.spawnEntityInWorld(e);
                    if (e.ridingEntity != null) {
                        world.spawnEntityInWorld(e.ridingEntity);
                    }
                }
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
