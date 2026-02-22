package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.AttackPunch;
import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBiped;

public class ModelGiantBoxer extends ModelBiped
{
    ModelRenderer rightarm1;
    ModelRenderer leftarm1;
    ModelRenderer earR;
    ModelRenderer earL;
    ModelRenderer mouth;
    ModelRenderer tail;
    boolean attacking;
    int attackAnim;
    int maxAttackAnimTime;
    int attackType;
    
    public ModelGiantBoxer() {
        this(0.0f);
    }
    
    public ModelGiantBoxer(final float s) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        (this.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -5.0f, -6.0f, 8, 8, 8, s);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.setTextureSize(64, 32);
        (this.bipedBody = new ModelRenderer((ModelBase)this, 16, 16)).addBox(-4.0f, 3.0f, -5.0f, 8, 12, 4, s);
        this.bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedBody.setTextureSize(64, 32);
        (this.bipedRightLeg = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-2.0f, 0.0f, -3.0f, 4, 12, 4, s);
        this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
        this.bipedRightLeg.setTextureSize(64, 32);
        (this.bipedLeftLeg = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-2.0f, 0.0f, -3.0f, 4, 12, 4, s);
        this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
        this.bipedLeftLeg.setTextureSize(64, 32);
        this.bipedLeftLeg.mirror = true;
        (this.bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16)).addBox(-3.0f, -1.0f, -4.0f, 4, 12, 4, s);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
        this.bipedRightArm.setTextureSize(64, 32);
        (this.rightarm1 = new ModelRenderer((ModelBase)this, 40, 16)).addBox(-2.0f, 1.0f, -4.0f, 4, 10, 4, s);
        this.rightarm1.setRotationPoint(-1.0f, 10.0f, 0.0f);
        this.rightarm1.setTextureSize(64, 32);
        this.bipedRightArm.addChild(this.rightarm1);
        (this.bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16)).addBox(-1.0f, -1.0f, -4.0f, 4, 12, 4, s);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
        this.bipedLeftArm.setTextureSize(64, 32);
        this.bipedLeftArm.mirror = true;
        (this.leftarm1 = new ModelRenderer((ModelBase)this, 40, 16)).addBox(-2.0f, 1.0f, -4.0f, 4, 10, 4);
        this.leftarm1.setRotationPoint(1.0f, 10.0f, 0.0f);
        this.leftarm1.setTextureSize(64, 32);
        this.leftarm1.mirror = true;
        this.bipedLeftArm.addChild(this.leftarm1);
        (this.earR = new ModelRenderer((ModelBase)this, 0, 0)).addBox(4.0f, -2.0f, -3.0f, 2, 2, 1);
        this.earR.mirror = true;
        (this.earL = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-6.0f, -2.0f, -3.0f, 2, 2, 1);
        (this.mouth = new ModelRenderer((ModelBase)this, 25, 0)).addBox(-2.0f, 0.0f, -8.0f, 4, 3, 3);
        this.bipedHead.addChild(this.earR);
        this.bipedHead.addChild(this.earL);
        this.bipedHead.addChild(this.mouth);
        (this.tail = new ModelRenderer((ModelBase)this, 0, 20)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 1);
        this.tail.setRotationPoint(0.0f, -3.0f, 14.0f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.isSneak = true;
        super.render(entity, f, f1, f2, f3, f4, f5);
        float px = -0.5f;
        float py = 13.0f;
        float pz = 5.5f;
        for (int i = 0; i < 15; ++i) {
            final float f6 = (float)Math.cos((15.0 - i / 15.0 + f / 10.0f) * 3.141592653589793 * 2.0);
            final float AnimOnTime = (float)Math.cos((i / 20.0 + f2 / 50.0f) * 3.141592653589793 * 2.0);
            this.tail.rotateAngleX = 3.86f + f6 / 10.0f;
            this.tail.rotateAngleY = AnimOnTime;
            this.tail.rotateAngleZ = 0.0f;
            this.tail.rotationPointX = px;
            this.tail.rotationPointY = py;
            this.tail.rotationPointZ = pz;
            px -= (float)(Math.sin(this.tail.rotateAngleY) * Math.cos(this.tail.rotateAngleX) * 0.8);
            py += (float)(Math.sin(this.tail.rotateAngleX) * 0.8);
            pz -= (float)(Math.cos(this.tail.rotateAngleY) * Math.cos(this.tail.rotateAngleX) * 0.8);
            this.tail.render(f5);
        }
    }
    
    public void setLivingAnimations(final EntityLivingBase entityliving, final float f, final float f1, final float f2) {
        super.setLivingAnimations(entityliving, f, f1, f2);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedRightLeg.rotationPointY = 13.0f;
        this.bipedLeftLeg.rotationPointY = 13.0f;
        final EntityGiantBoxer e = (EntityGiantBoxer)entity;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleX = 1.570796f;
        if (e.rightHand != null) {
            double x = e.posX;
            double y = e.posY + e.rightHand.getShoulderHeight();
            double z = e.posZ;
            float dist = (float)e.getDistance(e.rightHand.posX + x, e.rightHand.posY - e.getScaleSize() + y, e.rightHand.posZ + z) / 2.0f;
            float yDist = (float)(-(e.rightHand.getShoulderHeight() + e.rightHand.posY));
            final float armLength = (float)(e.getArmLength() / 2.0);
            float armAngle = 1.570796f;
            float AH = dist / armLength;
            float entityRot = e.rotationYaw * 3.141592f / 180.0f + 0.2f;
            if (AH <= 1.0f) {
                armAngle = (float)Math.asin(AH);
            }
            float shoulderAngle = (float)this.getAngleBetweenEntities((Entity)e, e.rightHand, f5) - entityRot;
            this.bipedRightArm.rotateAngleY = shoulderAngle + (float)(1.5707963267948966 - armAngle);
            this.rightarm1.rotateAngleZ = 3.141592f - armAngle * 2.0f;
            this.bipedRightArm.rotateAngleX = -(yDist - yDist / e.getScaleSize()) / armLength;
            x = e.posX;
            y = e.posY + e.leftHand.getShoulderHeight();
            z = e.posZ;
            dist = (float)e.getDistance(e.leftHand.posX + x, e.leftHand.posY - e.getScaleSize() + y, e.leftHand.posZ + z) / 2.0f;
            AH = dist / armLength;
            entityRot = e.rotationYaw * 3.141592f / 180.0f - 0.2f;
            if (AH <= 1.0f) {
                armAngle = (float)Math.asin(AH);
            }
            yDist = (float)(-(e.leftHand.getShoulderHeight() + e.leftHand.posY));
            shoulderAngle = (float)this.getAngleBetweenEntities((Entity)e, e.leftHand, f5) - entityRot;
            this.bipedLeftArm.rotateAngleY = shoulderAngle + (float)(-1.5707963267948966 + armAngle);
            this.leftarm1.rotateAngleZ = 3.141592f + armAngle * 2.0f;
            this.bipedLeftArm.rotateAngleX = -(yDist - yDist / e.getScaleSize()) / armLength;
        }
        final AttackKick kick = e.kickHelper;
        if (kick.kickTime > 0) {
            this.bipedRightLeg.rotateAngleX = 0.0f;
            this.bipedLeftLeg.rotateAngleX = 0.0f;
            final int maxAttackAnimTime = kick.kickSpeed;
            final int attackAnim = maxAttackAnimTime - kick.kickTime - maxAttackAnimTime / 10;
            final float animProgress = (attackAnim + 1 + attackAnim * f5) / maxAttackAnimTime * 6.283184f;
            final float dir = (kick.kickType == 2 || kick.kickType == 4) ? -1.0f : 1.0f;
            if (kick.kickType == 2 || kick.kickType == 1) {
                this.bipedLeftLeg.rotateAngleX = dir * MathHelper.sin(animProgress) * 1.2f - dir * 0.2f;
                this.bipedLeftLeg.rotateAngleY = dir * MathHelper.cos(animProgress) * 0.4f;
            }
            else {
                this.bipedRightLeg.rotateAngleX = dir * MathHelper.sin(animProgress) * 1.2f - dir * 0.2f;
                this.bipedRightLeg.rotateAngleY = dir * -MathHelper.cos(animProgress) * 0.4f;
            }
        }
        if (e.airSmashInProgress) {
            final ModelRenderer bipedBody = this.bipedBody;
            bipedBody.rotateAngleX -= 0.2f;
            final ModelRenderer bipedRightLeg = this.bipedRightLeg;
            bipedRightLeg.rotationPointY += 2.0f;
            final ModelRenderer bipedLeftLeg = this.bipedLeftLeg;
            bipedLeftLeg.rotationPointY += 2.0f;
            this.bipedRightLeg.rotateAngleX = -0.9424778f;
            this.bipedLeftLeg.rotateAngleX = -0.9424778f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }
    }
    
    public double getAngleBetweenEntities(final Entity entity, final AttackPunch part, final float tickTime) {
        final double d = -part.posX;
        final double d2 = -part.posZ;
        final double angle = Math.atan2(d, d2);
        return -angle;
    }
}
