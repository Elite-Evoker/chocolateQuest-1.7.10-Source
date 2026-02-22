package com.chocolate.chocolateQuest.client.rendererHuman;

import net.minecraft.client.model.ModelBiped;
import com.chocolate.chocolateQuest.client.model.ModelOgre;
import net.minecraft.util.ResourceLocation;

public class RenderHumanOrc extends RenderHuman
{
    public RenderHumanOrc(final float f, final ResourceLocation r) {
        super(new ModelOgre(), f, r);
    }
    
    @Override
    protected void func_82421_b() {
        this.modelArmourChestplate = new ModelOgre(1.0f, false);
        this.field_82425_h = new ModelOgre(0.5f, false);
    }
}
