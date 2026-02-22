package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.item.EnumAction;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemBaseSpear extends ItemBDSword
{
    public int cooldown;
    public String texture;
    float cachedDamage;
    
    public ItemBaseSpear(final Item.ToolMaterial mat, final float baseDamage) {
        super(mat, baseDamage);
        this.cooldown = 50;
        this.cachedDamage = 0.0f;
        this.elementModifier = 0.7f;
    }
    
    public ItemBaseSpear(final Item.ToolMaterial mat) {
        this(mat, 2.0f);
    }
    
    public ItemBaseSpear(final Item.ToolMaterial mat, final String texture) {
        this(mat);
        this.texture = texture;
    }
    
    public ItemBaseSpear(final Item.ToolMaterial mat, final String texture, final int baseDamage, final float elementModifier) {
        this(mat, (float)baseDamage);
        this.texture = texture;
        this.elementModifier = elementModifier;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.texture);
    }
    
    @Override
    public boolean hitEntity(final ItemStack par1ItemStack, final EntityLivingBase target, final EntityLivingBase entity) {
        final boolean flag = super.hitEntity(par1ItemStack, target, entity);
        if (flag && target.ridingEntity != null) {
            target.mountEntity((Entity)null);
        }
        par1ItemStack.damageItem(1, entity);
        return flag;
    }
    
    @Override
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            if (ep.isSwingInProgress && ep.getCurrentEquippedItem() == itemStack && ep.swingProgressInt == 0) {
                final int range = Awakements.getEnchantLevel(itemStack, Awakements.range);
                final Entity target = HelperPlayer.getTarget((EntityLivingBase)ep, world, 4.0 + 0.5 * range);
                if (target != null) {
                    ep.attackTargetEntityWithCurrentItem(target);
                }
            }
        }
        super.onUpdate(itemStack, world, entity, par4, par5);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return super.onItemRightClick(itemstack, world, entityPlayer);
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer, int useTime) {
        entityPlayer.swingItem();
        final Entity target = HelperPlayer.getTarget((EntityLivingBase)entityPlayer, world, 5.0);
        final int j = this.getMaxItemUseDuration(itemstack) - useTime;
        if (j > this.cooldown) {
            this.doSpecialSkill(itemstack, world, (EntityLivingBase)entityPlayer);
        }
        else {
            this.stopUsingItem(itemstack, world, entityPlayer, target);
        }
        if (target != null) {
            useTime = this.getMaxItemUseDuration(itemstack) - useTime;
            useTime = Math.min(useTime + 1, 90);
            this.cachedDamage = this.getWeaponDamage() * useTime / 30.0f + 1.0f;
            this.attackEntityWithItem(entityPlayer, target);
        }
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    public void doSpecialSkill(final ItemStack itemstack, final World world, final EntityLivingBase entityPlayer) {
    }
    
    public void stopUsingItem(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer, final Entity target) {
    }
}
