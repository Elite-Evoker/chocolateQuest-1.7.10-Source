package com.chocolate.chocolateQuest.entity.boss;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.Entity;

public class EntityPart extends Entity implements IEntityAdditionalSpawnData
{
    EntityBaseBoss mainBody;
    int maxHealth;
    public boolean staticPart;
    public float rotationYawOffset;
    public float distanceToMainBody;
    public float heightOffset;
    int partID;
    int ownerID;
    
    public EntityPart(final World world) {
        super(world);
        this.maxHealth = 100;
        this.staticPart = true;
        this.rotationYawOffset = 0.0f;
        this.distanceToMainBody = 0.0f;
        this.heightOffset = 0.0f;
        this.partID = 0;
        this.ownerID = 0;
        this.isImmuneToFire = true;
        this.setSize(1.0f, 1.0f);
    }
    
    public EntityPart(final World world, final EntityBaseBoss main, final int partID, final float rotationYawOffset, final float distanceToMainBody) {
        this(world);
        this.rotationYawOffset = rotationYawOffset;
        this.distanceToMainBody = distanceToMainBody;
        this.partID = partID;
        this.mainBody = main;
        if (main != null) {
            this.setPosition(main.posX, main.posY, main.posZ);
        }
    }
    
    public EntityPart(final World world, final EntityBaseBoss main, final int partID, final float rotationYawOffset, final float distanceToMainBody, final float heightOffset) {
        this(world, main, partID, rotationYawOffset, distanceToMainBody);
        this.heightOffset = heightOffset;
    }
    
    public void setSize(final float par1, final float par2) {
        super.setSize(par1, par2);
    }
    
    protected void entityInit() {
    }
    
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        if (!this.worldObj.isRemote && this.mainBody != null) {
            final Entity e = par1DamageSource.getEntity();
            final boolean ret = this.mainBody.attackFromPart(par1DamageSource, par2, this);
            if (ret && e instanceof EntityPlayer) {
                final ItemStack is = ((EntityPlayer)e).getCurrentEquippedItem();
                if (is != null) {
                    is.getItem().hitEntity(is, (EntityLivingBase)this.mainBody, (EntityLivingBase)e);
                }
            }
            return ret;
        }
        return false;
    }
    
    public void onUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.mainBody == null) {
                final Entity e = this.worldObj.getEntityByID(this.ownerID);
                if (e instanceof EntityBaseBoss) {
                    this.setMainBody((EntityBaseBoss)e);
                }
                else {
                    this.setDead();
                }
            }
            else if (this.mainBody.isDead) {
                this.setDead();
            }
        }
        super.onUpdate();
        if (this.mainBody != null) {
            this.motionX = this.mainBody.motionX;
            this.motionZ = this.mainBody.motionZ;
            this.motionY = this.mainBody.motionY;
            if (this.staticPart) {
                final double hx = -Math.sin(Math.toRadians(this.mainBody.rotationYaw + this.rotationYawOffset)) * this.distanceToMainBody;
                final double hz = Math.cos(Math.toRadians(this.mainBody.rotationYaw + this.rotationYawOffset)) * this.distanceToMainBody;
                this.setPositionAndRotation(this.mainBody.posX + hx + this.motionX, this.mainBody.posY + this.heightOffset + this.motionY, this.mainBody.posZ + hz + this.motionZ, this.mainBody.rotationYaw, this.mainBody.rotationPitch);
            }
            if (this.mainBody.motionY > 0.0) {
                this.posY += 20.0;
            }
        }
    }
    
    public void setPosition(final double x, final double y, final double z) {
        super.setPosition(x, y, z);
    }
    
    public void setPositionAndRotation(final double x, final double y, final double z, final float yaw, final float pitch) {
        super.setPositionAndRotation(x, y, z, yaw, pitch);
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    public void setMainBody(final EntityBaseBoss body) {
        (this.mainBody = body).setPart(this, this.partID);
    }
    
    public EntityBaseBoss getMainBody() {
        return this.mainBody;
    }
    
    public boolean isEntityEqual(final Entity par1Entity) {
        return this == par1Entity || this.mainBody == par1Entity;
    }
    
    protected void readEntityFromNBT(final NBTTagCompound var1) {
        this.setDead();
    }
    
    protected void writeEntityToNBT(final NBTTagCompound var1) {
    }
    
    public void readSpawnData(final ByteBuf additionalData) {
        if (additionalData.readBoolean()) {
            return;
        }
        final int id = additionalData.readInt();
        final Entity e = this.worldObj.getEntityByID(id);
        this.distanceToMainBody = additionalData.readFloat();
        this.rotationYawOffset = additionalData.readFloat();
        this.heightOffset = additionalData.readFloat();
        this.staticPart = (additionalData.readByte() == 1);
        this.partID = additionalData.readByte();
        if (e instanceof EntityBaseBoss) {
            this.setMainBody((EntityBaseBoss)e);
        }
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        buffer.writeBoolean(this.mainBody == null);
        if (this.mainBody != null) {
            buffer.writeInt(this.mainBody.getEntityId());
            buffer.writeFloat(this.distanceToMainBody);
            buffer.writeFloat(this.rotationYawOffset);
            buffer.writeFloat(this.heightOffset);
            buffer.writeByte((int)(this.staticPart ? 1 : 0));
            buffer.writeByte(this.partID);
        }
    }
    
    public void setDead() {
        super.setDead();
    }
}
