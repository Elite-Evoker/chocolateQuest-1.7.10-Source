package com.chocolate.chocolateQuest.client.itemsRender;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.IItemRenderer;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

public class RenderItemModel extends RenderItemBase
{
    public final ResourceLocation texture;
    ModelBase model;
    int armorType;
    
    public RenderItemModel(final ItemArmor item) {
        final ItemStack is = new ItemStack((Item)item);
        this.armorType = item.armorType;
        this.texture = new ResourceLocation(item.getArmorTexture(is, (Entity)null, this.armorType, ""));
        this.model = (ModelBase)item.getArmorModel((EntityLivingBase)null, is, this.armorType);
        if (this.model == null) {
            this.model = (ModelBase)ClientProxy.defaultArmor;
        }
    }
    
    @Override
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        item.getItem().getArmorModel((EntityLivingBase)null, item, this.armorType);
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY;
    }
    
    @Override
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    @Override
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
        if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
            final EntityLivingBase p = (EntityLivingBase)data[1];
            this.renderEquipped(p, item);
        }
        if (type == IItemRenderer.ItemRenderType.ENTITY) {
            final Entity p2 = (Entity)data[1];
            this.render(p2, item);
        }
        if (type == IItemRenderer.ItemRenderType.INVENTORY) {
            this.renderInventory(item);
        }
        if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
            final EntityLivingBase p = (EntityLivingBase)data[1];
            this.renderFP(p, item);
        }
    }
    
    @Override
    protected void renderInventory(final ItemStack itemstack) {
        GL11.glDisable(2884);
        GL11.glEnable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        int armorOffset = this.armorType * 6;
        if (this.armorType == 2) {
            armorOffset += 2;
        }
        GL11.glTranslatef(8.0f, (float)(10 - armorOffset), 0.0f);
        GL11.glScalef(15.0f, 15.0f, 15.0f);
        GL11.glRotatef(-25.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(220.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
        this.model.render((Entity)null, (float)this.armorType, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glEnable(2884);
    }
    
    protected void render(final Entity entity, final ItemStack itemstack) {
        GL11.glDisable(2884);
        float armorOffset = this.armorType * 1.2f;
        if (this.armorType == 3) {
            armorOffset = 3.2f;
        }
        GL11.glTranslatef(0.0f, armorOffset, 0.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        this.model.render(entity, (float)this.armorType, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glEnable(2884);
    }
    
    @Override
    protected void renderEquipped(final EntityLivingBase entity, final ItemStack itemstack) {
        float armorOffset = 0.4f - this.armorType * 0.6f;
        if (this.armorType == 2) {
            armorOffset += 0.2f;
        }
        GL11.glTranslatef(0.5f, armorOffset * 0.5f, 0.4f + armorOffset);
        GL11.glRotatef(-6.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(16.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(24.0f, 0.0f, 1.0f, 1.0f);
        this.model.render((Entity)entity, (float)this.armorType, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
    }
    
    protected void renderFP(final EntityLivingBase entity, final ItemStack itemstack) {
    }
}
