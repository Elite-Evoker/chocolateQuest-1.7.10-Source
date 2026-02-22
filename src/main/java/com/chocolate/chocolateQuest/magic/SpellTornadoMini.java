package com.chocolate.chocolateQuest.magic;

import net.minecraft.item.ItemStack;

public class SpellTornadoMini extends SpellProjectile
{
    @Override
    public int getType() {
        return 10;
    }
    
    @Override
    public int getCoolDown() {
        return 40;
    }
    
    @Override
    public int getRange(final ItemStack itemstack) {
        return 10;
    }
    
    @Override
    public int getCastingTime() {
        return 30;
    }
}
