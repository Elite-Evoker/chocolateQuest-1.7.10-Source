package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionSetCurrentPositionHome extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        npc.setHomeArea(MathHelper.floor_double(npc.posX), MathHelper.floor_double(npc.posY), MathHelper.floor_double(npc.posZ), (int)npc.getHomeDistance());
    }
    
    @Override
    public byte getType() {
        return 17;
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
        list.add("Sets the current npc position as home position");
    }
}
