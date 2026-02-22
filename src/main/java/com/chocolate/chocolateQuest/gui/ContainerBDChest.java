package com.chocolate.chocolateQuest.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerBDChest extends Container
{
    protected EntityPlayer player;
    protected IInventory chest;
    
    public ContainerBDChest(final IInventory playerInventory, final IInventory chestInventory) {
        this.chest = chestInventory;
        this.player = ((InventoryPlayer)playerInventory).player;
        chestInventory.openChest();
        this.layoutContainer(playerInventory, chestInventory);
    }
    
    public boolean canInteractWith(final EntityPlayer player) {
        return this.chest.isUseableByPlayer(player);
    }
    
    public void onContainerClosed(final EntityPlayer entityplayer) {
        this.chest.closeChest();
        super.onContainerClosed(entityplayer);
    }
    
    protected void layoutContainer(final IInventory playerInventory, final IInventory chestInventory) {
        this.layoutPlayerInventory(playerInventory);
        this.layoutInventory(chestInventory);
    }
    
    public void layoutPlayerInventory(final IInventory playerInventory) {
        final int leftCol = 8;
        final int yPos = this.getPlayerInventoryY();
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, leftCol + col * 18, 0 + row * 18 + yPos));
            }
        }
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
            this.addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, 58 + yPos));
        }
    }
    
    public int getPlayerInventoryY() {
        return 60;
    }
    
    public void layoutInventory(final IInventory chestInventory) {
        for (int chestRow = 0; chestRow < this.getRowCount(); ++chestRow) {
            for (int chestCol = 0; chestCol < this.getRowLength(); ++chestCol) {
                final int index = chestCol + chestRow * this.getRowLength();
                this.addSlotToContainer(new Slot(chestInventory, index, 8 + chestCol * 18, 18 + chestRow * 18));
            }
        }
    }
    
    public int getRowLength() {
        return 9;
    }
    
    public int getRowCount() {
        return 0;
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer player, final int index) {
        ItemStack slotItemStackCopy = null;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotItemStack = slot.getStack();
            final int playerInventorySize = this.inventorySlots.size() - this.chest.getSizeInventory();
            if (index < playerInventorySize) {
                for (int i = 0; i < this.inventorySlots.size() - playerInventorySize; ++i) {
                    final Slot chestSlot = this.inventorySlots.get(playerInventorySize + i);
                    if (chestSlot.isItemValid(slotItemStack)) {
                        if (chestSlot.getStack() == null) {
                            final int restingItems = slotItemStack.stackSize - this.chest.getInventoryStackLimit();
                            if (restingItems > 0) {
                                slotItemStackCopy = slotItemStack.splitStack(restingItems);
                            }
                            else {
                                slotItemStackCopy = chestSlot.getStack();
                            }
                            chestSlot.putStack(slotItemStack);
                            slot.putStack(slotItemStackCopy);
                            slot.onSlotChanged();
                            chestSlot.onSlotChanged();
                            return null;
                        }
                        if (chestSlot.getStack().isStackable() && chestSlot.getStack().isItemEqual(slotItemStack) && chestSlot.getStack().stackSize < this.chest.getInventoryStackLimit()) {
                            final int chestEmptyStacks = this.chest.getInventoryStackLimit() - chestSlot.getStack().stackSize;
                            final int restingItems2 = slotItemStack.stackSize - chestEmptyStacks;
                            if (restingItems2 > 0) {
                                slotItemStackCopy = slotItemStack.splitStack(restingItems2);
                                final ItemStack stack = chestSlot.getStack();
                                stack.stackSize += slotItemStack.stackSize;
                            }
                            else {
                                final ItemStack stack2 = chestSlot.getStack();
                                stack2.stackSize += slotItemStack.stackSize;
                                slotItemStackCopy = null;
                            }
                            slot.putStack(slotItemStackCopy);
                            slot.onSlotChanged();
                            chestSlot.onSlotChanged();
                            return null;
                        }
                    }
                }
            }
            else if (!this.mergeItemStack(slotItemStack, 0, playerInventorySize, true)) {
                return null;
            }
            if (slotItemStack.stackSize == 0) {
                slot.onPickupFromSlot(player, slotItemStack);
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            return null;
        }
        return slotItemStackCopy;
    }
}
