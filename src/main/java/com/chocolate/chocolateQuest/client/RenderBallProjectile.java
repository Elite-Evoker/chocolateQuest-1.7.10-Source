package com.chocolate.chocolateQuest.client;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import java.util.Random;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicStormProjectile;
import net.minecraft.util.Vec3;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.client.renderer.entity.Render;

public class RenderBallProjectile extends Render
{
    public RenderBallProjectile(final float f) {
    }
    
    public void doRenderFireball(final EntityBaseBall entityball, final double x, final double y, final double z, final float f, final float f1) {
        final int spriteIndex = entityball.getTextureIndex();
        final Tessellator tessellator = Tessellator.instance;
        if (spriteIndex >= 0) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, (float)z);
            GL11.glEnable(32826);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            final float f2 = entityball.getBallSize();
            GL11.glScalef(f2, f2, f2);
            this.bindTexture(BDHelper.texture);
            final float i1 = (spriteIndex % 16 * 16 + 0) / 256.0f;
            final float i2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
            final float i3 = (spriteIndex / 16 * 16 + 0) / 256.0f;
            final float i4 = (spriteIndex / 16 * 16 + 16) / 256.0f;
            final float f3 = 1.0f;
            final float f4 = 0.5f;
            final float f5 = 0.25f;
            GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, 0.0f, entityball.getBallData().getZOffset());
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
        else if (spriteIndex == -1 || spriteIndex == -2) {
            final float swingProgress = entityball.getThrower().getSwingProgress(f1);
            final float f6 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
            final Vec3 vec3 = Vec3.createVectorHelper(-0.5, 0.03, 0.8);
            vec3.rotateAroundX(-(entityball.getThrower().prevRotationPitch + (entityball.getThrower().rotationPitch - entityball.getThrower().prevRotationPitch) * f1) * 3.1415927f / 180.0f);
            vec3.rotateAroundY(-(entityball.getThrower().prevRotationYaw + (entityball.getThrower().rotationYaw - entityball.getThrower().prevRotationYaw) * f1) * 3.1415927f / 180.0f);
            vec3.rotateAroundY(f6 * 0.5f);
            vec3.rotateAroundX(-f6 * 0.7f);
            double endX = entityball.getThrower().prevPosX + (entityball.getThrower().posX - entityball.getThrower().prevPosX) * f1 + vec3.xCoord;
            double endY = entityball.getThrower().prevPosY + (entityball.getThrower().posY - entityball.getThrower().prevPosY) * f1 + vec3.yCoord;
            double endZ = entityball.getThrower().prevPosZ + (entityball.getThrower().posZ - entityball.getThrower().prevPosZ) * f1 + vec3.zCoord;
            if (spriteIndex == -2) {
                final ProjectileMagicStormProjectile ballData = (ProjectileMagicStormProjectile)entityball.getBallData();
                endX = ballData.x;
                endY = ballData.y;
                endZ = ballData.z;
            }
            final double startX = entityball.prevPosX + (entityball.posX - entityball.prevPosX) * f1;
            final double startY = entityball.prevPosY + (entityball.posY - entityball.prevPosY) * f1 + 0.25;
            final double startZ = entityball.prevPosZ + (entityball.posZ - entityball.prevPosZ) * f1;
            final double despX = (float)(endX - startX);
            final double despY = (float)(endY - startY);
            final double despZ = (float)(endZ - startZ);
            final short totalSteps = (short)(entityball.ticksExisted * 2);
            final int color = entityball.getBallData().getRopeColor();
            final float red = 0.4f;
            final float green = 0.4f;
            final float blue = 1.0f;
            tessellator.setColorRGBA_F(red, green, blue, 0.2f);
            GL11.glDisable(2884);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            final float f7 = 0.5f;
            tessellator.setColorRGBA_F(0.9f * f7, 0.9f * f7, 1.0f * f7, 0.3f);
            final Random rnd = new Random();
            final int startingPos = Math.max(0, totalSteps - 60);
            for (int step = 1; step < 4; ++step) {
                tessellator.startDrawing(5);
                tessellator.setColorRGBA_F(red, green, blue, 0.2f);
                rnd.setSeed(entityball.getEntityId());
                final double xdesp = Math.cos(-Math.toRadians(this.renderManager.playerViewY)) * step * 0.02;
                final double zdesp = Math.sin(Math.toRadians(this.renderManager.playerViewY)) * step * 0.02;
                for (int j = startingPos; j <= totalSteps; ++j) {
                    float stepVariation = j / (float)totalSteps;
                    final double varY = 1.0;
                    double diffx = (rnd.nextDouble() - 0.5) * varY;
                    double diffz = (rnd.nextDouble() - 0.5) * varY;
                    double diffy = (rnd.nextDouble() - 0.5) * varY;
                    if (j == startingPos) {
                        stepVariation = 0.0f;
                        diffx = 0.0;
                        diffz = 0.0;
                        diffy = 0.0;
                    }
                    tessellator.addVertex(x + despX - despX * stepVariation + diffx - xdesp, y + despY - despY * stepVariation + diffy, z + despZ - despZ * stepVariation + diffz + zdesp);
                    tessellator.addVertex(x + despX - despX * stepVariation + diffx + xdesp, y + despY - despY * stepVariation + diffy, z + despZ - despZ * stepVariation + diffz - zdesp);
                }
                tessellator.draw();
            }
            GL11.glEnable(3553);
            GL11.glEnable(2896);
        }
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.doRenderFireball((EntityBaseBall)entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return null;
    }
}
