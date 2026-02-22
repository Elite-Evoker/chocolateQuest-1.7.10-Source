package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraft.item.ItemArmor;

public class ItemArmorBootsCloud extends ItemArmorBase
{
    public ItemArmorBootsCloud() {
        super(ItemArmor.ArmorMaterial.CLOTH, 3);
        this.setMaxDurability(450);
    }
    
    @Override
    public void onHit(final LivingHurtEvent event, final ItemStack is, final EntityLivingBase entity) {
        if (event.source == DamageSource.fall) {
            event.setCanceled(true);
        }
    }
    
    public ItemArmor.ArmorMaterial getArmorMaterial() {
        return ItemArmor.ArmorMaterial.CLOTH;
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = (Multimap)HashMultimap.create();
        multimap.put((Object)SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemArmorBootsCloud.itemModifierUUID, "Boots modifier", 0.15, 2));
        return multimap;
    }
    
    @Override
    public void onUpdateEquiped(final ItemStack par1ItemStack, final World world, final EntityLivingBase entity) {
        final boolean onGround = false;
        if (entity.fallDistance >= 3.0f) {
            entity.fallDistance = 0.0f;
            if (world.isRemote) {
                for (int i = 0; i < 3; ++i) {
                    world.spawnParticle("cloud", entity.posX, entity.posY - 2.0, entity.posZ, (double)((ItemArmorBootsCloud.itemRand.nextFloat() - 0.5f) / 2.0f), -0.5, (double)((ItemArmorBootsCloud.itemRand.nextFloat() - 0.5f) / 2.0f));
                }
            }
        }
        if (entity.isSprinting() && world.isRemote) {
            world.spawnParticle("cloud", entity.posX, entity.posY - 1.5, entity.posZ, (double)((ItemArmorBootsCloud.itemRand.nextFloat() - 0.5f) / 2.0f), 0.1, (double)((ItemArmorBootsCloud.itemRand.nextFloat() - 0.5f) / 2.0f));
        }
        if (!entity.onGround && entity instanceof EntityPlayer) {
            entity.jumpMovementFactor += 0.03f;
        }
        if (entity.isCollidedHorizontally) {
            entity.stepHeight = 1.0f;
        }
        else {
            entity.stepHeight = 0.5f;
        }
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        return "chocolatequest:textures/armor/cloud_1.png";
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return false;
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.rare;
    }
}
