package com.chocolate.chocolateQuest.packets;

import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public class PacketUpdateHumanDummyDataFromServer implements IMessageHandler<PacketUpdateHumanDummyData, IMessage>
{
    public IMessage onMessage(final PacketUpdateHumanDummyData message, final MessageContext ctx) {
        final World world = ChannelHandlerClient.getClientWorld();
        message.execute(world);
        return null;
    }
}
