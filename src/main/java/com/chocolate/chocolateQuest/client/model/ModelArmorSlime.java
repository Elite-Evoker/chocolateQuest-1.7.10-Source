package com.chocolate.chocolateQuest.client.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;

public class ModelArmorSlime extends ModelArmor
{
    ModelRenderer bipedBodyPants;
    ModelRenderer bipedRightLegPants;
    ModelRenderer bipedLeftLegPants;
    
    public ModelArmorSlime(final int type) {
        this(1.0f, type);
    }
    
    public ModelArmorSlime(final float f, final int type) {
        super(f, type);
        this.type = type;
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        GL11.glDisable(3042);
    }
}
