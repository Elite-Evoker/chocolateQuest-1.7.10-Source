package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;

public class DialogActionSetTimer extends DialogAction
{
    String[] names;
    
    public DialogActionSetTimer() {
        this.names = EnumAiState.getNames();
    }
    
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        if (!this.name.isEmpty()) {
            npc.setTimeCounter(this.name, this.value);
        }
    }
    
    @Override
    public byte getType() {
        return 16;
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
        return false;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("Creates a timer with the specified name for this npc");
        list.add("counting down every tick and starting from the specified value");
    }
}
