package com.chocolate.chocolateQuest.items.gun;

import net.minecraft.item.EnumAction;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class ItemGolemFramethrower extends ItemGolemWeapon
{
    @Override
    public void shootFromGolem(final EntityLivingBase entity, final ItemStack is, final int angle, final Entity target) {
        final double armDist = 1.0;
        final float rotationYaw = (float)MathHelper.wrapAngleTo180_double((double)entity.rotationYawHead);
        this.shootFlames(entity, is, angle, target, 1.6, 1.0, rotationYaw);
    }
    
    public void shootFlames(final EntityLivingBase entity, final ItemStack is, final int angle, final Entity target, final double yOff, final double armDist, final float rotationYaw) {
        final World world = entity.worldObj;
        final double posX = entity.posX - Math.sin(Math.toRadians(rotationYaw + angle)) * armDist;
        final double posY = entity.posY + yOff;
        final double posZ = entity.posZ + Math.cos(Math.toRadians(rotationYaw + angle)) * armDist;
        float x = (float)(-Math.sin(Math.toRadians(rotationYaw)));
        float z = (float)Math.cos(Math.toRadians(rotationYaw));
        final double y = -Math.sin(Math.toRadians(entity.rotationPitch));
        x *= (float)(1.0 - Math.abs(y));
        z *= (float)(1.0 - Math.abs(y));
        if (world.isRemote) {
            for (int i = 0; i < 8; ++i) {
                EffectManager.spawnParticle(3, world, posX, posY, posZ, (x + ItemGolemFramethrower.itemRand.nextFloat() - 0.5) / 3.0, (y + ItemGolemFramethrower.itemRand.nextFloat() - 0.5) / 8.0, (z + ItemGolemFramethrower.itemRand.nextFloat() - 0.5) / 3.0);
            }
        }
        else {
            final int dist = 5;
            final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)entity, entity.boundingBox.addCoord(entity.getLookVec().xCoord * dist, entity.getLookVec().yCoord * dist, entity.getLookVec().zCoord * dist).expand(1.0, 1.0, 1.0));
            for (final Entity e : list) {
                if (e instanceof EntityLivingBase && !e.isWet() && e != entity.riddenByEntity) {
                    final double d = posX - e.posX;
                    final double d2 = posZ - e.posZ;
                    double rotDiff = Math.atan2(d, d2);
                    rotDiff = rotDiff * 180.0 / 3.141592;
                    rotDiff = -MathHelper.wrapAngleTo180_double(rotDiff - 180.0);
                    rotDiff -= rotationYaw;
                    if (Math.abs(rotDiff) >= 30.0) {
                        continue;
                    }
                    e.setFire(2);
                    e.attackEntityFrom(HelperDamageSource.causeFireDamage((Entity)entity), 1.0f);
                }
            }
        }
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
    
    @Override
    public void onUpdate(final ItemStack is, final World world, final Entity entity, final int par4, final boolean par5) {
        if (entity instanceof EntityGolemMecha) {
            this.shootFromGolem((EntityLivingBase)entity, is, par4, null);
        }
        else if (entity instanceof EntityHumanBase) {
            this.shootFromGolem((EntityLivingBase)entity, is, par4, null);
        }
        else if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            if (player.isUsingItem() && player.getItemInUse() == is) {
                final float rotationYaw = (float)MathHelper.wrapAngleTo180_double((double)player.rotationYawHead);
                this.shootFlames((EntityLivingBase)entity, is, par4, null, -0.3, 0.0, rotationYaw);
            }
        }
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    @Override
    public boolean isDamageable() {
        return false;
    }
    
    @Override
    public float getRange(final EntityLivingBase shooter, final ItemStack is) {
        return 36.0f;
    }
    
    @Override
    public int getCooldown(final EntityLivingBase shooter, final ItemStack is) {
        return 0;
    }
    
    @Override
    public boolean shouldUpdate(final EntityLivingBase shooter) {
        return true;
    }
}
