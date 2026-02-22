package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import java.util.List;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIBase;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityBull extends EntityBaseBoss
{
    public int smashTime;
    public final int smashSpeed = 30;
    final byte CHARGE_ANIM = 5;
    final byte SMASH = 6;
    public boolean charge;
    public int chargeTime;
    public int chargeTimeMax;
    private float damageRight;
    private float damageLeft;
    public AttackKickQuadruped kickHelper;
    
    public EntityBull(final World par1World) {
        super(par1World);
        this.chargeTime = 0;
        this.chargeTimeMax = 50;
        this.kickHelper = new AttackKickQuadruped(this);
        this.experienceValue = 5;
        this.size = this.rand.nextFloat() * 4.0f + 1.0f;
        this.stepHeight = 2.0f;
        this.resize();
        this.kickHelper.setSpeed(16 + (int)(this.size * 5.0f));
        this.limitRotation = true;
    }
    
    @Override
    protected void scaleAttributes() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3 + 0.01 * this.lvl);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(14.0 + 0.8 * this.lvl);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0 + 200.0 * this.lvl);
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
        return 0.6f;
    }
    
    @Override
    public float getSizeVariation() {
        return 0.4f;
    }
    
    @Override
    public void animationBoss(final byte animType) {
        switch (animType) {
            case 5: {
                this.startCharging();
                return;
            }
            case 6: {
                this.startSmashing();
                return;
            }
            default: {
                this.kickHelper.kick(animType);
            }
        }
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.isDead) {
            this.updateCharge();
            this.updateSmash();
            this.kickHelper.onUpdate();
        }
    }
    
    @Override
    public boolean attackInProgress() {
        return this.kickHelper.isAttackInProgress() || this.smashTime > 0;
    }
    
    @Override
    public boolean shouldMoveToEntity(final double d1, final Entity target) {
        return !this.attackInProgress() && !this.charge && super.shouldMoveToEntity(d1, target);
    }
    
    @Override
    public void attackEntity(final Entity entity, final float f) {
        final float width = this.width + entity.width;
        final boolean targetUp = this.posY - entity.posY + this.height <= 0.0 && f < this.size * this.size * 2.0f;
        if (!this.isAttacking()) {
            if (f > (this.width + 8.0f) * (this.width + 8.0f) || targetUp) {
                final int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities((Entity)this, entity) - this.rotationYaw);
                if ((angle < 1 && angle > -1) || targetUp) {
                    this.startCharging();
                }
            }
        }
        if (this.ticksExisted % 30 == 0 && !this.worldObj.isRemote && !this.charge && this.smashTime == 0 && f < (width + 2.0f) * (width + 2.0f)) {
            if (this.rand.nextInt(4) == 0 && this.kickHelper.kickTime == 0) {
                this.startSmashing();
            }
            else {
                this.kickHelper.attackTarget(entity);
            }
        }
        super.attackEntity(entity, f);
    }
    
    public void updateSmash() {
        if (this.smashTime > 0) {
            --this.smashTime;
            if (this.smashTime == 1) {
                final float rotation = this.rotationYawHead * 3.141592f / 180.0f;
                final double d = this.size;
                final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, 0.0, d).addCoord(0.0, -2.0, 0.0));
                final int min = -60;
                final int max = 60;
                for (int j = 0; j < list.size(); ++j) {
                    final Entity entity1 = list.get(j);
                    if (entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith() && !this.isEntityEqual(entity1)) {
                        if (entity1 != this.riddenByEntity) {
                            final int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities((Entity)this, entity1) - this.rotationYaw);
                            if (angle >= min && angle <= max && this.attackEntityAsMob(entity1)) {
                                final float dist = 1.0f;
                                final double x = -Math.sin(rotation) * dist;
                                final double z = Math.cos(rotation) * dist;
                                final Entity entity2 = entity1;
                                entity2.motionX += x;
                                final Entity entity3 = entity1;
                                entity3.motionZ += z;
                            }
                        }
                    }
                }
                if (!this.worldObj.isRemote) {
                    EntityBaseBall ball = new EntityBaseBall(this.worldObj, (EntityLivingBase)this, 4, 2);
                    final float legAngle = 0.5f;
                    double x2 = this.posX - MathHelper.sin(rotation + 0.5f) * this.width;
                    double z2 = this.posZ + MathHelper.cos(rotation + 0.5f) * this.width;
                    ball.posX = x2;
                    ball.posY = this.posY + 1.0;
                    ball.posZ = z2;
                    this.worldObj.spawnEntityInWorld((Entity)ball);
                    ball = new EntityBaseBall(this.worldObj, (EntityLivingBase)this, 4, 2);
                    x2 = this.posX - MathHelper.sin(rotation - 0.5f) * this.width;
                    z2 = this.posZ + MathHelper.cos(rotation - 0.5f) * this.width;
                    ball.posX = x2;
                    ball.posZ = z2;
                    this.worldObj.spawnEntityInWorld((Entity)ball);
                }
            }
        }
    }
    
    public void startSmashing() {
        if (!this.worldObj.isRemote) {
            final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)6);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet, 64);
        }
        if (this.smashTime == 0) {
            this.smashTime = 30;
        }
    }
    
    public void updateCharge() {
        if (this.charge) {
            ++this.chargeTime;
            final float speed = Math.min(0.5f, 0.1f + this.chargeTime / (float)this.chargeTimeMax);
            final float rotation = this.rotationYaw * 3.141592f / 180.0f;
            final double dx = -MathHelper.sin(rotation) * speed;
            final double dz = MathHelper.cos(rotation) * speed;
            this.motionX = dx;
            this.motionZ = dz;
            if (this.chargeTime >= this.chargeTimeMax) {
                this.chargeTime = 0;
                this.charge = false;
            }
            final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(0.2, 0.2, 0.2));
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity1 = list.get(j);
                if (entity1.canBeCollidedWith() && !this.isEntityEqual(entity1)) {
                    if (entity1 != this.riddenByEntity) {
                        if (this.attackEntityAsMob(entity1)) {
                            final float dist = 1.0f;
                            final double x = -Math.sin(rotation) * dist;
                            final double z = Math.cos(rotation) * dist;
                            final Entity entity2 = entity1;
                            entity2.motionX += x;
                            final Entity entity3 = entity1;
                            entity3.motionZ += z;
                        }
                    }
                }
            }
        }
    }
    
    public void startCharging() {
        if (!this.worldObj.isRemote) {
            final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)5);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet, 64);
        }
        if (this.chargeTime == 0) {
            this.charge = true;
        }
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        final boolean flag = super.attackEntityFrom(par1DamageSource, par2);
        if (!par1DamageSource.isProjectile() && par1DamageSource.getEntity() != null && flag) {
            final Entity attacker = par1DamageSource.getEntity();
            final double dist = this.getDistanceSqToEntity(attacker);
            if (!this.worldObj.isRemote && this.smashTime <= 0 && !this.charge && dist < this.width * this.width * 2.0f * 2.0f) {
                this.kickHelper.attackTarget(attacker);
            }
            final int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities((Entity)this, attacker) - this.rotationYaw);
            if (angle > 45 && angle < 135) {
                this.damageRight += par2;
                if (this.damageRight > this.getMaxHealth() / 4.0f) {
                    this.setHurtRight(true);
                }
            }
            if (angle < -45 && angle > -135) {
                this.damageLeft += par2;
                if (this.damageLeft > this.getMaxHealth() / 4.0f) {
                    this.setHurtLeft(true);
                }
            }
        }
        return flag;
    }
    
    @Override
    protected void resize() {
        this.setSize(this.size, this.size * 1.4f);
    }
    
    public boolean isHurtLeft() {
        return this.getAnimFlag(1);
    }
    
    public void setHurtLeft(final boolean attacking) {
        this.setAnimFlag(1, attacking);
    }
    
    public boolean isHurtRight() {
        return this.getAnimFlag(2);
    }
    
    public void setHurtRight(final boolean attacking) {
        this.setAnimFlag(2, attacking);
    }
    
    protected String getLivingSound() {
        return "chocolatequest:bull_speak";
    }
    
    protected String getHurtSound() {
        return "chocolatequest:bull_hurt";
    }
    
    protected String getDeathSound() {
        return "chocolatequest:bull_death";
    }
    
    @Override
    protected int getDropMaterial() {
        return 2;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        int ammount = 2 + (int)(this.getMonsterDificulty() * this.getMonsterDificulty()) / 2;
        if (this.isHurtRight()) {
            ammount += (int)(1.0f + this.getMonsterDificulty() / 2.0f);
        }
        if (this.isHurtLeft()) {
            ammount += (int)(1.0f + this.getMonsterDificulty() / 2.0f);
        }
        this.entityDropItem(new ItemStack(ChocolateQuest.material, ammount, this.getDropMaterial()), 0.2f);
    }
}
