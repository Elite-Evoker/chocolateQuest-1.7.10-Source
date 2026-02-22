package com.chocolate.chocolateQuest.API;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface ICooldownTracker
{
    Object getCooldownTracker(final ItemStack p0, final Entity p1);
    
    void onUpdateCooldown(final ItemStack p0, final Entity p1, final Object p2);
}
