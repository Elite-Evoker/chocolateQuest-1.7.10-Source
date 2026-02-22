package com.chocolate.chocolateQuest.entity.npc;

import net.minecraft.util.IChatComponent;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.command.server.CommandBlockLogic;

class npcLogic extends CommandBlockLogic
{
    EntityHumanNPC owner;
    
    public npcLogic(final EntityHumanNPC owner) {
        this.owner = owner;
    }
    
    public ChunkCoordinates getCommandSenderPosition() {
        return this.owner.getHomePosition();
    }
    
    public World getEntityWorld() {
        return this.owner.worldObj;
    }
    
    public void func_145756_e() {
    }
    
    public int func_145751_f() {
        return 0;
    }
    
    public void func_145757_a(final ByteBuf buff) {
    }
    
    public void addChatMessage(final IChatComponent p_145747_1_) {
        if (this.owner.playerSpeakingTo != null) {
            this.owner.playerSpeakingTo.addChatMessage(p_145747_1_);
        }
        else {
            super.addChatMessage(p_145747_1_);
        }
    }
    
    public boolean canCommandSenderUseCommand(final int aint, final String aString) {
        return aint <= 4;
    }
}
