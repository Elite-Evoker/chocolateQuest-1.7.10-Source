package com.chocolate.chocolateQuest.packets;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketShieldBlockFromServer implements IMessage, IMessageHandler<PacketShieldBlockFromServer, IMessage>
{
    int playerID;
    int targetID;
    
    public PacketShieldBlockFromServer() {
    }
    
    public PacketShieldBlockFromServer(final int playerID, final int targetID) {
        this.playerID = playerID;
        this.targetID = targetID;
    }
    
    public void fromBytes(final ByteBuf inputStream) {
        this.playerID = inputStream.readInt();
        this.targetID = inputStream.readInt();
    }
    
    public void toBytes(final ByteBuf outputStream) {
        outputStream.writeInt(this.playerID);
        outputStream.writeInt(this.targetID);
    }
    
    public void execute(final World world) {
        final Entity e = world.getEntityByID(this.targetID);
        final Entity e2 = world.getEntityByID(this.playerID);
        if (e instanceof EntityLivingBase && e2 instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)e2;
            final ItemStack is = player.getCurrentEquippedItem();
            final int useTime = player.getItemInUseDuration();
            if (useTime > 0 && useTime < 5) {
                player.swingItem();
                final PacketShieldBlock packet = new PacketShieldBlock(this.playerID, this.targetID);
                ChocolateQuest.channel.sendPaquetToServer((IMessage)packet);
            }
        }
    }
    
    public IMessage onMessage(final PacketShieldBlockFromServer message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = ChannelHandlerClient.getClientPlayer();
        message.execute(entityPlayer.worldObj);
        return null;
    }
}
