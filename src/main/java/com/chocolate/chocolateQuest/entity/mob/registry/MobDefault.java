package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import com.chocolate.chocolateQuest.entity.mob.EntityNecromancer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobDefault extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "default";
    }
    
    @Override
    public int getFlagId() {
        return 8;
    }
    
    @Override
    public String getRegisteredEntityName() {
        return "chocolateQuest.armoredSkeleton";
    }
    
    @Override
    public Entity getBoss(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityNecromancer(world);
    }
    
    @Override
    public Entity getEntity(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityHumanSkeleton(world);
    }
    
    @Override
    public String getTeamName() {
        return "mob_undead";
    }
    
    @Override
    public DungeonMonstersBase getDungeonMonster(final World world, final int x, final int y, final int z) {
        final double dist = Math.sqrt((world.getSpawnPoint().posX - x) * (world.getSpawnPoint().posX - x) + (world.getSpawnPoint().posZ - z) * (world.getSpawnPoint().posZ - z));
        if (dist < 1000.0) {
            return ChocolateQuest.skeleton;
        }
        if (dist < 2000.0) {
            return ChocolateQuest.zombie;
        }
        if (dist < 3000.0) {
            return ChocolateQuest.specter;
        }
        if (dist < 4000.0) {
            return ChocolateQuest.pigZombie;
        }
        if (dist < 5000.0) {
            return ChocolateQuest.minotaur;
        }
        return ChocolateQuest.skeleton;
    }
}
