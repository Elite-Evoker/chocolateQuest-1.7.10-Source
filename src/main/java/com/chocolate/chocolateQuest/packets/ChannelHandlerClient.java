package com.chocolate.chocolateQuest.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ChannelHandlerClient extends ChannelHandler
{
    static World getClientWorld() {
        return (World)Minecraft.getMinecraft().theWorld;
    }
    
    static EntityPlayer getClientPlayer() {
        return (EntityPlayer)Minecraft.getMinecraft().thePlayer;
    }
}
