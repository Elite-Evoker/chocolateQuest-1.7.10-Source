package com.chocolate.chocolateQuest.client;

import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.client.model.ModelMage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;

public class RenderLivingModel extends RenderLiving
{
    protected ModelBase model;
    private ResourceLocation texture;
    
    public RenderLivingModel(final ResourceLocation r) {
        super((ModelBase)new ModelMage(0.0f), 0.5f);
        this.texture = new ResourceLocation("chocolatequest:textures/entity/necromancer.png");
        this.texture = r;
    }
    
    public RenderLivingModel(final ModelBase model, final float f, final ResourceLocation r) {
        super(model, f);
        this.texture = new ResourceLocation("chocolatequest:textures/entity/necromancer.png");
        this.texture = r;
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.texture;
    }
}
