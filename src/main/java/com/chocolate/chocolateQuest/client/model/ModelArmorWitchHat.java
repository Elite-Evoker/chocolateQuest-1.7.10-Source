package com.chocolate.chocolateQuest.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelArmorWitchHat extends ModelArmor
{
    public boolean field_82900_g;
    
    public ModelArmorWitchHat(final float par1) {
        super(0);
        final float x = -5.0f;
        final float y = -9.0f;
        final float z = -5.0f;
        this.bipedHead = new ModelRenderer((ModelBase)this).setTextureSize(64, 128);
        this.bipedHead.setTextureOffset(0, 64).addBox(x, y, z, 10, 2, 10);
        final ModelRenderer modelrenderer = new ModelRenderer((ModelBase)this).setTextureSize(64, 128);
        modelrenderer.setRotationPoint(1.75f, -3.8f, 2.0f);
        modelrenderer.setTextureOffset(0, 76).addBox(x, y, z, 7, 4, 7);
        modelrenderer.rotateAngleX = -0.05235988f;
        modelrenderer.rotateAngleZ = 0.02617994f;
        this.bipedHead.addChild(modelrenderer);
        final ModelRenderer modelrenderer2 = new ModelRenderer((ModelBase)this).setTextureSize(64, 128);
        modelrenderer2.setRotationPoint(1.75f, -3.4f, 2.0f);
        modelrenderer2.setTextureOffset(0, 87).addBox(x, y, z, 4, 4, 4);
        modelrenderer2.rotateAngleX = -0.10471976f;
        modelrenderer2.rotateAngleZ = 0.05235988f;
        modelrenderer.addChild(modelrenderer2);
        final ModelRenderer modelrenderer3 = new ModelRenderer((ModelBase)this).setTextureSize(64, 128);
        modelrenderer3.setRotationPoint(1.0f, -1.0f, 0.5f);
        modelrenderer3.setTextureOffset(0, 95).addBox(x, y, z, 1, 2, 1, 0.25f);
        modelrenderer3.rotateAngleX = -0.20943952f;
        modelrenderer3.rotateAngleZ = 0.10471976f;
        modelrenderer2.addChild(modelrenderer3);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.bipedHead.render(par7);
    }
}
