package com.chocolate.chocolateQuest.builder;

import net.minecraft.util.ReportedException;
import net.minecraft.crash.CrashReport;
import java.io.File;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.item.Item;
import com.chocolate.chocolateQuest.API.RegisterChestItem;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.DungeonHooks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.item.ItemDoor;
import net.minecraft.nbt.NBTTagCompound;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityMobSpawner;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import java.util.Random;
import net.minecraft.world.World;
import java.util.ArrayList;
import net.minecraft.tileentity.TileEntity;
import java.util.List;

public class BuilderHelper
{
    public static BuilderHelper builderHelper;
    static final byte DEFAULT = 0;
    static final byte FOOD = 1;
    static final byte WEAPONS = 2;
    static final byte MINERALS = 3;
    static final byte TREASURE = 4;
    List<BlockData> specialBlocks;
    List<TileEntity> tileEntities;
    public static int BLOCK_NOTIFICATION;
    
    public void initialize(final int blockNotify) {
        BuilderHelper.BLOCK_NOTIFICATION = blockNotify;
        if (this.specialBlocks == null) {
            this.specialBlocks = new ArrayList<BlockData>();
        }
        this.specialBlocks.clear();
        if (this.tileEntities == null) {
            this.tileEntities = new ArrayList<TileEntity>();
        }
        this.tileEntities.clear();
    }
    
    public void flush(final World world) {
        this.copyTileEntities(world);
    }
    
    public void putSchematicInWorld(final Random random, final World world, final Schematic schematic, final int i, final int j, final int k, final int idMob, final boolean replaceFlags) {
        schematic.setPosition(i, j, k);
        final int sx = schematic.width;
        final int sy = schematic.height;
        final int sz = schematic.length;
        for (int y = 0; y < sy; ++y) {
            for (int x = 0; x < sx; ++x) {
                for (int z = 0; z < sz; ++z) {
                    final Block block = schematic.getBlock(x, y, z);
                    final int metadata = schematic.getBlockMetadata(x, y, z);
                    final int posX = i + x;
                    final int posY = j + y;
                    final int posZ = k + z;
                    if (!this.checkIfPlacedOnFirstPass(block)) {
                        this.specialBlocks.add(new BlockData(posX, posY, posZ, block, metadata));
                    }
                    else if (block != ChocolateQuest.emptyBlock) {
                        if (block == ChocolateQuest.exporterChest) {
                            if (metadata == 4) {
                                world.setBlockToAir(posX, posY, posZ);
                                final Entity boss = RegisterDungeonMobs.mobList.get(idMob).getBoss(world, posX, posY, posZ);
                                if (boss != null) {
                                    boss.setPosition((double)posX, (double)posY, (double)posZ);
                                    world.spawnEntityInWorld(boss);
                                    if (boss.ridingEntity != null) {
                                        boss.ridingEntity.setPosition((double)posX, (double)posY, (double)posZ);
                                        world.spawnEntityInWorld(boss.ridingEntity);
                                    }
                                }
                            }
                            else if (metadata == 1) {
                                addFoodChest(random, world, posX, posY, posZ);
                            }
                            else if (metadata == 0) {
                                addTreasure(random, world, posX, posY, posZ);
                            }
                            else if (metadata == 3) {
                                addMineralChest(random, world, posX, posY, posZ);
                            }
                            else if (metadata == 2) {
                                addWeaponChest(random, world, posX, posY, posZ);
                            }
                        }
                        else if (block == Blocks.chest) {
                            addChest(random, world, posX, posY, posZ);
                        }
                        else if (block == Blocks.furnace) {
                            world.setBlock(posX, posY, posZ, block, metadata, 3);
                            final TileEntityFurnace tef = (TileEntityFurnace)world.getTileEntity(posX, posY, posZ);
                            if (random.nextInt(15) == 0) {
                                tef.setInventorySlotContents(0, new ItemStack(Items.gold_ingot, random.nextInt(3) + 1));
                            }
                            if (random.nextInt(3) == 0) {
                                tef.setInventorySlotContents(1, new ItemStack(Items.coal, random.nextInt(45) + 1));
                            }
                            world.setBlockMetadataWithNotify(posX, posY, posZ, metadata, 3);
                        }
                        else if (block == Blocks.dispenser) {
                            world.setBlock(posX, posY, posZ, block, metadata, 0);
                            final TileEntityDispenser ted = (TileEntityDispenser)world.getTileEntity(posX, posY, posZ);
                            for (int v = 0; v < 9; ++v) {
                                if (random.nextInt(20) == 0) {
                                    if (random.nextInt(50) == 0) {
                                        ted.setInventorySlotContents(v, new ItemStack(Items.experience_bottle, random.nextInt(64) + 1));
                                    }
                                    ted.setInventorySlotContents(v, new ItemStack(Items.fire_charge, random.nextInt(64) + 1));
                                }
                                else {
                                    ted.setInventorySlotContents(v, new ItemStack(Items.arrow, random.nextInt(64) + 1));
                                }
                            }
                            world.setBlockMetadataWithNotify(posX, posY, posZ, metadata, 3);
                        }
                        else if (block == Blocks.dropper) {
                            world.setBlock(posX, posY, posZ, block, metadata, 3);
                            world.setBlockMetadataWithNotify(posX, posY, posZ, metadata, 3);
                        }
                        else {
                            world.setBlock(posX, posY, posZ, block, metadata, BuilderHelper.BLOCK_NOTIFICATION);
                        }
                    }
                }
            }
        }
        this.copySpecialBlocks(world);
        final List<TileEntity> list = schematic.getTileEntities();
        final NBTTagList tags = schematic.getTileEntitiesTag();
        int tagCount = 0;
        for (final TileEntity te : list) {
            final Block block2 = world.getBlock(te.xCoord, te.yCoord, te.zCoord);
            if (block2 instanceof BlockContainer) {
                final TileEntity tempEntity = ((BlockContainer)block2).createNewTileEntity(world, world.getBlockMetadata(te.xCoord, te.yCoord, te.zCoord));
                final NBTTagCompound tag = tags.getCompoundTagAt(tagCount);
                tempEntity.readFromNBT(tag);
                boolean putTileEntity = true;
                if (tempEntity instanceof BlockMobSpawnerTileEntity) {
                    final BlockMobSpawnerTileEntity spawner = (BlockMobSpawnerTileEntity)tempEntity;
                    spawner.mob = idMob;
                }
                if (tempEntity instanceof BlockBannerStandTileEntity && replaceFlags) {
                    final BlockBannerStandTileEntity stand = (BlockBannerStandTileEntity)tempEntity;
                    final int id = RegisterDungeonMobs.mobList.get(idMob).getFlagId();
                    stand.item = new ItemStack(ChocolateQuest.banner, 1, id);
                }
                if (tempEntity instanceof TileEntityMobSpawner) {
                    final TileEntityMobSpawner spawner2 = (TileEntityMobSpawner)tempEntity;
                    final String name = spawner2.func_145881_a().getEntityNameToSpawn();
                    if (name.equals("Pig")) {
                        setMobForSpawner(spawner2, idMob, te.xCoord, te.yCoord, te.zCoord, random);
                    }
                }
                if (block2 == Blocks.chest) {
                    putTileEntity = false;
                }
                if (block2 == Blocks.furnace && this.isInventoryEmpty((IInventory)tempEntity)) {
                    putTileEntity = false;
                }
                if (block2 == Blocks.dispenser && this.isInventoryEmpty((IInventory)tempEntity)) {
                    putTileEntity = false;
                }
                if (putTileEntity) {
                    this.addTileEntity(te.xCoord, te.yCoord, te.zCoord, tempEntity);
                }
            }
            ++tagCount;
        }
        final List<Entity> listEntity = schematic.getEntities(world);
        for (final Entity e : listEntity) {
            world.spawnEntityInWorld(e);
        }
    }
    
