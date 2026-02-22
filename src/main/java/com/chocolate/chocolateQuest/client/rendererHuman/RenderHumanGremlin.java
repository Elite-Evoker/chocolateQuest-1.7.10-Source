package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.model.ModelBiped;
import com.chocolate.chocolateQuest.client.model.ModelGremlin;
import net.minecraft.util.ResourceLocation;

public class RenderHumanGremlin extends RenderHuman
{
    public RenderHumanGremlin(final float f, final ResourceLocation r) {
        super(new ModelGremlin(), f, r);
        this.featherY = -0.3f;
    }
    
    @Override
    protected void func_82421_b() {
        this.modelArmourChestplate = new ModelGremlin(1.0f);
        this.field_82425_h = new ModelGremlin(0.5f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityliving, final float f) {
        super.preRenderCallback(entityliving, f);
        GL11.glTranslatef(0.0f, 0.7f, 0.0f);
    }
    
    @Override
    protected void renderCape(final EntityHumanBase e) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, -0.07f, -0.1f);
        super.renderCape(e);
        GL11.glPopMatrix();
    }
    
    @Override
    protected void setSitOffset() {
        GL11.glTranslatef(0.0f, 0.1f, 0.0f);
    }
}
