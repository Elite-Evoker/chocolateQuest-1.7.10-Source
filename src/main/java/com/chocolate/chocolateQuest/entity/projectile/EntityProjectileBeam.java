package com.chocolate.chocolateQuest.entity.projectile;

import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.util.Vec3;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.magic.Elements;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.Entity;

public class EntityProjectileBeam extends Entity implements IEntityAdditionalSpawnData
{
    Elements element;
    int elementID;
    EntityLivingBase mainBody;
    public float rotationYawOffset;
    public float distanceToMainBody;
    public float heightOffset;
    int ownerID;
    public float maxRange;
    public float range;
    
    public EntityProjectileBeam(final World world) {
        super(world);
        this.rotationYawOffset = 0.0f;
        this.distanceToMainBody = 0.0f;
        this.heightOffset = 0.0f;
        this.ownerID = 0;
        this.maxRange = 16.0f;
        this.range = 16.0f;
        this.isImmuneToFire = true;
        this.setSize(1.0f, 1.0f);
    }
    
    public EntityProjectileBeam(final World world, final EntityLivingBase main, final float rotationYawOffset, final float distanceToMainBody, final Elements element) {
        this(world);
        this.rotationYawOffset = rotationYawOffset;
        this.distanceToMainBody = distanceToMainBody;
        if (main != null) {
            this.setPosition(main.posX, main.posY, main.posZ);
        }
        this.mainBody = main;
        this.element = element;
        this.elementID = element.ordinal();
    }
    
    public EntityProjectileBeam(final World world, final EntityLivingBase main, final float rotationYawOffset, final float distanceToMainBody, final float heightOffset, final Elements element) {
        this(world, main, rotationYawOffset, distanceToMainBody, element);
        this.heightOffset = heightOffset;
    }
    
    public void setSize(final float par1, final float par2) {
        super.setSize(par1, par2);
    }
    
