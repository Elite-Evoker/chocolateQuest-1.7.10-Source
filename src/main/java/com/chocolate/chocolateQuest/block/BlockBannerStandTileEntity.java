package com.chocolate.chocolateQuest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTBase;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class BlockBannerStandTileEntity extends TileEntity
{
    public boolean hasFlag;
    public int rotation;
    public ItemStack item;
    
    public BlockBannerStandTileEntity() {
        this.hasFlag = false;
        this.rotation = 0;
    }
    
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().addCoord(0.0, 1.0, 0.0);
    }
    
    public void updateEntity() {
    }
    
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.rotation = nbttagcompound.getInteger("rot");
        this.hasFlag = nbttagcompound.getBoolean("flag");
        final NBTTagCompound itemNBT = (NBTTagCompound)nbttagcompound.getTag("Item");
        if (itemNBT != null) {
            this.item = ItemStack.loadItemStackFromNBT(itemNBT);
        }
        else if (this.hasFlag) {
            this.item = new ItemStack(ChocolateQuest.banner, 1, nbttagcompound.getInteger("type"));
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("rot", this.rotation);
        if (this.item != null) {
            final NBTTagCompound itemNBT = new NBTTagCompound();
            nbttagcompound.setTag("Item", (NBTBase)this.item.writeToNBT(itemNBT));
            nbttagcompound.setInteger("type", this.item.getMetadata());
        }
        nbttagcompound.setBoolean("flag", this.hasFlag);
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        data.setInteger("rot", this.rotation);
        if (this.item != null) {
            final NBTTagCompound itemNBT = new NBTTagCompound();
            data.setTag("Item", (NBTBase)this.item.writeToNBT(itemNBT));
        }
        data.setBoolean("flag", this.hasFlag);
        return (Packet)new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, data);
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound tag = packet.getNbtCompound();
        this.rotation = tag.getInteger("rot");
        final NBTTagCompound itemNBT = (NBTTagCompound)tag.getTag("Item");
        if (itemNBT != null) {
            this.item = ItemStack.loadItemStackFromNBT(itemNBT);
        }
        this.hasFlag = tag.getBoolean("flag");
    }
    
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 6400.0;
    }
}
