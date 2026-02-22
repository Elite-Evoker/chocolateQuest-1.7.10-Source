package com.chocolate.chocolateQuest.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import com.chocolate.chocolateQuest.gui.slot.SlotAmmo;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerGun extends ContainerBDChest
{
    static final int COLUMS = 9;
    static final int ICON_DESP = 17;
    static final int MARGIN = 10;
    int rows;
    ItemStack itemstackGun;
    
    public ContainerGun(final IInventory playerInventory, final IInventory chestInventory, final ItemStack is) {
        super(playerInventory, chestInventory);
        this.itemstackGun = is;
        this.layoutInventory(chestInventory);
    }
    
    @Override
    protected void layoutContainer(final IInventory playerInventory, final IInventory chestInventory) {
        this.rows = (chestInventory.getSizeInventory() - 1) / 9;
        super.layoutContainer(playerInventory, chestInventory);
    }
    
    @Override
    public void layoutInventory(final IInventory chestInventory) {
        if (this.itemstackGun == null) {
            return;
        }
        final ILoadableGun gun = (ILoadableGun)this.itemstackGun.getItem();
        int posY = 0;
        for (int i = 0; i < chestInventory.getSizeInventory(); ++i) {
            final int x = 10 + i * 17 - posY * 9;
            final int y = 10 + posY;
            this.addSlotToContainer((Slot)new SlotAmmo(chestInventory, i, x, y, gun));
            if (i % 9 == 8) {
                posY += 17;
            }
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer player, final int index) {
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotItemStack = slot.getStack();
            if (slotItemStack == this.itemstackGun) {
                return null;
            }
        }
        return super.transferStackInSlot(player, index);
    }
    
    @Override
    public int getPlayerInventoryY() {
        return super.getPlayerInventoryY() + this.rows * 17;
    }
}
