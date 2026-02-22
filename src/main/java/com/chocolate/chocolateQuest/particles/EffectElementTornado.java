package com.chocolate.chocolateQuest.particles;

import net.minecraft.world.World;

public class EffectElementTornado extends EffectElement
{
    public EffectElementTornado(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12, final int element) {
        super(par1World, posX, posY, posZ, par8, par10, par12, element);
        final int extraAge = this.rand.nextInt(10 + (int)par8);
        this.ticksExisted = extraAge;
        this.particleMaxAge = (int)(15.0 + par8) + extraAge;
        this.motionY = (10.0 + par8) / 200.0;
    }
    
    @Override
    public void onUpdate() {
        this.motionX = Math.sin(this.ticksExisted / 2) / 2.0 * this.ticksExisted / 20.0;
        this.motionZ = Math.cos(this.ticksExisted / 2) / 2.0 * this.ticksExisted / 20.0;
        super.onUpdate();
    }
}
