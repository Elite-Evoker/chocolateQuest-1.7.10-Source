package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.EntityBaiter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemDaggerNinja extends ItemBaseDagger
{
    public ItemDaggerNinja() {
        super(Item.ToolMaterial.EMERALD, 3);
        this.setMaxDurability(2048);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:daggerPirate");
    }
    
    public int getMaxDurability() {
        return 2048;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking()) {
            if (!world.isRemote) {
                final EntityBaiter e = new EntityBaiter(world, (EntityLivingBase)entityPlayer);
                world.spawnEntityInWorld((Entity)e);
            }
            entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 200, 5));
            itemStack.damageItem(20, (EntityLivingBase)entityPlayer);
        }
        else {
            entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 20, 5));
        }
        return super.onItemRightClick(itemStack, world, entityPlayer);
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return super.getIsRepairable(itemToRepair, itemMaterial);
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    public int getEntityLifespan(final ItemStack itemStack, final World world) {
        return 24000;
    }
}
