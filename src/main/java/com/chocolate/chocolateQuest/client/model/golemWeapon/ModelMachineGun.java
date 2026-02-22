package com.chocolate.chocolateQuest.client.model.golemWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelMachineGun extends ModelBase
{
    public ModelRenderer bipedGun;
    public ModelRenderer rifle;
    public ModelRenderer cannon0;
    public ModelRenderer cannon1;
    public ModelRenderer cannon2;
    public ModelRenderer cannon3;
    
    public ModelMachineGun() {
        float f = 0.0f;
        (this.bipedGun = new ModelRenderer((ModelBase)this, 0, 10)).addBox(0.0f, 0.0f, 0.0f, 4, 4, 4, f);
        (this.rifle = new ModelRenderer((ModelBase)this, 11, 25)).addBox(-1.5f, -1.5f, 9.4f, 3, 3, 1, f);
        this.rifle.setRotationPoint(2.0f, 2.0f, 0.0f);
        f = -0.1f;
        (this.cannon0 = new ModelRenderer((ModelBase)this, 9, 11)).addBox(-1.7f, -1.7f, 3.8f, 1, 1, 7, f);
        this.cannon0.setRotationPoint(2.0f, 2.0f, 0.0f);
        (this.cannon1 = new ModelRenderer((ModelBase)this, 9, 11)).addBox(-1.7f, 0.7f, 3.8f, 1, 1, 7, f);
        this.cannon1.setRotationPoint(2.0f, 2.0f, 0.0f);
        (this.cannon2 = new ModelRenderer((ModelBase)this, 9, 11)).addBox(0.7f, -1.7f, 3.8f, 1, 1, 7, f);
        this.cannon2.setRotationPoint(2.0f, 2.0f, 0.0f);
        (this.cannon3 = new ModelRenderer((ModelBase)this, 9, 11)).addBox(0.7f, 0.7f, 3.8f, 1, 1, 7, f);
        this.cannon3.setRotationPoint(2.0f, 2.0f, 0.0f);
        final float rotFactor = 0.04f;
        this.cannon0.rotateAngleX = -rotFactor;
        this.cannon0.rotateAngleY = rotFactor;
        this.cannon1.rotateAngleX = rotFactor;
        this.cannon1.rotateAngleY = rotFactor;
        this.cannon2.rotateAngleX = -rotFactor;
        this.cannon2.rotateAngleY = -rotFactor;
        this.cannon3.rotateAngleX = rotFactor;
        this.cannon3.rotateAngleY = -rotFactor;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.bipedGun.render(f5);
        this.rifle.render(f5);
        this.cannon0.render(f5);
        this.cannon1.render(f5);
        this.cannon2.render(f5);
        this.cannon3.render(f5);
    }
}
