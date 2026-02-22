package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.entity.EnumCreatureAttribute;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import net.minecraft.item.ItemBow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.world.World;

public class EntityWalkerBoss extends EntityHumanWalker
{
    int countDown;
    boolean bolt;
    double lightX;
    double lightY;
    double lightZ;
    
    public EntityWalkerBoss(final World world) {
        super(world);
        this.countDown = 0;
        this.blockRate = 80;
        this.parryRate = 80;
        this.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.endSword));
        this.setLeftHandItem(new ItemStack(ChocolateQuest.shield, 1, 10));
        this.updateHands();
        this.setCurrentItemOrArmor(4, this.getDiamondArmorForSlot(4));
        final ItemStack plate = new ItemStack(ChocolateQuest.kingArmor);
        this.colorArmor(plate, 8339378);
        plate.stackTagCompound.setInteger("cape", 10);
        plate.stackTagCompound.setInteger("apron", 10);
        this.setCurrentItemOrArmor(3, plate);
        this.setCurrentItemOrArmor(2, this.getDiamondArmorForSlot(2));
        this.setCurrentItemOrArmor(1, this.getDiamondArmorForSlot(1));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0);
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
    
    public boolean canBePushed() {
        return false;
    }
    
    @Override
    public boolean shouldRenderCape() {
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float i) {
        if (damagesource.getDamageType() == "fall") {
            return false;
        }
        if (damagesource.isProjectile() && damagesource.getEntity() instanceof EntityLiving) {
            this.setAttackTarget((EntityLivingBase)damagesource.getEntity());
            this.castTeleport(damagesource.getEntity());
        }
        return super.attackEntityFrom(damagesource, i);
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.getAttackTarget() != null) {
            final float dist = this.getDistanceToEntity((Entity)this.getAttackTarget());
            boolean isTargetRanged = false;
            final ItemStack is = this.getAttackTarget().getEquipmentInSlot(0);
            if (is != null && dist > 5.0f && (is.getItem() instanceof ItemBow || is.getItem() instanceof IRangedWeapon)) {
                isTargetRanged = true;
            }
            if (((dist > 15.0f && dist < 40.0f) || isTargetRanged) && !this.worldObj.isRemote) {
                this.castTeleport((Entity)this.getAttackTarget());
            }
            if (((dist > 5.0f && dist < 10.0f && this.rand.nextInt(30) == 0) || this.rand.nextInt(300) == 0) && this.ridingEntity == null && !this.worldObj.isRemote) {
                this.swingLeftHand();
                Elements element = Elements.darkness;
                if (this.getAttackTarget().getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
                    element = Elements.light;
                }
                final EntityBaseBall ball = new EntityBaseBall(this.worldObj, (EntityLivingBase)this, 10, 1, element);
                ball.motionY = this.getAttackTarget().posY - this.posY;
                this.worldObj.spawnEntityInWorld((Entity)ball);
            }
        }
        if (!this.worldObj.isRemote && this.getAttackTarget() == null && this.isDefending()) {
            this.setDefending(false);
        }
        super.onLivingUpdate();
    }
    
    public void onStruckByLightning(final EntityLightningBolt par1EntityLightningBolt) {
        this.heal(50.0f);
        for (int i = 0; i < 3; ++i) {
            this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() - 0.5, this.posY + this.rand.nextFloat() - 0.5, this.posZ + this.rand.nextFloat() - 0.5, 0.0, 1.0, 0.0);
        }
    }
    
    public void heal(final float par1) {
        super.heal(par1);
        if (this.getHealth() > this.getMaxHealth()) {
            this.setHealth(this.getMaxHealth());
        }
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        if (!this.worldObj.isRemote) {
            if (this.rand.nextInt(2) == 0) {
                this.dropItem(ChocolateQuest.endSword, 1);
            }
            if (this.rand.nextInt(4) == 0) {
                this.dropItem(ChocolateQuest.kingArmor, 1);
            }
        }
        super.onDeath(damagesource);
    }
    
    protected boolean castTeleport(final Entity entity) {
        if (this.ridingEntity != null) {
            return false;
        }
        final double d = entity.posX + (this.rand.nextDouble() - 0.5) * 4.0;
        final double d2 = entity.posY;
        final double d3 = entity.posZ + (this.rand.nextDouble() - 0.5) * 4.0;
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
    public boolean isSuitableMount(final Entity entity) {
        return entity instanceof EntityWyvern || super.isSuitableMount(entity);
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
}
