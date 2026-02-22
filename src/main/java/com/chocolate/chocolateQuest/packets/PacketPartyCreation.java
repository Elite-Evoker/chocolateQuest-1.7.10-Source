package com.chocolate.chocolateQuest.packets;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketPartyCreation implements IMessage, IMessageHandler<PacketPartyCreation, IMessage>
{
    int leaderId;
    int[] mobIds;
    
    public PacketPartyCreation() {
    }
    
    public PacketPartyCreation(final int leaderID, final int[] ids) {
        this.mobIds = ids;
        this.leaderId = leaderID;
    }
    
    public void fromBytes(final ByteBuf inputStream) {
        this.leaderId = inputStream.readInt();
        final int size = inputStream.readInt();
        this.mobIds = new int[size];
        for (int i = 0; i < size; ++i) {
            this.mobIds[i] = inputStream.readInt();
        }
    }
    
    public void toBytes(final ByteBuf outputStream) {
        outputStream.writeInt(this.leaderId);
        outputStream.writeInt(this.mobIds.length);
        for (int i = 0; i < this.mobIds.length; ++i) {
            outputStream.writeInt(this.mobIds[i]);
        }
    }
    
    public void execute(final World world) {
        Entity e = world.getEntityByID(this.leaderId);
        if (e instanceof EntityHumanBase) {
            final EntityHumanBase leader = (EntityHumanBase)e;
            for (int i = 0; i < this.mobIds.length; ++i) {
                e = world.getEntityByID(this.mobIds[i]);
                if (e instanceof EntityHumanBase) {
                    leader.tryPutIntoPArty((EntityHumanBase)e);
                }
            }
        }
    }
    
    public IMessage onMessage(final PacketPartyCreation message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = (EntityPlayer)ctx.getServerHandler().playerEntity;
        message.execute(entityPlayer.worldObj);
        return null;
    }
}
