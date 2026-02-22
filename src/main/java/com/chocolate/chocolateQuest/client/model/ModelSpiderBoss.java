package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.AttackPunch;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelSpiderBoss extends ModelBase
{
    public ModelRenderer spiderHead;
    public ModelRenderer spiderNeck;
    public ModelRenderer spiderBody;
    public ModelRenderer[] spiderLeg;
    public ModelRenderer[] spiderLegPart;
    public ModelRenderer spiderMouthLeft;
    public ModelRenderer spiderMouthRight;
    ModelRenderer rightarm1;
    ModelRenderer leftarm1;
    ModelRenderer bipedRightArm;
    ModelRenderer bipedLeftArm;
    ModelRenderer leftNeedle;
    ModelRenderer rightNeedle;
    
    public ModelSpiderBoss() {
        final float f = 0.0f;
        final int i = 15;
        this.textureWidth = 64;
        this.textureHeight = 64;
        (this.spiderHead = new ModelRenderer((ModelBase)this, 32, 4)).addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, f);
        this.spiderHead.setRotationPoint(0.0f, (float)(0 + i), -3.0f);
        this.spiderHead.setTextureSize(this.textureWidth, this.textureHeight);
        (this.spiderNeck = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6, f);
        this.spiderNeck.setRotationPoint(0.0f, (float)i, 0.0f);
        this.spiderNeck.setTextureSize(this.textureWidth, this.textureHeight);
        (this.spiderBody = new ModelRenderer((ModelBase)this, 0, 12)).addBox(-5.0f, -4.0f, -6.0f, 10, 8, 12, f);
        this.spiderBody.setRotationPoint(0.0f, (float)(0 + i), 9.0f);
        this.spiderBody.setTextureSize(this.textureWidth, this.textureHeight);
        this.spiderLeg = new ModelRenderer[8];
        (this.spiderLeg[0] = new ModelRenderer((ModelBase)this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, f);
        this.spiderLeg[0].setRotationPoint(-4.0f, (float)(0 + i), 2.0f);
        (this.spiderLeg[1] = new ModelRenderer((ModelBase)this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, f);
        this.spiderLeg[1].setRotationPoint(4.0f, (float)(0 + i), 2.0f);
        (this.spiderLeg[2] = new ModelRenderer((ModelBase)this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, f);
        this.spiderLeg[2].setRotationPoint(-4.0f, (float)(0 + i), 1.0f);
        (this.spiderLeg[3] = new ModelRenderer((ModelBase)this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, f);
        this.spiderLeg[3].setRotationPoint(4.0f, (float)(0 + i), 1.0f);
        (this.spiderLeg[4] = new ModelRenderer((ModelBase)this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, f);
        this.spiderLeg[4].setRotationPoint(-4.0f, (float)(0 + i), 0.0f);
        (this.spiderLeg[5] = new ModelRenderer((ModelBase)this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, f);
        this.spiderLeg[5].setRotationPoint(4.0f, (float)(0 + i), 0.0f);
        (this.spiderLeg[6] = new ModelRenderer((ModelBase)this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, f);
        this.spiderLeg[6].setRotationPoint(-4.0f, (float)(0 + i), -1.0f);
        (this.spiderLeg[7] = new ModelRenderer((ModelBase)this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, f);
        this.spiderLeg[7].setRotationPoint(4.0f, (float)(0 + i), -1.0f);
        for (int h = 0; h < this.spiderLeg.length; ++h) {
            this.spiderLeg[h].setTextureSize(this.textureWidth, this.textureHeight);
        }
        this.spiderLegPart = new ModelRenderer[8];
        for (int h = 0; h < this.spiderLegPart.length; ++h) {
            (this.spiderLegPart[h] = new ModelRenderer((ModelBase)this, 18, 32)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, f);
            this.spiderLegPart[h].setRotationPoint((h % 2 == 0) ? -15.0f : 15.0f, 0.0f, 0.0f);
            this.setRotation(this.spiderLegPart[h], 0.0f, 0.0f, 1.6f);
            this.spiderLegPart[h].setTextureSize(this.textureWidth, this.textureHeight);
        }
        this.spiderLeg[0].addChild(this.spiderLegPart[0]);
        this.spiderLeg[1].addChild(this.spiderLegPart[1]);
        this.spiderLeg[2].addChild(this.spiderLegPart[2]);
        this.spiderLeg[3].addChild(this.spiderLegPart[3]);
        this.spiderLeg[4].addChild(this.spiderLegPart[4]);
        this.spiderLeg[5].addChild(this.spiderLegPart[5]);
        this.spiderLeg[6].addChild(this.spiderLegPart[6]);
        this.spiderLeg[7].addChild(this.spiderLegPart[7]);
        (this.spiderMouthLeft = new ModelRenderer((ModelBase)this, 46, 20)).addBox(0.0f, -1.0f, -3.0f, 2, 2, 4, f);
        this.spiderMouthLeft.setRotationPoint(-3.0f, 3.0f, -8.0f);
        this.spiderMouthLeft.setTextureSize(this.textureWidth, this.textureHeight);
        this.spiderHead.addChild(this.spiderMouthLeft);
        (this.spiderMouthRight = new ModelRenderer((ModelBase)this, 46, 20)).addBox(0.0f, -1.0f, -3.0f, 2, 2, 4, f);
        this.spiderMouthRight.setRotationPoint(1.0f, 3.0f, -8.0f);
        this.spiderMouthRight.setTextureSize(this.textureWidth, this.textureHeight);
        this.spiderHead.addChild(this.spiderMouthRight);
        (this.bipedRightArm = new ModelRenderer((ModelBase)this, 0, 32)).addBox(-1.0f, -1.0f, -4.0f, 2, 16, 2);
        this.bipedRightArm.setRotationPoint(-4.0f, 16.0f, -4.0f);
        this.bipedRightArm.setTextureSize(this.textureWidth, this.textureHeight);
        (this.rightarm1 = new ModelRenderer((ModelBase)this, 8, 36)).addBox(-0.5f, 0.0f, -0.5f, 3, 16, 3);
        this.rightarm1.setRotationPoint(0.0f, 16.0f, -2.0f);
        this.rightarm1.setTextureSize(this.textureWidth, this.textureHeight);
        this.bipedRightArm.addChild(this.rightarm1);
        (this.bipedLeftArm = new ModelRenderer((ModelBase)this, 0, 32)).addBox(-1.0f, -1.0f, -4.0f, 2, 16, 2);
        this.bipedLeftArm.setRotationPoint(4.0f, 16.0f, -4.0f);
        this.bipedLeftArm.setTextureSize(this.textureWidth, this.textureHeight);
        this.bipedLeftArm.mirror = true;
        (this.leftarm1 = new ModelRenderer((ModelBase)this, 8, 36)).addBox(-0.5f, 0.0f, -0.5f, 3, 16, 3);
        this.leftarm1.setRotationPoint(-1.0f, 16.0f, -2.0f);
        this.leftarm1.setTextureSize(this.textureWidth, this.textureHeight);
        this.leftarm1.mirror = true;
        this.bipedLeftArm.addChild(this.leftarm1);
        (this.leftNeedle = new ModelRenderer((ModelBase)this, 8, 32)).addBox(0.5f, 0.0f, 0.5f, 1, 3, 1);
        this.leftNeedle.setRotationPoint(0.0f, 16.0f, 0.0f);
        this.leftNeedle.setTextureSize(this.textureWidth, this.textureHeight);
        this.leftarm1.addChild(this.leftNeedle);
        (this.rightNeedle = new ModelRenderer((ModelBase)this, 8, 32)).addBox(0.5f, 0.0f, 0.5f, 1, 3, 1);
        this.rightNeedle.setRotationPoint(0.0f, 16.0f, 0.0f);
        this.rightNeedle.setTextureSize(this.textureWidth, this.textureHeight);
        this.rightarm1.addChild(this.rightNeedle);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.textureHeight = 64;
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.leftarm1.setRotationPoint(-1.0f, 14.0f, -2.0f);
        this.spiderLeg[2].render(f5);
        this.spiderLeg[3].render(f5);
        this.spiderLeg[4].render(f5);
        this.spiderLeg[5].render(f5);
        this.spiderLeg[6].render(f5);
        this.spiderLeg[7].render(f5);
        this.spiderBody.render(f5);
        this.spiderHead.render(f5);
        this.spiderNeck.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        this.spiderHead.rotateAngleY = f3 / 57.295776f;
        this.spiderHead.rotateAngleX = f4 / 57.295776f;
        final float f6 = 2.66f;
        this.spiderLeg[2].rotateAngleZ = -f6;
        this.spiderLeg[3].rotateAngleZ = f6;
        this.spiderLeg[4].rotateAngleZ = -f6;
        this.spiderLeg[5].rotateAngleZ = f6;
        this.spiderLeg[6].rotateAngleZ = -f6;
        this.spiderLeg[7].rotateAngleZ = f6;
        final float f7 = 3.0f;
        final float f8 = 0.3926991f;
        this.spiderLeg[2].rotateAngleY = f8 * 1.0f + f7;
        this.spiderLeg[3].rotateAngleY = -f8 * 1.0f - f7;
        this.spiderLeg[4].rotateAngleY = -f8 * 1.0f + f7;
        this.spiderLeg[5].rotateAngleY = f8 * 1.0f - f7;
        this.spiderLeg[6].rotateAngleY = -f8 * 2.0f + f7;
        this.spiderLeg[7].rotateAngleY = f8 * 2.0f - f7;
        final float f9 = -(MathHelper.cos(f * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * f1;
        final float f10 = -(MathHelper.cos(f * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * f1;
        final float f11 = -(MathHelper.cos(f * 0.6662f * 2.0f + 4.712389f) * 0.4f) * f1;
        final float f12 = Math.abs(MathHelper.sin(f * 0.6662f + 0.0f) * 0.4f) * f1;
        final float f13 = Math.abs(MathHelper.sin(f * 0.6662f + 3.1415927f) * 0.4f) * f1;
        final float f14 = Math.abs(MathHelper.sin(f * 0.6662f + 1.5707964f) * 0.4f) * f1;
        final float f15 = Math.abs(MathHelper.sin(f * 0.6662f + 4.712389f) * 0.4f) * f1;
        final ModelRenderer modelRenderer = this.spiderLeg[2];
        modelRenderer.rotateAngleY += f9;
        final ModelRenderer modelRenderer2 = this.spiderLeg[3];
        modelRenderer2.rotateAngleY += -f9;
        final ModelRenderer modelRenderer3 = this.spiderLeg[4];
        modelRenderer3.rotateAngleY += f10;
        final ModelRenderer modelRenderer4 = this.spiderLeg[5];
        modelRenderer4.rotateAngleY += -f10;
        final ModelRenderer modelRenderer5 = this.spiderLeg[6];
        modelRenderer5.rotateAngleY += f11;
        final ModelRenderer modelRenderer6 = this.spiderLeg[7];
        modelRenderer6.rotateAngleY += -f11;
        final ModelRenderer modelRenderer7 = this.spiderLeg[2];
        modelRenderer7.rotateAngleZ += f13;
        final ModelRenderer modelRenderer8 = this.spiderLeg[3];
        modelRenderer8.rotateAngleZ += -f13;
        final ModelRenderer modelRenderer9 = this.spiderLeg[4];
        modelRenderer9.rotateAngleZ += f14;
        final ModelRenderer modelRenderer10 = this.spiderLeg[5];
        modelRenderer10.rotateAngleZ += -f14;
        final ModelRenderer modelRenderer11 = this.spiderLeg[6];
        modelRenderer11.rotateAngleZ += f15;
        final ModelRenderer modelRenderer12 = this.spiderLeg[7];
        modelRenderer12.rotateAngleZ += -f15;
        final float animTime = ((EntityBaseBoss)entity).swingProgress;
        this.spiderMouthLeft.rotateAngleY = animTime;
        this.spiderMouthRight.rotateAngleY = -animTime;
        final EntitySpiderBoss e = (EntitySpiderBoss)entity;
        if (e.rightHand != null) {
            this.bipedRightArm.rotateAngleX = 1.15f;
            this.bipedRightArm.rotateAngleZ = 3.141592f;
            this.bipedLeftArm.rotateAngleX = 1.15f;
            this.bipedLeftArm.rotateAngleZ = 3.141592f;
            float dist = (float)e.getDistance(e.posX + e.rightHand.posX, e.rightHand.posY - e.getScaleSize() / 2.0f + e.posY + e.rightHand.getShoulderHeight(), e.rightHand.posZ + e.posZ);
            float yDist = (float)(-(e.rightHand.getShoulderHeight() + e.rightHand.posY));
            float HA = (float)(dist / e.rightHand.getArmLength());
            float pitch = (float)(yDist / e.rightHand.getArmLength() * 3.14159);
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            bipedRightArm.rotateAngleX += 1.0f + pitch / 2.0f - MathHelper.cos(HA);
            this.rightarm1.rotateAngleX = 3.1416f - MathHelper.sin(HA) * 3.14159f + Math.max(pitch / 8.0f, 0.0f);
            float entityRot = e.rotationYaw * 3.141592f / 180.0f + 0.2f;
            float shoulderAngle = (float)this.getAngleBetweenEntities((Entity)e, e.rightHand, f5) - entityRot;
            this.bipedRightArm.rotateAngleY = -shoulderAngle;
            dist = (float)e.getDistance(e.posX + e.leftHand.posX, e.leftHand.posY - e.getScaleSize() / 2.0f + e.posY + e.leftHand.getShoulderHeight(), e.leftHand.posZ + e.posZ);
            yDist = (float)(-(e.leftHand.getShoulderHeight() + e.leftHand.posY));
            HA = (float)(dist / e.leftHand.getArmLength());
            pitch = (float)(yDist / e.leftHand.getArmLength() * 3.14159);
            final ModelRenderer bipedLeftArm = this.bipedLeftArm;
            bipedLeftArm.rotateAngleX += 1.0f + pitch / 2.0f - MathHelper.cos(HA);
            this.leftarm1.rotateAngleX = 3.1416f - MathHelper.sin(HA) * 3.14159f + Math.max(pitch / 8.0f, 0.0f);
            entityRot = e.rotationYaw * 3.141592f / 180.0f;
            shoulderAngle = (float)this.getAngleBetweenEntities((Entity)e, e.leftHand, f5) - entityRot;
            this.bipedLeftArm.rotateAngleY = -shoulderAngle;
        }
        this.bipedLeftArm.render(f5);
        this.bipedRightArm.render(f5);
    }
    
    public double getAngleBetweenEntities(final Entity entity, final AttackPunch part, final float tickTime) {
        final double d = -part.posX;
        final double d2 = -part.posZ;
        final double angle = Math.atan2(d, d2);
        return -angle;
    }
}
