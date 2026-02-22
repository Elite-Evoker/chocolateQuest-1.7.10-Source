package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.SharedMonsterAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class ItemArmorSpikedGloves extends ItemArmorBase
{
    AttributeModifier strength;
    
    public ItemArmorSpikedGloves(final int id) {
        super(ItemArmor.ArmorMaterial.CLOTH, 1);
        this.strength = new AttributeModifier("ArmorStrenght1", 6.0, 0);
        this.setMaxDurability(450);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:spikedGloves");
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = (Multimap)HashMultimap.create();
        multimap.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)this.strength);
        return multimap;
    }
    
    @Override
    public void onUpdateEquiped(final ItemStack par1ItemStack, final World world, final EntityLivingBase entity) {
        final boolean isCollidedVertically = false;
        if ((entity.isCollidedHorizontally || isCollidedVertically) && !entity.isSneaking()) {
            entity.motionY = 0.0;
            if (entity.moveForward > 0.0f) {
                entity.motionY = 0.2;
                if (!entity.isSwingInProgress) {
                    entity.swingItem();
                }
            }
            entity.onGround = true;
        }
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        return "chocolatequest:textures/entity/cloud_1.png";
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack item) {
        final Item itemMaterial = item.getItem();
        return Items.diamond == itemMaterial || Items.iron_ingot == itemMaterial || super.getIsRepairable(itemToRepair, item);
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.rare;
    }
}
