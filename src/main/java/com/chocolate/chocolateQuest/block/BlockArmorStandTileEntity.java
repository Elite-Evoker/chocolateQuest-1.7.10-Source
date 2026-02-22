package com.chocolate.chocolateQuest.block;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class BlockArmorStandTileEntity extends TileEntity implements IInventory
{
    public ItemStack[] cargoItems;
    public int rotation;
    
    public BlockArmorStandTileEntity() {
        this.rotation = 0;
        this.cargoItems = new ItemStack[6];
    }
    
    public boolean anyPlayerInRange() {
        return this.worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 16.0) != null;
    }
    
    public void updateEntity() {
        super.updateEntity();
    }
    
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.rotation = nbttagcompound.getInteger("rot");
        for (int i = 0; i < this.cargoItems.length; ++i) {
            final NBTTagCompound itemNBT = (NBTTagCompound)nbttagcompound.getTag("CItem" + i);
            if (itemNBT != null) {
                this.cargoItems[i] = ItemStack.loadItemStackFromNBT(itemNBT);
            }
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("rot", this.rotation);
        for (int i = 0; i < this.cargoItems.length; ++i) {
            if (this.cargoItems[i] != null) {
                final NBTTagCompound itemNBT = new NBTTagCompound();
                nbttagcompound.setTag("CItem" + i, (NBTBase)this.cargoItems[i].writeToNBT(itemNBT));
            }
        }
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        data.setInteger("rot", this.rotation);
        for (int i = 0; i < this.cargoItems.length; ++i) {
            if (this.cargoItems[i] != null) {
                final NBTTagCompound itemNBT = new NBTTagCompound();
                data.setTag("CItem" + i, (NBTBase)this.cargoItems[i].writeToNBT(itemNBT));
            }
        }
        return (Packet)new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, data);
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound tag = packet.getNbtCompound();
        this.rotation = tag.getInteger("rot");
        for (int i = 0; i < this.cargoItems.length; ++i) {
            final NBTTagCompound item = (NBTTagCompound)tag.getTag("CItem" + i);
            if (item != null) {
                this.cargoItems[i] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 6400.0;
    }
    
    public int getSizeInventory() {
        return this.cargoItems.length;
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
