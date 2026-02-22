package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.IItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

public class RenderItemGolemWeapon extends RenderItemBase
{
    public static final ResourceLocation texture;
    ModelBase model;
    
    public RenderItemGolemWeapon(final ModelBase model) {
        this.model = model;
    }
    
    @Override
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY;
    }
    
    @Override
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    @Override
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderItemGolemWeapon.texture);
        if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
            final EntityLivingBase p = (EntityLivingBase)data[1];
            this.renderEquipped(p, item);
        }
        if (type == IItemRenderer.ItemRenderType.ENTITY) {
            final Entity p2 = (Entity)data[1];
            this.render(p2, item, 0);
        }
        if (type == IItemRenderer.ItemRenderType.INVENTORY) {
            this.renderInventory(item);
        }
        if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
            final EntityLivingBase p = (EntityLivingBase)data[1];
            this.renderFP(p, item, 0);
        }
    }
    
    @Override
    protected void renderInventory(final ItemStack itemstack) {
        GL11.glDisable(2884);
        GL11.glTranslatef(8.0f, 14.0f, 0.0f);
        GL11.glScalef(30.0f, 30.0f, 30.0f);
        GL11.glRotatef(25.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(25.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        this.model.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
    }
    
    protected void render(final Entity entity, final ItemStack itemstack, final int par3) {
        GL11.glScalef(3.0f, 3.0f, 3.0f);
        this.model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
    }
    
    @Override
    protected void renderEquipped(final EntityLivingBase entity, final ItemStack itemstack) {
        GL11.glTranslatef(0.14f, -0.32f, -0.4f);
        if (entity instanceof EntityGolemMecha) {
            GL11.glTranslatef(-0.44f, -0.08f, -0.1f);
            GL11.glScalef(1.1f, 1.1f, 1.1f);
        }
        GL11.glRotatef(-6.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(16.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(24.0f, 0.0f, 1.0f, 1.0f);
        GL11.glScalef(2.2f, 2.2f, 2.2f);
        this.model.render((Entity)entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
    }
    
    protected void renderFP(final EntityLivingBase entity, final ItemStack itemstack, final int par3) {
        GL11.glTranslatef(0.54f, 0.52f, 0.61f);
        GL11.glRotatef(-20.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(80.0f, 0.0f, 1.0f, 0.0f);
        this.renderEquipped(entity, itemstack);
    }
    
    static {
        texture = new ResourceLocation("chocolateQuest:textures/entity/golemWeapon.png");
    }
}
