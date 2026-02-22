package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.magic.ElementsHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.magic.IElementWeak;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.EntityCreature;

public class EntityBaseBoss extends EntityCreature implements IBossDisplayData, IMob, IEntityAdditionalSpawnData, IElementWeak
{
    private boolean firstTick;
    float lvl;
    float size;
    protected boolean ridableBB;
    float rotSpeed;
    boolean limitRotation;
    int rage;
    float xpRatio;
    protected int physicDefense;
    protected int magicDefense;
    protected int blastDefense;
    protected int fireDefense;
    protected int projectileDefense;
    
    public EntityBaseBoss(final World par1World) {
        super(par1World);
        this.firstTick = true;
        this.lvl = 1.0f;
        this.size = 1.0f;
        this.ridableBB = true;
        this.rotSpeed = 3.0f;
        this.limitRotation = false;
        this.rage = 0;
        this.xpRatio = 1.0f;
        this.physicDefense = 0;
        this.magicDefense = 0;
        this.blastDefense = 0;
        this.fireDefense = 0;
        this.projectileDefense = 0;
        this.addAITasks();
        this.setMonsterScale(this.lvl);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (Object)0);
        this.experienceValue = 30 + (int)(this.getMonsterDificulty() * 10.0f * this.xpRatio);
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
    }
    
    protected void scaleAttributes() {
    }
    
    protected boolean isAIEnabled() {
        return true;
    }
    
    public void initBody() {
        this.firstTick = false;
    }
    
    public void setPart(final EntityPart entityPart, final int partID) {
        this.firstTick = false;
    }
    
    public void setMonsterScale(float size) {
        if (size < 0.0f) {
            size = 1.0f;
        }
        this.lvl = size;
        this.size = size * this.getSizeVariation() + this.getMinSize();
        this.resize();
        this.scaleAttributes();
        this.heal(this.getMaxHealth());
    }
    
    public float getMinSize() {
        return 1.0f;
    }
    
    public float getSizeVariation() {
        return 1.0f;
    }
    
    public float getMonsterDificulty() {
        return this.lvl;
    }
    
    public void onLivingUpdate() {
        if (this.firstTick) {
            this.initBody();
        }
        super.onLivingUpdate();
        if (this.rage > 0) {
            --this.rage;
        }
        this.updateArmSwingProgress();
        if (this.ticksExisted % 50 == 0 && this.getHealth() < this.getMaxHealth()) {
            if (this.getAttackTarget() != null) {
                if (this.ticksExisted % 500 == 0) {
                    this.heal(1.0f);
                }
            }
            else {
                this.heal(1.0f);
            }
        }
        this.rotationYawHead = this.rotationYaw;
    }
    
    public void setRotationYawHead(final float par1) {
        if (!this.limitRotation()) {
            super.setRotationYawHead(par1);
        }
    }
    
    public void moveEntityWithHeading(final float par1, final float par2) {
        if (this.limitRotation() && !this.worldObj.isRemote) {
            if (this.rotationYaw - this.prevRotationYaw > this.rotSpeed) {
                this.rotationYaw = this.prevRotationYaw + this.rotSpeed;
            }
            else if (this.rotationYaw - this.prevRotationYaw < -this.rotSpeed) {
                this.rotationYaw = this.prevRotationYaw - this.rotSpeed;
            }
        }
        super.moveEntityWithHeading(par1, par2);
    }
    
    protected boolean limitRotation() {
        return true;
    }
    
    public void moveAsBoss() {
    }
    
    public void attackEntity(final Entity par1Entity, final float par2) {
        super.attackEntity(par1Entity, par2);
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        return this.attackEntityAsMob(par1Entity, 1.0f);
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity, final float damageScale) {
        float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        final DamageSource ds = (DamageSource)new EntityDamageSource("mob", (Entity)this);
        if (par1Entity instanceof EntityCreeper) {
            damage = 40.0f;
        }
        return par1Entity.attackEntityFrom(ds, damage * damageScale);
    }
    
    public void addAITasks() {
    }
    
    protected boolean canDespawn() {
        return false;
    }
    
    public boolean isAttacking() {
        return this.getAnimFlag(0);
    }
    
    public void setAttacking(final boolean attacking) {
        this.setAnimFlag(0, attacking);
    }
    
    protected boolean getAnimFlag(final int index) {
        return (this.dataWatcher.getWatchableObjectByte(17) & 1 << index) != 0x0;
    }
    
    protected void setAnimFlag(final int index, final boolean result) {
        final byte b = this.dataWatcher.getWatchableObjectByte(17);
        if (result) {
            this.dataWatcher.updateObject(17, (Object)(byte)(b | 1 << index));
        }
        else {
            this.dataWatcher.updateObject(17, (Object)(byte)(b & ~(1 << index)));
        }
    }
    
    public boolean attackEntityFrom(final DamageSource ds, float damage) {
        final float td = damage;
        damage = ElementsHelper.getAmmountDecreased(this, damage, ds);
        final boolean ret = super.attackEntityFrom(ds, damage);
        this.rage += (int)damage;
        if (ds.getSourceOfDamage() != null) {
            if (!this.canAttackClass(ds.getSourceOfDamage().getClass())) {
                return true;
            }
            if (ret && !this.worldObj.isRemote && ds.getSourceOfDamage() instanceof EntityLivingBase) {
                this.setAttackTarget((EntityLivingBase)ds.getSourceOfDamage());
            }
        }
        return ret;
    }
    
    public boolean attackFromPart(final DamageSource par1DamageSource, final float par2, final EntityPart part) {
        return this.attackEntityFrom(par1DamageSource, par2);
    }
    
    public void swingItem() {
        if (!this.isSwingInProgress || this.swingProgressInt < 0) {
            this.swingProgressInt = -1;
            this.isSwingInProgress = true;
            if (!this.worldObj.isRemote) {
                final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)0);
                ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet, 64);
            }
        }
    }
    
    public float getScaleSize() {
        return this.size;
    }
    
    public void readSpawnData(final ByteBuf additionalData) {
        this.size = additionalData.readFloat();
        this.resize();
        this.onSpawn();
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        buffer.writeFloat(this.size);
        this.onSpawn();
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.size = nbt.getFloat("size");
        this.lvl = nbt.getFloat("scale");
        this.resize();
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("size", this.size);
        nbt.setFloat("scale", this.lvl);
    }
    
    public boolean shouldMoveToEntity(final double d1, final Entity target) {
        final float sizeBB = this.width + target.width + this.size / 2.0f;
        return d1 > sizeBB && !this.attackInProgress();
    }
    
    protected void resize() {
        this.setSize(this.size, this.size);
    }
    
    public void knockBack(final Entity entity, final float par2, final double par3, final double par5) {
    }
    
    public AxisAlignedBB getBoundingBox() {
        if (!this.ridableBB) {
            return null;
        }
        return this.boundingBox;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        if (entity.isEntityEqual((Entity)this) || !this.ridableBB) {
            return null;
        }
        return entity.boundingBox;
    }
    
    public void onCollideWithPlayer(final EntityPlayer par1EntityPlayer) {
    }
    
    public boolean canBePushed() {
        return true;
    }
    
    public void applyEntityCollision(final Entity par1Entity) {
        par1Entity.motionX += (par1Entity.posX - this.posX) / this.width;
        par1Entity.motionZ += (par1Entity.posZ - this.posZ) / this.width;
    }
    
    public void onSpawn() {
    }
    
    public void animationBoss(final byte animType) {
    }
    
    public void attackToXYZ(final byte arm, final double x, final double y, final double z) {
    }
    
    public boolean attackInProgress() {
        return false;
    }
    
    public boolean isRaging() {
        return this.getHealth() < this.getMaxHealth() / 5.0f;
    }
    
    public int getPhysicDefense() {
        return this.physicDefense;
    }
    
    public int getMagicDefense() {
        return this.magicDefense;
    }
    
    public int getBlastDefense() {
        return this.blastDefense;
    }
    
    public int getFireDefense() {
        return this.fireDefense;
    }
    
    public int getProjectileDefense() {
        return this.projectileDefense;
    }
    
    public boolean canAttackClass(final Class par1Class) {
        return super.canAttackClass(par1Class) && par1Class != this.getClass();
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        final int ammount = 2 + (int)(this.getMonsterDificulty() * this.getMonsterDificulty()) / 2;
        this.entityDropItem(new ItemStack(ChocolateQuest.material, ammount, this.getDropMaterial()), 0.2f);
    }
    
    protected int getDropMaterial() {
        return 5;
    }
}
