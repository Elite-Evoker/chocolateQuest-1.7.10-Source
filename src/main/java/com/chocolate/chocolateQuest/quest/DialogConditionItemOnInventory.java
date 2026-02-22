package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionItemOnInventory extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final IInventory inventory = (IInventory)player.inventory;
        final ItemStack checkedItem = BDHelper.getStackFromString(this.name);
        if (checkedItem != null) {
            for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                final ItemStack item = inventory.getStackInSlot(i);
                if (item != null && item.getItem() == checkedItem.getItem() && item.getMetadata() == checkedItem.getMetadata() && item.stackSize >= checkedItem.stackSize) {
                    if (!item.hasTagCompound()) {
                        return true;
                    }
                    if (BDHelper.compareTags(checkedItem.getTagCompound(), item.stackTagCompound)) {
                        return true;
                    }
                }
            }
        }
        return false;
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
    public int getSelectorForName() {
        return 3;
    }
    
    @Override
    public boolean hasValue() {
        return false;
    }
    
    @Override
    public boolean hasOperator() {
        return false;
    }
    
    @Override
    public String getNameForOperator() {
        return "Consume item?";
    }
    
    @Override
    public int getSelectorForOperator() {
        return 1;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("This condition will pass if the selected item is on the player inventory");
        list.add("Sintax: itemName ammount damage {tags}");
    }
}
