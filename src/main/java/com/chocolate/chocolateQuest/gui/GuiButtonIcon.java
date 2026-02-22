package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonIcon extends GuiButton
{
    float xIndex;
    float yIndex;
    float xSize;
    float ySize;
    
    public GuiButtonIcon(final int id, final int posX, final int posY, final float xIndex, final float yIndex, final float xSize, final float ySize, final String s) {
        super(id, posX, posY, (int)(xSize * 16.0f), (int)(ySize * 16.0f), s);
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.xSize = xSize;
        this.ySize = ySize;
    }
    
    public void drawButton(final Minecraft par1Minecraft, final int x, final int y) {
        if (this.visible) {
            final FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
            Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
            int textColor = 16777215;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean mouseOver = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            if (mouseOver) {
                GL11.glColor4f(0.9f, 0.9f, 1.0f, 1.0f);
                textColor = 16777079;
            }
            this.drawTexturedRect(this.xPosition, this.yPosition, this.width, this.height, this.xIndex, this.yIndex, this.xSize, this.ySize);
            this.drawString(font, this.displayString, this.xPosition + 16, this.yPosition + 4, textColor);
        }
    }
    
    public void drawTexturedRect(final int x, final int y, final int size, final int height, final float indexX, final float indexZ, final float iconWidth, final float iconHeight) {
        final float f = 0.0625f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)(y + height), (double)this.zLevel, (double)((indexX + 0.0f) * f), (double)((indexZ + iconHeight) * f));
        tessellator.addVertexWithUV((double)(x + size), (double)(y + height), (double)this.zLevel, (double)((indexX + iconWidth) * f), (double)((indexZ + iconHeight) * f));
        tessellator.addVertexWithUV((double)(x + size), (double)(y + 0), (double)this.zLevel, (double)((indexX + iconWidth) * f), (double)((indexZ + 0.0f) * f));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((indexX + 0.0f) * f), (double)((indexZ + 0.0f) * f));
        tessellator.draw();
    }
}
