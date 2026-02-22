package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import com.chocolate.chocolateQuest.items.IHookLauncher;

public class ItemHookSword extends ItemBDSword implements IHookLauncher
{
    public ItemHookSword(final Item.ToolMaterial material) {
        super(material);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:hookSword");
    }
    
    public IIcon getIconFromDamageForRenderPass(final int par1, final int par2) {
        if (par2 == 1) {
            return ItemHookShoot.hook;
        }
        return this.itemIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @Override
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int par4, final boolean flag) {
        final int id = this.getHookID(itemstack);
        if (id != 0) {
            final Entity e = world.getEntityByID(id);
            if (e != null) {
                if (e.isDead) {
                    this.setHookID(itemstack, 0);
                }
            }
            else {
                this.setHookID(itemstack, 0);
            }
        }
        super.onUpdate(itemstack, world, entity, par4, flag);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (this.getHookID(itemstack) == 0) {
            if (!world.isRemote) {
                final EntityHookShoot ball = new EntityHookShoot(world, (EntityLivingBase)entityPlayer, 4, itemstack);
                world.spawnEntityInWorld((Entity)ball);
                this.setHookID(itemstack, ball.getEntityId());
            }
        }
        else {
            final Entity e = world.getEntityByID(this.getHookID(itemstack));
            if (e instanceof EntityHookShoot) {
                e.setDead();
            }
            this.setHookID(itemstack, 0);
        }
        return super.onItemRightClick(itemstack, world, entityPlayer);
    }
    
    @Override
    public void setHookID(final ItemStack is, final int id) {
        if (is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
        }
        is.stackTagCompound.setInteger("hook", id);
    }
    
    @Override
    public int getHookID(final ItemStack is) {
        if (is.stackTagCompound == null) {
            return 0;
        }
        return is.stackTagCompound.getInteger("hook");
    }
}
