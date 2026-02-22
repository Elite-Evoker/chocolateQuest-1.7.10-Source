package com.chocolate.chocolateQuest.particles;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;

public class EffectLiquidDrip extends EffectBase
{
    private static final ResourceLocation particleTextures;
    
    public EffectLiquidDrip(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12) {
        super(par1World, posX, posY, posZ, par8, par10, par12);
        this.particleMaxAge = 30;
        this.worldObj = par1World;
        this.particleTextureIndexX = 5;
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
        this.motionY = -0.6;
        ++this.ticksExisted;
        if (this.ticksExisted > 1 && this.fallDistance == 0.0f && this.particleTextureIndexX < 8 && this.ticksExisted % 2 == 0) {
            ++this.particleTextureIndexX;
        }
        super.onUpdate();
    }
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
}
