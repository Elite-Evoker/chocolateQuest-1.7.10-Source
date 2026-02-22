package com.chocolate.chocolateQuest.entity.mob;

import net.minecraft.init.Items;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.EntityBaiter;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.ai.EntityAIBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIFirefighter;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import net.minecraft.world.World;

public class EntityPirateBoss extends EntityHumanPirate
{
    int invisibleCD;
    
    public EntityPirateBoss(final World world) {
        super(world);
        this.invisibleCD = 10;
        this.AICombatMode = EnumAiCombat.BACKSTAB.ordinal();
        this.potionCount = 3;
        final ItemStack is = this.getEquipedWeapon();
        this.setCurrentItemOrArmor(0, is);
        this.setAIForCurrentMode();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0);
    }
    
    public ItemStack getEquipedWeapon() {
        if (this.rand.nextBoolean()) {
            return new ItemStack(ChocolateQuest.ninjaDagger);
        }
        return new ItemStack(ChocolateQuest.tricksterDagger);
    }
    
    @Override
    public int getLeadershipValue() {
        return 1000;
    }
    
    @Override
    public boolean isBoss() {
        return true;
    }
    
    @Override
    public int getInteligence() {
        return 0;
    }
    
    @Override
    protected void addAITasks() {
        this.tasks.addTask(4, (EntityAIBase)new AIFirefighter(this, 1.0f, false));
        super.addAITasks();
    }
    
    public void onUpdate() {
        if (this.getAttackTarget() != null) {
            if (this.getHealth() < 450.0f && !this.isInvisible() && this.invisibleCD < 0) {
                this.addPotionEffect(new PotionEffect(Potion.invisibility.id, 200, 0));
                this.setInvisible(true);
                if (this.worldObj.isRemote) {
                    for (int n = 0; n < 5; ++n) {
                        this.worldObj.spawnParticle("mobSpell", this.posX + this.rand.nextFloat() - 0.5, this.posY + 1.0, this.posZ + this.rand.nextFloat() - 0.5, 1.0, 1.0, 1.0);
                    }
                }
                else {
                    final EntityBaiter e = new EntityBaiter(this.worldObj, (EntityLivingBase)this);
                    e.setPosition(this.posX, this.posY, this.posZ);
                    this.worldObj.spawnEntityInWorld((Entity)e);
                }
                this.castTeleport((Entity)this.getAttackTarget());
                this.invisibleCD = 200;
            }
            --this.invisibleCD;
        }
        if (this.isInvisible() && !this.worldObj.isRemote && this.getAttackTarget() == null) {
            this.setInvisible(false);
        }
        super.onUpdate();
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (this.rand.nextInt(15) == 1) {
            if (entity instanceof EntityLiving) {
                ((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 3));
            }
        }
        else if (this.rand.nextInt(15) == 1 && entity instanceof EntityLiving) {
            ((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 100, 5));
        }
        return super.attackEntityAsMob(entity);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float i) {
        if (this.isInvisible()) {
            this.setInvisible(false);
            this.invisibleCD = 60 - this.worldObj.difficultySetting.ordinal() * 10;
        }
        return (!this.isDefending() || (!damagesource.isProjectile() && this.rand.nextInt(5) != 0)) && super.attackEntityFrom(damagesource, i);
    }
    
    protected boolean castTeleport(final Entity entity) {
        final double d = entity.posX + (this.rand.nextDouble() - 0.5) * 8.0;
        final double d2 = entity.posY;
        final double d3 = entity.posZ + (this.rand.nextDouble() - 0.5) * 8.0;
        final double d4 = this.posX;
        final double d5 = this.posY;
        final double d6 = this.posZ;
        this.posX = d;
        this.posY = d2;
        this.posZ = d3;
        boolean flag = false;
        final int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        final int k = MathHelper.floor_double(this.posZ);
        if (this.worldObj.blockExists(i, j, k)) {
            boolean flag2 = false;
            while (!flag2 && j > 0) {
                final Block i2 = this.worldObj.getBlock(i, j - 1, k);
                if (i2 == Blocks.air || !i2.getMaterial().isSolid()) {
                    --this.posY;
                    --j;
                }
                else {
                    flag2 = true;
                }
            }
            if (flag2) {
                for (int n = 0; n < 10; ++n) {
                    this.worldObj.spawnParticle("largesmoke", d4 + this.rand.nextFloat() - 0.5, d5, d6 + this.rand.nextFloat() - 0.5, 1.0, 1.0, 1.0);
                }
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes((Entity)this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox)) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            this.setPosition(d4, d5, d6);
            return false;
        }
        for (int l = 128, j2 = 0; j2 < l; ++j2) {
            final double d7 = j2 / (l - 1.0);
            final float f = (this.rand.nextFloat() - 0.5f) * 0.2f;
            final float f2 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            final float f3 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            final double d8 = d4 + (this.posX - d4) * d7 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
            final double d9 = d5 + (this.posY - d5) * d7 + this.rand.nextDouble() * this.height;
            final double d10 = d6 + (this.posZ - d6) * d7 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
        }
        this.worldObj.playSoundEffect(d4, d5, d6, "mob.endermen.portal", 1.0f, 1.0f);
        this.worldObj.playSoundAtEntity((Entity)this, "mob.endermen.portal", 1.0f, 1.0f);
        return true;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        if (this.getEquipmentInSlot(0) != null) {
            this.dropItem(this.getEquipmentInSlot(0).getItem(), 1);
        }
        if (flag && (this.rand.nextInt(5) == 0 || this.rand.nextInt(1 + i) > 0)) {
            this.dropItem(Items.diamond, 2);
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
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
    
    public boolean isInvisible() {
        return this.dataWatcher.getWatchableObjectByte(30) == 1;
    }
    
    public void setInvisible(final boolean invisible) {
        this.dataWatcher.updateObject(30, (Object)(byte)(invisible ? 1 : 0));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(30, (Object)0);
    }
    
    public void setHealth(final int health) {
        this.dataWatcher.updateObject(15, (Object)health);
    }
    
    @Override
    public boolean shouldRenderCape() {
        return true;
    }
}
