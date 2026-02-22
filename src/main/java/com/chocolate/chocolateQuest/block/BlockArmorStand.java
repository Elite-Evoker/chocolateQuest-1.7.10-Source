package com.chocolate.chocolateQuest.block;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class BlockArmorStand extends BlockContainer
{
    int armorSlots;
    
    public BlockArmorStand() {
        super(Material.wood);
        this.armorSlots = 4;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("chocolatequest:armorStand");
    }
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        final ItemStack currentItem = player.getCurrentEquippedItem();
        final BlockArmorStandTileEntity stand = (BlockArmorStandTileEntity)world.getTileEntity(x, y, z);
        if (player.isSneaking()) {
            player.openGui((Object)ChocolateQuest.instance, 2, world, x, y, z);
            return true;
        }
        if (currentItem != null && currentItem.getItem() instanceof ItemArmor) {
            final int i = 3 - ((ItemArmor)currentItem.getItem()).armorType;
            final ItemStack stack = stand.cargoItems[i];
            stand.cargoItems[i] = currentItem;
            player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
            return true;
        }
        if (stand.cargoItems != null) {
            for (int i = 0; i < this.armorSlots; ++i) {
                final ItemStack stack = stand.cargoItems[i];
                final ItemStack currentItemArmor = player.inventory.armorInventory[i];
                if (currentItem != null || !player.capabilities.isCreativeMode) {
                    stand.cargoItems[i] = currentItemArmor;
                }
                player.inventory.armorInventory[i] = stack;
            }
            return true;
        }
        return super.onBlockActivated(world, x, y + 1, z, player, par6, par7, par8, par9);
    }
    
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase entity, final ItemStack itemstack) {
        super.onBlockPlacedBy(world, x, y, z, entity, itemstack);
        final BlockArmorStandTileEntity te = (BlockArmorStandTileEntity)this.createNewTileEntity(world, itemstack.getMetadata());
        te.rotation = (int)(entity.rotationYaw - 180.0f);
        world.setTileEntity(x, y, z, (TileEntity)te);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("chocolatequest:armorStand");
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        this.setBlockBounds(0.2f, 0.0f, 0.2f, 0.8f, 1.8f, 0.8f);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return -1;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public void breakBlock(final World world, final int x, final int y, final int z, final Block par5, final int par6) {
        final BlockArmorStandTileEntity stand = (BlockArmorStandTileEntity)world.getTileEntity(x, y, z);
        if (stand != null) {
            for (int i = 0; i < stand.cargoItems.length; ++i) {
                if (stand.cargoItems[i] != null && !world.isRemote) {
                    final EntityItem e = new EntityItem(world, x + 0.5, (double)(y + 1), z + 0.5, stand.cargoItems[i]);
                    world.spawnEntityInWorld((Entity)e);
                }
            }
        }
        super.breakBlock(world, x, y, z, par5, par6);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new BlockArmorStandTileEntity();
    }
}
