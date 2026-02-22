package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.item.EnumRarity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class ItemSwordWalker extends ItemBaseSwordDefensive
{
    public ItemSwordWalker() {
        super(Item.ToolMaterial.EMERALD, "swordEnd");
        this.setMaxDurability(2024);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking()) {
            world.playSoundAtEntity((Entity)entityPlayer, "mob.endermen.portal", 1.0f, 1.0f);
            for (int i = 0; i < 6; ++i) {
                world.spawnParticle("portal", entityPlayer.posX + ItemSwordWalker.itemRand.nextFloat() - 0.5, entityPlayer.posY + ItemSwordWalker.itemRand.nextFloat() - 0.5, entityPlayer.posZ + ItemSwordWalker.itemRand.nextFloat() - 0.5, (double)(ItemSwordWalker.itemRand.nextFloat() - 0.5f), (double)(ItemSwordWalker.itemRand.nextFloat() - 0.5f), (double)(ItemSwordWalker.itemRand.nextFloat() - 0.5f));
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
                world.spawnParticle("portal", entityPlayer.posX + ItemSwordWalker.itemRand.nextFloat() - 0.5, entityPlayer.posY + ItemSwordWalker.itemRand.nextFloat() - 0.5, entityPlayer.posZ + ItemSwordWalker.itemRand.nextFloat() - 0.5, (double)(ItemSwordWalker.itemRand.nextFloat() - 0.5f), (double)(ItemSwordWalker.itemRand.nextFloat() - 0.5f), (double)(ItemSwordWalker.itemRand.nextFloat() - 0.5f));
            }
        }
        return super.onItemRightClick(itemStack, world, entityPlayer);
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return super.getIsRepairable(itemToRepair, itemMaterial);
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    public int getEntityLifespan(final ItemStack itemStack, final World world) {
        return 24000;
    }
}
