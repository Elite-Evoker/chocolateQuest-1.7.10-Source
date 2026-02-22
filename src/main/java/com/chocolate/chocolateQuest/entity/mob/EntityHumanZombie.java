package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.misc.EnumVoice;
import net.minecraft.entity.EnumCreatureAttribute;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;

public class EntityHumanZombie extends EntityHumanMob
{
    public EntityHumanZombie(final World world) {
        super(world);
    }
    
    @Override
    public DungeonMonstersBase getMonsterType() {
        return ChocolateQuest.zombie;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    protected String getLivingSound() {
        return EnumVoice.ZOMBIE.say;
    }
    
    protected String getHurtSound() {
        return EnumVoice.ZOMBIE.hurt;
    }
    
    protected String getDeathSound() {
        return EnumVoice.ZOMBIE.death;
    }
}
