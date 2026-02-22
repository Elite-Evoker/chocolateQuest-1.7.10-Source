package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonTextBoxInteger extends GuiButtonTextBox
{
    public GuiButtonTextBoxInteger(final int id, final int posX, final int posY, final int width, final int height, final FontRenderer font) {
        super(id, posX, posY, width, height, font);
        this.textbox = new GuiTextFieldInteger(font, posX, posY, width, height);
    }
    
    public GuiButtonTextBoxInteger(final int id, final int posX, final int posY, final int width, final int height, final FontRenderer font, final int defaultValue) {
        this(id, posX, posY, width, height, font);
        this.textbox.setText(defaultValue + "");
    }
}
