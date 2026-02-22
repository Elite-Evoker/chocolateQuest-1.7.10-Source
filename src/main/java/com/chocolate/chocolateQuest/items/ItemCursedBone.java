package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.entity.mob.EntityLich;
import com.chocolate.chocolateQuest.entity.mob.EntityNecromancer;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.EntitySummonedUndead;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import net.minecraft.item.Item;

public class ItemCursedBone extends Item implements IRangedWeapon
{
    public ItemCursedBone() {
        this.setMaxStackSize(1);
        this.setMaxDurability(8);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:cursedBone");
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            final EntitySummonedUndead e = new EntitySummonedUndead(world, (EntityLivingBase)entityPlayer, 0);
            e.setlvl((byte)2);
            final double dist = 3.0;
            Vec3 dest = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
            final Vec3 look = entityPlayer.getLookVec();
            dest = dest.addVector(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
            e.setPosition(dest.xCoord, dest.yCoord + 1.0, dest.zCoord);
            world.spawnEntityInWorld((Entity)e);
            itemstack.damageItem(1, (EntityLivingBase)entityPlayer);
            entityPlayer.attackEntityFrom(DamageSource.wither, 2.0f);
        }
        return itemstack;
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.uncommon;
    }
    
    public float getRange(final EntityLivingBase shooter, final ItemStack is) {
        return 256.0f;
    }
    
    public int getCooldown(final EntityLivingBase shooter, final ItemStack is) {
        if (shooter instanceof EntityNecromancer || shooter instanceof EntityLich) {
            return 150;
        }
        return 300;
    }
    
    public void shootFromEntity(final EntityLivingBase shooter, final ItemStack is, final int angle, final Entity target) {
        final World world = shooter.worldObj;
        final EntitySummonedUndead e = new EntitySummonedUndead(world, shooter, 0);
        e.setlvl((byte)is.getMetadata());
        e.setPosition(shooter.posX, shooter.posY, shooter.posZ);
        world.spawnEntityInWorld((Entity)e);
    }
    
    public boolean canBeUsedByEntity(final Entity shooter) {
        return true;
    }
    
    public boolean isMeleeWeapon(final EntityLivingBase shooter, final ItemStack is) {
        return false;
    }
    
    public boolean shouldUpdate(final EntityLivingBase shooter) {
        return false;
    }
    
    public int startAiming(final ItemStack is, final EntityLivingBase shooter, final Entity target) {
        return 30;
    }
}
