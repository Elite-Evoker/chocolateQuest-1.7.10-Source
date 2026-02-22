package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import java.io.File;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScrollConversation extends GuiScrollOptions
{
    File file;
    public File[] files;
    
    public GuiScrollConversation(final int id, final int posX, final int posY, final int width, final int height, final String[] par5Str, final FontRenderer font, final int value) {
        super(id, posX, posY, width, height, par5Str, font, value);
    }
    
    @Override
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        super.drawButton(par1Minecraft, par2, par3);
        if (this.selectedMode < this.modeNames.length && this.selectedMode >= 0) {
            final int buttonHeight = this.height / this.MAX_ENTRIES;
            final int y = this.yPosition + buttonHeight * (this.selectedMode - this.scrollAmmount);
        }
    }
    
    @Override
    public void drawText(final Minecraft par1Minecraft, final int buttonHeight) {
        for (int i = 0; i < this.MAX_ENTRIES && this.scrollAmmount + i < this.modeNames.length; ++i) {
            final int y = this.yPosition + buttonHeight * i;
            int color = 16777215;
            if (i == this.selectedMode) {
                color = 16776960;
            }
            GuiNPC.drawTextWithSpecialChars(this.modeNames[this.scrollAmmount + i], this.xPosition + 5, y + buttonHeight / 2 - 5, this.font, color);
        }
    }
    
    @Override
    public void drawSelected(final int x, final int y) {
    }
}
