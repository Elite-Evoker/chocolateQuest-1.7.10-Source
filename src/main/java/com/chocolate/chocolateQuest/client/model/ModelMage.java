package com.chocolate.chocolateQuest.client.model;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelMage extends ModelBase
{
    public ModelRenderer field_40340_a;
    public ModelRenderer field_40338_b;
    public ModelRenderer field_40339_c;
    public ModelRenderer field_40336_d;
    public ModelRenderer field_40337_e;
    public int field_40334_f;
    public int field_40335_g;
    public boolean field_40341_n;
    public boolean field_40342_o;
    
    public ModelMage() {
        this(0.0f);
    }
    
    public ModelMage(final float f) {
        this(f, 0.0f);
    }
    
    public ModelMage(final float f, final float f1) {
        this.field_40334_f = 0;
        this.field_40335_g = 0;
        this.field_40341_n = false;
        this.field_40342_o = false;
        final byte byte0 = 64;
        final byte byte2 = 64;
        (this.field_40340_a = new ModelRenderer((ModelBase)this).setTextureSize((int)byte0, (int)byte2)).setRotationPoint(0.0f, 0.0f + f1, 0.0f);
        this.field_40340_a.setTextureOffset(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8, 10, 8, f);
        this.field_40340_a.setTextureOffset(24, 0).addBox(-1.0f, -3.0f, -6.0f, 2, 4, 2, f);
        (this.field_40338_b = new ModelRenderer((ModelBase)this).setTextureSize((int)byte0, (int)byte2)).setRotationPoint(0.0f, 0.0f + f1, 0.0f);
        this.field_40338_b.setTextureOffset(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8, 12, 6, f);
        this.field_40338_b.setTextureOffset(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8, 18, 6, f + 0.5f);
        (this.field_40339_c = new ModelRenderer((ModelBase)this).setTextureSize((int)byte0, (int)byte2)).setRotationPoint(0.0f, 0.0f + f1 + 2.0f, 0.0f);
        this.field_40339_c.setTextureOffset(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4, 8, 4, f);
        this.field_40339_c.setTextureOffset(44, 22).addBox(4.0f, -2.0f, -2.0f, 4, 8, 4, f);
        this.field_40339_c.setTextureOffset(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8, 4, 4, f);
        (this.field_40336_d = new ModelRenderer((ModelBase)this, 0, 22).setTextureSize((int)byte0, (int)byte2)).setRotationPoint(-2.0f, 12.0f + f1, 0.0f);
        this.field_40336_d.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, f);
        this.field_40337_e = new ModelRenderer((ModelBase)this, 0, 22).setTextureSize((int)byte0, (int)byte2);
        this.field_40337_e.mirror = true;
        this.field_40337_e.setRotationPoint(2.0f, 12.0f + f1, 0.0f);
        this.field_40337_e.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5);
        this.field_40340_a.render(f5);
        this.field_40338_b.render(f5);
        this.field_40336_d.render(f5);
        this.field_40337_e.render(f5);
        this.field_40339_c.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.field_40340_a.rotateAngleY = f3 / 57.295776f;
        this.field_40340_a.rotateAngleX = f4 / 57.295776f;
        this.field_40339_c.rotationPointY = 3.0f;
        this.field_40339_c.rotationPointZ = -1.0f;
        this.field_40339_c.rotateAngleX = -0.75f;
        this.field_40336_d.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1 * 0.5f;
        this.field_40337_e.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1 * 0.5f;
        this.field_40336_d.rotateAngleY = 0.0f;
        this.field_40337_e.rotateAngleY = 0.0f;
    }
}
