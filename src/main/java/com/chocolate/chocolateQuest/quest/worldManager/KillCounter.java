package com.chocolate.chocolateQuest.quest.worldManager;

import net.minecraft.nbt.NBTBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTException;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.nbt.NBTTagCompound;

public class KillCounter
{
    public String playerName;
    public String name;
    private String monster;
    private NBTTagCompound tags;
    public int killAmmount;
    
    public KillCounter() {
    }
    
    public KillCounter(final String playerName, final String name, final String monster, final String tags) {
        this.playerName = playerName;
        this.name = name;
        this.monster = monster;
        if (tags != null) {
            try {
                this.tags = (NBTTagCompound)BDHelper.JSONToNBT(tags);
            }
            catch (final NBTException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean entityMatchesCounter(final EntityLivingBase entity) {
        return entityMatchesNameAndTags(entity, this.monster, this.tags);
    }
    
    public static boolean entityMatchesNameAndTags(final EntityLivingBase entity, final String monsterName, final NBTTagCompound tags) {
        final String entityName = EntityList.getEntityString((Entity)entity);
        if (entityName == null) {
            return false;
        }
        if (entityName.equals(monsterName)) {
            if (tags != null) {
                final NBTTagCompound tagEntity = new NBTTagCompound();
                entity.writeEntityToNBT(tagEntity);
                if (!BDHelper.compareTags(tags, tagEntity)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public void writeToNBT(final NBTTagCompound tag) {
        tag.setString("name", this.name);
        tag.setString("playerName", this.playerName);
        tag.setString("monster", this.monster);
        if (this.tags != null) {
            tag.setTag("data", (NBTBase)this.tags);
        }
    }
    
    public void readFromNBT(final NBTTagCompound tag) {
        this.name = tag.getString("name");
        this.playerName = tag.getString("playerName");
        this.monster = tag.getString("monster");
        if (tag.hasKey("data")) {
            this.tags = (NBTTagCompound)tag.getTag("data");
        }
    }
}
