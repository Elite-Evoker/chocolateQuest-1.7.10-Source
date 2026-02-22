package com.chocolate.chocolateQuest.gui.guiParty;

import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class PartyActionUnmount extends PartyAction
{
    public PartyActionUnmount(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public void execute(final EntityHumanBase e) {
        if (e.ridingEntity != null) {
            e.mountEntity(null);
        }
    }
}
