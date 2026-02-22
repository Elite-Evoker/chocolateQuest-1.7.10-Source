package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiScrollOptions extends GuiButton
{
    FontRenderer font;
    int selectedMode;
    public String[] modeNames;
    int scrollAmmount;
    boolean drag;
    int MAX_ENTRIES;
    int SCROLL_WIDTH;
    int MAX_ENTRIES_FINAL;
    
    public GuiScrollOptions(final int id, final int posX, final int posY, final int width, final int height, final FontRenderer font) {
        this(id, posX, posY, width, height, new String[] { "Empty" }, font, 0);
    }
    
    public GuiScrollOptions(final int id, final int posX, final int posY, final int width, final int height, final String[] par5Str, final FontRenderer font, final int value) {
        this(id, posX, posY, width, height, par5Str, font, value, 4);
    }
    
    public GuiScrollOptions(final int id, final int posX, final int posY, final int width, final int height, final String[] par5Str, final FontRenderer font, final int value, final int maxEntries) {
        super(id, posX, posY, width, height, "");
        this.selectedMode = 0;
        this.drag = false;
        this.MAX_ENTRIES = 4;
        this.SCROLL_WIDTH = 8;
        this.MAX_ENTRIES_FINAL = 4;
        this.font = font;
        this.selectedMode = value;
        this.MAX_ENTRIES_FINAL = maxEntries;
        this.setModeNames(par5Str);
    }
    
    public void setModeNames(final String[] modeNames) {
        this.modeNames = modeNames;
        this.MAX_ENTRIES = Math.min(Math.max(1, modeNames.length), this.MAX_ENTRIES_FINAL);
        if (this.selectedMode > this.MAX_ENTRIES) {
            this.selectedMode = -1;
        }
        this.scrollAmmount = 0;
    }
    
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.enabled) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
            final int buttonHeight = this.height / this.MAX_ENTRIES;
            for (int i = 0; i < this.MAX_ENTRIES; ++i) {
                final int y = this.yPosition + buttonHeight * i;
                this.drawTexturedRect(this.xPosition, y, this.width, buttonHeight, 6.0f, 0.0f, 3.0f, 1.0f);
            }
            final int currentPosition = this.selectedMode - this.scrollAmmount;
            if (currentPosition >= 0 && currentPosition < this.MAX_ENTRIES) {
                final int currentX = this.xPosition;
                final int currentY = this.yPosition + buttonHeight * currentPosition;
                this.drawSelected(currentX, currentY);
            }
            if (this.modeNames.length - this.MAX_ENTRIES > 0) {
                this.drawTexturedRect(this.xPosition + this.width - this.SCROLL_WIDTH, this.yPosition, this.SCROLL_WIDTH, this.height, 15.5f, 2.0f, 0.5f, 4.0f);
                final int scrollY = this.scrollAmmount * (this.height - this.SCROLL_WIDTH) / (this.modeNames.length - this.MAX_ENTRIES);
                this.drawTexturedRect(this.xPosition + this.width - this.SCROLL_WIDTH, this.yPosition + scrollY, this.SCROLL_WIDTH, this.SCROLL_WIDTH, 15.0f, 2.0f, 0.5f, 0.5f);
            }
            this.drawText(null, buttonHeight);
            this.mouseDragged(par1Minecraft, par2, par3);
        }
    }
    
    public void drawText(final Minecraft par1Minecraft, final int buttonHeight) {
        for (int i = 0; i < this.MAX_ENTRIES && this.scrollAmmount + i < this.modeNames.length; ++i) {
            final int y = this.yPosition + buttonHeight * i;
            this.drawString(this.font, this.modeNames[this.scrollAmmount + i], this.xPosition + 5, y + buttonHeight / 2 - 5, 16777215);
        }
    }
    
    public void drawSelected(final int x, final int y) {
        final int buttonHeight = this.height / this.MAX_ENTRIES;
        this.drawTexturedRect(x, y, this.width, buttonHeight, 6.0f, 1.0f, 3.0f, 1.0f);
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
    
    protected void mouseDragged(final Minecraft par1Minecraft, final int x, final int y) {
        if (this.drag && this.enabled && this.visible && x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height) {
            this.handleMouseScroll(x, y);
        }
        else {
            this.drag = false;
        }
        super.mouseDragged(par1Minecraft, x, y);
    }
    
    public void mouseReleased(final int x, final int y) {
        this.drag = false;
    }
    
    public boolean mousePressed(final Minecraft par1Minecraft, final int x, final int y) {
        if (!super.mousePressed(par1Minecraft, x, y)) {
            return false;
        }
        if (this.handleMouseScroll(x, y)) {
            return false;
        }
        this.selectedMode = (y - this.yPosition) * this.MAX_ENTRIES / this.height + this.scrollAmmount;
        return true;
    }
    
    public boolean handleMouseScroll(final int x, final int y) {
        if (x - this.xPosition > this.width - this.SCROLL_WIDTH) {
            this.scrollAmmount = (y - this.yPosition) * (this.modeNames.length - this.MAX_ENTRIES + 1) / this.height;
            return true;
        }
        return false;
    }
    
    public void scrollDown() {
        if (this.selectedMode < this.modeNames.length - 1) {
            ++this.selectedMode;
            if (this.selectedMode - this.MAX_ENTRIES >= this.scrollAmmount) {
                ++this.scrollAmmount;
            }
        }
    }
    
    public void scrollUp() {
        if (this.selectedMode > 0) {
            --this.selectedMode;
            if (this.selectedMode < this.scrollAmmount) {
                --this.scrollAmmount;
            }
        }
    }
    
    public String getSelected() {
        return this.modeNames[this.selectedMode];
    }
}
