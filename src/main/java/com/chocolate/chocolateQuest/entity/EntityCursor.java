package com.chocolate.chocolateQuest.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.gui.guiParty.GuiParty;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;

public class EntityCursor extends Entity
{
    int lifeTime;
    public Entity followEntity;
    public ItemStack item;
    public Entity next;
    public int type;
    public static final int flag = 0;
    public static final int selector = 1;
    public int color;
    GuiParty guiParty;
    
    public EntityCursor(final World par1World) {
        super(par1World);
        this.lifeTime = 40;
        this.type = 0;
        this.color = 65280;
    }
    
    public EntityCursor(final World par1World, final double x, final double y, final double z, final float rotationYaw, final ItemStack item) {
        this(par1World, x, y, z, rotationYaw);
        this.item = item;
        this.setPositionAndRotation(x, y + 1.0, z, rotationYaw, 0.0f);
    }
    
    public EntityCursor(final World par1World, final double x, final double y, final double z, final float rotationYaw) {
        super(par1World);
        this.lifeTime = 40;
        this.type = 0;
        this.color = 65280;
        this.setPositionAndRotation(x, y + 1.0, z, rotationYaw, 0.0f);
    }
    
    public EntityCursor(final World par1World, final Entity entity, final int color, final GuiParty guiParty) {
        super(par1World);
        this.lifeTime = 40;
        this.type = 0;
        this.color = 65280;
        this.followEntity = entity;
        this.guiParty = guiParty;
        this.color = color;
        this.setPositionAndRotation(entity.posX, entity.posY + 1.0, entity.posZ, this.rotationYaw, 0.0f);
        this.type = 1;
    }
    
    public EntityCursor(final World par1World, final Entity entity, final ItemStack item) {
        super(par1World);
        this.lifeTime = 40;
        this.type = 0;
        this.color = 65280;
        this.followEntity = entity;
        this.item = item;
        this.setPositionAndRotation(entity.posX, entity.posY + 1.0, entity.posZ, this.rotationYaw, 0.0f);
    }
    
    public EntityCursor(final World par1World, final Entity entity, final ItemStack item, final int type) {
        this(par1World, entity, item);
        this.type = type;
    }
    
    public EntityCursor(final World par1World, final Entity entity, final ItemStack item, final int type, final Entity entityPointingTo) {
        this(par1World, entity, item, type);
        this.next = entityPointingTo;
    }
    
    protected void entityInit() {
        this.setSize(0.1f, 2.0f);
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted < 5) {
            this.posY -= 0.2;
        }
        if (this.ticksExisted > this.lifeTime) {
            this.setDead();
        }
        if (this.followEntity != null) {
            this.posX = this.followEntity.posX;
            this.posZ = this.followEntity.posZ;
            if (this.ticksExisted > 5) {
                this.posY = this.followEntity.posY + 0.01;
            }
            this.rotationYaw = this.followEntity.rotationYaw;
            if (this.ticksExisted > this.lifeTime) {
                this.setDead();
            }
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        if (this.next != null) {
            for (int i = 0; i < 4; ++i) {
                this.worldObj.spawnParticle("enchantmenttable", this.next.posX + (this.rand.nextFloat() - 0.5f) / 2.0f, this.next.posY + this.rand.nextFloat() / 2.0f + 1.5, this.next.posZ + (this.rand.nextFloat() - 0.5f) / 2.0f, this.posX - this.next.posX, this.posY - this.next.posY - 1.0, this.posZ - this.next.posZ);
            }
        }
    }
    
    public void setDead() {
        if (this.followEntity != null) {
            this.lifeTime = this.ticksExisted + 20;
            if (this.followEntity.isDead) {
                super.setDead();
                return;
            }
        }
        if (this.guiParty != null && Minecraft.getMinecraft().currentScreen == this.guiParty) {
            return;
        }
        if (this.item != null && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null && this.item.isItemEqual(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem())) {
            return;
        }
        super.setDead();
    }
    
    public void forceDead() {
        super.setDead();
    }
    
    protected void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
    }
    
    protected void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
    }
}
