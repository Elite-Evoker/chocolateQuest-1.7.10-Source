package com.chocolate.chocolateQuest.packets;

import io.netty.buffer.ByteBuf;
import com.chocolate.chocolateQuest.gui.guinpc.ContainerAwakement;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketUpdateAwakement implements IMessage, IMessageHandler<PacketUpdateAwakement, IMessage>
{
    int awakementID;
    
    public PacketUpdateAwakement() {
    }
    
    public PacketUpdateAwakement(final int awakementID) {
        this.awakementID = awakementID;
    }
    
    public IMessage onMessage(final PacketUpdateAwakement message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = (EntityPlayer)ctx.getServerHandler().playerEntity;
        message.execute(entityPlayer);
        return null;
    }
    
    public void execute(final EntityPlayer player) {
        if (player.openContainer instanceof ContainerAwakement) {
            ((ContainerAwakement)player.openContainer).enchantItem(this.awakementID);
        }
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.awakementID = bytes.readInt();
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeInt(this.awakementID);
    }
}
