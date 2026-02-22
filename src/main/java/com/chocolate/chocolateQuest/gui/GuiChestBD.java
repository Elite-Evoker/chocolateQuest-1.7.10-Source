package com.chocolate.chocolateQuest.gui;

import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiChestBD extends GuiContainer
{
    private static final ResourceLocation texture;
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    private int inventoryRows;
    
    public GuiChestBD(final IInventory playerInventory, final IInventory chestInventory) {
        super((Container)new ContainerBDChest(playerInventory, chestInventory));
        this.inventoryRows = 0;
        this.upperChestInventory = chestInventory;
        this.lowerChestInventory = playerInventory;
        this.allowUserInput = false;
        final short var3 = 222;
        final int var4 = var3 - 108;
        this.inventoryRows = chestInventory.getSizeInventory() / 9;
        this.ySize = var4 + this.inventoryRows * 18;
    }
    
    protected void drawGuiContainerForegroundLayer() {
        this.fontRendererObj.drawString(StatCollector.translateToLocal(this.lowerChestInventory.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInventoryName()), 8, this.ySize - 96 + 2, 4210752);
    }
    
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(GuiChestBD.texture);
        final int width = (this.width - this.xSize) / 2;
        final int height = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(width, height, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(width, height + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
    
    static {
        texture = new ResourceLocation("textures/gui/container/generic_54.png");
    }
}
