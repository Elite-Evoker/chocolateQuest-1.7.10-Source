package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class AIHumanPotion extends EntityAIBase
{
    protected World worldObj;
    protected EntityHumanBase owner;
    protected int attackCooldown;
    
    public AIHumanPotion(final EntityHumanBase par1EntityLiving) {
        this.owner = par1EntityLiving;
        this.worldObj = par1EntityLiving.worldObj;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        return this.owner.potionCount > 0 && this.owner.getHealth() <= Math.max(this.owner.getMaxHealth() * 0.1, 6.0);
    }
    
    public void startExecuting() {
        this.owner.getNavigator().clearPathEntity();
        this.attackCooldown = 0;
        this.owner.setDefending(true);
    }
    
    public void resetTask() {
        this.owner.getNavigator().clearPathEntity();
        this.owner.setDefending(false);
        this.owner.moveForwardHuman = 0.0f;
        this.owner.setEating(false);
    }
    
    public void updateTask() {
        final boolean flag = true;
        final int timeTillPotion = 90;
        if (this.owner.getAttackTarget() != null && this.attackCooldown < timeTillPotion) {
            this.owner.getLookHelper().setLookPositionWithEntity((Entity)this.owner.getAttackTarget(), 30.0f, 30.0f);
            this.owner.rotationYaw = this.owner.rotationYawHead;
            final double ry = Math.toRadians(this.owner.rotationYaw - 180.0f);
            final int x = MathHelper.floor_double(this.owner.posX - Math.sin(ry) * 3.0);
            final int z = MathHelper.floor_double(this.owner.posZ + Math.cos(ry) * 3.0);
            Material mat = this.owner.worldObj.getBlock(x, MathHelper.floor_double(this.owner.posY) - 1, z).getMaterial();
            boolean move = false;
            if (mat != Material.air && mat != Material.lava && mat.isSolid()) {
                move = true;
            }
            else {
                mat = this.owner.worldObj.getBlock(x, MathHelper.floor_double(this.owner.posY) - 2, z).getMaterial();
                if (mat.isSolid()) {
                    move = true;
                }
            }
            if (move) {
                this.owner.moveForwardHuman = -0.25f;
            }
            else {
                this.owner.moveForwardHuman = 0.0f;
            }
            if (this.owner.getDistanceSqToEntity((Entity)this.owner.getAttackTarget()) > 100.0 || !this.owner.getEntitySenses().canSee((Entity)this.owner.getAttackTarget())) {
                this.attackCooldown = timeTillPotion;
                this.owner.moveForwardHuman = 0.0f;
            }
            if (this.owner.isCollidedHorizontally) {
                this.attackCooldown += 5;
            }
            final double dist = this.owner.getDistanceSqToEntity((Entity)this.owner.getAttackTarget());
        }
        ++this.attackCooldown;
        if (this.attackCooldown > timeTillPotion) {
            if (this.owner.onGround) {
                this.owner.motionX = 0.0;
                this.owner.motionZ = 0.0;
            }
            if (!this.owner.isEating()) {
                this.owner.setEating(true);
                if (this.owner.isDefending()) {
                    this.owner.toogleBlocking();
                }
            }
            this.owner.swingItem();
            if (this.attackCooldown > timeTillPotion + 50) {
                this.owner.worldObj.playSoundAtEntity((Entity)this.owner, "random.burp", 0.5f, this.owner.worldObj.rand.nextFloat() * 0.1f + 0.9f);
                this.owner.heal(20.0f);
                --this.owner.potionCount;
                this.owner.setEating(false);
            }
            else {
                this.owner.worldObj.playSoundAtEntity((Entity)this.owner, "random.drink", 0.5f, this.owner.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
        }
    }
}
