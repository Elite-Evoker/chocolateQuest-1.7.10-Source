package com.chocolate.chocolateQuest.items.swords;

import java.util.Iterator;
import java.util.List;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.EntityLiving;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import net.minecraft.item.EnumRarity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import com.chocolate.chocolateQuest.API.IRangedWeapon;

public class ItemSpearFire extends ItemBaseSpear implements IRangedWeapon
{
    public ItemSpearFire() {
        super(Item.ToolMaterial.EMERALD, 4.0f);
        this.setMaxDurability(2048);
        this.cooldown = 30;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:spearDwarf");
    }
    
    @Override
    public void doSpecialSkill(final ItemStack itemstack, final World world, final EntityLivingBase shooter) {
        this.shootFromEntity(shooter, itemstack, 0, null);
        itemstack.damageItem(1, shooter);
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    public int getEntityLifespan(final ItemStack itemStack, final World world) {
        return 24000;
    }
    
    @Override
    public float getRange(final EntityLivingBase shooter, final ItemStack is) {
        return 64.0f;
    }
    
    @Override
    public int getCooldown(final EntityLivingBase shooter, final ItemStack is) {
        return this.cooldown + 20;
    }
    
    @Override
    public boolean canBeUsedByEntity(final Entity entity) {
        return true;
    }
    
    @Override
    public boolean isMeleeWeapon(final EntityLivingBase shooter, final ItemStack is) {
        return true;
    }
    
    @Override
    public boolean shouldUpdate(final EntityLivingBase shooter) {
        return false;
    }
    
    @Override
    public void shootFromEntity(final EntityLivingBase shooter, final ItemStack is, final int angle, final Entity target) {
        final World world = shooter.worldObj;
        world.playSoundEffect((double)(int)shooter.posX, (double)(int)shooter.posY, (double)(int)shooter.posZ, "fire.fire", 4.0f, (1.0f + (ItemSpearFire.itemRand.nextFloat() - ItemSpearFire.itemRand.nextFloat()) * 0.2f) * 0.7f);
        float x = (float)(-Math.sin(Math.toRadians(shooter.rotationYaw)));
        float z = (float)Math.cos(Math.toRadians(shooter.rotationYaw));
        final float y = (float)(-Math.sin(Math.toRadians(shooter.rotationPitch)));
        x *= 1.0f - Math.abs(y);
        z *= 1.0f - Math.abs(y);
        if (!world.isRemote) {
            final PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround((byte)2, shooter.getEntityId(), 0.0, 0.0);
            ChocolateQuest.channel.sendToAllAround((Entity)shooter, (IMessage)packet, 64);
        }
        final int dist = 15;
        final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)shooter, shooter.boundingBox.addCoord(shooter.getLookVec().xCoord * dist, shooter.getLookVec().yCoord * dist, shooter.getLookVec().zCoord * dist).expand(1.0, 1.0, 1.0));
        for (final Entity e : list) {
            if (e instanceof EntityLiving) {
                final double rotDiff = Math.abs(BDHelper.getAngleBetweenEntities((Entity)shooter, e));
                double rot = rotDiff - Math.abs(MathHelper.wrapAngleTo180_double((double)shooter.rotationYaw));
                rot = Math.abs(rot);
                if (rot >= 10.0) {
                    continue;
                }
                e.setFire(6);
                e.attackEntityFrom(HelperDamageSource.causeFireDamage((Entity)shooter), 4.0f);
            }
        }
    }
    
    @Override
    public int startAiming(final ItemStack is, final EntityLivingBase shooter, final Entity target) {
        return 80;
    }
}
