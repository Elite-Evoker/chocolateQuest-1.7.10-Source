package com.chocolate.chocolateQuest.client.itemsRender;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.IItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderTileEntityItem extends RenderItemBase
{
    TileEntitySpecialRenderer render;
    TileEntity entity;
    
    public RenderTileEntityItem(final TileEntity e, final TileEntitySpecialRenderer render) {
        this.entity = e;
        (this.render = render).func_147497_a(TileEntityRendererDispatcher.instance);
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
            this.renderEquipped(p, item);
        }
    }
    
    @Override
    protected void renderInventory(final ItemStack itemstack) {
        GL11.glScalef(10.0f, -10.0f, 10.0f);
        GL11.glTranslatef(0.5f, -1.4f, 0.0f);
        GL11.glRotatef(-30.0f, -0.6f, 1.0f, 0.0f);
        this.render.renderTileEntityAt(this.entity, 0.0, 0.0, 0.0, 0.0f);
    }
    
    protected void render(final Entity entity, final ItemStack itemstack, final int par3) {
        GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
        this.render.renderTileEntityAt(this.entity, 0.0, 0.0, 0.0, 0.0f);
    }
    
    @Override
    protected void renderEquipped(final EntityLivingBase entity, final ItemStack itemstack) {
        GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
        this.render.renderTileEntityAt(this.entity, 0.0, 0.0, 0.0, 0.0f);
    }
}
