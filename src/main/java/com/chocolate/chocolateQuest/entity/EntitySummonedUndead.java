package com.chocolate.chocolateQuest.entity;

import net.minecraft.entity.EnumCreatureAttribute;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.ai.AITargetOwner;
import com.chocolate.chocolateQuest.entity.ai.AIFollowOwner;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.EntityCreature;

public class EntitySummonedUndead extends EntityCreature implements IEntityOwnable, IAnimals
{
    EntityLivingBase summoner;
    int lifeTime;
    protected boolean isReanimatedCreature;
    
    public EntitySummonedUndead(final World par1World) {
        super(par1World);
        this.lifeTime = 0;
        this.isReanimatedCreature = true;
        this.experienceValue = 0;
        this.initTasks();
        for (int i = 0; i < this.equipmentDropChances.length; ++i) {
            this.equipmentDropChances[i] = 0.0f;
        }
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23);
    }
    
    public EntitySummonedUndead(final World world, final EntityLivingBase summoner, final int lvl) {
        this(world);
        this.summoner = summoner;
        this.setlvl((byte)lvl);
        if (!(summoner instanceof EntityPlayer)) {
            this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true));
        }
    }
    
    protected void initTasks() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackOnCollide((EntityCreature)this, (Class)EntityLivingBase.class, 1.0, true));
        this.tasks.addTask(2, (EntityAIBase)new AIFollowOwner((EntityLiving)this, 1.0f, 5.0f, 50.0f, true));
        this.targetTasks.addTask(1, (EntityAIBase)new AITargetOwner(this));
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (Object)0);
    }
    
    public int getlvl() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }
    
    public void setlvl(final byte lvl) {
        this.dataWatcher.updateObject(16, (Object)lvl);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public void onLivingUpdate() {
        if (this.isReanimatedCreature) {
            if (this.summoner != null && this.summoner.isDead) {
                this.setDead();
            }
            if (this.lifeTime > 1200) {
                this.setDead();
            }
        }
        ++this.lifeTime;
        super.onLivingUpdate();
    }
    
    protected String getLivingSound() {
        return "mob.skeleton";
    }
    
    protected String getHurtSound() {
        return "mob.skeleton.hurt";
    }
    
    protected String getDeathSound() {
        return "mob.skeleton.death";
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        if (entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue()) && !this.worldObj.isRemote) {
            this.swingItem();
            if (this.summoner != null && this.isReanimatedCreature) {
                final EntityBaseBall ball = new EntityBaseBall(this.worldObj, this.summoner, 9);
                ball.setlvl(this.getlvl());
                ball.setPosition(this.posX, this.posY + this.height, this.posZ);
                this.worldObj.spawnEntityInWorld((Entity)ball);
            }
            return true;
        }
        return false;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final int i) {
        if (damagesource.getEntity() == this.summoner || !super.attackEntityFrom(damagesource, (float)i)) {
            return false;
        }
        final Entity entity = damagesource.getEntity();
        if (this.riddenByEntity == entity || this.ridingEntity == entity) {
            return true;
        }
        if (entity instanceof EntityPlayer) {
            this.entityToAttack = entity;
        }
        if (this.getHealth() <= 0.0f) {
            this.setDead();
        }
        return true;
    }
    
    public void setDead() {
        if (this.worldObj.isRemote && this.isReanimatedCreature) {
            for (int r = 0; r < 30; ++r) {
                this.worldObj.spawnParticle("smoke", this.posX + this.rand.nextFloat() - 0.5, this.posY + this.rand.nextFloat() * 2.0f, this.posZ + this.rand.nextFloat() - 0.5, 0.0, 0.0, 0.0);
            }
        }
        super.setDead();
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public EntityLivingBase getOwner() {
        return this.summoner;
    }
    
    public boolean isEntityEqual(final Entity par1Entity) {
        return this.getOwner() == par1Entity || super.isEntityEqual(par1Entity);
    }
    
    public String func_152113_b() {
        return null;
    }
}
