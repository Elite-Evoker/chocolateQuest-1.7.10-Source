package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.particle.EntityFX;

public class EffectBase extends EntityFX
{
    private static final ResourceLocation particleTextures;
    protected float iconsAmmount;
    protected float iconScale;
    
    public EffectBase(final World par1World, final double posX, final double posY, final double posZ, final double par8, final double par10, final double par12) {
        super(par1World, posX, posY, posZ, par8, par10, par12);
        this.iconsAmmount = 16.0f;
        this.iconScale = 8.0f;
    }
    
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        final int iconX = this.particleTextureIndexX;
        final int iconY = this.particleTextureIndexY;
        final Tessellator tessellator1 = new Tessellator();
        tessellator1.startDrawingQuads();
        tessellator1.setBrightness(this.getBrightnessForRender(par2));
        Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getParticleTexture());
        final float iconWidth = this.iconScale / 128.0f;
        final float minX = iconX / this.iconsAmmount;
        final float maxX = minX + iconWidth;
        final float minY = iconY / this.iconsAmmount;
        final float maxY = minY + iconWidth;
        final float scale = 0.1f * this.particleScale;
        final float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - EffectBase.interpPosX);
        final float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - EffectBase.interpPosY);
        final float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - EffectBase.interpPosZ);
        final float f8 = 1.0f;
        tessellator1.setColorRGBA_F(this.particleRed * f8, this.particleGreen * f8, this.particleBlue * f8, 1.0f);
        tessellator1.addVertexWithUV((double)(x - par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z - par5 * scale - par7 * scale), (double)maxX, (double)maxY);
        tessellator1.addVertexWithUV((double)(x - par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z - par5 * scale + par7 * scale), (double)maxX, (double)minY);
        tessellator1.addVertexWithUV((double)(x + par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z + par5 * scale + par7 * scale), (double)minX, (double)minY);
        tessellator1.addVertexWithUV((double)(x + par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z + par5 * scale - par7 * scale), (double)minX, (double)maxY);
        tessellator1.draw();
        Minecraft.getMinecraft().renderEngine.bindTexture(EffectBase.particleTextures);
    }
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
}
