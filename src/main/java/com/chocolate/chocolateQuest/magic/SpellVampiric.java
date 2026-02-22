package com.chocolate.chocolateQuest.magic;

import net.minecraft.item.ItemStack;

public class SpellVampiric extends SpellProjectile
{
    @Override
    public int getType() {
        return 8;
    }
    
    @Override
    public int getCoolDown() {
        return 15;
    }
    
    @Override
    public int getRange(final ItemStack itemstack) {
        return 16;
    }
}
