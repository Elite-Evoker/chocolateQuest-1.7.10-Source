package com.chocolate.chocolateQuest.client.itemsRender;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class RenderItemPistol extends RenderItemBase
{
    @Override
    protected void renderEquipped(final EntityLivingBase par1EntityLiving, final ItemStack itemstack) {
        final float par1 = 0.0f;
        final float f1 = 0.0f;
        GL11.glTranslatef(0.4f, 0.2f, -0.4f);
        GL11.glRotatef(-75.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);
        final Minecraft mc = Minecraft.getMinecraft();
        RenderItemBase.doRenderItem(itemstack);
    }
}
