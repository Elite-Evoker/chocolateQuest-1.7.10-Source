package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.InventoryCargo;

public class InventoryShop extends InventoryCargo
{
    EntityHumanNPC human;
    int tempid;
    ShopRecipe[] trades;
    final int NEW_TRADE_ITEM = 18;
    
    public InventoryShop(final EntityHumanNPC human) {
        this.human = human;
        this.cargoItems = new ItemStack[this.getSizeInventory()];
        this.updateCargo();
    }
    
    public void updateCargo() {
        this.trades = this.human.getRecipes();
        if (this.trades != null) {
            for (int t = 0; t < this.cargoItems.length; ++t) {
                if (t < this.trades.length) {
                    this.cargoItems[t] = this.trades[t].tradedItem;
                }
                else {
                    this.cargoItems[t] = null;
                }
            }
        }
    }
    
    public boolean setShopRecipe(final int i) {
        if (this.cargoItems[18] == null) {
            return false;
        }
        final ItemStack tradedItem = this.cargoItems[18];
        int costItemsAmmount = 0;
        for (int f = 1; f < 4; ++f) {
            if (this.cargoItems[18 + f] != null) {
                ++costItemsAmmount;
            }
        }
        if (costItemsAmmount == 0) {
            return false;
        }
        final ItemStack[] costItems = new ItemStack[costItemsAmmount];
        int current = 0;
        for (int f2 = 1; f2 < 4; ++f2) {
            final ItemStack currentItem = this.cargoItems[18 + f2];
            if (currentItem != null) {
                costItems[current] = currentItem;
                ++current;
            }
        }
        this.human.setRecipes(i, new ShopRecipe(tradedItem, costItems));
        this.updateCargo();
        return true;
    }
    
    public void removeShopRecipe(final int i) {
        if (this.cargoItems[i] != null) {
            this.trades[i] = null;
            this.trades = this.removeNullEntries(this.trades);
            this.human.setRecipes(this.trades);
            this.updateCargo();
        }
    }
    
    public ShopRecipe getShopRecipe(final int i) {
        if (i >= this.trades.length) {
            return null;
        }
        return this.trades[i];
    }
    
    @Override
    public int getSizeInventory() {
        return 22;
    }
    
    @Override
    public void updateInventory() {
    }
    
    @Override
    public ItemStack decrStackSize(final int i, final int j) {
        if (i >= 18) {
            return super.decrStackSize(i, j);
        }
        final ItemStack is = ItemStack.copyItemStack(this.cargoItems[i]);
        if (is.stackSize <= 0) {
            is.stackSize = 1;
        }
        return is;
    }
    
    @Override
    public void setInventorySlotContents(final int i, final ItemStack itemstack) {
        if (i >= 18) {
            super.setInventorySlotContents(i, itemstack);
        }
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int i) {
        return super.getStackInSlotOnClosing(i);
    }
    
    public ShopRecipe[] removeNullEntries(final ShopRecipe[] original) {
        int entries = 0;
        for (int i = 0; i < original.length; ++i) {
            if (original[i] != null) {
                ++entries;
            }
        }
        if (entries == 0) {
            return null;
        }
        final ShopRecipe[] newStacks = new ShopRecipe[entries];
        for (int j = 0; j < newStacks.length; ++j) {
            ShopRecipe nextStack = null;
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
