package com.chocolate.chocolateQuest.magic;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class SpellBase
{
    public String name;
    public static SpellBase[] spells;
    
    public void onUpdate(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int angle) {
    }
    
    public boolean shouldUpdate() {
        return false;
    }
    
    public void onCastStart(final EntityLivingBase shooter, final Elements element, final ItemStack is) {
    }
    
    public void onShoot(final EntityLivingBase shooter, final Elements element, final ItemStack is, final int chargeTime) {
    }
    
    public boolean isProjectile() {
        return false;
    }
    
    public void onEntityHit() {
    }
    
    public int getCastingTime() {
        return 20;
    }
    
    public int getCoolDown() {
        return 4;
    }
    
    public int getRange(final ItemStack itemstack) {
        return 16;
    }
    
    public static SpellBase getSpellByID(final int id) {
        return SpellBase.spells[id];
    }
    
    public static String[] getNames() {
        final String[] names = new String[SpellBase.spells.length];
        for (int i = 0; i < names.length; ++i) {
            names[i] = SpellBase.spells[i].name;
        }
        return names;
    }
    
    public SpellBase setName(final String s) {
        this.name = s;
        return this;
    }
    
    public int getExpansion(final ItemStack itemStack) {
        return Awakements.getEnchantLevel(itemStack, Awakements.spellExpansion);
    }
    
    public int getDamage(final ItemStack itemStack) {
        return Awakements.getEnchantLevel(itemStack, Awakements.spellPower);
    }
    
    static {
        SpellBase.spells = new SpellBase[] { new SpellProjectile().setName("spell_projectile"), new SpellProjectileAimed().setName("spell_tracker"), new SpellProjectileBoomerang().setName("spell_boomerang"), new SpellProjectileArea().setName("spell_area"), new SpellTeleport().setName("spell_teleport"), new SpellSpray().setName("spell_spray"), new SpellTornadoMini().setName("spell_tornado"), new SpellVampiric().setName("spell_vampiric"), new SpellProjectileShield().setName("spell_shield"), new SpellStorm().setName("spell_storm"), new SpellBeam().setName("spell_beam") };
    }
}
