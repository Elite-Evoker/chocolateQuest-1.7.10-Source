package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import java.util.Properties;
import com.chocolate.chocolateQuest.builder.support.PlateauBuilder;
import com.chocolate.chocolateQuest.API.BuilderBase;

public class BuilderTemplate extends BuilderBase
{
    String folderName;
    int posY;
    PlateauBuilder supportStructure;
    BuilderEmptyCave cave;
    boolean replaceBanners;
    
    public BuilderTemplate() {
        this.replaceBanners = true;
    }
    
    @Override
    public BuilderBase load(final Properties prop) {
        this.folderName = prop.getProperty("folder").trim();
        if (this.folderName == null) {
            return null;
        }
        this.posY = Math.max(1, HelperReadConfig.getIntegerProperty(prop, "posY", 64));
        if (HelperReadConfig.getBooleanProperty(prop, "supportStructure", false)) {
            (this.supportStructure = new PlateauBuilder()).load(prop);
        }
        this.cave = new BuilderEmptyCave();
        if (HelperReadConfig.getBooleanProperty(prop, "emptyCave", false)) {
            (this.cave = new BuilderEmptyCave()).load(prop);
        }
        this.replaceBanners = HelperReadConfig.getBooleanProperty(prop, "replaceBanners", this.replaceBanners);
        return this;
    }
    
    @Override
    public String getName() {
        return "templateFloating";
    }
    
    @Override
    public void generate(final Random random, final World world, final int x, final int z, final int mob) {
        final Schematic schematic = BuilderHelper.getRandomNBTMap(this.folderName, random);
        final int maxX = schematic.width;
        final int maxY = schematic.length;
        this.generate(random, schematic, world, x - maxX / 2, this.posY, z - maxY / 2, mob);
    }
    
    @Override
    public void generate(final Random random, final World world, final int i, final int j, final int k, final int idMob) {
        final Schematic schematic = BuilderHelper.getRandomNBTMap(this.folderName, random);
        this.generate(random, schematic, world, i, this.posY, k, idMob);
    }
    
    public void generate(final Random random, final Schematic schematic, final World world, final int i, final int j, final int k, final int idMob) {
        final BuilderHelper builderHelper = BuilderHelper.builderHelper;
        if (this.supportStructure != null) {
            this.supportStructure.generate(random, world, i, j, k, schematic.width, schematic.length);
        }
        if (this.cave != null) {
            final int borderSize = 4;
            this.cave.generate(random, world, i - borderSize, j + this.cave.posY, k - borderSize, schematic.width + borderSize * 2, schematic.length + borderSize * 2, (this.cave.height <= 0) ? (schematic.height + 3) : this.cave.height);
        }
        builderHelper.putSchematicInWorld(random, world, schematic, i, j, k, idMob, this.replaceBanners);
    }
}
