package com.chocolate.chocolateQuest.client.model;

import net.minecraft.entity.Entity;

public class ModelHumanCustomArmor extends ModelHuman
{
    public ModelHumanCustomArmor(final float f) {
        super(f);
    }
    
    public ModelHumanCustomArmor(final float f, final boolean renderTinyArms) {
        super(f);
        this.renderTinyArms = renderTinyArms;
    }
    
    @Override
    public void render(final Entity z, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, z);
        this.bipedHead.render(f5);
        this.bipedBody.render(f5);
        this.bipedRightArm.render(f5);
        this.bipedLeftArm.render(f5);
        this.bipedHeadwear.render(f5);
    }
    
    @Override
    public boolean useTinyArms() {
        return false;
    }
}
