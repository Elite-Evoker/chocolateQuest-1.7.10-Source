package com.chocolate.chocolateQuest.API;

import java.io.IOException;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import java.util.StringTokenizer;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.io.Reader;
import java.io.FileReader;
import java.util.Properties;
import java.io.File;

public class DungeonBase
{
    String[] biomeList;
    int chance;
    int mobID;
    int[] dimensionID;
    String name;
    int icon;
    BuilderBase builder;
    boolean unique;
    String path;
    
    public DungeonBase() {
        this.chance = 10;
        this.mobID = 1;
        this.dimensionID = new int[] { 0 };
        this.icon = 1;
        this.unique = false;
    }
    
    public int getIcon() {
        return this.icon;
    }
    
    public String getName() {
        return this.name;
    }
    
    public BuilderBase getBuilder() {
        return this.builder;
    }
    
    public String[] getBiomes() {
        return this.biomeList;
    }
    
    public DungeonBase setBiomes(final String[] biomes) {
        this.biomeList = biomes;
        return this;
    }
    
    public int getChance() {
        return this.chance;
    }
    
    public DungeonBase setChance(final int chance) {
        this.chance = chance;
        return this;
    }
    
    public int getMobID() {
        return this.mobID;
    }
    
    public boolean isUnique() {
        return this.unique;
    }
    
    public int[] getDimension() {
        return this.dimensionID;
    }
    
    public DungeonBase setDimension(final int[] dim) {
        this.dimensionID = dim;
        return this;
    }
    
    public DungeonBase readData(final File file) {
        String[] ret = null;
        final Properties prop = new Properties();
        try {
            final FileReader fr = new FileReader(file);
            prop.load(fr);
            final String dungeonType = prop.getProperty("builder");
            if (dungeonType == null) {
                return null;
            }
            this.path = file.getPath();
            dungeonType.trim();
            this.builder = RegisterDungeonBuilder.getBuilderByName(dungeonType);
            if (this.builder == null) {
                BDHelper.println("Wrong builder: " + this.builder);
                return null;
            }
            final String s = prop.getProperty("biomes");
            final StringTokenizer stkn = new StringTokenizer(s, ",");
            ret = new String[stkn.countTokens()];
            for (int tknCount = stkn.countTokens(), i = 0; i < tknCount; ++i) {
                ret[i] = stkn.nextToken().trim();
            }
            this.setBiomes(ret);
            this.setChance(HelperReadConfig.getIntegerProperty(prop, "chance", 10));
            this.icon = HelperReadConfig.getIntegerProperty(prop, "icon", 10);
            this.name = file.getName();
            if (!file.getParentFile().getName().contains("DungeonConfig")) {
                this.name = file.getParentFile().getName() + "-" + file.getName();
            }
            this.setDimension(HelperReadConfig.getIntegerArray(prop, "dimensionID", 0));
            this.mobID = RegisterDungeonMobs.getMonster(prop.getProperty("mob").trim());
            this.unique = HelperReadConfig.getBooleanProperty(prop, "unique", this.unique);
            if (!this.readSpecialData(prop)) {
                return null;
            }
            fr.close();
        }
        catch (final IOException e) {
            BDHelper.println("Error reading dungeon config file at betterDungeons mod");
            e.printStackTrace();
            return null;
        }
        return this;
    }
    
    public boolean readSpecialData(final Properties prop) {
        return this.builder.load(prop) != null;
    }
    
    public String getPath() {
        return this.path;
    }
}
