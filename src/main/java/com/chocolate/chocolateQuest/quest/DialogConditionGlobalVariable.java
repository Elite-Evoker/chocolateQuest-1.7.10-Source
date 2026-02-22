package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionGlobalVariable extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final String name = this.name;
        if (name.contains("@sp")) {
            name.replace("@sp", player.getCommandSenderName());
        }
        final int var = ReputationManager.instance.getGlobal(name);
        final int value = this.value;
        return this.matches(var, value);
    }
    
    @Override
    public byte getType() {
        return 1;
    }
    
    @Override
    public String getNameForName() {
        return "Variable name";
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
