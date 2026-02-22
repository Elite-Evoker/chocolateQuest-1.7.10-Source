package com.chocolate.chocolateQuest.entity.mob;

import net.minecraft.entity.EnumCreatureAttribute;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;

public class EntityHumanPigZombie extends EntityHumanMob
{
    public EntityHumanPigZombie(final World world) {
        super(world);
    }
    
    @Override
    public DungeonMonstersBase getMonsterType() {
        return ChocolateQuest.pigZombie;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    protected String getLivingSound() {
        return "mob.zombiepig.zpig";
    }
    
    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }
    
    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }
}
