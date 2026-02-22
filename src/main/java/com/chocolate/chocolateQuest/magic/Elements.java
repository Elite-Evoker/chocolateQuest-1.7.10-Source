package com.chocolate.chocolateQuest.magic;

import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.StatCollector;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;

public enum Elements
{
    physic("physic", (ElementDamageSource)new ElementDamageSourceNature(), 1.0, "2", "physic", 8956552), 
    magic("magic", (ElementDamageSource)new ElementDamageSourceMagic(), 1.0, "3", "magic", 3394815), 
    blast("blast", (ElementDamageSource)new ElementDamageSourceBlast(), 1.0, "5", "blast", 10066380), 
    fire("fire", (ElementDamageSource)new ElementDamageSourceFire(), 1.0, "6", "fire", 16750848), 
    light("light", (ElementDamageSource)new ElementDamageSourceLight(), 1.0, "e", "light", 16777113), 
    darkness("darkness", (ElementDamageSource)new ElementDamageSourceDark(), 1.0, "8", "dark", 4456516);
    
    String name;
    public ElementDamageSource damageSource;
    public double ammountMultiplier;
    public String stringColor;
    public String particle;
    int color;
    
    private Elements(final String s, final ElementDamageSource ds, final double dmgMultiplier, final String stringColor, final String particle, final int color) {
        this.name = s;
        this.damageSource = ds;
        this.ammountMultiplier = dmgMultiplier;
        this.stringColor = stringColor;
        this.particle = particle;
        this.color = color;
    }
    
    public DamageSource getDamageSource(final Entity shooter) {
        return this.damageSource.getDamageSource(shooter, this.name);
    }
    
    public DamageSource getDamageSourceIndirect(final Entity shooter, final Entity projectile) {
        return this.damageSource.getIndirectDamage(projectile, shooter, this.name);
    }
    
    public DamageSource getDamageSource() {
        return this.damageSource.getDamageSource(this.name);
    }
    
    public float onHitEntity(final Entity source, final Entity entityHit, final float damage) {
        return this.damageSource.onHitEntity(source, entityHit, damage);
    }
    
    public String getTranslatedName() {
        return StatCollector.translateToLocal("element." + this.name + ".name");
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getStringColor() {
        return this.stringColor;
    }
    
    public String getParticle() {
        return this.particle;
    }
    
    public float getColorX() {
        return (this.color >> 16 & 0xFF) / 256.0f;
    }
    
    public float getColorY() {
        return (this.color >> 8 & 0xFF) / 256.0f;
    }
    
    public float getColorZ() {
        return (this.color >> 0 & 0xFF) / 256.0f;
    }
    
    public Enchantment getDefenseAgainstEnchantment() {
        return null;
    }
    
    public int getEntityDefense(final EntityLivingBase el, final DamageSource ds) {
        return 0;
    }
    
    public int getDamageSourceProtection(final EntityLivingBase el, final DamageSource ds) {
        int f = 0;
        final Enchantment enchantment = this.getDefenseAgainstEnchantment();
        if (enchantment != null) {
            for (int i = 1; i <= 4; ++i) {
                final ItemStack is = el.getEquipmentInSlot(i);
                if (is != null) {
                    final int lvl = EnchantmentHelper.getEnchantmentLevel(enchantment.effectId, is);
                    if (lvl > 0) {
                        f += enchantment.calcModifierDamage(lvl, ds);
                    }
                }
            }
        }
        if (el instanceof IElementWeak) {
            if (ds.isProjectile()) {
                f += ((IElementWeak)el).getProjectileDefense();
            }
            if (ds.isFireDamage()) {
                f += ((IElementWeak)el).getFireDefense();
            }
            else if (ds.isExplosion()) {
                f += ((IElementWeak)el).getBlastDefense();
            }
            else if (ds.isMagicDamage()) {
                f += ((IElementWeak)el).getMagicDefense();
            }
            else {
                f += ((IElementWeak)el).getPhysicDefense();
            }
        }
        return f;
    }
}
