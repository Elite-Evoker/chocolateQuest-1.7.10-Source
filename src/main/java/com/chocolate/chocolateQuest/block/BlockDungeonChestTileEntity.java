package com.chocolate.chocolateQuest.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class BlockDungeonChestTileEntity extends TileEntity implements IInventory
{
    ItemStack[] container;
    
    public BlockDungeonChestTileEntity() {
        this.container = new ItemStack[this.getSizeInventory()];
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeInventoryToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, data);
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
        final NBTTagCompound nbt = pkt.getNbtCompound();
        this.readInventoryFromNBT(nbt);
        super.onDataPacket(net, pkt);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.container = this.readInventoryFromNBT(nbt);
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeInventoryToNBT(nbt);
    }
    
    public void writeInventoryToNBT(final NBTTagCompound nbt) {
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.container.length; ++i) {
            if (this.container[i] != null) {
                final NBTTagCompound slotnbttagcompound = new NBTTagCompound();
                slotnbttagcompound.setByte("Slot", (byte)i);
                this.container[i].writeToNBT(slotnbttagcompound);
                list.appendTag((NBTBase)slotnbttagcompound);
            }
        }
        nbt.setTag("Items", (NBTBase)list);
    }
    
    public ItemStack[] readInventoryFromNBT(final NBTTagCompound nbt) {
        final ItemStack[] container = new ItemStack[this.getSizeInventory()];
        final NBTTagList list = nbt.getTagList("Items", (int)nbt.getId());
        if (list != null) {
            for (int i = 0; i < list.tagCount(); ++i) {
                final NBTTagCompound slotnbttagcompound = list.getCompoundTagAt(i);
                final int j = slotnbttagcompound.getByte("Slot") & 0xFF;
                if (j >= 0 && j < container.length) {
                    container[j] = ItemStack.loadItemStackFromNBT(slotnbttagcompound);
                }
            }
        }
        return container;
    }
    
    public void closeChest() {
    }
    
    public ItemStack decrStackSize(final int slot, final int amount) {
        if (this.container[slot] == null) {
            return null;
        }
        if (this.container[slot].stackSize <= amount) {
            final ItemStack itemstack = this.container[slot];
            this.container[slot] = null;
            return itemstack;
        }
        final ItemStack itemstack2 = this.container[slot].splitStack(amount);
        if (this.container[slot].stackSize == 0) {
            this.container[slot] = null;
        }
        return itemstack2;
    }
    
    public String getInventoryName() {
        return "Chest name";
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public int getSizeInventory() {
        return 27;
    }
    
    public ItemStack getStackInSlot(final int slot) {
        return this.container[slot];
    }
    
    public ItemStack getStackInSlotOnClosing(final int slot) {
        return null;
    }
    
    public boolean isCustomInventoryName() {
        return false;
    }
    
    public boolean isItemValidForSlot(final int slot, final ItemStack stack) {
        return false;
    }
    
    public boolean isUseableByPlayer(final EntityPlayer player) {
        return true;
    }
    
    public void openChest() {
    }
    
    public void setInventorySlotContents(final int slot, final ItemStack stack) {
        this.container[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }
}
