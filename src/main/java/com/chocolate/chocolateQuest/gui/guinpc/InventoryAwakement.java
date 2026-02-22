package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.InventoryCargo;

public class InventoryAwakement extends InventoryCargo
{
    EntityHumanNPC npc;
    
    public InventoryAwakement(final EntityHumanNPC human) {
        this.cargoItems = new ItemStack[this.getSizeInventory()];
        this.npc = human;
    }
    
    @Override
    public int getSizeInventory() {
        return 2;
    }
    
    @Override
    public void updateInventory() {
    }
}
