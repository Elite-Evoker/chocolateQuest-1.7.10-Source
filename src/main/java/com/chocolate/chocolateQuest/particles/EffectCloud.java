package com.chocolate.chocolateQuest.particles;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;

public class EffectCloud extends EffectFog
{
    private static final ResourceLocation particleTextures;
    
    public EffectCloud(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12) {
        super(par1World, posX, posY, posZ, par8, par10, par12);
        this.particleMaxAge = 16;
        this.particleScale = 32.0f + this.rand.nextFloat() * 16.0f;
    }
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
}
