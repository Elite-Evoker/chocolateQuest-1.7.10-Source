package com.chocolate.chocolateQuest.client.model;

import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelMonkey extends ModelHuman
{
    ModelRenderer mouth;
    ModelRenderer tail;
    
    public ModelMonkey() {
        (this.mouth = new ModelRenderer((ModelBase)this, 25, 0)).addBox(-2.0f, -3.0f, -6.0f, 4, 3, 3);
        this.bipedHead.addChild(this.mouth);
        (this.tail = new ModelRenderer((ModelBase)this, 0, 20)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 1);
        this.tail.setRotationPoint(0.0f, -3.0f, 14.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        float px = -0.5f;
        float py = 8.0f;
        float pz = 8.0f;
        final float dist = 1.1f;
        if (!par1Entity.isSneaking()) {
            pz = 2.0f;
            py = 11.0f;
        }
        for (int i = 0; i < 8; ++i) {
            final float f10 = (float)Math.cos((15.0 - i / 15.0 + par2 / 10.0f) * 3.141592653589793 * 2.0);
            final float AnimOnTime = (float)Math.cos((i / 20.0 + par4 / 50.0f) * 3.141592653589793 * 2.0);
            this.tail.rotateAngleX = 3.56f + f10 / 10.0f;
            this.tail.rotateAngleY = AnimOnTime;
            this.tail.rotateAngleZ = 0.0f;
            this.tail.rotationPointX = px;
            this.tail.rotationPointY = py;
            this.tail.rotationPointZ = pz;
            px -= (float)(Math.sin(this.tail.rotateAngleY) * Math.cos(this.tail.rotateAngleX) * dist);
            py += (float)(Math.sin(this.tail.rotateAngleX) * 0.8);
            pz -= (float)(Math.cos(this.tail.rotateAngleY) * Math.cos(this.tail.rotateAngleX) * dist);
            this.tail.render(par7);
        }
    }
    
    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
    
    @Override
    public void setAimingAngles(final float time) {
        super.setAimingAngles(time);
    }
    
    @Override
    public void setAimingAnglesLeft(final float time) {
        super.setAimingAnglesLeft(time);
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX += 0.7f;
    }
    
    @Override
    public void setHumanRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final EntityHumanBase e) {
        super.setHumanRotationAngles(f, f1, f2, f3, f4, f5, e);
        if (e.isSneaking()) {
            if (e.isTwoHanded()) {
                final ModelRenderer bipedRightArm = this.bipedRightArm;
                bipedRightArm.rotateAngleX += 0.5f;
                final ModelRenderer bipedLeftArm = this.bipedLeftArm;
                bipedLeftArm.rotateAngleX += 0.5f;
            }
            else {
                final ModelRenderer bipedRightArm2 = this.bipedRightArm;
                bipedRightArm2.rotateAngleX -= 0.7f;
                final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
                bipedLeftArm2.rotateAngleX -= 0.7f;
            }
        }
    }
    
    @Override
    public void setShiedRotation(final float time) {
        final float f7 = 0.0f;
        final float f8 = 2.0f;
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
}
