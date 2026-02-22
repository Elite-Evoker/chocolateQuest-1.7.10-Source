package com.chocolate.chocolateQuest.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelArmorMinotaur extends ModelArmor
{
    ModelRenderer horn22;
    ModelRenderer horn21;
    ModelRenderer horn2;
    ModelRenderer horn12;
    ModelRenderer horn1;
    ModelRenderer horn11;
    ModelRenderer mouth;
    
    public ModelArmorMinotaur() {
        this(0.0f);
    }
    
    public ModelArmorMinotaur(final float f) {
        super(0.6f, 0);
        final float size = 0.0f;
        (this.horn1 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -9.0f, -4.0f, 2, 2, 2, size);
        this.horn1.setRotationPoint(0.0f, 0.0f, -2.0f);
        this.horn1.mirror = true;
        this.setRotation(this.horn1, -0.0569039f, 0.0f, 0.0f);
        (this.horn11 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-5.0f, -10.0f, -5.0f, 2, 2, 2, size);
        this.horn11.setRotationPoint(0.0f, 0.0f, -2.0f);
        this.horn11.setTextureSize(64, 32);
        this.horn11.mirror = true;
        this.setRotation(this.horn11, -0.0569039f, 0.0f, 0.0f);
        (this.horn12 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-5.0f, -10.0f, -8.0f, 1, 1, 3, size);
        this.horn12.setRotationPoint(0.0f, 0.0f, -2.0f);
        this.horn12.mirror = true;
        this.setRotation(this.horn12, -0.0569039f, 0.0f, 0.0f);
        (this.horn22 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(4.0f, -10.0f, -8.0f, 1, 1, 3, size);
        this.horn22.setRotationPoint(0.0f, 0.0f, -2.0f);
        this.horn22.mirror = true;
        this.setRotation(this.horn22, -0.0569039f, 0.0f, 0.0f);
        (this.horn21 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(3.0f, -10.0f, -5.0f, 2, 2, 2, size);
        this.horn21.setRotationPoint(0.0f, 0.0f, -2.0f);
        this.horn21.mirror = true;
        this.setRotation(this.horn21, -0.0569039f, 0.0f, 0.0f);
        (this.horn2 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(2.0f, -9.0f, -4.0f, 2, 2, 2, size);
        this.horn2.setRotationPoint(0.0f, 0.0f, -2.0f);
        this.horn2.mirror = true;
        this.setRotation(this.horn2, -0.0569039f, 0.0f, 0.0f);
        this.bipedHead.addChild(this.horn1);
        this.bipedHead.addChild(this.horn11);
        this.bipedHead.addChild(this.horn12);
        this.bipedHead.addChild(this.horn2);
        this.bipedHead.addChild(this.horn21);
        this.bipedHead.addChild(this.horn22);
        (this.mouth = new ModelRenderer((ModelBase)this, 25, 0)).addBox(-2.0f, -3.0f, -6.0f, 4, 3, 3, 0.5f);
        this.bipedHead.addChild(this.mouth);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.bipedHead.render(par7);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
