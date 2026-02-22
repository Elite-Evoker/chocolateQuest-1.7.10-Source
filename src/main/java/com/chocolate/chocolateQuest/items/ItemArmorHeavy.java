package com.chocolate.chocolateQuest.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.SharedMonsterAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;

public class ItemArmorHeavy extends ItemArmorBase
{
    int type;
    Item repairItem;
    AttributeModifier speed;
    AttributeModifier knockBack;
    
    public ItemArmorHeavy(final int type, final String name, final Item repairItem) {
        this(type, name, repairItem, ItemArmorHeavy.DRAGON);
    }
    
    public ItemArmorHeavy(final int type, final String name, final Item repairItem, final ItemArmor.ArmorMaterial mat) {
        super(mat, type, name);
        this.repairItem = Items.diamond;
        this.speed = new AttributeModifier("HA Speed modifier", -0.07, 2);
        this.knockBack = new AttributeModifier("HA Speed modifier", 0.25, 2);
        this.type = type;
        this.repairItem = repairItem;
        this.setMaxDurability(mat.getDurability(type) + 1500);
        this.speed = new AttributeModifier("HA Speed modifier" + type, -0.06, 2);
        this.knockBack = new AttributeModifier("HA Speed modifier", 0.25, 0);
        this.isColoreable = (type == 1);
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = (Multimap)HashMultimap.create();
        multimap.put((Object)SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), (Object)this.knockBack);
        multimap.put((Object)SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), (Object)this.speed);
        return multimap;
    }
    
    @Override
    public void onUpdateEquiped(final ItemStack par1ItemStack, final World world, final EntityLivingBase entity) {
        if (!entity.onGround) {
            entity.jumpMovementFactor = (float)Math.max(0.015, entity.jumpMovementFactor - 0.01f);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        if (armorSlot == 1) {
            return ClientProxy.heavyArmor.reset();
        }
        return null;
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return itemMaterial.getItem() == this.repairItem || super.getIsRepairable(itemToRepair, itemMaterial);
    }
}
