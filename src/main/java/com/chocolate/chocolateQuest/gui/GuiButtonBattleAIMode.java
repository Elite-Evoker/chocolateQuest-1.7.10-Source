package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonBattleAIMode extends GuiButtonAIMode
{
    public GuiButtonBattleAIMode(final int id, final int posX, final int posY, final int width, final int height, final String[] par5Str, final FontRenderer font, final int value) {
        super(id, posX, posY, width, height, par5Str, font, value);
    }
    
    @Override
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        final int buttonHeight = this.height / this.modeNames.length;
        for (int i = 0; i < this.modeNames.length; ++i) {
            this.isMouseOver(i, par2, par3);
            final int y = this.yPosition + buttonHeight * i;
            this.drawTexturedRect(this.xPosition, y, this.width, buttonHeight, 5, i, 1, 1);
        }
        this.drawTexturedRect(this.xPosition, this.yPosition + buttonHeight * this.selectedMode, this.width, buttonHeight, 4, 1, 1, 1);
    }
}
