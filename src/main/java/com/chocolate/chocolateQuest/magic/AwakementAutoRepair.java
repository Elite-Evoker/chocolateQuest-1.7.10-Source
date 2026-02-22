package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.items.swords.ItemBDSword;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import net.minecraft.item.ItemStack;

public class AwakementAutoRepair extends Awakements
{
    public AwakementAutoRepair(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public boolean canBeUsedOnItem(final ItemStack is) {
        if (is.getItem() instanceof ItemArmorBase) {
            return ((ItemArmorBase)is.getItem()).isEpic();
        }
        return is.getItem() instanceof ItemBDSword;
    }
    
    @Override
    public int getMaxLevel() {
        return 4;
    }
    
    @Override
    public void onUpdate(final Entity entity, final ItemStack itemStack) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            final int repairLevel = Awakements.getEnchantLevel(itemStack, this);
            if (itemStack.getMetadata() >= repairLevel && player.experienceLevel > 0) {
                player.addExperience(-1);
                player.experienceTotal = 0;
                if (player.experience < 0.0f) {
                    if (player.experienceLevel > 0) {
                        player.addExperienceLevel(-1);
                        player.experience = 1.0f;
                    }
                    else {
                        player.experience = 0.0f;
                    }
                }
                itemStack.setMetadata(itemStack.getMetadata() - repairLevel);
            }
        }
    }
    
    @Override
    public boolean canBeAddedByNPC(final int type) {
        return type == EnumEnchantType.BLACKSMITH.ordinal();
    }
    
    @Override
    public int getLevelCost() {
        return 3;
    }
}
