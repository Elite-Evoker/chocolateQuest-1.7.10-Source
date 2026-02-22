package com.chocolate.chocolateQuest.client.itemsRender;

import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class RenderItemShield extends RenderItemBase
{
    @Override
    protected void renderEquipped(final EntityLivingBase par1EntityLiving, final ItemStack itemstack) {
        if (par1EntityLiving != Minecraft.getMinecraft().thePlayer || Minecraft.getMinecraft().gameSettings.thirdPersonView != 0) {
            GL11.glRotatef(-75.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(1.0f, -0.6f, -0.55f);
        }
        else {
            GL11.glTranslatef(0.0f, -0.3f, 0.3f);
        }
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(1.4f, 1.4f, 1.4f);
        doRenderShield(itemstack);
    }
    
    @Override
    protected void renderFirstPerson(final EntityLivingBase player, final ItemStack itemstack) {
        if (((EntityPlayer)player).isBlocking()) {
            GL11.glLoadIdentity();
            GL11.glTranslatef(-1.5f, -1.0f, -1.0f);
        }
        doRenderShield(itemstack);
    }
    
    public static void doRenderShield(final ItemStack itemstack) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderItemBase.doRenderItem(itemstack);
        GL11.glEnable(32826);
        Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
        final int spriteIndex = itemstack.getMetadata();
        final float i1 = (spriteIndex % 16 * 16 + 0) / 256.0f;
        final float i2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
        final float i3 = spriteIndex / 16 * 16 / 256.0f;
        final float i4 = i3 + 0.0625f;
        final float f5 = 1.0f;
        final float posZ = 0.0f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0, 0.0, (double)posZ, (double)i1, (double)i4);
        tessellator.addVertexWithUV((double)f5, 0.0, (double)posZ, (double)i2, (double)i4);
        tessellator.addVertexWithUV((double)f5, 1.0, (double)posZ, (double)i2, (double)i3);
        tessellator.addVertexWithUV(0.0, 1.0, (double)posZ, (double)i1, (double)i3);
        tessellator.draw();
        GL11.glDisable(32826);
    }
}
