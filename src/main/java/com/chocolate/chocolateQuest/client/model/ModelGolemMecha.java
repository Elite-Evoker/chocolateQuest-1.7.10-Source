package com.chocolate.chocolateQuest.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelGolemMecha extends ModelHuman
{
    public ModelRenderer antennaStick;
    public ModelRenderer antenna;
    
    public ModelGolemMecha() {
        this(0.0f);
    }
    
    public ModelGolemMecha(final float f) {
        super(f);
        (this.bipedBody = new ModelRenderer((ModelBase)this, 16, 22)).addBox(-3.0f, 6.0f, -2.0f, 6, 6, 4, f);
        this.bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-6.0f, -8.0f, -7.0f, 12, 14, 12, 1.0f + f);
        this.bipedHead.setRotationPoint(0.0f, 2.0f, 0.0f);
        (this.bipedHeadwear = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-3.0f, 6.0f, 1.0f, 6, 2, 2, f);
        this.bipedHeadwear.setRotationPoint(0.0f, 2.0f, 0.0f);
        (this.bipedRightArm = new ModelRenderer((ModelBase)this, 48, 16)).addBox(-6.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedRightArm.setRotationPoint(-8.0f, 2.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer((ModelBase)this, 48, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(2.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftArm.setRotationPoint(8.0f, 2.0f, 0.0f);
        (this.bipedRightLeg = new ModelRenderer((ModelBase)this, 48, 0)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, f);
        this.bipedRightLeg.setRotationPoint(-3.0f, 12.0f + f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer((ModelBase)this, 48, 0);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftLeg.setRotationPoint(3.0f, 12.0f + f, 0.0f);
        (this.antenna = new ModelRenderer((ModelBase)this, 0, 4)).addBox(-1.0f, -3.0f, 0.0f, 3, 3, 1, f);
        this.antenna.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.antennaStick = new ModelRenderer((ModelBase)this, 36, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 10, 1, f);
        this.antennaStick.setRotationPoint(6.0f, -12.0f, 5.0f);
        this.antennaStick.addChild(this.antenna);
    }
    
    @Override
    public void render(final Entity z, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(z, f, f1, f2, f3, f4, f5);
        this.antennaStick.render(f5);
    }
    
    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedHeadwear.rotateAngleX = -1.5707964f;
        this.bipedHeadwear.rotateAngleY = 0.0f;
        this.antenna.rotateAngleX = this.bipedHead.rotateAngleX;
        this.antenna.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHead.rotateAngleX = 0.0f;
        this.bipedHead.rotateAngleY = 0.0f;
    }
    
    @Override
    public boolean useTinyArms() {
        return false;
    }
}