    public void addTileEntity(final int x, final int y, final int z, final TileEntity tileEntity) {
        tileEntity.xCoord = x;
        tileEntity.yCoord = y;
        tileEntity.zCoord = z;
        this.tileEntities.add(tileEntity);
    }
    
    public void copyTileEntities(final World world) {
        if (this.tileEntities.size() > 0) {
            for (final TileEntity tempEntity : this.tileEntities) {
                world.setTileEntity(tempEntity.xCoord, tempEntity.yCoord, tempEntity.zCoord, tempEntity);
            }
        }
    }
    
    public boolean checkIfPlacedOnFirstPass(final Block id) {
        return id != Blocks.redstone_wire && id != Blocks.redstone_torch && id != Blocks.stone_button && id != Blocks.wooden_button && id != Blocks.bed && id != Blocks.torch && id != Blocks.ladder && id != Blocks.wooden_door && id != Blocks.iron_door && id != Blocks.lever && id != Blocks.torch && id != Blocks.ladder && id != Blocks.bed && id != Blocks.tripwire_hook && id != Blocks.wall_sign && id != Blocks.piston && id != Blocks.piston_head;
    }
    
    public void copySpecialBlocks(final World world) {
        BlockData b = null;
        final Random rand = new Random();
        if (this.specialBlocks.size() > 0) {
            final Iterator i = this.specialBlocks.iterator();
            while (i.hasNext()) {
                b = i.next();
                if (b.block == Blocks.wooden_door || b.block == Blocks.iron_door) {
                    if (b.blockMetadata < 8) {
                        ItemDoor.placeDoorBlock(world, b.x, b.y, b.z, b.blockMetadata, (b.block == Blocks.wooden_door) ? Blocks.wooden_door : Blocks.iron_door);
                    }
                    else {
                        world.setBlock(b.x, b.y, b.z, b.block, b.blockMetadata, 0);
                    }
                }
                else if (b.block == Blocks.redstone_torch) {
                    world.setBlock(b.x, b.y, b.z, b.block, b.blockMetadata, 3);
                }
                else if (b.block == Blocks.piston_head) {
                    world.setBlock(b.x, b.y, b.z, b.block, b.blockMetadata, 0);
                }
                else {
                    world.setBlock(b.x, b.y, b.z, b.block, b.blockMetadata, 3);
                }
            }
            this.specialBlocks.clear();
        }
    }
    
