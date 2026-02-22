package com.chocolate.chocolateQuest.client.model;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemArmor;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelArmorHeavyPlate extends ModelArmor
{
    boolean renderHead;
    
    public ModelArmorHeavyPlate() {
        this(0.0f);
    }
    
    public ModelArmorHeavyPlate(final float f, final boolean head) {
        this(f);
        this.renderHead = head;
    }
    
    public ModelArmorHeavyPlate(final float f) {
        super(f, 1);
        this.renderHead = false;
        final float scale = 1.5f;
        (this.bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16)).addBox(-4.0f, -3.0f, -2.0f, 5, 10, 5, scale);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-0.0f, -3.0f, -2.0f, 5, 10, 5, scale);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
        (this.bipedHeadwear = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, 1.1f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.renderHead) {
            this.bipedHeadwear.setRotationPoint(0.0f, -1.5f, 0.0f);
            this.bipedHeadwear.render(par7);
        }
        this.bipedBody.render(par7);
        this.bipedRightArm.render(par7);
        this.bipedLeftArm.render(par7);
        if (par1Entity != null) {
            final ItemStack cachedItem = ((EntityLivingBase)par1Entity).getEquipmentInSlot(3);
            if (cachedItem != null) {
                if (par1Entity.hurtResistantTime > 0) {
                    GL11.glEnable(3553);
                }
                this.renderCape(cachedItem);
                this.renderFront(cachedItem);
                if (this.renderPass <= 1) {
                    if (((ItemArmor)cachedItem.getItem()).hasColor(cachedItem)) {
                        this.setArmorColor(cachedItem);
                        Minecraft.getMinecraft().renderEngine.bindTexture(ModelArmorHeavyPlate.layer1);
                        this.bipedBody.render(par7);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(cachedItem.getItem().getArmorTexture(cachedItem, par1Entity, this.type, "")));
                    }
                    ++this.renderPass;
                }
            }
        }
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
