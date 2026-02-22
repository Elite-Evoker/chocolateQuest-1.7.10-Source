package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.entity.EntityReferee;
import net.minecraft.entity.monster.EntityGhast;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.util.DamageSource;
import com.chocolate.chocolateQuest.packets.PacketAttackToXYZ;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
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

public class EntityGiantBoxer extends EntityBaseBoss
{
    public AttackPunch rightHand;
    public AttackPunch leftHand;
    public AttackKick kickHelper;
    static final byte airSmash = 10;
    public boolean airSmashInProgress;
    public boolean airSmashFalling;
    public EntityPart head;
    public int attackSpeed;
    
    public EntityGiantBoxer(final World world) {
        super(world);
        this.airSmashInProgress = false;
        this.airSmashFalling = false;
        this.attackSpeed = 15;
        this.resize();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23 + this.size / 70.0f);
        this.kickHelper = new AttackKick(this);
        (this.leftHand = new AttackPunch(this, (byte)5)).setAngle(-15, -2, 0.4f);
        (this.rightHand = new AttackPunch(this, (byte)6)).setAngle(15, -2, 0.4f);
        this.xpRatio = 2.0f;
        this.projectileDefense = 40;
        this.blastDefense = 10;
        this.magicDefense = 10;
        this.fireDefense = -30;
    }
    
    @Override
    protected void scaleAttributes() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23 + this.lvl * 0.02);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0 + this.lvl * 0.5);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0 + this.lvl * 360.0);
        this.attackSpeed = (int)(6.0 + this.lvl * 1.8);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
    }
    
    @Override
    public void addAITasks() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new AIBossAttack(this, 1.0f, false));
        this.targetTasks.addTask(1, (EntityAIBase)new AITargetHurtBy(this, true));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityMob.class, 0, true));
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public void initBody() {
        (this.head = new EntityPartRidable(this.worldObj, this, 0, 0.0f, this.size / 6.0f, this.size)).setSize(this.size / 3.0f, this.size / 3.0f);
        if (!this.worldObj.isRemote) {
            this.worldObj.spawnEntityInWorld((Entity)this.head);
        }
        super.initBody();
    }
    
    @Override
    public void setPart(final EntityPart entityPart, final int partID) {
        super.setPart(entityPart, partID);
        entityPart.setSize(this.size / 3.0f, this.size / 3.0f);
    }
    
    public void onUpdate() {
        if (!this.isDead) {
            this.leftHand.onUpdate();
            this.rightHand.onUpdate();
            this.kickHelper.onUpdate();
            if (this.airSmashInProgress && this.fallDistance > 0.0f) {
                this.airSmashFalling = true;
            }
            if (this.airSmashInProgress && this.onGround && this.airSmashFalling) {
                this.airSmashInProgress = false;
                this.airSmashFalling = false;
                if (this.worldObj.isRemote) {
                    this.worldObj.spawnParticle("hugeexplosion", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
                }
                else {
                    final List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand((double)(1.0f + this.size / 10.0f), 1.0, (double)(1.0f + this.size / 10.0f)));
                    for (final Entity e : list) {
                        if (e instanceof EntityLivingBase) {
                            this.attackEntityAsMob(e);
                        }
                    }
                }
            }
        }
        if (this.worldObj.isRemote) {
            if (this.getHealth() < this.getMaxHealth() / 3.0f && this.ticksExisted % 10 < 2) {
                String part = "largesmoke";
                if (this.size < 5.0f) {
                    part = "smoke";
                }
                final float rot = this.rotationYawHead * 3.1416f / 180.0f;
                final double scale = this.width * 1.4;
                final float desp = this.width * 0.05f;
                double mx = MathHelper.sin(rot + 0.2f);
                double mz = MathHelper.cos(rot + 0.2f);
                double posX = this.posX - mx * scale;
                double posY = this.posY + this.height * 1.05;
                double posZ = this.posZ + mz * scale;
                this.worldObj.spawnParticle(part, posX + this.rand.nextGaussian() * desp, posY, posZ + this.rand.nextGaussian() * desp, -mx / 20.0, -(0.3 + this.rand.nextFloat() * this.size) / 25.0, mz / 20.0);
                mx = MathHelper.sin(rot - 0.2f);
                mz = MathHelper.cos(rot - 0.2f);
                posX = this.posX - mx * scale;
                posY = this.posY + this.height * 1.05;
                posZ = this.posZ + mz * scale;
                this.worldObj.spawnParticle(part, posX + this.rand.nextGaussian() * desp, posY, posZ + this.rand.nextGaussian() * desp, -mx / 20.0, -(0.3 + this.rand.nextFloat() * this.size) / 25.0, mz / 20.0);
            }
            if (this.ticksExisted % 200 < 10) {
                final float rot2 = this.rotationYaw * 3.1416f / 180.0f;
                final double scale2 = this.width / 1.6;
                final double mx2 = MathHelper.sin(rot2) * scale2;
                final double mz2 = MathHelper.cos(rot2) * scale2;
                final double posX2 = this.posX + mx2;
                final double posY2 = this.posY + this.height * 0.5;
                final double posZ2 = this.posZ - mz2;
                final float desp2 = this.width * 0.05f;
                this.worldObj.spawnParticle("cloud", posX2 + this.rand.nextGaussian() * desp2, posY2, posZ2 + this.rand.nextGaussian() * desp2, mx2 / 5.0, -0.3 + this.rand.nextFloat() / 4.0f, -mz2 / 5.0);
            }
        }
        super.onUpdate();
    }
    
    @Override
    public void animationBoss(final byte animType) {
        if (!this.worldObj.isRemote) {
            final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), animType);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet, 64);
        }
        switch (animType) {
            case 10: {
                this.airSmashInProgress = true;
                return;
            }
            default: {
                this.kickHelper.kick(animType);
            }
        }
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
    public boolean attackFromPart(final DamageSource par1DamageSource, float par2, final EntityPart part) {
        if (part == this.head) {
            if (par1DamageSource.isProjectile()) {
                par2 *= 2.0f;
            }
            else {
                par2 *= this.lvl;
            }
        }
        return super.attackFromPart(par1DamageSource, par2, part);
    }
    
    @Override
    public void attackEntity(final Entity target, final float dist) {
        final int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities((Entity)this, target) - this.rotationYaw);
        if (this.ticksExisted % this.attackSpeed == 0 && !this.worldObj.isRemote) {
            final double posY = this.posY + this.size;
            final double targetY = target.posY + target.height;
            final double dx = this.posX - target.posX;
            final double dy = this.posY + this.size - targetY;
            final double dz = this.posZ - target.posZ;
            final double distToHead = dx * dx + dy * dy + dz * dz - this.width * this.width;
            if (distToHead < (this.getArmLength() + 1.0) * (this.getArmLength() + 1.0)) {
                boolean handFlag = this.rand.nextBoolean();
                final boolean ARM_LEFT = false;
                final boolean ARM_RIGHT = true;
                if (!handFlag) {
                    if (this.leftHand.attackInProgress()) {
                        handFlag = true;
                    }
                }
                else if (this.rightHand.attackInProgress()) {
                    handFlag = false;
                }
                if (angle > -110 && angle < 110) {
                    if (!handFlag && !this.leftHand.attackInProgress()) {
                        this.leftHand.attackTarget(target);
                    }
                    else if (!this.rightHand.attackInProgress()) {
                        this.rightHand.attackTarget(target);
                    }
                }
            }
        }
        if (this.ticksExisted % 10 == 0 && !this.worldObj.isRemote && dist < (this.width + 3.0f) * (this.width + 3.0f)) {
            this.kickHelper.attackTarget(target);
        }
        if (this.ticksExisted % 10 == 0 && this.onGround && this.rand.nextInt(10) == 0 && ((dist > (this.width + 3.0f) * (this.width + 3.0f) && angle > -60 && angle < 60) || angle < -110 || angle > 110)) {
            this.motionY = 1.5 + this.lvl / 10.0f;
            this.motionX = (target.posX - this.posX) / 5.0;
            this.motionZ = (target.posZ - this.posZ) / 5.0;
            this.animationBoss((byte)10);
        }
    }
    
    public double getArmLength() {
        return this.size;
    }
    
    @Override
    protected void resize() {
        this.setSize(this.size / 3.0f, this.size);
    }
    
    @Override
    public float getMinSize() {
        return 1.1f;
    }
    
    @Override
    public float getSizeVariation() {
        return 1.4f;
    }
    
    @Override
    public boolean canAttackClass(final Class par1Class) {
        return EntityGhast.class != par1Class && par1Class != EntityReferee.class;
    }
    
    protected void fall(final float par1) {
    }
    
    public boolean isEntityEqual(final Entity par1Entity) {
        return this == par1Entity || par1Entity == this.head;
    }
    
    @Override
    public boolean attackInProgress() {
        return this.kickHelper.isAttackInProgress() || this.leftHand.isAttacking || this.rightHand.isAttacking;
    }
    
    protected String getLivingSound() {
        return "chocolatequest:monking_speak";
    }
    
    protected String getHurtSound() {
        return "chocolatequest:monking_hurt";
    }
    
    protected String getDeathSound() {
        return "chocolatequest:monking_death";
    }
    
    @Override
    protected int getDropMaterial() {
        return 5;
    }
}
