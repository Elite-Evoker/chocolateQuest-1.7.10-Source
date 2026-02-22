package com.chocolate.chocolateQuest.client.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelDragonPart extends ModelBase
{
    ModelRenderer leg;
    ModelRenderer Head;
    
    public ModelDragonPart() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        (this.leg = new ModelRenderer((ModelBase)this, 0, 24)).addBox(-2.0f, -2.0f, -2.0f, 4, 4, 4);
        this.leg.setRotationPoint(0.0f, -4.0f, 0.0f);
        this.leg.setTextureSize(64, 32);
        this.leg.mirror = true;
        this.setRotation(this.leg, 0.0f, 0.0f, 0.0f);
        (this.Head = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.Head.setRotationPoint(0.0f, -4.0f, 0.0f);
        this.Head.setTextureSize(64, 32);
        this.Head.mirror = true;
        this.setRotation(this.Head, 0.0f, 0.0f, 0.0f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.2f, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        this.Head.render(f5);
        GL11.glPopMatrix();
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
