package com.chocolate.chocolateQuest.items;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemEditableBook;

public class ItemQuestBook extends ItemEditableBook
{
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        super.onUpdate(itemStack, world, entity, par4, par5);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:book");
    }
    
    public boolean onEntityItemUpdate(final EntityItem entityItem) {
        return super.onEntityItemUpdate(entityItem);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
        if (itemstack.stackTagCompound == null) {
            itemstack.stackTagCompound = new NBTTagCompound();
        }
        itemstack.setTagInfo("title", (NBTBase)new NBTTagString("Arrr"));
        itemstack.setTagInfo("author", (NBTBase)new NBTTagString("Chocolatin, the pirate captain"));
        final NBTTagList bookPages = new NBTTagList();
        return itemstack;
    }
}
