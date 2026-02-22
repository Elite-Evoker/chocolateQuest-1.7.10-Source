package com.chocolate.chocolateQuest.misc;

public enum EnumRace
{
    HUMAN(0, 1.0f, "Human"), 
    DWARF(1, 0.5f, "Dwarf"), 
    ORC(2, 1.0f, "Orc"), 
    TRITON(3, 1.0f, "Triton"), 
    MINOTAUR(4, 1.0f, "Minotaur"), 
    SKELETON(5, 1.0f, "Skeleton"), 
    SPECTER(6, 1.0f, "Specter"), 
    MONKEY(7, 1.0f, "Monkey"), 
    GOLEM(8, 1.0f, "Golem");
    
    int model;
    float size;
    String name;
    
    private EnumRace(final int model, final float size, final String name) {
        this.model = model;
        this.size = size;
        this.name = name;
    }
    
    public static String[] getNames() {
        final EnumRace[] states = values();
        final String[] names = new String[states.length];
        for (int i = 0; i < states.length; ++i) {
            names[i] = states[i].name;
        }
        return names;
    }
}
