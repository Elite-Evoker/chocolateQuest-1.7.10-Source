package com.chocolate.chocolateQuest;

import java.util.ArrayList;
import net.minecraftforge.common.BiomeDictionary;
import java.util.Iterator;
import net.minecraft.world.biome.BiomeGenBase;
import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.API.DungeonBase;
import com.chocolate.chocolateQuest.API.DungeonRegister;
import com.chocolate.chocolateQuest.quest.worldManager.TerrainManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import java.util.Random;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorNew implements IWorldGenerator
{
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
        if (world.isRemote) {
            return;
        }
        final Random rnd = new Random();
        rnd.setSeed(getSeed(world, chunkX, chunkZ));
        final int dungeonSeparation = TerrainManager.getTerritorySeparation();
        if (chunkX % dungeonSeparation == 0 && chunkZ % dungeonSeparation == 0) {
            this.generateSurface(world, rnd, chunkX * 16 + 1, chunkZ * 16 + 1);
        }
    }
    
    public static long getSeed(final World world, final int chunkX, final int chunkZ) {
        final long mix = xorShift64(chunkX) + Long.rotateLeft(xorShift64(chunkZ), 32) - 1094792450L;
        final long result = xorShift64(mix);
        return world.getSeed() + result;
    }
    
    public static long xorShift64(long x) {
        x ^= x << 21;
        x ^= x >>> 35;
        x ^= x << 4;
        return x;
    }
    
    public static void createChunks(final World world, final int posX, final int posZ, final int sizeX, final int sizeZ) {
    }
    
    public void generateSurface(final World world, final Random random, final int i, final int k) {
        if (world.getWorldInfo().getTerrainType().getWorldTypeName() == "flat" && !ChocolateQuest.config.dungeonsInFlat) {
            return;
        }
        this.generateBigDungeon(world, random, i, k, true);
    }
    
    public void generateBigDungeon(final World world, final Random random, final int i, final int k, final boolean addDungeon) {
        BuilderBase builder = null;
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        DungeonBase dungeon = null;
        for (final DungeonBase d : DungeonRegister.dungeonList) {
            if (d.isUnique() && TerrainManager.instance.isDungeonSpawned(d.getName())) {
                continue;
            }
            if (d.getChance() <= 0) {
                continue;
            }
            final int[] dimension = d.getDimension();
            boolean dimensionPass = false;
            for (int a = 0; a < dimension.length; ++a) {
                if (dimension[a] == world.provider.dimensionId) {
                    dimensionPass = true;
                    break;
                }
            }
            if (!dimensionPass) {
                continue;
            }
            final String[] arr$;
            final String[] b = arr$ = d.getBiomes();
            final int len$ = arr$.length;
            int i$2 = 0;
            while (i$2 < len$) {
                final String currentName = arr$[i$2];
                if (this.isValidBiome(currentName, biome)) {
                    if (random.nextInt(d.getChance()) == 0) {
                        dungeon = d;
                        break;
                    }
                    break;
                }
                else {
                    ++i$2;
                }
            }
        }
        if (dungeon != null) {
            BuilderHelper.builderHelper.initialize(i);
            builder = dungeon.getBuilder();
            final int idMob = RegisterDungeonMobs.mobList.get(dungeon.getMobID()).getDungeonMonster(world, i, 60, k).getID();
            builder.generate(random, world, i, k, idMob);
            BDHelper.println("Generatig " + dungeon.getName() + " at x: " + i + ",  z:" + k);
            BuilderHelper.builderHelper.flush(world);
            if (dungeon.isUnique()) {
                TerrainManager.instance.dungeonSpawned(dungeon.getName());
            }
        }
    }
    
    public boolean isValidBiome(final String biomeName, final BiomeGenBase biome) {
        if (biomeName.equals("ALL") || biomeName.equals("*")) {
            return true;
        }
        if (biomeName.equals("NONWATER")) {
            return !BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WATER);
        }
        if (biome.biomeName.equals(biomeName)) {
            return true;
        }
        BiomeDictionary.Type type = null;
        for (final BiomeDictionary.Type e : BiomeDictionary.Type.values()) {
            if (biomeName.equals(e.toString())) {
                type = e;
            }
        }
        return type != null && BiomeDictionary.isBiomeOfType(biome, type);
    }
    
    public static int getMobIndex(final ArrayList<DungeonMonstersBase> chestList, final Random random) {
        final int[] weights = new int[chestList.size()];
        int maxNum = 0;
        for (int i = 0; i < chestList.size(); ++i) {
            weights[i] = chestList.get(i).getWeight();
            maxNum += weights[i];
        }
        final int randomNum = random.nextInt(maxNum);
        int index = 0;
        for (int weightSum = weights[0]; weightSum <= randomNum; weightSum += weights[index]) {
            ++index;
        }
        return index;
    }
}
