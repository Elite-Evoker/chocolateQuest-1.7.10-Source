package com.chocolate.chocolateQuest.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;

public class InventoryGun extends InventoryBag
{
    ILoadableGun gun;
    
    public InventoryGun(final ItemStack item, final EntityPlayer player) {
        super(item, player);
        this.gun = (ILoadableGun)item.getItem();
    }
    
    @Override
    public int getSizeInventory() {
        if (this.gun == null) {
            return super.getSizeInventory();
        }
        return this.gun.getAmmoLoaderAmmount(this.container);
    }
    
    @Override
    public String getInventoryName() {
        return "Gun";
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int i) {
        return null;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return this.gun.getAmmoLoaderStackSize(this.container);
    }
}
