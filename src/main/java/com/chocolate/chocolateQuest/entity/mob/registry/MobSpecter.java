package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanSpecter;
import com.chocolate.chocolateQuest.entity.mob.EntitySpecterBoss;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobSpecter extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "specter";
    }
    
    @Override
    public int getFlagId() {
        return 12;
    }
    
    @Override
    public String getRegisteredEntityName() {
        return "chocolateQuest.specter";
    }
    
    @Override
    public Entity getBoss(final World world, final int x, final int y, final int z) {
        return (Entity)new EntitySpecterBoss(world);
    }
    
    @Override
    public Entity getEntity(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityHumanSpecter(world);
    }
    
    @Override
    public String getTeamName() {
        return "mob_undead";
    }
    
    @Override
    public double getHealth() {
        return 30.0;
    }
    
    @Override
    public double getRange() {
        return 25.0;
    }
}
