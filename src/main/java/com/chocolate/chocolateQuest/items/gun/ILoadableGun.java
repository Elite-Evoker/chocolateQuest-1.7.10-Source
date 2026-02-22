package com.chocolate.chocolateQuest.items.gun;

import net.minecraft.item.ItemStack;

public interface ILoadableGun
{
    int getAmmoLoaderStackSize(final ItemStack p0);
    
    int getAmmoLoaderAmmount(final ItemStack p0);
    
    boolean isValidAmmo(final ItemStack p0);
    
    int getStackIcon(final ItemStack p0);
}
