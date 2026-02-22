package com.chocolate.chocolateQuest.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelGolemMechaHeavy extends ModelGolemMecha
{
    public ModelGolemMechaHeavy() {
        this(0.0f);
    }
    
    public ModelGolemMechaHeavy(final float f) {
        super(f);
        this.textureHeight = 64;
        this.textureWidth = 64;
        (this.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-9.0f, -7.0f, -7.0f, 18, 17, 14, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedBody = new ModelRenderer((ModelBase)this, 0, 38)).addBox(-4.0f, 10.01f, -2.0f, 8, 6, 4, f);
        this.bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedHeadwear = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-3.0f, 5.0f, 7.0f, 6, 2, 2, f);
        this.bipedHeadwear.setRotationPoint(0.0f, 2.0f, 0.0f);
        (this.bipedRightArm = new ModelRenderer((ModelBase)this, 24, 42)).addBox(-6.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedRightArm.setRotationPoint(-8.0f, 2.0f, 0.0f);
        (this.bipedLeftArm = new ModelRenderer((ModelBase)this, 24, 42)).addBox(2.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftArm.setRotationPoint(8.0f, 2.0f, 0.0f);
        this.bipedLeftArm.mirror = true;
        (this.bipedRightLeg = new ModelRenderer((ModelBase)this, 0, 50)).addBox(-4.0f, 2.0f, -2.0f, 4, 10, 4, f);
        this.bipedRightLeg.setRotationPoint(-4.0f, 12.0f + f, 0.0f);
        this.bipedRightLeg.setTextureSize(64, 64);
        this.bipedLeftLeg = new ModelRenderer((ModelBase)this, 0, 50);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(0.0f, 2.0f, -2.0f, 4, 10, 4, f);
        this.bipedLeftLeg.setRotationPoint(4.0f, 12.0f + f, 0.0f);
        this.bipedLeftLeg.setTextureSize(64, 64);
        (this.antenna = new ModelRenderer((ModelBase)this, 0, 4)).addBox(-1.0f, -3.0f, 0.0f, 3, 3, 1, f);
        this.antenna.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.antennaStick = new ModelRenderer((ModelBase)this, 36, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 3, 1, f);
        this.antennaStick.setRotationPoint(8.0f, -8.0f, 6.0f);
        this.antennaStick.setTextureSize(64, 64);
        this.antennaStick.addChild(this.antenna);
    }
    
    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedRightArm.setRotationPoint(-7.0f, 5.0f, 0.0f);
        this.bipedLeftArm.setRotationPoint(7.0f, 5.0f, 0.0f);
    }
}
