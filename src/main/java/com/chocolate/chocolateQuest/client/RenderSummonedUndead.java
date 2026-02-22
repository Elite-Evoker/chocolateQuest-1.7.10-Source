package com.chocolate.chocolateQuest.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderBiped;

public class RenderSummonedUndead extends RenderBiped
{
    public static final ResourceLocation skeletonTextures;
    
    public RenderSummonedUndead(final ModelBiped model, final float f) {
        super(model, f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity par1Entity) {
        return RenderSummonedUndead.skeletonTextures;
    }
    
    protected void preRenderCallback(final EntityLivingBase entityliving, final float f) {
        super.preRenderCallback(entityliving, f);
    }
    
    protected void renderEquippedItems(final EntityLivingBase par1EntityLivingBase, final float par2) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, -0.3f, 0.6f);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glRotatef(40.0f, 1.3f, -1.8f, 1.4f);
        super.renderEquippedItems(par1EntityLivingBase, par2);
        GL11.glPopMatrix();
    }
    
    static {
        skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    }
}
