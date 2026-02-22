package com.chocolate.chocolateQuest.particles;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.particle.EntityFX;

public class ColoredDust extends EntityFX
{
    private static final ResourceLocation particleTextures;
    private int coolDown;
    
    public ColoredDust(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.particleMaxAge = 12;
        this.worldObj = par1World;
        this.coolDown = 0;
        this.motionX *= 0.1;
        this.motionZ *= 0.1;
        this.motionY *= 0.2;
        this.particleTextureIndexX = 0;
        this.particleTextureIndexY = 0;
        this.particleRed = (float)par8;
        this.particleGreen = (float)par10;
        this.particleBlue = (float)par12;
    }
    
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        int iconX = this.particleTextureIndexX;
        final int iconY = this.particleTextureIndexY;
        if (this.particleAge < 6) {
            iconX += this.particleAge;
        }
        else {
            iconX += 10 - this.particleAge;
        }
        final Tessellator tessellator1 = new Tessellator();
        tessellator1.startDrawingQuads();
        tessellator1.setBrightness(this.getBrightnessForRender(par2));
        final float minX = iconX % 16 / 16.0f;
        final float maxX = minX + 0.0624375f;
        final float minY = iconY % 16 / 16.0f;
        final float maxY = minY + 0.0624375f;
        final float scale = 0.1f * this.particleScale;
        final float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - ColoredDust.interpPosX);
        final float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - ColoredDust.interpPosY);
        final float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - ColoredDust.interpPosZ);
        final float f8 = 1.0f;
        tessellator1.setColorOpaque_F(this.particleRed * f8, this.particleGreen * f8, this.particleBlue * f8);
        tessellator1.addVertexWithUV((double)(x - par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z - par5 * scale - par7 * scale), (double)maxX, (double)maxY);
        tessellator1.addVertexWithUV((double)(x - par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z - par5 * scale + par7 * scale), (double)maxX, (double)minY);
        tessellator1.addVertexWithUV((double)(x + par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z + par5 * scale + par7 * scale), (double)minX, (double)minY);
        tessellator1.addVertexWithUV((double)(x + par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z + par5 * scale - par7 * scale), (double)minX, (double)maxY);
        tessellator1.draw();
    }
    
    public void onUpdate() {
        super.onUpdate();
    }
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
}
