package com.chocolate.chocolateQuest.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotLocked extends Slot
{
    public SlotLocked(final IInventory par1iInventory, final int par2, final int par3, final int par4) {
        super(par1iInventory, par2, par3, par4);
    }
    
    public boolean isItemValid(final ItemStack is) {
        return false;
    }
    
    public boolean canTakeStack(final EntityPlayer p_82869_1_) {
        return false;
    }
}
