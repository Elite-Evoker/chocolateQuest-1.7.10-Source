package com.chocolate.chocolateQuest.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;

public class GuiArmorStand extends GuiHumanBase
{
    public GuiArmorStand(final BlockArmorStandTileEntity te, final EntityPlayer player, final IInventory par1IInventory) {
        super(new ContainerArmorStand((IInventory)player.inventory, par1IInventory), par1IInventory, player);
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
}
