package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemGolemWeapon extends ItemPistol
{
    final int projectileDamage;
    final int ammoCapacity;
    
    public ItemGolemWeapon() {
        this(10, 16.0f, 5.0f);
    }
    
    public ItemGolemWeapon(final int cooldown, final float range, final float accuracy) {
        this(cooldown, range, accuracy, 1, 16);
    }
    
    public ItemGolemWeapon(final int cooldown, final float range, final float accuracy, final int lvl, final int capacity) {
        super(cooldown, range * range, accuracy);
        this.projectileDamage = lvl;
        this.ammoCapacity = capacity;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister iconRegister) {
    }
    
    @Override
    public void shootFromEntity(final EntityLivingBase shooter, final ItemStack is, final int angle, final Entity target) {
        this.shootFromGolem(shooter, is, angle, target);
    }
    
    public void shootFromGolem(final EntityLivingBase shooter, final ItemStack is, final int angle, final Entity target) {
        if (!shooter.worldObj.isRemote) {
            final double armDist = shooter.width * 2.0f;
            final double posX = shooter.posX - Math.sin(Math.toRadians(shooter.rotationYawHead + angle)) * armDist;
            final double posY = shooter.posY + 1.6;
            final double posZ = shooter.posZ + Math.cos(Math.toRadians(shooter.rotationYawHead + angle)) * armDist;
            EntityBaseBall ball;
            if (target != null) {
                ball = this.getBall(shooter.worldObj, shooter, target.posX - posX, target.posY + target.height - posY, target.posZ - posZ);
            }
            else {
                final double ry = Math.toRadians(shooter.rotationYawHead - 180.0f);
                final double x = Math.sin(ry);
                final double z = -Math.cos(ry);
                final double y = -Math.sin(Math.toRadians(shooter.rotationPitch * 2.0f - 1.0f));
                final EntityBaseBall ball2;
                ball = (ball2 = this.getBall(shooter.worldObj, shooter, x, y, z));
                ball2.posY -= shooter.height / 2.0f;
            }
            ball.setPosition(posX, posY, posZ);
            shooter.worldObj.spawnEntityInWorld((Entity)ball);
        }
    }
    
    @Override
    public EntityBaseBall getBall(final World world, final EntityLivingBase shooter, final double x, final double y, final double z) {
        float accuracy = this.accuracy;
        byte projectile = 1;
        if (shooter instanceof EntityHumanBase) {
            accuracy += ((EntityHumanBase)shooter).accuracy;
            if (shooter instanceof EntityGolemMecha) {
                projectile = 2;
            }
        }
        return new EntityBaseBall(shooter.worldObj, shooter, x, y, z, projectile, 0, accuracy);
    }
    
    @Override
    public void onUpdate(final ItemStack is, final World world, final Entity entity, final int par4, final boolean par5) {
        super.onUpdate(is, world, entity, par4, par5);
    }
    
    @Override
    public int getCooldown(final EntityLivingBase shooter, final ItemStack is) {
        if (shooter instanceof EntityGolemMecha) {
            return Math.max(this.cooldownBase - 10, 0);
        }
        return this.cooldownBase;
    }
    
    @Override
    public int getAmmoLoaderStackSize(final ItemStack is) {
        return this.ammoCapacity;
    }
}
