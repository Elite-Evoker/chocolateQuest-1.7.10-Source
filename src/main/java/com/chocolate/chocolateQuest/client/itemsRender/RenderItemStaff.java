package com.chocolate.chocolateQuest.client.itemsRender;

import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class RenderItemStaff extends RenderItemBase
{
    @Override
    protected void renderEquipped(final EntityLivingBase par1EntityLiving, final ItemStack itemstack) {
        GL11.glTranslatef(0.2f, -0.32f, -0.0f);
        GL11.glTranslatef(-0.5f, 0.0f, 0.0f);
        GL11.glScalef(1.8f, 1.8f, 1.8f);
        RenderItemBase.doRenderItem(itemstack);
    }
    
    @Override
    protected void renderFirstPerson(final EntityLivingBase player, final ItemStack itemstack) {
        GL11.glTranslatef(0.0f, -0.5f, 0.4f);
        GL11.glScalef(1.5f, 2.5f, 1.5f);
        RenderItemBase.doRenderItem(itemstack);
    }
}
