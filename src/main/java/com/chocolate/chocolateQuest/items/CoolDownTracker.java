package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.gui.InventoryBag;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.magic.SpellBase;

class CoolDownTracker
{
    public SpellBase castingSpell;
    SpellBase[] spells;
    int[] cooldowns;
    
    public CoolDownTracker(final ItemStack is) {
        final ItemStack[] ammo = InventoryBag.getCargo(is);
        this.spells = new SpellBase[ammo.length];
        this.cooldowns = new int[ammo.length];
        for (int i = 0; i < ammo.length; ++i) {
            if (ammo[i] != null) {
                this.spells[i] = SpellBase.getSpellByID(ammo[i].getMetadata());
            }
        }
    }
    
    public void onUpdate() {
        for (int i = 0; i < this.cooldowns.length; ++i) {
            if (this.cooldowns[i] > 0) {
                final int[] cooldowns = this.cooldowns;
                final int n = i;
                --cooldowns[n];
            }
        }
    }
    
    public void increaseAllCooldowns(final int ammount) {
        for (int i = 0; i < this.cooldowns.length; ++i) {
            final int[] cooldowns = this.cooldowns;
            final int n = i;
            cooldowns[n] += ammount;
        }
    }
}
