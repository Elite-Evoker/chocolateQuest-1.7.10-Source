package com.chocolate.chocolateQuest.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBase;

public class RenderLivingModelFly extends RenderLivingBossModel
{
    public RenderLivingModelFly(final ModelBase model, final float f, final ResourceLocation r) {
        super(model, f, r);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float par2) {
        GL11.glRotatef(entity.rotationPitch, 1.0f, 0.0f, 0.0f);
        super.preRenderCallback(entity, par2);
    }
}
