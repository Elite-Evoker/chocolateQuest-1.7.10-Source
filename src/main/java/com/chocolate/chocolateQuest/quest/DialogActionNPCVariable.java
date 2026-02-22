package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionNPCVariable extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        final String name = this.name;
        if (name.contains("@sp")) {
            name.replace("@sp", player.getCommandSenderName());
        }
        final int newValue = this.operateValue(npc.npcVariables.getInteger(name));
        npc.npcVariables.setInteger(name, newValue);
    }
    
    @Override
    public byte getType() {
        return 4;
    }
    
    @Override
    public boolean hasName() {
        return true;
    }
    
    @Override
    public boolean hasValue() {
        return true;
    }
    
    @Override
    public boolean hasOperator() {
        return true;
    }
    
    @Override
    public String getNameForName() {
        return "Variable name";
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("Only the owner NPC has access to this variable.");
        list.add("@sp in the name will be replaced by the name of the player speaking to.");
        list.add("So you can use @sp in the name to create player related variables.");
    }
}
