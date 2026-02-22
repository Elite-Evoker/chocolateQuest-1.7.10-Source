package com.chocolate.chocolateQuest.entity.npc;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;
import com.chocolate.chocolateQuest.entity.handHelper.HandEmpty;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import java.util.List;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import net.minecraft.entity.projectile.EntityArrow;
import cpw.mods.fml.common.registry.IThrowableEntity;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileRocket;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.handHelper.HandGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import com.chocolate.chocolateQuest.entity.ai.HumanSelector;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.ai.AITargetOwner;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIBase;
import com.chocolate.chocolateQuest.entity.ai.AIHumanGoToPoint;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import net.minecraft.world.World;
import net.minecraft.entity.INpc;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class EntityGolemMecha extends EntityHumanBase implements INpc
{
    public static final byte SHIELD_WATCHER = 17;
    int gunCD;
    private int CDshield;
    private int shieldAmmount;
    
    public EntityGolemMecha(final World world) {
        super(world);
        this.gunCD = 0;
        this.fireDefense = 20;
        this.blastDefense = 20;
        this.magicDefense = -10;
        this.shouldDespawn = false;
        this.AIMode = EnumAiState.FOLLOW.ordinal();
        this.AICombatMode = EnumAiCombat.DEFENSIVE.ordinal();
    }
    
    public EntityGolemMecha(final World world, final EntityLivingBase owner) {
        this(world);
        this.setOwner(owner);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (Object)0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.24);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
    }
    
    @Override
    protected void addAITasks() {
        this.tasks.addTask(1, (EntityAIBase)new AIHumanGoToPoint(this));
        this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.setAIForCurrentMode();
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true));
        this.targetTasks.addTask(3, (EntityAIBase)new AITargetOwner(this));
        this.targetTasks.addTask(4, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true, false, (IEntitySelector)new HumanSelector(this)));
        this.targetTasks.addTask(4, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityHumanBase.class, 0, true, false, (IEntitySelector)new HumanSelector(this)));
        this.targetTasks.addTask(5, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)IMob.class, 0, true, false, (IEntitySelector)null));
    }
    
    @Override
    public void onLivingUpdate() {
        this.setSize(0.8f, 2.9f);
        if (this.riddenByEntity instanceof EntityPlayer) {
            final EntityPlayer ridden = (EntityPlayer)this.riddenByEntity;
            this.moveForwardHuman = 0.0f;
            if (this.gunCD > 0) {
                --this.gunCD;
            }
            if (ridden.isSwingInProgress && this.gunCD < 40) {
                ridden.swingProgress = 0.0f;
                ridden.swingProgressInt = 0;
                ridden.isSwingInProgress = false;
                if (this.leftHand instanceof HandGolem) {
                    final HandGolem hand = (HandGolem)this.leftHand;
                    hand.onClick();
                }
            }
        }
        if (!this.worldObj.isRemote) {
            if (this.hasElectricField() && this.ticksExisted % 18 - this.getUpgradeLevel(1) * 3 == 0) {
                final double expand = 1.5;
                final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(expand, 1.0, expand));
                for (int j = 0; j < list.size(); ++j) {
                    final Entity entity1 = list.get(j);
                    if (entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith() && !this.isEntityEqual(entity1)) {
                        if (entity1 != this.riddenByEntity) {
                            if (!this.isOnSameTeam((EntityLivingBase)entity1) && entity1.attackEntityFrom(Elements.blast.getDamageSource((Entity)this), 2.0f)) {
                                break;
                            }
                        }
                    }
                }
            }
            final int rocketLevel = this.getUpgradeLevel(3);
            if (rocketLevel > 0 && this.ticksExisted % (100 - 20 * rocketLevel) == 0) {
                final double expand2 = 25.0;
                final List list2 = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(expand2, 1.0, expand2));
                for (int i = 0; i < list2.size(); ++i) {
                    final Entity entity2 = list2.get(i);
                    if (entity2 instanceof EntityLivingBase && entity2.canBeCollidedWith() && !this.isEntityEqual(entity2)) {
                        if (entity2 != this.riddenByEntity) {
                            if (((EntityLivingBase)entity2).getTeam() != null && !this.isOnSameTeam((EntityLivingBase)entity2)) {
                                if (!this.worldObj.isRemote) {
                                    final EntityBaseBall ball = new EntityBaseBall(this.worldObj, (EntityLivingBase)this, 11, 0);
                                    ball.setBallData(new ProjectileRocket(ball, entity2, 10));
                                    final EntityBaseBall entityBaseBall = ball;
                                    ++entityBaseBall.posY;
                                    ball.motionY = 1.0;
                                    final EntityBaseBall entityBaseBall2 = ball;
                                    final EntityBaseBall entityBaseBall3 = ball;
                                    final double n = 0.0;
                                    entityBaseBall3.motionZ = n;
                                    entityBaseBall2.motionX = n;
                                    this.worldObj.spawnEntityInWorld((Entity)ball);
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
            final int shieldLevel = this.getUpgradeLevel(2);
            if (this.hasElectricShield()) {
                final double expand3 = 1.5;
                final List list3 = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(expand3, 1.0, expand3));
                for (int k = 0; k < list3.size(); ++k) {
                    final Entity entity3 = list3.get(k);
                    boolean isProjectile = false;
                    if (entity3 instanceof IThrowableEntity && ((IThrowableEntity)entity3).getThrower() != this) {
                        this.damageShield(20);
                        isProjectile = true;
                    }
                    if (entity3 instanceof EntityArrow) {
                        this.damageShield(30);
                        isProjectile = true;
                    }
                    if (isProjectile && !this.worldObj.isRemote) {
                        entity3.setDead();
                        final PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround((byte)5, entity3.posX, entity3.posY, entity3.posZ);
                        ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet, 64);
                    }
                }
            }
            if (this.CDshield > 0) {
                --this.CDshield;
                if (this.CDshield == 0) {
                    this.setShieldON(true);
                }
                if (this.shieldAmmount < 50 + shieldLevel * 50) {
                    this.shieldAmmount += shieldLevel;
                }
            }
            if (this.shieldAmmount < 50 + shieldLevel * 50 && this.ticksExisted % 2 == 0) {
                ++this.shieldAmmount;
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void updateHands() {
        if (this.getEquipmentInSlot(0) != null && this.getEquipmentInSlot(0).getItem() instanceof ItemGolemWeapon) {
            this.rightHand = new HandGolem(this, this.getEquipmentInSlot(0));
        }
        else {
            this.rightHand = new HandHelper(this, this.getEquipmentInSlot(0));
        }
        if (this.getLeftHandItem() != null && this.getLeftHandItem().getItem() instanceof ItemGolemWeapon) {
            this.leftHand = new HandGolem(this, this.getLeftHandItem());
        }
        else {
            this.leftHand = new HandEmpty(this, this.getLeftHandItem());
        }
    }
    
    @Override
    public boolean isSitting() {
        return false;
    }
    
    @Override
    public double getMountedYOffset() {
        if (this.riddenByEntity instanceof EntityPlayer) {
            return 1.88;
        }
        return 1.1;
    }
    
    @Override
    public float getSizeModifier() {
        return 1.6f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float i) {
        final Entity entity = damagesource.getEntity();
        if (this.riddenByEntity != null && damagesource.getEntity() instanceof EntityLivingBase) {
            final EntityLivingBase e = (EntityLivingBase)entity;
            if (e == this.riddenByEntity) {
                return false;
            }
            if (!this.isSuitableTargetAlly(e)) {
                this.setAttackTarget((EntityLivingBase)entity);
            }
        }
        if (this.hasElectricShield()) {
            if (damagesource.isProjectile()) {
                this.damageShield(30);
                return false;
            }
            if (entity != null && this.getDistanceSqToEntity(entity) > 6.0) {
                if (entity instanceof EntityPlayer && ((EntityPlayer)entity).swingProgress != 0.0f) {
                    return false;
                }
                this.damageShield((int)(i * 3.0f));
                return true;
            }
        }
        return super.attackEntityFrom(damagesource, i);
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public void moveEntityWithHeading(float par1, float par2) {
        if (par2 < 0.0f) {
            par2 /= 3.0f;
        }
        if (this.riddenByEntity instanceof EntityPlayer) {
            final float rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationYaw = rotationYaw;
            this.prevRotationYaw = rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5f;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            final float rotationYaw2 = this.rotationYaw;
            this.renderYawOffset = rotationYaw2;
            this.rotationYawHead = rotationYaw2;
            par1 = ((EntityLivingBase)this.riddenByEntity).moveStrafing;
            par1 /= 2.0f;
            par2 = ((EntityLivingBase)this.riddenByEntity).moveForward;
            if (par2 < 0.0f) {
                par2 /= 2.0f;
            }
            final float moveSpeed = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            this.setAIMoveSpeed(moveSpeed / 2.0f);
            this.stepHeight = 1.0f;
            if (!this.worldObj.isRemote) {
                super.moveEntityWithHeading(par1, par2);
            }
            this.prevLimbSwingAmount = this.limbSwingAmount;
            final double d0 = this.posX - this.prevPosX;
            final double d2 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 4.0f;
            if (f4 > 1.0f) {
                f4 = 1.0f;
            }
            this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4f;
            this.limbSwing += this.limbSwingAmount;
        }
        else {
            super.moveEntityWithHeading(par1, par2);
        }
    }
    
    public boolean interact(final EntityPlayer entityPlayer) {
        if (this.riddenByEntity == null) {
            if (entityPlayer.isSneaking()) {
                return super.interact(entityPlayer);
            }
            this.setAttackTarget(null);
            if (!this.worldObj.isRemote) {
                entityPlayer.mountEntity((Entity)this);
            }
        }
        else if (this.riddenByEntity instanceof EntityHumanNPC) {
            ((EntityHumanNPC)this.riddenByEntity).interact(entityPlayer);
        }
        else if (this.rightHand instanceof HandGolem) {
            final HandGolem hand = (HandGolem)this.rightHand;
            hand.onClick();
        }
        return false;
    }
    
    @Override
    public void setOwner(final EntityLivingBase entity) {
        super.setOwner(entity);
        if (entity instanceof EntityPlayer && entity.getTeam() != null) {
            this.entityTeam = entity.getTeam().getRegisteredName();
        }
    }
    
    protected String getLivingSound() {
        return "none";
    }
    
    protected String getHurtSound() {
        return "mob.irongolem.hit";
    }
    
    protected String getDeathSound() {
        return "mob.irongolem.death";
    }
    
    public boolean canRiderInteract() {
        return true;
    }
    
    @Override
    public float getEyeHeight() {
        return super.getEyeHeight();
    }
    
    @Override
    public boolean isTwoHanded() {
        return false;
    }
    
    @Override
    public boolean canSprint() {
        return false;
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    @Override
    public boolean canAimBeCanceled() {
        return false;
    }
    
    public boolean isInvisible() {
        return false;
    }
    
    public boolean isPushedByWater() {
        return false;
    }
    
    public void setFire(final int par1) {
    }
    
    public int getTotalArmorValue() {
        return this.getUpgradeLevel(0) * 5;
    }
    
    public boolean hasElectricField() {
        return this.getUpgradeLevel(1) > 0;
    }
    
    public boolean hasElectricShield() {
        return this.CDshield == 0 && this.getUpgradeLevel(2) > 0;
    }
    
    public void setShieldON(final boolean mode) {
        this.dataWatcher.updateObject(17, (Object)(byte)(mode ? 0 : 1));
    }
    
    public boolean shieldON() {
        return this.dataWatcher.getWatchableObjectByte(17) == 0;
    }
    
    public void damageShield(final int damage) {
        this.shieldAmmount -= damage;
        if (this.shieldAmmount <= 0 && !this.worldObj.isRemote) {
            this.shieldAmmount = 0;
            this.CDshield = 60;
            this.setShieldON(false);
        }
    }
    
    public int getUpgradeLevel(final int upgrade) {
        int armor = 0;
        for (int i = 1; i <= 4; ++i) {
            final ItemStack is = this.getEquipmentInSlot(i);
            if (is != null && is.getItem() == ChocolateQuest.golemUpgrade && is.getMetadata() == upgrade) {
                ++armor;
            }
        }
        return armor;
    }
}
