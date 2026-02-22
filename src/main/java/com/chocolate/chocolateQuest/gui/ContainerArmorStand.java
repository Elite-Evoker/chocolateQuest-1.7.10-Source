package com.chocolate.chocolateQuest.gui;

import net.minecraft.inventory.Slot;
import com.chocolate.chocolateQuest.gui.slot.SlotArmor;
import net.minecraft.inventory.IInventory;

public class ContainerArmorStand extends ContainerBDChest
{
    public ContainerArmorStand(final IInventory playerInventory, final IInventory chestInventory) {
        super(playerInventory, chestInventory);
    }
    
    @Override
    public void layoutInventory(final IInventory chestInventory) {
        final int yPos = 14;
        final int x = 0;
        final int SLOT_DIST = 18;
        for (int i = 0; i < 4; ++i) {
            this.addSlotToContainer((Slot)new SlotArmor(chestInventory, 3 - i, x + SLOT_DIST, yPos + 10 + i * 18, i));
        }
        this.addSlotToContainer(new Slot(chestInventory, 4, x, yPos + 28));
        this.addSlotToContainer(new Slot(chestInventory, 5, x + SLOT_DIST * 2, yPos + 28));
    }
    
    @Override
    public int getPlayerInventoryY() {
        return 190 + (this.getRowCount() - 4) * 18;
    }
}
