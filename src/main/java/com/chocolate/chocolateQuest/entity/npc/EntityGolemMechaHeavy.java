package com.chocolate.chocolateQuest.entity.npc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGolemMechaHeavy extends EntityGolemMecha
{
    int gunCD;
    
    public EntityGolemMechaHeavy(final World world) {
        super(world);
        this.gunCD = 0;
    }
    
    public EntityGolemMechaHeavy(final World world, final EntityLivingBase owner) {
        super(world, owner);
        this.gunCD = 0;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.19);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(140.0);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.riddenByEntity != null && this.ticksExisted % 2 == 0) {
            ((EntityLivingBase)this.riddenByEntity).addPotionEffect(new PotionEffect(Potion.absorption.id, 8, 2));
        }
    }
    
    @Override
    public double getMountedYOffset() {
        if (this.riddenByEntity instanceof EntityPlayer) {
            return 1.55;
        }
        return 1.0;
    }
    
    @Override
    protected String getLivingSound() {
        return "none";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.irongolem.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.irongolem.death";
    }
}
