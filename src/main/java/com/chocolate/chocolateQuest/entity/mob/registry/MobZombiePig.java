package com.chocolate.chocolateQuest.entity.mob.registry;

import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.SharedMonsterAttributes;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPigZombie;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobZombiePig extends DungeonMonstersBase
{
    @Override
    public String getEntityName() {
        return "pigzombie";
    }
    
    @Override
    public int getFlagId() {
        return 11;
    }
    
    @Override
    public String getRegisteredEntityName() {
        return "chocolateQuest.pigzombie";
    }
    
    @Override
    public Entity getBoss(final World world, final int x, final int y, final int z) {
        final EntityHumanPigZombie p = new EntityHumanPigZombie(world);
        p.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0);
        final ItemStack is = new ItemStack(ChocolateQuest.staffFire);
        is.stackTagCompound = new NBTTagCompound();
        final ItemStack[] cargo = { new ItemStack(ChocolateQuest.spell, 1, 4), new ItemStack(ChocolateQuest.spell, 1, 3), new ItemStack(ChocolateQuest.spell, 1, 7), new ItemStack(ChocolateQuest.spell, 1, 1), new ItemStack(ChocolateQuest.spell, 1, 0) };
        InventoryBag.saveCargo(is, cargo);
        Awakements.addEnchant(is, Awakements.spellExpansion, 2);
        p.setCurrentItemOrArmor(0, is);
        p.setLeftHandItem(new ItemStack(ChocolateQuest.cursedBone));
        final ItemStack armor = new ItemStack(ChocolateQuest.armorMage);
        final NBTTagCompound tag = new NBTTagCompound();
        final NBTTagCompound tagDisplay = new NBTTagCompound();
        tag.setTag("display", (NBTBase)tagDisplay);
        armor.stackTagCompound = tag;
        tagDisplay.setInteger("color", 6684672);
        p.setCurrentItemOrArmor(3, armor);
        p.setCurrentItemOrArmor(2, new ItemStack((Item)Items.chainmail_leggings));
        p.setCurrentItemOrArmor(1, new ItemStack((Item)Items.chainmail_boots));
        return (Entity)p;
    }
    
    @Override
    public Entity getEntity(final World world, final int x, final int y, final int z) {
        return (Entity)new EntityHumanPigZombie(world);
    }
    
    @Override
    public String getTeamName() {
        return "mob_undead";
    }
    
    @Override
    public double getHealth() {
        return 35.0;
    }
    
    @Override
    public double getRange() {
        return 30.0;
    }
}
