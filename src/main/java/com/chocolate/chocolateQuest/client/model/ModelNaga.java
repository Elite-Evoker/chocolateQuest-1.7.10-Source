package com.chocolate.chocolateQuest.client.model;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelNaga extends ModelHuman
{
    ModelRenderer[] mouth;
    ModelRenderer[] tail;
    
    public ModelNaga() {
        this.mouth = new ModelRenderer[4];
        for (int i = 0; i < this.mouth.length; ++i) {
            (this.mouth[i] = new ModelRenderer((ModelBase)this, 24, 0)).addBox(-2.0f + i, -3.0f, -7.0f, 1, 1, 4);
            this.bipedHead.addChild(this.mouth[i]);
        }
        this.tail = new ModelRenderer[4];
        (this.tail[0] = new ModelRenderer((ModelBase)this, 34, 0)).addBox(-6.0f, 0.0f, -2.0f, 8, 4, 4);
        this.tail[0].setRotationPoint(0.0f, -3.0f, 14.0f);
        (this.tail[1] = new ModelRenderer((ModelBase)this, 34, 8)).addBox(-5.0f, 0.0f, -2.0f, 6, 4, 4);
        this.tail[1].setRotationPoint(0.0f, -3.0f, 14.0f);
        (this.tail[2] = new ModelRenderer((ModelBase)this, 1, 17)).addBox(-4.0f, 0.0f, -2.0f, 4, 3, 3);
        this.tail[2].setRotationPoint(0.0f, -3.0f, 14.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.bipedHead.render(par7);
        this.bipedBody.render(par7);
        this.renderArms(par7);
        for (int i = 0; i < this.mouth.length; ++i) {
            this.mouth[i].rotateAngleX = (float)Math.sin(par4 / 10.0f + i * 20) / 6.0f + 0.5f;
        }
        float px = 1.5f;
        float py = 10.0f;
        float pz = 2.0f;
        final float dist = 3.0f;
        for (int j = 0; j < 5; ++j) {
            py += j;
            pz -= (float)Math.sin(j + 1);
            px += MathHelper.cos(par2 * 0.6662f) * 1.4f * par3;
            int tailPos = 0;
            if (j >= 4) {
                tailPos = 2;
            }
            if (j > 2) {
                tailPos = 1;
            }
            this.tail[tailPos].rotationPointX = px;
            this.tail[tailPos].rotationPointY = py;
            this.tail[tailPos].rotationPointZ = pz;
            this.tail[tailPos].render(par7);
        }
        for (int j = 0; j < 3; ++j) {
            px += MathHelper.cos(par2 * 0.6662f) * 1.4f * par3;
            this.tail[2].rotationPointX = px;
            this.tail[2].rotationPointY = py;
            this.tail[2].rotationPointZ = pz + j * dist;
            this.tail[2].render(par7);
        }
    }
    
    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
