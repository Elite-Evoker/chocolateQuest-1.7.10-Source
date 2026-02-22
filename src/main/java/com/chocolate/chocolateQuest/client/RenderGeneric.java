package com.chocolate.chocolateQuest.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;

public class RenderGeneric extends Render
{
    protected ModelBase model;
    private ResourceLocation texture;
    
    public RenderGeneric(final ModelBase par1ModelBase, final float par2) {
        this.texture = new ResourceLocation("chocolatequest:textures/entity/dragonbd.png");
        this.model = par1ModelBase;
        this.shadowSize = par2;
    }
    
    public RenderGeneric(final ModelBase par1ModelBase, final float par2, final ResourceLocation texture) {
        this(par1ModelBase, par2);
        this.texture = texture;
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.texture;
    }
    
    public void doRender(final Entity entity, final double d0, final double d1, final double d2, final float f, final float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d0, (float)d1, (float)d2);
        GL11.glRotatef(-180.0f - f, 0.0f, 1.0f, 0.0f);
        this.bindTexture(this.texture);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        this.model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
}
