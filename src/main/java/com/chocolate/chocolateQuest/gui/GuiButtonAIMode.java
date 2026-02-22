package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonAIMode extends GuiButton
{
    FontRenderer font;
    int selectedMode;
    String[] modeNames;
    
    public GuiButtonAIMode(final int id, final int posX, final int posY, final int width, final int height, final String[] par5Str, final FontRenderer font, final int value) {
        super(id, posX, posY, width, height, "");
        this.selectedMode = 0;
        this.displayString = "";
        this.font = font;
        this.modeNames = par5Str;
        this.selectedMode = value;
    }
    
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        final int buttonHeight = this.height / this.modeNames.length;
        for (int i = 0; i < this.modeNames.length; ++i) {
            final int y = this.yPosition + buttonHeight * i;
            this.isMouseOver(i, par2, par3);
            this.drawTexturedRect(this.xPosition, y, this.width, buttonHeight, 6, 0, 3, 1);
        }
        this.drawTexturedRect(this.xPosition, this.yPosition + buttonHeight * this.selectedMode, this.width, buttonHeight, 6, 1, 3, 1);
        for (int i = 0; i < this.modeNames.length; ++i) {
            final int y = this.yPosition + buttonHeight * i;
            this.drawString(this.font, this.modeNames[i], this.xPosition + 5, y + buttonHeight / 2 - 5, 16777215);
        }
    }
    
    public void drawTexturedRect(final int x, final int y, final int size, final int height, final int indexX, final int indexZ, final int iconWidth, final int iconHeight) {
        final float f = 0.0625f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)(y + height), (double)this.zLevel, (double)((indexX + 0) * f), (double)((indexZ + iconHeight) * f));
        tessellator.addVertexWithUV((double)(x + size), (double)(y + height), (double)this.zLevel, (double)((indexX + iconWidth) * f), (double)((indexZ + iconHeight) * f));
        tessellator.addVertexWithUV((double)(x + size), (double)(y + 0), (double)this.zLevel, (double)((indexX + iconWidth) * f), (double)((indexZ + 0) * f));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((indexX + 0) * f), (double)((indexZ + 0) * f));
        tessellator.draw();
    }
    
    public boolean mousePressed(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.selectedMode = (par3 - this.yPosition) * this.modeNames.length / this.height;
            return true;
        }
        return false;
    }
    
    public boolean isMouseOver(final int selectedMode, final int x, final int y) {
        if (x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height && selectedMode == (y - this.yPosition) * this.modeNames.length / this.height) {
            GL11.glColor4f(0.9f, 0.9f, 1.0f, 1.0f);
        }
        else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        return false;
    }
}
