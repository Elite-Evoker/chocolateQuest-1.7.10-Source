package com.chocolate.chocolateQuest.packets;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSaveNPC implements IMessage
{
    EntityHumanNPC npc;
    int npcID;
    NBTTagCompound tag;
    static String fileName;
    
    public PacketSaveNPC() {
    }
    
    public PacketSaveNPC(final EntityHumanNPC npc, final String fileName) {
        this.npc = npc;
        this.npcID = npc.getEntityId();
    }
    
    public PacketSaveNPC(final EntityHumanNPC npc, final NBTTagCompound tag) {
        this.npc = npc;
        this.npcID = npc.getEntityId();
        this.tag = tag;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.npcID = buf.readInt();
        this.tag = PacketBase.readTag(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.npcID);
        if (this.tag == null) {
            this.tag = new NBTTagCompound();
            this.npc.writeStats(this.tag, true);
        }
        PacketBase.writeTag(buf, this.tag);
    }
    
    public void execute(final World worldObj) {
        final Entity e = worldObj.getEntityByID(this.npcID);
        if (e instanceof EntityHumanNPC) {
            this.npc = (EntityHumanNPC)e;
        }
    }
    
    static {
        PacketSaveNPC.fileName = null;
    }
}
