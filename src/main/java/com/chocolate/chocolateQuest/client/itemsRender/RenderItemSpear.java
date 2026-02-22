package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class RenderItemSpear extends RenderItemBase
{
    @Override
    protected void renderEquipped(final EntityLivingBase player, final ItemStack itemstack) {
        GL11.glTranslatef(-0.6f, -0.2f, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        if (player instanceof EntityPlayer) {
            if (((EntityPlayer)player).getItemInUse() == itemstack) {
                GL11.glTranslatef(0.7f, 1.0f, 0.2f);
                GL11.glRotatef(-104.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(10.0f, 0.0f, 1.0f, 0.0f);
            }
        }
        else if (player instanceof EntityHumanBase && ((EntityHumanBase)player).isAiming()) {
            GL11.glTranslatef(0.7f, 1.0f, 0.3f);
            GL11.glRotatef(-104.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(10.0f, 0.0f, 1.0f, 0.0f);
        }
        this.doRender(player, itemstack);
    }
    
    @Override
    protected void renderFirstPerson(final EntityLivingBase player, final ItemStack itemstack) {
        GL11.glScalef(2.5f, 2.5f, 2.5f);
        GL11.glTranslatef(0.12f, 0.05f, 0.8f);
        GL11.glRotatef(-48.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(30.0f, 1.0f, 1.0f, 0.0f);
        if (player instanceof EntityPlayer && (((EntityPlayer)player).getItemInUse() == itemstack || player.isSwingInProgress)) {
            GL11.glTranslatef(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-75.0f, 0.0f, 0.0f, 1.0f);
        }
        this.doRender(player, itemstack);
    }
    
    public void doRender(final EntityLivingBase par1EntityLiving, final ItemStack itemstack) {
        RenderItemBase.doRenderItem(itemstack, 0);
    }
}
