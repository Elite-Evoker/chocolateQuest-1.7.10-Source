package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionKillCounter extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final int var = ReputationManager.instance.getKillAmmount(this.name, player.getCommandSenderName());
        final int value = this.value;
        return this.matches(var, value);
    }
    
    @Override
    public byte getType() {
        return 8;
    }
    
    @Override
    public String getNameForName() {
        return "KillCounter";
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
