package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionGiveItem extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        final ItemStack item = BDHelper.getStackFromString(this.name);
        if (item != null && !player.inventory.addItemStackToInventory(item)) {
            player.dropPlayerItemWithRandomChoice(item, true);
        }
    }
    
    @Override
    public byte getType() {
        return 7;
    }
    
    @Override
    public boolean hasName() {
        return true;
    }
    
    @Override
    public int getSelectorForName() {
        return 3;
    }
    
    @Override
    public boolean hasValue() {
        return false;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
