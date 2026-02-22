package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemGoldenFeather extends Item
{
    public ItemGoldenFeather() {
        this.setMaxStackSize(1);
        this.setMaxDurability(385);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:goldenFeather");
    }
    
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (entity.fallDistance >= 3.0f) {
            itemstack.damageItem(1, (EntityLivingBase)entity);
            entity.fallDistance = 0.0f;
            for (int i = 0; i < 3; ++i) {
                entity.worldObj.spawnParticle("cloud", entity.posX, entity.posY - 2.0, entity.posZ, (double)((ItemGoldenFeather.itemRand.nextFloat() - 0.5f) / 2.0f), -0.5, (double)((ItemGoldenFeather.itemRand.nextFloat() - 0.5f) / 2.0f));
            }
        }
        super.onUpdate(itemstack, world, entity, par4, par5);
    }
    
    public boolean isDamageable() {
        return true;
    }
    
    public String getTextureFile() {
        return "/bdimg/items.png";
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.uncommon;
    }
}
