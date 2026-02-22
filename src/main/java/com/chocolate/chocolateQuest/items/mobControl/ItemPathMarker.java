package com.chocolate.chocolateQuest.items.mobControl;

import java.util.Iterator;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.Vec4I;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityCursor;
import java.util.ArrayList;
import net.minecraft.item.Item;

public class ItemPathMarker extends Item
{
    ArrayList<EntityCursor> cursors;
    ItemStack currentItem;
    
    public ItemPathMarker() {
        this.cursors = new ArrayList<EntityCursor>();
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking() && itemstack.stackTagCompound != null) {
            this.spawnCursors(world, itemstack);
            this.removePoint(itemstack, entityPlayer);
        }
        else {
            final Entity target = HelperPlayer.getTarget((EntityLivingBase)entityPlayer, world, 6.0);
            final MovingObjectPosition mop = HelperPlayer.getMovingObjectPositionFromPlayer((EntityLivingBase)entityPlayer, world, 50.0);
            if (mop != null && mop.entityHit == null) {
                if (itemstack.stackTagCompound == null) {
                    itemstack.stackTagCompound = new NBTTagCompound();
                    itemstack.setMetadata(ItemPathMarker.itemRand.nextInt(16));
                }
                if (this.addPoint(itemstack, entityPlayer, mop.blockX, mop.blockY, mop.blockZ) && world.isRemote) {
                    this.spawnCursors(world, itemstack);
                }
            }
        }
        return super.onItemRightClick(itemstack, world, entityPlayer);
    }
    
    public void onUpdate(final ItemStack itemstack, final World world, final Entity par3Entity, final int par4, final boolean par5) {
        if (world.isRemote && par3Entity instanceof EntityPlayer) {
            boolean itemChanged = false;
            final EntityPlayer player = (EntityPlayer)par3Entity;
            if (player.inventory.getCurrentItem() != null) {
                final ItemStack playerCurrentItem = player.inventory.getCurrentItem();
                Label_0095: {
                    if (playerCurrentItem.getItem() == this) {
                        if (this.currentItem != null) {
                            if (playerCurrentItem.isItemEqual(this.currentItem)) {
                                break Label_0095;
                            }
                        }
                        this.currentItem = playerCurrentItem;
                        itemChanged = true;
                    }
                    else {
                        itemChanged = true;
                        this.currentItem = null;
                    }
                }
            }
            else {
                this.currentItem = null;
            }
            if (itemChanged && this.currentItem != null) {
                this.spawnCursors(world, this.currentItem);
            }
        }
        super.onUpdate(itemstack, world, par3Entity, par4, par5);
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        if (entity instanceof EntityHumanBase && stack.stackTagCompound != null) {
            final int points = stack.stackTagCompound.getInteger("pos");
            if (points < 2) {
                return false;
            }
            final Vec4I[] path = new Vec4I[points];
            for (int p = 0; p < points; ++p) {
                path[p] = new Vec4I(stack.stackTagCompound.getInteger("x" + p), stack.stackTagCompound.getInteger("y" + p), stack.stackTagCompound.getInteger("z" + p), 0);
            }
            ((EntityHumanBase)entity).path = path;
            if (player.worldObj.isRemote) {
                player.addChatMessage((IChatComponent)new ChatComponentText("Assigned path for " + BDHelper.StringColor("2") + entity.getCommandSenderName()));
            }
        }
        return true;
    }
    
    public boolean hasEffect(final ItemStack par1ItemStack) {
        return par1ItemStack.stackTagCompound != null;
    }
    
    public boolean addPoint(final ItemStack itemstack, final EntityPlayer entityPlayer, final int blockX, final int blockY, final int blockZ) {
        final int point = itemstack.stackTagCompound.getInteger("pos");
        if (point >= this.getMaxPoints(itemstack)) {
            entityPlayer.addChatMessage((IChatComponent)new ChatComponentText("Can't add more points"));
            return false;
        }
        if (point > 0) {
            int x = itemstack.stackTagCompound.getInteger("x" + (point - 1));
            int y = itemstack.stackTagCompound.getInteger("y" + (point - 1));
            int z = itemstack.stackTagCompound.getInteger("z" + (point - 1));
            x -= blockX;
            y -= blockY;
            z -= blockZ;
            final double dist = Math.sqrt(x * x + y * y + z * z);
            if (dist > 30.0) {
                entityPlayer.addChatMessage((IChatComponent)new ChatComponentText("Too far from previous point"));
                return false;
            }
        }
        itemstack.stackTagCompound.setInteger("x" + point, blockX);
        itemstack.stackTagCompound.setInteger("y" + point, blockY);
        itemstack.stackTagCompound.setInteger("z" + point, blockZ);
        itemstack.stackTagCompound.setInteger("rot" + point, (int)entityPlayer.rotationYaw);
        itemstack.stackTagCompound.setInteger("pos", point + 1);
        return true;
    }
    
    public int getMaxPoints(final ItemStack is) {
        return 32;
    }
    
    public boolean removePoint(final ItemStack itemstack, final EntityPlayer entityPlayer) {
        if (itemstack.stackTagCompound != null) {
            final int currentPoint = Math.max(0, itemstack.stackTagCompound.getInteger("pos") - 1);
            itemstack.stackTagCompound.setInteger("pos", currentPoint);
            if (currentPoint > 0 && entityPlayer.worldObj.isRemote) {
                final EntityCursor e = this.cursors.get(currentPoint);
                if (e != null) {
                    e.item = null;
                    e.setDead();
                }
            }
            if (currentPoint == 0) {
                itemstack.stackTagCompound = null;
            }
        }
        return true;
    }
    
    public void spawnCursors(final World world, final ItemStack itemstack) {
        if (world.isRemote && itemstack.stackTagCompound != null) {
            for (final EntityCursor e : this.cursors) {
                e.item = null;
                e.setDead();
            }
            this.cursors.clear();
            for (int points = itemstack.stackTagCompound.getInteger("pos"), p = 0; p < points; ++p) {
                final EntityCursor c = new EntityCursor(world, itemstack.stackTagCompound.getInteger("x" + p) + 0.5, itemstack.stackTagCompound.getInteger("y" + p) + 1, itemstack.stackTagCompound.getInteger("z" + p) + 0.5, (float)itemstack.stackTagCompound.getInteger("rot" + p), itemstack);
                if (p >= 1) {
                    this.cursors.get(p - 1).next = c;
                }
                this.cursors.add(c);
                world.spawnEntityInWorld((Entity)c);
            }
        }
    }
}
