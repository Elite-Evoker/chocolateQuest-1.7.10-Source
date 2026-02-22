package com.chocolate.chocolateQuest.gui.guiParty;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import io.netty.buffer.ByteBuf;

public class PartyActionAttack extends PartyAction
{
    int idTarget;
    
    public PartyActionAttack(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public void write(final ByteBuf stream, final MovingObjectPosition playerMop, final EntityPlayer player) {
        int id = 0;
        if (playerMop.entityHit != null) {
            id = playerMop.entityHit.getEntityId();
        }
        stream.writeInt(id);
    }
    
    @Override
    public void read(final ByteBuf stream) {
        this.idTarget = stream.readInt();
    }
    
    @Override
    public void execute(final EntityHumanBase e) {
        final Entity target = e.worldObj.getEntityByID(this.idTarget);
        if (target instanceof EntityLivingBase) {
            e.setAttackTarget((EntityLivingBase)target);
        }
    }
}
