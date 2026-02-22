package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityLich;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobZombie extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "zombie";
    }
    
    @Override
    public int getFlagId() {
        return 7;
    }
    
    @Override
    public String getRegisteredEntityName() {
        return "chocolateQuest.armoredZombie";
    }
    
    @Override
    public Entity getBoss(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityLich(world);
    }
    
    @Override
    public Entity getEntity(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityHumanZombie(world);
    }
    
    @Override
    public String getTeamName() {
        return "mob_undead";
    }
    
    @Override
    public double getHealth() {
        return 25.0;
    }
    
    @Override
    public double getAttack() {
        return 1.0;
    }
}
