package com.chocolate.chocolateQuest.items;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class ItemMulti extends Item
{
    public String[] names;
    public IIcon[] icon;
    String textureName;
    
    public ItemMulti(final String[] names, final String texture) {
        this.names = new String[] { "???" };
        this.names = names;
        this.textureName = texture;
    }
    
    public boolean getHasSubtypes() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.icon = new IIcon[this.names.length];
        for (int i = 0; i < this.icon.length; ++i) {
            this.icon[i] = iconRegister.registerIcon("chocolatequest:" + this.textureName + i);
        }
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        final int i = itemstack.getMetadata();
        if (i < this.names.length) {
            return ("" + StatCollector.translateToLocal("item." + this.names[i] + ".name")).trim();
        }
        return "????";
    }
    
    public IIcon getIconFromDamage(final int par1) {
        if (par1 < this.icon.length) {
            return this.icon[par1];
        }
        return this.icon[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int i = 0; i < this.names.length; ++i) {
            par3List.add(new ItemStack((Item)this, 1, i));
        }
    }
}
