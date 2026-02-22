package com.chocolate.chocolateQuest.client.model;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class Modelbee extends ModelBase
{
    ModelRenderer mainBody;
    ModelRenderer leftWing;
    ModelRenderer head;
    ModelRenderer tail;
    ModelRenderer leftWing1;
    ModelRenderer sendaryBody;
    
    public Modelbee() {
        (this.mainBody = new ModelRenderer((ModelBase)this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 6, 5, 8);
        this.mainBody.setRotationPoint(-2.0f, 0.0f, 0.0f);
        this.mainBody.rotateAngleX = 0.0f;
        this.mainBody.rotateAngleY = 0.0f;
        this.mainBody.rotateAngleZ = 0.0f;
        this.mainBody.mirror = false;
        (this.leftWing = new ModelRenderer((ModelBase)this, 0, 21)).addBox(0.0f, 0.0f, 0.0f, 7, 0, 11);
        this.leftWing.setRotationPoint(0.0f, 1.0f, 7.0f);
        this.leftWing.rotateAngleX = 0.00227f;
        this.leftWing.rotateAngleY = 1.041f;
        this.leftWing.rotateAngleZ = 0.0f;
        this.leftWing.mirror = false;
        (this.head = new ModelRenderer((ModelBase)this, 37, 13)).addBox(0.0f, 1.0f, 0.0f, 4, 4, 5);
        this.head.setRotationPoint(-1.0f, 0.0f, -4.0f);
        this.head.rotateAngleX = 0.0f;
        this.head.rotateAngleY = 0.0f;
        this.head.rotateAngleZ = 0.0f;
        this.head.mirror = false;
        (this.tail = new ModelRenderer((ModelBase)this, 56, 0)).addBox(0.0f, 0.0f, 0.0f, 2, 7, 2);
        this.tail.setRotationPoint(0.0f, 7.0f, 8.0f);
        this.tail.rotateAngleX = -0.88392f;
        this.tail.rotateAngleY = 0.0f;
        this.tail.rotateAngleZ = 0.0f;
        this.tail.mirror = false;
        (this.leftWing1 = new ModelRenderer((ModelBase)this, 0, 21)).addBox(0.0f, 0.0f, 0.0f, 7, 0, 11);
        this.leftWing1.setRotationPoint(-2.0f, 1.0f, 1.0f);
        this.leftWing1.rotateAngleX = 0.00227f;
        this.leftWing1.rotateAngleY = -1.02974f;
        this.leftWing1.rotateAngleZ = 0.0f;
        this.leftWing1.mirror = false;
        (this.sendaryBody = new ModelRenderer((ModelBase)this, 29, 1)).addBox(0.0f, 0.0f, 0.0f, 4, 3, 7);
        this.sendaryBody.setRotationPoint(-1.0f, 1.0f, 8.0f);
        this.sendaryBody.rotateAngleX = -1.07818f;
        this.sendaryBody.rotateAngleY = 0.0f;
        this.sendaryBody.rotateAngleZ = 0.0f;
        this.sendaryBody.mirror = false;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.mainBody.render(f5);
        this.leftWing.render(f5);
        this.head.render(f5);
        this.tail.render(f5);
        this.leftWing1.render(f5);
        this.sendaryBody.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.leftWing.rotateAngleX = MathHelper.cos((float)(f / 1.9191077f * 1.5707963267949 * f1 + 0.00227202624519969));
        this.leftWing1.rotateAngleX = MathHelper.cos((float)(f / 1.9191077f * 1.5707963267949 * f1 + 0.00227202624519969));
        this.tail.rotateAngleX = MathHelper.cos((float)(f / 1.9191077f * 0.523598775598299 * f1 - 0.883921483302927));
    }
}
