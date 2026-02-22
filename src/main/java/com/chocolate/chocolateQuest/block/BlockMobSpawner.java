package com.chocolate.chocolateQuest.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class BlockMobSpawner extends BlockContainer
{
    public BlockMobSpawner() {
        super(Material.rock);
    }
    
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return 0;
    }
    
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public IIcon getIcon(final int i, final int j) {
        return Blocks.mob_spawner.getIcon(i, j);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new BlockMobSpawnerTileEntity();
    }
}
