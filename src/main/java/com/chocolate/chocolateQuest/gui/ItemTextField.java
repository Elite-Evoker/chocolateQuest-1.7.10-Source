package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

class ItemTextField extends GuiTextField
{
    GuiButtonItemSearch gui;
    
    public ItemTextField(final FontRenderer font, final int x, final int y, final int width, final int height, final GuiButtonItemSearch holder) {
        super(font, x, y, width, height);
        this.gui = holder;
    }
    
    public boolean textboxKeyTyped(final char c, final int i) {
        final boolean b = super.textboxKeyTyped(c, i);
        this.gui.updateItem();
        return b;
    }
    
    public void setText(final String p_146180_1_) {
        super.setText(p_146180_1_);
    }
}
