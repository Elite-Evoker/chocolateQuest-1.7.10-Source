package com.chocolate.chocolateQuest.entity.boss;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityPartRidable extends EntityPart
{
    public EntityPartRidable(final World par1World) {
        super(par1World);
    }
    
    public EntityPartRidable(final World world, final EntityBaseBoss entity, final int partID, final float rotationYawOffset, final float distanceToMainBody, final float heightOffset) {
        super(world, entity, partID, rotationYawOffset, distanceToMainBody, heightOffset);
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return entity.boundingBox;
    }
    
    public void applyEntityCollision(final Entity entity) {
        super.applyEntityCollision(entity);
    }
    
    public void onCollideWithPlayer(final EntityPlayer par1EntityPlayer) {
        super.onCollideWithPlayer(par1EntityPlayer);
    }
}
