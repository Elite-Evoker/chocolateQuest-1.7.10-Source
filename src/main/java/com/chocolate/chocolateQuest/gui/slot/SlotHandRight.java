package com.chocolate.chocolateQuest.gui.slot;

import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSwordDefensive;
import com.chocolate.chocolateQuest.API.ITwoHandedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotHandRight extends Slot
{
    Slot oppositeHand;
    
    public SlotHandRight(final IInventory arg0, final int id, final int x, final int y) {
        super(arg0, id, x, y);
    }
    
    public void setOpossedHandSlot(final Slot slot) {
        this.oppositeHand = slot;
    }
    
    public boolean isItemValid(final ItemStack is) {
        return (!this.oppositeHand.getHasStack() || (!(is.getItem() instanceof ITwoHandedItem) && !(is.getItem() instanceof ItemBaseSwordDefensive))) && super.isItemValid(is);
    }
    
    public void onPickupFromSlot(final EntityPlayer par1EntityPlayer, final ItemStack is) {
        if (is.getItem() instanceof ItemBaseSwordDefensive) {
            this.oppositeHand.putStack((ItemStack)null);
        }
        super.onPickupFromSlot(par1EntityPlayer, is);
    }
    
    public void putStack(final ItemStack is) {
        if (is != null && is.getItem() instanceof ItemBaseSwordDefensive) {
            this.oppositeHand.putStack(new ItemStack(ChocolateQuest.shield, 0, ((ItemBaseSwordDefensive)is.getItem()).getShieldID(is)));
        }
        super.putStack(is);
    }
}
