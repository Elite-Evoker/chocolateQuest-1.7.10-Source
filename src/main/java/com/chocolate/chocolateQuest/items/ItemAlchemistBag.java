package com.chocolate.chocolateQuest.items;

import java.util.Iterator;
import java.util.List;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.init.Items;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import com.chocolate.chocolateQuest.ChocolateQuest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemPotion;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import net.minecraft.item.Item;

public class ItemAlchemistBag extends Item implements ILoadableGun
{
    public ItemStack onItemUseFinish(final ItemStack itemstack, final World world, final EntityPlayer player) {
        final ItemStack[] cargo = InventoryBag.getCargo(itemstack);
        int itemPos = 0;
        for (int i = 0; i < cargo.length; ++i) {
            if (cargo[i] != null) {
                itemPos = i;
                break;
            }
        }
        final ItemStack potionStack = cargo[itemPos];
        if (potionStack != null) {
            final ItemPotion potion = (ItemPotion)potionStack.getItem();
            if (!ItemPotion.isSplash(potionStack.getMetadata())) {
                potion.onItemUseFinish(potionStack, world, player);
                if (!player.capabilities.isCreativeMode) {
                    final ItemStack itemStack = potionStack;
                    --itemStack.stackSize;
                    if (potionStack.stackSize <= 0) {
                        cargo[itemPos] = null;
                    }
                    InventoryBag.saveCargo(itemstack, cargo);
                }
            }
        }
        return itemstack;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:bag");
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking()) {
            entityPlayer.openGui((Object)ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
            return itemstack;
        }
        final ItemStack[] cargo = InventoryBag.getCargo(itemstack);
        int itemPos = 0;
        for (int i = 0; i < cargo.length; ++i) {
            if (cargo[i] != null) {
                itemPos = i;
                break;
            }
        }
        final ItemStack potionStack = cargo[itemPos];
        if (potionStack != null) {
            final ItemPotion potion = (ItemPotion)potionStack.getItem();
            if (ItemPotion.isSplash(potionStack.getMetadata())) {
                if (!world.isRemote) {
                    final EntityPotion e = new EntityPotion(world, (EntityLivingBase)entityPlayer, potionStack);
                    world.spawnEntityInWorld((Entity)e);
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = potionStack;
                        --itemStack.stackSize;
                        if (potionStack.stackSize <= 0) {
                            cargo[itemPos] = null;
                        }
                        InventoryBag.saveCargo(itemstack, cargo);
                    }
                }
            }
            else {
                entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
            }
        }
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 30;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.drink;
    }
    
    public boolean isValidAmmo(final ItemStack is) {
        return is.getItem() instanceof ItemPotion;
    }
    
    public int getAmmoLoaderStackSize(final ItemStack is) {
        return 64;
    }
    
    public int getAmmoLoaderAmmount(final ItemStack is) {
        return 4 + is.getMetadata();
    }
    
    public int getStackIcon(final ItemStack is) {
        return 84;
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        if (entity instanceof EntityLiving && !entity.worldObj.isRemote && player.capabilities.isCreativeMode) {
            final ItemStack[] cargo = InventoryBag.getCargo(stack);
            final ItemStack potion = cargo[0];
            if (potion != null && potion.getItem() == Items.potionitem) {
                final List<PotionEffect> list = PotionHelper.getPotionEffects(potion.getMetadata(), true);
                if (list != null) {
                    for (final PotionEffect effect : list) {
                        ((EntityLiving)entity).addPotionEffect(new PotionEffect(effect.getPotionID(), Integer.MAX_VALUE, effect.getAmplifier()));
                    }
                }
                return true;
            }
        }
        return false;
    }
}
