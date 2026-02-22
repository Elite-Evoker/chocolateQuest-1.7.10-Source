package com.chocolate.chocolateQuest.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;

public class InventoryCargo implements IInventory
{
    protected ItemStack[] cargoItems;
    
    public int getSizeInventory() {
        return 6;
    }
    
    public void updateInventory() {
    }
    
    public ItemStack getStackInSlot(final int i) {
        return this.cargoItems[i];
    }
    
    public ItemStack decrStackSize(final int i, final int j) {
        if (this.cargoItems[i] == null) {
            return null;
        }
        if (this.cargoItems[i].stackSize <= j) {
            final ItemStack itemstack = this.cargoItems[i];
            this.cargoItems[i] = null;
            return itemstack;
        }
        final ItemStack itemstack2 = this.cargoItems[i].splitStack(j);
        if (this.cargoItems[i].stackSize == 0) {
            this.cargoItems[i] = null;
        }
        return itemstack2;
    }
    
    public ItemStack getStackInSlotOnClosing(final int i) {
        if (this.cargoItems[i] != null) {
            final ItemStack is = this.cargoItems[i];
            this.cargoItems[i] = null;
            return is;
        }
        return null;
    }
    
    public void setInventorySlotContents(final int i, final ItemStack itemstack) {
        this.cargoItems[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        this.updateInventory();
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return true;
    }
    
    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }
    
    public void closeChest() {
        this.updateInventory();
    }
    
    public String getInventoryName() {
        return "NPC inventory";
    }
    
    public boolean isCustomInventoryName() {
        return false;
    }
    
    public void markDirty() {
    }
    
    public void openChest() {
    }
}
