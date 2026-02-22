package com.chocolate.chocolateQuest.gui.slot;

import com.chocolate.chocolateQuest.gui.guinpc.ShopRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.gui.guinpc.InventoryShop;
import net.minecraft.inventory.Slot;

public class SlotShop extends Slot
{
    InventoryShop shopInventory;
    
    public SlotShop(final InventoryShop shopInventory, final int id, final int x, final int y) {
        super((IInventory)shopInventory, id, x, y);
        this.shopInventory = shopInventory;
    }
    
    public boolean isItemValid(final ItemStack is) {
        return false;
    }
    
    public void onPickupFromSlot(final EntityPlayer player, final ItemStack is) {
        super.onPickupFromSlot(player, is);
    }
    
    public void putStack(final ItemStack is) {
        super.putStack(is);
    }
    
    public boolean canTakeStack(final EntityPlayer player) {
        return true;
    }
    
    public ShopRecipe getRecipe() {
        return this.shopInventory.getShopRecipe(this.getSlotIndex());
    }
    
    public boolean hasRecipe(final EntityPlayer player, final boolean consumeItems) {
        final IInventory playerInv = (IInventory)player.inventory;
        final ShopRecipe recipe = this.getRecipe();
        if (recipe != null) {
            ItemStack[] tempRecipe = new ItemStack[recipe.costItems.length];
            for (int t = 0; t < tempRecipe.length; ++t) {
                tempRecipe[t] = recipe.costItems[t].copy();
            }
            for (int i = 0; i < playerInv.getSizeInventory(); ++i) {
                ItemStack currentStack = playerInv.getStackInSlot(i);
                if (currentStack != null) {
                    currentStack = new ItemStack(currentStack.getItem(), currentStack.stackSize, currentStack.getMetadata());
                    for (int t2 = 0; t2 < tempRecipe.length; ++t2) {
                        if (this.areItemsEqual(currentStack, tempRecipe[t2])) {
                            final int recipeAmmount = tempRecipe[t2].stackSize;
                            tempRecipe[t2] = this.decreaseStackSize(currentStack.stackSize, tempRecipe[t2]);
                            currentStack = this.decreaseStackSize(recipeAmmount, currentStack);
                            if (consumeItems) {
                                playerInv.setInventorySlotContents(i, currentStack);
                            }
                            if (currentStack == null) {
                                break;
                            }
                        }
                    }
                    tempRecipe = this.removeNullEntries(tempRecipe);
                    if (tempRecipe == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public ItemStack decreaseStackSize(final int stackDecrease, final ItemStack stack) {
        final int newSize = stack.stackSize - stackDecrease;
        if (newSize <= 0) {
            return null;
        }
        stack.stackSize = newSize;
        return stack;
    }
    
    public boolean areItemsEqual(final ItemStack is1, final ItemStack is2) {
        return is1.isItemEqual(is2) && is1.stackTagCompound == is2.stackTagCompound;
    }
    
    public ItemStack[] removeNullEntries(final ItemStack[] original) {
        int entries = 0;
        for (int i = 0; i < original.length; ++i) {
            if (original[i] != null) {
                ++entries;
            }
        }
        if (entries == 0) {
            return null;
        }
        final ItemStack[] newStacks = new ItemStack[entries];
        for (int j = 0; j < newStacks.length; ++j) {
            ItemStack nextStack = null;
            for (int t = 0; t < original.length; ++t) {
                if (original[t] != null) {
                    nextStack = original[t];
                    original[t] = null;
                    break;
                }
            }
            newStacks[j] = nextStack;
        }
        return newStacks;
    }
}
