package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class ModelHumanZombie extends ModelHuman
{
    public ModelHumanZombie() {
        this(0.0f, false);
    }
    
    protected ModelHumanZombie(final float par1, final float par2, final int par3, final int par4) {
        super(par1, par2, par3, par4);
    }
    
    public ModelHumanZombie(final float par1, final boolean par2) {
        super(par1, 0.0f, 64, par2 ? 32 : 64);
    }
    
    @Override
    public void setHumanRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final EntityHumanBase e) {
        super.setHumanRotationAngles(f, f1, f2, f3, f4, f5, e);
    }
}
