package com.chocolate.chocolateQuest.gui.slot;

import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.inventory.Slot;

public class SlotLockedToClass extends Slot
{
    Item item;
    int damage;
    boolean checkDamage;
    
    public SlotLockedToClass(final IInventory par1iInventory, final int par2, final int par3, final int par4, final Item item) {
        super(par1iInventory, par2, par3, par4);
        this.damage = 0;
        this.checkDamage = false;
        this.item = item;
    }
    
    public SlotLockedToClass(final IInventory par1iInventory, final int par2, final int par3, final int par4, final Item item, final int metadata) {
        this(par1iInventory, par2, par3, par4, item);
        this.damage = metadata;
        this.checkDamage = true;
    }
    
    public boolean isItemValid(final ItemStack is) {
        if (this.checkDamage) {
            return is.getItem() == this.item && is.getMetadata() == this.damage;
        }
        return is.getItem() == this.item;
    }
}
