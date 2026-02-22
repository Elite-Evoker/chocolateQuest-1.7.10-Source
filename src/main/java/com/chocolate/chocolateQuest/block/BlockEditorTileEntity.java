package com.chocolate.chocolateQuest.block;

import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BlockEditorTileEntity extends TileEntity
{
    public int red;
    public int yellow;
    public int height;
    public String name;
    
    public BlockEditorTileEntity() {
        this.red = 15;
        this.yellow = 15;
        this.height = 20;
        this.name = "Template";
    }
    
    public boolean anyPlayerInRange() {
        return this.worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 16.0) != null;
    }
    
    public void updateEntity() {
        super.updateEntity();
    }
    
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.red = par1NBTTagCompound.getInteger("red");
        this.yellow = par1NBTTagCompound.getInteger("yellow");
        this.height = par1NBTTagCompound.getInteger("height");
        this.setName(par1NBTTagCompound.getString("name"));
    }
    
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("red", this.red);
        par1NBTTagCompound.setInteger("yellow", this.yellow);
        par1NBTTagCompound.setInteger("height", this.height);
        par1NBTTagCompound.setString("name", this.getName());
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return (Packet)new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound tag = packet.getNbtCompound();
        this.red = tag.getInteger("red");
        this.yellow = tag.getInteger("yellow");
        this.height = tag.getInteger("height");
        this.setName(tag.getString("name"));
    }
    
    public double getRenderDistance() {
        return 64 + this.red + this.yellow + this.height;
    }
    
    @SideOnly(Side.CLIENT)
    public double func_82115_m() {
        return (64 + this.red + this.yellow + this.height) * (64 + this.red + this.yellow + this.height);
    }
    
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().addCoord((double)this.red, (double)this.height, (double)this.yellow);
    }
}
