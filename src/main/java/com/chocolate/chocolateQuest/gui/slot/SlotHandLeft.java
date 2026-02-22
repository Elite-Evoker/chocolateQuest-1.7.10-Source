package com.chocolate.chocolateQuest.gui.slot;

import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSwordDefensive;
import com.chocolate.chocolateQuest.API.ITwoHandedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotHandLeft extends Slot
{
    Slot rightHand;
    
    public SlotHandLeft(final IInventory arg0, final int id, final int x, final int y, final Slot rightHand) {
        super(arg0, id, x, y);
        this.rightHand = rightHand;
    }
    
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return !this.isItemTwoHanded(par1ItemStack) && (!this.rightHand.getHasStack() || !this.isItemTwoHanded(this.rightHand.getStack())) && super.isItemValid(par1ItemStack);
    }
    
    public boolean isItemTwoHanded(final ItemStack is) {
        return is.getItem() instanceof ITwoHandedItem || is.getItem() instanceof ItemBaseSwordDefensive;
    }
    
    public boolean canTakeStack(final EntityPlayer par1EntityPlayer) {
        if (this.getStack() != null && this.getStack().getItem() == ChocolateQuest.shield) {
            return this.rightHand.getStack() == null || !(this.rightHand.getStack().getItem() instanceof ItemBaseSwordDefensive);
        }
        return super.canTakeStack(par1EntityPlayer);
    }
}
