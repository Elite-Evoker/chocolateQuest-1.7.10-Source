package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.AttackKickQuadruped;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelQuadruped;

public class ModelBull extends ModelQuadruped
{
    ModelRenderer horn22;
    ModelRenderer horn21;
    ModelRenderer horn2;
    ModelRenderer horn12;
    ModelRenderer horn1;
    ModelRenderer horn11;
    ModelRenderer sideLeft;
    ModelRenderer sideRight;
    ModelRenderer sideLeftBleeding;
    ModelRenderer sideRightBleeding;
    ModelRenderer mouth;
    ModelRenderer tail;
    ModelRenderer tailEnd;
    ModelRenderer tailEndM;
    
    public ModelBull() {
        super(12, 0.0f);
        this.textureWidth = 64;
        this.textureHeight = 64;
        final float ho = 3.0f;
        (this.head = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -4.0f, -8.0f, 8, 8, 6);
        this.head.setRotationPoint(0.0f, 6.0f, -6.0f);
        (this.body = new ModelRenderer((ModelBase)this, 0, 36)).addBox(-6.0f, -10.0f, -18.0f, 12, 10, 18);
        this.body.setRotationPoint(0.0f, 12.0f, 9.0f);
        this.body.setTextureSize(this.textureWidth, this.textureHeight);
        (this.leg1 = new ModelRenderer((ModelBase)this, 48, 0)).addBox(-3.0f, 0.0f + ho, -2.0f, 4, 12, 4);
        this.leg1.setRotationPoint(-3.0f, 8.0f, 7.0f);
        this.leg1.setTextureSize(this.textureWidth, this.textureHeight);
        this.leg1.mirror = true;
        (this.leg2 = new ModelRenderer((ModelBase)this, 48, 0)).addBox(-1.0f, 0.0f + ho, -2.0f, 4, 12, 4);
        this.leg2.setRotationPoint(3.0f, 8.0f, 7.0f);
        this.leg2.setTextureSize(this.textureWidth, this.textureHeight);
        this.leg2.mirror = true;
        (this.leg3 = new ModelRenderer((ModelBase)this, 48, 0)).addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4);
        this.leg3.setRotationPoint(-5.0f, 1.0f, -16.0f);
        this.leg3.setTextureSize(this.textureWidth, this.textureHeight);
        this.leg3.mirror = true;
        (this.leg4 = new ModelRenderer((ModelBase)this, 48, 0)).addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4);
        this.leg4.setRotationPoint(3.0f, 1.0f, -16.0f);
        this.leg4.setTextureSize(this.textureWidth, this.textureHeight);
        this.leg4.mirror = true;
        this.body.addChild(this.leg3);
        this.body.addChild(this.leg4);
        (this.horn1 = new ModelRenderer((ModelBase)this, 22, 0)).addBox(-4.0f, -3.0f, -5.0f, 2, 2, 2);
        this.horn1.setRotationPoint(0.0f, -1.0f, -3.0f);
        this.horn1.setTextureSize(this.textureWidth, this.textureHeight);
        this.horn1.mirror = true;
        this.setRotation(this.horn1, -0.0569039f, 0.0f, 0.0f);
        (this.horn11 = new ModelRenderer((ModelBase)this, 22, 0)).addBox(-5.0f, -4.0f, -6.0f, 2, 2, 2);
        this.horn11.setRotationPoint(0.0f, -1.0f, -3.0f);
        this.horn11.setTextureSize(this.textureWidth, this.textureHeight);
        this.horn11.mirror = true;
        this.setRotation(this.horn11, -0.0569039f, 0.0f, 0.0f);
        (this.horn12 = new ModelRenderer((ModelBase)this, 22, 0)).addBox(-5.0f, -4.0f, -9.0f, 1, 1, 3);
        this.horn12.setRotationPoint(0.0f, -1.0f, -3.0f);
        this.horn12.setTextureSize(this.textureWidth, this.textureHeight);
        this.horn12.mirror = true;
        this.setRotation(this.horn12, -0.0569039f, 0.0f, 0.0f);
        (this.horn22 = new ModelRenderer((ModelBase)this, 22, 0)).addBox(4.0f, -4.0f, -9.0f, 1, 1, 3);
        this.horn22.setRotationPoint(0.0f, -1.0f, -3.0f);
        this.horn22.setTextureSize(this.textureWidth, this.textureHeight);
        this.horn22.mirror = true;
        this.setRotation(this.horn22, -0.0569039f, 0.0f, 0.0f);
        (this.horn21 = new ModelRenderer((ModelBase)this, 22, 0)).addBox(3.0f, -4.0f, -6.0f, 2, 2, 2);
        this.horn21.setRotationPoint(0.0f, -1.0f, -3.0f);
        this.horn21.setTextureSize(this.textureWidth, this.textureHeight);
        this.horn21.mirror = true;
        this.setRotation(this.horn21, -0.0569039f, 0.0f, 0.0f);
        (this.horn2 = new ModelRenderer((ModelBase)this, 22, 0)).addBox(2.0f, -3.0f, -5.0f, 2, 2, 2);
        this.horn2.setRotationPoint(0.0f, -1.0f, -3.0f);
        this.horn2.setTextureSize(this.textureWidth, this.textureHeight);
        this.horn2.mirror = true;
        this.setRotation(this.horn2, -0.0569039f, 0.0f, 0.0f);
        this.head.addChild(this.horn1);
        this.head.addChild(this.horn11);
        this.head.addChild(this.horn12);
        this.head.addChild(this.horn2);
        this.head.addChild(this.horn21);
        this.head.addChild(this.horn22);
        this.head.setRotationPoint(0.0f, -6.0f, -16.0f);
        this.body.addChild(this.head);
        (this.sideLeft = new ModelRenderer((ModelBase)this, 30, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 9, 16);
        this.sideLeft.setRotationPoint(6.0f, -9.0f, -17.0f);
        (this.sideRight = new ModelRenderer((ModelBase)this, 30, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 9, 16);
        this.sideRight.setRotationPoint(-7.0f, -9.0f, -17.0f);
        this.body.addChild(this.sideLeft);
        this.body.addChild(this.sideRight);
        (this.sideLeftBleeding = new ModelRenderer((ModelBase)this, 12, 11)).addBox(0.0f, 0.0f, 0.0f, 1, 9, 16);
        this.sideLeftBleeding.setRotationPoint(6.0f, -9.0f, -17.0f);
        (this.sideRightBleeding = new ModelRenderer((ModelBase)this, 12, 11)).addBox(0.0f, 0.0f, 0.0f, 1, 9, 16);
        this.sideRightBleeding.setRotationPoint(-7.0f, -9.0f, -17.0f);
        this.body.addChild(this.sideLeftBleeding);
        this.body.addChild(this.sideRightBleeding);
        (this.mouth = new ModelRenderer((ModelBase)this, 30, 0)).addBox(-2.0f, 1.0f, -9.0f, 4, 3, 2);
        this.head.addChild(this.mouth);
        (this.tail = new ModelRenderer((ModelBase)this, 0, 14)).addBox(-0.5f, 0.0f, 0.0f, 1, 1, 6);
        this.tail.setRotationPoint(0.0f, -9.0f, 0.0f);
        this.body.addChild(this.tail);
        (this.tailEnd = new ModelRenderer((ModelBase)this, 46, 24)).addBox(0.0f, -1.5f, 0.0f, 0, 4, 8);
        this.tailEnd.setRotationPoint(0.0f, 0.0f, 6.0f);
        (this.tailEndM = new ModelRenderer((ModelBase)this, 46, 24)).addBox(0.5f, -2.0f, 0.0f, 0, 4, 8);
        this.tailEndM.setRotationPoint(0.0f, 0.0f, 6.0f);
        this.tailEndM.rotateAngleZ = 1.5708f;
        this.tail.addChild(this.tailEnd);
        this.tail.addChild(this.tailEndM);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.rotateAngleX = 0.0f;
        final ModelRenderer leg1 = this.leg1;
        final ModelRenderer leg2 = this.leg2;
        final ModelRenderer leg3 = this.leg3;
        final ModelRenderer leg4 = this.leg4;
        final float n = 0.0f;
        leg4.rotateAngleY = n;
        leg3.rotateAngleY = n;
        leg2.rotateAngleY = n;
        leg1.rotateAngleY = n;
        this.tail.rotateAngleX = -1.4f;
        final EntityBull e = (EntityBull)entity;
        if (e.charge) {
            final ModelRenderer head = this.head;
            head.rotateAngleX += (float)(0.3 - (float)Math.cos(e.chargeTime / (float)e.chargeTimeMax * 24.0f) * 0.4f);
            final ModelRenderer tail = this.tail;
            tail.rotateAngleX += e.chargeTime / (float)e.chargeTimeMax * 1.4f;
        }
        else {
            this.head.offsetY = 0.0f;
        }
        final AttackKickQuadruped kick = e.kickHelper;
        if (kick.kickTime > 0 || kick.kickTimeBack > 0) {
            final ModelRenderer leg5 = this.leg4;
            final ModelRenderer leg6 = this.leg3;
            final ModelRenderer leg7 = this.leg2;
            final ModelRenderer leg8 = this.leg1;
            final float n2 = 0.0f;
            leg8.rotateAngleX = n2;
            leg7.rotateAngleX = n2;
            leg6.rotateAngleX = n2;
            leg5.rotateAngleX = n2;
            final int maxAttackAnimTime = kick.kickSpeed;
            int attackAnim = maxAttackAnimTime - kick.kickTime - maxAttackAnimTime / 10;
            float animProgress = (attackAnim + 1 + attackAnim * f5) / maxAttackAnimTime * 6.283184f;
            if (kick.kickType == 1) {
                this.leg4.rotateAngleX = MathHelper.sin(animProgress) * 1.4f - 0.2f;
                this.leg4.rotateAngleY = MathHelper.cos(animProgress) * 0.4f;
            }
            else if (kick.kickType == 3) {
                this.leg3.rotateAngleX = MathHelper.sin(animProgress) * 1.4f - 0.2f;
                this.leg3.rotateAngleY = -MathHelper.cos(animProgress) * 0.4f;
            }
            attackAnim = maxAttackAnimTime - kick.kickTimeBack - maxAttackAnimTime / 10;
            animProgress = (attackAnim + 1 + attackAnim * f5) / maxAttackAnimTime * 6.283184f;
            if (kick.kickTypeBack == 2) {
                this.leg2.rotateAngleX = -MathHelper.sin(animProgress) * 1.4f + 0.2f;
                this.leg2.rotateAngleY = -MathHelper.cos(animProgress) * 0.4f;
            }
            else if (kick.kickTypeBack == 4) {
                this.leg1.rotateAngleX = -MathHelper.sin(animProgress) * 1.4f + 0.2f;
                this.leg1.rotateAngleY = MathHelper.cos(animProgress) * 0.4f;
            }
        }
        if (e.smashTime > 0) {
            e.getClass();
            final int maxAttackAnimTime = 30;
            final int attackAnim = maxAttackAnimTime - e.smashTime - maxAttackAnimTime / 10;
            final float animProgress = (attackAnim + 1 + attackAnim * f5) / maxAttackAnimTime * 6.283184f;
            final float rot = -MathHelper.sin(animProgress / 2.0f) * 0.5f;
            this.body.rotateAngleX = rot;
            this.leg4.rotateAngleX = rot;
            this.leg3.rotateAngleX = rot;
        }
        this.sideLeft.isHidden = e.isHurtLeft();
        this.sideLeftBleeding.isHidden = !e.isHurtLeft();
        this.sideRight.isHidden = e.isHurtRight();
        this.sideRightBleeding.isHidden = !e.isHurtRight();
    }
}
