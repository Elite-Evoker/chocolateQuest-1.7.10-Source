package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.slot.SlotLockedToClass;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;

public class ContainerGolemInventory extends ContainerHumanInventory
{
    public ContainerGolemInventory(final IInventory playerInventory, final IInventory chestInventory) {
        super(playerInventory, chestInventory);
    }
    
    @Override
    public void layoutInventory(final IInventory chestInventory) {
        final int yPos = 14;
        final int x = 0;
        final int SLOT_DIST = 18;
        this.addSlotToContainer(new Slot(chestInventory, 0, x, yPos + 28));
        for (int i = 0; i < 4; ++i) {
            this.addSlotToContainer((Slot)new SlotLockedToClass(chestInventory, 4 - i, x + SLOT_DIST, yPos + 10 + i * 18, ChocolateQuest.golemUpgrade));
        }
        this.addSlotToContainer(new Slot(chestInventory, 5, x + SLOT_DIST * 2, yPos + 28));
    }
}
