package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.items.IHookLauncher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import com.chocolate.chocolateQuest.items.ItemArmorHeavy;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketHookImpact;
import net.minecraft.util.MovingObjectPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.IThrowableEntity;
import net.minecraft.entity.projectile.EntityThrowable;

public class EntityHookShoot extends EntityThrowable implements IThrowableEntity
{
    public ItemStack item;
    int lifeTime;
    EntityLivingBase shootingEntity;
    public double radio;
    public boolean returning;
    public boolean reeling;
    public static final byte hookManual = 2;
    public static final byte hookSpider = 3;
    public static final byte hookWeapon = 4;
    public static final byte hookSpiderBoss = 5;
    public Entity hookedEntity;
    public double hookedAtHeight;
    public double hookedAtDistance;
    public double hookedAtAngle;
    public int blockX;
    public int blockY;
    public int blockZ;
    
    public EntityHookShoot(final World par1World) {
        super(par1World);
        this.item = null;
        this.lifeTime = 0;
        this.radio = 0.0;
        this.returning = false;
        this.reeling = false;
    }
    
    public EntityHookShoot(final World world, final EntityLivingBase entityliving, final int type) {
        super(world, entityliving);
        this.item = null;
        this.lifeTime = 0;
        this.radio = 0.0;
        this.returning = false;
        this.reeling = false;
        this.shootingEntity = entityliving;
        this.setHookType(type);
        final float s = 0.6f;
        this.setSize(s, s);
        this.worldObj.playSoundEffect((double)(int)this.posX, (double)(int)this.posY, (double)(int)this.posZ, "random.bow", 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
    }
    
    public EntityHookShoot(final World world, final EntityLivingBase entityliving, final int type, final ItemStack item) {
        this(world, entityliving, type);
        this.item = item;
    }
    
    protected void entityInit() {
        this.dataWatcher.addObject(10, (Object)0);
        this.dataWatcher.addObject(11, (Object)0);
    }
    
    public void setReeling(final boolean b) {
        this.dataWatcher.updateObject(10, (Object)(byte)(b ? 1 : 0));
    }
    
    public boolean isReeling() {
        return this.dataWatcher.getWatchableObjectByte(10) == 1;
    }
    
    public void setHookType(final int par) {
        this.dataWatcher.updateObject(11, (Object)(byte)par);
    }
    
    public byte getHookType() {
        return this.dataWatcher.getWatchableObjectByte(11);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(final double par1) {
        double d1 = this.boundingBox.getAverageEdgeLength() * 4.0;
        d1 *= 64.0;
        return par1 < d1 * d1;
    }
    
    protected void onImpact(final MovingObjectPosition mop) {
        if (!this.isReeling()) {
            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !this.worldObj.isRemote) {
                this.blockX = mop.blockX;
                this.blockY = mop.blockY;
                this.blockZ = mop.blockZ;
                final PacketHookImpact packet = new PacketHookImpact(this.getEntityId(), mop.blockX, mop.blockY, mop.blockZ);
                ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
                this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
                this.onGround = true;
            }
            if (mop.entityHit instanceof Entity && !this.worldObj.isRemote) {
                if (mop.entityHit instanceof EntityThrowable) {
                    return;
                }
                if (!mop.entityHit.isEntityEqual((Entity)this.getThrower())) {
                    this.hookedEntity = mop.entityHit;
                    this.hookedAtHeight = Math.min(this.hookedEntity.height, this.posY - this.hookedEntity.posY);
                    this.hookedAtAngle = Math.atan2(this.hookedEntity.posZ - this.posZ, this.hookedEntity.posX - this.posX) * 180.0 / 3.141592653589793 + 90.0;
                    this.hookedAtAngle -= this.hookedEntity.rotationYaw;
                    this.hookedAtDistance = Math.min(this.hookedEntity.width, Math.sqrt(Math.abs(this.hookedEntity.posX - this.posX + this.hookedEntity.posZ - this.posZ)));
                    final PacketHookImpact packet = new PacketHookImpact(this.getEntityId(), this.hookedEntity.getEntityId(), this.hookedAtAngle, this.hookedAtDistance, this.hookedAtHeight);
                    ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
                    this.returning = true;
                    this.onGround = true;
                }
            }
            if (!this.worldObj.isRemote && this.onGround) {
                this.setReeling(true);
                this.onImpact();
            }
        }
    }
    
    public void onImpact() {
        ++this.radio;
    }
    
    public void onUpdate() {
        if ((this.getThrower() != null && this.getThrower().isDead) || !this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ)) {
            this.setDead();
        }
        else {
            super.onUpdate();
            if (this.hookedEntity != null) {
                final float angle = (float)((this.hookedAtAngle + this.hookedEntity.rotationYaw) * 3.1415998935699463 / 180.0);
                this.posX = this.hookedEntity.posX - MathHelper.sin(angle) * this.hookedAtDistance;
                this.posY = this.hookedEntity.posY + this.hookedAtHeight;
                this.posZ = this.hookedEntity.posZ + MathHelper.cos(angle) * this.hookedAtDistance;
                if (!this.hookedEntity.isEntityAlive()) {
                    this.hookedEntity = null;
                    this.setReeling(false);
                }
            }
            if (this.isReeling()) {
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            ++this.lifeTime;
            if (this.getThrower() != null) {
                final EntityLivingBase shooting = this.getThrower();
                final double dist = this.getDistanceToEntity((Entity)this.getThrower());
                if (dist > this.radio) {
                    this.getThrower().fallDistance = 0.0f;
                }
                if (shooting instanceof EntityPlayer) {
                    if (shooting.isSneaking() && this.radio > 0.0) {
                        this.radio -= 0.4;
                        if (this.isSpiderHook() && this.isReeling() && this.shootingEntity instanceof EntityPlayer) {
                            --this.radio;
                            if (dist >= this.radio) {
                                final double distY = this.posY - shooting.posY;
                                if (distY > 0.0) {
                                    final EntityLivingBase entityLivingBase = shooting;
                                    entityLivingBase.motionY += Math.max(0.02, distY * 0.1);
                                }
                                final double maxMotion = Math.min(1.4, Math.max(1.1, distY / 10.0));
                                if (shooting.motionY > maxMotion) {
                                    shooting.motionY = maxMotion;
                                }
                            }
                        }
                    }
                }
                else {
                    this.radio -= 1.2;
                }
                if ((shooting.swingProgress > 0.0f || shooting.isSprinting()) && this.radio < this.getMaxRadius() && shooting.getEquipmentInSlot(0) != null && shooting.getEquipmentInSlot(0).getItem() instanceof ItemHookShoot) {
                    this.radio += 0.5;
                }
                if (this.isReeling()) {
                    if (!this.worldObj.isRemote && this.worldObj.isAirBlock(this.blockX, this.blockY, this.blockZ) && this.hookedEntity == null) {
                        this.setReeling(false);
                    }
                    boolean pullToShooter = false;
                    final Entity ride = this.hookedEntity;
                    if (ride != null) {
                        if (ride instanceof EntityPlayer && ride.isSneaking()) {
                            this.setDead();
                        }
                        final float ridenSize = ride.width * 2.0f + ride.height;
                        float shootingSize = shooting.width * 2.0f + shooting.height + 0.2f;
                        for (int i = 1; i <= 4; ++i) {
                            final ItemStack boots = shooting.getEquipmentInSlot(i);
                            if (boots != null && boots.getItem() != null && boots.getItem() instanceof ItemArmorHeavy) {
                                shootingSize += 5.0f;
                            }
                        }
                        if (shootingSize > ridenSize && this.hookedEntity.canBePushed()) {
                            pullToShooter = true;
                        }
                    }
                    double pullSpeed = 0.0;
                    if (this.manualPull()) {
                        double playerSpeed = Math.sqrt(shooting.motionX * shooting.motionX + shooting.motionZ * shooting.motionZ + shooting.motionY * shooting.motionY);
                        playerSpeed *= Math.max(1.0, this.getDistanceSqToEntity((Entity)shooting) / (this.radio * this.radio));
                        playerSpeed /= 10.0;
                        if (playerSpeed > shooting.jumpMovementFactor && !pullToShooter) {
                            shooting.jumpMovementFactor = (float)Math.min(playerSpeed, 0.14);
                        }
                    }
                    else {
                        float max = shooting.height;
                        if (shooting instanceof EntityPlayer) {
                            max = 0.0f;
                        }
                        this.radio = Math.max(shooting.height, this.radio - Math.min(0.6, this.radio / 30.0));
                    }
                    pullSpeed = dist - 0.2;
                    pullSpeed -= this.radio;
                    if (pullSpeed < 0.0) {
                        pullSpeed = 0.0;
                    }
                    if (pullSpeed > 2.0) {
                        pullSpeed = 2.0;
                    }
                    pullSpeed *= 0.03;
                    final Vec3 fc = Vec3.createVectorHelper(shooting.posX - this.posX, shooting.posY - this.posY, shooting.posZ - this.posZ);
                    fc.normalize();
                    final Vec3 vec3 = fc;
                    vec3.xCoord *= pullSpeed;
                    final Vec3 vec4 = fc;
                    vec4.zCoord *= pullSpeed;
                    final Vec3 vec5 = fc;
                    vec5.yCoord *= pullSpeed;
                    if (pullToShooter) {
                        if (dist > this.radio || this.radio < 1.0) {
                            final double s = 1.0;
                            ride.motionX = fc.xCoord;
                            ride.motionY = fc.yCoord;
                            ride.motionZ = fc.zCoord;
                            this.motionX = fc.xCoord;
                            this.motionY = fc.yCoord;
                            this.motionZ = fc.zCoord;
                        }
                    }
                    else {
                        shooting.motionX -= fc.xCoord;
                        shooting.motionZ -= fc.zCoord;
                        shooting.motionY -= fc.yCoord;
                    }
                }
                else {
                    if (dist > this.getMaxRadius()) {
                        this.returning = true;
                    }
                    if (this.returning) {
                        this.radio = dist;
                        final Vec3 fc2 = Vec3.createVectorHelper(shooting.posX - this.posX, shooting.posY + 1.4 - this.posY, shooting.posZ - this.posZ);
                        fc2.normalize();
                        double s2 = 0.2;
                        if (this.getHookType() == 5) {
                            s2 = 0.05;
                        }
                        final boolean flag = true;
                        this.motionX = fc2.xCoord * s2;
                        this.motionY = fc2.yCoord * s2;
                        this.motionZ = fc2.zCoord * s2;
                    }
                    else {
                        this.radio = ((dist < this.getMaxRadius()) ? dist : this.getMaxRadius());
                    }
                }
                if (this.returning && !this.isReeling() && dist <= shooting.width + shooting.height) {
                    this.setDead();
                }
            }
        }
    }
    
    protected float getGravityVelocity() {
        return 0.0f;
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.setDead();
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public ItemStack getHookShoot(final EntityPlayer ep) {
        if (ep != null) {
            for (int i = 0; i < ep.inventory.getSizeInventory(); ++i) {
                final ItemStack is = ep.inventory.getStackInSlot(i);
                if (is != null) {
                    final int id = this.getEntityId();
                    if (is.getItem() instanceof IHookLauncher && ((IHookLauncher)is.getItem()).getHookID(is) == id) {
                        return is;
                    }
                }
            }
        }
        return null;
    }
    
    public void setDead() {
        if (this.getThrower() instanceof EntityPlayer) {
            final ItemStack is = this.getHookShoot((EntityPlayer)this.getThrower());
            if (is != null) {
                ((IHookLauncher)is.getItem()).setHookID(is, 0);
            }
        }
        super.setDead();
    }
    
    public double getRadius() {
        return this.radio;
    }
    
    public int getRopeColor() {
        final byte type = this.getHookType();
        return (type == 3 || type == 5) ? 16777215 : 4473924;
    }
    
    public int getMaxRadius() {
        if (this.getHookType() == 3) {
            return 40;
        }
        if (this.getHookType() == 2) {
            return 30;
        }
        if (this.getHookType() == 1) {
            return 25;
        }
        if (this.getHookType() == 0) {
            return 15;
        }
        return 40;
    }
    
    public EntityLivingBase getThrower() {
        return this.shootingEntity;
    }
    
    public void setThrower(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.shootingEntity = (EntityLivingBase)entity;
        }
    }
    
    protected boolean isSpiderHook() {
        final int hookType = this.getHookType();
        return hookType == 3 || hookType == 4;
    }
    
    private boolean manualPull() {
        final int hookType = this.getHookType();
        return hookType == 2 || hookType == 3 || hookType == 4;
    }
}
