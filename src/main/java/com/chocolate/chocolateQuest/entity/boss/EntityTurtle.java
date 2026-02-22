package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.world.EnumSkyBlock;
import net.minecraft.init.Items;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.ai.AITargetHurtBy;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityTurtle extends EntityBaseBoss
{
    private EntityTurtlePart[] body;
    double tempRYaw;
    double tempRPich;
    int rotAccel;
    boolean rotDir;
    boolean bubbleAttack;
    int bubbleCD;
    boolean hurt;
    int[] healPart;
    boolean hurtOnPartSoundFlag;
    double tempmx;
    double tempmz;
    double tempmy;
    
    public EntityTurtle(final World par1World) {
        super(par1World);
        this.rotAccel = 0;
        this.rotDir = true;
        this.bubbleAttack = false;
        this.bubbleCD = 0;
        this.hurtOnPartSoundFlag = false;
        this.experienceValue = 5;
        this.body = new EntityTurtlePart[5];
        this.fireDefense = 20;
        this.blastDefense = 20;
        this.limitRotation = true;
    }
    
    @Override
    protected void scaleAttributes() {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2 + this.lvl * 0.005);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue((double)(6.0f + this.lvl));
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0);
    }
    
    @Override
    public void addAITasks() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveTowardsTarget((EntityCreature)this, 1.0, 1.0f));
        this.tasks.addTask(2, (EntityAIBase)new AIBossAttack(this, 1.0f, false));
        this.targetTasks.addTask(1, (EntityAIBase)new AITargetHurtBy(this, false));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityMob.class, 0, true));
    }
    
    @Override
    public float getMinSize() {
        return 2.0f;
    }
    
    @Override
    public float getSizeVariation() {
        return 0.4f;
    }
    
    @Override
    protected void resize() {
        this.setSize(this.size, this.size * 0.4f);
    }
    
    @Override
    public void initBody() {
        super.initBody();
        final float dist = this.size - this.size / 4.0f;
        if (!this.worldObj.isRemote) {
            (this.body[0] = new EntityTurtlePart(this.worldObj, 0, 0.0f, this.size - this.size / 3.0f, this.size / 10.0f)).setMainBody(this);
            this.body[0].setHead();
            this.scalePart(this.body[0]);
            if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld((Entity)this.body[0]);
            }
            final int[] pos = { 45, -45, 135, -135 };
            for (int i = 1; i <= 4; ++i) {
                (this.body[i] = new EntityTurtlePart(this.worldObj, i, (float)pos[i - 1], dist)).setMainBody(this);
                this.body[i].setPosition(this.posX, this.posY, this.posZ);
                this.worldObj.spawnEntityInWorld((Entity)this.body[i]);
            }
        }
    }
    
    @Override
    protected boolean isAIEnabled() {
        return !this.isAttacking() && !this.isHealing();
    }
    
    public void onUpdate() {
        if (!this.worldObj.isRemote && this.getHealth() < 50.0f && !this.isHealing() && this.body[0] != null) {
            for (int i = 0; i < 5; ++i) {
                if (this.body[i] != null) {
                    this.body[i].setHeartsLife(this.body[i].getMaxHealth());
                }
            }
            this.setHealing(true);
        }
        for (int i = 0; i < this.body.length; ++i) {
            if (this.body[i] != null) {
                if (!this.body[i].isEntityAlive()) {
                    this.body[i] = null;
                }
            }
        }
        if (this.isHealing()) {
            if (this.worldObj.isRemote) {
                this.worldObj.spawnParticle("heart", this.posX + this.rand.nextDouble() * 2.0 - 1.0, this.posY + 1.0 + this.rand.nextDouble() * 2.0, this.posZ + this.rand.nextDouble() * 2.0 - 1.0, 0.0, 1.0, 0.0);
            }
            this.heal(1.0f);
            if (this.getHealth() >= this.getMaxHealth()) {
                this.setHealing(false);
            }
        }
        if (this.getAttackTarget() != null) {
            if (this.inWater || this.handleLavaMovement()) {
                if (this.posY + 3.0 < this.getAttackTarget().posY && this.motionY < 0.2) {
                    this.motionY += 0.03;
                }
                else if (this.posY + 3.0 > this.getAttackTarget().posY && this.motionY > -0.2) {
                    this.motionY -= 0.03;
                }
            }
        }
        else if (this.inWater && this.motionY < 0.1) {
            this.motionY -= 0.03;
        }
        super.onUpdate();
        if (this.isAttacking()) {
            this.doAttack();
        }
        if (this.bubbleAttack) {
            this.bubbleRay();
        }
        if (this.isAttacking() || this.isHealing()) {
            this.hidePartsInShell();
            this.hideHeadInShell();
        }
        else {
            for (int i = 0; i < 5; ++i) {
                if (this.body[i] != null) {
                    this.body[i].staticPart = true;
                }
            }
        }
    }
    
    private void hidePartsInShell() {
        for (int i = 1; i < 5; ++i) {
            if (this.body[i] != null) {
                this.body[i].staticPart = false;
                this.body[i].setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            }
        }
    }
    
    private void hideHeadInShell() {
        if (this.body[0] != null) {
            this.body[0].staticPart = false;
            this.body[0].setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
    
    @Override
    public void attackEntity(final Entity entity, final float f) {
        if (!this.isAttacking() && !this.bubbleAttack && !this.isHealing()) {
            float width = this.size * 0.3f;
            width = width * width + this.width * this.width;
            int attackChance = (int)(f - width - entity.width * entity.width);
            attackChance = ((attackChance < 40) ? 40 : ((attackChance > 80) ? 80 : attackChance));
            if (this.rand.nextInt(attackChance) == 0) {
                if (this.rand.nextInt(3) == 0) {
                    this.setAttacking(true);
                    this.rotDir = true;
                }
                else {
                    final double d = this.posX - entity.posX;
                    final double d2 = this.posY - (entity.posY + entity.height / 2.0f);
                    final double d3 = this.posZ - entity.posZ;
                    float angle;
                    for (angle = (float)Math.atan2(d, d3), angle = this.rotationYaw - entity.rotationYaw; angle > 360.0f; angle -= 360.0f) {}
                    while (angle < 0.0f) {
                        angle += 360.0f;
                    }
                    angle = Math.abs(angle - 180.0f);
                    if (angle < 60.0f) {
                        this.startBubble();
                    }
                }
            }
        }
    }
    
    @Override
    protected boolean limitRotation() {
        return !this.isAttacking();
    }
    
    public void doAttack() {
        if (this.getAttackTarget() == null) {
            return;
        }
        if (this.rotDir) {
            ++this.rotAccel;
        }
        else {
            --this.rotAccel;
            if (this.rotAccel > 80) {
                this.motionX = this.tempmx;
                this.motionZ = this.tempmz;
                this.motionY = this.tempmy;
            }
        }
        this.rotationYaw += this.rotAccel / 2;
        if (this.rotAccel >= 100) {
            this.rotDir = false;
        }
        if (this.rotAccel == 50 && this.getHealth() < 200.0f) {
            this.startBubble();
        }
        if (this.rotAccel == 80) {
            final Entity e = (Entity)this.getAttackTarget();
            final double px = this.posX - e.posX;
            final double pz = this.posZ - e.posZ;
            final double py = this.posY - (e.posY + e.height / 2.0f);
            this.tempRYaw = Math.atan2(px, pz);
            this.tempRPich = Math.atan2(py, MathHelper.sqrt_double(px * px + pz * pz));
            this.tempmy = -Math.sin(this.tempRPich);
            this.tempmx = -Math.sin(this.tempRYaw);
            this.tempmz = -Math.cos(this.tempRYaw);
        }
        if (this.rotAccel >= 20) {
            final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(1.0, 1.0, 1.0));
            final double d = 0.0;
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity1 = list.get(j);
                if (entity1.canBeCollidedWith() && !entity1.isEntityEqual((Entity)this)) {
                    if (entity1 != this.riddenByEntity) {
                        final AxisAlignedBB axisalignedbb = entity1.boundingBox;
                        this.attackEntityAsMob(entity1);
                    }
                }
            }
        }
        if (this.rotAccel == 0) {
            this.setAttacking(false);
        }
    }
    
    public void startBubble() {
        final Entity e = (Entity)this.getAttackTarget();
        final double px = this.posX - e.posX;
        final double pz = this.posZ - e.posZ;
        final double py = this.posY - e.posY;
        this.tempRYaw = Math.atan2(px, pz);
        this.tempRPich = Math.atan2(py, MathHelper.sqrt_double(px * px + pz * pz));
        this.tempmy = -Math.sin(this.tempRPich);
        this.tempmx = -Math.sin(this.tempRYaw);
        this.tempmz = -Math.cos(this.tempRYaw);
        this.bubbleAttack = true;
    }
    
    public void bubbleRay() {
        if (this.bubbleCD >= 20) {
            this.bubbleCD = 0;
            this.worldObj.spawnEntityInWorld((Entity)new EntityBaseBall(this.worldObj, (EntityLivingBase)this, 7));
            this.bubbleAttack = false;
            this.bubbleCD = 0;
            if (!this.isAttacking() && this.rand.nextInt(3) == 0) {
                this.bubbleAttack = true;
            }
        }
        else {
            ++this.bubbleCD;
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        final boolean flag = super.attackEntityFrom(par1DamageSource, par2);
        if (this.body != null) {
            for (int i = 0; i < 5; ++i) {
                if (this.body[i] != null) {
                    if (this.body[i].isDead) {
                        this.hurt = true;
                        this.body[i] = null;
                    }
                }
                else {
                    this.hurt = true;
                }
            }
        }
        return flag;
    }
    
    @Override
    public boolean attackFromPart(final DamageSource par1DamageSource, final float par2, final EntityPart part) {
        this.hurtOnPartSoundFlag = true;
        return this.attackEntityFrom(par1DamageSource, par2);
    }
    
    public int getTotalArmorValue() {
        return 0;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        if (!this.worldObj.isRemote) {
            int scalesDropped = 2;
            int partsLeft = 0;
            if (this.body[0] == null) {
                scalesDropped += 6;
                ++partsLeft;
            }
            for (int k = 1; k < this.body.length; ++k) {
                if (this.body[k] == null) {
                    scalesDropped += 4;
                    ++partsLeft;
                }
            }
            this.worldObj.spawnEntityInWorld((Entity)new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(ChocolateQuest.material, scalesDropped, 1)));
        }
        if (flag && (this.rand.nextInt(5) == 0 || this.rand.nextInt(1 + i) > 0)) {
            this.dropItem(Items.diamond, 2);
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    public boolean isEntityEqual(final Entity par1Entity) {
        boolean flag = false;
        if (this.body != null) {
            for (int i = 0; i < this.body.length && !flag; flag = (this.body[i] == par1Entity), ++i) {}
        }
        return this == par1Entity || flag;
    }
    
    public boolean canBreatheUnderwater() {
        return true;
    }
    
    protected boolean isValidLightLevel() {
        final int i = MathHelper.floor_double(this.posX);
        final int j = MathHelper.floor_double(this.boundingBox.minY);
        final int k = MathHelper.floor_double(this.posZ);
        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > this.rand.nextInt(32)) {
            return false;
        }
        int l = this.worldObj.getBlockLightValue(i, j, k);
        if (this.worldObj.isThundering()) {
            final int i2 = this.worldObj.skylightSubtracted;
            this.worldObj.skylightSubtracted = 10;
            l = this.worldObj.getBlockLightValue(i, j, k);
            this.worldObj.skylightSubtracted = i2;
        }
        return l <= this.rand.nextInt(8);
    }
    
    protected String getHurtSound() {
        if (this.hurtOnPartSoundFlag) {
            this.hurtOnPartSoundFlag = false;
            return super.getHurtSound();
        }
        return "mob.blaze.hit";
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (Object)0);
    }
    
    public boolean isHealing() {
        return this.dataWatcher.getWatchableObjectByte(16) == 0;
    }
    
    public void setHealing(final boolean healing) {
        this.dataWatcher.updateObject(16, (Object)(healing ? 0 : ((Byte)1)));
    }
    
    @Override
    public void setPart(final EntityPart entityTurtlePart, final int partID) {
        this.body[partID] = (EntityTurtlePart)entityTurtlePart;
        this.scalePart(entityTurtlePart);
    }
    
    protected void scalePart(final EntityPart part) {
        final float scale = this.size * 0.3f;
        part.setSize(scale, scale);
        part.setPosition(this.posX, this.posY, this.posZ);
    }
    
    @Override
    public boolean canBePushed() {
        return false;
    }
    
    @Override
    public boolean shouldMoveToEntity(final double d1, final Entity target) {
        return !this.isAttacking();
    }
    
    public EntityTurtlePart[] getBossParts() {
        return this.body;
    }
    
    @Override
    protected int getDropMaterial() {
        return 1;
    }
}
