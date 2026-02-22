package com.chocolate.chocolateQuest.client.model;

import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBiped;

public class ModelHuman extends ModelBiped
{
    public boolean isFemale;
    public boolean renderTinyArms;
    ModelRenderer bipedRightArmTiny;
    ModelRenderer bipedLeftArmTiny;
    
    public ModelHuman() {
        this(0.0f);
    }
    
    public ModelHuman(final float f) {
        this(f, 0.0f);
    }
    
    public ModelHuman(final float f, final boolean renderTinyArms) {
        this(f, 0.0f);
        this.renderTinyArms = renderTinyArms;
    }
    
    public ModelHuman(final float f, final float f1) {
        this.renderTinyArms = true;
        this.heldItemLeft = 0;
        this.heldItemRight = 0;
        this.aimedBow = false;
        (this.bipedCloak = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, f);
        (this.bipedEars = new ModelRenderer((ModelBase)this, 25, 0)).setRotationPoint(0.0f, 0.0f + f1, 0.0f);
        this.bipedEars.addBox(-7.0f, -7.0f, -1.0f, 3, 4, 0, f);
        (this.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f + f1, 0.0f);
        (this.bipedHeadwear = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f + f1, 0.0f);
        (this.bipedBody = new ModelRenderer((ModelBase)this, 16, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + f1, 0.0f);
        (this.bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16)).addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f + f1, 0.0f);
        this.bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + f1, 0.0f);
        (this.bipedRightLeg = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, f);
        this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f + f1, 0.0f);
        this.bipedLeftLeg = new ModelRenderer((ModelBase)this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f + f1, 0.0f);
        (this.bipedEars = new ModelRenderer((ModelBase)this, 25, 1)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedEars.addBox(-7.0f, -7.0f, -1.0f, 3, 4, 0, 0.0f);
        (this.bipedRightArmTiny = new ModelRenderer((ModelBase)this, 41, 17)).addBox(-3.0f, -2.0f, -2.0f, 3, 12, 3, f);
        this.bipedRightArmTiny.setRotationPoint(-4.0f, 2.0f + f1, 0.0f);
        this.bipedLeftArmTiny = new ModelRenderer((ModelBase)this, 41, 17);
        this.bipedLeftArmTiny.mirror = true;
        this.bipedLeftArmTiny.addBox(-1.0f, -2.0f, -2.0f, 3, 12, 3, f);
        this.bipedLeftArmTiny.setRotationPoint(5.0f, 2.0f + f1, 0.0f);
    }
    
    public ModelHuman(final float par1, final float par2, final int par3, final int par4) {
        super(par1, par2, par3, par4);
        this.renderTinyArms = true;
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        if (entity == null) {
            return;
        }
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        final EntityHumanBase human = (EntityHumanBase)entity;
        this.setHumanRotationAngles(f, f1, f2, f3, f4, f5, human);
    }
    
    public void setHumanRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final EntityHumanBase e) {
        this.isFemale = !e.isMale;
        if (e.isSitting()) {
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            bipedRightArm.rotateAngleX -= 0.62831855f;
            final ModelRenderer bipedLeftArm = this.bipedLeftArm;
            bipedLeftArm.rotateAngleX -= 0.62831855f;
            this.bipedRightLeg.rotateAngleX = -1.5707964f;
            this.bipedLeftLeg.rotateAngleX = -1.5707964f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }
        else {
            if (e.isSneaking()) {
                this.bipedBody.rotateAngleX = 0.5f;
                final ModelRenderer bipedRightLeg = this.bipedRightLeg;
                bipedRightLeg.rotateAngleX -= 0.0f;
                final ModelRenderer bipedLeftLeg = this.bipedLeftLeg;
                bipedLeftLeg.rotateAngleX -= 0.0f;
                final ModelRenderer bipedRightArm2 = this.bipedRightArm;
                bipedRightArm2.rotateAngleX += 0.4f;
                final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
                bipedLeftArm2.rotateAngleX += 0.4f;
                this.bipedRightLeg.rotationPointZ = 4.0f;
                this.bipedLeftLeg.rotationPointZ = 4.0f;
                this.bipedRightLeg.rotationPointY = 9.0f;
                this.bipedLeftLeg.rotationPointY = 9.0f;
                this.bipedHead.rotationPointY = 1.0f;
            }
            if (e.isTwoHanded()) {
                this.setTwoHandedAngles(f2);
            }
            if (e.isAiming()) {
                final float rotX = this.bipedRightArm.rotateAngleX;
                final float rotY = this.bipedRightArm.rotateAngleY;
                if (e.rightHand.isTwoHanded()) {
                    if (e.rightHand.isAiming()) {
                        this.setAimingAngles(f2);
                    }
                }
                else {
                    if (e.rightHand.isAiming()) {
                        this.setAimingAnglesRight(f2);
                    }
                    if (e.leftHand.isAiming()) {
                        this.setAimingAnglesLeft(f2);
                    }
                    else if (!e.rightHand.isAiming()) {
                        this.setAimingAngles(f2);
                    }
                }
                if (e.rightHand.isAiming() && this.swingProgress > 0.0f) {
                    final ModelRenderer bipedRightArm3 = this.bipedRightArm;
                    bipedRightArm3.rotateAngleY += rotY;
                    final ModelRenderer bipedRightArm4 = this.bipedRightArm;
                    bipedRightArm4.rotateAngleX += rotX + 0.3f;
                }
            }
            if (e.leftHandSwing > 0) {
                final int attackAnim = e.leftHandSwing;
                final int maxAttackAnimTime = 10;
                if (e.haveShied()) {
                    this.setShiedRotation(f2);
                    final float animProgress = (attackAnim + (attackAnim - 1) * f5) / maxAttackAnimTime * 4.82f;
                    final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
                    bipedLeftArm3.rotateAngleY += MathHelper.cos(animProgress) * 1.8f - 0.8f;
                    final ModelRenderer bipedLeftArm4 = this.bipedLeftArm;
                    bipedLeftArm4.rotateAngleX -= MathHelper.cos(animProgress) * 0.6f;
                }
                else {
                    final float animProgress = (float)((attackAnim + (attackAnim - 1) * f5) / maxAttackAnimTime * 3.141592653589793);
                    final ModelRenderer bipedLeftArm5 = this.bipedLeftArm;
                    bipedLeftArm5.rotateAngleY += MathHelper.cos(animProgress) * 0.5f;
                    final ModelRenderer bipedLeftArm6 = this.bipedLeftArm;
                    bipedLeftArm6.rotateAngleX -= MathHelper.sin(animProgress) * 1.2f;
                }
            }
            else if (e.isDefending()) {
                this.setShiedRotation(f2);
            }
        }
    }
    
    public void render(final Entity z, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, z);
        this.bipedHead.render(f5);
        this.bipedBody.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedLeftLeg.render(f5);
        this.bipedHeadwear.render(f5);
        this.renderArms(f5);
    }
    
    public void renderArms(final float f) {
        if (this.isFemale && this.useTinyArms() && this.renderTinyArms) {
            this.bipedRightArmTiny.rotateAngleX = this.bipedRightArm.rotateAngleX;
            this.bipedRightArmTiny.rotateAngleY = this.bipedRightArm.rotateAngleY;
            this.bipedRightArmTiny.rotateAngleZ = this.bipedRightArm.rotateAngleZ;
            this.bipedLeftArmTiny.rotateAngleX = this.bipedLeftArm.rotateAngleX;
            this.bipedLeftArmTiny.rotateAngleY = this.bipedLeftArm.rotateAngleY;
            this.bipedLeftArmTiny.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
            this.bipedRightArmTiny.render(f);
            this.bipedLeftArmTiny.render(f);
        }
        else {
            this.bipedRightArm.render(f);
            this.bipedLeftArm.render(f);
        }
    }
    
    public void renderEars(final float f) {
        this.bipedEars.rotationPointX = 0.0f;
        this.bipedEars.rotationPointZ = this.bipedHead.rotationPointZ + 1.0f;
        this.bipedEars.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedEars.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedEars.render(f);
        this.bipedEars.rotateAngleX = -this.bipedHead.rotateAngleX;
        this.bipedEars.rotateAngleY = (float)(this.bipedHead.rotateAngleY - 3.141592653589793);
        this.bipedEars.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedEars.rotationPointZ = this.bipedHead.rotationPointZ - 1.0f;
        this.bipedEars.render(f);
    }
    
    public void renderCloak(final float f) {
        this.bipedCloak.render(f);
    }
    
    public void setTwoHandedAngles(final float time) {
        float f7 = 0.0f;
        final float rotationYaw = 0.0f;
        final float swing = MathHelper.sin(this.swingProgress * 3.1415927f);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = -0.3f;
        this.bipedRightArm.rotateAngleY = rotationYaw - 0.6f;
        this.bipedLeftArm.rotateAngleY = rotationYaw + 0.6f;
        this.bipedRightArm.rotateAngleX = -0.8f + swing;
        this.bipedLeftArm.rotateAngleX = -0.8f + swing;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleZ += MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleX += MathHelper.sin(time * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
        float f8 = 1.0f - this.swingProgress;
        f8 *= f8;
        f8 *= f8;
        f8 = 1.0f - f8;
        f7 = MathHelper.sin(f8 * 3.1415927f);
        final float f9 = MathHelper.sin(this.swingProgress * 3.1415927f) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
        this.bipedRightArm.rotateAngleX -= (float)(f7 * 1.2 + f9);
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
        this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
        this.bipedLeftArm.rotateAngleX = (float)(this.bipedRightArm.rotateAngleX - (f7 * 1.2 + f9));
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
        this.bipedLeftArm.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
    }
    
    public void setAimingAngles(final float time) {
        final float f7 = 0.0f;
        final float f8 = 0.0f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleY = -(0.1f - f7 * 0.6f) + this.bipedHead.rotateAngleY;
        this.bipedLeftArm.rotateAngleY = 0.1f - f7 * 0.6f + this.bipedHead.rotateAngleY + 0.4f;
        this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleZ += MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleZ -= MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleX += MathHelper.sin(time * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
    }
    
    public void setAimingAnglesRight(final float time) {
        final float f7 = 0.0f;
        final float f8 = 0.0f;
        this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
        this.bipedRightArm.rotateAngleY = -0.060000002f + this.bipedHead.rotateAngleY;
        this.bipedRightArm.rotateAngleZ = MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
    }
    
    public void setAimingAnglesLeft(final float time) {
        final float f7 = 0.0f;
        final float f8 = 0.0f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
        this.bipedLeftArm.rotateAngleY = 0.1f - f7 * 0.6f + this.bipedHead.rotateAngleY;
        this.bipedLeftArm.rotateAngleZ = -MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
    }
    
    public void setShiedRotation(final float time) {
        final float f7 = 0.0f;
        final float f8 = 0.0f;
        this.bipedLeftArm.rotateAngleZ = -0.7f;
        this.bipedLeftArm.rotateAngleY = 1.2f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleZ -= MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
    }
    
    public boolean useTinyArms() {
        return true;
    }
}
