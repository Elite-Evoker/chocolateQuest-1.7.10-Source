package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.slot.SlotLocked;
import com.chocolate.chocolateQuest.gui.slot.SlotHandLeft;
import com.chocolate.chocolateQuest.gui.slot.SlotArmor;
import net.minecraft.inventory.Slot;
import com.chocolate.chocolateQuest.gui.slot.SlotHandRight;
import net.minecraft.inventory.IInventory;

public class ContainerHumanInventory extends ContainerBDChest
{
    public ContainerHumanInventory(final IInventory playerInventory, final IInventory chestInventory) {
        super(playerInventory, chestInventory);
    }
    
    @Override
    public void layoutInventory(final IInventory chestInventory) {
        final EntityHumanBase human = ((InventoryHuman)chestInventory).human;
        final int yPos = 14;
        final int x = 0;
        final int SLOT_DIST = 18;
        if (!human.inventoryLocked) {
            final SlotHandRight rightHandSlot = new SlotHandRight(chestInventory, 0, x, yPos + 28);
            this.addSlotToContainer((Slot)rightHandSlot);
            for (int i = 0; i < 4; ++i) {
                this.addSlotToContainer((Slot)new SlotArmor(chestInventory, 4 - i, x + SLOT_DIST, yPos + 10 + i * 18, i));
            }
            final Slot leftHandSlot = new SlotHandLeft(chestInventory, 5, x + SLOT_DIST * 2, yPos + 28, rightHandSlot);
            this.addSlotToContainer(leftHandSlot);
            rightHandSlot.setOpossedHandSlot(leftHandSlot);
        }
        else {
            this.addSlotToContainer((Slot)new SlotLocked(chestInventory, 0, x, yPos + 28));
            for (int j = 0; j < 4; ++j) {
                this.addSlotToContainer((Slot)new SlotLocked(chestInventory, 4 - j, x + SLOT_DIST, yPos + 10 + j * 18));
            }
            this.addSlotToContainer((Slot)new SlotLocked(chestInventory, 5, x + SLOT_DIST * 2, yPos + 28));
        }
        final Slot potions = new Slot(chestInventory, 6, x + SLOT_DIST * 2, yPos + 28 + 16);
        this.addSlotToContainer(potions);
    }
    
    @Override
    public int getPlayerInventoryY() {
        return 190 + (this.getRowCount() - 4) * 18;
    }
}
