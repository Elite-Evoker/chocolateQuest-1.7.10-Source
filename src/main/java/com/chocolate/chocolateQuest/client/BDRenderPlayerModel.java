package com.chocolate.chocolateQuest.client;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.items.swords.ItemBaseBroadSword;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;

public class BDRenderPlayerModel extends ModelBiped
{
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            if (ep.inventory.getCurrentItem() != null) {
                final ItemStack is = ep.inventory.getCurrentItem();
                if (is.getItem() instanceof ItemBaseBroadSword) {
                    this.setTwoHandedAngles(f5);
                }
                if (ep.isBlocking()) {
                    this.setShiedRotation(f5);
                }
            }
        }
    }
    
    public void setTwoHandedAngles(final float time) {
        float f7 = 0.0f;
        final float rotationYaw = 0.0f;
        final float swing = MathHelper.sin(this.swingProgress * 3.1415927f);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = -0.3f;
        this.bipedRightArm.rotateAngleY = rotationYaw - 0.6f;
        this.bipedLeftArm.rotateAngleY = rotationYaw + 0.6f;
        this.bipedRightArm.rotateAngleX = -0.8f + swing;
        this.bipedLeftArm.rotateAngleX = -0.8f + swing;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleZ += MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleX += MathHelper.sin(time * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
        float f8 = 1.0f - this.swingProgress;
        f8 *= f8;
        f8 *= f8;
        f8 = 1.0f - f8;
        f7 = MathHelper.sin(f8 * 3.1415927f);
        final float f9 = MathHelper.sin(this.swingProgress * 3.1415927f) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
        this.bipedRightArm.rotateAngleX -= (float)(f7 * 1.2 + f9);
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
        this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
        this.bipedLeftArm.rotateAngleX = (float)(this.bipedRightArm.rotateAngleX - (f7 * 1.2 + f9));
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
        this.bipedLeftArm.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
    }
    
    public void setShiedRotation(final float time) {
        final float f7 = 0.0f;
        final float f8 = 0.0f;
        this.bipedLeftArm.rotateAngleZ = -0.7f;
        this.bipedLeftArm.rotateAngleY = 1.2f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleZ -= MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
    }
}
