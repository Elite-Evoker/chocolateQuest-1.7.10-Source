package com.chocolate.chocolateQuest.packets;

import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import com.chocolate.chocolateQuest.quest.DialogOption;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketEditConversation implements IMessage, IMessageHandler<PacketEditConversation, IMessage>
{
    int npcID;
    EntityHumanNPC npc;
    DialogOption options;
    int[] breadCrumbs;
    
    public PacketEditConversation() {
    }
    
    public PacketEditConversation(final EntityHumanNPC npc, final int[] breadCrumbs) {
        this.npcID = npc.getEntityId();
        this.npc = npc;
        this.breadCrumbs = breadCrumbs;
    }
    
    public IMessage onMessage(final PacketEditConversation message, final MessageContext ctx) {
        message.execute(ChannelHandlerClient.getClientPlayer());
        return null;
    }
    
    public void execute(final EntityPlayer player) {
        final Entity entity = player.worldObj.getEntityByID(this.npcID);
        if (entity instanceof EntityHumanNPC) {
            this.npc = (EntityHumanNPC)entity;
            this.npc.conversation = this.options;
            player.openGui((Object)ChocolateQuest.instance, 6, player.worldObj, this.npcID, 1, 0);
        }
    }
    
    public void executeServer(final EntityPlayer player) {
        final Entity entity = player.worldObj.getEntityByID(this.npcID);
        if (entity instanceof EntityHumanNPC) {
            this.npc = (EntityHumanNPC)entity;
            this.npc.conversation = this.options;
            this.npc.endConversation();
        }
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.npcID = bytes.readInt();
        final int length = bytes.readInt();
        this.breadCrumbs = new int[length];
        for (int i = 0; i < this.breadCrumbs.length; ++i) {
            this.breadCrumbs[i] = bytes.readInt();
        }
        final NBTTagCompound tag = PacketBase.readTag(bytes);
        (this.options = new DialogOption()).readFromNBT(tag);
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeInt(this.npcID);
        bytes.writeInt(this.breadCrumbs.length);
        for (final int i : this.breadCrumbs) {
            bytes.writeInt(i);
        }
        final DialogOption conversation = this.npc.conversation;
        final NBTTagCompound tag = new NBTTagCompound();
        conversation.writeToNBT(tag);
        PacketBase.writeTag(bytes, tag);
    }
}
