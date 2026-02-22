package com.chocolate.chocolateQuest.particles;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;

public class EffectFog extends EffectBase
{
    private static final ResourceLocation particleTextures;
    
    public EffectFog(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12) {
        super(par1World, posX, posY, posZ, par8, par10, par12);
        this.particleMaxAge = 16;
        this.worldObj = par1World;
        this.particleTextureIndexY = 0;
        final double motionX = 0.0;
        this.motionY = motionX;
        this.motionZ = motionX;
        this.motionX = motionX;
        this.particleScale = 6.0f + this.rand.nextFloat() * 6.0f;
        this.particleRed = (float)par8;
        this.particleGreen = (float)par10;
        this.particleBlue = (float)par12;
        this.iconsAmmount = 8.0f;
        this.iconScale = 16.0f;
    }
    
    public void onUpdate() {
        super.onUpdate();
        ++this.particleTextureIndexX;
        if (this.particleTextureIndexX == 8) {
            ++this.particleTextureIndexY;
            this.particleTextureIndexX = 0;
        }
    }
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
}
