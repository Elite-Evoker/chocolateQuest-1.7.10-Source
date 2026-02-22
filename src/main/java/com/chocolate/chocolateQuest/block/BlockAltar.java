package com.chocolate.chocolateQuest.block;

import net.minecraft.block.Block;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.world.IBlockAccess;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class BlockAltar extends BlockContainer
{
    public BlockAltar() {
        super(Material.wood);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
    }
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        final ItemStack playerItem = player.getCurrentEquippedItem();
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof BlockAltarTileEntity) {
            final BlockAltarTileEntity stand = (BlockAltarTileEntity)te;
            if (stand.item != null) {
                if (!world.isRemote) {
                    final EntityItem e = new EntityItem(world, (double)x, (double)(y + 1), (double)z, stand.item);
                    world.spawnEntityInWorld((Entity)e);
                }
                stand.item = null;
                return true;
            }
            if (playerItem != null) {
                stand.item = playerItem.splitStack(1);
                stand.rotation = (int)player.rotationYaw - 180;
                return true;
            }
        }
        else {
            world.setTileEntity(x, y, z, this.createNewTileEntity(world, world.getBlockMetadata(x, y, z)));
        }
        return super.onBlockActivated(world, x, y + 1, z, player, par6, par7, par8, par9);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        if (world == null) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            return;
        }
        float minX = 0.2f;
        byte xAxis = 0;
        byte zAxis = 0;
        if (world.getBlock(x - 1, y, z) == this) {
            minX = 0.0f;
            ++xAxis;
        }
        float maxX = 0.8f;
        if (world.getBlock(x + 1, y, z) == this) {
            maxX = 1.0f;
            ++xAxis;
        }
        float minZ = 0.2f;
        if (world.getBlock(x, y, z - 1) == this) {
            minZ = 0.0f;
            ++zAxis;
        }
        float maxZ = 0.8f;
        if (world.getBlock(x, y, z + 1) == this) {
            maxZ = 1.0f;
            ++zAxis;
        }
        float minY = 0.0f;
        if (xAxis == 2 || zAxis == 2) {
            minY = 0.9f;
        }
        this.setBlockBounds(minX, minY, minZ, maxX, 1.0f, maxZ);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return ClientProxy.tableRenderID;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public void breakBlock(final World world, final int x, final int y, final int z, final Block par5, final int par6) {
        final BlockAltarTileEntity stand = (BlockAltarTileEntity)world.getTileEntity(x, y, z);
        if (stand != null && stand.item != null && !world.isRemote) {
            final EntityItem e = new EntityItem(world, x + 0.5, (double)(y + 1), z + 0.5, stand.item);
            world.spawnEntityInWorld((Entity)e);
        }
        super.breakBlock(world, x, y, z, par5, par6);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new BlockAltarTileEntity();
    }
}
