package com.chocolate.chocolateQuest.client.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelDragonChineseHead extends ModelBase
{
    ModelRenderer head;
    ModelRenderer mouthUp;
    ModelRenderer mouthDown;
    ModelRenderer nose;
    ModelRenderer hornLeft;
    ModelRenderer hornRight;
    
    public ModelDragonChineseHead() {
        (this.head = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-2.5f, -2.0f, -4.0f, 5, 5, 8);
        (this.mouthUp = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-2.0f, -2.0f, -11.0f, 4, 3, 7);
        (this.mouthDown = new ModelRenderer((ModelBase)this, 32, 10)).addBox(-2.0f, 1.0f, -10.0f, 4, 1, 6);
        (this.nose = new ModelRenderer((ModelBase)this, 27, 0)).addBox(-2.0f, -3.0f, -11.0f, 4, 1, 1);
        (this.hornLeft = new ModelRenderer((ModelBase)this, 27, 18)).addBox(1.5f, -2.0f, 3.0f, 1, 1, 8);
        this.hornLeft.rotateAngleX = 0.5f;
        this.hornLeft.rotateAngleY = 0.098f;
        (this.hornRight = new ModelRenderer((ModelBase)this, 27, 18)).addBox(-2.5f, -2.0f, 3.0f, 1, 1, 8);
        this.hornRight.rotateAngleX = 0.5f;
        this.hornRight.rotateAngleY = -0.298f;
        this.head.addChild(this.mouthUp);
        this.head.addChild(this.mouthDown);
        this.head.addChild(this.nose);
        this.head.addChild(this.hornLeft);
        this.head.addChild(this.hornRight);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        GL11.glPushMatrix();
        GL11.glScalef(3.0f, 3.0f, 3.0f);
        this.head.setRotationPoint(0.0f, 6.0f, 0.0f);
        this.head.render(f5);
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
