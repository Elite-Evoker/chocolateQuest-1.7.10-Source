package com.chocolate.chocolateQuest.client.model.golemWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelBubbleCannon extends ModelBase
{
    public ModelRenderer bipedGun;
    public ModelRenderer rifle;
    
    public ModelBubbleCannon() {
        final float f = 0.0f;
        (this.bipedGun = new ModelRenderer((ModelBase)this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 4, 4, 6, f);
        (this.rifle = new ModelRenderer((ModelBase)this, 25, 10)).addBox(0.5f, 0.5f, 6.0f, 3, 3, 6, f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.bipedGun.render(f5);
        this.rifle.render(f5);
    }
}
