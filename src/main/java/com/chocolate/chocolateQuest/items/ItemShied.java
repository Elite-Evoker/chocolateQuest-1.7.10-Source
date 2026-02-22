package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumAction;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class ItemShied extends ItemMulti
{
    public ItemShied() {
        super(new String[] { "Chocolate", "Terra", "Aigua", "Ignis", "Orc", "Dwarf", "Triton", "Zombie", "Skeleton", "Pirate", "Walker", "Wood", "Specter", "Diurna", "Nocturna", "Turtle", "Rusted", "Monking", "Spider" }, "s");
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemstack) {
        return ("" + StatCollector.translateToLocal("item.shield.name")).trim();
    }
    
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            if (ep.isBlocking() && ep.getCurrentEquippedItem() == itemStack) {
                ep.addPotionEffect(new PotionEffect(Potion.resistance.id, 1, 3));
                final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(1.2, 1.2, 1.2));
                for (final Entity e : list) {
                    if (e instanceof EntityArrow) {
                        e.motionX = entity.getLookVec().xCoord;
                        e.motionZ = entity.getLookVec().zCoord;
                    }
                }
            }
        }
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemstack, 72000);
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.block;
    }
}
