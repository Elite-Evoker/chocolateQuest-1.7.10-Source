package com.chocolate.chocolateQuest.items;

import net.minecraft.world.World;
import net.minecraft.item.EnumRarity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.item.ItemArmor;

public class ItemArmorKing extends ItemArmorBase
{
    public ItemArmorKing() {
        super(ItemArmor.ArmorMaterial.DIAMOND, 1);
        this.setMaxDurability(8588);
        this.isColoreable = true;
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = (Multimap)HashMultimap.create();
        multimap.put((Object)SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), (Object)new AttributeModifier("KingArmorModifier", 0.6, 0));
        return multimap;
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        return "chocolatequest:textures/armor/king_1.png";
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return super.getIsRepairable(itemToRepair, itemMaterial);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        return ClientProxy.kingArmor.reset();
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    public int getEntityLifespan(final ItemStack itemStack, final World world) {
        return 24000;
    }
}
