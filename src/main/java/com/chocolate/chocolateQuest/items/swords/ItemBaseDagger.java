package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.util.DamageSource;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.FoodStats;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class ItemBaseDagger extends ItemBDSword
{
    String texture;
    double speedModifier;
    
    public ItemBaseDagger(final Item.ToolMaterial mat, final int baseDamage) {
        super(mat, (float)baseDamage);
        this.speedModifier = 0.05;
    }
    
    public ItemBaseDagger(final Item.ToolMaterial mat, final String texture) {
        this(mat, 2);
        this.texture = texture;
    }
    
    public ItemBaseDagger(final Item.ToolMaterial mat, final String texture, final int baseDamage, final float elementModifier) {
        this(mat, baseDamage);
        this.texture = texture;
        this.elementModifier = elementModifier;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.onGround && !entityPlayer.isSwingInProgress) {
            final FoodStats food = entityPlayer.getFoodStats();
            if (food.getFoodLevel() > 2) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    float exhaustion = 4.0f;
                    final int staminaSaving = Awakements.getEnchantLevel(itemStack, Awakements.dodgeStamina);
                    if (staminaSaving > 0) {
                        exhaustion = (exhaustion - 1.0f) / staminaSaving;
                    }
                    food.addExhaustion(exhaustion);
                }
                float rot = entityPlayer.rotationYawHead * 3.1416f / 180.0f;
                double mx = -Math.sin(rot);
                double mz = Math.cos(rot);
                entityPlayer.motionX += mx * entityPlayer.moveForward;
                entityPlayer.motionZ += mz * entityPlayer.moveForward;
                rot -= (float)1.57;
                mx = -Math.sin(rot);
                mz = Math.cos(rot);
                entityPlayer.motionX += mx * entityPlayer.moveStrafing;
                entityPlayer.motionZ += mz * entityPlayer.moveStrafing;
                entityPlayer.motionY = 0.2;
            }
        }
        return super.onItemRightClick(itemStack, world, entityPlayer);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.texture);
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = super.getItemAttributeModifiers();
        multimap.put((Object)SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemBaseDagger.itemModifierUUID, "Weapon modifier", this.speedModifier, 2));
        return multimap;
    }
    
    @Override
    public boolean hitEntity(final ItemStack par1ItemStack, final EntityLivingBase target, final EntityLivingBase entity) {
        double angle;
        for (angle = entity.rotationYaw - target.rotationYaw; angle > 360.0; angle -= 360.0) {}
        while (angle < 0.0) {
            angle += 360.0;
        }
        angle = Math.abs(angle - 180.0);
        if (angle > 130.0) {
            if (!entity.worldObj.isRemote) {
                final PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround((byte)1, target.posX, target.posY + 1.0 + ItemBaseDagger.itemRand.nextDouble(), target.posZ);
                ChocolateQuest.channel.sendToAllAround((Entity)entity, (IMessage)packet, 64);
            }
            if (entity.worldObj.isRemote) {
                for (int i = 0; i < 5; ++i) {
                    entity.worldObj.spawnParticle("crit", target.posX, target.posY + 1.0 + ItemBaseDagger.itemRand.nextDouble(), target.posZ, ItemBaseDagger.itemRand.nextDouble() - 0.5, -0.5 + ItemBaseDagger.itemRand.nextDouble(), ItemBaseDagger.itemRand.nextDouble() - 0.5);
                }
            }
            final float damage = (float)entity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            final int awkModifier = Awakements.getEnchantLevel(par1ItemStack, Awakements.backStab);
            final float backstabModifier = 2.5f + 0.4f * awkModifier;
            target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), damage * backstabModifier);
        }
        return super.hitEntity(par1ItemStack, target, entity);
    }
    
    private float getBackStabModifier() {
        return 3.0f;
    }
}
