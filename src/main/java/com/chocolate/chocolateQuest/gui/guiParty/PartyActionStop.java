package com.chocolate.chocolateQuest.gui.guiParty;

import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class PartyActionStop extends PartyAction
{
    public PartyActionStop(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public void execute(final EntityHumanBase e) {
        e.currentPos = null;
        e.setAttackTarget(null);
    }
}
