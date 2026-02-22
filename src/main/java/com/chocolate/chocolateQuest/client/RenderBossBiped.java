package com.chocolate.chocolateQuest.client;

import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.client.model.ModelGiantBoxer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderBiped;

public class RenderBossBiped extends RenderBiped
{
    ResourceLocation texture;
    
    public RenderBossBiped(final ModelBiped par1ModelBiped, final float par2) {
        super(par1ModelBiped, par2);
        this.texture = new ResourceLocation("chocolatequest:textures/entity/mandril.png");
    }
    
    public RenderBossBiped(final ModelBiped par1ModelBiped, final float par2, final ResourceLocation texture) {
        super(par1ModelBiped, par2);
        this.texture = new ResourceLocation("chocolatequest:textures/entity/mandril.png");
        this.texture = texture;
    }
    
    protected void func_82421_b() {
        this.modelArmourChestplate = new ModelGiantBoxer(1.0f);
        this.field_82425_h = new ModelGiantBoxer(0.5f);
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float par2) {
        final EntityBaseBoss e = (EntityBaseBoss)entity;
        final float scale = e.getScaleSize() * 0.76f;
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(0.0f, 0.0f, -0.1f);
        super.preRenderCallback(entity, par2);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.texture;
    }
}
