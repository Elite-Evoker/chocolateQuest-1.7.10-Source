package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelDragonQuadruped extends ModelBase
{
    ModelRenderer rightWing;
    ModelRenderer rightWingPart;
    ModelRenderer rightWingArm;
    ModelRenderer rightWingArmPart;
    ModelRenderer leftWing;
    ModelRenderer leftWingPart;
    ModelRenderer leftWingArm;
    ModelRenderer leftWingArmPart;
    ModelRenderer body1;
    ModelRenderer body2;
    ModelRenderer body3;
    ModelRenderer[] portal;
    ModelRenderer neck;
    ModelRenderer tail;
    ModelRenderer tail2;
    ModelRenderer tailEnd;
    ModelRenderer head;
    ModelRenderer mouthUp;
    ModelRenderer mouthDown;
    ModelRenderer nose;
    ModelRenderer hornLeft;
    ModelRenderer hornRight;
    ModelRenderer[] leg;
    ModelRenderer[] leg1;
    ModelRenderer[] foot;
    
    public ModelDragonQuadruped() {
        final int textureSizeX = 128;
        final int textureSizeY = 64;
        (this.body1 = new ModelRenderer((ModelBase)this, 0, 0).setTextureSize(textureSizeX, textureSizeY)).addBox(-7.0f, -7.0f, -7.0f, 14, 14, 14);
        this.body1.setRotationPoint(0.0f, 0.0f, -10.0f);
        this.body1.rotateAngleX = 45.0f;
        this.body1.rotateAngleY = 0.0f;
        this.body1.rotateAngleZ = 0.0f;
        (this.body2 = new ModelRenderer((ModelBase)this, 25, 40).setTextureSize(textureSizeX, textureSizeY)).addBox(-5.0f, -4.0f, -8.0f, 10, 8, 16);
        this.body2.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.body2.rotateAngleX = 0.0f;
        this.body2.rotateAngleY = 0.0f;
        this.body2.rotateAngleZ = 0.0f;
        (this.body3 = new ModelRenderer((ModelBase)this, 0, 28).setTextureSize(textureSizeX, textureSizeY)).addBox(-5.0f, -5.0f, -5.0f, 10, 10, 10);
        this.body3.setRotationPoint(0.0f, 0.0f, 10.0f);
        this.body3.rotateAngleX = 45.0f;
        this.body3.rotateAngleY = 0.0f;
        this.body3.rotateAngleZ = 0.0f;
        (this.neck = new ModelRenderer((ModelBase)this, 0, 51).setTextureSize(textureSizeX, textureSizeY)).addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6);
        this.neck.setRotationPoint(0.0f, -6.0f, -14.0f);
        this.neck.rotateAngleX = 0.0f;
        this.neck.rotateAngleY = 0.0f;
        this.neck.rotateAngleZ = 0.0f;
        (this.tail = new ModelRenderer((ModelBase)this, 40, 28).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.0f, -2.0f, -2.0f, 4, 4, 4);
        this.tail.setRotationPoint(0.0f, -3.0f, 14.0f);
        this.tail.rotateAngleX = 0.0f;
        this.tail.rotateAngleY = 0.0f;
        this.tail.rotateAngleZ = 0.0f;
        (this.tail2 = new ModelRenderer((ModelBase)this, 42, 30).setTextureSize(textureSizeX, textureSizeY)).addBox(-1.0f, -1.0f, -1.0f, 2, 2, 2);
        this.tail2.setRotationPoint(0.0f, -3.0f, 14.0f);
        (this.tailEnd = new ModelRenderer((ModelBase)this, 44, 0).setTextureSize(textureSizeX, textureSizeY)).addBox(0.0f, -2.0f, -2.0f, 0, 4, 4);
        this.leg = new ModelRenderer[4];
        this.leg1 = new ModelRenderer[4];
        this.foot = new ModelRenderer[4];
        for (int i = 0; i < 4; ++i) {
            (this.leg[i] = new ModelRenderer((ModelBase)this, 56, 0).setTextureSize(textureSizeX, textureSizeY)).addBox(-1.0f, -4.0f, -2.0f, 4, 14, 4);
            (this.leg1[i] = new ModelRenderer((ModelBase)this, 56, 19).setTextureSize(textureSizeX, textureSizeY)).addBox(-1.5f, 1.0f, -1.5f, 3, 10, 3);
            this.leg[i].addChild(this.leg1[i]);
            this.leg1[i].setRotationPoint(1.0f, 7.0f, -1.0f);
            (this.foot[i] = new ModelRenderer((ModelBase)this, 52, 32).setTextureSize(textureSizeX, textureSizeY)).addBox(-1.5f, -1.0f, -4.0f, 3, 2, 5);
            this.leg1[i].addChild(this.foot[i]);
            this.foot[i].setRotationPoint(0.0f, 11.0f, 0.0f);
        }
        this.leg[0].setRotationPoint(8.0f, 5.0f, -10.0f);
        this.leg[1].setRotationPoint(-10.0f, 5.0f, -10.0f);
        this.leg[2].setRotationPoint(6.0f, 5.0f, 10.0f);
        this.leg[3].setRotationPoint(-8.0f, 5.0f, 10.0f);
        (this.head = new ModelRenderer((ModelBase)this, 74, 0).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.5f, -3.0f, -4.0f, 5, 5, 8);
        (this.mouthUp = new ModelRenderer((ModelBase)this, 106, 0).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.0f, -2.0f, -11.0f, 4, 3, 7);
        (this.mouthDown = new ModelRenderer((ModelBase)this, 92, 0).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.0f, 1.0f, -10.0f, 4, 1, 6);
        (this.nose = new ModelRenderer((ModelBase)this, 107, 11).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.0f, -3.0f, -11.0f, 4, 1, 1);
        (this.hornLeft = new ModelRenderer((ModelBase)this, 94, 8).setTextureSize(textureSizeX, textureSizeY)).addBox(1.5f, -2.0f, 3.0f, 1, 1, 8);
        this.hornLeft.rotateAngleX = 0.5f;
        this.hornLeft.rotateAngleY = 0.098f;
        (this.hornRight = new ModelRenderer((ModelBase)this, 94, 8).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.5f, -2.0f, 3.0f, 1, 1, 8);
        this.hornRight.rotateAngleX = 0.5f;
        this.hornRight.rotateAngleY = -0.298f;
        this.head.addChild(this.mouthUp);
        this.head.addChild(this.mouthDown);
        this.head.addChild(this.nose);
        this.head.addChild(this.hornLeft);
        this.head.addChild(this.hornRight);
        (this.rightWing = new ModelRenderer((ModelBase)this, 58, 43).setTextureSize(textureSizeX, textureSizeY)).addBox(0.0f, 0.0f, 0.0f, 14, 0, 21);
        this.rightWing.setRotationPoint(6.0f, -4.0f, -10.0f);
        (this.rightWingPart = new ModelRenderer((ModelBase)this, 46, 21).setTextureSize(textureSizeX, textureSizeY)).addBox(0.0f, 0.0f, 0.0f, 30, 0, 22);
        this.rightWingPart.setRotationPoint(14.0f, 0.0f, 0.0f);
        (this.rightWingArm = new ModelRenderer((ModelBase)this, 82, 17).setTextureSize(textureSizeX, textureSizeY)).addBox(0.0f, -1.0f, 0.0f, 14, 2, 2);
        (this.rightWingArmPart = new ModelRenderer((ModelBase)this, 64, 18).setTextureSize(textureSizeX, textureSizeY)).addBox(0.0f, -1.0f, 0.0f, 30, 1, 1);
        this.rightWing.addChild(this.rightWingPart);
        this.rightWing.addChild(this.rightWingArm);
        this.rightWingPart.addChild(this.rightWingArmPart);
        (this.leftWing = new ModelRenderer((ModelBase)this, 58, 43).setTextureSize(textureSizeX, textureSizeY)).addBox(0.0f, 0.0f, 0.0f, 14, 0, 21);
        this.leftWing.setRotationPoint(-6.0f, -4.0f, -10.0f);
        (this.leftWingPart = new ModelRenderer((ModelBase)this, 46, 21).setTextureSize(textureSizeX, textureSizeY)).addBox(0.0f, 0.0f, 0.0f, 30, 0, 22);
        this.leftWingPart.setRotationPoint(14.0f, 0.0f, 0.0f);
        (this.leftWingArm = new ModelRenderer((ModelBase)this, 82, 17).setTextureSize(textureSizeX, textureSizeY)).addBox(0.0f, -1.0f, 0.0f, 14, 2, 2);
        (this.leftWingArmPart = new ModelRenderer((ModelBase)this, 64, 18).setTextureSize(textureSizeX, textureSizeY)).addBox(0.0f, -1.0f, 0.0f, 30, 1, 1);
        this.leftWing.addChild(this.leftWingPart);
        this.leftWing.addChild(this.leftWingArm);
        this.leftWingPart.addChild(this.leftWingArmPart);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.rightWing.render(f5);
        this.leftWing.render(f5);
        this.body1.render(f5);
        this.body2.render(f5);
        this.body3.render(f5);
        for (int i = 0; i < 4; ++i) {
            this.leg[i].render(f5);
        }
        float px = 0.0f;
        float py = -6.0f;
        float pz = -14.0f;
        final float rotX = MathHelper.cos(f / 1.9191077f) * 0.2617994f * f1;
        for (int j = 0; j < 4; ++j) {
            final float f6 = (float)Math.cos(j * 0.45f) * 0.15f;
            this.neck.rotateAngleX = j / 4.0f - 0.4f + this.head.rotateAngleX;
            this.neck.rotateAngleY = this.head.rotateAngleY * (j / 1.8f);
            this.neck.rotateAngleZ = this.head.rotateAngleY / 10.0f;
            this.neck.rotationPointX = px;
            this.neck.rotationPointY = py;
            this.neck.rotationPointZ = pz;
            px -= (float)(Math.sin(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 6.0);
            py += (float)(Math.sin(this.neck.rotateAngleX) * 6.0);
            pz -= (float)(Math.cos(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 6.0);
            this.neck.render(f5);
        }
        this.head.rotationPointX = px;
        this.head.rotationPointY = py - 0.4f;
        this.head.rotationPointZ = pz;
        this.head.rotateAngleY = this.neck.rotateAngleY;
        this.head.render(f5);
        px = 0.0f;
        py = -3.0f;
        pz = 14.0f;
        for (int j = 0; j < 10; ++j) {
            final float rotXVar = (float)Math.cos((j / 10.0 + f / 10.0f) * 3.141592653589793 * 2.0) * 0.5f;
            final float AnimOnTime = (float)Math.cos((j / 10.0 + f2 / 50.0f) * 3.141592653589793 * 2.0);
            this.tail.rotateAngleX = 3.16f + rotXVar;
            this.tail.rotateAngleY = AnimOnTime;
            this.tail.rotateAngleZ = 0.0f;
            this.tail.rotationPointX = px;
            this.tail.rotationPointY = py;
            this.tail.rotationPointZ = pz;
            px -= (float)(Math.sin(this.tail.rotateAngleY) * Math.cos(this.tail.rotateAngleX) * 4.0);
            py += (float)(Math.sin(this.tail.rotateAngleX) * 4.0);
            pz -= (float)(Math.cos(this.tail.rotateAngleY) * Math.cos(this.tail.rotateAngleX) * 4.0);
            this.tail.render(f5);
        }
        px += 2.0f;
        pz -= 2.0f;
        for (int j = 0; j < 10; ++j) {
            final float f6 = (float)Math.cos((j / 10.0 + f / 10.0f) * 3.141592653589793 * 2.0);
            final float AnimOnTime = (float)Math.cos((j / 10.0 + f2 / 40.0f) * 3.141592653589793 * 2.0);
            this.tail2.rotateAngleX = 3.16f + f6;
            this.tail2.rotateAngleY = AnimOnTime;
            this.tail2.rotateAngleZ = 0.0f;
            this.tail2.rotationPointX = px;
            this.tail2.rotationPointY = py;
            this.tail2.rotationPointZ = pz;
            px -= (float)(Math.sin(this.tail2.rotateAngleY) * Math.cos(this.tail.rotateAngleX) * 2.0);
            py += (float)(Math.sin(this.tail2.rotateAngleX) * 2.0);
            pz -= (float)(Math.cos(this.tail2.rotateAngleY) * Math.cos(this.tail.rotateAngleX) * 2.0);
            this.tail2.render(f5);
        }
        this.tailEnd.rotationPointX = px;
        this.tailEnd.rotationPointY = py;
        this.tailEnd.rotationPointZ = pz;
        this.tailEnd.render(f5);
    }
    
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity entity) {
        this.head.rotateAngleX = par5 / 57.295776f;
        this.head.rotateAngleY = par4 / 57.295776f;
        final float rx = MathHelper.cos(par1 * 0.4662f) * 1.4f * par2;
        for (int i = 0; i < 4; ++i) {
            final float sign = (i % 2 == 0) ? 1.0f : -1.0f;
            this.leg[i].rotateAngleX = rx * sign;
            this.leg1[i].rotateAngleX = -0.436f - rx * 2.0f * sign;
            this.foot[i].rotateAngleX = 0.576f - rx * -0.3f * sign;
        }
        final float yOffsetSit = 14.0f;
        this.rightWing.rotateAngleZ = -1.3f;
        this.rightWingPart.rotateAngleY = -1.5f;
        this.rightWingPart.rotateAngleZ = 0.0f;
        if (!entity.onGround) {
            for (int j = 0; j < 4; ++j) {
                this.leg[j].rotateAngleX = -0.9f;
                this.leg1[j].rotateAngleX = 2.4f;
            }
            final float wingRotation;
            final float motionOnTime = wingRotation = MathHelper.cos((par3 + par1) * 0.1f);
            this.rightWing.rotateAngleZ = wingRotation;
            this.rightWingPart.rotateAngleZ = wingRotation / 2.0f;
            this.rightWingPart.rotateAngleY = -0.25f;
            this.leftWing.rotateAngleZ = -wingRotation;
            this.leftWingPart.rotateAngleZ = -wingRotation / 2.0f;
            this.leftWingPart.rotateAngleY = -0.25f;
        }
        if (this.swingProgress > 0.0f) {
            this.leg[1].rotateAngleX = MathHelper.sin(this.swingProgress * 3.1415927f) * -1.4f;
            this.leg[1].rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927f) * -1.0f;
            this.leg[0].rotateAngleX = MathHelper.sin(this.swingProgress * 3.1415927f) * -1.4f;
            this.leg[0].rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927f);
        }
        this.leftWing.rotateAngleZ = 3.1416f - this.rightWing.rotateAngleZ;
        this.leftWingPart.rotateAngleZ = -this.rightWingPart.rotateAngleZ;
        this.leftWingPart.rotateAngleY = this.rightWingPart.rotateAngleY;
        final EntityWyvern e = (EntityWyvern)entity;
        this.mouthDown.rotateAngleX = 0.0f;
        this.mouthDown.rotateAngleY = 0.0f;
        if (e.openMouthTime > 0) {
            final float n = e.openMouthTime + (e.openMouthTime - 1) * par6;
            e.getClass();
            final float animProgress = (float)(n / 10.0f * 3.141592653589793);
            final ModelRenderer mouthDown = this.mouthDown;
            mouthDown.rotateAngleX += MathHelper.sin(animProgress) * 0.6f;
        }
    }
}
