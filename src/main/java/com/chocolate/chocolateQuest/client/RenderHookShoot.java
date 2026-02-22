package com.chocolate.chocolateQuest.client;

import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.Render;

public class RenderHookShoot extends Render
{
    private static final ResourceLocation arrowTextures;
    
    public RenderHookShoot(final float f) {
    }
    
    public void doRenderFireball(final EntityHookShoot entity, final double x, final double y, final double z, final float f, final float f1) {
        final Tessellator tessellator = Tessellator.instance;
        final byte type = entity.getHookType();
        if (type == 3 || type == 5) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, (float)z);
            GL11.glEnable(32826);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            final float f2 = 0.8f;
            GL11.glScalef(f2, f2, f2);
            this.bindTexture(BDHelper.getItemTexture());
            final int spriteIndex = 241;
            final float i1 = (spriteIndex % 16 * 16 + 0) / 256.0f;
            final float i2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
            final float i3 = (spriteIndex / 16 * 16 + 0) / 256.0f;
            final float i4 = (spriteIndex / 16 * 16 + 16) / 256.0f;
            final float f3 = 1.0f;
            final float f4 = 0.5f;
            final float f5 = 0.25f;
            GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0f, 1.0f, 0.0f);
            tessellator.addVertexWithUV((double)(0.0f - f4), (double)(0.0f - f5), 0.0, (double)i1, (double)i4);
            tessellator.addVertexWithUV((double)(f3 - f4), (double)(0.0f - f5), 0.0, (double)i2, (double)i4);
            tessellator.addVertexWithUV((double)(f3 - f4), (double)(1.0f - f5), 0.0, (double)i2, (double)i3);
            tessellator.addVertexWithUV((double)(0.0f - f4), (double)(1.0f - f5), 0.0, (double)i1, (double)i3);
            tessellator.draw();
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
        else {
            final ResourceLocation r = RenderHookShoot.arrowTextures;
            this.bindTexture(r);
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            final byte var11 = 0;
            float txmin = 0.0f;
            float txmax = 0.15625f;
            float tymin = (5 + var11 * 10) / 32.0f;
            float tymax = (10 + var11 * 10) / 32.0f;
            final float var12 = 0.05625f;
            GL11.glEnable(32826);
            GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(var12, var12, var12);
            GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
            GL11.glNormal3f(var12, 0.0f, 0.0f);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-7.0, -2.0, -2.0, (double)txmin, (double)tymin);
            tessellator.addVertexWithUV(-7.0, -2.0, 2.0, (double)txmax, (double)tymin);
            tessellator.addVertexWithUV(-7.0, 2.0, 2.0, (double)txmax, (double)tymax);
            tessellator.addVertexWithUV(-7.0, 2.0, -2.0, (double)txmin, (double)tymax);
            tessellator.draw();
            GL11.glNormal3f(-var12, 0.0f, 0.0f);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-7.0, 2.0, -2.0, (double)txmin, (double)tymin);
            tessellator.addVertexWithUV(-7.0, 2.0, 2.0, (double)txmax, (double)tymin);
            tessellator.addVertexWithUV(-7.0, -2.0, 2.0, (double)txmax, (double)tymax);
            tessellator.addVertexWithUV(-7.0, -2.0, -2.0, (double)txmin, (double)tymax);
            tessellator.draw();
            txmin = 0.0f;
            txmax = 0.5f;
            tymin = (0 + var11 * 10) / 32.0f;
            tymax = (5 + var11 * 10) / 32.0f;
            for (int var13 = 0; var13 < 4; ++var13) {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glNormal3f(0.0f, 0.0f, var12);
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(-8.0, -2.0, 0.0, (double)txmin, (double)tymin);
                tessellator.addVertexWithUV(8.0, -2.0, 0.0, (double)txmax, (double)tymin);
                tessellator.addVertexWithUV(8.0, 2.0, 0.0, (double)txmax, (double)tymax);
                tessellator.addVertexWithUV(-8.0, 2.0, 0.0, (double)txmin, (double)tymax);
                tessellator.draw();
            }
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
        if (entity.getThrower() != null) {
            final float f6 = entity.getThrower().getSwingProgress(f1);
            final float f7 = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.1415927f);
            final Vec3 vec3 = Vec3.createVectorHelper(-0.5, 0.03, 0.8);
            vec3.rotateAroundX(-(entity.getThrower().prevRotationPitch + (entity.getThrower().rotationPitch - entity.getThrower().prevRotationPitch) * f1) * 3.1415927f / 180.0f);
            vec3.rotateAroundY(-(entity.getThrower().prevRotationYaw + (entity.getThrower().rotationYaw - entity.getThrower().prevRotationYaw) * f1) * 3.1415927f / 180.0f);
            vec3.rotateAroundY(f7 * 0.5f);
            vec3.rotateAroundX(-f7 * 0.7f);
            double endX = entity.getThrower().prevPosX + (entity.getThrower().posX - entity.getThrower().prevPosX) * f1 + vec3.xCoord;
            double endY = entity.getThrower().prevPosY + (entity.getThrower().posY - entity.getThrower().prevPosY) * f1 + vec3.yCoord;
            double endZ = entity.getThrower().prevPosZ + (entity.getThrower().posZ - entity.getThrower().prevPosZ) * f1 + vec3.zCoord;
            final double d6 = (entity.getThrower() != Minecraft.getMinecraft().thePlayer) ? entity.getThrower().getEyeHeight() : 0.0;
            if (this.renderManager.options.thirdPersonView > 0 || entity.getThrower() != Minecraft.getMinecraft().thePlayer) {
                final float f8 = (entity.getThrower().prevRenderYawOffset + (entity.getThrower().renderYawOffset - entity.getThrower().prevRenderYawOffset) * f1) * 3.1415927f / 180.0f;
                final double d7 = MathHelper.sin(f8);
                final double d8 = MathHelper.cos(f8);
                endX = entity.getThrower().prevPosX + (entity.getThrower().posX - entity.getThrower().prevPosX) * f1 - d8 * 0.35 - d7 * 0.85;
                endY = entity.getThrower().prevPosY + d6 + (entity.getThrower().posY - entity.getThrower().prevPosY) * f1 - 0.45;
                endZ = entity.getThrower().prevPosZ + (entity.getThrower().posZ - entity.getThrower().prevPosZ) * f1 - d7 * 0.35 + d8 * 0.85;
            }
            final double startX = entity.prevPosX + (entity.posX - entity.prevPosX) * f1;
            final double startY = entity.prevPosY + (entity.posY - entity.prevPosY) * f1 + 0.25;
            final double startZ = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * f1;
            final double despX = (float)(endX - startX);
            final double despY = (float)(endY - startY);
            final double despZ = (float)(endZ - startZ);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            final byte steps = 16;
            double hang = entity.getRadius() - entity.getDistanceToEntity((Entity)entity.getThrower());
            if (hang < 0.2) {
                hang = 0.0;
            }
            tessellator.startDrawing(3);
            tessellator.setColorOpaque_I(entity.getRopeColor());
            for (int j = 0; j <= steps; ++j) {
                final float stepVariation = j / (float)steps;
                final double varY = -Math.sin(j * 3.141592653589793 / steps) * hang;
                tessellator.addVertex(x + despX * stepVariation, y + despY * stepVariation + varY, z + despZ * stepVariation);
            }
            tessellator.draw();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.doRenderFireball((EntityHookShoot)entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return null;
    }
    
    static {
        arrowTextures = new ResourceLocation("textures/entity/arrow.png");
    }
}
