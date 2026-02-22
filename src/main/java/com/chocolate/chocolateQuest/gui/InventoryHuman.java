package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class InventoryHuman extends InventoryCargo
{
    public EntityHumanBase human;
    int tempid;
    public static final int POTION_SLOT = 6;
    
    public InventoryHuman(final EntityHumanBase human) {
        this.cargoItems = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < 5; ++i) {
            this.cargoItems[i] = human.getEquipmentInSlot(i);
        }
        this.cargoItems[5] = human.leftHandItem;
        this.human = human;
        if (human.potionCount > 0) {
            this.cargoItems[6] = new ItemStack(ChocolateQuest.potion, human.potionCount, 0);
        }
    }
    
    @Override
    public int getSizeInventory() {
        return 7;
    }
    
    @Override
    public void setInventorySlotContents(final int i, final ItemStack itemstack) {
        super.setInventorySlotContents(i, itemstack);
    }
    
    @Override
    public void updateInventory() {
        for (int i = 0; i < 5; ++i) {
            this.human.setCurrentItemOrArmor(i, this.cargoItems[i]);
        }
        this.human.leftHandItem = this.cargoItems[5];
        if (this.cargoItems[6] != null) {
            this.human.potionCount = this.cargoItems[6].stackSize;
        }
        else {
            this.human.potionCount = 0;
        }
    }
    
    @Override
    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return super.isItemValidForSlot(i, itemstack);
    }
}
