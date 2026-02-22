package com.chocolate.chocolateQuest.misc;

import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;

public class DungeonsItemsTab extends CreativeTabs
{
    Item item;
    
    public DungeonsItemsTab(final String label) {
        super(label);
    }
    
    public void setItemIcon(final Item parItem) {
        this.item = parItem;
    }
    
    public Item getTabIconItem() {
        return this.item;
    }
}
