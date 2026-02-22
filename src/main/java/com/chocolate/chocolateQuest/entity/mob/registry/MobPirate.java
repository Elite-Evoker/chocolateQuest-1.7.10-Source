package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanPirate;
import com.chocolate.chocolateQuest.entity.mob.EntityPirateBoss;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobPirate extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "pirate";
    }
    
    @Override
    public int getFlagId() {
        return 9;
    }
    
    @Override
    public String getRegisteredEntityName() {
        return "chocolateQuest.pirate";
    }
    
    @Override
    public Entity getBoss(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityPirateBoss(world);
    }
    
    @Override
    public Entity getEntity(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityHumanPirate(world);
    }
    
    @Override
    public String getTeamName() {
        return "mob_pirate";
    }
}
