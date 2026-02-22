package com.chocolate.chocolateQuest.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.init.Items;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSwordDefensive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.model.ModelBiped;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class BDRenderPlayer extends RenderPlayer
{
    public BDRenderPlayer() {
        final ModelBiped model = new BDRenderPlayerModel();
        ReflectionHelper.setPrivateValue((Class)RenderPlayer.class, (Object)this, (Object)(this.mainModel = (ModelBase)model), new String[] { "modelBipedMain", "f" });
    }
    
    protected void renderModel(final EntityLivingBase entityliving, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.renderModel(entityliving, f, f1, f2, f3, f4, f5);
        final EntityPlayer ep = (EntityPlayer)entityliving;
        if (ep.inventory.getCurrentItem() != null) {
            final ItemStack is = ep.inventory.getCurrentItem();
            if (is.getItem() instanceof ItemBaseSwordDefensive) {
                this.renderLeftHandItem(entityliving, f1, is);
            }
        }
    }
    
    protected void renderLeftHandItem(final EntityLivingBase entityliving, final float f, final ItemStack is) {
        final ItemStack itemstack = new ItemStack(ChocolateQuest.shield, 1, ((ItemBaseSwordDefensive)is.getItem()).getShieldID(is));
        if (itemstack != null) {
            GL11.glPushMatrix();
            ((ModelBiped)this.mainModel).bipedLeftArm.postRender(0.0625f);
            GL11.glTranslatef(0.0325f, 0.4375f, 0.0625f);
            final IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
            final boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D);
            if (itemstack.getItem() == ChocolateQuest.shield) {
                final float var6 = 1.2f;
                GL11.glTranslatef(0.22f, 0.35f, 0.0f);
                GL11.glRotatef(169.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(22.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(8.0f, 0.0f, 1.0f, 0.0f);
            }
            if (itemstack.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType()))) {
                float var6 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                var6 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(-var6, -var6, var6);
            }
            else if (itemstack.getItem() == Items.bow) {
                final float var6 = 0.625f;
                GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
                GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (itemstack.getItem().isFull3D()) {
                final float var6 = 0.625f;
                if (itemstack.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float var6 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef(var6, var6, var6);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            this.renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
            if (itemstack.getItem().requiresMultipleRenderPasses()) {
                for (int x = 1; x < itemstack.getItem().getRenderPasses(itemstack.getMetadata()); ++x) {
                    this.renderManager.itemRenderer.renderItem(entityliving, itemstack, x);
                }
            }
            GL11.glPopMatrix();
        }
    }
}
