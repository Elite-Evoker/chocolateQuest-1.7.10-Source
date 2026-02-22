package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonTextBoxList extends GuiButton
{
    public GuiTextField[] textbox;
    FontRenderer fontRenderer;
    int scroll;
    List<String> values;
    final int TEXT_BOXES = 4;
    
    public GuiButtonTextBoxList(final int id, final int posX, final int posY, final int width, final int height, final FontRenderer font, final String[] values) {
        super(id, posX, posY, width, height, "");
        this.scroll = 0;
        this.values = new ArrayList<String>();
        this.fontRenderer = font;
        this.textbox = new GuiTextField[4];
        for (int i = 0; i < this.textbox.length; ++i) {
            (this.textbox[i] = new TextField(font, posX, posY + i * 20, width, height / 4, this)).setMaxStringLength(128);
        }
        this.setValues(values);
    }
    
    public void drawButton(final Minecraft mc, final int x, final int y) {
    }
    
    public String[] getValues() {
        for (int last = this.values.size(); last > 1 && this.values.get(last - 1).isEmpty(); last = this.values.size()) {
            this.values.remove(last - 1);
        }
        if (this.values.size() == 0) {
            return new String[] { "Nothing to tell you" };
        }
        final String[] s = new String[this.values.size()];
        for (int i = 0; i < s.length; ++i) {
            s[i] = this.values.get(i);
        }
        return s;
    }
    
    public void setValues(String[] text) {
        if (text == null) {
            text = new String[] { "" };
        }
        this.values.clear();
        for (final String s : text) {
            this.values.add(s);
        }
        this.setTextBoxValues();
    }
    
    public void setTextBoxValues() {
        for (int i = 0; i < this.textbox.length; ++i) {
            String s = "";
            if (this.scroll + i < this.values.size()) {
                s = this.values.get(this.scroll + i);
            }
            if (s == null) {
                s = "";
            }
            this.textbox[i].setText(s);
        }
    }
    
    public void updateValues() {
        for (int i = 0; i < 4; ++i) {
            if (this.values.size() > this.scroll + i) {
                this.values.set(this.scroll + i, this.textbox[i].getText());
            }
            else {
                this.values.add(this.textbox[i].getText());
            }
        }
    }
    
    public boolean mousePressed(final Minecraft mc, final int x, final int y) {
        if (super.mousePressed(mc, x, y)) {
            this.updateValues();
            return true;
        }
        return false;
    }
    
    public void scrollUp() {
        if (this.scroll > 0) {
            --this.scroll;
        }
        this.setTextBoxValues();
    }
    
    public void scrollDown() {
        ++this.scroll;
        if (this.values.size() > this.scroll) {
            this.values.add("");
        }
        this.setTextBoxValues();
    }
}
