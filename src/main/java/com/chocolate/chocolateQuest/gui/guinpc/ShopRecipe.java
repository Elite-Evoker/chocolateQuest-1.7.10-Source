package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;

public class ShopRecipe
{
    public ItemStack tradedItem;
    public ItemStack[] costItems;
    
    public ShopRecipe(final ItemStack tradedItem, final ItemStack[] costItems) {
        this.tradedItem = tradedItem;
        this.costItems = costItems;
    }
    
    public ShopRecipe(final NBTTagCompound nbt) {
        this.readFromNBT(nbt);
    }
    
    public ShopRecipe(final NBTTagCompound nbt, final boolean readWithMapping) {
        if (readWithMapping) {
            this.readFromNBTWithMapping(nbt);
        }
        else {
            this.readFromNBT(nbt);
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        final NBTTagList priceListTag = (NBTTagList)nbt.getTag("items");
        this.tradedItem = ItemStack.loadItemStackFromNBT(priceListTag.getCompoundTagAt(0));
        this.costItems = new ItemStack[priceListTag.tagCount() - 1];
        for (int i = 1; i < priceListTag.tagCount(); ++i) {
            this.costItems[i - 1] = ItemStack.loadItemStackFromNBT(priceListTag.getCompoundTagAt(i));
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        final NBTTagList priceListTag = new NBTTagList();
        final NBTTagCompound tradedItemTag = new NBTTagCompound();
        this.tradedItem.writeToNBT(tradedItemTag);
        priceListTag.appendTag((NBTBase)tradedItemTag);
        for (int i = 1; i < this.costItems.length + 1; ++i) {
            final NBTTagCompound priceItemTag = new NBTTagCompound();
            this.costItems[i - 1].writeToNBT(priceItemTag);
            priceListTag.appendTag((NBTBase)priceItemTag);
        }
        nbt.setTag("items", (NBTBase)priceListTag);
    }
    
    public void readFromNBTWithMapping(final NBTTagCompound nbt) {
        final NBTTagList list = (NBTTagList)nbt.getTag("items");
        for (int i = 0; i < list.tagCount(); ++i) {
            final String id = list.getCompoundTagAt(i).getString("name");
            final Item item = (Item)Item.itemRegistry.getObject(id);
            if (item != null) {
                final short newID = (short)Item.getIdFromItem(item);
                list.getCompoundTagAt(i).setShort("id", newID);
            }
        }
        this.readFromNBT(nbt);
    }
    
    public void writeToNBTWithMapping(final NBTTagCompound nbt) {
        this.writeToNBT(nbt);
        final NBTTagList list = (NBTTagList)nbt.getTag("items");
        for (int i = 0; i < list.tagCount(); ++i) {
            String id = "";
            if (i == 0) {
                id = Item.itemRegistry.getNameForObject((Object)this.tradedItem.getItem());
            }
            else {
                id = Item.itemRegistry.getNameForObject((Object)this.costItems[i - 1].getItem());
            }
            list.getCompoundTagAt(i).setString("name", id);
        }
    }
}
