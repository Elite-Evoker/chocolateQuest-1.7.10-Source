package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionInventory extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        npc.openInventory(player);
    }
    
    @Override
    public byte getType() {
        return 2;
    }
    
    @Override
    public boolean hasName() {
        return false;
    }
    
    @Override
    public boolean hasValue() {
        return false;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
