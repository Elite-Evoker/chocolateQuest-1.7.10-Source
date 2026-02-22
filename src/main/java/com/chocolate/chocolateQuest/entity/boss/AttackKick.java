package com.chocolate.chocolateQuest.entity.boss;

import java.util.List;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.Entity;

public class AttackKick
{
    public int kickTime;
    public byte kickType;
    public int kickSpeed;
    EntityBaseBoss owner;
    
    public AttackKick(final EntityBaseBoss owner) {
        this.kickSpeed = 30;
        this.owner = owner;
    }
    
    public void setSpeed(final int speed) {
        this.kickSpeed = speed;
    }
    
    public int getSpeed() {
        return this.kickSpeed;
    }
    
    public void onUpdate() {
        if (this.kickTime > 0) {
            --this.kickTime;
            if (this.kickTime == this.getSpeed() / 3) {
                this.doKickDamage(this.kickType);
            }
        }
    }
    
    public void attackTarget(final Entity entity) {
        final int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities((Entity)this.owner, entity) - this.owner.rotationYaw);
        byte type = 0;
        if (angle > 0) {
            if (angle < 90) {
                type = 3;
            }
            else {
                type = 4;
            }
        }
        else if (angle > -90) {
            type = 1;
        }
        else {
            type = 2;
        }
        this.kick(type);
    }
    
    public void kick(final byte type) {
        if (!this.owner.worldObj.isRemote) {
            final PacketEntityAnimation packet = new PacketEntityAnimation(this.owner.getEntityId(), type);
            ChocolateQuest.channel.sendToAllAround((Entity)this.owner, (IMessage)packet, 64);
        }
        if (this.kickTime == 0) {
            this.kickTime = this.getSpeed();
            this.kickType = type;
        }
    }
    
    public void doKickDamage(final byte kickType) {
        final double d = Math.max(2.5, this.owner.width * 0.8);
        final List list = this.owner.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this.owner, this.owner.boundingBox.expand(d, 0.0, d).addCoord(0.0, -2.0, 0.0));
        int min = -180;
        int max = 180;
        switch (kickType) {
            case 3: {
                min = -5;
                max = 70;
                break;
            }
            case 4: {
                min = 70;
                max = 180;
                break;
            }
            case 1: {
                min = -70;
                max = 5;
                break;
            }
            case 2: {
                min = -180;
                max = -70;
                break;
            }
        }
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity1 = list.get(j);
            if (entity1.canBeCollidedWith() && !this.owner.isEntityEqual(entity1)) {
                if (entity1 != this.owner.riddenByEntity) {
                    final int angle = (int)MathHelper.wrapAngleTo180_double(this.getAngleBetweenEntities((Entity)this.owner, entity1, kickType) - this.owner.rotationYaw);
                    if (angle >= min && angle <= max && this.owner.attackEntityAsMob(entity1)) {
                        float rotation = this.owner.rotationYaw;
                        if (kickType == 2 || kickType == 4) {
                            rotation -= 180.0f;
                        }
                        rotation = rotation * 3.141592f / 180.0f;
                        final float dist = Math.max(1.5f, this.owner.width / 3.0f);
                        final double dx = -Math.sin(rotation) * dist;
                        final double dy = this.owner.size / 20.0f;
                        final double dz = Math.cos(rotation) * dist;
                        final Entity entity2 = entity1;
                        entity2.motionX += dx;
                        final Entity entity3 = entity1;
                        entity3.motionY += dy;
                        final Entity entity4 = entity1;
                        entity4.motionZ += dz;
                    }
                }
            }
        }
    }
    
    public double getAngleBetweenEntities(final Entity entity, final Entity target, final byte kickType) {
        final double d = entity.posX - target.posX;
        final double d2 = entity.posZ - target.posZ;
        double angle = Math.atan2(d, d2);
        angle = angle * 180.0 / 3.141592;
        angle = -MathHelper.wrapAngleTo180_double(angle - 180.0);
        return angle;
    }
    
    public boolean isAttackInProgress() {
        return this.kickTime > 0;
    }
}
