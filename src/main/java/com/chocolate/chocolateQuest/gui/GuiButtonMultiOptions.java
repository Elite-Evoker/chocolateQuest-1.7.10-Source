package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonMultiOptions extends GuiButton
{
    public int value;
    String[] names;
    
    public GuiButtonMultiOptions(final int id, final int posX, final int posY, final int width, final int height, final String[] par5Str, final int value) {
        super(id, posX, posY, width, height, par5Str[value]);
        this.value = 0;
        this.names = par5Str;
        this.value = value;
    }
    
    public boolean mousePressed(final Minecraft mc, final int x, final int y) {
        if (super.mousePressed(mc, x, y)) {
            ++this.value;
            if (this.value >= this.names.length) {
                this.value = 0;
            }
            this.displayString = this.names[this.value];
            return true;
        }
        return false;
    }
}
