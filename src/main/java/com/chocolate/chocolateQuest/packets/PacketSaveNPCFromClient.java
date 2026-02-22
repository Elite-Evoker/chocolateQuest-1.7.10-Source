package com.chocolate.chocolateQuest.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public class PacketSaveNPCFromClient implements IMessageHandler<PacketSaveNPC, IMessage>
{
    public IMessage onMessage(final PacketSaveNPC message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = (EntityPlayer)ctx.getServerHandler().playerEntity;
        message.execute(entityPlayer.worldObj);
        final NBTTagCompound tag = new NBTTagCompound();
        message.npc.writeEntityToSpawnerNBT(tag, 0, 0, 0);
        message.tag = tag;
        ChocolateQuest.channel.sendToPlayer((IMessage)message, (EntityPlayerMP)entityPlayer);
        return null;
    }
}
