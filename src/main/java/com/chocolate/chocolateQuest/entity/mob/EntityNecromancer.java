package com.chocolate.chocolateQuest.entity.mob;

import net.minecraft.entity.SharedMonsterAttributes;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import net.minecraft.world.World;

public class EntityNecromancer extends EntityHumanSkeleton
{
    public EntityNecromancer(final World world) {
        super(world);
        this.AICombatMode = EnumAiCombat.EVASIVE.ordinal();
        this.setCurrentItemOrArmor(this.potionCount = 0, this.getEquipedWeapon());
        this.setLeftHandItem(new ItemStack(ChocolateQuest.cursedBone));
        final ItemStack is = new ItemStack(ChocolateQuest.armorMage);
        final NBTTagCompound tag = new NBTTagCompound();
        final NBTTagCompound tagDisplay = new NBTTagCompound();
        tag.setTag("display", (NBTBase)tagDisplay);
        is.stackTagCompound = tag;
        tagDisplay.setInteger("color", 3355443);
        this.setCurrentItemOrArmor(3, is);
        this.setCurrentItemOrArmor(2, new ItemStack((Item)Items.leather_leggings));
        this.setCurrentItemOrArmor(1, new ItemStack((Item)Items.leather_boots));
        this.setAIForCurrentMode();
    }
    
    public ItemStack getEquipedWeapon() {
        final ItemStack is = new ItemStack(ChocolateQuest.staffPhysic);
        is.stackTagCompound = new NBTTagCompound();
        final ItemStack[] cargo = { new ItemStack(ChocolateQuest.spell, 1, 7), new ItemStack(ChocolateQuest.spell, 1, 0) };
        InventoryBag.saveCargo(is, cargo);
        return is;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
    }
    
    @Override
    public int getLeadershipValue() {
        return 1000;
    }
    
    @Override
    public boolean isBoss() {
        return true;
    }
}
