package com.chocolate.chocolateQuest.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ItemHookShootSpider extends ItemHookShoot
{
    public IIcon hook;
    
    public ItemHookShootSpider(final int lvl) {
        super(lvl, "");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister iconRegister) {
        this.hook = iconRegister.registerIcon("chocolatequest:hookWeb");
        this.itemIcon = iconRegister.registerIcon("chocolatequest:hookSpider");
    }
    
    @Override
    public IIcon getIconFromDamageForRenderPass(final int par1, final int par2) {
        if (par2 == 0 && par1 == 0) {
            return this.hook;
        }
        return this.itemIcon;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    @Override
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (entity.ticksExisted % 400 == 0 && entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            if (itemStack.stackTagCompound == null) {
                itemStack.stackTagCompound = new NBTTagCompound();
            }
            itemStack.stackTagCompound.setString("OriginalOwner", ep.getCommandSenderName());
        }
    }
    
    public boolean onEntityItemUpdate(final EntityItem entityItem) {
        if (entityItem.getEntityItem().stackTagCompound != null && entityItem.age > entityItem.lifespan - 100) {
            entityItem.age = 0;
            final EntityPlayer player = entityItem.worldObj.getPlayerEntityByName(entityItem.getEntityItem().stackTagCompound.getString("OriginalOwner"));
            if (player != null) {
                entityItem.setPosition(player.posX, player.posY, player.posZ);
            }
        }
        if (entityItem.isBurning()) {
            entityItem.extinguish();
            entityItem.motionY = 0.5;
        }
        return super.onEntityItemUpdate(entityItem);
    }
}
