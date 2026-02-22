package com.chocolate.chocolateQuest.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonIconOnOff extends GuiButtonIcon
{
    boolean isOn;
    
    public GuiButtonIconOnOff(final int id, final int posX, final int posY, final float xIndex, final float yIndex, final float xSize, final float ySize, final String s, final boolean isOn) {
        super(id, posX, posY, xIndex, yIndex, xSize, ySize, s);
        this.isOn = isOn;
        if (isOn) {
            ++this.yIndex;
        }
    }
    
    public void mouseReleased(final int x, final int y) {
        if (this.isOn) {
            --this.yIndex;
        }
        else {
            ++this.yIndex;
        }
        this.isOn = !this.isOn;
        super.mouseReleased(x, y);
    }
}
