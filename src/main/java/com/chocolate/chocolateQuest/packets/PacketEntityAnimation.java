package com.chocolate.chocolateQuest.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketEntityAnimation implements IMessage, IMessageHandler<PacketEntityAnimation, IMessage>
{
    byte type;
    int entityID;
    public static final byte SWING_ITEM = 0;
    public static final byte LEFT_HAND_SWING = 1;
    public static final byte AIM_RIGHT = 2;
    public static final byte AIM_LEFT = 3;
    
    public PacketEntityAnimation() {
    }
    
    public PacketEntityAnimation(final int entityID, final byte animType) {
        this.entityID = entityID;
        this.type = animType;
    }
    
    public IMessage onMessage(final PacketEntityAnimation message, final MessageContext ctx) {
        final World world = ChannelHandlerClient.getClientWorld();
        message.execute(world);
        return null;
    }
    
    public void execute(final World world) {
        final Entity entity = world.getEntityByID(this.entityID);
        if (entity != null) {
            if (this.type == 0) {
                if (entity instanceof EntityLivingBase) {
                    ((EntityLivingBase)entity).swingItem();
                }
                return;
            }
            if (entity instanceof EntityHumanBase) {
                switch (this.type) {
                    case 1: {
                        ((EntityHumanBase)entity).swingLeftHand();
                        return;
                    }
                    case 2: {
                        ((EntityHumanBase)entity).toogleAimRight();
                        return;
                    }
                    case 3: {
                        ((EntityHumanBase)entity).toogleAimLeft();
                        return;
                    }
                }
            }
            if (entity instanceof EntityBaseBoss) {
                ((EntityBaseBoss)entity).animationBoss(this.type);
            }
        }
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.type = bytes.readByte();
        this.entityID = bytes.readInt();
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeByte((int)this.type);
        bytes.writeInt(this.entityID);
    }
}
