package com.chocolate.chocolateQuest.entity.mob;

import net.minecraft.entity.EnumCreatureAttribute;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;

public class EntityHumanSpecter extends EntityHumanMob
{
    public EntityHumanSpecter(final World world) {
        super(world);
    }
    
    @Override
    public DungeonMonstersBase getMonsterType() {
        return ChocolateQuest.specter;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    protected String getLivingSound() {
        return "chocolatequest:specter_speak";
    }
    
    protected String getHurtSound() {
        return "chocolatequest:specter_hurt";
    }
    
    protected String getDeathSound() {
        return "chocolatequest:specter_death";
    }
}
