package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class GuiGolem extends GuiHuman
{
    public GuiGolem(final EntityHumanBase human, final IInventory par1IInventory, final EntityPlayer playerInventory) {
        super(new ContainerGolemInventory((IInventory)playerInventory.inventory, par1IInventory), human, par1IInventory, playerInventory);
    }
    
    @Override
    protected void drawEquipementPanel() {
        this.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        final int width = (this.width - this.xSize) / 2;
        final int height = this.height - this.height / 2 - 86;
        this.drawTexturedModalRect(width - 6, height - 3, 0, 144, 64, 80);
    }
}
