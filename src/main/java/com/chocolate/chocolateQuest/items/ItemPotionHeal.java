package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumAction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class ItemPotionHeal extends Item
{
    public ItemPotionHeal() {
        this.setHasSubtypes(true);
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer player) {
        player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:potion");
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        final int i = itemstack.getMetadata();
        String num = " ";
        switch (i) {
            case 1: {
                num = " I";
                break;
            }
            case 2: {
                num = " II";
                break;
            }
            case 3: {
                num = " III";
                break;
            }
            case 4: {
                num = " IV";
                break;
            }
            case 5: {
                num = " V";
                break;
            }
        }
        return super.getItemStackDisplayName(itemstack) + num;
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityPlayer entityplayer, final int i) {
        final int var6 = this.getMaxItemUseDuration(itemstack) - i;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return itemStack.getMetadata() * 10 + 25;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.drink;
    }
    
    public ItemStack onItemUseFinish(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (entityplayer.getMaxHealth() > entityplayer.getHealth() && !entityplayer.capabilities.isCreativeMode) {
            entityplayer.heal((float)(4 + itemstack.getMetadata() * 2));
            entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
        }
        return itemstack;
    }
}
