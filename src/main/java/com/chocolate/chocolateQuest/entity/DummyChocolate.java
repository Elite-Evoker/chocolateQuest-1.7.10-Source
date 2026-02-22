package com.chocolate.chocolateQuest.entity;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChunkCoordinates;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class DummyChocolate extends EntityPlayer
{
    public DummyChocolate(final World par1World) {
        super(par1World, new GameProfile(new UUID(0L, 0L), "ArrgChocolate"));
    }
    
    public boolean canCommandSenderUseCommand(final int i, final String s) {
        return false;
    }
    
    public ChunkCoordinates getCommandSenderPosition() {
        return null;
    }
    
    public void addChatMessage(final IChatComponent var1) {
    }
}
