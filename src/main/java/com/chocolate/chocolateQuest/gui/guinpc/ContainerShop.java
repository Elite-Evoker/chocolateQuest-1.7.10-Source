package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import com.chocolate.chocolateQuest.gui.slot.SlotShop;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.gui.ContainerBDChest;

public class ContainerShop extends ContainerBDChest
{
    static final int COLUMS = 9;
    static final int ICON_DESP = 17;
    static final int MARGIN = 10;
    static final int SHOP_SLOTS = 18;
    boolean isCreative;
    public InventoryShop shopInventory;
    
    public ContainerShop(final IInventory playerInventory, final InventoryShop chestInventory) {
        super(playerInventory, (IInventory)chestInventory);
        this.isCreative = true;
        this.shopInventory = chestInventory;
    }
    
    @Override
    public void layoutInventory(final IInventory chestInventory) {
        int posY = 0;
        for (int i = 0; i < 18; ++i) {
            final int x = 10 + i * 17 - posY * 9;
            final int y = 10 + posY;
            this.addSlotToContainer((Slot)new SlotShop((InventoryShop)chestInventory, i, x, y));
            if (i % 9 == 8) {
                posY += 17;
            }
        }
        if (this.player.capabilities.isCreativeMode) {
            for (int i = 0; i < 4; ++i) {
                int x = 10 + i * 17;
                final int y = 54;
                if (i > 0) {
                    x += 16;
                }
                this.addSlotToContainer(new Slot(chestInventory, 18 + i, x, y));
            }
        }
    }
    
    public void layoutInventory2(final IInventory chestInventory) {
    }
    
    public ItemStack slotClick(final int slotID, final int par2, final int par3, final EntityPlayer player) {
        if (par3 == 6) {
            return null;
        }
        if (par3 == 1) {
            return null;
        }
        if (slotID < 36 || slotID >= 54) {
            return super.slotClick(slotID, par2, par3, player);
        }
        final SlotShop slot = this.inventorySlots.get(slotID);
        if (slot.hasRecipe(player, false)) {
            if (player.inventory.getItemStack() != null) {
                final ItemStack heldStack = player.inventory.getItemStack();
                if (!heldStack.isItemEqual(slot.getStack())) {
                    return null;
                }
                if (heldStack.stackSize + slot.getStack().stackSize > heldStack.getMaxStackSize()) {
                    return null;
                }
            }
            slot.hasRecipe(player, true);
            return super.slotClick(slotID, par2, par3, player);
        }
        return null;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer player, final int index) {
        return super.transferStackInSlot(player, index);
    }
    
    @Override
    public int getPlayerInventoryY() {
        return super.getPlayerInventoryY() + 34;
    }
}
