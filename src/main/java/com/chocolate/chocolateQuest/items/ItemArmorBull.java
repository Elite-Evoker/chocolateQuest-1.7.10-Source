package com.chocolate.chocolateQuest.items;

import java.util.List;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.item.EnumRarity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.SharedMonsterAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class ItemArmorBull extends ItemArmorBase
{
    int type;
    String name;
    AttributeModifier strength;
    
    public ItemArmorBull(final int type, final String name) {
        super(ItemArmor.ArmorMaterial.DIAMOND, type);
        this.strength = new AttributeModifier("Armor0", 2.0, 0);
        this.type = type;
        this.name = name;
        this.setMaxDurability(ItemArmor.ArmorMaterial.DIAMOND.getDurability(type) + 100);
        this.strength = new AttributeModifier("BullAmodifier" + type, 1.0, 0);
        this.setEpic();
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = (Multimap)HashMultimap.create();
        multimap.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)this.strength);
        return multimap;
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        if (slot == 2) {
            return "chocolatequest:textures/armor/bull_2.png";
        }
        return "chocolatequest:textures/armor/bull_1.png";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        if (armorSlot == 0) {
            return ClientProxy.bullHead;
        }
        return super.getArmorModel(entityLiving, itemStack, armorSlot);
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    @Override
    public void onUpdateEquiped(final ItemStack par1ItemStack, final World world, final EntityLivingBase entityliving) {
        if (this.armorType == 1 && entityliving.isSprinting() && this.isFullSet(entityliving, par1ItemStack)) {
            final List list = world.getEntitiesWithinAABBExcludingEntity((Entity)entityliving, entityliving.boundingBox.expand(1.1, 1.1, 1.1));
            for (int k2 = 0; k2 < list.size(); ++k2) {
                final Entity entity = list.get(k2);
                if (entity instanceof EntityLivingBase && !entityliving.isOnSameTeam((EntityLivingBase)entity)) {
                    entity.attackEntityFrom((DamageSource)new EntityDamageSource(DamageSource.generic.damageType, (Entity)entityliving), 4.0f);
                }
            }
        }
    }
}
