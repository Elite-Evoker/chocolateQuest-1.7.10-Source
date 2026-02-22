package com.chocolate.chocolateQuest.magic;

import net.minecraft.util.StatCollector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.item.EntityItem;
import com.chocolate.chocolateQuest.items.swords.ItemBDSword;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import net.minecraft.item.ItemStack;

public class AwakementProperty extends Awakements
{
    public AwakementProperty(final String name, final int icon) {
        super(name, icon);
    }
    
    @Override
    public boolean canBeUsedOnItem(final ItemStack is) {
        if (is.getItem() instanceof ItemArmorBase) {
            return ((ItemArmorBase)is.getItem()).isEpic();
        }
        return is.getItem() instanceof ItemBDSword;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
    
    @Override
    public void onEntityItemUpdate(final EntityItem entityItem) {
        final ItemStack is = entityItem.getEntityItem();
        if (Awakements.hasEnchant(is, this)) {
            if (entityItem.getEntityItem().stackTagCompound != null && entityItem.age > entityItem.lifespan - 100) {
                entityItem.age = 0;
            }
            if (!entityItem.isEntityInvulnerable()) {
                final double x = entityItem.posX;
                final double y = entityItem.posY;
                final double z = entityItem.posZ;
                final double mx = entityItem.motionX;
                final double my = entityItem.motionY;
                final double mz = entityItem.motionZ;
                final NBTTagCompound tag = new NBTTagCompound();
                entityItem.writeEntityToNBT(tag);
                tag.setBoolean("Invulnerable", true);
                entityItem.readFromNBT(tag);
                entityItem.setPosition(x, y, z);
                entityItem.motionX = mx;
                entityItem.motionY = my;
                entityItem.motionZ = mz;
            }
            if (entityItem.isBurning()) {
                entityItem.extinguish();
                entityItem.motionY = 0.2;
            }
        }
    }
    
    @Override
    public void onUpdate(final Entity entity, final ItemStack itemStack) {
        if (entity.ticksExisted % 400 == 0 && this.getOwner(itemStack) == null && entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            this.setOwner(itemStack, ep.getCommandSenderName());
        }
    }
    
    public String getOwner(final ItemStack is) {
        if (is.getTagCompound() == null) {
            return null;
        }
        final String ownerName = is.stackTagCompound.getString("OriginalOwner");
        if (ownerName == "") {
            return null;
        }
        return ownerName;
    }
    
    public void setOwner(final ItemStack is, final String name) {
        if (is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
        }
        is.stackTagCompound.setString("OriginalOwner", name);
    }
    
    @Override
    public String getDescription(final ItemStack is) {
        return StatCollector.translateToLocal("enchantment." + this.getName() + ".name") + ": " + this.getOwner(is);
    }
    
    @Override
    public int getLevelCost() {
        return 10;
    }
}
