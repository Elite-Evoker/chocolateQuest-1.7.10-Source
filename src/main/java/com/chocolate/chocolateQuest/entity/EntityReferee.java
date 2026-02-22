package com.chocolate.chocolateQuest.entity;

import java.util.Iterator;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import java.util.List;
import net.minecraft.entity.EntityCreature;

public class EntityReferee extends EntityCreature
{
    List<EntityLivingBase> trackingPlayers;
    List<EntityLivingBase> trackingMobs;
    int lifeTime;
    
    public EntityReferee(final World par1World) {
        super(par1World);
        this.lifeTime = 0;
        this.experienceValue = 0;
        this.initTasks();
        for (int i = 0; i < this.equipmentDropChances.length; ++i) {
            this.equipmentDropChances[i] = 0.0f;
        }
        this.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.egg, 1, 499));
    }
    
    public EntityReferee(final World world, final List<EntityLivingBase> trackingPlayers, final List<EntityLivingBase> trackingMobs) {
        this(world);
        this.trackingMobs = trackingMobs;
        this.trackingPlayers = trackingPlayers;
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.33);
    }
    
    protected void initTasks() {
    }
    
    public void onLivingUpdate() {
        boolean lost = false;
        boolean win = false;
        if (this.trackingPlayers != null) {
            final Iterator<EntityLivingBase> iter = this.trackingPlayers.listIterator();
            while (iter.hasNext()) {
                final Entity e = (Entity)iter.next();
                if (e.isDead) {
                    iter.remove();
                    final EntityPlayer player = (EntityPlayer)e;
                    player.addChatMessage((IChatComponent)new ChatComponentText("Game Over. Try again!"));
                }
            }
            if (this.trackingPlayers.isEmpty()) {
                lost = true;
            }
        }
        if (this.trackingMobs != null) {
            final Iterator<EntityLivingBase> iter = this.trackingMobs.listIterator();
            while (iter.hasNext()) {
                final Entity e = (Entity)iter.next();
                if (e.isDead) {
                    iter.remove();
                }
            }
            if (this.trackingMobs.isEmpty()) {
                win = true;
            }
        }
        if (!this.worldObj.isRemote && this.trackingMobs == null && this.trackingPlayers == null) {
            this.setDead();
        }
        if (lost) {
            this.setDead();
            for (Entity e : this.trackingPlayers) {}
            for (final Entity e : this.trackingMobs) {
                e.setDead();
            }
        }
        if (win) {
            this.setDead();
            for (final Entity e : this.trackingPlayers) {
                final EntityPlayer player = (EntityPlayer)e;
                final int MINUTE = 60;
                final float TICK_PER_SEC = 20.0f;
                final float timef = this.ticksExisted / 20.0f;
                final String time = (int)(timef / 60.0f) + "m " + (int)(timef % 60.0f) + "s ";
                player.addChatMessage((IChatComponent)new ChatComponentText("Winner!!! " + time));
            }
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
    
    protected void attackEntity(final Entity par1Entity, final float par2) {
        if (par1Entity instanceof EntityPlayer) {
            super.attackEntity(par1Entity, par2);
        }
    }
    
    public boolean isAIEnabled() {
        return true;
    }
}
