package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonTextBox extends GuiButton
{
    public GuiTextField textbox;
    protected FontRenderer fontRenderer;
    
    public GuiButtonTextBox(final int id, final int posX, final int posY, final int width, final int height, final FontRenderer font) {
        super(id, posX, posY, width, height, "");
        this.fontRenderer = font;
        this.textbox = new GuiTextField(font, posX, posY, width, height);
    }
    
    public void drawButton(final Minecraft mc, final int x, final int y) {
        super.drawButton(mc, x, y);
        this.textbox.xPosition = this.xPosition;
        this.textbox.yPosition = this.yPosition;
        this.textbox.drawTextBox();
    }
    
    public String getValue() {
        String s = this.textbox.getText();
        if (s.contains("-")) {
            final boolean negative = s.startsWith("-");
            s = s.replace("-", "");
            if (negative) {
                s = "-".concat(s);
            }
        }
        return s;
    }
    
    public void setText(final String text) {
        this.textbox.setText(text);
    }
}
