package com.chocolate.chocolateQuest.client.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;

public class ModelElemental extends ModelHuman
{
    public ModelElemental() {
        this(0.0f);
    }
    
    public ModelElemental(final float f) {
        super(f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        GL11.glDisable(2896);
        GL11.glMatrixMode(5890);
        GL11.glPushMatrix();
        final float desp = System.nanoTime() * 1.0E-10f;
        GL11.glTranslatef(desp, desp, 0.0f);
        GL11.glMatrixMode(5888);
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        GL11.glMatrixMode(5890);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glEnable(2896);
    }
}
