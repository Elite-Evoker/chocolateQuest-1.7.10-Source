package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.nbt.NBTBase;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class ItemSoulBottle extends Item
{
    public static final String ENTITY_NAMETAG = "entity";
    public static final String NAME_NAMETAG = "itemName";
    
    public ItemSoulBottle() {
        this.setMaxStackSize(1);
    }
    
    public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player) {
        return super.onItemRightClick(stack, world, player);
    }
    
    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int f1, final float f2, final float f3, final float f4) {
        if (!world.isRemote && stack.stackTagCompound != null) {
            final NBTTagCompound tag = (NBTTagCompound)stack.getTagCompound().getTag("entity");
            final Entity entity = createEntityFromNBT(tag, world, x, y, z);
            if (entity != null) {
                world.spawnEntityInWorld(entity);
            }
            if (!player.capabilities.isCreativeMode) {
                stack.stackTagCompound = null;
                stack.stackSize = 0;
            }
        }
        return super.onItemUse(stack, player, world, x, y, z, f1, f2, f3, f4);
    }
    
    public static Entity createEntityFromNBT(final NBTTagCompound tag, final World world, final int x, final int y, final int z) {
        final Entity entity = EntityList.createEntityFromNBT(tag, world);
        if (entity instanceof EntityHumanBase) {
            final EntityHumanBase human = (EntityHumanBase)entity;
            human.readEntityFromSpawnerNBT(tag, x, y, z);
            if (tag.getTag("Riding") != null) {
                final NBTTagCompound ridingNBT = (NBTTagCompound)tag.getTag("Riding");
                final Entity riding = EntityList.createEntityFromNBT(ridingNBT, world);
                if (riding != null) {
                    riding.setPosition(x + 0.5, (double)(y + 1), z + 0.5);
                    world.spawnEntityInWorld(riding);
                    human.mountEntity(riding);
                    if (riding instanceof EntityHumanBase) {
                        ((EntityHumanBase)riding).entityTeam = human.entityTeam;
                    }
                }
            }
        }
        else {
            entity.readFromNBT(tag);
        }
        if (entity != null) {
            entity.posX = x + 0.5;
            entity.posY = y + 1;
            entity.posZ = z + 0.5;
            entity.setPosition(entity.posX, entity.posY, entity.posZ);
        }
        return entity;
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        final NBTTagCompound tag1 = new NBTTagCompound();
        if (stack.stackTagCompound == null) {
            final NBTTagCompound tag2 = new NBTTagCompound();
            tag2.setString("itemName", entity.getCommandSenderName());
            if (entity instanceof EntityHumanBase) {
                final int x = MathHelper.floor_double(entity.posX);
                final int y = MathHelper.floor_double(entity.posY);
                final int z = MathHelper.floor_double(entity.posZ);
                tag2.setTag("entity", (NBTBase)ItemMobToSpawner.getHumanSaveTagAndKillIt(x, y, z, (EntityHumanBase)entity));
            }
            else {
                final NBTTagCompound entityTag = new NBTTagCompound();
                if (entity.writeToNBTOptional(entityTag)) {
                    tag2.setTag("entity", (NBTBase)entityTag);
                    entity.setDead();
                }
            }
            if (player.worldObj.isRemote) {
                BDHelper.println(tag2.getTag("entity").toString());
            }
            stack.stackTagCompound = tag2;
        }
        return true;
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        if (itemstack.stackTagCompound != null) {
            return super.getItemStackDisplayName(itemstack) + ": " + itemstack.stackTagCompound.getString("itemName");
        }
        return super.getItemStackDisplayName(itemstack);
    }
}
