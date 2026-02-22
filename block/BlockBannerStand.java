package com.chocolate.chocolateQuest.block;

import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.IBlockAccess;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class BlockBannerStand extends BlockContainer
{
    public BlockBannerStand() {
        super(Material.wood);
    }
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        final ItemStack playerItem = player.getCurrentEquippedItem();
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof BlockBannerStandTileEntity) {
            final BlockBannerStandTileEntity stand = (BlockBannerStandTileEntity)te;
            if (stand.item != null) {
                if (!world.isRemote) {
                    final EntityItem e = new EntityItem(world, (double)x, (double)(y + 1), (double)z, stand.item);
                    world.spawnEntityInWorld((Entity)e);
                }
                stand.hasFlag = false;
                stand.item = null;
                return true;
            }
            if (playerItem != null) {
                if (playerItem.getItem() == ChocolateQuest.banner) {
                    stand.item = playerItem.splitStack(1);
                    stand.rotation = (int)player.rotationYaw - 180;
                    return stand.hasFlag = true;
                }
            }
        }
        else {
            world.setTileEntity(x, y, z, this.createNewTileEntity(world, world.getBlockMetadata(x, y, z)));
        }
        return super.onBlockActivated(world, x, y + 1, z, player, par6, par7, par8, par9);
    }
    
    public void onEntityWalking(final World world, final int par2, final int par3, final int par4, final Entity par5Entity) {
        super.onEntityWalking(world, par2, par3, par4, par5Entity);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        this.setBlockBounds(0.4f, 0.0f, 0.4f, 0.6f, 0.2f, 0.6f);
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
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public void breakBlock(final World world, final int x, final int y, final int z, final Block par5, final int par6) {
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof BlockBannerStandTileEntity) {
            final BlockBannerStandTileEntity stand = (BlockBannerStandTileEntity)te;
            if (stand != null && stand.hasFlag && !world.isRemote) {
                final EntityItem e = new EntityItem(world, (double)x, (double)y, (double)z, stand.item);
                world.spawnEntityInWorld((Entity)e);
            }
        }
        super.breakBlock(world, x, y, z, par5, par6);
    }
    
    public IIcon getIcon(final int side, final int metadata) {
        if (metadata == 1) {
            return Blocks.enchanting_table.getIcon(side, metadata);
        }
        return Blocks.planks.getIcon(side, metadata);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new BlockBannerStandTileEntity();
    }
}
