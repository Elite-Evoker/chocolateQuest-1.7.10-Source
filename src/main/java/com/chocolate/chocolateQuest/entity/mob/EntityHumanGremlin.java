package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;

public class EntityHumanGremlin extends EntityHumanMob
{
    public EntityHumanGremlin(final World world) {
        super(world);
    }
    
    @Override
    public DungeonMonstersBase getMonsterType() {
        return ChocolateQuest.gremlin;
    }
    
    protected String getLivingSound() {
        return EnumVoice.GOBLIN.say;
    }
    
    protected String getHurtSound() {
        return EnumVoice.GOBLIN.hurt;
    }
    
    protected String getDeathSound() {
        return EnumVoice.GOBLIN.death;
    }
}
