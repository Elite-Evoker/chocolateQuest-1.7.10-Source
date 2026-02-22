package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.item.EnumRarity;
import java.util.List;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import net.minecraft.item.Item;

public class ItemPistol extends Item implements IRangedWeapon, ILoadableGun
{
    final int NONE = -1;
    int cooldownBase;
    final float accuracy;
    final float range;
    
    public ItemPistol() {
        this(10, 100.0f, 10.0f);
    }
    
    public ItemPistol(final int cooldown, final float range, final float accuracy) {
        this.cooldownBase = 10;
        this.cooldownBase = cooldown;
        this.range = range;
        this.accuracy = accuracy;
        this.setMaxStackSize(1);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:revolver");
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking() && !this.freeAmmo()) {
            entityPlayer.openGui((Object)ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
            return itemstack;
        }
        if (itemstack.getMetadata() == 0 && this.shoot(itemstack, world, entityPlayer)) {
            itemstack.setMetadata(this.getCooldown(itemstack));
        }
        return super.onItemRightClick(itemstack, world, entityPlayer);
    }
    
    public boolean shoot(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        final int NONE = -1;
        int bulletType = this.getAmmo(itemstack, entityPlayer);
        if (this.freeAmmo()) {
            bulletType = 1;
        }
        if (entityPlayer.capabilities.isCreativeMode && bulletType == -1) {
            bulletType = 1;
        }
        if (bulletType != -1) {
            if (!world.isRemote) {
                final EntityBaseBall ball = new EntityBaseBall(world, (EntityLivingBase)entityPlayer, 1, bulletType);
                final float accuracy = this.accuracy / 100.0f;
                final EntityBaseBall entityBaseBall = ball;
                entityBaseBall.motionX += ItemPistol.itemRand.nextGaussian() * accuracy;
                final EntityBaseBall entityBaseBall2 = ball;
                entityBaseBall2.motionY += ItemPistol.itemRand.nextGaussian() * accuracy;
                final EntityBaseBall entityBaseBall3 = ball;
                entityBaseBall3.motionZ += ItemPistol.itemRand.nextGaussian() * accuracy;
                final int power = Awakements.getEnchantLevel(itemstack, Awakements.power);
                ball.setDamageMultiplier(1.0f + power / 10.0f);
                world.spawnEntityInWorld((Entity)ball);
            }
            return true;
        }
        return false;
    }
    
    public boolean freeAmmo() {
        return false;
    }
    
    protected int getAmmo(final ItemStack itemstack, final EntityPlayer entityPlayer) {
        int bulletType = -1;
        final ItemStack[] ammo = InventoryBag.getCargo(itemstack);
        int i = 0;
        while (i < ammo.length) {
            if (ammo[i] != null && ammo[i].getItem() == ChocolateQuest.bullet) {
                bulletType = ammo[i].getMetadata();
                final int ammoSaver = Awakements.getEnchantLevel(itemstack, Awakements.ammoSaver);
                if (!entityPlayer.capabilities.isCreativeMode) {
                    if (ammoSaver != 0) {
                        if (ItemPistol.itemRand.nextInt(2 + ammoSaver) < 2) {
                            break;
                        }
                    }
                    final ItemStack itemStack = ammo[i];
                    --itemStack.stackSize;
                    if (ammo[i].stackSize <= 0) {
                        ammo[i] = null;
                    }
                    InventoryBag.saveCargo(itemstack, ammo);
                    break;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return bulletType;
    }
    
    public int getAmmoLoaderStackSize(final ItemStack is) {
        return 8;
    }
    
    public int getAmmoLoaderAmmount(final ItemStack is) {
        final int loaders = Awakements.getEnchantLevel(is, Awakements.ammoCapacity);
        return 1 + loaders;
    }
    
    public boolean isValidAmmo(final ItemStack is) {
        return is != null && is.getItem() == ChocolateQuest.bullet;
    }
    
    public int getStackIcon(final ItemStack is) {
        return 85;
    }
    
    public void addInformation(final ItemStack is, final EntityPlayer player, final List list, final boolean par4) {
        super.addInformation(is, player, list, par4);
        for (final Awakements a : Awakements.awekements) {
            if (Awakements.hasEnchant(is, a)) {
                list.add(a.getDescription(is));
            }
        }
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.rare;
    }
    
    public boolean isDamageable() {
        return true;
    }
    
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (itemStack.getMetadata() > 0) {
            itemStack.setMetadata(itemStack.getMetadata() - 1);
        }
        super.onUpdate(itemStack, world, entity, par4, par5);
    }
    
    public int getMaxDurability() {
        return this.cooldownBase;
    }
    
    public boolean shouldRotateAroundWhenRendering() {
        return false;
    }
    
    public boolean isFull3D() {
        return false;
    }
    
    public int getCooldown(final ItemStack is) {
        return this.cooldownBase;
    }
    
    public float getRange(final EntityLivingBase shooter, final ItemStack is) {
        return this.range;
    }
    
    public int getCooldown(final EntityLivingBase shooter, final ItemStack is) {
        return this.cooldownBase;
    }
    
    public void shootFromEntity(final EntityLivingBase shooter, final ItemStack is, final int angle, final Entity target) {
        if (!shooter.worldObj.isRemote) {
            final double armDist = 2.0;
            final double posX = shooter.posX - Math.sin(Math.toRadians(shooter.rotationYaw + angle)) * 2.0;
            final double posY = shooter.posY + 1.6;
            final double posZ = shooter.posZ + Math.cos(Math.toRadians(shooter.rotationYaw + angle)) * 2.0;
            EntityBaseBall ball;
            if (target != null) {
                ball = this.getBall(shooter.worldObj, shooter, target.posX - posX, target.posY + target.height - posY, target.posZ - posZ);
            }
            else {
                final double ry = Math.toRadians(shooter.rotationYaw - 180.0f);
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
    
    public EntityBaseBall getBall(final World world, final EntityLivingBase shooter, final double x, final double y, final double z) {
        float accuracy = 5.0f;
        if (shooter instanceof EntityHumanBase) {
            accuracy += ((EntityHumanBase)shooter).accuracy;
        }
        return new EntityBaseBall(shooter.worldObj, shooter, x, y, z, 1, 1, accuracy);
    }
    
    public boolean canBeUsedByEntity(final Entity entity) {
        return true;
    }
    
    public boolean isMeleeWeapon(final EntityLivingBase shooter, final ItemStack is) {
        return false;
    }
    
    public boolean shouldUpdate(final EntityLivingBase shooter) {
        return false;
    }
    
    public int startAiming(final ItemStack is, final EntityLivingBase shooter, final Entity target) {
        return this.cooldownBase + 10;
    }
}
