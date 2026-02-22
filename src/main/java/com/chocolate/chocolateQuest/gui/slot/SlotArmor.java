package com.chocolate.chocolateQuest.gui.slot;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotArmor extends Slot
{
    int armorSlot;
    
    public SlotArmor(final IInventory par1iInventory, final int par2, final int par3, final int par4, final int armorSlot) {
        super(par1iInventory, par2, par3, par4);
        this.armorSlot = armorSlot;
    }
    
    public boolean isItemValid(final ItemStack par1ItemStack) {
        if (this.armorSlot == 0) {
            return par1ItemStack.getItem().isValidArmor(par1ItemStack, this.armorSlot, (Entity)null) || par1ItemStack.getItem() instanceof ItemBlock;
        }
        return par1ItemStack.getItem() instanceof ItemArmor && ((ItemArmor)par1ItemStack.getItem()).armorType == this.armorSlot;
    }
}
