package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonDisplayString extends GuiButton
{
    final int color;
    
    public GuiButtonDisplayString(final int id, final int posX, final int posY, final int width, final int height, final String par5Str) {
        this(id, posX, posY, width, height, par5Str, 16777079);
    }
    
    public GuiButtonDisplayString(final int id, final int posX, final int posY, final int width, final int height, final String par5Str, final int color) {
        super(id, posX, posY, width, height, par5Str);
        this.color = color;
    }
    
    public void drawButton(final Minecraft mc, final int par2, final int par3) {
        this.drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, this.color);
    }
}
