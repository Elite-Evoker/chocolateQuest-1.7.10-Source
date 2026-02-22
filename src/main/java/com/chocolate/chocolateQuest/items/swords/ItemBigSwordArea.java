package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.item.EnumRarity;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class ItemBigSwordArea extends ItemBaseBroadSword
{
    final int weaponDamage = 8;
    
    public ItemBigSwordArea(final Item.ToolMaterial mat, final String texture, final float baseDamage) {
        super(mat, texture, baseDamage);
    }
    
    @Override
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        super.onUpdate(itemStack, world, entity, par4, par5);
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase entityliving1) {
        final float range = 1.5f;
        final World world = entityliving.worldObj;
        final double mx = entityliving.posX - range;
        final double my = entityliving.posY - range;
        final double mz = entityliving.posZ - range;
        final double max = entityliving.posX + range;
        final double may = entityliving.posY + range;
        final double maz = entityliving.posZ + range;
        final List<Entity> l = world.getEntitiesWithinAABBExcludingEntity((Entity)entityliving, AxisAlignedBB.getBoundingBox(mx, my, mz, max, may, maz));
        for (final Entity e : l) {
            if (e instanceof EntityLivingBase && e != entityliving1) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entityliving1), 4.0f);
                e.worldObj.spawnParticle("largeexplode", e.posX + ItemBigSwordArea.itemRand.nextFloat() - 0.5, e.posY + ItemBigSwordArea.itemRand.nextFloat(), e.posZ + ItemBigSwordArea.itemRand.nextFloat() - 0.5, (double)(ItemBigSwordArea.itemRand.nextFloat() - 0.5f), (double)(ItemBigSwordArea.itemRand.nextFloat() - 0.5f), (double)(ItemBigSwordArea.itemRand.nextFloat() - 0.5f));
            }
        }
        entityliving.worldObj.spawnParticle("largeexplode", entityliving.posX + ItemBigSwordArea.itemRand.nextFloat() - 0.5, entityliving.posY + ItemBigSwordArea.itemRand.nextFloat(), entityliving.posZ + ItemBigSwordArea.itemRand.nextFloat() - 0.5, (double)(ItemBigSwordArea.itemRand.nextFloat() - 0.5f), (double)(ItemBigSwordArea.itemRand.nextFloat() - 0.5f), (double)(ItemBigSwordArea.itemRand.nextFloat() - 0.5f));
        return super.hitEntity(itemstack, entityliving, entityliving1);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return super.onItemRightClick(itemStack, world, entityPlayer);
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
}
