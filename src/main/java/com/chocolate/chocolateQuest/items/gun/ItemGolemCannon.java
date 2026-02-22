package com.chocolate.chocolateQuest.items.gun;

import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class ItemGolemCannon extends ItemGolemWeapon
{
    public ItemGolemCannon(final int cooldown, final float range, final float accuracy, final int lvl) {
        super(cooldown, range, accuracy, lvl, 16);
    }
    
    @Override
    public EntityBaseBall getBall(final World world, final EntityLivingBase shooter, final double x, final double y, final double z) {
        float accuracy = this.accuracy;
        byte projectile = 1;
        if (shooter instanceof EntityHumanBase) {
            accuracy += ((EntityHumanBase)shooter).accuracy;
            if (shooter instanceof EntityGolemMecha) {
                projectile = 2;
            }
        }
        return new EntityBaseBall(shooter.worldObj, shooter, x, y, z, projectile, 4, accuracy);
    }
    
    @Override
    public boolean isValidAmmo(final ItemStack is) {
        return super.isValidAmmo(is) && is.getMetadata() == 4;
    }
}
