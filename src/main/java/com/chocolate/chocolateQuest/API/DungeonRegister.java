package com.chocolate.chocolateQuest.API;

import java.util.ArrayList;

public class DungeonRegister
{
    public static ArrayList<DungeonBase> dungeonList;
    
    public static void addDungeon(final DungeonBase dungeon) {
        if (DungeonRegister.dungeonList == null) {
            DungeonRegister.dungeonList = new ArrayList<DungeonBase>();
        }
        DungeonRegister.dungeonList.add(dungeon);
    }
}
