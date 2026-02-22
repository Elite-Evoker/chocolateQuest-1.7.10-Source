package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemAmmoLoader extends ItemPistol
{
    @Override
    public int getAmmoLoaderStackSize(final ItemStack is) {
        return 64;
    }
    
    @Override
    public int getAmmoLoaderAmmount(final ItemStack is) {
        return 8;
    }
    
    @Override
    public boolean canBeUsedByEntity(final Entity entity) {
        return false;
    }
    
    @Override
    public void shootFromEntity(final EntityLivingBase shooter, final ItemStack is, final int angle, final Entity target) {
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:ammoLoader");
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.openGui((Object)ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
        return itemstack;
    }
}
