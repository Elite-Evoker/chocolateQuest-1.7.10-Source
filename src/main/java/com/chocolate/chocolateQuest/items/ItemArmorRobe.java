package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumRarity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;

public class ItemArmorRobe extends ItemArmorBase
{
    public ItemArmorRobe() {
        super(ItemArmor.ArmorMaterial.CLOTH, 1);
        this.setMaxDurability(1850);
        this.isColoreable = true;
        this.defaultColor = 16777215;
    }
    
    @Override
    public void onUpdateEquiped(final ItemStack par1ItemStack, final World world, final EntityLivingBase entity) {
        if (entity instanceof EntityPlayer && entity.ticksExisted % 100 == 0) {
            final EntityPlayer e = (EntityPlayer)entity;
            e.getFoodStats().addStats(1, 6.0f);
        }
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        return "chocolatequest:textures/armor/mageRobe.png";
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return super.getIsRepairable(itemToRepair, itemMaterial);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        return ClientProxy.mageArmor.reset();
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.uncommon;
    }
}
