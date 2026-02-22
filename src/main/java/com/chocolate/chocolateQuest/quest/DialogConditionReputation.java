package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionReputation extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final int var = ReputationManager.instance.getPlayerReputation(player.getCommandSenderName(), this.name);
        final int value = this.value;
        return this.matches(var, value);
    }
    
    @Override
    public byte getType() {
        return 3;
    }
    
    @Override
    public String getNameForName() {
        return "Faction name";
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
