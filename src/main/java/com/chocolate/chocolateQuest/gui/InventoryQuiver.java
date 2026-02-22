package com.chocolate.chocolateQuest.gui;

import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventoryQuiver extends InventoryBag
{
    public InventoryQuiver(final ItemStack items, final EntityPlayer player) {
        super(items, player);
    }
    
    @Override
    public int getSizeInventory() {
        return 9;
    }
    
    @Override
    public String getInventoryName() {
        return "Quiver";
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int i) {
        if (this.cargoItems[i] != null && this.cargoItems[i].getItem() != Items.arrow) {
            final ItemStack temp = this.cargoItems[i];
            this.cargoItems[i] = null;
            return temp;
        }
        return null;
    }
}
