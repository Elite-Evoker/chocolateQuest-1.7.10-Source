package com.chocolate.chocolateQuest.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
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

public class BlockDungeonBrick extends Block
{
    public IIcon[] icon;
    
    public BlockDungeonBrick() {
        super(Material.rock);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.icon = new IIcon[16];
        for (int i = 0; i < 16; ++i) {
            this.icon[i] = iconRegister.registerIcon("chocolatequest:w" + i);
        }
    }
    
    public IIcon getIcon(final int i, final int j) {
        return this.icon[j];
    }
    
    public int damageDropped(final int metadata) {
        return metadata;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs par2CreativeTabs, final List list) {
        for (int i = 0; i < 16; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    public void onBlockHarvested(final World par1World, final int x, final int y, final int z, final int par5, final EntityPlayer par6EntityPlayer) {
        super.onBlockHarvested(par1World, x, y, z, par5, par6EntityPlayer);
    }
    
    public MapColor getMapColor(final int data) {
        switch (data) {
            case 3: {
                return MapColor.lightBlueColor;
            }
            case 4: {
                return MapColor.limeColor;
            }
            case 5: {
                return MapColor.greenColor;
            }
            case 6: {
                return MapColor.pinkColor;
            }
            case 7: {
                return MapColor.brownColor;
            }
            case 8: {
                return MapColor.dirtColor;
            }
            case 9: {
                return MapColor.cyanColor;
            }
            case 10: {
                return MapColor.purpleColor;
            }
            case 11: {
                return MapColor.blueColor;
            }
            case 12: {
                return MapColor.adobeColor;
            }
            case 13: {
                return MapColor.foliageColor;
            }
            case 14: {
                return MapColor.redColor;
            }
            case 15: {
                return MapColor.blackColor;
            }
            default: {
                return super.getMapColor(data);
            }
        }
    }
}
