package com.chocolate.chocolateQuest.gui.slot;

import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import net.minecraft.inventory.Slot;

public class SlotAmmo extends Slot
{
    ILoadableGun gun;
    
    public SlotAmmo(final IInventory par1iInventory, final int par2, final int par3, final int par4, final ILoadableGun gun) {
        super(par1iInventory, par2, par3, par4);
        this.gun = gun;
    }
    
    public boolean isItemValid(final ItemStack is) {
        return this.gun.isValidAmmo(is);
    }
}
