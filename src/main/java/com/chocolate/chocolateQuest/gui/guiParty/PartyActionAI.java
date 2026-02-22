package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class PartyActionAI extends PartyAction
{
    int AIMode;
    
    public PartyActionAI(final String name, final int icon, final int AIMode) {
        super(name, icon);
        this.AIMode = AIMode;
    }
    
    @Override
    public void execute(final EntityHumanBase e) {
        e.AIMode = this.AIMode;
        e.setAIForCurrentMode();
    }
}
