package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionTime extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final long var = npc.worldObj.getWorldTime() % 24000L;
        final int value = this.value;
        return this.matches(var, value);
    }
    
    @Override
    public byte getType() {
        return 6;
    }
    
    @Override
    public boolean hasName() {
        return false;
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
    }
}
