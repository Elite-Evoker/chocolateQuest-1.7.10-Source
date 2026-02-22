package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.StatCollector;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;

public class Awakements
{
    public int id;
    private static int lastID;
    public static Awakements property;
    public static Awakements autoRepair;
    public static Awakements backStab;
    public static Awakements dodgeStamina;
    public static Awakements backDodge;
    public static Awakements berserk;
    public static Awakements range;
    public static Awakements blockStamina;
    public static Awakements parryDamage;
    public static Awakements ammoCapacity;
    public static Awakements power;
    public static Awakements ammoSaver;
    public static Awakements spellPower;
    public static Awakements spellExpansion;
    static final String NBT_NAME = "awk";
    private final String name;
    private final int icon;
    public static Awakements[] awekements;
    
    public Awakements(final String name, final int icon) {
        this.id = 0;
        this.id = Awakements.lastID;
        ++Awakements.lastID;
        this.name = name;
        this.icon = icon;
    }
    
    public static void addEnchant(final ItemStack is, final Awakements awekement, final int lvl) {
        if (is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
        }
        final NBTTagCompound tag = is.stackTagCompound.getCompoundTag("awk");
        tag.setShort(awekement.id + "", (short)lvl);
        is.stackTagCompound.setTag("awk", (NBTBase)tag);
    }
    
    public static boolean hasEnchant(final ItemStack is, final Awakements awekement) {
        if (is.stackTagCompound == null) {
            return false;
        }
        final NBTTagCompound tag = is.stackTagCompound.getCompoundTag("awk");
        final short lvl = tag.getShort(awekement.id + "");
        return lvl > 0;
    }
    
    public static int getEnchantLevel(final ItemStack is, final Awakements awekement) {
        if (is.stackTagCompound == null) {
            return 0;
        }
        final NBTTagCompound tag = is.stackTagCompound.getCompoundTag("awk");
        final short lvl = tag.getShort(awekement.id + "");
        return lvl;
    }
    
    public float getValueModifier(final ItemStack is) {
        return 1.0f;
    }
    
    public void onUpdate(final Entity entity, final ItemStack is) {
    }
    
    public String getDescription(final ItemStack is) {
        final int lvl = getEnchantLevel(is, this);
        return StatCollector.translateToLocal("enchantment." + this.getName() + ".name") + " " + StatCollector.translateToLocal("enchantment.level." + lvl);
    }
    
    public int getMaxLevel() {
        return 4;
    }
    
    public void onEntityItemUpdate(final EntityItem entityItem) {
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getIconIndex() {
        return this.icon;
    }
    
    public int getLevelCost() {
        return 1;
    }
    
    public boolean canBeUsedOnItem(final ItemStack is) {
        return false;
    }
    
    public boolean canBeAddedByNPC(final int type) {
        return type == EnumEnchantType.BLACKSMITH.ordinal();
    }
    
    public static int getExperienceForNextLevel(final ItemStack is) {
        int expRequired = 1;
        for (final Awakements aw : Awakements.awekements) {
            if (aw.canBeUsedOnItem(is)) {
                final int lvl = getEnchantLevel(is, aw);
                expRequired += lvl * aw.getLevelCost();
            }
        }
        return Math.min(expRequired * expRequired, 104);
    }
    
    static {
        Awakements.lastID = 0;
        Awakements.property = new AwakementProperty("property", 0);
        Awakements.autoRepair = new AwakementAutoRepair("autoRepair", 0);
        Awakements.backStab = new AwakementDagger("backstab", 0);
        Awakements.dodgeStamina = new AwakementDagger("dodge", 0);
        Awakements.backDodge = new AwakementBigSword("backwardsJump", 0);
        Awakements.berserk = new AwakementBigSword("berserk", 0);
        Awakements.range = new AwakementSpear("range", 0);
        Awakements.blockStamina = new AwakementSword("shieldBlock", 0);
        Awakements.parryDamage = new AwakementSword("parryDamage", 0);
        Awakements.ammoCapacity = new AwakementPistol("ammoCapacity", 0, 8);
        Awakements.power = new AwakementPistol("power", 0);
        Awakements.ammoSaver = new AwakementPistol("ammoSaver", 0);
        Awakements.spellPower = new AwakementStaff("power", 0);
        Awakements.spellExpansion = new AwakementStaff("spellExpansion", 0);
        Awakements.awekements = new Awakements[] { Awakements.property, Awakements.autoRepair, Awakements.backStab, Awakements.dodgeStamina, Awakements.backDodge, Awakements.berserk, Awakements.range, Awakements.blockStamina, Awakements.parryDamage, Awakements.ammoCapacity, Awakements.power, Awakements.ammoSaver, Awakements.spellPower, Awakements.spellExpansion };
    }
}
