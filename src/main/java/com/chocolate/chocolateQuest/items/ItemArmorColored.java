package com.chocolate.chocolateQuest.items;

import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.Item;

public class ItemArmorColored extends ItemArmorBase
{
    Item repairItem;
    
    public ItemArmorColored(final int type, final String name, final Item repairItem, final int defaultColor) {
        this(type, name, repairItem, ItemArmorColored.DRAGON, defaultColor);
    }
    
    public ItemArmorColored(final ItemArmor.ArmorMaterial material, final int renderIndex, final String name, final int defaultColor) {
        super(material, renderIndex, name);
        this.repairItem = Items.diamond;
        this.name = name;
        this.defaultColor = defaultColor;
        this.isColoreable = true;
    }
    
    public ItemArmorColored(final int type, final String name, final Item repairItem, final ItemArmor.ArmorMaterial mat, final int defaultColor) {
        this(mat, type, name, defaultColor);
        this.repairItem = repairItem;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        return ClientProxy.coloredArmor[armorSlot].reset();
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        if (slot == 2) {
            return "chocolatequest:textures/armor/armor_" + this.name + "_2_overlay.png";
        }
        return "chocolatequest:textures/armor/armor_" + this.name + "_1_overlay.png";
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return itemMaterial.getItem() == this.repairItem || super.getIsRepairable(itemToRepair, itemMaterial);
    }
}
