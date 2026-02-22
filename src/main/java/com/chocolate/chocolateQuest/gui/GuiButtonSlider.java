package com.chocolate.chocolateQuest.gui;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonSlider extends GuiButton
{
    int SLIDER_MAX_VALUE;
    public float sliderValue;
    public boolean dragging;
    boolean isInteger;
    String name;
    
    public GuiButtonSlider(final int par1, final int par2, final int par3, final String par5Str, final float par6) {
        this(par1, par2, par3, par5Str, par6, 10);
    }
    
    public GuiButtonSlider(final int par1, final int par2, final int par3, final String par5Str, final float par6, final int maxValue) {
        this(par1, par2, par3, par5Str, par6, maxValue, false);
    }
    
    public GuiButtonSlider(final int par1, final int par2, final int par3, final String par5Str, final float par6, final int maxValue, final boolean isInteger) {
        super(par1, par2, par3, 108, 20, par5Str);
        this.SLIDER_MAX_VALUE = 10;
        this.sliderValue = 1.0f;
        this.isInteger = false;
        this.sliderValue = par6;
        this.SLIDER_MAX_VALUE = maxValue;
        this.name = par5Str;
        this.isInteger = isInteger;
        final float value = this.sliderValue * this.SLIDER_MAX_VALUE;
        this.displayString = this.name + ": " + (isInteger ? ((float)(int)Math.ceil(value)) : value);
    }
    
    protected void mouseDragged(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.enabled) {
            if (this.dragging) {
                this.sliderValue = (par2 - (this.xPosition + 4)) / (float)(this.width - 8);
                if (this.sliderValue < 0.01f) {
                    this.sliderValue = 0.01f;
                }
                if (this.sliderValue > 1.0f) {
                    this.sliderValue = 1.0f;
                }
                final float value = this.sliderValue * this.SLIDER_MAX_VALUE;
                this.displayString = this.name + ": " + (this.isInteger ? ((float)(int)Math.ceil(value)) : value);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    public boolean mousePressed(final Minecraft par1Minecraft, final int x, final int y) {
        if (super.mousePressed(par1Minecraft, x, y)) {
            this.sliderValue = (x - (this.xPosition + 4)) / (float)(this.width - 8);
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            this.dragging = !this.dragging;
            return true;
        }
        return false;
    }
    
    public void mouseReleased(final int x, final int y) {
        this.dragging = false;
        super.mouseReleased(x, y);
    }
}
