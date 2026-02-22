package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.item.EnumAction;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class ItemGolemMachineGun extends ItemGolemWeapon
{
    public ItemGolemMachineGun() {
        super(0, 20.0f, 10.0f, 0, 0);
    }
    
    @Override
    public void shootFromGolem(final EntityLivingBase shooter, final ItemStack is, final int angle, final Entity target) {
    }
    
    @Override
    public boolean shouldUpdate(final EntityLivingBase shooter) {
        return true;
    }
    
    @Override
    public void onUpdate(final ItemStack is, final World world, final Entity entity, final int par4, final boolean par5) {
        final boolean shoot = false;
        if (entity instanceof EntityHumanBase && world.getWorldTime() % 3L == 0L) {
            super.shootFromGolem((EntityLivingBase)entity, is, par4, null);
        }
        if (entity instanceof EntityPlayer && world.getWorldTime() % 2L == 0L && ((EntityPlayer)entity).getItemInUse() == is) {
            this.shoot(is, world, (EntityPlayer)entity);
        }
    }
    
    @Override
    protected int getAmmo(final ItemStack itemstack, final EntityPlayer entityPlayer) {
        int bulletType = -1;
        for (int index = 0; index < entityPlayer.inventory.getSizeInventory(); ++index) {
            final ItemStack currentIs = entityPlayer.inventory.getStackInSlot(index);
            if (currentIs != null && currentIs.stackTagCompound != null && currentIs.getItem() == ChocolateQuest.ammoLoader) {
                bulletType = super.getAmmo(currentIs, entityPlayer);
                if (bulletType != -1) {
                    return bulletType;
                }
            }
        }
        return bulletType;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    @Override
    public EntityBaseBall getBall(final World world, final EntityLivingBase shooter, final double x, final double y, final double z) {
        final EntityBaseBall ball = super.getBall(world, shooter, x, y, z);
        ball.setDamageMultiplier(0.7f);
        return ball;
    }
}
