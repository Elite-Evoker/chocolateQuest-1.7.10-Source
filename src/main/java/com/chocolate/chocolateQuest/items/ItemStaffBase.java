package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.creativetab.CreativeTabs;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import com.chocolate.chocolateQuest.magic.SpellBase;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.magic.Awakements;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.API.ICooldownTracker;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import net.minecraft.item.Item;

public class ItemStaffBase extends Item implements ILoadableGun, IRangedWeapon, ICooldownTracker
{
    int cooldown;
    Elements element;
    CoolDownTracker cachedTracker;
    
    public ItemStaffBase() {
        this.cooldown = 10;
        this.setMaxStackSize(1);
    }
    
    public ItemStaffBase(final Elements element) {
        this();
        this.element = element;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.getUnlocalizedName().replace("item.", ""));
    }
    
    public void addInformation(final ItemStack is, final EntityPlayer player, final List list, final boolean par4) {
        super.addInformation(is, player, list, par4);
        for (final Awakements a : Awakements.awekements) {
            if (Awakements.hasEnchant(is, a)) {
                list.add(a.getDescription(is));
            }
        }
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking()) {
            final ItemStack[] ammo = InventoryBag.getCargo(itemstack);
            boolean isEmpty = true;
            for (int i = 0; i < ammo.length; ++i) {
                if (ammo[i] != null) {
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                final ItemStack[] ammoNew = InventoryBag.getCargo(itemstack);
                int last = 0;
                final ItemStack lastItem = ammo[0];
                for (int j = 1; j < ammo.length; ++j) {
                    if (ammo[j] != null) {
                        ammoNew[last] = ammo[j];
                        ++last;
                    }
                }
                ammoNew[last] = lastItem;
                InventoryBag.saveCargo(itemstack, ammoNew);
            }
            else {
                entityPlayer.openGui((Object)ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
            }
            return itemstack;
        }
        final ItemStack[] cargo = InventoryBag.getCargo(itemstack);
        if (cargo[0] != null && cargo[0].stackTagCompound == null) {
            final SpellBase spell = this.getSpell(itemstack);
            spell.onCastStart((EntityLivingBase)entityPlayer, this.element, itemstack);
            entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return super.onItemRightClick(itemstack, world, entityPlayer);
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer, int useTime) {
        useTime = this.getMaxItemUseDuration(itemstack) - useTime;
        useTime = Math.min(useTime + 1, 60);
        final SpellBase spell = this.getSpell(itemstack);
        if (spell != null && spell.isProjectile()) {
            spell.onShoot((EntityLivingBase)entityPlayer, this.getElement(itemstack), itemstack, useTime);
            final ItemStack[] spellsStack = InventoryBag.getCargo(itemstack);
            if (spellsStack[0].stackTagCompound == null) {
                spellsStack[0].stackTagCompound = new NBTTagCompound();
            }
            spellsStack[0].stackTagCompound.setInteger("cd", spell.getCoolDown());
            InventoryBag.saveCargo(itemstack, spellsStack);
        }
    }
    
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            if (player.isUsingItem() && player.getItemInUse() == itemStack) {
                final SpellBase spell = this.getSpell(itemStack);
                if (spell != null && spell.shouldUpdate()) {
                    spell.onUpdate((EntityLivingBase)entity, this.getElement(itemStack), itemStack, 30);
                }
            }
            else {
                final NBTTagCompound tag = itemStack.stackTagCompound;
                if (tag != null) {
                    final ItemStack[] cargo = InventoryBag.getCargo(itemStack);
                    for (int i = 0; i < cargo.length; ++i) {
                        if (cargo[i] != null && cargo[i].stackTagCompound != null) {
                            final int cd = cargo[i].stackTagCompound.getInteger("cd");
                            if (cd > 0) {
                                cargo[i].stackTagCompound.setInteger("cd", cd - 1);
                            }
                            else {
                                cargo[i].stackTagCompound = null;
                            }
                        }
                    }
                    InventoryBag.saveCargo(itemStack, cargo);
                    if (cargo[0] != null) {
                        if (cargo[0].stackTagCompound != null) {
                            final int cd2 = cargo[0].stackTagCompound.getInteger("cd");
                            if (cd2 > 0) {
                                itemStack.setMetadata(cd2);
                            }
                            else {
                                itemStack.setMetadata(-1);
                            }
                        }
                        else {
                            itemStack.setMetadata(-1);
                        }
                    }
                }
            }
        }
        else {
            this.onUpdateEntity(itemStack, world, entity, par4, par5);
        }
        super.onUpdate(itemStack, world, entity, par4, par5);
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    public boolean isDamageable() {
        return true;
    }
    
    public int getMaxDurability() {
        return 100;
    }
    
    public int getDisplayDamage(final ItemStack stack) {
        final ItemStack[] cargo = InventoryBag.getCargo(stack);
        if (cargo[0] == null) {
            return 0;
        }
        if (cargo[0].stackTagCompound != null) {
            final SpellBase spell = this.getSpell(stack);
            final int damage = cargo[0].stackTagCompound.getInteger("cd");
            final int maxCD = spell.getCoolDown();
            return damage * 100 / maxCD;
        }
        return 100;
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        return BDHelper.StringColor(this.element.stringColor) + super.getItemStackDisplayName(itemstack) + BDHelper.StringColor("r");
    }
    
    public Entity getTarget(final EntityPlayer ep, final World world) {
        return HelperPlayer.getTarget((EntityLivingBase)ep, world, 30.0);
    }
    
    public boolean isFull3D() {
        return true;
    }
    
    public CreativeTabs getCreativeTab() {
        return ChocolateQuest.tabItems;
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.rare;
    }
    
    public SpellBase getSpell(final ItemStack is) {
        final ItemStack[] ammo = InventoryBag.getCargo(is);
        if (ammo[0] == null) {
            return null;
        }
        return SpellBase.getSpellByID(ammo[0].getMetadata());
    }
    
    public Elements getElement(final ItemStack is) {
        if (this.element != null) {
            return this.element;
        }
        return Elements.light;
    }
    
    public int getAmmoLoaderStackSize(final ItemStack is) {
        return 1;
    }
    
    public int getAmmoLoaderAmmount(final ItemStack is) {
        return 4;
    }
    
    public boolean isValidAmmo(final ItemStack is) {
        return is.getItem() == ChocolateQuest.spell;
    }
    
    public int getStackIcon(final ItemStack is) {
        return 70;
    }
    
    public float getRange(final EntityLivingBase shooter, final ItemStack is) {
        int maxRange = 0;
        if (this.cachedTracker != null && this.cachedTracker.castingSpell != null) {
            maxRange = this.cachedTracker.castingSpell.getRange(is);
        }
        else {
            final ItemStack[] spells = InventoryBag.getCargo(is);
            for (int i = 0; i < spells.length; ++i) {
                if (spells[i] != null) {
                    maxRange = Math.max(maxRange, SpellBase.getSpellByID(spells[i].getMetadata()).getRange(is));
                }
            }
        }
        return (float)(maxRange * maxRange);
    }
    
    public int getCooldown(final EntityLivingBase shooter, final ItemStack is) {
        return 10;
    }
    
    public void shootFromEntity(final EntityLivingBase shooter, final ItemStack is, final int angle, final Entity target) {
        if (this.cachedTracker.castingSpell != null) {
            shooter.rotationYaw = shooter.rotationYawHead;
            this.cachedTracker.castingSpell.onShoot(shooter, this.getElement(is), is, 5);
        }
        this.cachedTracker.castingSpell = null;
    }
    
    public boolean canBeUsedByEntity(final Entity entity) {
        return true;
    }
    
    public boolean isMeleeWeapon(final EntityLivingBase shooter, final ItemStack is) {
        return false;
    }
    
    public boolean shouldUpdate(final EntityLivingBase shooter) {
        return true;
    }
    
    public int startAiming(final ItemStack is, final EntityLivingBase shooter, final Entity target) {
        for (int i = 0; i < this.cachedTracker.spells.length; ++i) {
            if (this.cachedTracker.cooldowns[i] == 0) {
                final SpellBase spell = this.cachedTracker.spells[i];
                if (spell != null) {
                    final double dist = shooter.getDistanceSqToEntity(target);
                    if (dist < spell.getRange(is) * spell.getRange(is)) {
                        this.cachedTracker.castingSpell = spell;
                        final int[] cooldowns = this.cachedTracker.cooldowns;
                        final int n = i;
                        cooldowns[n] += spell.getCoolDown() * 2 + 10;
                        spell.onCastStart(shooter, this.getElement(is), is);
                        return spell.getCastingTime();
                    }
                }
            }
        }
        return -1;
    }
    
    private void onUpdateEntity(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (this.cachedTracker != null) {
            final SpellBase spell = this.cachedTracker.castingSpell;
            if (spell != null && spell.shouldUpdate()) {
                spell.onUpdate((EntityLivingBase)entity, this.getElement(itemStack), itemStack, par4);
            }
        }
    }
    
    public Object getCooldownTracker(final ItemStack is, final Entity entity) {
        return new CoolDownTracker(is);
    }
    
    public void onUpdateCooldown(final ItemStack is, final Entity entity, final Object tracker) {
        (this.cachedTracker = (CoolDownTracker)tracker).onUpdate();
    }
}
