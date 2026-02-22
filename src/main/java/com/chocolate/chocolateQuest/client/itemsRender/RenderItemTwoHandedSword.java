package com.chocolate.chocolateQuest.client.itemsRender;

import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemTwoHandedSword extends RenderItemBase implements IItemRenderer
{
    @Override
    protected void renderEquipped(final EntityLivingBase par1EntityLiving, final ItemStack itemstack) {
        GL11.glTranslatef(-0.9f, -0.08f, 0.14f);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        RenderItemBase.doRenderItem(itemstack);
    }
    
    @Override
    protected void renderFirstPerson(final EntityLivingBase player, final ItemStack itemstack) {
        GL11.glTranslatef(0.0f, -0.3f, 0.6f);
        GL11.glRotatef(-8.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        RenderItemBase.doRenderItem(itemstack);
    }
}
