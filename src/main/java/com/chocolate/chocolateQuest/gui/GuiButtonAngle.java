package com.chocolate.chocolateQuest.gui;

import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonAngle extends GuiButton
{
    public boolean dragging;
    double xPoint;
    double yPoint;
    EntityHumanBase human;
    final int maxRadio = 10;
    
    public GuiButtonAngle(final int par1, final int par2, final int par3, final String par5Str, final float par6, final EntityHumanBase human) {
        super(par1, par2, par3, 80, 80, par5Str);
        this.xPoint = 0.0;
        this.yPoint = 0.0;
        this.displayString = "";
        this.human = human;
        this.setCoords(human.partyPositionAngle);
    }
    
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        super.drawButton(par1Minecraft, par2, par3);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        this.drawTexturedRect(this.xPosition, this.yPosition, 0, 0, this.width, 4);
        int radius = this.width / 2;
        final int iconSize = (int)(radius * 0.2);
        radius -= iconSize / 2;
        double x = this.xPoint;
        double y = this.yPoint;
        x = this.xPosition + radius - x * radius / 10.0;
        y = this.yPosition + radius - y * radius / 10.0;
        this.drawTexturedRect((int)x, (int)y, 4, 0, iconSize);
    }
    
    public int getAngle() {
        return (int)(Math.atan2(this.yPoint, this.xPoint) * 180.0 / 3.141592653589793 - 90.0);
    }
    
    public int getDistance() {
        return (int)Math.max(1.0, Math.sqrt(this.xPoint * this.xPoint + this.yPoint * this.yPoint));
    }
    
    public void setCoords(int angle) {
        angle += 90;
        final double fangle = angle * 3.141592653589793 / 180.0;
        final int dist = this.human.partyDistanceToLeader;
        this.xPoint = Math.cos(fangle) * dist;
        this.yPoint = Math.sin(fangle) * dist;
    }
    
    protected void mouseDragged(final Minecraft par1Minecraft, final int par2, final int par3) {
        this.displayString = "";
        final int radius = this.width / 2;
        if (this.dragging) {
            final float x = (this.xPosition + radius - par2) * 10.0f / radius;
            final float y = (this.yPosition + radius - par3) * 10.0f / radius;
            this.xPoint = (int)Math.min(10.0f, Math.max(-10.0f, x));
            this.yPoint = (int)Math.min(10.0f, Math.max(-10.0f, y));
        }
    }
    
    public void drawTexturedRect(final int x, final int y, final int indexX, final int indexZ, final int size, final int iconSize) {
        final float f = 0.0625f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)(y + size), (double)this.zLevel, (double)((indexX + 0) * f), (double)((indexZ + iconSize) * f));
        tessellator.addVertexWithUV((double)(x + size), (double)(y + size), (double)this.zLevel, (double)((indexX + iconSize) * f), (double)((indexZ + iconSize) * f));
        tessellator.addVertexWithUV((double)(x + size), (double)(y + 0), (double)this.zLevel, (double)((indexX + iconSize) * f), (double)((indexZ + 0) * f));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((indexX + 0) * f), (double)((indexZ + 0) * f));
        tessellator.draw();
    }
    
    public void drawTexturedRect(final int x, final int y, final int indexX, final int indexZ, final int size) {
        this.drawTexturedRect(x, y, indexX, indexZ, size, 1);
    }
    
    public boolean mousePressed(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.dragging = !this.dragging;
            return true;
        }
        return false;
    }
    
    public void mouseReleased(final int par1, final int par2) {
        this.dragging = false;
    }
}
