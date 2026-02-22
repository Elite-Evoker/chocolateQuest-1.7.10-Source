package com.chocolate.chocolateQuest.items.gun;

import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class ItemBubbleCannon extends ItemGolemWeapon
{
    public ItemBubbleCannon(final int cooldown, final float range, final float accuracy, final int lvl) {
        super(cooldown, range, accuracy, lvl, 16);
    }
    
    @Override
    public EntityBaseBall getBall(final World world, final EntityLivingBase shooter, final double x, final double y, final double z) {
        return new EntityBaseBall(shooter.worldObj, shooter, x, y, z, 7, 0, this.accuracy);
    }
    
    @Override
    public boolean freeAmmo() {
        return true;
    }
    
    @Override
    public boolean shoot(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            final EntityBaseBall ball = new EntityBaseBall(world, (EntityLivingBase)entityPlayer, 7, 0);
            final float accuracy = this.accuracy / 100.0f;
            final EntityBaseBall entityBaseBall = ball;
            entityBaseBall.motionX += ItemBubbleCannon.itemRand.nextGaussian() * accuracy;
            final EntityBaseBall entityBaseBall2 = ball;
            entityBaseBall2.motionY += ItemBubbleCannon.itemRand.nextGaussian() * accuracy;
            final EntityBaseBall entityBaseBall3 = ball;
            entityBaseBall3.motionZ += ItemBubbleCannon.itemRand.nextGaussian() * accuracy;
            final int power = Awakements.getEnchantLevel(itemstack, Awakements.power);
            ball.setDamageMultiplier(1.0f + power / 10.0f);
            world.spawnEntityInWorld((Entity)ball);
        }
        return true;
    }
}
