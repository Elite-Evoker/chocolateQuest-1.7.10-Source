package com.chocolate.chocolateQuest.entity.ai;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class AIHumanAttackHeal extends AIHumanAttackAggressive
{
    private int pathFindingCooldown;
    
    public AIHumanAttackHeal(final EntityHumanBase par1EntityLiving, final Class par2Class, final float par3, final boolean par4, final World world) {
        this(par1EntityLiving, par3, par4);
        this.classTarget = par2Class;
        this.worldObj = world;
    }
    
    public AIHumanAttackHeal(final EntityHumanBase par1EntityLiving, final float par2, final boolean par3) {
        super(par1EntityLiving, par2, par3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase var1 = this.owner.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        if (!this.owner.isSuitableTargetAlly(var1)) {
            return false;
        }
        if (var1.getHealth() >= var1.getMaxHealth() || var1 == this.owner) {
            this.owner.setAttackTarget(null);
            return false;
        }
        this.entityTarget = var1;
        this.entityPathEntity = this.owner.getNavigator().getPathToEntityLiving((Entity)this.entityTarget);
        return this.entityPathEntity != null || this.owner.ridingEntity != null;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase target = this.owner.getAttackTarget();
        return target != null && this.owner.isSuitableTargetAlly(target) && target != null && this.entityTarget.isEntityAlive() && (this.requireSight ? this.owner.isWithinHomeDistance(MathHelper.floor_double(this.entityTarget.posX), MathHelper.floor_double(this.entityTarget.posY), MathHelper.floor_double(this.entityTarget.posZ)) : (!this.owner.getNavigator().noPath()));
    }
    
    @Override
    public void startExecuting() {
        this.owner.getNavigator().setPath(this.entityPathEntity, (double)this.moveSpeed);
        this.pathFindingCooldown = 0;
    }
    
    @Override
    public void resetTask() {
        this.entityTarget = null;
        this.owner.getNavigator().clearPathEntity();
        this.owner.setDefending(false);
    }
    
    @Override
    public void updateTask() {
        this.owner.getLookHelper().setLookPositionWithEntity((Entity)this.entityTarget, 30.0f, 30.0f);
        if ((this.requireSight || this.owner.getEntitySenses().canSee((Entity)this.entityTarget)) && --this.pathFindingCooldown <= 0) {
            this.pathFindingCooldown = 4 + this.owner.getRNG().nextInt(7);
            if (this.owner.ridingEntity instanceof EntityLiving) {
                ((EntityLiving)this.owner.ridingEntity).getNavigator().tryMoveToEntityLiving((Entity)this.entityTarget, 1.2000000476837158);
            }
            this.owner.getNavigator().tryMoveToEntityLiving((Entity)this.entityTarget, (double)this.moveSpeed);
        }
        this.owner.attackTime = Math.max(this.owner.attackTime - 1, 0);
        final double bounds = this.getMinDistanceToInteract();
        final double dist = this.owner.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ);
        if (this.owner.isDefending() && dist <= bounds * 2.0) {
            this.owner.setDefending(false);
        }
        if (dist <= bounds && this.owner.attackTime <= 0) {
            this.owner.attackTime = this.owner.getAttackSpeed();
            if (this.owner.getHeldItem() != null && this.owner.getHeldItem().getItem() == ChocolateQuest.staffHeal) {
                this.owner.swingItem();
            }
            else if (this.owner.leftHandItem != null && this.owner.leftHandItem.getItem() == ChocolateQuest.staffHeal) {
                this.owner.swingLeftHand();
            }
            this.entityTarget.heal(2.0f);
            if (!this.owner.worldObj.isRemote) {
                this.entityTarget.worldObj.playSoundEffect((double)(int)this.entityTarget.posX, (double)(int)this.entityTarget.posY, (double)(int)this.entityTarget.posZ, "chocolateQuest:magic", 1.0f, (1.0f + (this.entityTarget.worldObj.rand.nextFloat() - this.entityTarget.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
                final PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround((byte)0, this.entityTarget.posX, this.entityTarget.posY + 1.6, this.entityTarget.posZ);
                ChocolateQuest.channel.sendToAllAround((Entity)this.entityTarget, (IMessage)packet, 64);
            }
        }
        if (this.entityTarget.getHealth() >= this.entityTarget.getMaxHealth()) {
            this.owner.setAttackTarget(null);
        }
    }
}
