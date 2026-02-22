package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.util.StatCollector;

public enum EnumAiState
{
    FOLLOW("ai.follow.name"), 
    FORMATION("ai.formation.name"), 
    WARD("ai.ward.name"), 
    PATH("ai.path.name"), 
    SIT("ai.sit.name"), 
    WANDER("ai.wander.name");
    
    public String ainame;
    
    private EnumAiState(final String name) {
        this.ainame = name;
    }
    
    public static String[] getNames() {
        final EnumAiState[] states = values();
        final String[] names = new String[states.length];
        for (int i = 0; i < states.length; ++i) {
            names[i] = StatCollector.translateToLocal(states[i].ainame);
        }
        return names;
    }
}
