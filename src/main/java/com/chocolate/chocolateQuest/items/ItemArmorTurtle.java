package com.chocolate.chocolateQuest.items;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.ChocolateQuest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemArmorTurtle extends ItemArmorBase
{
    int type;
    String name;
    
    public ItemArmorTurtle(final int type, final String name) {
        super(ItemArmorTurtle.TURTLE, type);
        this.type = type;
        this.name = name;
        this.setEpic();
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        if (((ItemArmorTurtle)stack.getItem()).type == 2) {
            return "chocolatequest:textures/armor/turtle_2.png";
        }
        return "chocolatequest:textures/armor/turtle_1.png";
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        if (armorSlot == 1) {
            return ClientProxy.turtleArmorModel;
        }
        if (armorSlot == 0) {
            return ClientProxy.turtleHelmetModel;
        }
        return null;
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return (itemMaterial.getItem() == ChocolateQuest.material && itemMaterial.getMetadata() == 1) || super.getIsRepairable(itemToRepair, itemMaterial);
    }
    
    @Override
    public void onUpdateEquiped(final ItemStack itemStack, final World world, final EntityLivingBase entity) {
        if (entity.ticksExisted % 800 == this.type * 200) {
            entity.heal(1.0f);
            if (world.isRemote) {
                entity.worldObj.spawnParticle("heart", entity.posX + ItemArmorTurtle.itemRand.nextDouble() - 0.5, entity.posY + ItemArmorTurtle.itemRand.nextDouble(), entity.posZ + ItemArmorTurtle.itemRand.nextDouble() - 0.5, 0.0, 1.0, 0.0);
            }
        }
        if (itemStack.stackTagCompound != null) {
            if (itemStack.stackTagCompound.hasKey("CD")) {
                this.setCooldown(itemStack, this.getCoolDown(itemStack) - 1);
            }
            if (itemStack.stackTagCompound.hasKey("ON")) {
                if (entity.getHealth() < entity.getMaxHealth()) {
                    entity.heal(1.0f);
                    if (world.isRemote) {
                        entity.worldObj.spawnParticle("heart", entity.posX + ItemArmorTurtle.itemRand.nextDouble() - 0.5, entity.posY + ItemArmorTurtle.itemRand.nextDouble(), entity.posZ + ItemArmorTurtle.itemRand.nextDouble() - 0.5, 0.0, 1.0, 0.0);
                    }
                }
                else {
                    itemStack.stackTagCompound.removeTag("ON");
                }
            }
        }
    }
    
    @Override
    public void onHit(final LivingHurtEvent event, final ItemStack is, final EntityLivingBase entity) {
        if (this.armorType == 1 && this.isFullSet(entity, is)) {
            float ammount = event.ammount + 4.0f;
            if (!event.source.isUnblockable()) {
                ammount *= 1.0f / entity.getTotalArmorValue() * 4.0f;
            }
            if (entity.getHealth() - ammount <= 0.0f && this.getCoolDown(is) == 0) {
                entity.setHealth(0.1f);
                event.setCanceled(true);
                this.setCooldown(is, 7200);
                is.stackTagCompound.setBoolean("ON", true);
            }
        }
    }
    
    public void setCooldown(final ItemStack is, final int cooldown) {
        if (is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
        }
        if (cooldown == 0) {
            is.stackTagCompound.removeTag("CD");
            return;
        }
        is.stackTagCompound.setInteger("CD", cooldown);
    }
    
    public int getCoolDown(final ItemStack is) {
        if (is.stackTagCompound == null) {
            return 0;
        }
        return is.stackTagCompound.getInteger("CD");
    }
}
