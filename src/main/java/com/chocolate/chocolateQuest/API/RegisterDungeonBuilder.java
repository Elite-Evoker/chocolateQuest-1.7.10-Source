package com.chocolate.chocolateQuest.API;

import java.util.Iterator;
import java.util.ArrayList;

public class RegisterDungeonBuilder
{
    public static ArrayList<BuilderBase> builderList;
    
    public static void addDungeonBuilder(final BuilderBase mob) {
        if (RegisterDungeonBuilder.builderList == null) {
            RegisterDungeonBuilder.builderList = new ArrayList<BuilderBase>();
        }
        RegisterDungeonBuilder.builderList.add(mob);
    }
    
    public static void addMob(final BuilderBase mob, final int position) {
        if (RegisterDungeonBuilder.builderList == null) {
            RegisterDungeonBuilder.builderList = new ArrayList<BuilderBase>();
        }
        RegisterDungeonBuilder.builderList.add(mob);
    }
    
    public static BuilderBase getBuilderByName(final String s) {
        try {
            for (final BuilderBase builder : RegisterDungeonBuilder.builderList) {
                if (builder.getName().equals(s)) {
                    return (BuilderBase)builder.getClass().newInstance();
                }
            }
        }
        catch (final InstantiationException e) {
            e.printStackTrace();
        }
        catch (final IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return null;
    }
}
