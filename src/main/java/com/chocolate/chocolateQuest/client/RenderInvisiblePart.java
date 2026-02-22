package com.chocolate.chocolateQuest.client;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.Render;

public class RenderInvisiblePart extends Render
{
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return null;
    }
}
