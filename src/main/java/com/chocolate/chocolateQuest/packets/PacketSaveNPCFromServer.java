package com.chocolate.chocolateQuest.packets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import com.chocolate.chocolateQuest.gui.guinpc.GuiImportNPC;
import cpw.mods.fml.client.FMLClientHandler;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public class PacketSaveNPCFromServer implements IMessageHandler<PacketSaveNPC, IMessage>
{
    public IMessage onMessage(final PacketSaveNPC message, final MessageContext ctx) {
        message.execute(ChannelHandlerClient.getClientWorld());
        final NBTTagCompound tag = message.tag;
        final EntityHumanNPC npc = message.npc;
        this.test(tag);
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public void test(final NBTTagCompound tag) {
        final GuiScreen screen = FMLClientHandler.instance().getClient().currentScreen;
        if (screen instanceof GuiImportNPC) {
            ((GuiImportNPC)screen).saveNPC(tag);
        }
    }
}
