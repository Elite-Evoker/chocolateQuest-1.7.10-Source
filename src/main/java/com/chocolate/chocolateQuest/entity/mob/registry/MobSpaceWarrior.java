package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanPirate;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.SharedMonsterAttributes;
import com.chocolate.chocolateQuest.entity.mob.EntitySpaceWarrior;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobSpaceWarrior extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "space_invader";
    }
    
    @Override
    public int getFlagId() {
        return 9;
    }
    
    @Override
    public String getRegisteredEntityName() {
        return "chocolateQuest.spaceWarrior";
    }
    
    @Override
    public Entity getBoss(final World world, final int x, final int y, final int z) {
        final EntitySpaceWarrior m = new EntitySpaceWarrior(world);
        m.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(800.0);
        m.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0);
        m.setCurrentItemOrArmor(0, null);
        return (Entity)m;
    }
    
    @Override
    public Entity getEntity(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityHumanPirate(world);
    }
    
    @Override
    public String getTeamName() {
        return "mob_invader";
    }
}
