package com.chocolate.chocolateQuest.items.mobControl;

import net.minecraft.util.StatCollector;
import java.util.Iterator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.EntityLiving;
import com.chocolate.chocolateQuest.utils.Vec4I;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityCursor;
import java.util.ArrayList;
import net.minecraft.item.Item;

public class ItemController extends Item
{
    public final int CONTROLLER = 0;
    public final int GOLEM_CONTROLLER = 1;
    public static final int MOVE = 0;
    public static final int STANDING_POS = 1;
    public static final int TEAM_EDITOR = 2;
    public static final int CLEAR = 3;
    ArrayList<EntityCursor> cursors;
    ItemStack currentItem;
    
    public ItemController() {
        this.cursors = new ArrayList<EntityCursor>();
    }
    
    public boolean itemInteractionForEntity(final ItemStack itemstack, final EntityPlayer entityPlayer, final EntityLivingBase entity) {
        final EntityLivingBase[] arr$;
        final EntityLivingBase[] entities = arr$ = this.getEntity(itemstack, entityPlayer.worldObj);
        for (final EntityLivingBase e : arr$) {
            if (e != null) {
                final MovingObjectPosition mop = new MovingObjectPosition((Entity)entity);
                this.doStuff(itemstack, entityPlayer.worldObj, entityPlayer, mop, (Entity)e);
                return true;
            }
        }
        return false;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking()) {
            entityPlayer.openGui((Object)ChocolateQuest.instance, 8, entityPlayer.worldObj, 0, 0, 0);
            return itemstack;
        }
        final MovingObjectPosition mop = HelperPlayer.getMovingObjectPositionFromPlayer((EntityLivingBase)entityPlayer, world, 80.0);
        final EntityLivingBase[] entities = this.getEntity(itemstack, world);
        if (entities != null) {
            for (final EntityLivingBase e : entities) {
                if (e != null) {
                    this.doStuff(itemstack, world, entityPlayer, mop, (Entity)e);
                }
            }
        }
        return super.onItemRightClick(itemstack, world, entityPlayer);
    }
    
    public void doStuff(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer, final MovingObjectPosition mop, final Entity assignedEntity) {
        if (mop != null) {
            final int mode = this.getMode(itemstack);
            if (mop.entityHit == null) {
                if (assignedEntity instanceof EntityHumanBase) {
                    final EntityHumanBase human = (EntityHumanBase)assignedEntity;
                    if (world.isRemote) {
                        world.spawnEntityInWorld((Entity)new EntityCursor(world, mop.blockX + 0.5, mop.blockY + 1, mop.blockZ + 0.5, entityPlayer.rotationYaw));
                    }
                    if (mode == 0) {
                        human.currentPos = new Vec4I(mop.blockX, mop.blockY, mop.blockZ, (int)entityPlayer.rotationYaw);
                    }
                    else if (mode == 1) {
                        human.standingPosition = new Vec4I(mop.blockX, mop.blockY, mop.blockZ, (int)entityPlayer.rotationYaw);
                    }
                }
            }
            else if (mop.entityHit instanceof EntityLiving && mop.entityHit != assignedEntity) {
                EntityLivingBase entityHit = (EntityLivingBase)mop.entityHit;
                if (assignedEntity instanceof EntityHumanBase) {
                    final EntityHumanBase human2 = (EntityHumanBase)assignedEntity;
                    if (world.isRemote) {
                        world.spawnEntityInWorld((Entity)new EntityCursor(world, mop.entityHit, itemstack));
                    }
                    boolean isMount = human2.isSuitableMount((Entity)entityHit);
                    if (isMount && entityHit.riddenByEntity != null && entityHit instanceof EntityLivingBase) {
                        entityHit = (EntityLivingBase)entityHit.riddenByEntity;
                        isMount = false;
                    }
                    if (isMount) {
                        if ((human2.ridingEntity == null && mode == 0) || mode == 2) {
                            if (human2.isSuitableMount((Entity)entityHit)) {
                                entityPlayer.addChatMessage((IChatComponent)new ChatComponentText(BDHelper.StringColor("2") + human2.getCommandSenderName() + BDHelper.StringColor("f") + " mounting " + BDHelper.StringColor("2") + entityHit.getCommandSenderName() + BDHelper.StringColor("f")));
                            }
                            human2.setAttackTarget(entityHit);
                        }
                    }
                    else if (!human2.isSuitableTargetAlly(entityHit)) {
                        if (mode == 0) {
                            human2.setAttackTarget(entityHit);
                        }
                    }
                    else {
                        if (mode == 2) {
                            boolean added = true;
                            if (entityHit instanceof EntityHumanBase) {
                                if (!((EntityHumanBase)entityHit).tryPutIntoPArty(human2)) {
                                    added = false;
                                }
                            }
                            else {
                                human2.setOwner(entityHit);
                            }
                            if (added) {
                                entityPlayer.addChatMessage((IChatComponent)new ChatComponentText(BDHelper.StringColor("2") + human2.getCommandSenderName() + BDHelper.StringColor("f") + " now following " + BDHelper.StringColor("2") + entityHit.getCommandSenderName() + BDHelper.StringColor("f") + " orders"));
                            }
                        }
                        if (mode == 0) {
                            human2.currentPos = new Vec4I(MathHelper.floor_double(entityHit.posX), MathHelper.floor_double(entityHit.posY), MathHelper.floor_double(entityHit.posZ), (int)entityPlayer.rotationYaw);
                        }
                    }
                }
                else if (assignedEntity instanceof EntityLiving && mode == 0) {
                    ((EntityLiving)assignedEntity).setAttackTarget(entityHit);
                    if (world.isRemote) {
                        world.spawnEntityInWorld((Entity)new EntityCursor(world, mop.entityHit, itemstack));
                    }
                }
            }
        }
    }
    
    public boolean tryMount(final Entity mount, final EntityHumanBase rider) {
        return false;
    }
    
    public byte getMode(final ItemStack itemstack) {
        if (itemstack.stackTagCompound == null) {
            return 0;
        }
        return itemstack.stackTagCompound.getByte("Mode");
    }
    
    public void setMode(final ItemStack itemstack, final byte mode) {
        if (itemstack.stackTagCompound == null) {
            itemstack.stackTagCompound = new NBTTagCompound();
        }
        itemstack.stackTagCompound.setByte("Mode", mode);
    }
    
    public void addEntity(final ItemStack itemstack, final EntityLivingBase entity) {
        if (itemstack.stackTagCompound == null) {
            itemstack.stackTagCompound = new NBTTagCompound();
        }
        NBTTagList list = (NBTTagList)itemstack.stackTagCompound.getTag("Entities");
        if (list == null) {
            list = new NBTTagList();
        }
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("EntityID", entity.getEntityId());
        list.appendTag((NBTBase)tag);
        itemstack.stackTagCompound.setTag("Entities", (NBTBase)list);
    }
    
    public EntityLivingBase[] getEntity(final ItemStack itemstack, final World world) {
        if (itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("Entities")) {
            final NBTTagList list = (NBTTagList)itemstack.stackTagCompound.getTag("Entities");
            if (list != null) {
                final EntityLivingBase[] entities = new EntityLivingBase[list.tagCount()];
                for (int i = 0; i < list.tagCount(); ++i) {
                    final NBTTagCompound tag = list.getCompoundTagAt(i);
                    final int id = tag.getInteger("EntityID");
                    if (id != 0) {
                        final Entity e = world.getEntityByID(id);
                        if (e instanceof EntityLivingBase) {
                            entities[i] = (EntityLivingBase)e;
                        }
                    }
                }
                for (int i = entities.length - 1; i > 0; --i) {
                    if (entities[i] != null) {
                        if (!entities[i].isDead) {
                            continue;
                        }
                    }
                    list.removeTag(i);
                }
                return entities;
            }
        }
        return null;
    }
    
    public boolean hasEffect(final ItemStack par1ItemStack) {
        return par1ItemStack.stackTagCompound != null;
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        if (this.isSuitableTarget(entity, stack) && entity instanceof EntityLivingBase) {
            if (!(entity instanceof EntityHumanBase) || !((EntityHumanBase)entity).isOnSameTeam((EntityLivingBase)player)) {
                if (!player.capabilities.isCreativeMode) {
                    return true;
                }
            }
            this.addEntity(stack, (EntityLivingBase)entity);
            stack.setMetadata(ItemController.itemRand.nextInt());
            final String name = entity.getCommandSenderName();
            if (player.worldObj.isRemote) {
                player.addChatMessage((IChatComponent)new ChatComponentText("Assigned " + BDHelper.StringColor("2") + name + BDHelper.StringColor("f") + " to this item"));
            }
            this.currentItem = null;
            return true;
        }
        return true;
    }
    
    public boolean isSuitableTarget(final Entity e, final ItemStack is) {
        return true;
    }
    
    public void onUpdate(final ItemStack itemstack, final World world, final Entity par3Entity, final int par4, final boolean par5) {
        if (world.isRemote && par3Entity instanceof EntityPlayer) {
            boolean itemChanged = false;
            final EntityPlayer player = (EntityPlayer)par3Entity;
            final ItemStack playerCurrentItem = player.inventory.getCurrentItem();
            Label_0106: {
                if (player.inventory.getCurrentItem() != null) {
                    if (playerCurrentItem.getItem() == this) {
                        if (this.currentItem != null) {
                            if (playerCurrentItem.isItemEqual(this.currentItem)) {
                                break Label_0106;
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
                else {
                    itemChanged = true;
                    this.currentItem = null;
                }
            }
            if (itemChanged && playerCurrentItem != null) {
                this.spawnCursors(world, playerCurrentItem);
            }
        }
    }
    
    public void spawnCursors(final World world, final ItemStack itemstack) {
        for (final EntityCursor e : this.cursors) {
            e.setDead();
        }
        this.cursors.clear();
        final EntityLivingBase[] entities = this.getEntity(itemstack, world);
        if (entities != null) {
            for (final EntityLivingBase e2 : entities) {
                if (e2 != null) {
                    final EntityCursor c = new EntityCursor(world, (Entity)e2, itemstack, 1);
                    world.spawnEntityInWorld((Entity)c);
                    this.cursors.add(c);
                }
            }
        }
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        final int mode = this.getMode(itemstack);
        String surName = null;
        switch (mode) {
            case 0: {
                surName = StatCollector.translateToLocal("item.move.name");
                break;
            }
            case 1: {
                surName = StatCollector.translateToLocal("item.ward.name");
                break;
            }
            case 2: {
                surName = StatCollector.translateToLocal("item.team.name");
                break;
            }
            default: {
                surName = "????";
                break;
            }
        }
        return super.getItemStackDisplayName(itemstack) + (" " + surName).trim();
    }
}
