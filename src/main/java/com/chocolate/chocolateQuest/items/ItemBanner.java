package com.chocolate.chocolateQuest.items;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumAction;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.API.ITwoHandedItem;
import net.minecraft.item.Item;

public class ItemBanner extends Item implements ITwoHandedItem
{
    public String[] names;
    
    public ItemBanner() {
        this.names = new String[] { "End banner", "Pigmen banner", "Dwarf banner", "Zombie banner", "Skeleton banner", "Pirate banner", "Shadows banner", "Goblin banner", "Specter banner", "Colorful banner", "Squid banner", "Minotaur banner", "Colorful banner", "", "" };
    }
    
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            if (ep.isBlocking() && ep.getCurrentEquippedItem() == itemStack) {
                ep.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1, 1));
            }
        }
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.block;
    }
    
    public boolean getHasSubtypes() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:bannerHolder");
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        final int i = itemstack.getMetadata();
        if (i < this.names.length) {
            return this.names[i];
        }
        return "????";
    }
    
    public boolean requiresMultipleRenderPasses() {
        return false;
    }
    
    public IIcon getIconFromDamageForRenderPass(final int par1, final int par2) {
        return this.itemIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (int i = 0; i <= this.names.length; ++i) {
            itemList.add(new ItemStack(item, 1, i));
        }
    }
}
