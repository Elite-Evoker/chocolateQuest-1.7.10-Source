package com.chocolate.chocolateQuest.packets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import com.chocolate.chocolateQuest.gui.guinpc.GuiEditNpc;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public class PacketEditNPCFromServer implements IMessageHandler<PacketEditNPC, IMessage>
{
    public IMessage onMessage(final PacketEditNPC message, final MessageContext ctx) {
        message.execute(ChannelHandlerClient.getClientWorld());
        this.test(message);
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public void test(final PacketEditNPC message) {
        if (message.npc != null) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiEditNpc(message.npc));
        }
    }
}
