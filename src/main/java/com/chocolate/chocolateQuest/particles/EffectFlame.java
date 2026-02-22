package com.chocolate.chocolateQuest.particles;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;

public class EffectFlame extends EffectBase
{
    private static final ResourceLocation particleTextures;
    double mx;
    double my;
    double mz;
    
    public EffectFlame(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12) {
        super(par1World, posX, posY, posZ, par8, par10, par12);
        this.particleMaxAge = 10;
        this.worldObj = par1World;
        this.particleTextureIndexX = 3;
        this.particleTextureIndexY = 5;
        this.particleScale = 1.5f;
        this.mx = par8;
        this.motionX = par8;
        this.my = par10;
        this.motionY = par10;
        this.mz = par12;
        this.motionZ = par12;
    }
    
    public void onUpdate() {
        this.motionX = this.mx;
        this.motionZ = this.mz;
        super.onUpdate();
    }
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
}
