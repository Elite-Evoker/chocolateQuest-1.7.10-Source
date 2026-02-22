package com.chocolate.chocolateQuest.magic;

import net.minecraft.util.StatCollector;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.enchantment.EnchantmentProtection;

public class EnchantmentMagicDefense extends EnchantmentProtection
{
    public EnchantmentMagicDefense(final int par1, final int par2) {
        super(par1, par2, 0);
        this.setName("magicResist");
    }
    
    public int calcModifierDamage(final int level, final DamageSource ds) {
        if (!ds.isMagicDamage()) {
            return 0;
        }
        final float TYPE_MODIFIER = 1.5f;
        if (level == 0) {
            return 0;
        }
        final float damageReduction = (float)MathHelper.floor_float((6 + level * level) * TYPE_MODIFIER / 3.0f);
        return (int)damageReduction;
    }
    
    public boolean canApply(final ItemStack stack) {
        return stack.getItem() instanceof ItemArmor && super.canApply(stack);
    }
    
    public boolean canApplyAtEnchantingTable(final ItemStack stack) {
        return super.canApplyAtEnchantingTable(stack);
    }
    
    public boolean canApplyTogether(final Enchantment enchantment) {
        return enchantment != Enchantment.protection && enchantment != Enchantment.projectileProtection;
    }
    
    public boolean isAllowedOnBooks() {
        return true;
    }
    
    public String getTranslatedName(final int par1) {
        return StatCollector.translateToLocal("enchantment.magicDefense.name") + " " + StatCollector.translateToLocal("enchantment.level." + par1);
    }
    
    public int getMaxLevel() {
        return 4;
    }
}
