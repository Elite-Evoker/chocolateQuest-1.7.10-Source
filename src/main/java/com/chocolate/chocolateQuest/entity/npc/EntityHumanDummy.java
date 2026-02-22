package com.chocolate.chocolateQuest.entity.npc;

import net.minecraft.util.DamageSource;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDummyData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.util.Iterator;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.init.Items;
import com.chocolate.chocolateQuest.entity.ai.AIControlledSit;
import com.chocolate.chocolateQuest.entity.ai.AIControlledWardPosition;
import com.chocolate.chocolateQuest.entity.ai.AIControlledPath;
import com.chocolate.chocolateQuest.entity.ai.AIControlledFormation;
import com.chocolate.chocolateQuest.entity.ai.AIControlledFollowOwner;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import com.chocolate.chocolateQuest.entity.ai.HumanSelector;
import com.chocolate.chocolateQuest.entity.ai.AIHumanMount;
import com.chocolate.chocolateQuest.entity.ai.AIHumanGoToPoint;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class EntityHumanDummy extends EntityHumanBase
{
    public EntityHumanDummy(final World world) {
        super(world);
        this.shouldDespawn = false;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
    }
    
    @Override
    public boolean isSuitableMount(final Entity entity) {
        if (entity instanceof EntityHumanBase) {
            return entity instanceof EntityGolemMecha;
        }
        return !(entity instanceof EntityPlayer);
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public boolean isAiming() {
        return this.getAnimFlag(5);
    }
    
    public void setAiming(final boolean aiming) {
        this.setAnimFlag(5, aiming);
    }
    
    @Override
    public boolean isEating() {
        return false;
    }
    
    @Override
    public void setEating(final boolean flag) {
    }
    
    @Override
    public boolean canSee(final EntityLivingBase entity) {
        if (super.canSee(entity)) {
            this.setAiming(true);
        }
        else {
            this.setAiming(false);
        }
        return false;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }
    
    public void addPotionEffect(final PotionEffect potion) {
        super.addPotionEffect(new PotionEffect(potion.getPotionID(), Integer.MAX_VALUE, potion.getAmplifier()));
    }
    
    @Override
    protected void addAITasks() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(2, (EntityAIBase)new AIHumanGoToPoint(this));
        this.tasks.addTask(1, (EntityAIBase)new AIHumanMount(this, 1.0f, false));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true, false, (IEntitySelector)new HumanSelector(this)));
        this.setAIForCurrentMode();
    }
    
    @Override
    public void setAIForCurrentMode() {
        if (this.controlledAI != null) {
            this.tasks.removeTask(this.controlledAI);
        }
        int priority = 4;
        if (this.AIMode == EnumAiState.FOLLOW.ordinal()) {
            this.controlledAI = new AIControlledFollowOwner(this, 8.0f, 50.0f);
        }
        else if (this.AIMode == EnumAiState.FORMATION.ordinal()) {
            this.controlledAI = new AIControlledFormation(this);
        }
        else if (this.AIMode == EnumAiState.PATH.ordinal()) {
            this.controlledAI = new AIControlledPath(this);
        }
        else if (this.AIMode == EnumAiState.WARD.ordinal()) {
            this.controlledAI = new AIControlledWardPosition(this);
        }
        else if (this.AIMode == EnumAiState.SIT.ordinal()) {
            this.controlledAI = new AIControlledSit(this);
            priority = 2;
        }
        if (this.controlledAI != null) {
            this.tasks.addTask(priority, this.controlledAI);
        }
    }
    
    @Override
    protected boolean interact(final EntityPlayer player) {
        final ItemStack is = player.getCurrentEquippedItem();
        if (is != null && !this.worldObj.isRemote && is.getItem() == Items.potionitem) {
            final List<PotionEffect> list = PotionHelper.getPotionEffects(is.getMetadata(), true);
            if (list != null) {
                for (final PotionEffect potion : list) {
                    this.addPotionEffect(potion);
                }
            }
            return true;
        }
        this.addedToParty = true;
        return super.interact(player);
    }
    
    @Override
    public IMessage getEntityGUIUpdatePacket(final EntityPlayer player) {
        return (IMessage)new PacketUpdateHumanDummyData(this);
    }
    
    @Override
    public ItemStack getHeldItem() {
        if (this.rightHand != null) {
            return this.rightHand.getItem();
        }
        return super.getHeldItem();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, float damage) {
        if (damagesource.damageType == DamageSource.inWall.damageType) {
            damage = 0.1f;
        }
        return super.attackEntityFrom(damagesource, damage);
    }
    
    protected void fall(final float par1) {
    }
}
