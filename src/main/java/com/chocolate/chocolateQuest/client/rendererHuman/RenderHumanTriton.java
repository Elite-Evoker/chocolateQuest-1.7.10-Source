package com.chocolate.chocolateQuest.client.rendererHuman;

import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.client.model.ModelBiped;
import com.chocolate.chocolateQuest.client.model.ModelNaga;
import net.minecraft.util.ResourceLocation;

public class RenderHumanTriton extends RenderHuman
{
    public RenderHumanTriton(final float f, final ResourceLocation r) {
        super(new ModelNaga(), f, r);
        this.featherY = -0.3f;
    }
    
    @Override
    protected void func_82421_b() {
        this.modelArmourChestplate = new ModelHuman(1.0f, false);
        this.field_82425_h = new ModelHuman(0.5f, false);
    }
    
    protected int shouldRenderPass(final EntityLivingBase par1EntityLivingBase, final int par2, final float par3) {
        return (par2 != 2 && par2 != 3) ? super.shouldRenderPass(par1EntityLivingBase, par2, par3) : 0;
    }
}
