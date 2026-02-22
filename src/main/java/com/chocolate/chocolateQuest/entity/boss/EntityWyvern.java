package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.init.Items;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.ai.AITargetNearestAttackableForDragon;
import net.minecraft.entity.monster.IMob;
import com.chocolate.chocolateQuest.entity.ai.AITargetHurtBy;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import com.chocolate.chocolateQuest.entity.ai.AIFlyRoam;
import net.minecraft.entity.ai.EntityAIBase;
import com.chocolate.chocolateQuest.entity.ai.AIFly;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityWyvern extends EntityBaseBoss
{
    public int openMouthTime;
    public final int totalOpenMouthTime = 10;
    int speedBoost;
    private int flameThrowerCD;
    protected int flameThrowerMaxCD;
    protected final byte OPEN_MOUTH = 1;
    
    public EntityWyvern(final World par1World) {
        super(par1World);
        this.openMouthTime = 0;
        this.speedBoost = 0;
        this.flameThrowerCD = 0;
        this.flameThrowerMaxCD = 10;
        this.addAITasks();
        this.setSize(1.8f, 2.5f);
        this.isImmuneToFire = true;
        this.size = 1.0f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35 + this.size / 8.0f);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0 + this.size * 10.0f);
    }
    
    @Override
    public void addAITasks() {
        this.tasks.addTask(0, (EntityAIBase)new AIFly(this));
        this.tasks.addTask(0, (EntityAIBase)new AIFlyRoam(this, 88));
        this.tasks.addTask(2, (EntityAIBase)new AIBossAttack(this, 1.0f, false));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIAttackOnCollide((EntityCreature)this, (Class)EntityLivingBase.class, 1.0, false));
        this.targetTasks.addTask(1, (EntityAIBase)new AITargetHurtBy(this, false));
        this.targetTasks.addTask(2, (EntityAIBase)new AITargetNearestAttackableForDragon(this, IMob.class, 0, true));
        this.targetTasks.addTask(2, (EntityAIBase)new AITargetNearestAttackableForDragon(this, EntityPlayer.class, 0, true));
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.openMouthTime > 0) {
            --this.openMouthTime;
        }
        if (!this.hasHome()) {
            this.setHomeArea(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 200);
        }
        if (!this.onGround && this.getAttackTarget() != null) {
            this.motionY = 0.0;
        }
        if (this.riddenByEntity instanceof EntityPlayer) {
            if (this.flameThrowerCD > 0) {
                --this.flameThrowerCD;
            }
            if (((EntityPlayer)this.riddenByEntity).isSwingInProgress && this.flameThrowerCD == 0 && !this.worldObj.isRemote) {
                final double ry = Math.toRadians(this.rotationYaw - 180.0f);
                final double x = Math.sin(ry);
                final double z = -Math.cos(ry);
                final double y = -Math.sin(Math.toRadians(this.riddenByEntity.rotationPitch));
                this.shootFireball(x, y, z);
                this.flameThrowerCD = this.flameThrowerMaxCD;
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public boolean canAttackClass(final Class par1Class) {
        return true;
    }
    
    @Override
    protected boolean limitRotation() {
        return true;
    }
    
    protected void updateFallState(final double par1, final boolean par3) {
    }
    
    public void openMouth() {
        if (this.openMouthTime <= 0) {
            this.openMouthTime = 10;
            if (!this.worldObj.isRemote) {
                final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)1);
                ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet, 64);
            }
        }
    }
    
    @Override
    public void animationBoss(final byte animType) {
        switch (animType) {
            case 1: {
                this.openMouth();
                break;
            }
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        for (int a = this.rand.nextInt(5) + 1; a > 0; --a) {
            this.dropItem(Items.diamond, 2);
        }
        this.dropItem(ChocolateQuest.dragonHelmet, 1);
    }
    
    @Override
    public void moveEntityWithHeading(float par1, float par2) {
        if (this.riddenByEntity instanceof EntityPlayer) {
            final float rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationYaw = rotationYaw;
            this.prevRotationYaw = rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5f;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            final float rotationYaw2 = this.rotationYaw;
            this.renderYawOffset = rotationYaw2;
            this.rotationYawHead = rotationYaw2;
            par1 = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5f;
            par2 = ((EntityLivingBase)this.riddenByEntity).moveForward;
            this.stepHeight = 1.0f;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1f;
            this.prevLimbSwingAmount = this.limbSwingAmount;
            final double d0 = this.posX - this.prevPosX;
            final double d2 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 4.0f;
            if (f4 > 1.0f) {
                f4 = 1.0f;
            }
            this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4f;
            this.limbSwing += this.limbSwingAmount;
            final double ry = Math.toRadians(this.rotationYaw - 180.0f);
            final float moveSpeed = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() * par2;
            this.motionX = Math.sin(ry) * moveSpeed;
            this.motionZ = -Math.cos(ry) * moveSpeed;
            this.motionY = -Math.sin(Math.toRadians(this.rotationPitch)) * moveSpeed;
            super.moveEntityWithHeading(par1, par2);
        }
        else {
            super.moveEntityWithHeading(par1, par2);
        }
    }
    
    public boolean interact(final EntityPlayer entityPlayer) {
        if (entityPlayer.getCommandSenderName().equals("ArrgChocolate")) {
            this.setAttackTarget((EntityLivingBase)null);
            entityPlayer.mountEntity((Entity)this);
        }
        return false;
    }
    
    public void fireBreath(final double x, final double y, final double z) {
        this.openMouth();
        final World world = this.worldObj;
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity)new EntityBaseBall(world, (EntityLivingBase)this, x + (this.getRNG().nextFloat() - 0.5f), y + (this.getRNG().nextFloat() - 0.25f), z + (this.getRNG().nextFloat() - 0.5f), 6, 0));
            world.playSoundEffect((double)(int)this.posX, (double)(int)this.posY, (double)(int)this.posZ, "fire.fire", 4.0f, (1.0f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2f) * 0.7f);
        }
    }
    
    public void shootFireball(final double x, final double y, final double z) {
        this.openMouth();
        final World world = this.worldObj;
        for (int i = 0; i < 4; ++i) {
            final EntityBaseBall entitylargefireball = new EntityBaseBall(world, (EntityLivingBase)this, x + (this.getRNG().nextFloat() - 0.5f) / 4.0f, y + (this.getRNG().nextFloat() - 0.5f) / 4.0f, z + (this.getRNG().nextFloat() - 0.5f) / 4.0f, 5, 2);
            world.spawnEntityInWorld((Entity)entitylargefireball);
        }
        world.playSoundEffect((double)(int)this.posX, (double)(int)this.posY, (double)(int)this.posZ, "mob.ghast.fireball", 4.0f, (1.0f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2f) * 0.7f);
    }
    
    @Override
    protected void resize() {
        this.setSize(this.size * 1.8f, this.size * 2.0f);
    }
    
    protected String getLivingSound() {
        return "chocolatequest:dragon_speak";
    }
    
    protected String getHurtSound() {
        return "chocolatequest:dragon_hurt";
    }
    
    protected String getDeathSound() {
        return "chocolatequest:dragon_death";
    }
}
