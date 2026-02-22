package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionSetItem extends DialogAction
{
    String[] slots;
    
    public DialogActionSetItem() {
        this.slots = new String[] { "Right hand", "Boots", "Leggings", "Plate", "Helmet", "Left hand" };
    }
    
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        final ItemStack checkedItem = BDHelper.getStackFromString(this.name);
        npc.setCurrentItemOrArmor(this.operator, checkedItem);
    }
    
    @Override
    public byte getType() {
        return 11;
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
    public boolean hasOperator() {
        return true;
    }
    
    @Override
    public int getSelectorForOperator() {
        return 2;
    }
    
    @Override
    public String[] getOptionsForOperator() {
        return this.slots;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("Select an item to equip in the selected slot");
    }
}
