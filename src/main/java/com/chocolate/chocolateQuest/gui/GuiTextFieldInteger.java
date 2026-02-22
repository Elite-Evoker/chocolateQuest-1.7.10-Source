package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiTextFieldInteger extends GuiTextField
{
    public GuiTextFieldInteger(final FontRenderer font, final int x, final int y, final int width, final int height) {
        super(font, x, y, width, height);
    }
    
    public boolean textboxKeyTyped(final char c, final int i) {
        return (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '-' || i == 14 || i == 203 || i == 205 || i == 201) && super.textboxKeyTyped(c, i);
    }
}
