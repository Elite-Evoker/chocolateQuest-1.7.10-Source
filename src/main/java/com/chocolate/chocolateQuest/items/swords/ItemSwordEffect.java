package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.item.Item;

public class ItemSwordEffect extends ItemBaseSwordDefensive
{
    int potionEffect;
    
    public ItemSwordEffect(final Item.ToolMaterial mat, final String texture) {
        super(mat, texture);
        this.potionEffect = Potion.weakness.id;
    }
    
    public ItemSwordEffect(final Item.ToolMaterial mat, final String texture, final int effect) {
        this(mat, texture);
        this.potionEffect = effect;
    }
    
    @Override
    public boolean hitEntity(final ItemStack par1ItemStack, final EntityLivingBase target, final EntityLivingBase entity) {
        final PotionEffect effect = new PotionEffect(this.potionEffect, 100, 0);
        target.addPotionEffect(effect);
        return super.hitEntity(par1ItemStack, target, entity);
    }
}
