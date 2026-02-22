package com.chocolate.chocolateQuest.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketAttackToXYZ implements IMessage, IMessageHandler<PacketAttackToXYZ, IMessage>
{
    byte type;
    int entityID;
    double x;
    double y;
    double z;
    
    public PacketAttackToXYZ() {
    }
    
    public PacketAttackToXYZ(final int entityID, final byte animType, final double x, final double y, final double z) {
        this.entityID = entityID;
        this.type = animType;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public IMessage onMessage(final PacketAttackToXYZ message, final MessageContext ctx) {
        final Entity entity = ChannelHandlerClient.getClientWorld().getEntityByID(message.entityID);
        if (entity instanceof EntityBaseBoss) {
            final EntityBaseBoss g = (EntityBaseBoss)entity;
            g.attackToXYZ(message.type, message.x, message.y, message.z);
        }
        return null;
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.type = bytes.readByte();
        this.entityID = bytes.readInt();
        this.x = bytes.readDouble();
        this.y = bytes.readDouble();
        this.z = bytes.readDouble();
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeByte((int)this.type);
        bytes.writeInt(this.entityID);
        bytes.writeDouble(this.x);
        bytes.writeDouble(this.y);
        bytes.writeDouble(this.z);
    }
}
