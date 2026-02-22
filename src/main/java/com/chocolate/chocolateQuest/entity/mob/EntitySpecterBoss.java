package com.chocolate.chocolateQuest.entity.mob;

import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.world.World;

public class EntitySpecterBoss extends EntityHumanSpecter
{
    int invisibleCD;
    
    public EntitySpecterBoss(final World world) {
        super(world);
        this.invisibleCD = 10;
        this.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.swordMoonLight));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0);
    }
    
    @Override
    public int getLeadershipValue() {
        return 1000;
    }
    
    @Override
    public boolean isBoss() {
        return true;
    }
    
    public void onUpdate() {
        this.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 200, 0));
        super.onUpdate();
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        return super.attackEntityAsMob(entity);
    }
    
    public boolean canBeCollidedWith() {
        return this.isMaterialized();
    }
    
    public boolean canBePushed() {
        return this.canBeCollidedWith();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float i) {
        boolean ret = false;
        if (this.isSwingInProgress || this.getAttackTarget() == null || damagesource.isUnblockable() || damagesource.isFireDamage() || damagesource.isExplosion()) {
            ret = super.attackEntityFrom(damagesource, i);
        }
        return ret;
    }
    
    public boolean isMaterialized() {
        return this.isSwingInProgress || this.leftHandSwing > 0 || this.getAttackTarget() == null;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        if (flag && (this.rand.nextInt(5) == 0 || this.rand.nextInt(1 + i) > 0)) {
            this.dropItem(Items.diamond, 2);
        }
        if (this.rand.nextInt(3) == 0) {
            this.dropItem(ChocolateQuest.swordMoonLight, 1);
        }
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.villager.default";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.villager.defaulthurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.villager.defaultdeath";
    }
    
    @Override
    public boolean shouldRenderCape() {
        return true;
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
}
