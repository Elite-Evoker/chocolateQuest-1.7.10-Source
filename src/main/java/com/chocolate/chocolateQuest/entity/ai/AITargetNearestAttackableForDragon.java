package com.chocolate.chocolateQuest.entity.ai;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.ai.EntityAITarget;

public class AITargetNearestAttackableForDragon extends EntityAITarget
{
    private final Class targetClass;
    private final int targetChance;
    private final NearestAttackableTargetSorter theNearestAttackableTargetSorter;
    private final IEntitySelector targetEntitySelector;
    private EntityLivingBase targetEntity;
    
    public AITargetNearestAttackableForDragon(final EntityCreature par1EntityCreature, final Class par2Class, final int par3, final boolean par4) {
        this(par1EntityCreature, par2Class, par3, par4, false);
    }
    
    public AITargetNearestAttackableForDragon(final EntityCreature par1EntityCreature, final Class par2Class, final int par3, final boolean par4, final boolean par5) {
        this(par1EntityCreature, par2Class, par3, par4, par5, null);
    }
    
    public AITargetNearestAttackableForDragon(final EntityCreature par1EntityCreature, final Class par2Class, final int par3, final boolean par4, final boolean par5, final IEntitySelector par6IEntitySelector) {
        super(par1EntityCreature, par4, par5);
        this.targetClass = par2Class;
        this.targetChance = par3;
        this.theNearestAttackableTargetSorter = new NearestAttackableTargetSorter((EntityLivingBase)par1EntityCreature);
        this.setMutexBits(1);
        this.targetEntitySelector = (IEntitySelector)new SelectorLiving(par1EntityCreature, this, par6IEntitySelector);
    }
    
    public boolean isSuitableTarget(final EntityLivingBase par1EntityLivingBase, final boolean par2) {
        return super.isSuitableTarget(par1EntityLivingBase, par2);
    }
    
    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }
        final double d0 = this.getTargetDistance();
        final List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 10.0, d0), this.targetEntitySelector);
        Collections.sort((List<Object>)list, (Comparator<? super Object>)this.theNearestAttackableTargetSorter);
        if (list.isEmpty()) {
            return false;
        }
        this.targetEntity = list.get(0);
        return true;
    }
    
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
}
