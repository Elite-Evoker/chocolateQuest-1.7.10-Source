package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanWalker;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobGremlin extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "gremlin";
    }
    
    @Override
    public int getFlagId() {
        return 11;
    }
    
    @Override
    public String getRegisteredEntityName() {
        return "chocolateQuest.gremlin";
    }
    
    @Override
    public Entity getBoss(final World world, final int x, final int y, final int z) {
        return null;
    }
    
    @Override
    public Entity getEntity(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityHumanWalker(world);
    }
    
    @Override
    public String getTeamName() {
        return "mob_gremlin";
    }
}
