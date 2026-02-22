package com.chocolate.chocolateQuest.client.rendererHuman;

import net.minecraft.client.model.ModelBiped;
import com.chocolate.chocolateQuest.client.model.ModelGolemSmall;
import net.minecraft.util.ResourceLocation;

public class RenderHumanGolem extends RenderHuman
{
    public RenderHumanGolem(final float f, final ResourceLocation r) {
        super(new ModelGolemSmall(), f, r);
    }
    
    @Override
    protected void func_82421_b() {
        this.modelArmourChestplate = new ModelGolemSmall(1.0f);
        this.field_82425_h = new ModelGolemSmall(0.5f);
    }
}
