package com.chocolate.chocolateQuest.client;

import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBase;

public class RenderLivingBossModel extends RenderLivingModel
{
    public RenderLivingBossModel(final ModelBase model, final float f, final ResourceLocation r) {
        super(model, f, r);
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float par2) {
        super.preRenderCallback(entity, par2);
        final float scale = ((EntityBaseBoss)entity).getScaleSize();
        GL11.glScalef(scale, scale, scale);
    }
}
