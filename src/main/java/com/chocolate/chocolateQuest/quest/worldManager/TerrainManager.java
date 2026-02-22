package com.chocolate.chocolateQuest.quest.worldManager;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import com.chocolate.chocolateQuest.ChocolateQuest;
import java.util.ArrayList;
import java.util.List;

public class TerrainManager extends WorldManagerBase
{
    public static TerrainManager instance;
    int territorySeparation;
    List<String> uniqueDungeonsSpawned;
    
    public TerrainManager(final int territorySeparation) {
        this.territorySeparation = territorySeparation;
        this.uniqueDungeonsSpawned = new ArrayList<String>();
    }
    
    public static int getTerritorySeparation() {
        return (TerrainManager.instance == null) ? ChocolateQuest.config.dungeonSeparation : TerrainManager.instance.territorySeparation;
    }
    
    public boolean isDungeonSpawned(final String name) {
        return this.uniqueDungeonsSpawned.contains(name);
    }
    
    public void dungeonSpawned(final String name) {
        this.uniqueDungeonsSpawned.add(name);
    }
    
    public void readFromNBT(final NBTTagCompound tag) {
        this.territorySeparation = tag.getInteger("Separation");
        final NBTTagList list = (NBTTagList)tag.getTag("dungeons");
        if (list != null) {
            final int tags = list.tagCount();
            if (tags > 0) {
                for (int i = 0; i < tags; ++i) {
                    this.uniqueDungeonsSpawned.add(list.getCompoundTagAt(i).getString("name"));
                }
            }
        }
    }
    
    public void writeToNBT(final NBTTagCompound tag) {
        tag.setInteger("Separation", this.territorySeparation);
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.uniqueDungeonsSpawned.size(); ++i) {
            final NBTTagCompound nameTag = new NBTTagCompound();
            nameTag.setString("name", (String)this.uniqueDungeonsSpawned.get(i));
            list.appendTag((NBTBase)nameTag);
        }
        tag.setTag("dungeons", (NBTBase)list);
    }
}
