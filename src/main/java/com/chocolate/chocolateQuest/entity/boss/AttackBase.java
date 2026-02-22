package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.entity.Entity;

public class AttackBase
{
    EntityBaseBoss owner;
    
    public AttackBase(final EntityBaseBoss owner) {
        this.owner = owner;
    }
    
    public void onUpdate() {
    }
    
    public boolean isAttackInProgress() {
        return false;
    }
    
    public boolean attackTarget(final Entity target) {
        return false;
    }
}
