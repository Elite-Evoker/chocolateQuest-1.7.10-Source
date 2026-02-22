package com.chocolate.chocolateQuest.client.model;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;

public class ModelArmorColored extends ModelArmor
{
    public ModelArmorColored(final int type) {
        super(type);
    }
    
    public ModelArmorColored(final float f, final int type) {
        super(f, type);
    }
    
    @Override
    public void render(final Entity e, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        if (e != null) {
            this.setRotationAngles(par2, par3, par4, par5, par6, par7, e);
            if (e.hurtResistantTime > 0) {
                GL11.glEnable(3553);
            }
            final ItemStack cachedItem = ((EntityLivingBase)e).getEquipmentInSlot(4 - this.type);
            if (cachedItem != null && this.renderPass == 0) {
                this.setArmorColor(cachedItem);
                if (this.type == 2) {
                    Minecraft.getMinecraft().renderEngine.bindTexture(ModelArmorColored.layer2);
                }
                else {
                    Minecraft.getMinecraft().renderEngine.bindTexture(ModelArmorColored.layer1);
                }
                super.render(e, par2, par3, par4, par5, par6, par7);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(cachedItem.getItem().getArmorTexture(cachedItem, e, this.type, "")));
            }
        }
        super.render(e, par2, par3, par4, par5, par6, par7);
        ++this.renderPass;
    }
}
