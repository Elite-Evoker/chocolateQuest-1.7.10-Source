package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;

public class EntitySpaceWarrior extends EntityHumanMob
{
    public EntitySpaceWarrior(final World world) {
        super(world);
    }
    
    @Override
    public DungeonMonstersBase getMonsterType() {
        return ChocolateQuest.spaceWarrior;
    }
}
