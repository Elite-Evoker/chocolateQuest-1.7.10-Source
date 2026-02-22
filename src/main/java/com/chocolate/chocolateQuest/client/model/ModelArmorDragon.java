package com.chocolate.chocolateQuest.client.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBiped;

public class ModelArmorDragon extends ModelBiped
{
    ModelRenderer mouthUp;
    ModelRenderer mouthDown;
    ModelRenderer nose;
    ModelRenderer hornLeft;
    ModelRenderer hornRight;
    
    public ModelArmorDragon() {
        this(1.0f);
    }
    
    public ModelArmorDragon(float size) {
        final int textureSizeX = 128;
        final int textureSizeY = 64;
        size = 0.0f;
        (this.bipedHead = new ModelRenderer((ModelBase)this, 74, 0).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.5f, -3.0f, -4.0f, 5, 5, 8, size);
        (this.mouthUp = new ModelRenderer((ModelBase)this, 106, 0).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.0f, -2.0f, -11.0f, 4, 3, 7, size);
        (this.mouthDown = new ModelRenderer((ModelBase)this, 92, 0).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.0f, 1.0f, -10.0f, 4, 1, 6, size);
        (this.nose = new ModelRenderer((ModelBase)this, 107, 11).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.0f, -3.0f, -11.0f, 4, 1, 1, size);
        (this.hornLeft = new ModelRenderer((ModelBase)this, 94, 8).setTextureSize(textureSizeX, textureSizeY)).addBox(1.5f, -2.0f, 3.0f, 1, 1, 8, size);
        this.hornLeft.rotateAngleX = 0.5f;
        this.hornLeft.rotateAngleY = 0.098f;
        (this.hornRight = new ModelRenderer((ModelBase)this, 94, 8).setTextureSize(textureSizeX, textureSizeY)).addBox(-2.5f, -2.0f, 3.0f, 1, 1, 8, size);
        this.hornRight.rotateAngleX = 0.5f;
        this.hornRight.rotateAngleY = -0.298f;
        this.bipedHead.addChild(this.mouthUp);
        this.bipedHead.addChild(this.mouthDown);
        this.bipedHead.addChild(this.nose);
        this.bipedHead.addChild(this.hornLeft);
        this.bipedHead.addChild(this.hornRight);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        GL11.glPushMatrix();
        final float scale = 1.71f;
        GL11.glScalef(scale + 0.2f, scale, scale - 0.2f);
        this.bipedHead.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.bipedHead.render(f5);
        GL11.glPopMatrix();
    }
}
