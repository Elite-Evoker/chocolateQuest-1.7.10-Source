package com.chocolate.chocolateQuest.particles;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;

public class EffectElement extends EffectBase
{
    private static final ResourceLocation particleTextures;
    public static final int FIRE = 0;
    public static final int PHYSIC = 1;
    public static final int BLAST = 2;
    public static final int MAGIC = 3;
    double mx;
    double my;
    double mz;
    
    public EffectElement(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12, final int element) {
        super(par1World, posX, posY, posZ, par8, par10, par12);
        this.particleMaxAge = 10;
        this.worldObj = par1World;
        this.particleTextureIndexX = element;
        this.particleTextureIndexY = 5;
        this.mx = par8;
        this.motionX = par8;
        this.my = par10;
        this.motionY = par10;
        this.mz = par12;
        this.motionZ = par12;
    }
    
    public void onUpdate() {
        ++this.ticksExisted;
        super.onUpdate();
    }
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
}
