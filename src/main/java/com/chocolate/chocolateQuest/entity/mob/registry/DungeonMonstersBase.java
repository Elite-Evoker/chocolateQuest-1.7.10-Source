package com.chocolate.chocolateQuest.entity.mob.registry;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class DungeonMonstersBase
{
    int id;
    
    public void setID(final int id) {
        this.id = id;
    }
    
    public int getID() {
        return this.id;
    }
    
    public abstract Entity getBoss(final World p0, final int p1, final int p2, final int p3);
    
    public abstract Entity getEntity(final World p0, final int p1, final int p2, final int p3);
    
    public String getSpawnerName(final int x, final int y, final int z, final Random random) {
        return null;
    }
    
    public abstract String getEntityName();
    
    public abstract String getRegisteredEntityName();
    
    public String getTeamName() {
        return "npc";
    }
    
    public double getHealth() {
        return 20.0;
    }
    
    public double getAttack() {
        return 1.0;
    }
    
    public double getRange() {
        return 20.0;
    }
    
    public int getWeight() {
        return 100;
    }
    
    public int getFlagId() {
        return 0;
    }
    
    public DungeonMonstersBase getDungeonMonster(final World world, final int x, final int y, final int z) {
        return this;
    }
}
