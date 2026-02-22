package com.chocolate.chocolateQuest.block;

import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class BlockAltarTileEntity extends TileEntity
{
    public ItemStack item;
    public int rotation;
    
    public BlockAltarTileEntity() {
        this.rotation = 0;
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
        final NBTTagCompound itemNBT = (NBTTagCompound)nbttagcompound.getTag("CItem");
        if (itemNBT != null) {
            this.item = ItemStack.loadItemStackFromNBT(itemNBT);
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("rot", this.rotation);
        if (this.item != null) {
            final NBTTagCompound itemNBT = new NBTTagCompound();
            nbttagcompound.setTag("CItem", (NBTBase)this.item.writeToNBT(itemNBT));
        }
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        data.setInteger("rot", this.rotation);
        if (this.item != null) {
            final NBTTagCompound itemNBT = new NBTTagCompound();
            data.setTag("CItem", (NBTBase)this.item.writeToNBT(itemNBT));
        }
        return (Packet)new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, data);
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound tag = packet.getNbtCompound();
        this.rotation = tag.getInteger("rot");
        final NBTTagCompound itemNBT = (NBTTagCompound)tag.getTag("CItem");
        if (itemNBT != null) {
            this.item = ItemStack.loadItemStackFromNBT(itemNBT);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 6400.0;
    }
    
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().addCoord(0.0, -1.0, 0.0);
    }
}
