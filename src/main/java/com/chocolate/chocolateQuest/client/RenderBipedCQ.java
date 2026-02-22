package com.chocolate.chocolateQuest.client;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;

public class RenderBipedCQ extends RenderBiped
{
    protected ModelBiped model;
    private ResourceLocation texture;
    
    public RenderBipedCQ(final ResourceLocation r) {
        super(new ModelBiped(0.0f), 0.5f);
        this.texture = new ResourceLocation("chocolatequest:textures/entity/necromancer.png");
        this.texture = r;
    }
    
    public RenderBipedCQ(final ModelBiped model, final float f, final ResourceLocation r) {
        super(model, f);
        this.texture = new ResourceLocation("chocolatequest:textures/entity/necromancer.png");
        this.texture = r;
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.texture;
    }
}
