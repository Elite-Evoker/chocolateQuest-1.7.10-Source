package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.ChocolateQuest;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.GuiScreen;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;

public class GuiButtonSoulBottleSearch extends GuiButtonTextBox
{
    GuiScreen holder;
    ItemStack[] cachedBottles;
    String displayName;
    int textBoxHeight;
    public NBTTagCompound entityTag;
    
    public GuiButtonSoulBottleSearch(final int id, final int x, final int y, final int width, final int height, final FontRenderer font) {
        super(id, x, y, width, height, font);
        this.displayName = "";
        this.textBoxHeight = 16;
        this.updateItems();
        this.textbox.setEnabled(false);
        this.textbox.setVisible(false);
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int x, final int y) {
        int despX = 0;
        int despY = 0;
        drawRect(this.xPosition - 1, this.yPosition + despY + this.textBoxHeight, this.xPosition + this.width + 1, this.yPosition + despY + this.textBoxHeight + this.height, -7829368);
        drawRect(this.xPosition + despX - 1, this.yPosition, this.xPosition + this.width + 1, this.yPosition + this.textBoxHeight, -16777216);
        this.fontRenderer.drawString(this.displayName, this.xPosition + 5, this.yPosition + this.fontRenderer.FONT_HEIGHT / 2, 16777215);
        if (this.cachedBottles != null) {
            for (int i = 0; i < this.cachedBottles.length; ++i) {
                if (this.cachedBottles[i] != null) {
                    final ItemStack cargoItem = this.cachedBottles[i];
                    final float scale = 16.0f;
                    final RenderItem r = new RenderItem();
                    r.renderItemIntoGUI(this.fontRenderer, mc.getTextureManager(), cargoItem, this.xPosition + despX, this.yPosition + despY + 16);
                    despX += (int)scale;
                    if (despX + this.textBoxHeight > this.width) {
                        despX = 0;
                        despY += (int)scale;
                        if (despY + this.textBoxHeight > this.height) {
                            break;
                        }
                    }
                }
            }
        }
        GL11.glDisable(2896);
    }
    
    public boolean mousePressed(final Minecraft mc, final int x, final int y) {
        final boolean b = super.mousePressed(mc, x, y);
        if (b) {
            final ItemStack is = this.getCurrentItem(x, y);
            if (is != null) {
                this.displayName = is.stackTagCompound.getString("itemName");
                this.textbox.setText(this.displayName);
                this.entityTag = is.stackTagCompound;
            }
        }
        return b;
    }
    
    protected void updateItems() {
        final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
        final IInventory inventory = (IInventory)player.inventory;
        int countBottles = 0;
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            final ItemStack is = inventory.getStackInSlot(i);
            if (is != null && is.getItem() == ChocolateQuest.soulBottle && is.stackTagCompound != null) {
                ++countBottles;
            }
        }
        int currentBottle = 0;
        this.cachedBottles = new ItemStack[countBottles];
        for (int j = 0; j < inventory.getSizeInventory(); ++j) {
            final ItemStack is2 = inventory.getStackInSlot(j);
            if (is2 != null && is2.getItem() == ChocolateQuest.soulBottle && is2.stackTagCompound != null) {
                this.cachedBottles[currentBottle++] = is2;
            }
        }
    }
    
    public ItemStack getCurrentItem(int i, int j) {
        i -= this.xPosition;
        j -= this.yPosition + this.textBoxHeight;
        if (this.cachedBottles != null && j >= 0) {
            final int x = i / 16;
            final int y = j / 16;
            final int index = y * (this.width / 16) + x;
            if (index < this.cachedBottles.length) {
                return this.cachedBottles[index];
            }
        }
        return null;
    }
    
    @Override
    public String getValue() {
        return this.textbox.getText();
    }
}
