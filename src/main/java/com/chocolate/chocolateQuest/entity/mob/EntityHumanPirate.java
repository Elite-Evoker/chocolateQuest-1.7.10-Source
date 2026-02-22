package com.chocolate.chocolateQuest.entity.mob;

import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.ai.EntityAIBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIFirefighter;
import net.minecraft.world.World;

public class EntityHumanPirate extends EntityHumanMob
{
    public EntityHumanPirate(final World world) {
        super(world);
    }
    
    @Override
    protected void addAITasks() {
        this.tasks.addTask(4, (EntityAIBase)new AIFirefighter(this, 1.0f, false));
        super.addAITasks();
    }
    
    @Override
    public DungeonMonstersBase getMonsterType() {
        return ChocolateQuest.pirate;
    }
    
    protected String getLivingSound() {
        return EnumVoice.PIRATE.say;
    }
    
    protected String getHurtSound() {
        return EnumVoice.PIRATE.hurt;
    }
    
    protected String getDeathSound() {
        return EnumVoice.PIRATE.death;
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
        this.colorArmor(is, 2236962);
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
        this.colorArmor(is, 2236962);
        return is;
    }
}
