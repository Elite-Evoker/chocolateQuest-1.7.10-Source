package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.world.World;

public class EffectSmokeElement extends EffectSmoke
{
    public EffectSmokeElement(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12, final Elements element) {
        super(par1World, posX, posY, posZ, par8, par10, par12);
        this.particleRed = element.getColorX();
        this.particleGreen = element.getColorY();
        this.particleBlue = element.getColorZ();
        this.particleMaxAge *= 3;
        this.animationTicks = 3;
    }
}
