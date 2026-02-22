package com.chocolate.chocolateQuest.config;

import java.util.Enumeration;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import cpw.mods.fml.common.FMLLog;
import java.util.StringTokenizer;
import java.io.Reader;
import java.io.FileReader;
import java.util.Properties;
import java.io.File;
import java.util.ArrayList;
import com.chocolate.chocolateQuest.API.RegisterChestItem;
import com.chocolate.chocolateQuest.utils.BDHelper;

public class ConfigHelper
{
    public static void readChests() {
        BDHelper.println("## Chest items register ##");
        getItems("Chocolate/ChestConfig/chests.prop", RegisterChestItem.chestList, "default");
        getItems("Chocolate/ChestConfig/treasures.prop", RegisterChestItem.treasureList, "treasure");
        getItems("Chocolate/ChestConfig/weapons.prop", RegisterChestItem.weaponList, "weapon");
        getItems("Chocolate/ChestConfig/ores.prop", RegisterChestItem.mineralList, "ores");
        getItems("Chocolate/ChestConfig/food.prop", RegisterChestItem.foodList, "food");
    }
    
    public static void getItems(final String st, final ArrayList list, final String listName) {
        final File file = new File(BDHelper.getAppDir(), st);
        try {
            final Properties prop = new Properties();
            final FileReader fr = new FileReader(file);
            prop.load(fr);
            final Enumeration<Object> e = prop.elements();
            int cont = 0;
            while (e.hasMoreElements()) {
                final String s = e.nextElement();
                final StringTokenizer stkn = new StringTokenizer(s, ",");
                final int tokens = stkn.countTokens();
                final String name = ((String)stkn.nextElement()).trim();
                int numberOfItems = 1;
                int damageOfItems = 0;
                int itemsWeight = 100;
                if (tokens == 4) {
                    numberOfItems = Integer.parseInt(((String)stkn.nextElement()).trim());
                    damageOfItems = Integer.parseInt(((String)stkn.nextElement()).trim());
                    itemsWeight = Integer.parseInt(((String)stkn.nextElement()).trim());
                }
                if (tokens == 3) {
                    numberOfItems = Integer.parseInt(((String)stkn.nextElement()).trim());
                    itemsWeight = Integer.parseInt(((String)stkn.nextElement()).trim());
                }
                if (tokens == 2) {
                    itemsWeight = Integer.parseInt(((String)stkn.nextElement()).trim());
                }
                ++cont;
                FMLLog.getLogger().info("Added " + name + " to " + listName + " chests list");
                final Object item = Item.itemRegistry.getObject(name);
                if (item instanceof Item) {
                    final ItemStack is = new ItemStack((Item)item, numberOfItems, damageOfItems);
                    RegisterChestItem.addChestItem(is, itemsWeight, list);
                }
                else {
                    BDHelper.println("Error loading item: " + s + " into " + listName + " chests list");
                }
            }
        }
        catch (final FileNotFoundException e2) {
            FMLLog.getLogger().error("Not found config file at Chocolate Quest mod: " + file.getAbsolutePath());
            e2.printStackTrace();
        }
        catch (final IOException e3) {
            FMLLog.getLogger().error("Error reading config file at Chocolate Quest mod: " + file.getAbsolutePath());
            e3.printStackTrace();
        }
    }
}
