package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class GuiHumanBase extends GuiPlayerInventory
{
    public GuiHumanBase(final ContainerBDChest container, final IInventory par1IInventory, final EntityPlayer playerInventory) {
        super(container, par1IInventory, playerInventory);
    }
    
    public GuiHumanBase(final IInventory par1IInventory, final EntityPlayer player) {
        this(new ContainerHumanInventory((IInventory)player.inventory, par1IInventory), par1IInventory, player);
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        this.drawEquipementPanel();
    }
    
    protected void drawEquipementPanel() {
        this.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        final int width = (this.width - this.xSize) / 2;
        final int height = this.height - this.height / 2 - 86;
        this.drawTexturedModalRect(width - 6, height - 2, 0, 64, 64, 80);
    }
    
    public static void drawEntity(final EntityLivingBase human, final int x, final int y) {
        drawEntity(human, x, y, 23.0f);
    }
    
    public static void drawEntity(final EntityLivingBase human, final int x, final int y, final float scale) {
        GL11.glPushMatrix();
        RenderHelper.enableStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glTranslatef((float)x, (float)y, 0.0f);
        GL11.glScalef(scale, -scale, scale);
        final float prevYaw = human.rotationYaw;
        final float prevYawHead = human.rotationYawHead;
        final float prevYawOffset = human.renderYawOffset;
        human.rotationYaw = 0.0f;
        human.rotationYawHead = 0.0f;
        human.renderYawOffset = 0.0f;
        RenderManager.instance.renderEntityWithPosYaw((Entity)human, 0.0, 0.0, 5.0, 0.0f, 1.0f);
        human.rotationYaw = prevYaw;
        human.rotationYawHead = prevYawHead;
        human.renderYawOffset = prevYawOffset;
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glPopMatrix();
    }
}
