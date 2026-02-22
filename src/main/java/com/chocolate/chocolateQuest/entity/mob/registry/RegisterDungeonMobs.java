package com.chocolate.chocolateQuest.entity.mob.registry;

import java.util.Iterator;
import java.util.ArrayList;

public class RegisterDungeonMobs
{
    public static ArrayList<DungeonMonstersBase> mobList;
    
    public static void addMob(final DungeonMonstersBase mob) {
        if (RegisterDungeonMobs.mobList == null) {
            RegisterDungeonMobs.mobList = new ArrayList<DungeonMonstersBase>();
        }
        RegisterDungeonMobs.mobList.add(mob);
        mob.setID(RegisterDungeonMobs.mobList.indexOf(mob));
    }
    
    public static void addMob(final DungeonMonstersBase mob, final int position) {
        if (RegisterDungeonMobs.mobList == null) {
            RegisterDungeonMobs.mobList = new ArrayList<DungeonMonstersBase>();
        }
        RegisterDungeonMobs.mobList.add(mob);
        mob.setID(RegisterDungeonMobs.mobList.indexOf(mob));
    }
    
    public static int getMonsterId(final String s) {
        int id = 0;
        for (final DungeonMonstersBase mob : RegisterDungeonMobs.mobList) {
            if (mob.getEntityName().equals(s)) {
                id = mob.getID();
            }
        }
        return id;
    }
    
    public static int getMonster(final String s) {
        int id = 1;
        for (final DungeonMonstersBase mob : RegisterDungeonMobs.mobList) {
            if (mob.getEntityName().equals(s)) {
                id = mob.getID();
            }
        }
        return id;
    }
}
