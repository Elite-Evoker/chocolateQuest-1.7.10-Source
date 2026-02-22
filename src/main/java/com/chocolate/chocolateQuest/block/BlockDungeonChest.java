package com.chocolate.chocolateQuest.block;

import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class BlockDungeonChest extends BlockContainer
{
    public BlockDungeonChest() {
        super(Material.wood);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new BlockDungeonChestTileEntity();
    }
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float posX, final float posY, final float posZ) {
        player.openGui((Object)"chocolateQuest", 4, world, x, y, z);
        return super.onBlockActivated(world, x, y, z, player, side, posX, posY, posZ);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 1.0f, 0.9f);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
}
