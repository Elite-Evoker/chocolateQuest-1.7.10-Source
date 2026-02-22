package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimePart;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

public class ItemArmorSlime extends ItemArmorBase
{
    int type;
    String name;
    AttributeModifier health;
    AttributeModifier knockBack;
    
    public ItemArmorSlime(final int type, final String name) {
        super(ItemArmorSlime.TURTLE, type);
        this.type = type;
        this.name = name;
        this.health = new AttributeModifier("SlimeHmodifier" + type, 4.0, 0);
        this.knockBack = new AttributeModifier("SlimeKBmodifier" + type, -0.25, 0);
        this.setEpic();
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = (Multimap)HashMultimap.create();
        multimap.put((Object)SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), (Object)this.knockBack);
        multimap.put((Object)SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), (Object)this.health);
        return multimap;
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        if (slot == 2) {
            return "chocolatequest:textures/armor/armorSlime_1.png";
        }
        return "chocolatequest:textures/armor/armorSlime.png";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        return ClientProxy.slimeArmor[armorSlot];
    }
    
    @Override
    public void onHit(final LivingHurtEvent event, final ItemStack is, final EntityLivingBase entity) {
        if (!entity.worldObj.isRemote && entity.hurtTime == 0 && this.armorType == 1 && this.isFullSet(entity, is)) {
            final float ammount = event.ammount * (1.0f / entity.getTotalArmorValue() * 4.0f);
            if (event.ammount > 0.0f && (ammount >= 2.0f || ItemArmorSlime.itemRand.nextInt(2) == 0)) {
                final int size = (int)Math.min(2.0, Math.max(1.0, ammount * 0.8));
                final EntitySlimePart part = new EntitySlimePart(entity.worldObj, entity, size);
                part.setPosition(entity.posX, entity.posY + 1.0, entity.posZ);
                part.motionX = ItemArmorSlime.itemRand.nextGaussian();
                part.motionZ = ItemArmorSlime.itemRand.nextGaussian();
                entity.worldObj.spawnEntityInWorld((Entity)part);
            }
        }
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return (itemMaterial.getItem() == ChocolateQuest.material && itemMaterial.getMetadata() == 3) || super.getIsRepairable(itemToRepair, itemMaterial);
    }
}
