package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import com.chocolate.chocolateQuest.entity.mob.EntityNecromancer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobSkeleton extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "skeleton";
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
}
