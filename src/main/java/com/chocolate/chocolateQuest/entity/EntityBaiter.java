package com.chocolate.chocolateQuest.entity;

import net.minecraft.util.DamageSource;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.ai.AITargetOwner;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import cpw.mods.fml.common.registry.IThrowableEntity;
import net.minecraft.entity.EntityCreature;

public class EntityBaiter extends EntityCreature implements IThrowableEntity, IEntityOwnable
{
    int lifeTime;
    EntityLivingBase summoner;
    boolean firstTick;
    
    public EntityBaiter(final World par1World) {
        super(par1World);
        this.lifeTime = 0;
        this.firstTick = true;
        this.experienceValue = 0;
        this.initTasks();
        for (int i = 0; i < this.equipmentDropChances.length; ++i) {
            this.equipmentDropChances[i] = 0.0f;
        }
    }
    
    public EntityBaiter(final World world, final EntityLivingBase summoner) {
        this(world);
        this.summoner = summoner;
        for (int i = 0; i < 5; ++i) {
            this.setCurrentItemOrArmor(i, summoner.getEquipmentInSlot(i));
        }
        this.posX = summoner.posX;
        this.posY = summoner.posY;
        this.posZ = summoner.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
        if (summoner.getLastAttacker() != null) {
            this.setAttackTarget(summoner.getLastAttacker());
        }
        if (summoner instanceof EntityLiving) {
            this.setAttackTarget(((EntityLiving)summoner).getAttackTarget());
        }
        this.setHealth(summoner.getHealth());
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((this.summoner == null) ? 20.0 : ((double)this.summoner.getMaxHealth()));
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23);
    }
    
    protected void initTasks() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackOnCollide((EntityCreature)this, (Class)EntityLiving.class, 1.0, true));
        this.targetTasks.addTask(1, (EntityAIBase)new AITargetOwner(this));
    }
    
    public void onLivingUpdate() {
        if (this.getThrower() != null) {
            if (this.firstTick) {
                final double dist = 40.0;
                final AxisAlignedBB var3 = this.boundingBox.expand(dist, 0.0, dist);
                final List<Entity> list = this.worldObj.getEntitiesWithinAABB((Class)EntityLiving.class, var3);
                for (final Entity e : list) {
                    if (e instanceof EntityLiving) {
                        final EntityLiving el = (EntityLiving)e;
                        if (el.getAttackTarget() != this.getThrower() && el.getAITarget() != this.getThrower()) {
                            continue;
                        }
                        el.setAttackTarget((EntityLivingBase)this);
                        el.setRevengeTarget((EntityLivingBase)this);
                    }
                }
                this.firstTick = false;
            }
            if (this.getThrower().isDead) {
                this.setDead();
            }
        }
        else {
            this.setDead();
        }
        if (this.lifeTime > 600) {
            this.setDead();
        }
        super.onLivingUpdate();
    }
    
    public void setDead() {
        if (this.worldObj.isRemote) {
            for (int r = 0; r < 30; ++r) {
                this.worldObj.spawnParticle("smoke", this.posX + this.rand.nextFloat() - 0.5, this.posY + this.rand.nextFloat() * 2.0f, this.posZ + this.rand.nextFloat() - 0.5, 0.0, 0.0, 0.0);
            }
        }
        super.setDead();
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        this.lifeTime += 10;
        return false;
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public String getCommandSenderName() {
        if (this.getThrower() != null) {
            return this.getThrower().getCommandSenderName();
        }
        return "Bait";
    }
    
    public Entity getThrower() {
        return (Entity)this.summoner;
    }
    
    public void setThrower(final Entity entity) {
        this.summoner = (EntityLivingBase)entity;
    }
    
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        this.lifeTime += 100;
        super.attackEntityFrom(par1DamageSource, par2);
        return false;
    }
    
    public EntityLivingBase getOwner() {
        return this.summoner;
    }
    
    public String func_152113_b() {
        return this.summoner.getCommandSenderName();
    }
}
