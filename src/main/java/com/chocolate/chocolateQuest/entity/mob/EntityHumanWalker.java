package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.misc.EnumVoice;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;

public class EntityHumanWalker extends EntityHumanMob
{
    public EntityHumanWalker(final World world) {
        super(world);
    }
    
    @Override
    public DungeonMonstersBase getMonsterType() {
        return ChocolateQuest.walker;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isRemote && this.ticksExisted % 10 == 0) {
            int eyeOff = 25;
            final double eyeDist = 0.38;
            final double yLook = 1.0 - Math.abs(this.getLookVec().yCoord);
            double x = this.posX - Math.sin(Math.toRadians(this.rotationYawHead + eyeOff)) * eyeDist * yLook + this.motionX;
            double z = this.posZ + Math.cos(Math.toRadians(this.rotationYawHead + eyeOff)) * eyeDist * yLook + this.motionZ;
            final double y = this.posY + (this.getEyeHeight() + 0.2) * this.getSizeModifier() + this.getLookVec().yCoord;
            EffectManager.spawnParticle(7, this.worldObj, x, y, z, 0.4, 0.8, 1.0);
            eyeOff = -25;
            x = this.posX - Math.sin(Math.toRadians(this.rotationYawHead + eyeOff)) * eyeDist * yLook + this.motionX;
            z = this.posZ + Math.cos(Math.toRadians(this.rotationYawHead + eyeOff)) * eyeDist * yLook + this.motionZ;
            EffectManager.spawnParticle(7, this.worldObj, x, y, z, 0.4, 0.8, 1.0);
        }
    }
    
    @Override
    public ItemStack getDiamondArmorForSlot(final int slot) {
        ItemStack is = null;
        switch (slot) {
            case 3: {
                is = new ItemStack(ChocolateQuest.diamondPlate);
                break;
            }
            case 2: {
                is = new ItemStack(ChocolateQuest.diamondPants);
                break;
            }
            case 1: {
                is = new ItemStack(ChocolateQuest.diamondBoots);
                break;
            }
            default: {
                is = new ItemStack(ChocolateQuest.diamondHelmet);
                break;
            }
        }
        this.colorArmor(is, 8339378);
        return is;
    }
    
    @Override
    public ItemStack getIronArmorForSlot(final int slot) {
        ItemStack is = null;
        switch (slot) {
            case 3: {
                is = new ItemStack(ChocolateQuest.ironPlate);
                break;
            }
            case 2: {
                is = new ItemStack(ChocolateQuest.ironPants);
                break;
            }
            case 1: {
                is = new ItemStack(ChocolateQuest.ironBoots);
                break;
            }
            default: {
                is = new ItemStack(ChocolateQuest.ironHelmet);
                break;
            }
        }
        this.colorArmor(is, 8339378);
        return is;
    }
    
    protected String getLivingSound() {
        return EnumVoice.ENDERMEN.say;
    }
    
    protected String getHurtSound() {
        return EnumVoice.ENDERMEN.hurt;
    }
    
    protected String getDeathSound() {
        return EnumVoice.ENDERMEN.death;
    }
}
