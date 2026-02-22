package com.chocolate.chocolateQuest.packets;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public class PacketUpdateHumanDummyDataFromClient implements IMessageHandler<PacketUpdateHumanDummyData, IMessage>
{
    public IMessage onMessage(final PacketUpdateHumanDummyData message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = (EntityPlayer)ctx.getServerHandler().playerEntity;
        message.execute(entityPlayer.worldObj);
        return null;
    }
}
