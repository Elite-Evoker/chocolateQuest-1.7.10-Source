package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.model.ModelRenderer;
import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantZombie;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;

public class ModelGiantZombie extends ModelBiped
{
    boolean attacking;
    int attackAnim;
    int maxAttackAnimTime;
    int attackType;
    
    public ModelGiantZombie() {
        this(0.0f);
    }
    
    public ModelGiantZombie(final float s) {
        super(s, 0.0f, 64, 64);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }
    
    public void setLivingAnimations(final EntityLivingBase entityliving, final float f, final float f1, final float f2) {
        super.setLivingAnimations(entityliving, f, f1, f2);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedRightLeg.rotationPointY = 12.0f;
        this.bipedLeftLeg.rotationPointY = 12.0f;
        final EntityGiantZombie e = (EntityGiantZombie)entity;
        final AttackKick kick = e.kickHelper;
        if (kick.kickTime > 0) {
            this.bipedRightLeg.rotateAngleX = 0.0f;
            this.bipedLeftLeg.rotateAngleX = 0.0f;
            final int maxAttackAnimTime = kick.kickSpeed;
            final int attackAnim = maxAttackAnimTime - kick.kickTime - maxAttackAnimTime / 10;
            final float animProgress = (attackAnim + 1 + attackAnim * f5) / maxAttackAnimTime * 6.283184f;
            final float dir = (kick.kickType == 2 || kick.kickType == 4) ? -1.0f : 1.0f;
            if (kick.kickType == 2 || kick.kickType == 1) {
                this.bipedLeftLeg.rotateAngleX = dir * MathHelper.sin(animProgress) * 1.2f - dir * 0.2f;
                this.bipedLeftLeg.rotateAngleY = dir * MathHelper.cos(animProgress) * 0.4f;
            }
            else {
                this.bipedRightLeg.rotateAngleX = dir * MathHelper.sin(animProgress) * 1.2f - dir * 0.2f;
                this.bipedRightLeg.rotateAngleY = dir * -MathHelper.cos(animProgress) * 0.4f;
            }
        }
        final float f6 = MathHelper.sin(this.swingProgress * 3.1415927f);
        final float f7 = MathHelper.sin((1.0f - (1.0f - this.swingProgress) * (1.0f - this.swingProgress)) * 3.1415927f);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleY = 0.1f - f6 * 0.6f;
        this.bipedRightArm.rotateAngleX = -1.5707964f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleX += f6 * 1.2f - f7 * 0.4f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
    }
}
