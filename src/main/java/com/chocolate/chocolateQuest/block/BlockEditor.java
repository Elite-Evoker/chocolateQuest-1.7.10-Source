package com.chocolate.chocolateQuest.block;

import net.minecraft.tileentity.TileEntity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockContainer;

public class BlockEditor extends BlockContainer
{
    public IIcon icon1;
    public IIcon icon2;
    public IIcon icon3;
    
    public BlockEditor() {
        super(Material.cake);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("chocolatequest:e0");
        this.icon1 = iconRegister.registerIcon("chocolatequest:e1");
        this.icon2 = iconRegister.registerIcon("chocolatequest:e2");
        this.icon3 = iconRegister.registerIcon("chocolatequest:e3");
    }
    
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        final BlockEditorTileEntity block = (BlockEditorTileEntity)par1World.getTileEntity(par2, par3, par4);
        par5EntityPlayer.openGui((Object)ChocolateQuest.instance, 1, par1World, par2, par3, par4);
        return true;
    }
    
    public IIcon getIcon(final int i, final int j) {
        switch (i) {
            case 2: {
                return this.icon1;
            }
            case 4: {
                return this.blockIcon;
            }
            case 0: {
                return this.icon3;
            }
            default: {
                return this.icon2;
            }
        }
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return 0;
    }
    
    public TileEntity createNewTileEntity(final World world, final int var2) {
        return new BlockEditorTileEntity();
    }
}
