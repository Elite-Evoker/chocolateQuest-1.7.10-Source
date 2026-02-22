package com.chocolate.chocolateQuest.entity.boss;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.Entity;

public class AttackKickQuadruped extends AttackKick
{
    public int kickTimeBack;
    public byte kickTypeBack;
    float legOffset;
    
    public AttackKickQuadruped(final EntityBaseBoss owner) {
        super(owner);
        this.legOffset = 0.5f;
    }
    
    @Override
    public void onUpdate() {
        if (this.kickTime > 0) {
            --this.kickTime;
            if (this.kickTime == this.getSpeed() / 4) {
                this.doKickDamage(this.kickType);
            }
        }
        if (this.kickTimeBack > 0) {
            --this.kickTimeBack;
            if (this.kickTimeBack == this.getSpeed() / 4) {
                this.doKickDamage(this.kickTypeBack);
            }
        }
    }
    
    @Override
    public void attackTarget(final Entity entity) {
        final int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities((Entity)this.owner, entity) - this.owner.rotationYaw);
        byte type = -1;
        if (angle < 90 && angle > -90) {
            if (this.kickTime > 0) {
                return;
            }
            if (angle < 80 && angle > 0) {
                type = 3;
            }
            else if (angle > -80 && angle < 0) {
                type = 1;
            }
        }
        else {
            if (this.kickTimeBack > 0) {
                return;
            }
            if (angle < -100) {
                type = 2;
            }
            else if (angle > 100) {
                type = 4;
            }
        }
        if (type != -1) {
            this.kick(type);
        }
    }
    
    @Override
    public void kick(final byte type) {
        if (!this.owner.worldObj.isRemote) {
            final PacketEntityAnimation packet = new PacketEntityAnimation(this.owner.getEntityId(), type);
            ChocolateQuest.channel.sendToAllAround((Entity)this.owner, (IMessage)packet, 64);
        }
        if (type == 1 || type == 3) {
            this.kickTime = this.getSpeed();
            this.kickType = type;
        }
        else {
            this.kickTimeBack = this.getSpeed();
            this.kickTypeBack = type;
        }
    }
    
    @Override
    public boolean isAttackInProgress() {
        return this.kickTime > 0 || this.kickTimeBack > 0;
    }
    
    @Override
    public double getAngleBetweenEntities(final Entity entity, final Entity target, final byte kickType) {
        final float dist = this.owner.width * this.legOffset;
        final float rotationYaw = this.owner.rotationYaw * 3.141592f / 180.0f;
        double despX = -Math.sin(rotationYaw) * dist;
        double despZ = Math.cos(rotationYaw) * dist;
        if (kickType == 4 || kickType == 2) {
            despX = -despX;
            despZ = -despZ;
        }
        final double d = entity.posX + despX - target.posX;
        final double d2 = entity.posZ + despZ - target.posZ;
        double angle = Math.atan2(d, d2);
        angle = angle * 180.0 / 3.141592;
        angle = -MathHelper.wrapAngleTo180_double(angle - 180.0);
        return angle;
    }
}
