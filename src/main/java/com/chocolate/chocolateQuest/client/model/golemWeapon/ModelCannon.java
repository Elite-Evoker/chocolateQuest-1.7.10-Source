package com.chocolate.chocolateQuest.client.model.golemWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelCannon extends ModelBase
{
    public ModelRenderer bipedGun;
    public ModelRenderer cannon;
    public ModelRenderer cannonMouth;
    
    public ModelCannon() {
        final float f = 0.0f;
        (this.bipedGun = new ModelRenderer((ModelBase)this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 4, 4, 6, f);
        (this.cannon = new ModelRenderer((ModelBase)this, 20, 0)).addBox(0.0f, 0.0f, 6.0f, 4, 4, 6, f);
        (this.cannonMouth = new ModelRenderer((ModelBase)this, 16, 25)).addBox(1.0f, 1.0f, 7.0f, 2, 2, 5, f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.bipedGun.render(f5);
        this.cannon.render(f5);
        this.cannonMouth.render(f5);
    }
}
