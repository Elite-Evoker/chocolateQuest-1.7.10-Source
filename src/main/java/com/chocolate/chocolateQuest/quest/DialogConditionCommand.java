package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionCommand extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final int var = npc.executeCommand(this.name);
        final int value = this.value;
        return this.matches(var, value);
    }
    
    @Override
    public byte getType() {
        return 7;
    }
    
    @Override
    public String getNameForName() {
        return "Command";
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("Executes a testfor command and compares the result with the value");
    }
}
