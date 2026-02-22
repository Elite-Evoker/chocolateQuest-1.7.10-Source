package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemDaggerEnd extends ItemBaseDagger
{
    public ItemDaggerEnd() {
        super(Item.ToolMaterial.EMERALD, 3);
        this.setMaxDurability(2048);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:daggerNinja");
    }
    
    public int getMaxDurability() {
        return 2048;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking()) {
            world.playSoundAtEntity((Entity)entityPlayer, "mob.endermen.portal", 1.0f, 1.0f);
            for (int i = 0; i < 6; ++i) {
                world.spawnParticle("portal", entityPlayer.posX + ItemDaggerEnd.itemRand.nextFloat() - 0.5, entityPlayer.posY + ItemDaggerEnd.itemRand.nextFloat() - 0.5, entityPlayer.posZ + ItemDaggerEnd.itemRand.nextFloat() - 0.5, (double)(ItemDaggerEnd.itemRand.nextFloat() - 0.5f), (double)(ItemDaggerEnd.itemRand.nextFloat() - 0.5f), (double)(ItemDaggerEnd.itemRand.nextFloat() - 0.5f));
            }
            double x = -Math.sin(Math.toRadians(entityPlayer.rotationYaw));
            double z = Math.cos(Math.toRadians(entityPlayer.rotationYaw));
            final double y = -Math.sin(Math.toRadians(entityPlayer.rotationPitch));
            x *= 1.0 - Math.abs(y);
            z *= 1.0 - Math.abs(y);
            final int dist = 4;
            entityPlayer.setPosition(entityPlayer.posX + x * dist, entityPlayer.posY + y * dist, entityPlayer.posZ + z * dist);
            itemStack.damageItem(1, (EntityLivingBase)entityPlayer);
            for (int j = 0; j < 6; ++j) {
                world.spawnParticle("portal", entityPlayer.posX + ItemDaggerEnd.itemRand.nextFloat() - 0.5, entityPlayer.posY + ItemDaggerEnd.itemRand.nextFloat() - 0.5, entityPlayer.posZ + ItemDaggerEnd.itemRand.nextFloat() - 0.5, (double)(ItemDaggerEnd.itemRand.nextFloat() - 0.5f), (double)(ItemDaggerEnd.itemRand.nextFloat() - 0.5f), (double)(ItemDaggerEnd.itemRand.nextFloat() - 0.5f));
            }
            itemStack.damageItem(1, (EntityLivingBase)entityPlayer);
        }
        else {
            itemStack.damageItem(1, (EntityLivingBase)entityPlayer);
        }
        entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 100, 5));
        return super.onItemRightClick(itemStack, world, entityPlayer);
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return super.getIsRepairable(itemToRepair, itemMaterial);
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    public int getEntityLifespan(final ItemStack itemStack, final World world) {
        return 24000;
    }
}
