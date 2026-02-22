package com.chocolate.chocolateQuest.utils;

import java.util.Iterator;
import java.util.Set;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.item.Item;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import net.minecraft.nbt.CompressedStreamTools;
import java.io.FileOutputStream;
import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemSword;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import java.util.Random;
import net.minecraft.item.ItemStack;
import java.util.Properties;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.util.ResourceLocation;

public class BDHelper
{
    public static final ResourceLocation texture;
    public static final ResourceLocation guiButtonsTexture;
    public static final ResourceLocation guiParticleTexture;
    
    public static ResourceLocation getItemTexture() {
        return BDHelper.texture;
    }
    
    public static ResourceLocation getParticleTexture() {
        return BDHelper.guiParticleTexture;
    }
    
    public static void println(final String msg) {
        FMLLog.getLogger().info(msg);
    }
    
    public static void printWarn(final String msg) {
        FMLLog.getLogger().warn(msg);
    }
    
    public static String getAppDir() {
        final String s = Loader.instance().getConfigDir().getAbsolutePath();
        return s;
    }
    
    public static String getInfoDir() {
        return "/Chocolate/";
    }
    
    public static String getChocolateDir() {
        return getAppDir() + getInfoDir();
    }
    
    public static String getQuestDir() {
        return getInfoDir() + "Quest/";
    }
    
    public static boolean getBooleanProperty(final Properties prop, final String name, final boolean defaultValue) {
        String s = prop.getProperty(name);
        if (s == null) {
            return defaultValue;
        }
        s = s.trim();
        return s.equals("true");
    }
    
    public static int getIntegerProperty(final Properties prop, final String name, final int defaultValue) {
        String s = prop.getProperty(name);
        if (s == null) {
            return defaultValue;
        }
        int ret = defaultValue;
        try {
            s = s.trim();
            ret = Integer.parseInt(s);
        }
        finally {}
        return ret;
    }
    
    public static int getIntegerProperty(final Properties prop, final String name, final int defaultValue, final int minValue, final int maxValue) {
        int i = getIntegerProperty(prop, name, defaultValue);
        i = Math.max(minValue, i);
        i = Math.min(maxValue, i);
        return i;
    }
    
