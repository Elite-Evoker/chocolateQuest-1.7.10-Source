package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelGremlin extends ModelHuman
{
    public ModelRenderer wingl;
    public ModelRenderer wingr;
    public ModelRenderer tail;
    
    public ModelGremlin() {
        this(0.0f);
    }
    
    public ModelGremlin(final float f) {
        super(f);
        (this.bipedRightLeg = new ModelRenderer((ModelBase)this, 0, 22)).addBox(0.0f, 0.0f, -2.0f, 4, 6, 4, f);
        this.bipedRightLeg.setRotationPoint(0.0f, 0.0f, -2.0f);
        this.bipedLeftLeg = new ModelRenderer((ModelBase)this, 0, 22);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-8.0f, 0.0f, -2.0f, 4, 6, 4, f);
        this.bipedLeftLeg.setRotationPoint(2.9f, 12.0f, 6.0f);
        (this.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -5.0f, -7.0f, 8, 8, 8, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedHeadwear = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-4.0f, -5.0f, -7.0f, 8, 8, 8, f + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.wingl = new ModelRenderer((ModelBase)this, 0, 21)).addBox(0.0f, -2.0f, 0.0f, 0, 5, 12, f);
        this.wingl.setRotationPoint(-2.0f, 0.0f, 3.0f);
        (this.wingr = new ModelRenderer((ModelBase)this, 0, 21)).addBox(0.0f, -2.0f, 0.0f, 0, 5, 12, f);
        this.wingr.setRotationPoint(2.0f, 0.0f, 3.0f);
        (this.tail = new ModelRenderer((ModelBase)this, 1, 17)).addBox(-4.0f, 0.0f, -2.0f, 4, 3, 3);
        this.tail.setRotationPoint(0.0f, -3.0f, 14.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        final float rot = (float)(Math.sin((double)(System.nanoTime() / 4L)) * 0.3);
    }
    
    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        final EntityHumanBase e = (EntityHumanBase)entity;
        if (e.isSitting()) {
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            bipedRightArm.rotateAngleX += 1.1f;
            final ModelRenderer bipedLeftArm = this.bipedLeftArm;
            bipedLeftArm.rotateAngleX += 1.1f;
        }
        if (e.isTwoHanded()) {
            final ModelRenderer bipedRightArm2 = this.bipedRightArm;
            bipedRightArm2.rotateAngleX += 0.5f;
            final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
            bipedLeftArm2.rotateAngleX += 0.5f;
        }
        else if (!e.isAiming()) {
            final ModelRenderer bipedRightArm3 = this.bipedRightArm;
            bipedRightArm3.rotateAngleX -= 0.7f;
            final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
            bipedLeftArm3.rotateAngleX -= 0.7f;
        }
        if (e.isDefending()) {
            this.setShiedRotation(f2);
        }
        this.bipedRightLeg.setRotationPoint(2.0f, 7.0f, 8.0f);
        this.bipedLeftLeg.setRotationPoint(2.0f, 7.0f, 8.0f);
        this.bipedBody.rotateAngleX = 0.7f;
    }
    
    @Override
    public boolean useTinyArms() {
        return false;
    }
}
