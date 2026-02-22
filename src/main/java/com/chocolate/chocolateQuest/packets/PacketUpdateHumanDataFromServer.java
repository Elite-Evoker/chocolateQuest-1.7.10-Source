package com.chocolate.chocolateQuest.packets;

import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public class PacketUpdateHumanDataFromServer implements IMessageHandler<PacketUpdateHumanData, IMessage>
{
    public IMessage onMessage(final PacketUpdateHumanData message, final MessageContext ctx) {
        final World world = ChannelHandlerClient.getClientWorld();
        message.execute(world);
        return null;
    }
}
