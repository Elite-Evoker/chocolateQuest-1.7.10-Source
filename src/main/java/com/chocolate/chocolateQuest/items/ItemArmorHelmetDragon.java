package com.chocolate.chocolateQuest.items;

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
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class ItemArmorHelmetDragon extends ItemArmorBase
{
    AttributeModifier attackDamage;
    AttributeModifier maxHealth;
    
    public ItemArmorHelmetDragon() {
        super(ItemArmorHelmetDragon.DRAGON, 0);
        this.attackDamage = new AttributeModifier("ArmorDamage0", 1.0, 1);
        this.maxHealth = new AttributeModifier("ArmorHealth0", 10.0, 0);
        this.setMaxDurability(2850);
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = (Multimap)HashMultimap.create();
        multimap.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)this.attackDamage);
        multimap.put((Object)SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), (Object)this.maxHealth);
        return multimap;
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        return "chocolatequest:textures/entity/dragonbd.png";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        return ClientProxy.dragonHead;
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
}
