package com.chocolate.chocolateQuest.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemStaffHeal extends Item
{
    public ItemStaffHeal() {
        this.setMaxStackSize(1);
        this.setFull3D();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.getUnlocalizedName().replace("item.", ""));
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        if (entity instanceof EntityLivingBase && (player.getFoodStats().getFoodLevel() > 1 || player.capabilities.isCreativeMode)) {
            if (!player.capabilities.isCreativeMode) {
                player.getFoodStats().addStats(-1, 0.0f);
            }
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            ((EntityLivingBase)entity).heal(2.0f);
            if (entity.worldObj.isRemote) {
                for (int i = 0; i < 5; ++i) {
                    entity.worldObj.spawnParticle("heart", entity.posX + ItemStaffHeal.itemRand.nextFloat() - 0.5, entity.posY + 1.0 + ItemStaffHeal.itemRand.nextFloat(), entity.posZ + ItemStaffHeal.itemRand.nextFloat() - 0.5, (double)(ItemStaffHeal.itemRand.nextFloat() - 0.5f), (double)(ItemStaffHeal.itemRand.nextFloat() - 0.5f), (double)(ItemStaffHeal.itemRand.nextFloat() - 0.5f));
                }
            }
            entity.worldObj.playSoundEffect((double)(int)entity.posX, (double)(int)entity.posY, (double)(int)entity.posZ, "chocolatequest:magic", 0.5f, (1.0f + (entity.worldObj.rand.nextFloat() - entity.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
            return true;
        }
        return false;
    }
}
