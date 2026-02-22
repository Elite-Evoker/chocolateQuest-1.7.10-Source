package com.chocolate.chocolateQuest.client.itemsRender;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemBase implements IItemRenderer
{
    private static final ResourceLocation itemGlint;
    
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
            final EntityLivingBase p = (EntityLivingBase)data[1];
            this.renderEquipped(p, item);
        }
        if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
            final EntityLivingBase p = (EntityLivingBase)data[1];
            this.renderFirstPerson(p, item);
        }
        if (type == IItemRenderer.ItemRenderType.INVENTORY) {
            this.renderInventory(item);
        }
        if (type == IItemRenderer.ItemRenderType.ENTITY) {
            final Entity e = (Entity)data[1];
            GL11.glRotatef((float)(e.ticksExisted % 360), 0.0f, 1.0f, 0.0f);
            this.renderAsEntity(item);
        }
    }
    
    protected void renderFirstPerson(final EntityLivingBase player, final ItemStack itemstack) {
        this.renderItem(itemstack);
    }
    
    protected void renderInventory(final ItemStack itemstack) {
        this.renderItem(itemstack);
    }
    
    protected void renderAsEntity(final ItemStack itemstack) {
        this.renderItem(itemstack);
    }
    
    protected void renderEquipped(final EntityLivingBase player, final ItemStack itemstack) {
        this.renderItem(itemstack);
    }
    
    protected void renderItem(final ItemStack is) {
        doRenderItem(is);
    }
    
    public static void doRenderItem(final ItemStack itemstack) {
        doRenderItem(itemstack, 16751001, false);
    }
    
    public static void doRenderItem(final ItemStack itemstack, final int color) {
        doRenderItem(itemstack, color, false);
    }
    
    public static void doRenderItem(final ItemStack itemstack, final int color, final boolean effect) {
        final IIcon icon = itemstack.getItem().getIcon(itemstack, 0);
        doRenderItem(icon, itemstack, color, false);
    }
    
    public static void doRenderItem(final IIcon icon, final ItemStack itemstack, final int effectColor, final boolean effect) {
        if (icon == null) {
            GL11.glPopMatrix();
            return;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        final ResourceLocation resourcelocation = mc.renderEngine.getResourceLocation(itemstack.getItemSpriteNumber());
        mc.renderEngine.bindTexture(resourcelocation);
        GL11.glPushMatrix();
        final Tessellator tessellator = Tessellator.instance;
        final float f = icon.getMinU();
        final float f2 = icon.getMaxU();
        final float f3 = icon.getMinV();
        final float f4 = icon.getMaxV();
        final float f5 = 0.0f;
        final float f6 = 0.3f;
        ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
        if (itemstack != null && (itemstack.hasEffect() || effect)) {
            final float red = (float)(((effectColor & 0xFF0000) >> 16) / 255.0);
            final float green = (float)(((effectColor & 0xFF00) >> 8) / 255.0);
            final float blue = (float)((effectColor & 0xFF) / 255.0);
            GL11.glDepthFunc(514);
            GL11.glDisable(2896);
            mc.renderEngine.bindTexture(RenderItemBase.itemGlint);
            GL11.glEnable(3042);
            GL11.glBlendFunc(768, 1);
            GL11.glColor4f(red, green, blue, 0.5f);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            final float f7 = 0.125f;
            GL11.glScalef(f7, f7, f7);
            final float f8 = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
            GL11.glTranslatef(f8, 0.0f, 0.0f);
            GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
            ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glDepthFunc(515);
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    static {
        itemGlint = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
