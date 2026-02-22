package com.chocolate.chocolateQuest.entity.mob.registry;

import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.SharedMonsterAttributes;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMinotaur;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobMinotaur extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "minotaur";
    }
    
    @Override
    public int getFlagId() {
        return 11;
    }
    
    @Override
    public String getRegisteredEntityName() {
        return "chocolateQuest.minotaur";
    }
    
    @Override
    public Entity getBoss(final World world, final int x, final int y, final int z) {
        final EntityHumanMinotaur m = new EntityHumanMinotaur(world);
        m.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0);
        m.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.bigSwordBull));
        return (Entity)m;
    }
    
    @Override
    public Entity getEntity(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityHumanMinotaur(world);
    }
    
    @Override
    public String getTeamName() {
        return "mob_undead";
    }
    
    @Override
    public double getHealth() {
        return 40.0;
    }
    
    @Override
    public double getRange() {
        return 35.0;
    }
    
    @Override
    public double getAttack() {
        return 2.0;
    }
}
