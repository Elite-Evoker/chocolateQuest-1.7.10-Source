package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.util.DamageSource;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketAttackToXYZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityCreature;
import com.chocolate.chocolateQuest.entity.ai.AITargetHurtBy;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;

public class EntitySpiderBoss extends EntityBaseBoss
{
    public AttackPunch rightHand;
    public AttackPunch leftHand;
    public int attackTimeLeft;
    public int attackTimeRight;
    public int attackSpeed;
    int projectileCoolDown;
    EntityHookShoot web;
    EntityHookShoot escapeWeb;
    
    public EntitySpiderBoss(final World par1World) {
        super(par1World);
        this.attackTimeLeft = 0;
        this.attackTimeRight = 0;
        this.attackSpeed = 10;
        this.ridableBB = false;
        (this.leftHand = new AttackPunch(this, (byte)5, 0.2f, 2.0f)).setAngle(-55, 2, 0.4f);
        (this.rightHand = new AttackPunch(this, (byte)6, 0.2f, 2.0f)).setAngle(55, 2, 0.4f);
        this.projectileDefense = 10;
        this.magicDefense = -20;
        this.limitRotation = true;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(46.0);
    }
    
    @Override
    protected void scaleAttributes() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2 + this.lvl * 0.02);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(14.0 + this.lvl * 0.8);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0 + this.lvl * 250.0);
    }
    
    @Override
    public void addAITasks() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(2, (EntityAIBase)new AIBossAttack(this, 1.0f, false));
        this.targetTasks.addTask(1, (EntityAIBase)new AITargetHurtBy(this, false));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityMob.class, 0, true));
    }
    
    @Override
    public float getMinSize() {
        return 0.6f;
    }
    
    @Override
    public float getSizeVariation() {
        return 0.4f;
    }
    
    public void onUpdate() {
        if (this.worldObj.isRemote) {
            if (this.getHealth() < this.getMaxHealth() / 3.0f) {
                final float rot = this.rotationYaw * 3.1416f / 180.0f;
                final double scale = this.width / 2.0f;
                final double posX = this.posX - MathHelper.sin(rot) * scale;
                final double posY = this.posY + this.height * 0.3;
                final double posZ = this.posZ + MathHelper.cos(rot) * scale;
                final float desp = this.width * 0.05f;
                EffectManager.spawnParticle(2, this.worldObj, posX + this.rand.nextGaussian() * desp, posY + 0.2, posZ + this.rand.nextGaussian() * desp, 0.0 + this.rand.nextFloat() / 8.0f, 0.3 + this.rand.nextFloat() / 4.0f, 0.0 + this.rand.nextFloat() / 8.0f);
            }
        }
        else if (this.getAttackTarget() == null && this.escapeWeb == null && !this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) {
            this.escapeWeb = new EntityHookShoot(this.worldObj, (EntityLivingBase)this, 3);
            this.escapeWeb.motionY = 2.6;
            this.escapeWeb.motionX = 0.0;
            this.escapeWeb.motionZ = 0.0;
            this.worldObj.spawnEntityInWorld((Entity)this.escapeWeb);
        }
        if (this.web != null && this.web.isReeling()) {
            boolean setHookDead = false;
            if (this.web.hookedEntity != null) {
                if (this.getAttackTarget() != this.web.hookedEntity && this.web.hookedEntity instanceof EntityLivingBase) {
                    this.setAttackTarget((EntityLivingBase)this.web.hookedEntity);
                }
            }
            else if (this.getAttackTarget() != null && this.getDistanceSqToEntity((Entity)this.web) < this.getDistanceSqToEntity((Entity)this.getAttackTarget())) {
                setHookDead = true;
            }
            if (this.web.ticksExisted > 100 || setHookDead) {
                this.web.setDead();
                this.web = null;
            }
        }
        if (this.escapeWeb != null) {
            if (!this.escapeWeb.isReeling()) {
                final EntityHookShoot escapeWeb = this.escapeWeb;
                escapeWeb.motionY -= 0.2;
            }
            if (this.escapeWeb.radio > this.size) {
                final EntityHookShoot escapeWeb2 = this.escapeWeb;
                --escapeWeb2.radio;
            }
            if ((this.escapeWeb.ticksExisted > 100 && this.onGround) || this.escapeWeb.hookedEntity != null || this.escapeWeb.ticksExisted > 300) {
                this.escapeWeb.setDead();
            }
            if (!this.escapeWeb.isEntityAlive()) {
                this.escapeWeb = null;
            }
            if (this.isCollidedHorizontally || this.isCollidedVertically) {
                this.motionY = 0.0;
            }
        }
        if (!this.isDead) {
            this.rightHand.onUpdate();
            this.leftHand.onUpdate();
        }
        super.onUpdate();
    }
    
    @Override
    public void attackToXYZ(final byte arm, final double x, final double y, final double z) {
        if (!this.worldObj.isRemote) {
            final PacketAttackToXYZ packet = new PacketAttackToXYZ(this.getEntityId(), arm, x, y, z);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet, 64);
        }
        if (arm == 5) {
            this.leftHand.swingArmTo(x, y, z);
        }
        else {
            this.rightHand.swingArmTo(x, y, z);
        }
    }
    
    @Override
    public void attackEntity(final Entity target, final float par2) {
        if (!this.attackInProgress()) {
            if (this.projectileCoolDown < 60) {
                ++this.projectileCoolDown;
                if (this.projectileCoolDown == 54) {
                    this.swingItem();
                }
            }
            else {
                this.projectileCoolDown = this.rand.nextInt(30);
                if (this.rand.nextInt(3) == 0 && this.web == null) {
                    this.shootWeb();
                }
                else if (!this.worldObj.isRemote) {
                    final EntityBaseBall ball = new EntityBaseBall(this.worldObj, (EntityLivingBase)this, 0, 0);
                    ball.setThrowableHeading(target.posX - this.posX, target.posY - this.posY, target.posZ - this.posZ, 1.0f, 0.0f);
                    final EntityBaseBall entityBaseBall = ball;
                    entityBaseBall.posY -= this.height / 2.0f;
                    this.worldObj.spawnEntityInWorld((Entity)ball);
                }
            }
        }
        if (this.ticksExisted % (this.attackSpeed + 1) == 0 && !this.worldObj.isRemote && (!this.leftHand.attackInProgress() || !this.rightHand.attackInProgress())) {
            final double posY = this.posY + this.size;
            final int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities((Entity)this, target) - this.rotationYaw);
            final double targetY = target.posY + target.height;
            final double dx = this.posX - target.posX;
            final double dy = this.posY + this.size - targetY;
            final double dz = this.posZ - target.posZ;
            final double distToHead = dx * dx + dy * dy + dz * dz - this.width * this.width;
            if (distToHead < (this.rightHand.getArmLength() + 1.0) * (this.rightHand.getArmLength() + 1.0)) {
                final boolean targetDown = this.posY - target.posY - target.height > 0.0;
                boolean handFlag = this.rand.nextBoolean();
                final boolean ARM_LEFT = false;
                final boolean ARM_RIGHT = true;
                handFlag = this.rand.nextBoolean();
                if (!targetDown) {
                    if (!handFlag) {
                        if (this.leftHand.attackInProgress() || angle > 20) {
                            handFlag = true;
                        }
                    }
                    else if (this.rightHand.attackInProgress() || angle < -20) {
                        handFlag = false;
                    }
                }
                if ((angle > -110 && angle < 110) || targetDown) {
                    if (!handFlag && !this.leftHand.attackInProgress()) {
                        this.leftHand.attackTarget(target);
                    }
                    else if (handFlag && !this.rightHand.attackInProgress()) {
                        this.rightHand.attackTarget(target);
                    }
                }
            }
        }
        super.attackEntity(target, par2);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        if (this.escapeWeb == null && this.rand.nextInt(10) == 0) {
            this.shootEscapeWeb();
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    public void shootWeb() {
        if (!this.worldObj.isRemote) {
            final int pos = 0;
            if (this.web != null) {
                this.web.setDead();
                this.web = null;
            }
            final Entity t = (Entity)this.getAttackTarget();
            (this.web = new EntityHookShoot(this.worldObj, (EntityLivingBase)this, 3)).setThrowableHeading(t.posX - this.posX, t.posY - this.posY - this.height / 2.0f, t.posZ - this.posZ, 1.0f, 0.0f);
            this.worldObj.spawnEntityInWorld((Entity)this.web);
        }
    }
    
    public void shootEscapeWeb() {
        if (!this.worldObj.isRemote) {
            final int pos = 0;
            if (this.escapeWeb != null) {
                this.escapeWeb.setDead();
                this.escapeWeb = null;
            }
            this.escapeWeb = new EntityHookShoot(this.worldObj, (EntityLivingBase)this, 5);
            this.escapeWeb.motionY = 2.5;
            this.escapeWeb.motionX = this.rand.nextGaussian();
            this.escapeWeb.motionZ = this.rand.nextGaussian();
            this.worldObj.spawnEntityInWorld((Entity)this.escapeWeb);
        }
    }
    
    public void setInWeb() {
    }
    
    @Override
    public boolean attackInProgress() {
        return this.leftHand.isAttacking || this.rightHand.isAttacking || this.isSwingInProgress;
    }
    
    public boolean isSneaking() {
        return true;
    }
    
    public double getArmLength() {
        return this.size;
    }
    
    @Override
    protected void resize() {
        this.setSize(this.size * 1.4f, this.size);
    }
    
    protected void fall(final float par1) {
    }
    
    protected String getLivingSound() {
        return "mob.spider.say";
    }
    
    protected String getHurtSound() {
        return "mob.spider.say";
    }
    
    protected String getDeathSound() {
        return "mob.spider.death";
    }
    
    @Override
    protected int getDropMaterial() {
        return 4;
    }
}