    public static ItemStack EnchantItemRandomly(final ItemStack itemstack, final Random random) {
        if (itemstack.getItem() instanceof ItemArmor) {
            final ItemArmor armor = (ItemArmor)itemstack.getItem();
            if (armor.armorType == 0) {
                final int cant = 7;
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.protection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.fireProtection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.blastProtection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.projectileProtection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.respiration, random.nextInt(3) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.aquaAffinity, 1);
                }
            }
            else if (armor.armorType == 3) {
                final int cant = 5;
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.protection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.fireProtection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.blastProtection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.projectileProtection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.featherFalling, random.nextInt(4) + 1);
                }
            }
            else {
                final int cant = 6;
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.protection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.fireProtection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.blastProtection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.projectileProtection, random.nextInt(4) + 1);
                }
                if (random.nextInt(cant) == 0) {
                    itemstack.addEnchantment(Enchantment.thorns, random.nextInt(3) + 1);
                }
            }
        }
        else if (itemstack.getItem() instanceof ItemSword) {
            final int cant2 = 7;
            if (random.nextInt(cant2) == 0) {
                itemstack.addEnchantment(Enchantment.sharpness, random.nextInt(2) + 1);
            }
            if (random.nextInt(cant2) == 0) {
                itemstack.addEnchantment(Enchantment.fireAspect, random.nextInt(2) + 1);
            }
            if (random.nextInt(cant2) == 0) {
                itemstack.addEnchantment(Enchantment.smite, random.nextInt(5) + 1);
            }
            if (random.nextInt(cant2) == 0) {
                itemstack.addEnchantment(Enchantment.baneOfArthropods, random.nextInt(5) + 1);
            }
            if (random.nextInt(cant2) == 0) {
                itemstack.addEnchantment(Enchantment.knockback, 1);
            }
        }
        return itemstack;
    }
    
    public static int getRandomIndex(final int[] weights, final Random random) {
        int maxNum = 0;
        for (final int i : weights) {
            maxNum += i;
        }
        final int randomNum = random.nextInt(weights.length);
        int index = 0;
        for (int weightSum = weights[0]; weightSum <= randomNum; weightSum += weights[index]) {
            ++index;
        }
        return index;
    }
    
    public static double getRotationDiffBetweenEntity(final Entity entity, final Entity target) {
        double angle;
        for (angle = entity.rotationYaw - target.rotationYaw; angle > 360.0; angle -= 360.0) {}
        while (angle < 0.0) {
            angle += 360.0;
        }
        angle = Math.abs(angle - 180.0);
        return angle;
    }
    
    public static double getAngleBetweenEntities(final Entity entity, final Entity target) {
        final double d = entity.posX - target.posX;
        final double d2 = entity.posZ - target.posZ;
        double angle = Math.atan2(d, d2);
        angle = angle * 180.0 / 3.141592;
        angle = -MathHelper.wrapAngleTo180_double(angle - 180.0);
        return angle;
    }
    
    public static String StringColor(final String color) {
        return '��' + color;
    }
    
    public static double getWeaponDamage(final ItemStack weapon) {
        double damage = 0.0;
        final Multimap map = weapon.getAttributeModifiers();
        if (map.containsKey((Object)"generic.attackDamage")) {
            final AttributeModifier a = (AttributeModifier)map.get((Object)"generic.attackDamage").toArray()[0];
            damage += (float)a.getAmount();
        }
        return damage;
    }
    
    public static void writeCompressed(final NBTTagCompound tagCompound, final File file) throws IOException {
        CompressedStreamTools.writeCompressed(tagCompound, (OutputStream)new FileOutputStream(file));
    }
    
    public static NBTTagCompound readCompressed(final File file) {
        try {
            return CompressedStreamTools.readCompressed((InputStream)new FileInputStream(file));
        }
        catch (final Exception exception) {
            printWarn("Error reading: " + file.getPath());
            return null;
        }
    }
    
    public static float getColorRed(final int color) {
        return (color >> 16 & 0xFF) / 256.0f;
    }
    
    public static float getColorGreen(final int color) {
        return (color >> 8 & 0xFF) / 256.0f;
    }
    
    public static float getColorBlue(final int color) {
        return (color >> 0 & 0xFF) / 256.0f;
    }
    
    public static ItemStack getStackFromString(final String itemText) {
        final String[] textArray = itemText.split(" ");
        ItemStack stack = null;
        if (textArray.length > 0) {
            final String name = textArray[0];
            final Item currentItem = (Item)Item.itemRegistry.getObject(name);
            if (currentItem != null) {
                int ammount = 1;
                int damage = 0;
                try {
                    if (textArray.length > 1) {
                        ammount = Integer.parseInt(textArray[1]);
                    }
                    if (textArray.length > 2) {
                        damage = Integer.parseInt(textArray[2]);
                    }
                }
                catch (final NumberFormatException ex) {}
                stack = new ItemStack(currentItem, ammount, damage);
            }
            if (textArray.length > 3 && stack != null) {
                try {
                    final NBTBase nbt = JSONToNBT(textArray[3]);
                    if (nbt instanceof NBTTagCompound) {
                        stack.stackTagCompound = (NBTTagCompound)nbt;
                    }
                }
                catch (final NBTException e) {
                    e.printStackTrace();
                }
            }
        }
        return stack;
    }
    
    public static NBTBase JSONToNBT(final String value) throws NBTException {
        final NBTBase nbt = JsonToNBT.func_150315_a(value);
        return nbt;
    }
    
    public static int getIntegerFromString(final String value) {
        int i = 0;
        try {
            i = Integer.valueOf(value);
        }
        catch (final NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public static boolean compareTags(final NBTTagCompound tagWithKeys, final NBTTagCompound tagToCheck) {
        final Set set = tagWithKeys.getKeySet();
        for (final String name : set) {
            if (!tagWithKeys.getTag(name).equals((Object)tagToCheck.getTag(name))) {
                return false;
            }
        }
        return true;
    }
    
    static {
        texture = new ResourceLocation("chocolateQuest:textures/entity/items.png");
        guiButtonsTexture = new ResourceLocation("chocolateQuest:textures/entity/gui.png");
        guiParticleTexture = new ResourceLocation("chocolateQuest:textures/entity/particles.png");
    }
}
