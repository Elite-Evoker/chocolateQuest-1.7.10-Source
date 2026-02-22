package com.chocolate.chocolateQuest.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketUpdateConversation implements IMessage, IMessageHandler<PacketUpdateConversation, IMessage>
{
    public static final int END_CONVERSATION = 1;
    public static final int EDIT_CONVERSATION = 2;
    public static final int EDIT_NPC = 3;
    public static final int INVENTORY = 4;
    int[] step;
    int npc;
    byte type;
    
    public PacketUpdateConversation() {
    }
    
    public PacketUpdateConversation(final int actionType, final EntityHumanBase npc) {
        this.type = (byte)actionType;
        this.npc = npc.getEntityId();
        this.step = new int[0];
    }
    
    public PacketUpdateConversation(final int[] step, final EntityHumanBase npc) {
        this.step = step;
        this.npc = npc.getEntityId();
    }
    
    public IMessage onMessage(final PacketUpdateConversation message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = (EntityPlayer)ctx.getServerHandler().playerEntity;
        message.execute(entityPlayer);
        return null;
    }
    
    public void execute(final EntityPlayer player) {
        final Entity e = player.worldObj.getEntityByID(this.npc);
        if (e instanceof EntityHumanNPC) {
            final EntityHumanNPC npc = (EntityHumanNPC)e;
            switch (this.type) {
                case 1: {
                    npc.endConversation();
                    return;
                }
                case 2: {
                    npc.editConversation(player);
                    return;
                }
                case 3: {
                    npc.editNPC(player);
                    return;
                }
                case 4: {
                    npc.openInventory(player);
                    return;
                }
                default: {
                    npc.updateConversation(player, this.step);
                    break;
                }
            }
        }
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.npc = bytes.readInt();
        this.type = bytes.readByte();
        final int length = bytes.readInt();
        this.step = new int[length];
        for (int i = 0; i < length; ++i) {
            this.step[i] = bytes.readInt();
        }
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeInt(this.npc);
        bytes.writeByte((int)this.type);
        bytes.writeInt(this.step.length);
        for (int i = 0; i < this.step.length; ++i) {
            bytes.writeInt(this.step[i]);
        }
    }
}
