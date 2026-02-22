package com.chocolate.chocolateQuest.particles;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EffectSmoke extends EffectBase
{
    int animationTicks;
    
    public EffectSmoke(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12) {
        super(par1World, posX, posY, posZ, par8, par10, par12);
        this.animationTicks = 1;
        this.particleMaxAge = 8;
        this.worldObj = par1World;
        this.iconsAmmount = 128.0f;
        this.iconScale = 8.0f;
        this.particleTextureIndexX = 8;
        this.particleTextureIndexY = 0;
        final double motionX = 0.0;
        this.motionY = motionX;
        this.motionZ = motionX;
        this.motionX = motionX;
        this.particleRed = (float)par8;
        this.particleGreen = (float)par10;
        this.particleBlue = (float)par12;
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        final int iconX = this.particleTextureIndexX;
        final int iconY = this.particleTextureIndexY;
        final Tessellator tessellator1 = new Tessellator();
        tessellator1.startDrawingQuads();
        tessellator1.setBrightness(this.getBrightnessForRender(par2));
        final float iconWidth = this.iconScale / this.iconsAmmount;
        final float minX = iconX / this.iconsAmmount * this.iconScale;
        final float maxX = minX + iconWidth;
        final float minY = iconY / this.iconsAmmount * this.iconScale;
        final float maxY = minY + iconWidth;
        final float scale = 0.1f * this.particleScale;
        final float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - EffectSmoke.interpPosX);
        final float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - EffectSmoke.interpPosY);
        final float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - EffectSmoke.interpPosZ);
        final float f8 = 1.0f;
        tessellator1.setColorRGBA_F(this.particleRed * f8, this.particleGreen * f8, this.particleBlue * f8, 1.0f);
        tessellator1.addVertexWithUV((double)(x - par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z - par5 * scale - par7 * scale), (double)maxX, (double)maxY);
        tessellator1.addVertexWithUV((double)(x - par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z - par5 * scale + par7 * scale), (double)maxX, (double)minY);
        tessellator1.addVertexWithUV((double)(x + par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z + par5 * scale + par7 * scale), (double)minX, (double)minY);
        tessellator1.addVertexWithUV((double)(x + par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z + par5 * scale - par7 * scale), (double)minX, (double)maxY);
        tessellator1.draw();
    }
    
    public void onUpdate() {
        if (this.ticksExisted % this.animationTicks == 0) {
            --this.particleTextureIndexX;
        }
        this.motionY += 0.005;
        ++this.ticksExisted;
        super.onUpdate();
    }
}
