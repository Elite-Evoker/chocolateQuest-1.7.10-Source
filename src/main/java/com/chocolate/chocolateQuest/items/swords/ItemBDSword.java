package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraft.util.DamageSource;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.magic.Elements;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.SharedMonsterAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemSword;

public class ItemBDSword extends ItemSword
{
    protected float weaponAttackDamage;
    protected AttributeModifier damageModifier;
    protected float elementModifier;
    protected float cachedDamage;
    
    public ItemBDSword(final Item.ToolMaterial material) {
        this(material, 4.0f);
    }
    
    public ItemBDSword(final Item.ToolMaterial material, final float baseDamage) {
        super(material);
        this.elementModifier = 1.0f;
        this.cachedDamage = 0.0f;
        this.weaponAttackDamage = baseDamage + material.getDamageVsEntity();
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = (Multimap)HashMultimap.create();
        this.damageModifier = new AttributeModifier(ItemBDSword.itemModifierUUID, "Weapon modifier", (double)this.weaponAttackDamage, 0);
        multimap.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)this.damageModifier);
        return multimap;
    }
    
    public float getWeaponDamage() {
        return this.weaponAttackDamage;
    }
    
    public void addInformation(final ItemStack is, final EntityPlayer player, final List list, final boolean par4) {
        super.addInformation(is, player, list, par4);
        for (final Elements element : Elements.values()) {
            final float value = this.getElementDamage(is, element);
            if (value > 0.0f) {
                list.add(BDHelper.StringColor(element.getStringColor()) + this.getElementString(element, value));
            }
        }
        for (final Awakements a : Awakements.awekements) {
            if (Awakements.hasEnchant(is, a)) {
                list.add(a.getDescription(is));
            }
        }
    }
    
    protected String getElementString(final Elements element, final float value) {
        return "+" + this.translateFloat(value) + " " + element.getTranslatedName();
    }
    
    public String translateFloat(final float d) {
        String value = Double.toString(d);
        int index = value.indexOf(".");
        if (index < 3) {
            index += 2;
        }
        value = value.substring(0, index);
        return value;
    }
    
    public boolean hitEntity(final ItemStack is, final EntityLivingBase target, final EntityLivingBase entity) {
        this.applyEnchantmentHit(is, target, entity);
        return super.hitEntity(is, target, entity);
    }
    
    public void applyEnchantmentHit(final ItemStack is, final EntityLivingBase target, final EntityLivingBase entity) {
        if (target.hurtResistantTime == 20 || target.hurtResistantTime == 0) {
            for (final Elements element : Elements.values()) {
                float damage = this.getElementDamage(is, element);
                if (damage > 0.0f) {
                    target.hurtResistantTime = 0;
                    final DamageSource ds = element.getDamageSource();
                    damage = element.onHitEntity((Entity)entity, (Entity)target, damage);
                    if (target.attackEntityFrom(ds, damage) && !target.worldObj.isRemote) {
                        final byte particle = PacketSpawnParticlesAround.getParticleFromName(element.getParticle());
                        final PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround(particle, target.posX, target.posY + 1.0 + ItemBDSword.itemRand.nextDouble(), target.posZ);
                        ChocolateQuest.channel.sendToAllAround((Entity)target, (IMessage)packet, 64);
                    }
                }
            }
        }
    }
    
    public void attackEntityWithItem(final EntityPlayer player, final Entity e) {
        final AttributeModifier cacheDamageModifier = new AttributeModifier(ItemBDSword.itemModifierUUID, "Weapon modifier", (double)this.cachedDamage, 0);
        player.getEntityAttribute(SharedMonsterAttributes.attackDamage).removeModifier(this.damageModifier);
        player.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(cacheDamageModifier);
        player.attackTargetEntityWithCurrentItem(e);
        player.getEntityAttribute(SharedMonsterAttributes.attackDamage).removeModifier(cacheDamageModifier);
        player.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(this.damageModifier);
    }
    
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (Awakements.hasEnchant(itemStack, Awakements.property)) {
            Awakements.property.onUpdate(entity, itemStack);
        }
        if (Awakements.hasEnchant(itemStack, Awakements.autoRepair)) {
            Awakements.autoRepair.onUpdate(entity, itemStack);
        }
    }
    
    public boolean onEntityItemUpdate(final EntityItem entityItem) {
        Awakements.property.onEntityItemUpdate(entityItem);
        return super.onEntityItemUpdate(entityItem);
    }
    
    public float getPhysicDamage(final ItemStack is) {
        return this.getElementDamage(is, Elements.physic);
    }
    
    public float getFireDamage(final ItemStack is) {
        return this.getElementDamage(is, Elements.fire);
    }
    
    public float getMagicDamage(final ItemStack is) {
        return this.getElementDamage(is, Elements.magic);
    }
    
    public float getBlastDamage(final ItemStack is) {
        return this.getElementDamage(is, Elements.blast);
    }
    
    public void setPhysicDamage(final int i, final ItemStack is) {
        this.setElementValue(is, Elements.physic, i);
    }
    
    public void setFireDamage(final int i, final ItemStack is) {
        this.setElementValue(is, Elements.fire, i);
    }
    
    public void setMagicDamage(final int i, final ItemStack is) {
        this.setElementValue(is, Elements.magic, i);
    }
    
    public void setBlastDamage(final int i, final ItemStack is) {
        this.setElementValue(is, Elements.blast, i);
    }
    
    protected float getElementDamage(final ItemStack is, final Elements element) {
        return this.getElementValue(is, element) * this.elementModifier;
    }
    
    public int getElementValue(final ItemStack is, final Elements element) {
        if (is.stackTagCompound == null) {
            return 0;
        }
        return is.stackTagCompound.getByte(element.getName());
    }
    
    public void setElementValue(final ItemStack is, final Elements element, final int value) {
        if (is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
        }
        is.stackTagCompound.setByte(element.getName(), (byte)value);
    }
}
