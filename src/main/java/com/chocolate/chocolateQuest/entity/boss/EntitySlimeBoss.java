package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.util.DamageSource;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.List;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIBase;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntitySlimeBoss extends EntityBaseBoss
{
    public AttackKick kickHelper;
    public int slimePoolAttackTime;
    public int slimePoolChargeTime;
    public int slimePoolAttackTimeMax;
    final byte SLIMEPOOL = 5;
    
    public EntitySlimeBoss(final World par1World) {
        super(par1World);
        this.kickHelper = new AttackKick(this);
        this.slimePoolChargeTime = 30;
        this.slimePoolAttackTimeMax = 100;
        this.kickHelper.setSpeed(16 + (int)(this.size * 4.0f));
        this.projectileDefense = 30;
        this.physicDefense = 10;
        this.blastDefense = -20;
        this.limitRotation = true;
        this.xpRatio = 1.5f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(25.0);
    }
    
    @Override
    protected void scaleAttributes() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3 + this.lvl * 0.01);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0 + this.lvl * 0.6);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0 + this.lvl * 150.0);
    }
    
    @Override
    public void addAITasks() {
        this.tasks.addTask(1, (EntityAIBase)new AIBossAttack(this, 1.0f, false));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityLivingBase.class, 0, true));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true));
    }
    
    @Override
    public float getMinSize() {
        return 1.4f;
    }
    
    @Override
    public float getSizeVariation() {
        return 0.5f;
    }
    
    @Override
    public void animationBoss(final byte animType) {
        switch (animType) {
            case 5: {
                this.startPoolAttack();
                break;
            }
        }
        this.kickHelper.kick(animType);
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            if (this.slimePoolAttackTime > 0) {
                final double width = this.width / 2.0f;
                if (this.slimePoolAttackTime < this.slimePoolAttackTimeMax - this.slimePoolChargeTime) {
                    for (int i = 0; i < 3; ++i) {
                        EffectManager.spawnParticle(0, this.worldObj, this.posX + this.rand.nextGaussian() * width, this.posY + 0.3, this.posZ + this.rand.nextGaussian() * width, 0.2 + this.rand.nextFloat() / 8.0f, 0.6 + this.rand.nextFloat() / 4.0f, 0.2 + this.rand.nextFloat() / 8.0f);
                    }
                }
                final float desp = this.size / 2.0f;
                EffectManager.spawnParticle(1, this.worldObj, this.posX + this.rand.nextGaussian() * desp, this.posY + 0.2, this.posZ + this.rand.nextGaussian() * desp, 0.2 + this.rand.nextFloat() / 8.0f, 0.6 + this.rand.nextFloat() / 4.0f, 0.2 + this.rand.nextFloat() / 8.0f);
            }
            else {
                final float desp2 = this.size / 2.0f;
                EffectManager.spawnParticle(2, this.worldObj, this.posX + this.rand.nextGaussian() * desp2, this.posY + 0.2, this.posZ + this.rand.nextGaussian() * desp2, 0.2 + this.rand.nextFloat() / 8.0f, 0.6 + this.rand.nextFloat() / 4.0f, 0.2 + this.rand.nextFloat() / 8.0f);
            }
        }
        if (this.getHealth() < this.getMaxHealth() / 10.0f && this.ticksExisted % 20 == 0) {
            final List list = this.worldObj.getEntitiesWithinAABB((Class)EntitySlimePart.class, this.boundingBox.expand(16.0, 4.0, 16.0));
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity1 = list.get(j);
                this.setAttackTarget((EntityLivingBase)entity1);
            }
        }
        if (!this.isDead) {
            this.kickHelper.onUpdate();
            if (this.isAttacking()) {
                if (this.onGround) {
                    this.setAttacking(false);
                }
                else {
                    this.damageNearby(1.0);
                }
            }
            if (this.slimePoolAttackTime > 0) {
                if (this.slimePoolAttackTime > 40 && this.slimePoolAttackTime % 30 == 0) {
                    this.worldObj.playSoundEffect((double)(int)this.posX, (double)(int)this.posY, (double)(int)this.posZ, "chocolatequest:bubble_explode", 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
                }
                --this.slimePoolAttackTime;
                if (this.slimePoolAttackTime < this.slimePoolAttackTimeMax - this.slimePoolChargeTime) {
                    this.damagePool();
                }
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public boolean attackInProgress() {
        return this.kickHelper.isAttackInProgress() || this.slimePoolAttackTime > 0;
    }
    
    @Override
    public void attackEntity(final Entity entity, final float f) {
        if (this.slimePoolAttackTime > 0) {
            return;
        }
        if (f > 64.0f * this.width / 2.0f + this.width * this.width && this.onGround) {
            final int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities((Entity)this, entity) - this.rotationYaw);
            if (Math.abs(angle) < 2) {
                this.jumpToTarget(entity);
            }
        }
        float width = this.width + entity.width;
        width *= width;
        if (entity.getClass() == EntitySlimePart.class) {
            if (f < width) {
                entity.applyEntityCollision((Entity)this);
                this.swingItem();
            }
            return;
        }
        if (this.ticksExisted % 20 == 0 && !this.worldObj.isRemote) {
            final boolean targetUp = this.posY - entity.posY + this.height <= 0.0 && f < this.size * this.size * 2.0f;
            if ((this.rand.nextInt(6) == 0 || targetUp) && f < width * 2.0f) {
                this.startPoolAttack();
                return;
            }
            if (f < width * 1.5) {
                this.kickHelper.attackTarget(entity);
            }
        }
        super.attackEntity(entity, f);
    }
    
    public void startPoolAttack() {
        if (!this.worldObj.isRemote) {
            final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)5);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet, 64);
        }
        if (this.slimePoolAttackTime == 0) {
            this.slimePoolAttackTime = this.slimePoolAttackTimeMax;
        }
    }
    
    public boolean jumpToTarget(final Entity entity) {
        if (!this.isAttacking()) {
            final float rotation = this.rotationYaw * 3.141592f / 180.0f;
            final double vx = entity.posX - this.posX;
            final double vy = entity.posY - this.posY;
            final double vz = entity.posZ - this.posZ;
            this.motionX = vx / 4.0;
            this.motionY = vy / 5.0 + entity.height / 6.0f;
            this.motionZ = vz / 4.0;
            this.setAttacking(true);
            return true;
        }
        return false;
    }
    
    public void damageNearby(final double expand) {
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(expand, expand, expand));
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity1 = list.get(j);
            if (entity1.canBeCollidedWith() && !this.isEntityEqual(entity1)) {
                if (entity1 != this.riddenByEntity) {
                    this.attackEntityAsMob(entity1);
                    this.setAttacking(false);
                }
            }
        }
    }
    
    public void damagePool() {
        final double expand = this.width * 0.7;
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(expand, expand, expand));
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity1 = list.get(j);
            if (entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith() && !this.isEntityEqual(entity1)) {
                if (entity1 != this.riddenByEntity) {
                    this.attackEntityAsMob(entity1, 0.6f);
                    this.setAttacking(false);
                }
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        final boolean flag = super.attackEntityFrom(par1DamageSource, par2);
        if (!this.worldObj.isRemote && flag && this.getHealth() > 0.0f && this.hurtTime != 0 && (this.rand.nextInt(4) == 0 || (par2 >= 4.0f && this.rand.nextInt(2) == 0))) {
            final EntitySlimePart part = new EntitySlimePart(this.worldObj, (EntityLivingBase)this, (int)Math.max(1.0f, Math.min(this.size * 2.0f, par2 / 4.0f)));
            part.setPosition(this.posX, this.posY + 1.0, this.posZ);
            part.motionX = this.rand.nextGaussian();
            part.motionZ = this.rand.nextGaussian();
            this.worldObj.spawnEntityInWorld((Entity)part);
        }
        return flag;
    }
    
    protected void fall(final float par1) {
    }
    
    protected String getHurtSound() {
        return "mob.slime.big";
    }
    
    protected String getDeathSound() {
        return "mob.slime.big";
    }
    
    @Override
    protected int getDropMaterial() {
        return 3;
    }
}
