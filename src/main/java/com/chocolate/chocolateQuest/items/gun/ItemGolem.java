package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemGolem extends Item
{
    String icon;
    
    public ItemGolem(final String name) {
        this.icon = name;
        this.setMaxStackSize(1);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.icon);
    }
    
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        boolean spawnGolem = entityPlayer.capabilities.isCreativeMode;
        final Block block = Blocks.iron_block;
        if (world.getBlock(i, j, k) == block && world.getBlock(i, j - 1, k) == block) {
            if (world.getBlock(i + 1, j, k) == block && world.getBlock(i - 1, j, k) == block) {
                world.setBlockToAir(i + 1, j, k);
                world.setBlockToAir(i - 1, j, k);
                spawnGolem = true;
            }
            else if (world.getBlock(i, j, k + 1) == block && world.getBlock(i, j, k - 1) == block) {
                world.setBlockToAir(i, j, k + 1);
                world.setBlockToAir(i, j, k - 1);
                spawnGolem = true;
            }
        }
        if (spawnGolem) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                --itemStack.stackSize;
                world.setBlockToAir(i, j, k);
                world.setBlockToAir(i, j - 1, k);
            }
            if (!world.isRemote) {
                final EntityGolemMecha golem = this.getGolem(world, entityPlayer);
                golem.setPosition((double)i, (double)(j + 1), (double)k);
                golem.setOwner((EntityLivingBase)entityPlayer);
                world.spawnEntityInWorld((Entity)golem);
            }
        }
        return false;
    }
    
    public EntityGolemMecha getGolem(final World world, final EntityPlayer entityPlayer) {
        return new EntityGolemMecha(world, (EntityLivingBase)entityPlayer);
    }
}
