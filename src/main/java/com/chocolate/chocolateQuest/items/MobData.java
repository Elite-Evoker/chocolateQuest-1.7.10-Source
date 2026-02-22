package com.chocolate.chocolateQuest.items;

class MobData
{
    Class mobClass;
    String name;
    int color;
    
    public MobData(final Class mob, final String name, final int color) {
        this.mobClass = mob;
        this.name = name;
        this.color = color;
    }
}
