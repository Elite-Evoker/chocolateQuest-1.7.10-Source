package com.chocolate.chocolateQuest.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class BlockDecoration extends Block
{
    public IIcon[] icon;
    public String[] names;
    public String textureName;
    
    public BlockDecoration(final Material p_i45394_1_, final String textureName, final String[] names) {
        super(p_i45394_1_);
        this.textureName = textureName;
        this.names = names;
        this.icon = new IIcon[names.length];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        for (int i = 0; i < this.icon.length; ++i) {
            this.icon[i] = iconRegister.registerIcon("chocolatequest:" + this.textureName + i);
        }
    }
    
    public IIcon getIcon(final int i, final int j) {
        if (j < this.icon.length) {
            return this.icon[j];
        }
        return this.icon[0];
    }
    
    public int damageDropped(final int metadata) {
        return metadata;
    }
    
    public String getBlockName(final int i) {
        if (i < this.names.length) {
            return this.names[i];
        }
        return this.names[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs par2CreativeTabs, final List list) {
        for (int i = 0; i < this.names.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
