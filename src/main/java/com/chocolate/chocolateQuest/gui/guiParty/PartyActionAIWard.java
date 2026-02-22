package com.chocolate.chocolateQuest.gui.guiParty;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import io.netty.buffer.ByteBuf;
import com.chocolate.chocolateQuest.utils.Vec4I;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class PartyActionAIWard extends PartyActionAI
{
    int x;
    int y;
    int z;
    int w;
    
    public PartyActionAIWard(final String name, final int icon, final int AIMode) {
        super(name, icon, AIMode);
    }
    
    @Override
    public void execute(final EntityHumanBase e) {
        super.execute(e);
        e.standingPosition = new Vec4I(this.x, this.y, this.z, this.w);
    }
    
    @Override
    public void write(final ByteBuf stream, final MovingObjectPosition playerMop, final EntityPlayer player) {
        stream.writeInt(playerMop.blockX);
        stream.writeInt(playerMop.blockY);
        stream.writeInt(playerMop.blockZ);
        stream.writeInt((int)player.rotationYawHead);
    }
    
    @Override
    public void read(final ByteBuf stream) {
        this.x = stream.readInt();
        this.y = stream.readInt();
        this.z = stream.readInt();
        this.w = stream.readInt();
    }
}
