package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.misc.EnumVoice;
import net.minecraft.entity.EnumCreatureAttribute;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;

public class EntityHumanSkeleton extends EntityHumanMob
{
    public EntityHumanSkeleton(final World world) {
        super(world);
    }
    
    @Override
    public DungeonMonstersBase getMonsterType() {
        return ChocolateQuest.skeleton;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    protected String getLivingSound() {
        return EnumVoice.SKELETON.say;
    }
    
    protected String getHurtSound() {
        return EnumVoice.SKELETON.hurt;
    }
    
    protected String getDeathSound() {
        return EnumVoice.SKELETON.death;
    }
}