    public boolean isInventoryEmpty(final IInventory inv) {
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            if (inv.getStackInSlot(i) != null) {
                return false;
            }
        }
        return true;
    }
    
    public static void clearArea(final Random random, final World world, int i, final int j, int k, final int sizeX, final int sizeZ) {
        final Perlin3D p = new Perlin3D(world.getSeed(), 8, random);
        final Perlin3D p2 = new Perlin3D(world.getSeed(), 32, random);
        final int wallSize = 8;
        final int size = sizeX + wallSize * 2;
        final int height = 32;
        i -= wallSize;
        k -= wallSize;
        for (int x = 0; x < size; ++x) {
            for (int z = 0; z < size; ++z) {
                for (int maxHeight = world.getTopSolidOrLiquidBlock(x + i, z + k) - 1 - j, y = 0; y <= maxHeight; ++y) {
                    if (x > wallSize && z > wallSize && x < size - wallSize && z < size - wallSize) {
                        world.setBlockToAir(x + i, j + y, z + k);
                    }
                    else {
                        float noiseVar = (maxHeight - y) / (Math.max(1, maxHeight) * 1.5f);
                        final int tWallSize = wallSize;
                        noiseVar += Math.max(0.0f, (tWallSize - x) / 8.0f);
                        noiseVar += Math.max(0.0f, (tWallSize - (size - x)) / 8.0f);
                        noiseVar += Math.max(0.0f, (tWallSize - z) / 8.0f);
                        noiseVar += Math.max(0.0f, (tWallSize - (size - z)) / 8.0f);
                        final double value = (p.getNoiseAt(x + i, y, z + k) + p2.getNoiseAt(x + i, y, z + k) + noiseVar) / 3.0;
                        if (value < 0.5) {
                            world.setBlockToAir(i + x, j + y, k + z);
                        }
                    }
                }
                final int maxHeight = world.getTopSolidOrLiquidBlock(x + i, z + k);
                final BiomeGenBase biome = world.getBiomeGenForCoords(x + i, z + k);
                world.setBlock(i + x, maxHeight - 1, k + z, biome.topBlock);
            }
        }
    }
    
    public static void addSpawner(final Random random, final World world, final int x, final int y, final int z, final int idMob) {
        world.setBlock(x, y, z, Blocks.mob_spawner, 0, 0);
        final TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(x, y, z);
        if (tileentitymobspawner != null) {
            setMobForSpawner(tileentitymobspawner, idMob, x, y, z, random);
        }
    }
    
    public static void setMobForSpawner(final TileEntityMobSpawner spawner, final int idMob, final int x, final int y, final int z, final Random random) {
        final String mob = RegisterDungeonMobs.mobList.get(idMob).getSpawnerName(x, y, z, random);
        if (mob != null) {
            spawner.func_145881_a().setEntityName(mob);
        }
        else {
            spawner.func_145881_a().setEntityName(DungeonHooks.getRandomDungeonMob(random));
        }
    }
    
    public static boolean addTreasure(final Random random, final World world, final int x, final int y, final int z) {
        world.setBlock(x, y, z, (Block)Blocks.chest, 0, 0);
        final TileEntityChest tileentitychest = new TileEntityChest();
        final int itemsCount = random.nextInt(4) + 2;
        ItemStack itemstack = null;
        for (int f = 1; f < itemsCount + 1; ++f) {
            itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.treasureList, random);
            final int slot = random.nextInt(tileentitychest.getSizeInventory());
            tileentitychest.setInventorySlotContents(slot, itemstack);
        }
        if (random.nextInt(3) == 0) {
            Item record = null;
            switch (random.nextInt(10)) {
                case 0: {
                    record = Items.record_blocks;
                    break;
                }
                case 1: {
                    record = Items.record_cat;
                    break;
                }
                case 2: {
                    record = Items.record_chirp;
                    break;
                }
                case 3: {
                    record = Items.record_far;
                    break;
                }
                case 4: {
                    record = Items.record_mall;
                    break;
                }
                case 5: {
                    record = Items.record_mellohi;
                    break;
                }
                case 6: {
                    record = Items.record_stal;
                    break;
                }
                case 7: {
                    record = Items.record_strad;
                    break;
                }
                case 8: {
                    record = Items.record_wait;
                    break;
                }
                case 9: {
                    record = Items.record_ward;
                    break;
                }
                default: {
                    record = Items.record_blocks;
                    break;
                }
            }
            final int slot = random.nextInt(tileentitychest.getSizeInventory());
            tileentitychest.setInventorySlotContents(slot, new ItemStack(record));
        }
        world.setTileEntity(x, y, z, (TileEntity)tileentitychest);
        return true;
    }
    
    public static boolean addFoodChest(final Random random, final World world, final int x, final int y, final int z) {
        world.setBlock(x, y, z, (Block)Blocks.chest, 0, 0);
        final TileEntityChest tileentitychest = new TileEntityChest();
        for (int itemsCount = random.nextInt(6) + 2, f = 0; f < itemsCount; ++f) {
            final ItemStack itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.foodList, random);
            final int slot = random.nextInt(tileentitychest.getSizeInventory());
            tileentitychest.setInventorySlotContents(slot, itemstack);
        }
        world.setTileEntity(x, y, z, (TileEntity)tileentitychest);
        return true;
    }
    
    public static boolean addMineralChest(final Random random, final World world, final int x, final int y, final int z) {
        world.setBlock(x, y, z, (Block)Blocks.chest, 0, 0);
        final TileEntityChest tileentitychest = new TileEntityChest();
        final int itemsCount = random.nextInt(6) + 2;
        ItemStack itemstack = null;
        for (int f = 0; f < itemsCount; ++f) {
            itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.mineralList, random);
            final int slot = random.nextInt(tileentitychest.getSizeInventory());
            tileentitychest.setInventorySlotContents(slot, itemstack);
        }
        world.setTileEntity(x, y, z, (TileEntity)tileentitychest);
        return true;
    }
    
    public static boolean addWeaponChest(final Random random, final World world, final int x, final int y, final int z) {
        world.setBlock(x, y, z, (Block)Blocks.chest, 0, 0);
        final TileEntityChest tileentitychest = new TileEntityChest();
        final int itemsCount = random.nextInt(6) + 1;
        ItemStack itemstack = null;
        for (int f = 0; f < itemsCount; ++f) {
            itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.weaponList, random);
            if (itemstack != null) {
                BDHelper.EnchantItemRandomly(itemstack, random);
            }
            final int slot = random.nextInt(tileentitychest.getSizeInventory());
            tileentitychest.setInventorySlotContents(slot, itemstack);
        }
        world.setTileEntity(x, y, z, (TileEntity)tileentitychest);
        return true;
    }
    
    public static boolean addChest(final Random random, final World world, final int x, final int y, final int z) {
        world.setBlock(x, y, z, (Block)Blocks.chest, 0, 3);
        int itemsCount = random.nextInt(8) + 1;
        final TileEntityChest tileentitychest = new TileEntityChest();
        int f = 0;
        if (random.nextInt(150) == 0) {
            final Item record = random.nextBoolean() ? Items.record_11 : Items.record_13;
            tileentitychest.setInventorySlotContents(f, new ItemStack(record));
            ++f;
            ++itemsCount;
        }
        while (f < itemsCount) {
            final ItemStack itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.chestList, random);
            final int slot = random.nextInt(tileentitychest.getSizeInventory());
            tileentitychest.setInventorySlotContents(slot, itemstack);
            ++f;
        }
        world.setTileEntity(x, y, z, (TileEntity)tileentitychest);
        return true;
    }
    
    public static Schematic getNBTMap(final String mapDir) {
        return getNBTMap(new File(BDHelper.getAppDir(), BDHelper.getInfoDir() + mapDir));
    }
    
    public static Schematic getRandomNBTMap(final String d, final Random random) {
        final File dir = new File(BDHelper.getAppDir(), BDHelper.getInfoDir() + d);
        final File[] file = dir.listFiles();
        int s = 0;
        try {
            if (file.length > 1) {
                s = random.nextInt(file.length);
            }
            final File mapFile = file[s];
            return getNBTMap(mapFile);
        }
        catch (final Throwable throwable1) {
            final String type = dir.exists() ? "empty" : "missing";
            final CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Error loading dungeon from " + type + " folder: " + dir.getPath());
            throw new ReportedException(crashreport);
        }
    }
    
    public static Schematic getNBTMap(final File file) {
        return new Schematic(file);
    }
    
    static {
        BuilderHelper.builderHelper = new BuilderHelper();
    }
}
