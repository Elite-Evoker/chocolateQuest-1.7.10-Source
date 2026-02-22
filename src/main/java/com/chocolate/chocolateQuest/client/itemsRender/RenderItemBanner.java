package com.chocolate.chocolateQuest.client.itemsRender;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.client.IItemRenderer;
import net.minecraft.item.ItemStack;

public class RenderItemBanner extends RenderItemBase
{
    @Override
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY;
    }
    
    @Override
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        super.renderItem(type, item, data);
    }
    
    @Override
    protected void renderInventory(final ItemStack itemstack) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
        final int spriteIndex = itemstack.getMetadata();
        renderBanner(spriteIndex, 16.0f);
        GL11.glDisable(3042);
    }
    
    @Override
    protected void renderEquipped(final EntityLivingBase entity, final ItemStack itemstack) {
        GL11.glTranslatef(-0.1f, 0.12f, 0.0f);
        if (entity == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
            GL11.glTranslatef(0.0f, -0.5f, 0.3f);
            GL11.glScalef(2.0f, 4.0f, 2.0f);
        }
        else {
            GL11.glTranslatef(-0.2f, 0.0f, 0.0f);
            GL11.glScalef(2.0f, 4.0f, 2.0f);
        }
        this.renderItem(itemstack);
    }
    
    @Override
    protected void renderItem(final ItemStack is) {
        RenderItemBase.doRenderItem(is);
        GL11.glEnable(32826);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
        final int spriteIndex = is.getMetadata();
        final float i1 = (spriteIndex % 16 * 16 + 0) / 256.0f;
        final float i2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
        final float i3 = (32 + spriteIndex / 16 * 32) / 256.0f;
        final float i4 = (64 + spriteIndex / 16 * 32) / 256.0f;
        final float f5 = 1.0f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        final double desp = 0.24;
        final double timeDiff = 1000.0;
        final double inclinationX = 0.8;
        final double d1 = desp + Math.cos(System.currentTimeMillis() / timeDiff) * desp + inclinationX;
        final double d2 = desp + Math.cos((System.currentTimeMillis() + timeDiff) / timeDiff) * desp + inclinationX;
        final double extraY = -0.1;
        tessellator.addVertexWithUV(0.0 + inclinationX, extraY, d1, (double)i1, (double)i4);
        tessellator.addVertexWithUV(f5 + inclinationX, 0.0, d2, (double)i2, (double)i4);
        tessellator.addVertexWithUV((double)f5, (double)f5, 0.0, (double)i2, (double)i3);
        tessellator.addVertexWithUV(0.0, (double)f5, 0.0, (double)i1, (double)i3);
        tessellator.draw();
        GL11.glEnable(2884);
        GL11.glDisable(32826);
        GL11.glDisable(3042);
    }
    
    public static void renderBanner(final int spriteIndex, final float size) {
        final float i1 = (spriteIndex % 16 * 16 + 0) / 256.0f;
        final float i2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
        final float ty0 = (32 + spriteIndex / 16 * 32) / 256.0f;
        final float ty2 = (64 + spriteIndex / 16 * 32) / 256.0f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0, (double)size, 0.0, (double)i1, (double)ty2);
        tessellator.addVertexWithUV((double)size, (double)size, 0.0, (double)i2, (double)ty2);
        tessellator.addVertexWithUV((double)size, 0.0, 0.0, (double)i2, (double)ty0);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)i1, (double)ty0);
        tessellator.draw();
    }
}
