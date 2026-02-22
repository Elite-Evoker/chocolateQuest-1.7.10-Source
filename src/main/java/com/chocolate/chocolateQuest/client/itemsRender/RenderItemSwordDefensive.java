package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.ItemRenderer;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSwordDefensive;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemSwordDefensive extends RenderItemBase implements IItemRenderer
{
    @Override
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY;
    }
    
    @Override
    protected void renderInventory(final ItemStack itemstack) {
        GL11.glScalef(16.0f, 16.0f, 0.0f);
        final Tessellator tessellator = Tessellator.instance;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        IIcon icon = ChocolateQuest.shield.getIconFromDamage(((ItemBaseSwordDefensive)itemstack.getItem()).getShieldID(itemstack));
        ItemRenderer.renderItemIn2D(tessellator, icon.getMinU(), icon.getMaxV(), icon.getMaxU(), icon.getMinV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
        icon = itemstack.getIconIndex();
        ItemRenderer.renderItemIn2D(tessellator, icon.getMinU(), icon.getMaxV(), icon.getMaxU(), icon.getMinV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
    }
    
    @Override
    protected void renderFirstPerson(final EntityLivingBase player, final ItemStack itemstack) {
        RenderItemBase.doRenderItem(itemstack);
        if (((EntityPlayer)player).isBlocking()) {
            GL11.glLoadIdentity();
            GL11.glTranslatef(-1.5f, -1.0f, -1.0f);
            final ItemStack shield = new ItemStack(ChocolateQuest.shield, 1, ((ItemBaseSwordDefensive)itemstack.getItem()).getShieldID(itemstack));
            RenderItemBase.doRenderItem(shield);
            Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
            final int spriteIndex = shield.getMetadata();
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
        }
    }
    
    @Override
    protected void renderAsEntity(final ItemStack is) {
        RenderItemBase.doRenderItem(is);
        final int shieldID = ((ItemBaseSwordDefensive)is.getItem()).getShieldID(is);
        GL11.glTranslatef(0.0f, 0.0f, -0.07f);
        RenderItemShield.doRenderShield(new ItemStack(ChocolateQuest.shield, 1, shieldID));
    }
}
