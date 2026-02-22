package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelHumanSkeleton extends ModelHuman
{
    public ModelHumanSkeleton() {
        this(0.0f);
    }
    
    public ModelHumanSkeleton(final float f) {
        this(f, 0.0f);
    }
    
    public ModelHumanSkeleton(final float f, final float f1) {
        this.heldItemLeft = 0;
        this.heldItemRight = 0;
        this.isSneak = false;
        this.aimedBow = false;
        (this.bipedCloak = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, f);
        (this.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f + f1, 0.0f);
        (this.bipedHeadwear = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f + f1, 0.0f);
        (this.bipedBody = new ModelRenderer((ModelBase)this, 16, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + f1, 0.0f);
        (this.bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16)).addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, f);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, f);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
        (this.bipedRightLeg = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, f);
        this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer((ModelBase)this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, f);
        this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
    }
    
    @Override
    public boolean useTinyArms() {
        return false;
    }
}
