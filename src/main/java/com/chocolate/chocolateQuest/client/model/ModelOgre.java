package com.chocolate.chocolateQuest.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelOgre extends ModelHuman
{
    public ModelRenderer bipedBody2;
    
    public ModelOgre() {
        this(0.0f);
    }
    
    public ModelOgre(final float f, final boolean useTinyArms) {
        this(f);
        this.renderTinyArms = useTinyArms;
    }
    
    public ModelOgre(final float f) {
        super(f);
        (this.bipedBody2 = new ModelRenderer((ModelBase)this, 16, 22)).addBox(-4.0f, 0.0f, -2.0f, 8, 6, 4, 0.0f);
        this.bipedBody2.setRotationPoint(0.0f, 6.0f, -3.0f);
        this.bipedBody.addChild(this.bipedBody2);
        (this.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -5.0f, -7.0f, 8, 8, 8, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedHeadwear = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-4.0f, -5.0f, -7.0f, 8, 8, 8, f + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.bipedRightLeg.setRotationPoint(-2.5f, 0.0f, 0.0f);
        this.bipedLeftLeg.setRotationPoint(2.5f, 0.0f, 0.0f);
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
    }
}
