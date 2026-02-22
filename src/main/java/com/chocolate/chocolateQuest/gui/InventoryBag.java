package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import net.minecraft.nbt.NBTBase;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.nbt.NBTTagList;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;

public class InventoryBag implements IInventory
{
    ItemStack[] cargoItems;
    ItemStack container;
    EntityPlayer player;
    int tempid;
    
    public InventoryBag(final ItemStack items, final EntityPlayer player) {
        this.player = player;
        this.container = items;
        this.cargoItems = new ItemStack[this.getSizeInventory()];
        if (items.stackTagCompound == null) {
            items.stackTagCompound = new NBTTagCompound();
        }
        final NBTTagList nbttaglist = items.stackTagCompound.getTagList("Items", (int)items.stackTagCompound.getId());
        if (nbttaglist != null) {
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                final NBTTagCompound slotnbttagcompound = nbttaglist.getCompoundTagAt(i);
                final int j = slotnbttagcompound.getByte("Slot") & 0xFF;
                if (j >= 0 && j < this.cargoItems.length) {
                    this.cargoItems[j] = ItemStack.loadItemStackFromNBT(slotnbttagcompound);
                }
            }
        }
        else {
            this.cargoItems = new ItemStack[this.getSizeInventory()];
            this.markDirty();
        }
        this.tempid = new Random().nextInt();
        this.container.stackTagCompound.setInteger("tempid", this.tempid);
    }
    
    public static ItemStack[] getCargo(final ItemStack is) {
        if (is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
        }
        ItemStack[] cargoItems = new ItemStack[getSizeInventory(is)];
        final NBTTagList nbttaglist = is.stackTagCompound.getTagList("Items", (int)is.stackTagCompound.getId());
        if (nbttaglist != null) {
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                final NBTTagCompound slotnbttagcompound = nbttaglist.getCompoundTagAt(i);
                final int j = slotnbttagcompound.getByte("Slot") & 0xFF;
                if (j >= 0 && j < cargoItems.length) {
                    cargoItems[j] = ItemStack.loadItemStackFromNBT(slotnbttagcompound);
                    if (cargoItems[j] == null && !slotnbttagcompound.hasNoTags()) {
                        cargoItems[j] = new ItemStack(ChocolateQuest.spell, 1, (int)slotnbttagcompound.getShort("Damage"));
                    }
                }
            }
        }
        else {
            cargoItems = new ItemStack[getSizeInventory(is)];
        }
        return cargoItems;
    }
    
    public static void saveCargo(final ItemStack container, final ItemStack[] cargoItems) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < cargoItems.length; ++i) {
            if (cargoItems[i] != null) {
                final NBTTagCompound slotnbttagcompound = new NBTTagCompound();
                slotnbttagcompound.setByte("Slot", (byte)i);
                cargoItems[i].writeToNBT(slotnbttagcompound);
                nbttaglist.appendTag((NBTBase)slotnbttagcompound);
            }
        }
        container.stackTagCompound.setTag("Items", (NBTBase)nbttaglist);
    }
    
    public static int getSizeInventory(final ItemStack is) {
        if (is.getItem() instanceof ILoadableGun) {
            return ((ILoadableGun)is.getItem()).getAmmoLoaderAmmount(is);
        }
        return is.getMetadata() * 9 + 9;
    }
    
    public int getSizeInventory() {
        return getSizeInventory(this.container);
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
        this.markDirty();
        return itemstack2;
    }
    
    public ItemStack getStackInSlotOnClosing(final int i) {
        if (this.cargoItems[i] != null && this.cargoItems[i].getItem() == this.container.getItem()) {
            final ItemStack temp = this.cargoItems[i];
            this.cargoItems[i] = null;
            this.saveToNBT(this.container);
            return temp;
        }
        return null;
    }
    
    public void setInventorySlotContents(final int i, final ItemStack itemstack) {
        this.cargoItems[i] = itemstack;
    }
    
    public boolean isCustomInventoryName() {
        return false;
    }
    
    public String getInventoryName() {
        return "Bag";
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public void markDirty() {
        this.closeChest();
        this.saveToNBT(this.container);
    }
    
    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return entityplayer.inventory.getCurrentItem() != null && (entityplayer.inventory.getItemStack() == null || entityplayer.inventory.getItemStack() != this.container) && this.container.isItemEqual(entityplayer.inventory.getCurrentItem());
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
        final ItemStack bag = this.getBag();
        this.saveToNBT(bag);
    }
    
    public void saveToNBT(final ItemStack container) {
        if (container == null) {
            return;
        }
        if (container.stackTagCompound == null || container.stackTagCompound.getInteger("tempid") != this.tempid) {
            return;
        }
        saveCargo(container, this.cargoItems);
    }
    
    public ItemStack getBag() {
        ItemStack container = null;
        for (int i = 0; i < this.player.inventory.getSizeInventory(); ++i) {
            final ItemStack currentItemStack = this.player.inventory.getStackInSlot(i);
            if (currentItemStack != null && currentItemStack.stackTagCompound != null && currentItemStack.stackTagCompound.getInteger("tempid") == this.tempid) {
                container = currentItemStack;
                break;
            }
        }
        if (container == null) {
            final ItemStack currentItemStack2 = this.player.inventory.getItemStack();
            if (currentItemStack2 != null && currentItemStack2.stackTagCompound != null && currentItemStack2.stackTagCompound.getInteger("tempid") == this.tempid) {
                container = currentItemStack2;
            }
        }
        return container;
    }
    
    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }
}
