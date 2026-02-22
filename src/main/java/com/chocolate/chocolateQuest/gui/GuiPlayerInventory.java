package com.chocolate.chocolateQuest.gui;

import net.minecraft.inventory.Slot;
import org.lwjgl.opengl.GL11;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiPlayerInventory extends GuiContainer
{
    protected static final int ICON_SIZE = 16;
    protected static final int ICONS_PER_ROW = 16;
    private static final ResourceLocation texture;
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    protected EntityPlayer player;
    
    public GuiPlayerInventory(final ContainerBDChest container, final IInventory par1IInventory, final EntityPlayer player) {
        super((Container)container);
        this.player = player;
        this.upperChestInventory = par1IInventory;
        this.lowerChestInventory = (IInventory)player.inventory;
        this.allowUserInput = false;
        final short var3 = 222;
        final int var4 = var3 - 108;
        final int inventoryRows = this.lowerChestInventory.getSizeInventory() / 9;
        this.ySize = var4 + inventoryRows * 18 + 28;
    }
    
    protected void drawGuiContainerBackgroundLayer(final float var1, final int var2, final int var3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(GuiPlayerInventory.texture);
        final int width = (this.width - this.xSize) / 2;
        final int height = (this.height - this.ySize) / 2;
        final int offset = this.getPlayerInventoryOffset();
        this.drawTexturedModalRect(width, height + offset - 3, 0, 0, this.xSize, 17);
        this.drawTexturedModalRect(width, height + 0 + offset, 0, 126, this.xSize, 96);
    }
    
    public int getPlayerInventoryOffset() {
        return 104;
    }
    
    public void initGui() {
        super.initGui();
    }
    
    protected void handleMouseClick(final Slot slot, final int slotID, final int x, final int y) {
        super.handleMouseClick(slot, slotID, x, y);
    }
    
    public void drawIcon(final int icon, final int xPos, final int yPos) {
        this.drawTexturedModalRect(xPos, yPos, icon % 16 * 16, icon / 16 * 16, 16, 16);
    }
    
    static {
        texture = new ResourceLocation("textures/gui/container/generic_54.png");
    }
}
