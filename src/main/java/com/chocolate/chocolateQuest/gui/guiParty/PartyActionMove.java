package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.utils.Vec4I;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import io.netty.buffer.ByteBuf;

public class PartyActionMove extends PartyAction
{
    int x;
    int y;
    int z;
    
    public PartyActionMove(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public void write(final ByteBuf stream, final MovingObjectPosition playerMop, final EntityPlayer player) {
        stream.writeInt(playerMop.blockX);
        stream.writeInt(playerMop.blockY);
        stream.writeInt(playerMop.blockZ);
    }
    
    @Override
    public void read(final ByteBuf stream) {
        this.x = stream.readInt();
        this.y = stream.readInt();
        this.z = stream.readInt();
    }
    
    @Override
    public void execute(final EntityHumanBase e) {
        e.currentPos = new Vec4I(this.x, this.y, this.z, 0);
    }
}
