package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.item.ItemStack;

public class HandTNT extends HandHelper
{
    int explosionTimer;
    final ItemStack whiteBlock;
    
    public HandTNT(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
        this.explosionTimer = 0;
        this.whiteBlock = new ItemStack(Blocks.quartz_block);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.owner.isSwingInProgress(this) || this.explosionTimer > 0) {
            ++this.explosionTimer;
            if (this.explosionTimer > 30) {
                this.owner.worldObj.createExplosion((Entity)this.owner, this.owner.posX, this.owner.posY, this.owner.posZ, 5.0f, true);
                this.owner.setDead();
            }
        }
        if (this.owner.currentPos != null) {
            if (this.owner.getDistanceSq((double)this.owner.currentPos.xCoord, (double)this.owner.currentPos.yCoord, (double)this.owner.currentPos.zCoord) < 4.0) {
                this.owner.worldObj.playSoundAtEntity((Entity)this.owner, "minecraft:random.fuse", 0.2f, 1.0f);
                if (this.explosionTimer == 0) {
                    ++this.explosionTimer;
                }
            }
            else {
                this.explosionTimer = 0;
            }
        }
    }
    
    @Override
    public void attackEntity(final Entity entity) {
        if (this.explosionTimer == 0) {
            this.owner.worldObj.playSoundAtEntity(entity, "minecraft:random.fuse", 0.2f, 1.0f);
            this.owner.swingHand(this);
        }
    }
    
    @Override
    public boolean isTwoHanded() {
        return true;
    }
    
    @Override
    public ItemStack getItem() {
        if (this.explosionTimer % 3 == 1) {
            return this.whiteBlock;
        }
        return super.getItem();
    }
}
