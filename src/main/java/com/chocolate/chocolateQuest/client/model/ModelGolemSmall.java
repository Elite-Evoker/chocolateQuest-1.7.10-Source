package com.chocolate.chocolateQuest.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelGolemSmall extends ModelHuman
{
    ModelRenderer mouth;
    public ModelRenderer bipedBody2;
    
    public ModelGolemSmall() {
        this(0.0f);
    }
    
    public ModelGolemSmall(final float f) {
        super(f);
        (this.bipedBody = new ModelRenderer((ModelBase)this, 16, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 6, 4, f);
        this.bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedBody2 = new ModelRenderer((ModelBase)this, 16, 22)).addBox(-3.0f, 6.0f, -2.0f, 6, 6, 4, f);
        this.bipedBody2.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedBody.addChild(this.bipedBody2);
        (this.mouth = new ModelRenderer((ModelBase)this, 25, 0)).addBox(-1.0f, -3.0f, -6.0f, 2, 4, 2);
        this.bipedHead.addChild(this.mouth);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.bipedRightLeg.setRotationPoint(-3.0f, 0.0f, 0.0f);
        this.bipedLeftLeg.setRotationPoint(3.0f, 0.0f, 0.0f);
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
    }
    
    @Override
    public boolean useTinyArms() {
        return true;
    }
}
