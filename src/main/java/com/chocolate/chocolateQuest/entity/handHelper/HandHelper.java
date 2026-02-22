package com.chocolate.chocolateQuest.entity.handHelper;

import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import com.chocolate.chocolateQuest.API.ITwoHandedItem;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.init.Items;
import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.ICooldownTracker;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.item.ItemStack;

public class HandHelper
{
    private boolean extendedRange;
    private boolean twoHanded;
    ItemStack currentItem;
    EntityHumanBase owner;
    public int attackTime;
    
    public static HandHelper getHandHelperForItem(final EntityHumanBase owner, final ItemStack itemStack) {
        if (itemStack == null) {
            return new HandEmpty(owner, itemStack);
        }
        final Item item = itemStack.getItem();
        if (item instanceof IRangedWeapon && item instanceof ICooldownTracker) {
            return new HandRangedAdvanced(owner, itemStack);
        }
        if (item instanceof IRangedWeapon) {
            return new HandRanged(owner, itemStack);
        }
        if (item == ChocolateQuest.banner) {
            return new HandSupport(owner, itemStack);
        }
        if (item == ChocolateQuest.shield) {
            return new HandShield(owner, itemStack);
        }
        if (item instanceof ItemHookShoot || item == ChocolateQuest.hookSword) {
            return new HandHook(owner, itemStack);
        }
        if (item instanceof ItemBaseDagger) {
            return new HandDagger(owner, itemStack);
        }
        if (item instanceof ItemStaffHeal) {
            return new HandHealer(owner, itemStack);
        }
        if (item == Items.bow) {
            return new HandBow(owner, itemStack);
        }
        if (item == Items.lead) {
            return new HandLead(owner, itemStack);
        }
        if (item == Items.snowball) {
            return new HandSnowBall(owner, itemStack);
        }
        if (item == Items.fire_charge) {
            return new HandFireChange(owner, itemStack);
        }
        if (item == Items.flint_and_steel) {
            return new HandFire(owner, itemStack);
        }
        if (item instanceof ItemPotion) {
            return new HandPotion(owner, itemStack);
        }
        if (item instanceof ItemBlock && Block.getBlockFromItem(item) == Blocks.tnt) {
            return new HandTNT(owner, itemStack);
        }
        return new HandHelper(owner, itemStack);
    }
    
    public HandHelper(final EntityHumanBase owner, final ItemStack itemStack) {
        this.extendedRange = false;
        this.twoHanded = false;
        this.attackTime = 0;
        this.owner = owner;
        this.currentItem = itemStack;
        if (itemStack != null) {
            final Item item = itemStack.getItem();
            if (item instanceof ITwoHandedItem || item == Items.bow) {
                this.twoHanded = true;
            }
            if (item instanceof ItemBaseSpear) {
                this.extendedRange = true;
            }
        }
    }
    
    public void attackEntity(final Entity entity) {
        if (this.attackTime <= 0) {
            this.attackTime = this.owner.getAttackSpeed();
            this.owner.swingHand(this);
            this.owner.attackEntityAsMob(entity, this.currentItem);
        }
    }
    
    public boolean attackWithRange(final Entity target, final float f) {
        return false;
    }
    
    public void setAiming(final boolean aim) {
    }
    
    public boolean isAiming() {
        return false;
    }
    
    public void onUpdate() {
        if (this.attackTime > 0) {
            --this.attackTime;
        }
    }
    
    public boolean isTwoHanded() {
        return this.twoHanded;
    }
    
    public boolean isRanged() {
        return false;
    }
    
    public boolean canBlock() {
        return false;
    }
    
    public boolean isHealer() {
        return false;
    }
    
    public double getAttackRangeBonus() {
        return this.extendedRange ? 1.0 : 0.0;
    }
    
    public double getMaxRangeForAttack() {
        return this.owner.width * this.owner.width + this.getAttackRangeBonus();
    }
    
    public double getDistanceToStopAdvancing() {
        return this.getAttackRangeBonus();
    }
    
    public ItemStack getItem() {
        return this.currentItem;
    }
}
