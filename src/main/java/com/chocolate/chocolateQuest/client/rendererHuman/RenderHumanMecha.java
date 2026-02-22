package com.chocolate.chocolateQuest.client.rendererHuman;

import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBiped;
import java.util.Random;
import org.lwjgl.util.glu.Sphere;

public class RenderHumanMecha extends RenderHuman
{
    Sphere bubble;
    int divisions;
    Random rnd;
    
    public RenderHumanMecha(final ModelBiped model, final float f, final ResourceLocation r) {
        super(model, f, r);
        this.bubble = new Sphere();
        this.divisions = 15;
        this.rnd = new Random();
    }
    
    @Override
    public void doLeftHandRotationForGolemWeapon() {
        GL11.glTranslatef(0.6f, -0.2f, -1.05f);
    }
    
    public void doRender(final EntityLivingBase entityliving, final double x, final double y, final double z, final float par8, final float par9) {
        super.doRender(entityliving, x, y, z, par8, par9);
    }
    
    public void doRender(final Entity par1Entity, final double x, final double y, final double z, final float par8, final float par9) {
        super.doRender(par1Entity, x, y, z, par8, par9);
        this.renderElectricField(x, y, z, (EntityGolemMecha)par1Entity);
    }
    
    protected void renderElectricField(final double x, final double y, final double z, final EntityGolemMecha entityliving) {
        if (entityliving.hasElectricField() || entityliving.shieldON()) {
            GL11.glDisable(3553);
        }
        final Tessellator tessellator = Tessellator.instance;
        final int color = 5592575;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        this.rnd.setSeed(entityliving.getEntityId() + entityliving.ticksExisted / 2);
        int steps = 16;
        GL11.glLineWidth(1.0f);
        if (entityliving.hasElectricField()) {
            for (int a = 0; a < 5; ++a) {
                GL11.glDisable(2896);
                steps = this.rnd.nextInt(26) + 5;
                tessellator.startDrawing(3);
                tessellator.setColorRGBA_F(0.5f, 0.64f, 1.0f, 0.6f);
                final int startX = this.rnd.nextInt();
                final int startY = this.rnd.nextInt();
                final int startZ = this.rnd.nextInt();
                for (int i = 0; i <= steps; ++i) {
                    final float stepVariation = i / (float)steps;
                    final double dist = entityliving.width * 1.6;
                    final double boltSize = 80.0;
                    final double varX = Math.sin((i + startX) / boltSize * 3.141592653589793 * 2.0) * dist + (this.rnd.nextDouble() - 0.5) * 0.5;
                    final double varZ = Math.cos((i + startZ) / boltSize * 3.141592653589793 * 2.0) * dist + (this.rnd.nextDouble() - 0.5) * 0.5;
                    final double varY = Math.sin((entityliving.ticksExisted + i + startY) / boltSize * 3.141592653589793) + this.rnd.nextDouble() + 1.6;
                    tessellator.addVertex(x + varX, y + varY, z + varZ);
                }
                tessellator.draw();
                GL11.glEnable(2896);
            }
        }
        if (entityliving.hasElectricShield() && entityliving.shieldON()) {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y + entityliving.height / 2.0f + 0.2, z);
            GL11.glRotated((double)entityliving.rotationYaw, x, y, z);
            GL11.glColor4f(0.2f, 0.2f, 1.0f, 0.6f);
            GL11.glDisable(2884);
            GL11.glDepthMask(false);
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            this.bubble.draw((entityliving.width + entityliving.height) * 0.52f, this.divisions, this.divisions);
            GL11.glDepthMask(true);
            GL11.glEnable(2884);
            GL11.glPopMatrix();
        }
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
}
