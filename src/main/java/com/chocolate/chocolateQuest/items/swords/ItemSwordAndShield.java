package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemSwordAndShield extends ItemBaseSwordDefensive
{
    final boolean isIron;
    
    public ItemSwordAndShield(final Item.ToolMaterial mat) {
        super(mat, "");
        if (mat == Item.ToolMaterial.IRON) {
            this.isIron = true;
        }
        else {
            this.isIron = false;
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister iconRegister) {
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item itemId, final CreativeTabs table, final List list) {
        for (int i = 0; i < 15; ++i) {
            final ItemStack is = new ItemStack(itemId);
            (is.stackTagCompound = new NBTTagCompound()).setShort("Shield", (short)i);
            list.add(is);
        }
    }
    
    @Override
    public int getShieldID(final ItemStack is) {
        if (is.stackTagCompound != null) {
            return is.stackTagCompound.getShort("Shield");
        }
        return super.getShieldID(is);
    }
    
    public IIcon getIconFromDamage(final int par1) {
        if (this.isIron) {
            return Items.iron_sword.getIconFromDamage(par1);
        }
        return Items.diamond_sword.getIconFromDamage(par1);
    }
}
