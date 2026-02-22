package com.chocolate.chocolateQuest.client.model.golemWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelFlameThrower extends ModelBase
{
    public ModelRenderer bipedGun;
    public ModelRenderer rifle;
    public ModelRenderer mouth;
    public ModelRenderer spark;
    
    public ModelFlameThrower() {
        final float f = 0.0f;
        (this.bipedGun = new ModelRenderer((ModelBase)this, 0, 10)).addBox(0.0f, 0.0f, 0.0f, 4, 4, 4, f);
        (this.rifle = new ModelRenderer((ModelBase)this, 0, 18)).addBox(0.5f, 0.2f, 4.0f, 3, 3, 3, f);
        (this.mouth = new ModelRenderer((ModelBase)this, 0, 0)).addBox(1.5f, 1.2f, 7.0f, 1, 1, 2, f);
        (this.spark = new ModelRenderer((ModelBase)this, 14, 0)).addBox(1.5f, 0.0f, 0.0f, 1, 1, 3, -0.38f);
        this.spark.setRotationPoint(0.0f, 0.0f, 6.9f);
        this.spark.rotateAngleX = -0.2618f;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.bipedGun.render(f5);
        this.rifle.render(f5);
        this.mouth.render(f5);
        this.spark.render(f5);
    }
}
