package com.chocolate.chocolateQuest.items;

import net.minecraftforge.common.util.EnumHelper;
import com.chocolate.chocolateQuest.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.magic.Awakements;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor;

public class ItemArmorBase extends ItemArmor
{
    String name;
    boolean isEpic;
    protected static final ItemArmor.ArmorMaterial DRAGON;
    protected static final ItemArmor.ArmorMaterial TURTLE;
    boolean isColoreable;
    int defaultColor;
    
    public ItemArmorBase(final ItemArmor.ArmorMaterial material, final int renderIndex) {
        super(material, 0, renderIndex);
        this.isEpic = false;
        this.isColoreable = false;
        this.defaultColor = 16777215;
    }
    
    public ItemArmorBase(final ItemArmor.ArmorMaterial material, final int renderIndex, final String name) {
        this(material, renderIndex);
        this.name = name;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(this.getIconString());
    }
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        if (slot == 2) {
            return "chocolatequest:textures/armor/" + this.name + "_2.png";
        }
        return "chocolatequest:textures/armor/" + this.name + ".png";
    }
    
    public void addInformation(final ItemStack is, final EntityPlayer player, final List list, final boolean par4) {
        super.addInformation(is, player, list, par4);
        if (this.isEpic()) {
            for (final Awakements a : Awakements.awekements) {
                if (Awakements.hasEnchant(is, a)) {
                    list.add(a.getDescription(is));
                }
            }
        }
    }
    
    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {
        this.onUpdateEquiped(itemStack, world, (EntityLivingBase)player);
        if (this.isEpic) {
            if (Awakements.hasEnchant(itemStack, Awakements.property)) {
                Awakements.property.onUpdate((Entity)player, itemStack);
            }
            if (Awakements.hasEnchant(itemStack, Awakements.autoRepair)) {
                Awakements.autoRepair.onUpdate((Entity)player, itemStack);
            }
        }
    }
    
    public void onUpdateEquiped(final ItemStack itemStack, final World world, final EntityLivingBase entity) {
    }
    
    public int getColor(final ItemStack par1ItemStack) {
        return 16777215;
    }
    
    public boolean requiresMultipleRenderPasses() {
        return false;
    }
    
    public ItemArmorBase setEpic() {
        this.isEpic = true;
        return this;
    }
    
    public boolean isEpic() {
        return this.isEpic;
    }
    
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (this.isEpic) {
            if (Awakements.hasEnchant(itemStack, Awakements.property)) {
                Awakements.property.onUpdate(entity, itemStack);
            }
            if (Awakements.hasEnchant(itemStack, Awakements.autoRepair)) {
                Awakements.autoRepair.onUpdate(entity, itemStack);
            }
        }
    }
    
    public void onHit(final LivingHurtEvent event, final ItemStack is, final EntityLivingBase entity) {
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        return super.getItemStackDisplayName(itemstack);
    }
    
    public boolean isFullSet(final EntityLivingBase e, final ItemStack itemstack) {
        for (int i = 1; i < 5; ++i) {
            final ItemStack is = e.getEquipmentInSlot(i);
            if (is == null) {
                return false;
            }
            if (is.getItem().getClass() != itemstack.getItem().getClass()) {
                return false;
            }
        }
        return true;
    }
    
    public Item setUnlocalizedName(final String name) {
        this.setTextureName("chocolateQuest:" + name);
        return super.setUnlocalizedName(name);
    }
    
    public int getColorFromItemStack(final ItemStack is, final int i) {
        if (this.hasColor(is) && is.stackTagCompound != null) {
            final NBTTagCompound tag = (NBTTagCompound)is.stackTagCompound.getTag("display");
            if (tag.hasKey("color")) {
                return tag.getInteger("color");
            }
        }
        return this.defaultColor;
    }
    
    public boolean hasColor(final ItemStack is) {
        if (!this.isColoreable || is.stackTagCompound == null || !is.stackTagCompound.hasKey("display")) {
            if (!super.hasColor(is)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean hasApron(final ItemStack is) {
        return is.getTagCompound() != null && is.getTagCompound().hasKey("apron");
    }
    
    public int getApron(final ItemStack is) {
        return is.getTagCompound().getInteger("apron");
    }
    
    public boolean hasCape(final ItemStack is) {
        return is.getTagCompound() != null && is.getTagCompound().hasKey("cape");
    }
    
    public int getCape(final ItemStack is) {
        return is.getTagCompound().getInteger("cape");
    }
    
    public void setCape(final ItemStack is, final int capeID) {
        is.getTagCompound().setInteger("cape", capeID);
    }
    
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        if (armorSlot == 1) {
            return ClientProxy.defaultArmor.reset();
        }
        return null;
    }
    
    static {
        DRAGON = EnumHelper.addArmorMaterial("DRAGON", 55, new int[] { 4, 9, 7, 4 }, 15);
        TURTLE = EnumHelper.addArmorMaterial("TURTLE", 45, new int[] { 3, 8, 6, 3 }, 25);
    }
}
