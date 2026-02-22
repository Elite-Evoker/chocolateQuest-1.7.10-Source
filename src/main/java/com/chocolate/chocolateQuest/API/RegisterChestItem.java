package com.chocolate.chocolateQuest.API;

import java.util.Random;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;

public class RegisterChestItem
{
    public static ArrayList<WeightedItemStack> chestList;
    public static ArrayList<WeightedItemStack> weaponList;
    public static ArrayList<WeightedItemStack> mineralList;
    public static ArrayList<WeightedItemStack> foodList;
    public static ArrayList<WeightedItemStack> treasureList;
    
    public static void addChestItem(final ItemStack stack, final int weight, final ArrayList<WeightedItemStack> list) {
        list.add(new WeightedItemStack(stack, weight));
    }
    
    public static void addChestItem(final ItemStack stack, final int weight) {
        RegisterChestItem.chestList.add(new WeightedItemStack(stack, weight));
    }
    
    public static void addToolsChestItem(final ItemStack stack, final int weight) {
        RegisterChestItem.weaponList.add(new WeightedItemStack(stack, weight));
    }
    
    public static void addMineralsChestItem(final ItemStack stack, final int weight) {
        RegisterChestItem.mineralList.add(new WeightedItemStack(stack, weight));
    }
    
    public static void addFoodChestItem(final ItemStack stack, final int weight) {
        RegisterChestItem.foodList.add(new WeightedItemStack(stack, weight));
    }
    
    public static void addTreasureItem(final ItemStack stack, final int weight) {
        RegisterChestItem.treasureList.add(new WeightedItemStack(stack, weight));
    }
    
    public static ItemStack getRandomItemStack(final ArrayList<WeightedItemStack> chestList, final Random random) {
        final int[] weights = new int[chestList.size()];
        int maxNum = 0;
        for (int i = 0; i < chestList.size(); ++i) {
            weights[i] = chestList.get(i).weight;
            maxNum += weights[i];
        }
        final int randomNum = random.nextInt(maxNum);
        int index = 0;
        for (int weightSum = weights[0]; weightSum <= randomNum; weightSum += weights[index]) {
            ++index;
        }
        return chestList.get(index).stack.copy();
    }
    
    static {
        RegisterChestItem.chestList = new ArrayList<WeightedItemStack>();
        RegisterChestItem.weaponList = new ArrayList<WeightedItemStack>();
        RegisterChestItem.mineralList = new ArrayList<WeightedItemStack>();
        RegisterChestItem.foodList = new ArrayList<WeightedItemStack>();
        RegisterChestItem.treasureList = new ArrayList<WeightedItemStack>();
    }
}
