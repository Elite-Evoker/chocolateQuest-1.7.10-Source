package com.chocolate.chocolateQuest.entity.mob;

import net.minecraft.entity.EnumCreatureAttribute;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;

public class EntityHumanMinotaur extends EntityHumanMob
{
    public EntityHumanMinotaur(final World world) {
        super(world);
    }
    
    @Override
    public DungeonMonstersBase getMonsterType() {
        return ChocolateQuest.minotaur;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    protected String getLivingSound() {
        return "mob.cow.say";
    }
    
    protected String getHurtSound() {
        return "mob.cow.hurt";
    }
    
    protected String getDeathSound() {
        return "mob.cow.death";
    }
}
