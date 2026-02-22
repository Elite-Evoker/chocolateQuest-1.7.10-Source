package com.chocolate.chocolateQuest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockGlass;

public class BlockEditorVoid extends BlockGlass
{
    public BlockEditorVoid() {
        super(Material.iron, false);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("chocolatequest:null");
    }
}
