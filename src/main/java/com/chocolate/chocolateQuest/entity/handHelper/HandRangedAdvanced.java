package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.API.ICooldownTracker;

public class HandRangedAdvanced extends HandRanged
{
    ICooldownTracker itemTracker;
    public Object cooldownTracker;
    
    public HandRangedAdvanced(final EntityHumanBase owner, final ItemStack itemStack) {
        super(owner, itemStack);
        this.cooldownTracker = null;
        if (itemStack.getItem() instanceof ICooldownTracker) {
            this.itemTracker = (ICooldownTracker)itemStack.getItem();
            this.cooldownTracker = this.itemTracker.getCooldownTracker(itemStack, (Entity)owner);
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.cooldownTracker != null) {
            this.itemTracker.onUpdateCooldown(this.currentItem, (Entity)this.owner, this.cooldownTracker);
        }
        super.onUpdate();
    }
}
