package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionNPCTimer extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final long var = npc.getTimeCounter(this.name);
        final int value = this.value;
        return this.matches(var, value);
    }
    
    @Override
    public byte getType() {
        return 10;
    }
    
    @Override
    public boolean hasName() {
        return true;
    }
    
    @Override
    public boolean hasValue() {
        return true;
    }
    
    @Override
    public String getNameForValue() {
        return "Time";
    }
    
    @Override
    public boolean hasOperator() {
        return true;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("Checks if the timer with selected name has (operation) value than the value specified");
    }
}
