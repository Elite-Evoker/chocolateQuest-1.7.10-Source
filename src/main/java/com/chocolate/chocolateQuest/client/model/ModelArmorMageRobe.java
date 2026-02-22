package com.chocolate.chocolateQuest.client.model;

import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelArmorMageRobe extends ModelArmor
{
    int count;
    
    public ModelArmorMageRobe() {
        this(0.0f);
    }
    
    public ModelArmorMageRobe(final float f) {
        super(f, 1);
        this.count = 0;
        (this.bipedBody = new ModelRenderer((ModelBase)this)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedBody.setTextureOffset(0, 0).addBox(-4.0f, 0.0f, -3.0f, 8, 18, 6, 0.8f);
        (this.bipedHead = new ModelRenderer((ModelBase)this)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.setTextureOffset(32, 0).addBox(-4.0f, -10.0f, -4.0f, 8, 10, 8, 1.0f);
    }
    
    @Override
    public void render(final Entity e, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        ItemStack cachedItem = null;
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, e);
        if (e != null) {
            cachedItem = ((EntityLivingBase)e).getEquipmentInSlot(4 - this.type);
        }
        if (cachedItem != null) {
            if (e != null && e.hurtResistantTime > 0) {
                GL11.glEnable(3553);
            }
            this.setArmorColor(cachedItem);
            if (!(e instanceof EntityLivingBase) || ((EntityLivingBase)e).getEquipmentInSlot(4) == null) {
                this.bipedHead.render(par7);
            }
            this.bipedBody.render(par7);
        }
        else {
            if (!(e instanceof EntityLivingBase) || ((EntityLivingBase)e).getEquipmentInSlot(4) == null) {
                this.bipedHead.render(par7);
            }
            this.bipedBody.render(par7);
        }
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
