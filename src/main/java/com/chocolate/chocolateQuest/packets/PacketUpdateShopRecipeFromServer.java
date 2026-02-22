package com.chocolate.chocolateQuest.packets;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public class PacketUpdateShopRecipeFromServer implements IMessageHandler<PacketUpdateShopRecipe, IMessage>
{
    public IMessage onMessage(final PacketUpdateShopRecipe message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = ChannelHandlerClient.getClientPlayer();
        message.execute(entityPlayer);
        return null;
    }
}
