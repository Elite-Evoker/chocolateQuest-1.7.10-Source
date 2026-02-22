package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanWalker;
import com.chocolate.chocolateQuest.entity.mob.EntityWalkerBoss;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobWalker extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "walker";
    }
    
    @Override
    public int getFlagId() {
        return 10;
    }
    
    @Override
    public String getRegisteredEntityName() {
        return "chocolateQuest.abyssWalker";
    }
    
    @Override
    public Entity getBoss(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityWalkerBoss(world);
    }
    
    @Override
    public Entity getEntity(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityHumanWalker(world);
    }
    
    @Override
    public String getTeamName() {
        return "mob_walker";
    }
    
    @Override
    public double getHealth() {
        return 30.0;
    }
    
    @Override
    public double getAttack() {
        return 2.0;
    }
    
    @Override
    public double getRange() {
        return 30.0;
    }
}
