package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionSetOwner extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        final String name = this.operateName(player);
        final EntityPlayer targetPlayer = player.worldObj.getPlayerEntityByName(name);
        if (npc.getOwner() == targetPlayer) {
            npc.followTime += this.value;
        }
        else {
            npc.followTime = this.value;
        }
        npc.setOwner((EntityLivingBase)targetPlayer);
    }
    
    @Override
    public String getNameForName() {
        return "Player name ( @sp for speaking player, empty to clear owner)";
    }
    
    @Override
    public byte getType() {
        return 10;
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
    public String getNameForValue() {
        return "Owned ticks, 0 for unlimited time";
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
