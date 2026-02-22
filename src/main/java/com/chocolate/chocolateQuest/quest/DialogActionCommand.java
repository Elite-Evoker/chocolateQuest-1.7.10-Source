package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionCommand extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        npc.executeCommand(this.name);
    }
    
    @Override
    public byte getType() {
        return 9;
    }
    
    @Override
    public boolean hasName() {
        return true;
    }
    
    @Override
    public String getNameForName() {
        return "Command";
    }
    
    @Override
    public boolean hasValue() {
        return false;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
