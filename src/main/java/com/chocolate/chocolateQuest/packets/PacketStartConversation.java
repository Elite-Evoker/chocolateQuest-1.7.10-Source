package com.chocolate.chocolateQuest.packets;

import java.util.Iterator;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import java.util.ArrayList;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.gui.guinpc.GuiNPC;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import com.chocolate.chocolateQuest.quest.DialogOption;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketStartConversation implements IMessage, IMessageHandler<PacketStartConversation, IMessage>
{
    int npcID;
    EntityHumanNPC npc;
    EntityPlayer playerSpeakingTo;
    DialogOption options;
    int[] breadCrumbs;
    
    public PacketStartConversation() {
    }
    
    public PacketStartConversation(final EntityHumanNPC npc, final int[] breadCrumbs, final EntityPlayer playerSpeakingTo) {
        this.npcID = npc.getEntityId();
        this.npc = npc;
        this.breadCrumbs = breadCrumbs;
        this.playerSpeakingTo = playerSpeakingTo;
    }
    
    public IMessage onMessage(final PacketStartConversation message, final MessageContext ctx) {
        message.execute(ChannelHandlerClient.getClientPlayer());
        return null;
    }
    
    public void execute(final EntityPlayer player) {
        final Entity entity = player.worldObj.getEntityByID(this.npcID);
        if (entity instanceof EntityHumanNPC) {
            this.npc = (EntityHumanNPC)entity;
            if (GuiNPC.instance == null || this.breadCrumbs.length == 0) {
                if (this.breadCrumbs.length == 0) {
                    this.npc.conversation = this.options;
                    player.openGui((Object)ChocolateQuest.instance, 6, player.worldObj, this.npcID, 0, 0);
                }
            }
            else {
                GuiNPC.instance.setDialogOption(this.options, this.breadCrumbs);
            }
        }
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.npcID = bytes.readInt();
        this.options = this.readOption(bytes);
        final int length = bytes.readInt();
        this.breadCrumbs = new int[length];
        for (int i = 0; i < this.breadCrumbs.length; ++i) {
            this.breadCrumbs[i] = bytes.readInt();
        }
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeInt(this.npcID);
        final DialogOption conversation = this.npc.getDialogOption(this.breadCrumbs);
        this.writeOption(bytes, conversation);
        bytes.writeInt(this.breadCrumbs.length);
        for (final int i : this.breadCrumbs) {
            bytes.writeInt(i);
        }
    }
    
    public void writeOption(final ByteBuf bytes, final DialogOption mainOption) {
        bytes.writeInt(mainOption.id);
        PacketBase.writeString(bytes, mainOption.folder);
        PacketBase.writeString(bytes, mainOption.name);
        if (mainOption.options != null) {
            final ArrayList<DialogOption> list = new ArrayList<DialogOption>();
            for (final DialogOption option : mainOption.options) {
                boolean shouldOptionPass = true;
                if (option.conditions != null) {
                    for (final DialogCondition condition : option.conditions) {
                        if (!condition.matches(this.playerSpeakingTo, this.npc)) {
                            shouldOptionPass = false;
                            break;
                        }
                    }
                }
                if (shouldOptionPass) {
                    list.add(option);
                }
            }
            bytes.writeInt(list.size());
            for (final DialogOption option2 : list) {
                bytes.writeInt(option2.id);
                PacketBase.writeString(bytes, option2.folder);
                PacketBase.writeString(bytes, option2.name);
            }
        }
        else {
            bytes.writeInt(0);
        }
    }
    
    public DialogOption readOption(final ByteBuf bytes) {
        final DialogOption option = new DialogOption();
        option.id = bytes.readInt();
        option.folder = PacketBase.readString(bytes);
        option.name = PacketBase.readString(bytes);
        final int length = bytes.readInt();
        if (length > 0) {
            final DialogOption[] options = new DialogOption[length];
            for (int i = 0; i < options.length; ++i) {
                options[i] = new DialogOption();
                options[i].id = bytes.readInt();
                options[i].folder = PacketBase.readString(bytes);
                options[i].name = PacketBase.readString(bytes);
            }
            option.options = options;
        }
        return option;
    }
}
