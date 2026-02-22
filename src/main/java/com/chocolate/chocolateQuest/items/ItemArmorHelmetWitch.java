package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumRarity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;

public class ItemArmorHelmetWitch extends ItemArmorBase
{
    public ItemArmorHelmetWitch() {
        super(ItemArmor.ArmorMaterial.CLOTH, 0);
        this.setMaxDurability(555);
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        return "textures/entity/witch.png";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        return ClientProxy.witchHat;
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.rare;
    }
}
