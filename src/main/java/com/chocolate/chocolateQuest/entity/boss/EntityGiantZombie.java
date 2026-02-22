package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.misc.EnumVoice;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.scoreboard.Team;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.monster.EntityGhast;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
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
import com.chocolate.chocolateQuest.utils.MobTeam;

public class EntityGiantZombie extends EntityBaseBoss
{
    public AttackKick kickHelper;
    public EntityPart head;
    MobTeam team;
    
    public EntityGiantZombie(final World world) {
        super(world);
        this.team = new MobTeam("mob_undead");
        this.resize();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23 + this.size / 70.0f);
        this.kickHelper = new AttackKick(this);
        this.xpRatio = 2.0f;
        this.projectileDefense = 40;
        this.blastDefense = 10;
        this.magicDefense = 10;
        this.fireDefense = 10;
    }
    
    @Override
    protected void scaleAttributes() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23 + this.lvl * 0.02);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0 + this.lvl * 0.4);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0 + this.lvl * 200.0);
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
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public void initBody() {
        (this.head = new EntityPartRidable(this.worldObj, this, 0, 0.0f, this.size / 20.0f, this.size * 1.2f)).setSize(this.size / 3.0f, this.size / 3.0f);
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
    
    public void onUpdate() {
        if (!this.isDead) {
            this.kickHelper.onUpdate();
        }
        super.onUpdate();
    }
    
    @Override
    public void animationBoss(final byte animType) {
        if (!this.worldObj.isRemote) {
            final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), animType);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet, 64);
        }
        this.kickHelper.kick(animType);
    }
    
    @Override
    public void attackEntity(final Entity target, final float dist) {
        if (this.ticksExisted % 10 == 0 && !this.worldObj.isRemote && dist < (this.width + 3.0f) * (this.width + 3.0f)) {
            this.kickHelper.attackTarget(target);
        }
    }
    
    @Override
    protected void resize() {
        this.setSize(this.size / 3.0f, this.size * 1.2f);
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
        return EntityGhast.class != par1Class;
    }
    
    protected void fall(final float par1) {
    }
    
    public boolean isEntityEqual(final Entity par1Entity) {
        return this == par1Entity || par1Entity == this.head;
    }
    
    @Override
    public boolean attackInProgress() {
        return this.kickHelper.isAttackInProgress();
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        final int ammount = 4 + (int)(this.getMonsterDificulty() * this.getMonsterDificulty());
        this.entityDropItem(new ItemStack(Items.emerald, ammount + this.getRNG().nextInt(5), 0), this.size);
        this.entityDropItem(new ItemStack(Items.rotten_flesh, ammount / 2 + this.getRNG().nextInt(5), 0), this.size);
    }
    
    public Team getTeam() {
        return this.team;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    protected String getLivingSound() {
        return EnumVoice.ZOMBIE.say;
    }
    
    protected String getHurtSound() {
        return EnumVoice.ZOMBIE.hurt;
    }
    
    protected String getDeathSound() {
        return EnumVoice.ZOMBIE.death;
    }
}
