package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.WorldGeneratorNew;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import java.util.Properties;
import com.chocolate.chocolateQuest.builder.support.PlateauBuilder;
import com.chocolate.chocolateQuest.API.BuilderBase;

public class BuilderTemplateSurface extends BuilderBase
{
    String folderName;
    int underGroundOffset;
    boolean replaceBanners;
    PlateauBuilder supportStructure;
    
    public BuilderTemplateSurface() {
        this.replaceBanners = true;
    }
    
    @Override
    public BuilderBase load(final Properties prop) {
        this.folderName = prop.getProperty("folder").trim();
        if (this.folderName == null) {
            return null;
        }
        this.underGroundOffset = HelperReadConfig.getIntegerProperty(prop, "underGroundOffset", 10);
        if (HelperReadConfig.getBooleanProperty(prop, "supportStructure", false)) {
            (this.supportStructure = new PlateauBuilder()).load(prop);
        }
        this.replaceBanners = HelperReadConfig.getBooleanProperty(prop, "replaceBanners", this.replaceBanners);
        return this;
    }
    
    @Override
    public String getName() {
        return "templateSurface";
    }
    
    @Override
    public void generate(final Random random, final World world, int i, int k, final int mob) {
        final Schematic schematic = BuilderHelper.getRandomNBTMap(this.folderName, random);
        final int maxX = schematic.width;
        final int maxY = schematic.length;
        i -= maxX / 2;
        k -= maxY / 2;
        final int cont = 0;
        int cant = 0;
        int media = 0;
        for (int x = 0; x < maxX; ++x) {
            for (int y = 0; y < maxY; ++y) {
                final int h = world.getTopSolidOrLiquidBlock(i + x, y + k);
                media += h;
                ++cant;
            }
        }
        final int height = media / cant;
        this.generate(random, world, i, height, k, mob);
    }
    
    @Override
    public void generate(final Random random, final World world, final int i, final int j, final int k, final int idMob) {
        final Schematic schematic = BuilderHelper.getRandomNBTMap(this.folderName, random);
        this.generate(random, schematic, world, i, j, k, idMob);
    }
    
    public void generate(final Random random, final Schematic schematic, final World world, final int i, int j, final int k, final int idMob) {
        WorldGeneratorNew.createChunks(world, i, k, schematic.width, schematic.length);
        if (this.supportStructure != null) {
            this.supportStructure.generate(random, world, i, j - 2, k, schematic.width, schematic.length);
        }
        j -= this.underGroundOffset;
        j = Math.max(1, j);
        final BuilderHelper builderHelper = BuilderHelper.builderHelper;
        builderHelper.putSchematicInWorld(random, world, schematic, i, j, k, idMob, this.replaceBanners);
    }
}
