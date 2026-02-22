package com.chocolate.chocolateQuest.config;

import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.Configuration;

public class Configurations
{
    public int dungeonSeparation;
    public boolean dungeonsInFlat;
    public int distToDespawn;
    public boolean updateData;
    
    public void load(final Configuration config) {
        config.load();
        Property prop = config.get("general", "dungeonSeparation", 10);
        prop.comment = "Distance in chunks(16 blocks) from dungeon to dungeon";
        this.dungeonSeparation = prop.getInt(10);
        prop = config.get("general", "dungeonsInFlat", false);
        prop.comment = "Generate dungeons in flat maps";
        this.dungeonsInFlat = prop.getBoolean(false);
        prop = config.get("general", "distanceToDespawn", 64);
        prop.comment = "Distance in blocks to the closest player to despawn mobs";
        this.distToDespawn = prop.getInt(64);
        prop = config.get("general", "extractData", true);
        prop.comment = "If true the mod will update the Chocolate folder on startup";
        this.updateData = prop.getBoolean();
        config.save();
    }
}
