package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.FontRenderer;
import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonAwakements extends GuiButton
{
    int xpRequired;
    
    public GuiButtonAwakements(final int id, final int posX, final int posY, final int width, final int height, final String s) {
        super(id, posX, posY, width, height, s);
        this.xpRequired = 0;
    }
    
    public void drawButton(final Minecraft par1Minecraft, final int x, final int y) {
        final FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
        GL11.glColor4f(1.0f, 0.9f, 0.6f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        int textColor = 16777215;
        if (!this.enabled) {
            GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
            textColor = 8947797;
        }
        else {
            final boolean mouseOver = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            if (mouseOver) {
                GL11.glColor4f(1.0f, 0.8f, 0.9f, 1.0f);
                textColor = 16777079;
            }
        }
        this.drawTexturedRect(this.xPosition, this.yPosition, this.width, this.height, 6, 0, 3, 1);
        this.drawString(font, this.displayString, this.xPosition + 2, this.yPosition + 1, textColor);
        if (this.enabled || this.xpRequired > 0) {
            final String ammount = "" + this.xpRequired;
            final int fontWidth = font.getStringWidth(ammount);
            this.drawString(font, ammount, this.xPosition + this.width - fontWidth - 1, this.yPosition + this.height - font.FONT_HEIGHT, 65280);
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
    
    public void disable() {
        this.displayString = "";
        this.xpRequired = 0;
        this.enabled = false;
    }
}
