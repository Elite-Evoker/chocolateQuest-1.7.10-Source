package com.chocolate.chocolateQuest.client.blockRender;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.Minecraft;
import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import net.minecraft.tileentity.TileEntity;
import com.chocolate.chocolateQuest.client.RenderBanner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderBlockBanner extends TileEntitySpecialRenderer
{
    private static final ResourceLocation enderPortalEndSkyTextures;
    private static final ResourceLocation endPortalTextures;
    RenderBanner render;
    
    public RenderBlockBanner() {
        this.render = new RenderBanner(0.0f);
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double x, final double y, final double z, final float f) {
        final BlockBannerStandTileEntity te = (BlockBannerStandTileEntity)tileentity;
        if (te.item != null) {
            if (te.hasFlag) {
                this.render.renderBanner(x + 0.5, y, z + 0.5, (float)te.rotation, te.item.getMetadata(), this.field_147501_a.renderEngine);
            }
            else {
                GL11.glPushMatrix();
                GL11.glEnable(2884);
                this.field_147501_a.renderEngine.bindTexture(BDHelper.getItemTexture());
                GL11.glTranslatef((float)x + 0.5f, (float)y, (float)z + 0.5f);
                GL11.glRotatef((float)(-te.rotation), 0.0f, 1.0f, 0.0f);
                GL11.glTranslatef(-0.5f, 0.0f, 0.0f);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                for (int c = 0; c < 6; ++c) {
                    GL11.glMatrixMode(5890);
                    GL11.glPushMatrix();
                    if (c == 0) {
                        this.bindTexture(RenderBlockBanner.enderPortalEndSkyTextures);
                        GL11.glBlendFunc(770, 771);
                        GL11.glTexGeni(8193, 9472, 9216);
                        GL11.glTexGeni(8192, 9472, 9216);
                        GL11.glEnable(3168);
                        GL11.glEnable(3169);
                        final float scale = 4.0f - Minecraft.getSystemTime() % 10000L / 10000.0f * 4.0f;
                        GL11.glScalef(scale, scale, scale);
                        GL11.glRotatef(scale, 0.0f, 1.0f, 0.0f);
                    }
                    else {
                        this.bindTexture(RenderBlockBanner.endPortalTextures);
                        GL11.glBlendFunc(1, 1);
                        GL11.glScalef((float)c, (float)c, (float)c);
                        GL11.glTranslatef(c * 0.8f, Minecraft.getSystemTime() % 70000L / 7000.0f * c, 0.0f);
                    }
                    final Tessellator tessellator = Tessellator.instance;
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(0.0, 0.10000000149011612, 0.001, 0.0, 0.0);
                    tessellator.addVertexWithUV(1.0, 0.10000000149011612, 0.001, 1.0, 0.0);
                    tessellator.addVertexWithUV(1.0, 2.0, 0.001, 1.0, 1.0);
                    tessellator.addVertexWithUV(0.0, 2.0, 0.001, 0.0, 1.0);
                    tessellator.draw();
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(5888);
                    GL11.glDisable(3168);
                    GL11.glDisable(3169);
                }
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }
    }
    
    static {
        enderPortalEndSkyTextures = new ResourceLocation("textures/environment/end_sky.png");
        endPortalTextures = new ResourceLocation("textures/entity/end_portal.png");
    }
}
