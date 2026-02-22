package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.EntityTurtlePart;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelTurtle extends ModelBase
{
    ModelRenderer Body;
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer leg;
    ModelRenderer head;
    
    public ModelTurtle() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        final float y = 23.0f;
        (this.Body = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-8.0f, -6.0f + y, -8.0f, 16, 7, 16);
        this.Body.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.Body.setTextureSize(64, 32);
        this.Body.mirror = true;
        (this.Shape1 = new ModelRenderer((ModelBase)this, 18, 2)).addBox(1.0f, -8.0f + y, 0.0f, 5, 1, 6);
        this.Shape1.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.Shape1.setTextureSize(64, 32);
        this.Shape1.mirror = true;
        (this.Shape2 = new ModelRenderer((ModelBase)this, 18, 9)).addBox(0.0f, -8.0f + y, -6.0f, 6, 1, 5);
        this.Shape2.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.Shape2.setTextureSize(64, 32);
        this.Shape2.mirror = true;
        (this.Shape3 = new ModelRenderer((ModelBase)this, 13, 2)).addBox(-6.0f, -8.0f + y, 1.0f, 6, 1, 5);
        this.Shape3.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.Shape3.setTextureSize(64, 32);
        this.Shape3.mirror = true;
        (this.Shape4 = new ModelRenderer((ModelBase)this, 12, 7)).addBox(-6.0f, -8.0f + y, -6.0f, 5, 1, 6);
        this.Shape4.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.Shape4.setTextureSize(64, 32);
        this.Shape4.mirror = true;
        (this.Shape5 = new ModelRenderer((ModelBase)this, 2, 1)).addBox(-7.0f, -7.0f + y, -7.0f, 14, 1, 14);
        this.Shape5.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.Shape5.setTextureSize(64, 32);
        this.Shape5.mirror = true;
        (this.leg = new ModelRenderer((ModelBase)this, 0, 24)).addBox(-2.0f, 1.0f + y, -2.0f, 4, 4, 4);
        this.leg.setRotationPoint(0.0f, -4.0f, 0.0f);
        this.leg.setTextureSize(64, 32);
        this.leg.mirror = true;
        (this.head = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-2.0f, 0.0f + y, -2.0f, 4, 4, 4);
        this.head.setRotationPoint(0.0f, -4.0f, 0.0f);
        this.head.setTextureSize(64, 32);
        this.head.mirror = true;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Body.render(f5);
        this.Shape1.render(f5);
        this.Shape2.render(f5);
        this.Shape3.render(f5);
        this.Shape4.render(f5);
        this.Shape5.render(f5);
        final EntityTurtle turtle = (EntityTurtle)entity;
        final EntityTurtlePart[] parts = ((EntityTurtle)entity).getBossParts();
        final float scale = turtle.getScaleSize();
        if (!turtle.isAttacking() && !turtle.isHealing()) {
            for (final EntityTurtlePart part : parts) {
                if (part != null) {
                    if (part.isHead()) {
                        this.head.offsetZ = -part.distanceToMainBody / scale;
                        this.head.rotateAngleY = -(turtle.rotationYaw - turtle.prevRotationYaw) * 3.141592f / 18.0f * f5;
                        this.head.render(f5);
                    }
                    else {
                        final float hx = -MathHelper.sin(part.rotationYawOffset * 3.141592f / 180.0f) * part.distanceToMainBody / scale;
                        final float hz = -MathHelper.cos(part.rotationYawOffset * 3.141592f / 180.0f) * part.distanceToMainBody / scale;
                        this.leg.offsetX = hx;
                        this.leg.offsetZ = hz;
                        this.leg.render(f5);
                    }
                }
            }
        }
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
