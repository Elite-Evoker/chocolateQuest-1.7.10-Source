package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelQuadruped;

public class ModelSlimeBoss extends ModelQuadruped
{
    ModelRenderer foot2;
    ModelRenderer foot1;
    ModelRenderer headBT;
    ModelRenderer core;
    ModelRenderer eyer;
    ModelRenderer eyel;
    ModelSlimeBoss goomy;
    boolean isMain;
    
    public ModelSlimeBoss() {
        this(0.0f, 0, true);
    }
    
    public ModelSlimeBoss(final float s, final int to, final boolean isMain) {
        super(12, s);
        this.isMain = isMain;
        this.textureWidth = 64;
        this.textureHeight = 64;
        (this.body = new ModelRenderer((ModelBase)this, 0, to)).addBox(-5.0f, -6.0f, -2.0f, 10, 6, 12, s);
        this.body.setRotationPoint(0.0f, 19.0f, -4.0f);
        this.body.setTextureSize(this.textureWidth, this.textureHeight);
        (this.leg1 = new ModelRenderer((ModelBase)this, 0, to)).addBox(0.0f, -1.0f, -1.0f, 3, 9, 3, s);
        this.leg1.setRotationPoint(5.0f, -3.0f, 8.0f);
        this.leg1.setTextureSize(this.textureWidth, this.textureHeight);
        (this.foot1 = new ModelRenderer((ModelBase)this, 56, to)).addBox(-1.0f, -10.0f, -2.0f, 2, 10, 2, s);
        this.foot1.setRotationPoint(1.0f, 8.0f, 0.0f);
        this.foot1.setTextureSize(this.textureWidth, this.textureHeight);
        this.body.addChild(this.leg1);
        this.leg1.addChild(this.foot1);
        this.setRotation(this.leg1, -1.047198f, 0.0f, 0.0f);
        this.setRotation(this.foot1, -1.047198f, 0.0f, 0.0f);
        (this.leg2 = new ModelRenderer((ModelBase)this, 0, to)).addBox(-0.0f, -1.0f, -1.0f, 3, 9, 3, s);
        this.leg2.setRotationPoint(-8.0f, -3.0f, 8.0f);
        this.leg2.setTextureSize(this.textureWidth, this.textureHeight);
        (this.foot2 = new ModelRenderer((ModelBase)this, 56, to)).addBox(-1.0f, -10.0f, -1.0f, 2, 10, 2, s);
        this.foot2.setRotationPoint(1.0f, 8.0f, 0.0f);
        this.foot2.setTextureSize(this.textureWidth, this.textureHeight);
        this.body.addChild(this.leg2);
        this.leg2.addChild(this.foot2);
        this.setRotation(this.leg2, -1.047198f, 0.0f, 0.0f);
        this.setRotation(this.foot2, -1.047198f, 0.0f, 0.0f);
        (this.leg4 = new ModelRenderer((ModelBase)this, 56, to + 12)).addBox(-1.0f, 0.0f, -1.0f, 2, 5, 2, s);
        this.leg4.setRotationPoint(4.0f, 0.0f, -1.0f);
        this.leg4.setTextureSize(this.textureWidth, this.textureHeight);
        (this.leg3 = new ModelRenderer((ModelBase)this, 56, to + 12)).addBox(-1.0f, 0.0f, -1.0f, 2, 5, 2, s);
        this.leg3.setRotationPoint(-4.0f, 0.0f, -1.0f);
        this.leg3.setTextureSize(this.textureWidth, this.textureHeight);
        this.body.addChild(this.leg4);
        this.body.addChild(this.leg3);
        (this.head = new ModelRenderer((ModelBase)this, 0, to + 18)).addBox(-4.0f, -4.0f, -6.0f, 8, 6, 8, s);
        this.head.setRotationPoint(0.0f, -6.0f, -1.0f);
        this.head.setTextureSize(this.textureWidth, this.textureHeight);
        this.body.addChild(this.head);
        (this.headBT = new ModelRenderer((ModelBase)this, 32, to + 20)).addBox(-4.0f, 2.0f, -10.0f, 8, 4, 8, s);
        this.headBT.setRotationPoint(0.0f, -2.0f, 4.0f);
        this.headBT.setTextureSize(this.textureWidth, this.textureHeight);
        this.head.addChild(this.headBT);
        if (isMain) {
            (this.core = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-3.0f, -1.0f, -2.0f, 6, 6, 6);
            this.core.setRotationPoint(0.0f, -1.5f, -3.0f);
            this.core.setTextureSize(this.textureWidth, this.textureHeight);
            this.setRotation(this.core, 0.4f, 0.0f, 0.0f);
            this.head.addChild(this.core);
            (this.eyer = new ModelRenderer((ModelBase)this, 0, 18)).addBox(0.0f, -1.0f, -1.0f, 2, 2, 2);
            this.eyer.setRotationPoint(3.0f, -1.0f, -1.0f);
            this.eyer.setTextureSize(this.textureWidth, this.textureHeight);
            (this.eyel = new ModelRenderer((ModelBase)this, 0, 18)).addBox(-2.0f, -1.0f, -1.0f, 2, 2, 2);
            this.eyel.setRotationPoint(-3.0f, -1.0f, -1.0f);
            this.eyel.setTextureSize(this.textureWidth, this.textureHeight);
            this.head.addChild(this.eyer);
            this.head.addChild(this.eyel);
            this.goomy = new ModelSlimeBoss(1.2f, 32, false);
        }
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.goomy.copyRotations(this);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.goomy.body.render(f5);
        GL11.glDisable(3042);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    public void copyRotations(final ModelSlimeBoss model) {
        this.setRotation(this.body, model.body.rotateAngleX, model.body.rotateAngleY, model.body.rotateAngleZ);
        this.setRotation(this.leg1, model.leg1.rotateAngleX, model.leg1.rotateAngleY, model.leg1.rotateAngleZ);
        this.setRotation(this.leg2, model.leg2.rotateAngleX, model.leg2.rotateAngleY, model.leg2.rotateAngleZ);
        this.setRotation(this.leg3, model.leg3.rotateAngleX, model.leg3.rotateAngleY, model.leg3.rotateAngleZ);
        this.setRotation(this.leg4, model.leg4.rotateAngleX, model.leg4.rotateAngleY, model.leg4.rotateAngleZ);
        this.setRotation(this.foot1, model.foot1.rotateAngleX, model.foot1.rotateAngleY, model.foot1.rotateAngleZ);
        this.setRotation(this.foot2, model.foot2.rotateAngleX, model.foot2.rotateAngleY, model.foot2.rotateAngleZ);
        this.setRotation(this.head, model.head.rotateAngleX, model.head.rotateAngleY, model.head.rotateAngleZ);
        this.setRotation(this.headBT, model.headBT.rotateAngleX, model.headBT.rotateAngleY, model.headBT.rotateAngleZ);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.setRotation(this.body, 0.1919862f, 0.0f, 0.0f);
        final float ankleRot = -1.047198f;
        final float ankleRotY = 0.7f;
        final ModelRenderer leg2 = this.leg2;
        leg2.rotateAngleX += ankleRot;
        final ModelRenderer leg3 = this.leg1;
        leg3.rotateAngleX += ankleRot;
        this.leg1.rotateAngleY = -ankleRotY;
        this.leg2.rotateAngleY = ankleRotY;
        this.headBT.rotateAngleX = 0.0f;
        this.setRotation(this.foot2, -1.047198f, 0.0f, 0.0f);
        this.setRotation(this.foot1, -1.047198f, 0.0f, 0.0f);
        final EntitySlimeBoss e = (EntitySlimeBoss)entity;
        final AttackKick kick = e.kickHelper;
        if (kick.kickTime > 0) {
            final ModelRenderer leg4 = this.leg4;
            final ModelRenderer leg5 = this.leg3;
            final float n = 0.0f;
            leg5.rotateAngleX = n;
            leg4.rotateAngleX = n;
            final int maxAttackAnimTime = kick.kickSpeed;
            final int attackAnim = maxAttackAnimTime - kick.kickTime - maxAttackAnimTime / 10;
            final float animProgress = (attackAnim + 1 + attackAnim * f5) / maxAttackAnimTime * 6.283184f;
            if (kick.kickType == 1) {
                this.leg1.rotateAngleX = MathHelper.sin(animProgress) * 1.4f - 0.2f;
                final ModelRenderer leg6 = this.leg1;
                leg6.rotateAngleY += MathHelper.cos(animProgress) * 0.4f;
                this.foot1.rotateAngleX = -MathHelper.sin(animProgress / 2.0f) * 3.0f - 1.0f;
            }
            else if (kick.kickType == 3) {
                this.leg2.rotateAngleX = MathHelper.sin(animProgress) * 1.4f - 0.2f;
                final ModelRenderer leg7 = this.leg2;
                leg7.rotateAngleY -= MathHelper.cos(animProgress) * 0.4f;
                this.foot2.rotateAngleX = -MathHelper.sin(animProgress / 2.0f) * 3.0f - 1.0f;
            }
            else if (kick.kickType == 2) {
                final ModelRenderer leg8 = this.leg2;
                leg8.rotateAngleX += -MathHelper.sin(animProgress) * 1.4f + 0.2f;
                final ModelRenderer leg9 = this.leg2;
                leg9.rotateAngleY += -MathHelper.cos(animProgress) * 0.4f;
            }
            else if (kick.kickType == 4) {
                final ModelRenderer leg10 = this.leg1;
                leg10.rotateAngleX += -MathHelper.sin(animProgress) * 1.4f + 0.2f;
                final ModelRenderer leg11 = this.leg1;
                leg11.rotateAngleY += MathHelper.cos(animProgress) * 0.4f;
            }
        }
        if (e.isAttacking()) {
            this.head.rotateAngleX = -0.8f;
            this.headBT.rotateAngleX = 0.8f;
        }
        if (this.swingProgress > 0.0f) {
            this.head.rotateAngleX = MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
            this.headBT.rotateAngleX = MathHelper.sin(this.swingProgress * 3.1415927f) * 0.4f;
        }
        if (e.slimePoolAttackTime > 0) {
            final int slimePoolAttackTimeMax = e.slimePoolAttackTimeMax;
            final float i = (float)(e.slimePoolAttackTime - slimePoolAttackTimeMax + e.slimePoolChargeTime);
            this.head.rotateAngleX = (1.0f - Math.max(0.0f, i / slimePoolAttackTimeMax)) * 2.0f;
        }
    }
}
