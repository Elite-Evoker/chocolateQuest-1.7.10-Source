package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionLocalVariable extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final String name = this.name;
        if (name.contains("@sp")) {
            name.replace("@sp", player.getCommandSenderName());
        }
        int var = 0;
        if (npc.npcVariables != null) {
            var = npc.npcVariables.getInteger(name);
        }
        final int value = this.value;
        return this.matches(var, value);
    }
    
    @Override
    public byte getType() {
        return 0;
    }
    
    @Override
    public String getNameForName() {
        return "Variable name";
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
