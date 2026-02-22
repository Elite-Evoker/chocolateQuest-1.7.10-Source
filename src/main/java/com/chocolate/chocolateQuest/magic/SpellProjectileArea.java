package com.chocolate.chocolateQuest.magic;

import net.minecraft.item.ItemStack;

public class SpellProjectileArea extends SpellProjectile
{
    @Override
    public int getType() {
        return 102;
    }
    
    @Override
    public int getCoolDown() {
        return 100;
    }
    
    @Override
    public int getCastingTime() {
        return 30;
    }
    
    @Override
    public int getRange(final ItemStack itemstack) {
        return 8;
    }
}
