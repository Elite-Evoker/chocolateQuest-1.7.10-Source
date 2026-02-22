package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.item.EnumRarity;
import com.chocolate.chocolateQuest.items.gun.ItemPistol;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import com.chocolate.chocolateQuest.API.IRangedWeapon;

public class ItemSpearGun extends ItemBaseSpear implements IRangedWeapon, ILoadableGun
{
    public ItemSpearGun() {
        super(Item.ToolMaterial.IRON, 4.0f);
        this.setMaxDurability(2048);
        this.cooldown = 50;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:spearGun");
    }
    
    @Override
    public void doSpecialSkill(final ItemStack itemstack, final World world, final EntityLivingBase entityPlayer) {
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity)new EntityBaseBall(world, entityPlayer, 1, 4));
        }
        itemstack.damageItem(1, entityPlayer);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking()) {
            entityPlayer.openGui((Object)ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
            return itemstack;
        }
        return super.onItemRightClick(itemstack, world, entityPlayer);
    }
    
    @Override
    public void stopUsingItem(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer, final Entity target) {
        if (!world.isRemote && target == null) {
            ((ItemPistol)ChocolateQuest.revolver).shoot(itemstack, world, entityPlayer);
            itemstack.damageItem(1, (EntityLivingBase)entityPlayer);
        }
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    public int getEntityLifespan(final ItemStack itemStack, final World world) {
        return 24000;
    }
    
    @Override
    public void shootFromEntity(final EntityLivingBase shooter, final ItemStack is, final int angle, final Entity target) {
        ((ItemPistol)ChocolateQuest.revolver).shootFromEntity(shooter, is, angle, target);
    }
    
    @Override
    public float getRange(final EntityLivingBase shooter, final ItemStack is) {
        return 100.0f;
    }
    
    @Override
    public int getCooldown(final EntityLivingBase shooter, final ItemStack is) {
        return 10;
    }
    
    @Override
    public boolean canBeUsedByEntity(final Entity entity) {
        return true;
    }
    
    @Override
    public boolean isMeleeWeapon(final EntityLivingBase shooter, final ItemStack is) {
        return true;
    }
    
    @Override
    public boolean shouldUpdate(final EntityLivingBase shooter) {
        return false;
    }
    
    @Override
    public int getAmmoLoaderStackSize(final ItemStack is) {
        return 12;
    }
    
    @Override
    public int getAmmoLoaderAmmount(final ItemStack is) {
        final int loaders = Awakements.getEnchantLevel(is, Awakements.ammoCapacity);
        return 1 + loaders;
    }
    
    @Override
    public boolean isValidAmmo(final ItemStack is) {
        return is != null && is.getItem() == ChocolateQuest.bullet;
    }
    
    @Override
    public int getStackIcon(final ItemStack is) {
        return 85;
    }
    
    @Override
    public int startAiming(final ItemStack is, final EntityLivingBase shooter, final Entity target) {
        return 30;
    }
}
