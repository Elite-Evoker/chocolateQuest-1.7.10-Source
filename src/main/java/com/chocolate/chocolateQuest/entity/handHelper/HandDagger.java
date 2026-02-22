package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class HandDagger extends HandHelper
{
    public HandDagger(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.owner.getAttackTarget() != null) {
            final EntityLivingBase target = this.owner.getAttackTarget();
            float angle;
            for (angle = this.owner.rotationYawHead - target.rotationYawHead; angle > 360.0f; angle -= 360.0f) {}
            while (angle < 0.0f) {
                angle += 360.0f;
            }
            angle = Math.abs(angle - 180.0f);
            if (Math.abs(angle) > 130.0f) {
                this.owner.setSneaking(true);
            }
            else {
                this.owner.setSneaking(false);
            }
        }
    }
}
