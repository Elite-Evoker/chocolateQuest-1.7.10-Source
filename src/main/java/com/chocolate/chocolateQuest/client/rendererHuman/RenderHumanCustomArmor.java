package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.client.model.ModelHumanCustomArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBiped;

public class RenderHumanCustomArmor extends RenderHuman
{
    public RenderHumanCustomArmor(final ModelBiped modelbase, final float f, final ResourceLocation r) {
        super(modelbase, f, r);
    }
    
    @Override
    protected void func_82421_b() {
        this.modelArmourChestplate = new ModelHumanCustomArmor(1.0f, false);
        this.field_82425_h = new ModelHumanCustomArmor(0.5f, false);
    }
}
