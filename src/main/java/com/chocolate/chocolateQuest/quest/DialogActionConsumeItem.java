package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionConsumeItem extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        final IInventory inventory = (IInventory)player.inventory;
        final ItemStack checkedItem = BDHelper.getStackFromString(this.name);
        if (checkedItem != null) {
            for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                ItemStack item = inventory.getStackInSlot(i);
                if (item != null && item.getItem() == checkedItem.getItem() && item.getMetadata() == checkedItem.getMetadata() && item.stackSize >= checkedItem.stackSize && item.stackTagCompound == checkedItem.stackTagCompound) {
                    final int stacksRemoved = item.stackSize;
                    final ItemStack itemStack = item;
                    itemStack.stackSize -= checkedItem.stackSize;
                    final ItemStack itemStack2 = checkedItem;
                    itemStack2.stackSize -= stacksRemoved;
                    if (item.stackSize <= 0) {
                        item = null;
                    }
                    inventory.setInventorySlotContents(i, item);
                    if (checkedItem.stackSize <= 0) {
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public byte getType() {
        return 8;
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
        list.add("Consumes an item from the player inventory");
    }
}
