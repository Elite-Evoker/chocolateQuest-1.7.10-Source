package com.chocolate.chocolateQuest.builder;

import net.minecraft.world.World;
import java.util.Random;
import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import java.util.Properties;
import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.API.BuilderBase;

public class BuilderVolcano extends BuilderBase
{
    int numTiles;
    static int filer;
    BuilderBlockData blockWalls;
    BuilderBlockData blockPath;
    int oreChance;
    String folder;
    int dungeonRooms;
    boolean generatePath;
    boolean generateDungeon;
    static int vacio;
    int counter;
    
    public BuilderVolcano() {
        this.oreChance = 8;
        this.folder = "dungeons";
        this.dungeonRooms = 25;
        this.generatePath = true;
        this.generateDungeon = true;
        this.counter = 120;
    }
    
    @Override
    public BuilderBase load(final Properties prop) {
        this.oreChance = HelperReadConfig.getIntegerProperty(prop, "oreChance", 16);
        this.blockWalls = HelperReadConfig.getBlock(prop, "blockWalls", new BuilderBlockData(Blocks.stone));
        this.blockPath = HelperReadConfig.getBlock(prop, "blockPath", new BuilderBlockData(Blocks.netherrack));
        this.dungeonRooms = HelperReadConfig.getIntegerProperty(prop, "dungeonRooms", 25);
        this.generatePath = HelperReadConfig.getBooleanProperty(prop, "generatePath", true);
        this.generateDungeon = HelperReadConfig.getBooleanProperty(prop, "generateDungeon", false);
        return this;
    }
    
    @Override
    public String getName() {
        return "volcano";
    }
    
    @Override
    public void generate(final Random random, final World world, final int x, final int z, final int mob) {
        final int y = 5;
        this.generate(random, world, x, y, z, mob);
    }
    
    @Override
    public void generate(final Random random, final World world, final int i, final int j, final int k, final int mob) {
    }
    
    static {
        BuilderVolcano.filer = 1;
        BuilderVolcano.vacio = -1;
    }
}
