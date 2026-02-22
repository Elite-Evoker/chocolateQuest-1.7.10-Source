package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.entity.projectile.EntityProjectileBeam;
import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.Render;

public class RenderBeam extends Render
{
    private static final ResourceLocation arrowTextures;
    
    public void doRender(final Entity entity, final double x, final double y, final double z, final float f, final float f1) {
        final Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        this.bindTexture(BDHelper.getItemTexture());
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(-entity.rotationYaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.rotationPitch, 1.0f, 0.0f, 0.0f);
        final EntityProjectileBeam beam = (EntityProjectileBeam)entity;
        final float length = beam.range;
        final float width = 0.05f;
        final Elements element = beam.getElement();
        GL11.glDisable(3553);
        GL11.glColor3f(element.getColorX(), element.getColorY(), element.getColorZ());
        this.drawBox(tessellator, -width, width, -width, width, length, 0.0, 0.9375, 1.0, 0.9375, 1.0, 0.0f);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public RenderBeam(final float f) {
    }
    
    public void drawBox(final Tessellator tessellator, final double x0, final double x1, final double y0, final double y1, final double z0, final double z1, final double tx0, final double tx1, final double ty0, final double ty1, final float b) {
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y0, z0, tx1, ty0);
        tessellator.addVertexWithUV(x0, y1, z0, tx1, ty1);
        tessellator.addVertexWithUV(x1, y1, z0, tx0, ty1);
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(x0, y1, z1, tx0, ty1);
        tessellator.addVertexWithUV(x1, y1, z1, tx1, ty1);
        tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
        tessellator.addVertexWithUV(x0, y0, z1, tx0, ty0);
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(x1, y0, z0, tx1, ty1);
        tessellator.addVertexWithUV(x1, y0, z1, tx0, ty1);
        tessellator.addVertexWithUV(x1, y1, z1, tx0, ty0);
        tessellator.addVertexWithUV(x1, y1, z0, tx1, ty0);
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(x0, y1, z1, tx1, ty0);
        tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
        tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
        tessellator.addVertexWithUV(x0, y1, z0, tx0, ty0);
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
        tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
        tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.addVertexWithUV(x0, y1, z0, tx1, ty0);
        tessellator.addVertexWithUV(x1, y1, z0, tx1, ty1);
        tessellator.addVertexWithUV(x1, y1, z1, tx0, ty1);
        tessellator.addVertexWithUV(x0, y1, z1, tx0, ty0);
        tessellator.draw();
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return null;
    }
    
    static {
        arrowTextures = new ResourceLocation("textures/blocks/water_flow.png");
    }
}
