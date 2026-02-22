package com.chocolate.chocolateQuest.entity.npc;

class TimeCounter
{
    public String name;
    public int time;
    
    public TimeCounter(final String name, final int time) {
        this.name = name;
        this.time = time;
    }
    
    public void update() {
        if (this.time > 0) {
            --this.time;
        }
    }
}
