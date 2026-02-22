package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class PartyActionAICombat extends PartyAction
{
    int AIMode;
    
    public PartyActionAICombat(final String name, final int icon, final int AIMode) {
        super(name, icon);
        this.AIMode = AIMode;
    }
    
    @Override
    public void execute(final EntityHumanBase e) {
        e.AICombatMode = this.AIMode;
        e.setAIForCurrentMode();
    }
}