    public void onUpdate() {
        final Elements element = this.getElement();
        super.onUpdate();
        if (this.mainBody != null) {
            final double hx = -Math.sin(Math.toRadians(this.mainBody.rotationYawHead + this.rotationYawOffset)) * this.distanceToMainBody;
            final double hz = Math.cos(Math.toRadians(this.mainBody.rotationYawHead + this.rotationYawOffset)) * this.distanceToMainBody;
            this.setPositionAndRotation(this.mainBody.posX + hx + this.motionX, this.mainBody.posY + this.heightOffset + this.motionY, this.mainBody.posZ + hz + this.motionZ, this.mainBody.rotationYawHead, this.mainBody.rotationPitch);
            if (this.mainBody instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)this.mainBody;
                if (!player.isUsingItem()) {
                    this.setDead();
                }
            }
            else if (this.ticksExisted > 30) {
                this.setDead();
            }
            final EntityLivingBase shooter = this.mainBody;
            final MovingObjectPosition mop = getMovingObjectPositionFromPlayer(shooter, shooter.worldObj, this.maxRange, 0.0);
            if (mop != null) {
                double x = mop.blockX;
                final double y = mop.blockY;
                double z = mop.blockZ;
                final double dist = this.getDistance(x, y, z);
                this.range = (float)dist;
                if (mop.entityHit != null) {
                    float damage = 2.0f;
                    damage = element.onHitEntity((Entity)this.mainBody, mop.entityHit, damage);
                    mop.entityHit.attackEntityFrom(element.getDamageSource((Entity)this.mainBody).setProjectile(), damage);
                }
                if (this.worldObj.isRemote) {
                    switch (mop.sideHit) {
                        case 2: {
                            x += 0.5;
                            break;
                        }
                        case 3: {
                            z += 1.5;
                            break;
                        }
                        case 4: {
                            x -= 0.5;
                            break;
                        }
                        case 5: {
                            z += 0.5;
                            x += 1.2;
                            break;
                        }
                    }
                    final float s = 0.2f;
                    double ry = Math.cos(Math.toRadians(this.rotationPitch));
                    final double rx = -Math.sin(Math.toRadians(this.rotationYaw)) * dist * ry;
                    final double rz = Math.cos(Math.toRadians(this.rotationYaw)) * dist * ry;
                    ry = -Math.sin(Math.toRadians(this.rotationPitch)) * dist;
                    EffectManager.spawnElementParticle(0, this.worldObj, this.posX + rx, this.posY + ry, this.posZ + rz, (this.rand.nextFloat() - 0.5f) * s, 0.1, (this.rand.nextFloat() - 0.5f) * s, element);
                }
            }
            else {
                this.range = this.maxRange;
            }
        }
        else {
            this.setDead();
        }
    }
    
    public boolean canBeCollidedWith() {
        return false;
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
        if (e instanceof EntityLivingBase) {
            this.mainBody = (EntityLivingBase)e;
        }
        this.distanceToMainBody = additionalData.readFloat();
        this.rotationYawOffset = additionalData.readFloat();
        this.heightOffset = additionalData.readFloat();
        this.elementID = additionalData.readInt();
        this.maxRange = (float)additionalData.readInt();
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        buffer.writeBoolean(this.mainBody == null);
        if (this.mainBody != null) {
            buffer.writeInt(this.mainBody.getEntityId());
            buffer.writeFloat(this.distanceToMainBody);
            buffer.writeFloat(this.rotationYawOffset);
            buffer.writeFloat(this.heightOffset);
            buffer.writeInt(this.elementID);
            buffer.writeInt((int)this.maxRange);
        }
    }
    
    protected void entityInit() {
    }
    
    public static MovingObjectPosition getMovingObjectPositionFromPlayer(final EntityLivingBase ep, final World world, double dist, final double removeMe) {
        MovingObjectPosition mop = null;
        final float yOffset = ep.getEyeHeight();
        final Vec3 playerPos = Vec3.createVectorHelper(ep.posX, ep.posY + yOffset, ep.posZ);
        final Vec3 look = ep.getLookVec();
        if (ep instanceof EntityPlayer) {
            mop = HelperPlayer.getBlockMovingObjectPositionFromPlayer(world, ep, dist, true);
        }
        else {
            mop = HelperPlayer.getBlockMovingObjectPositionFromPlayer(world, ep, dist, true);
        }
        if (mop != null) {
            final Vec3 v = Vec3.createVectorHelper(ep.posX - mop.blockX, ep.posY - mop.blockY, ep.posZ - mop.blockZ);
            dist = v.lengthVector();
        }
        final Vec3 playerView = playerPos.addVector(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
        final List list = world.getEntitiesWithinAABBExcludingEntity((Entity)ep, ep.boundingBox.addCoord(ep.getLookVec().xCoord * dist, ep.getLookVec().yCoord * dist, ep.getLookVec().zCoord * dist).expand(1.0, 1.0, 1.0));
        MovingObjectPosition tempMop = null;
        double prevDist = dist * dist;
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity1 = list.get(j);
            if (entity1.canBeCollidedWith() && entity1 != ep.ridingEntity) {
                if (ep != ep.riddenByEntity) {
                    if (entity1 instanceof EntityLivingBase) {
                        final float f2 = 0.4f;
                        final AxisAlignedBB axisalignedbb = entity1.boundingBox;
                        final MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(playerPos, playerView);
                        if (movingobjectposition1 != null) {
                            movingobjectposition1.entityHit = entity1;
                            movingobjectposition1.blockX = MathHelper.floor_double(entity1.posX);
                            movingobjectposition1.blockY = MathHelper.floor_double(entity1.posY + entity1.height / 2.0f);
                            movingobjectposition1.blockZ = MathHelper.floor_double(entity1.posZ);
                            final double entityDist = entity1.getDistanceSqToEntity((Entity)ep);
                            if (entityDist < prevDist) {
                                tempMop = movingobjectposition1;
                                prevDist = entityDist;
                            }
                        }
                    }
                }
            }
        }
        if (tempMop != null) {
            return tempMop;
        }
        return mop;
    }
    
    public Elements getElement() {
        if (this.element == null) {
            this.element = Elements.values()[this.elementID];
        }
        return this.element;
    }
}
