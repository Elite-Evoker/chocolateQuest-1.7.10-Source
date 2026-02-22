package com.chocolate.chocolateQuest.packets;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketPartyMemberAction implements IMessage, IMessageHandler<PacketPartyMemberAction, IMessage>
{
    private byte actionID;
    int id;
    PartyAction action;
    public static final byte MOVE = 0;
    MovingObjectPosition playerMop;
    EntityPlayer player;
    
    public PacketPartyMemberAction() {
    }
    
    public PacketPartyMemberAction(final EntityHumanBase e, final PartyAction action, final MovingObjectPosition playerMop, final EntityPlayer player) {
        this.id = e.getEntityId();
        this.action = action;
        this.actionID = action.id;
        this.playerMop = playerMop;
        this.player = player;
    }
    
    public void fromBytes(final ByteBuf inputStream) {
        this.id = inputStream.readInt();
        this.actionID = inputStream.readByte();
        this.action = PartyAction.actions.get(this.actionID);
        if (this.action != null) {
            this.action.read(inputStream);
        }
    }
    
    public void toBytes(final ByteBuf outputStream) {
        outputStream.writeInt(this.id);
        outputStream.writeByte((int)this.actionID);
        this.action.write(outputStream, this.playerMop, this.player);
    }
    
    public void execute(final World world) {
        final Entity e = world.getEntityByID(this.id);
        if (e instanceof EntityHumanBase && this.action != null) {
            this.action.execute((EntityHumanBase)e);
        }
    }
    
    public IMessage onMessage(final PacketPartyMemberAction message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = (EntityPlayer)ctx.getServerHandler().playerEntity;
        message.execute(entityPlayer.worldObj);
        return null;
    }
}
