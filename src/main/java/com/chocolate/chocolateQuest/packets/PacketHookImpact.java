package com.chocolate.chocolateQuest.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketHookImpact implements IMessage, IMessageHandler<PacketHookImpact, IMessage>
{
    byte type;
    int hookID;
    int entityID;
    int posX;
    int posY;
    int posZ;
    int angle;
    int distance;
    int height;
    public static final byte ENTITY = 0;
    public static final byte BLOCK = 1;
    int offsetScale;
    
    public PacketHookImpact() {
        this.offsetScale = 100000;
    }
    
    public PacketHookImpact(final int hookID, final int entityID, final double angle, final double dist, final double height) {
        this.offsetScale = 100000;
        this.type = 0;
        this.hookID = hookID;
        this.entityID = entityID;
        this.angle = (int)angle;
        this.distance = (int)(dist * this.offsetScale);
        this.height = (int)(height * this.offsetScale);
    }
    
    public PacketHookImpact(final int hookID, final int posX, final int posY, final int posZ) {
        this.offsetScale = 100000;
        this.type = 1;
        this.hookID = hookID;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
    
    public IMessage onMessage(final PacketHookImpact message, final MessageContext ctx) {
        final World world = ChannelHandlerClient.getClientWorld();
        message.execute(world);
        return null;
    }
    
    public void execute(final World world) {
        Entity entity = world.getEntityByID(this.hookID);
        if (entity instanceof EntityHookShoot) {
            final EntityHookShoot hook = (EntityHookShoot)entity;
            switch (this.type) {
                case 0: {
                    entity = world.getEntityByID(this.entityID);
                    if (entity != null) {
                        final double offX = this.angle;
                        final double distance = this.distance / (double)this.offsetScale;
                        final double height = this.height / this.offsetScale;
                        hook.hookedEntity = entity;
                        hook.hookedAtHeight = height;
                        hook.hookedAtAngle = this.angle;
                        hook.hookedAtDistance = distance;
                        break;
                    }
                    break;
                }
                case 1: {
                    hook.blockX = this.posX;
                    hook.blockY = this.posY;
                    hook.blockZ = this.posZ;
                    break;
                }
            }
            hook.onImpact();
        }
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.type = bytes.readByte();
        this.hookID = bytes.readInt();
        switch (this.type) {
            case 0: {
                this.entityID = bytes.readInt();
                this.angle = bytes.readInt();
                this.distance = bytes.readInt();
                this.height = bytes.readInt();
                break;
            }
            case 1: {
                this.posX = bytes.readInt();
                this.posY = bytes.readInt();
                this.posZ = bytes.readInt();
                break;
            }
        }
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeByte((int)this.type);
        bytes.writeInt(this.hookID);
        switch (this.type) {
            case 0: {
                bytes.writeInt(this.entityID);
                bytes.writeInt(this.angle);
                bytes.writeInt(this.distance);
                bytes.writeInt(this.height);
                break;
            }
            case 1: {
                bytes.writeInt(this.posX);
                bytes.writeInt(this.posY);
                bytes.writeInt(this.posZ);
                break;
            }
        }
    }
}
