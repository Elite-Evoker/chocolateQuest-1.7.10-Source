package com.chocolate.chocolateQuest.misc;

import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class EquipementHelper
{
    public static final int swordsman = 0;
    public static final int defender = 1;
    public static final int ninja = 2;
    public static final int berserk = 3;
    public static final int spearman = 4;
    public static final int archer = 5;
    public static final int gunner = 6;
    public static final int healer = 7;
    public static final int bannerman = 8;
    public static final int music = 9;
    
    public static void equipHumanRandomly(final EntityHumanBase e, final int lvl, final int type) {
        equipEntity((EntityLivingBase)e, lvl);
        switch (type) {
            case 0: {
                e.setCurrentItemOrArmor(0, getSword(e.getRNG(), lvl));
                e.setLeftHandItem(null);
                break;
            }
            case 1: {
                e.setCurrentItemOrArmor(0, getSword(e.getRNG(), lvl));
                e.setLeftHandItem(new ItemStack(ChocolateQuest.shield, 1, e.getTeamID()));
                break;
            }
            case 2: {
                ItemStack is;
                if (lvl < 4) {
                    is = new ItemStack(ChocolateQuest.ironDagger);
                }
                else {
                    is = new ItemStack(ChocolateQuest.diamondDagger);
                }
                e.setCurrentItemOrArmor(0, is);
                e.setLeftHandItem(null);
                break;
            }
            case 3: {
                ItemStack is;
                if (lvl < 4) {
                    is = new ItemStack(ChocolateQuest.ironBigsword);
                }
                else {
                    is = new ItemStack(ChocolateQuest.diamondBigsword);
                }
                e.setCurrentItemOrArmor(0, is);
                e.setLeftHandItem(null);
                break;
            }
            case 4: {
                ItemStack is;
                if (lvl < 4) {
                    is = new ItemStack(ChocolateQuest.ironSpear);
                }
                else {
                    is = new ItemStack(ChocolateQuest.diamondSpear);
                }
                e.setCurrentItemOrArmor(0, is);
                e.setLeftHandItem(null);
                break;
            }
            case 5: {
                e.setCurrentItemOrArmor(0, new ItemStack((Item)Items.bow));
                e.setLeftHandItem(null);
                break;
            }
            case 6: {
                e.setCurrentItemOrArmor(0, getSword(e.getRNG(), lvl));
                e.setLeftHandItem(new ItemStack(ChocolateQuest.revolver));
                break;
            }
            case 7: {
                e.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.staffHeal));
                e.setLeftHandItem(null);
                break;
            }
            case 8: {
                e.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.banner, 1, e.getTeamID()));
                e.setLeftHandItem(null);
                break;
            }
        }
    }
    
    public static int getRandomType(final EntityHumanBase e, final int rareRatio) {
        final Random random = e.getRNG();
        if (random.nextInt(50 * rareRatio) == 0) {
            return 8;
        }
        if (random.nextInt(4 * rareRatio) == 0) {
            return 5;
        }
        if (random.nextInt(5 * rareRatio) == 0) {
            return 3;
        }
        if (random.nextInt(4 * rareRatio) == 0) {
            return 4;
        }
        if (random.nextInt(4 * rareRatio) == 0) {
            return 2;
        }
        if (random.nextInt(3 * rareRatio) == 0) {
            return 7;
        }
        if (random.nextInt(2 * rareRatio) == 0) {
            return 1;
        }
        return 0;
    }
    
    public static int getRandomLevel(final EntityHumanBase e) {
        final Random random = e.getRNG();
        return random.nextInt(5);
    }
    
    public static void equipEntity(final EntityLivingBase e, final int lvl) {
        if (e instanceof EntityHumanBase && (lvl == 4 || lvl == 3)) {
            if (lvl == 4) {
                for (int i = 1; i <= 4; ++i) {
                    e.setCurrentItemOrArmor(i, ((EntityHumanBase)e).getDiamondArmorForSlot(i));
                }
            }
            if (lvl == 3) {
                for (int i = 1; i <= 4; ++i) {
                    e.setCurrentItemOrArmor(i, ((EntityHumanBase)e).getIronArmorForSlot(i));
                }
            }
        }
        else {
            for (int i = 1; i <= 4; ++i) {
                e.setCurrentItemOrArmor(i, getArmor(e.getRNG(), i, lvl));
            }
        }
        e.setCurrentItemOrArmor(0, getSword(e.getRNG(), lvl));
    }
    
    public static ItemStack getSword(final Random rand, final int lvl) {
        switch (lvl) {
            case 1: {
                return new ItemStack(Items.stone_sword);
            }
            case 2: {
                return new ItemStack(Items.golden_sword);
            }
            case 3: {
                return new ItemStack(Items.iron_sword);
            }
            case 4: {
                return new ItemStack(Items.diamond_sword);
            }
            default: {
                return new ItemStack(Items.wooden_sword);
            }
        }
    }
    
    public static ItemStack getArmor(final Random rand, final int stack, final int lvl) {
        if (1 == stack) {
            switch (lvl) {
                case 1: {
                    return new ItemStack((Item)Items.chainmail_boots);
                }
                case 2: {
                    return new ItemStack((Item)Items.golden_boots);
                }
                case 3: {
                    return new ItemStack((Item)Items.iron_boots);
                }
                case 4: {
                    return new ItemStack((Item)Items.diamond_boots);
                }
                case 0: {
                    return new ItemStack((Item)Items.leather_boots);
                }
            }
        }
        if (2 == stack) {
            switch (lvl) {
                case 1: {
                    return new ItemStack((Item)Items.chainmail_leggings);
                }
                case 2: {
                    return new ItemStack((Item)Items.golden_leggings);
                }
                case 3: {
                    return new ItemStack((Item)Items.iron_leggings);
                }
                case 4: {
                    return new ItemStack((Item)Items.diamond_leggings);
                }
                case 0: {
                    return new ItemStack((Item)Items.leather_leggings);
                }
            }
        }
        if (3 == stack) {
            switch (lvl) {
                case 1: {
                    return new ItemStack((Item)Items.chainmail_chestplate);
                }
                case 2: {
                    return new ItemStack((Item)Items.golden_chestplate);
                }
                case 3: {
                    return new ItemStack((Item)Items.iron_chestplate);
                }
                case 4: {
                    return new ItemStack((Item)Items.diamond_chestplate);
                }
                case 0: {
                    return new ItemStack((Item)Items.leather_chestplate);
                }
            }
        }
        if (4 == stack) {
            switch (lvl) {
                case 1: {
                    return new ItemStack((Item)Items.chainmail_helmet);
                }
                case 2: {
                    return new ItemStack((Item)Items.golden_helmet);
                }
                case 3: {
                    return new ItemStack((Item)Items.iron_helmet);
                }
                case 4: {
                    return new ItemStack((Item)Items.diamond_helmet);
                }
                case 0: {
                    return new ItemStack((Item)Items.leather_helmet);
                }
            }
        }
        return null;
    }
}
