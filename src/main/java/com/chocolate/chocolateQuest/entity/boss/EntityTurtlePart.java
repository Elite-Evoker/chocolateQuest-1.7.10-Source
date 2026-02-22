package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTurtlePart extends EntityPart
{
    int maxHealth;
    private final int LIFEWATCHER = 10;
    
    public EntityTurtlePart(final World par1World) {
        super(par1World);
        this.maxHealth = 100;
        this.isImmuneToFire = true;
        this.setHeartsLife(100);
        this.noClip = true;
    }
    
    public EntityTurtlePart(final World world, final int partID, final float rotationYawOffset, final float distanceToMainBody) {
        this(world);
        this.rotationYawOffset = rotationYawOffset;
        this.distanceToMainBody = distanceToMainBody;
        this.partID = partID;
    }
    
    public EntityTurtlePart(final World world, final int partID, final float rotationYawOffset, final float distanceToMainBody, final float heightOffset) {
        this(world, partID, rotationYawOffset, distanceToMainBody);
        this.heightOffset = heightOffset;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        this.setHeartsLife((int)(this.getHeartsLife() - par2));
        if (this.getHeartsLife() <= 0 && this.worldObj.isRemote) {
            this.setDead();
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    public void onUpdate() {
        if (this.getHeartsLife() < 0) {
            this.setDead();
        }
        super.onUpdate();
    }
    
    public int getMaxHealth() {
        return this.isHead() ? 300 : 100;
    }
    
    public void setHead() {
        this.partID = 0;
        this.setHeartsLife(300);
    }
    
    public boolean isHead() {
        return this.partID == 0;
    }
    
    public int getHeartsLife() {
        return this.dataWatcher.getWatchableObjectShort(10);
    }
    
    public void setHeartsLife(final int heartsLife) {
        this.dataWatcher.updateObject(10, (Object)(short)heartsLife);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(10, (Object)100);
    }
    
    @Override
    public void readSpawnData(final ByteBuf additionalData) {
        super.readSpawnData(additionalData);
    }
    
    @Override
    public void writeSpawnData(final ByteBuf buffer) {
        super.writeSpawnData(buffer);
    }
    
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        if (entity.isEntityEqual((Entity)this)) {
            return null;
        }
        return entity.boundingBox;
    }
}
