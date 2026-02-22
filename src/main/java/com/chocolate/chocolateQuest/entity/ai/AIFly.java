package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.world.World;
import net.minecraft.entity.SharedMonsterAttributes;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import net.minecraft.entity.ai.EntityAIBase;

public class AIFly extends EntityAIBase
{
    private EntityWyvern owner;
    protected EntityLivingBase entityTarget;
    int timeFollowedByEntity;
    int randomCounter;
    int fireBreathTime;
    int fireballTime;
    int chargeTime;
    
    public AIFly(final EntityWyvern par1EntityLiving) {
        this.timeFollowedByEntity = 0;
        this.randomCounter = 0;
        this.fireBreathTime = 0;
        this.fireballTime = 0;
        this.owner = par1EntityLiving;
        this.setMutexBits(4);
        par1EntityLiving.getNavigator().setCanSwim(true);
    }
    
    public boolean shouldExecute() {
        final EntityLivingBase var1 = this.owner.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        if (!var1.isEntityAlive()) {
            this.owner.setAttackTarget((EntityLivingBase)null);
            return false;
        }
        if (var1 == this.owner.riddenByEntity) {
            this.owner.setAttackTarget((EntityLivingBase)null);
            return false;
        }
        this.entityTarget = var1;
        return true;
    }
    
    public boolean continueExecuting() {
        final EntityLivingBase var1 = this.owner.getAttackTarget();
        return var1 != null && var1 == this.entityTarget && this.entityTarget.isEntityAlive() && this.owner.isWithinHomeDistance(MathHelper.floor_double(this.entityTarget.posX), MathHelper.floor_double(this.entityTarget.posY), MathHelper.floor_double(this.entityTarget.posZ));
    }
    
    public int getChargeTime() {
        return this.chargeTime;
    }
    
    public void updateTask() {
        this.owner.getLookHelper().setLookPositionWithEntity((Entity)this.entityTarget, 30.0f, 30.0f);
        final double dist = this.owner.getDistanceToEntity((Entity)this.entityTarget);
        final boolean canSee = this.owner.getEntitySenses().canSee((Entity)this.entityTarget);
        final double angle = Math.abs(BDHelper.getAngleBetweenEntities((Entity)this.owner, (Entity)this.entityTarget));
        final double rotDif = Math.abs(angle - Math.abs(MathHelper.wrapAngleTo180_double((double)this.owner.rotationYaw)));
        if (!this.owner.onGround) {
            this.owner.getNavigator().clearPathEntity();
            if (rotDif < 15.0 + dist) {
                this.chargeTime = Math.min(this.chargeTime + 4, 120);
            }
            else {
                this.chargeTime = Math.max(this.chargeTime - 1, 0);
            }
            final int maxHeight = 8;
            if (this.entityTarget.posY + this.entityTarget.height - 1.0 + Math.min(8.0, dist / 3.0) - this.owner.posY > 0.0 || (this.owner.isCollidedHorizontally && !this.owner.onGround)) {
                final int blockX = MathHelper.floor_double(this.owner.posX + this.owner.motionX * 10.0);
                final int blockY = MathHelper.floor_double(this.owner.posY);
                final int blockZ = MathHelper.floor_double(this.owner.posZ + this.owner.motionZ * 10.0);
                if ((this.owner.worldObj.canBlockSeeTheSky(blockX, blockY, blockZ) || this.owner.worldObj.isAirBlock(blockX, blockY + 4, blockZ)) && this.owner.posY < 250.0) {
                    if (this.owner.rotationPitch > -0.3f) {
                        final EntityWyvern owner = this.owner;
                        owner.rotationPitch -= 0.001f;
                    }
                    this.owner.motionY = 0.3;
                }
                else {
                    if (this.owner.rotationPitch < 0.3f) {
                        final EntityWyvern owner2 = this.owner;
                        owner2.rotationPitch += 0.001f;
                    }
                    this.owner.motionY = -0.3;
                }
            }
            else {
                this.owner.rotationPitch = 0.0f;
                this.owner.motionY = -0.3;
            }
            final double ry = Math.toRadians(this.owner.rotationYaw - 180.0f);
            final int currentSpeed = Math.max(this.chargeTime / 2, 10);
            final float moveSpeed = (float)this.owner.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            this.owner.motionX = Math.sin(ry) * currentSpeed / 30.0 * moveSpeed;
            this.owner.motionZ = -Math.cos(ry) * currentSpeed / 30.0 * moveSpeed;
            final float maxRot = (float)(180.0 - rotDif) / 30.0f;
            if (this.owner.rotationYawHead + maxRot > this.owner.rotationYaw) {
                final EntityWyvern owner3 = this.owner;
                owner3.rotationYaw += maxRot;
            }
            else if (this.owner.rotationYawHead - maxRot < this.owner.rotationYaw) {
                final EntityWyvern owner4 = this.owner;
                owner4.rotationYaw -= maxRot;
            }
            if (dist > 20.0 && this.owner.getRNG().nextInt(50) == 0) {
                this.shootFireballAtTarget();
            }
        }
        else {
            this.owner.rotationPitch = 0.0f;
            if (dist > 4.9 && this.owner.getNavigator().noPath()) {
                this.owner.getNavigator().tryMoveToEntityLiving((Entity)this.entityTarget, 1.0);
            }
        }
        if (rotDif > 30.0) {
            ++this.timeFollowedByEntity;
            if (this.timeFollowedByEntity > 120) {
                if (dist < 8.0) {
                    this.fireBreath();
                }
                else if (this.owner.getRNG().nextInt(50) == 0) {
                    this.shootFireballAtTarget();
                }
            }
        }
        else {
            this.timeFollowedByEntity = 0;
        }
        if (dist < 8.0 && rotDif < 20.0) {
            ++this.randomCounter;
            if (this.randomCounter > 20) {
                this.fireBreath();
            }
        }
        else {
            this.randomCounter = 0;
        }
        if (dist < 4.9 && canSee) {
            if (this.owner.attackTime <= 0) {
                this.owner.attackEntityAsMob((Entity)this.entityTarget);
                this.owner.swingItem();
                this.owner.attackTime = 40;
            }
            else if (this.owner.attackTime > 0) {
                final EntityWyvern owner5 = this.owner;
                --owner5.attackTime;
            }
        }
    }
    
    public void fireBreath() {
        this.owner.openMouth();
        final World world = this.owner.worldObj;
        final double x = this.entityTarget.posX - this.owner.posX;
        final double y = this.entityTarget.boundingBox.minY + this.entityTarget.height / 2.0f - (this.owner.posY + this.owner.height / 2.0f);
        final double z = this.entityTarget.posZ - this.owner.posZ;
        this.owner.fireBreath(x, y, z);
        world.playSoundEffect((double)(int)this.owner.posX, (double)(int)this.owner.posY, (double)(int)this.owner.posZ, "fire.fire", 4.0f, (1.0f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2f) * 0.7f);
    }
    
    public void shootFireballAtTarget() {
        this.owner.openMouth();
        final World world = this.owner.worldObj;
        final double x = this.entityTarget.posX - this.owner.posX;
        final double y = this.entityTarget.boundingBox.minY + this.entityTarget.height / 2.0f - (this.owner.posY + 2.0);
        final double z = this.entityTarget.posZ - this.owner.posZ;
        this.owner.shootFireball(x, y, z);
        world.playSoundEffect((double)(int)this.owner.posX, (double)(int)this.owner.posY, (double)(int)this.owner.posZ, "mob.ghast.fireball", 4.0f, (1.0f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2f) * 0.7f);
    }
}
