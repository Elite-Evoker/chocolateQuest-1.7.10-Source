package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class ItemHookShoot extends Item implements IHookLauncher
{
    public static IIcon hook;
    int lvl;
    String iconTexture;
    
    public ItemHookShoot(final int lvl, final String iconTexture) {
        this.lvl = lvl;
        this.iconTexture = iconTexture;
        this.setMaxStackSize(1);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        ItemHookShoot.hook = iconRegister.registerIcon("chocolatequest:hook");
        this.itemIcon = iconRegister.registerIcon(this.iconTexture);
    }
    
    public IIcon getIconFromDamageForRenderPass(final int par1, final int par2) {
        if (par2 == 0 && par1 == 0) {
            return ItemHookShoot.hook;
        }
        return this.itemIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    public int getHookType() {
        return this.lvl;
    }
    
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int par4, final boolean flag) {
        if (itemstack.getMetadata() != 0) {
            if (world.getEntityByID(itemstack.getMetadata()) != null) {
                if (world.getEntityByID(itemstack.getMetadata()).isDead) {
                    itemstack.setMetadata(0);
                }
            }
            else {
                itemstack.setMetadata(0);
            }
        }
        super.onUpdate(itemstack, world, entity, par4, flag);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (itemstack.getMetadata() == 0) {
            if (!world.isRemote) {
                final EntityHookShoot ball = new EntityHookShoot(world, (EntityLivingBase)entityPlayer, this.lvl, itemstack);
                world.spawnEntityInWorld((Entity)ball);
                itemstack.setMetadata(ball.getEntityId());
            }
        }
        else {
            final Entity e = world.getEntityByID(itemstack.getMetadata());
            if (e instanceof EntityHookShoot) {
                e.setDead();
            }
            itemstack.setMetadata(0);
        }
        return super.onItemRightClick(itemstack, world, entityPlayer);
    }
    
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.rare;
    }
    
    public int getHookID(final ItemStack is) {
        return is.getMetadata();
    }
    
    public void setHookID(final ItemStack is, final int id) {
        is.setMetadata(id);
    }
}
