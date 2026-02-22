package com.chocolate.chocolateQuest.gui;

import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.item.Item;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.GuiScreen;

public class GuiButtonItemSearch extends GuiButtonTextBox
{
    GuiScreen holder;
    ItemStack[] is;
    String currentName;
    int textBoxHeight;
    
    public GuiButtonItemSearch(final int id, final int x, final int y, final int width, final int height, final FontRenderer font) {
        super(id, x, y, width, height, font);
        this.currentName = "";
        this.textBoxHeight = 18;
        (this.textbox = new ItemTextField(font, x, y, width, 20, this)).setMaxStringLength(255);
        final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
        final IInventory inventory = (IInventory)player.inventory;
        int countBottles = 0;
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            final ItemStack is = inventory.getStackInSlot(i);
            if (is != null) {
                ++countBottles;
            }
        }
        int currentBottle = 0;
        this.is = new ItemStack[countBottles];
        for (int j = 0; j < inventory.getSizeInventory(); ++j) {
            final ItemStack is2 = inventory.getStackInSlot(j);
            if (is2 != null) {
                this.is[currentBottle++] = is2;
            }
        }
        this.height += this.textBoxHeight;
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int x, final int y) {
        int despX = 0;
        int despY = 0;
        drawRect(this.xPosition + despX - 1, this.yPosition + despY + this.textBoxHeight, this.xPosition + despX + this.width + 1, this.yPosition + despY + this.height, -7829368);
        if (this.is != null) {
            for (int i = 0; i < this.is.length; ++i) {
                if (this.is[i] != null) {
                    final ItemStack cargoItem = this.is[i];
                    final float scale = 16.0f;
                    final RenderItem r = new RenderItem();
                    r.renderItemAndEffectIntoGUI(this.fontRenderer, mc.getTextureManager(), cargoItem, this.xPosition + despX, this.yPosition + despY + 16);
                    despX += (int)scale;
                    if (despX + this.textBoxHeight > this.width) {
                        despX = 0;
                        despY += (int)scale;
                        if (despY > this.height) {
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
                String text = Item.itemRegistry.getNameForObject((Object)is.getItem());
                if (is.stackTagCompound != null) {
                    text = text + " " + is.stackSize + " " + is.getMetadata() + " " + is.stackTagCompound.toString();
                }
                else if (is.getMetadata() > 0 || is.stackSize > 1) {
                    text = text + " " + is.stackSize + " " + is.getMetadata();
                }
                this.textbox.setText(text);
            }
        }
        return b;
    }
    
    protected void updateItem() {
        final String itemText = this.textbox.getText();
        final String[] textArray = itemText.split(" ");
        if (textArray.length > 1) {
            final ItemStack stack = BDHelper.getStackFromString(itemText);
            if (stack != null) {
                this.is = new ItemStack[] { stack };
            }
        }
        else if (textArray.length > 0) {
            final String name = textArray[0];
            final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            final Set registeredItems = Item.itemRegistry.getKeys();
            for (final Object o : registeredItems) {
                if (o.toString().contains(name)) {
                    final Item currentItem = (Item)Item.itemRegistry.getObject(o);
                    currentItem.getSubItems(currentItem, currentItem.getCreativeTab(), (List)list);
                }
            }
            this.is = new ItemStack[list.size()];
            for (int index = 0; index < list.size(); ++index) {
                this.is[index] = list.get(index);
            }
        }
    }
    
    public ItemStack getCurrentItem(int i, int j) {
        i -= this.xPosition;
        j -= this.yPosition + this.textBoxHeight;
        if (this.is != null && j >= 0) {
            final int x = i / 16;
            final int y = j / 16;
            final int index = y * (this.width / 16) + x;
            if (index < this.is.length) {
                return this.is[index];
            }
        }
        return null;
    }
}
