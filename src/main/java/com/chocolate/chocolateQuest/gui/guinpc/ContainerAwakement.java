package com.chocolate.chocolateQuest.gui.guinpc;

import java.util.Iterator;
import net.minecraft.enchantment.Enchantment;
import java.util.Map;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.gui.slot.SlotLockedToClass;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.gui.ContainerBDChest;

public class ContainerAwakement extends ContainerBDChest
{
    static final int COLUMS = 9;
    static final int ICON_DESP = 17;
    static final int MARGIN = 10;
    InventoryAwakement inventory;
    public boolean mode;
    public static final boolean MODE_ENCHANTMENT = true;
    int type;
    int maxLevel;
    
    public ContainerAwakement(final IInventory playerInventory, final InventoryAwakement chestInventory, final int type, final int level) {
        super(playerInventory, (IInventory)chestInventory);
        this.mode = true;
        this.inventory = chestInventory;
        this.mode = (type == EnumEnchantType.ENCHANT.ordinal());
        this.maxLevel = level;
    }
    
    @Override
    public void layoutInventory(final IInventory chestInventory) {
        final int x = 10;
        final int y = 60;
        this.addSlotToContainer(new Slot(chestInventory, 0, x, y));
        this.addSlotToContainer((Slot)new SlotLockedToClass(chestInventory, 1, x + 32, y - 42, Items.dye, 4));
    }
    
    @Override
    public int getPlayerInventoryY() {
        return super.getPlayerInventoryY() + 54;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer player) {
        ItemStack is = this.inventory.getStackInSlot(0);
        if (is != null) {
            player.dropPlayerItemWithRandomChoice(is, false);
        }
        is = this.inventory.getStackInSlot(1);
        if (is != null) {
            player.dropPlayerItemWithRandomChoice(is, false);
        }
    }
    
    public void enchantItem(final int enchantment) {
        final ItemStack is = this.inventory.getStackInSlot(0);
        int lvl = 0;
        if (this.mode) {
            lvl = EnchantmentHelper.getEnchantmentLevel(enchantment, is);
        }
        else {
            lvl = Awakements.getEnchantLevel(is, Awakements.awekements[enchantment]);
        }
        int expRequired = this.getXPRequiredToEnchantItem() + this.getXPRequiredForEnchantment(enchantment, lvl);
        expRequired -= this.getCatalystRebate(expRequired);
        if (!this.player.capabilities.isCreativeMode) {
            final EntityPlayer player = this.player;
            player.experienceLevel -= expRequired;
        }
        this.inventory.setInventorySlotContents(1, null);
        if (this.mode) {
            final Map map = EnchantmentHelper.getEnchantments(is);
            if (map.containsKey(enchantment)) {
                map.put(enchantment, map.get(enchantment) + 1);
            }
            else {
                map.put(enchantment, 1);
            }
            EnchantmentHelper.setEnchantments(map, is);
        }
        else {
            final Awakements aw = Awakements.awekements[enchantment];
            Awakements.addEnchant(is, aw, Awakements.getEnchantLevel(is, aw) + 1);
        }
    }
    
    public int getXPRequiredToEnchantItem() {
        final ItemStack is = this.inventory.getStackInSlot(0);
        int expRequired = 0;
        if (this.mode) {
            final int itemEnchantability = is.getItem().getItemEnchantability();
            final Map map = EnchantmentHelper.getEnchantments(is);
            final Iterator iterator = map.entrySet().iterator();
            for (final Object e : map.entrySet()) {
                if (e != null) {
                    final int key = ((Map.Entry)e).getKey();
                    final int value = ((Map.Entry)e).getValue();
                    final Enchantment enchant = Enchantment.enchantmentsList[((Map.Entry)e).getKey()];
                    final int enchantability = Enchantment.enchantmentsList[key].getMinEnchantability(8) * value;
                    expRequired += enchantability;
                }
            }
            expRequired = Math.min(104, expRequired / 12);
        }
        else {
            expRequired = Awakements.getExperienceForNextLevel(is);
        }
        expRequired = Math.max(1, expRequired);
        return expRequired;
    }
    
    public int getXPRequiredForEnchantment(final int enchant, final int level) {
        if (this.mode) {
            return Enchantment.enchantmentsList[enchant].getMaxEnchantability(0) / 4;
        }
        return 0;
    }
    
    public int getCatalystRebate(final int expRequired) {
        final ItemStack catalyst = this.inventory.getStackInSlot(1);
        if (catalyst != null) {
            int ret = catalyst.stackSize;
            if (ret > expRequired) {
                ret = expRequired - 1;
            }
            return ret;
        }
        return 0;
    }
}
