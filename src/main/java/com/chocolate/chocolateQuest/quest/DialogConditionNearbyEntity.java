package com.chocolate.chocolateQuest.quest;

import java.util.Iterator;
import java.util.List;
import com.chocolate.chocolateQuest.quest.worldManager.KillCounter;
import net.minecraft.nbt.NBTException;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionNearbyEntity extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final List<Entity> list = npc.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)npc, AxisAlignedBB.getBoundingBox(npc.posX - this.value, npc.posY - this.value, npc.posZ - this.value, npc.posX + this.value, npc.posY + this.value, npc.posZ + this.value));
        for (final Entity e : list) {
            if (e instanceof EntityLivingBase) {
                final String[] vars = this.name.split(" ");
                String name = this.name;
                String sTags = null;
                if (vars.length > 0) {
                    name = vars[0];
                }
                if (vars.length > 1) {
                    sTags = vars[1];
                }
                if (name == null) {
                    continue;
                }
                NBTTagCompound tag = null;
                if (sTags != null) {
                    try {
                        tag = (NBTTagCompound)BDHelper.JSONToNBT(sTags);
                    }
                    catch (final NBTException e2) {
                        e2.printStackTrace();
                    }
                }
                final boolean matches = KillCounter.entityMatchesNameAndTags((EntityLivingBase)e, name, tag);
                if (matches) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    @Override
    public byte getType() {
        return 9;
    }
    
    @Override
    public boolean hasName() {
        return true;
    }
    
    @Override
    public boolean hasValue() {
        return true;
    }
    
    @Override
    public String getNameForValue() {
        return "Radio";
    }
    
    @Override
    public boolean hasOperator() {
        return false;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("This condition will pass if there are any nearby entities with the");
        list.add("specified id and data, sintax is ENTITY_ID {ENTITY_DATA}, use:");
        list.add("chocolateQuest.CQ_npc {nameID:\"innkeeper\"}");
        list.add("to search a nearby NPC tagged as innkeeper");
    }
}
