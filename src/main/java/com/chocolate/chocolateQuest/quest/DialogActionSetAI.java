package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;

public class DialogActionSetAI extends DialogAction
{
    String[] names;
    
    public DialogActionSetAI() {
        this.names = EnumAiState.getNames();
    }
    
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        npc.AIMode = this.operator;
        npc.setAIForCurrentMode();
    }
    
    @Override
    public byte getType() {
        return 12;
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
    public boolean hasOperator() {
        return true;
    }
    
    @Override
    public int getSelectorForOperator() {
        return 2;
    }
    
    @Override
    public String[] getOptionsForOperator() {
        return this.names;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
