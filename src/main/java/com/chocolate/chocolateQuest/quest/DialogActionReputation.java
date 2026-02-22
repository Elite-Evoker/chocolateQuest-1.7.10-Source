package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionReputation extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        ReputationManager.instance.addReputation(player, this.name, this.value);
    }
    
    @Override
    public byte getType() {
        return 6;
    }
    
    @Override
    public boolean hasName() {
        return true;
    }
    
    @Override
    public String getNameForName() {
        return "Faction name";
    }
    
    @Override
    public boolean hasValue() {
        return true;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
