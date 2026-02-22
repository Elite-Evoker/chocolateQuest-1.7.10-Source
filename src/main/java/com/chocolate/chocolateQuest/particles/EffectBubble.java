package com.chocolate.chocolateQuest.particles;

import net.minecraft.world.World;

public class EffectBubble extends EffectBase
{
    public EffectBubble(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12) {
        super(par1World, posX, posY, posZ, par8, par10, par12);
        this.particleMaxAge = 20;
        this.worldObj = par1World;
        this.particleTextureIndexX = 0;
        this.particleTextureIndexY = 4;
        final double motionX = 0.0;
        this.motionY = motionX;
        this.motionZ = motionX;
        this.motionX = motionX;
        this.particleRed = (float)par8;
        this.particleGreen = (float)par10;
        this.particleBlue = (float)par12;
    }
    
    public void onUpdate() {
        if (this.ticksExisted > 12 && this.ticksExisted % 2 == 0) {
            ++this.particleTextureIndexX;
        }
        else {
            this.motionY += 0.01;
        }
        ++this.ticksExisted;
        super.onUpdate();
    }
}
