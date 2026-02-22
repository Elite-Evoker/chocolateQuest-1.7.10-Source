package com.chocolate.chocolateQuest.builder.decorator;

import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import com.chocolate.chocolateQuest.block.BlockAltarTileEntity;
import com.chocolate.chocolateQuest.misc.EquipementHelper;
import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapData;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;

public class RoomBase
{
    public static final int CENTER = 5;
    public static final int BEDROOM = 0;
    public static final int KITCHEN = 1;
    public static final int LIBRARY = 2;
    public static final int MONSTER_ROOM = 3;
    public static final int OTHER_MONSTER_ROOM = 4;
    public static final int BIG_LIBRARY = 200;
    public static final int ALCHEMY = 201;
    public static final int BLACKSMITH = 202;
    public static final int ENCHANTMENT_ROOM = 203;
    public static final int FLAG_ROOM = 204;
    public static final int MAGIC_ROOM = 205;
    public static final int STORAGE = 206;
    public static final int PORTAL = 208;
    public static final int STAIRS = 300;
    public static final int LADDERS = 301;
    public static final int FULL_MONSTER_ROOM = 302;
    public static final int BOSS_ROOM = 303;
    public static final int ARCHERS = 304;
    public static final int DINNING_ROOM = 400;
    public static final int JAIL = 401;
    public static final int SPIDER_NEST = 402;
    public static final int TREASURE = 403;
    public static final int GARDEN = 500;
    public int sizeX;
    public int sizeZ;
    public boolean wallNorth;
    public boolean wallSouth;
    public boolean wallEast;
    public boolean wallWest;
    public boolean doorNorth;
    public boolean doorSouth;
    public boolean doorEast;
    public boolean doorWest;
    public boolean decorateNorth;
    public boolean decorateSouth;
    public boolean decorateEast;
    public boolean decorateWest;
    public boolean doorSet;
    public int stairsHeight;
    public BuildingProperties properties;
    
    public RoomBase() {
        this.wallNorth = true;
        this.wallSouth = true;
        this.wallEast = true;
        this.wallWest = true;
        this.doorNorth = false;
        this.doorSouth = false;
        this.doorEast = false;
        this.doorWest = false;
        this.decorateNorth = true;
        this.decorateSouth = true;
        this.decorateEast = true;
        this.decorateWest = true;
        this.doorSet = false;
        this.stairsHeight = 0;
    }
    
    public RoomBase(final int sizeX, final int sizeZ, final int height, final int roomType, final BuildingProperties data) {
        this.wallNorth = true;
        this.wallSouth = true;
        this.wallEast = true;
        this.wallWest = true;
        this.doorNorth = false;
        this.doorSouth = false;
        this.doorEast = false;
        this.doorWest = false;
        this.decorateNorth = true;
        this.decorateSouth = true;
        this.decorateEast = true;
        this.decorateWest = true;
        this.doorSet = false;
        this.stairsHeight = 0;
        this.configure(sizeX, sizeZ, data);
    }
    
    public RoomBase copyDataFrom(final RoomBase room) {
        this.configure(room.sizeX, room.sizeZ, room.properties);
        this.wallNorth = room.wallNorth;
        this.wallSouth = room.wallSouth;
        this.wallEast = room.wallEast;
        this.wallWest = room.wallWest;
        this.doorNorth = room.doorNorth;
        this.doorSouth = room.doorSouth;
        this.doorEast = room.doorEast;
        this.doorWest = room.doorWest;
        this.decorateNorth = room.decorateNorth;
        this.decorateSouth = room.decorateSouth;
        this.decorateEast = room.decorateEast;
        this.decorateWest = room.decorateWest;
        return this;
    }
    
    public void configure(final int sizeX, final int sizeZ, final BuildingProperties data) {
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
        this.properties = data;
    }
    
    public int getType() {
        return -1;
    }
    
    public RoomBase clearWalls() {
        this.wallEast = false;
        this.decorateEast = false;
        this.wallNorth = false;
        this.decorateNorth = false;
        this.wallSouth = false;
        this.decorateSouth = false;
        this.wallWest = false;
        this.decorateWest = false;
        return this;
    }
    
    public void decorate(final Random random, final World world, final int posX, final int posY, final int posZ) {
        final int roomCenterX = posX + this.sizeX / 2;
        final int roomCenterZ = posZ + this.sizeZ / 2;
        final int floorHeight = this.properties.floorHeight;
        for (int roomPos = 0; roomPos <= this.sizeZ; ++roomPos) {
            if (this.wallEast) {
                for (int h = 0; h < this.properties.floorHeight; ++h) {
                    this.properties.wallBlock.placeBlock(world, posX, posY + h, posZ + roomPos, random);
                }
            }
            if (this.wallWest) {
                for (int h = 0; h < this.properties.floorHeight; ++h) {
                    this.properties.wallBlock.placeBlock(world, posX + this.sizeX, posY + h, posZ + roomPos, random);
                }
            }
        }
        for (int roomPos = 0; roomPos <= this.sizeX; ++roomPos) {
            if (this.wallNorth) {
                for (int h = 0; h < this.properties.floorHeight; ++h) {
                    this.properties.wallBlock.placeBlock(world, posX + roomPos, posY + h, posZ, random);
                }
            }
            if (this.wallSouth) {
                for (int h = 0; h < this.properties.floorHeight; ++h) {
                    this.properties.wallBlock.placeBlock(world, posX + roomPos, posY + h, posZ + this.sizeZ, random);
                }
            }
        }
        this.addRoomDecoration(random, world, posX, posY, posZ);
        world.setBlock(roomCenterX, posY + floorHeight - 1, roomCenterZ, Blocks.redstone_lamp);
        world.setBlock(roomCenterX, posY + floorHeight - 2, roomCenterZ, Blocks.lever, 0, 3);
        this.decorateDoors(random, world, posX, posY, posZ);
        for (int i = 0; i < this.properties.mobRatio; ++i) {
            final int x = posX + random.nextInt(this.sizeX);
            final int y = posY;
            final int z = posZ + random.nextInt(this.sizeZ);
            if (world.isAirBlock(x, y, z)) {
                this.addMob(random, world, x, y, z);
            }
        }
    }
    
    public void addRoomDecoration(final Random random, final World world, final int posX, final int posY, final int posZ) {
        final int roomCenterX = posX + this.sizeX / 2;
        final int roomCenterZ = posZ + this.sizeZ / 2;
        for (int roomPos = 1; roomPos < this.sizeZ; ++roomPos) {
            if (this.decorateEast) {
                this.placeDecorationBlock(random, world, posX + 1, posY, posZ + roomPos, 1);
            }
            if (this.decorateWest) {
                this.placeDecorationBlock(random, world, posX + this.sizeX - 1, posY, posZ + roomPos, 2);
            }
        }
        for (int roomPos = 1; roomPos < this.sizeX; ++roomPos) {
            if (this.decorateNorth) {
                this.placeDecorationBlock(random, world, posX + roomPos, posY, posZ + 1, 3);
            }
            if (this.decorateSouth) {
                this.placeDecorationBlock(random, world, posX + roomPos, posY, posZ + this.sizeZ - 1, 4);
            }
        }
        this.placeDecorationBlock(random, world, roomCenterX, posY, roomCenterZ, 5);
    }
    
    public void decorateDoors(final Random random, final World world, final int posX, final int posY, final int posZ) {
        if (this.wallWest && this.doorWest) {
            this.properties.doors.generate(random, world, posX + this.sizeX, posY, posZ + this.sizeZ / 2, ForgeDirection.WEST);
        }
        if (this.wallSouth && this.doorSouth) {
            this.properties.doors.generate(random, world, posX + this.sizeX / 2, posY, posZ + this.sizeZ, ForgeDirection.SOUTH);
        }
        if (this.wallEast && this.doorEast) {
            this.properties.doors.generate(random, world, posX, posY, posZ + this.sizeZ / 2, ForgeDirection.EAST);
        }
        if (this.wallNorth && this.doorNorth) {
            this.properties.doors.generate(random, world, posX + this.sizeX / 2, posY, posZ, ForgeDirection.NORTH);
        }
    }
    
    public void placeDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (random.nextInt(100) == 0) {
            this.placePainting(random, world, x, y + 1, z, side);
        }
        if (side != 5) {
            this.placeRandomDecorationBlock(random, world, x, y, z, side);
        }
    }
    
    public void placeRandomDecorationBlock(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (random.nextInt(10) == 0) {
            if (random.nextInt(4000) == 0) {
                world.setBlock(x, y, z, Blocks.ender_chest);
            }
            else if (random.nextInt(2000) == 0) {
                world.setBlock(x, y, z, Blocks.end_portal_frame);
            }
            else if (random.nextInt(800) == 0) {
                world.setBlock(x, y, z, Blocks.anvil);
            }
            else if (random.nextInt(400) == 0) {
                world.setBlock(x, y, z, Blocks.jukebox);
            }
            else if (random.nextInt(500) == 0) {
                world.setBlockToAir(x, y, z);
                world.setBlock(x, y, z, Blocks.brewing_stand, 0, 3);
            }
            else if (random.nextInt(400) == 0) {
                world.setBlock(x, y, z, Blocks.enchanting_table);
            }
            else if (random.nextInt(100) == 0) {
                world.setBlock(x, y + random.nextInt(this.properties.floorHeight - 2) + 1, z, Blocks.web);
            }
            else if (random.nextInt(100) == 0) {
                this.placeCake(random, world, x, y, z);
            }
            else if (random.nextInt(100) == 0) {
                this.placeBed(random, world, x, y, z, side);
            }
            else if (random.nextInt(50) == 0) {
                world.setBlock(x, y, z, (Block)Blocks.cauldron, random.nextInt(3), 3);
            }
            else if (random.nextInt(50) == 0) {
                this.placeFrame(random, world, x, y + 1, z, side, new ItemStack(Items.clock));
            }
            else if (random.nextInt(50) == 0) {
                this.placeShied(random, world, x, y + 1, z, side);
            }
            else if (random.nextInt(50) == 0) {
                this.placeFurnace(random, world, x, y, z, side);
            }
            else if (random.nextInt(20) == 0) {
                world.setBlock(x, y, z, Blocks.bookshelf);
            }
            else if (random.nextInt(15) == 0) {
                this.placeFlowerPot(random, world, x, y, z);
            }
            else if (random.nextInt(20) == 0) {
                world.setBlock(x, y, z, Blocks.crafting_table);
            }
        }
    }
    
    public void decorateFullMonsterRoom(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side == 5) {
            this.addMob(random, world, x + 1, y, z + 1);
            if (random.nextInt(8) == 0) {
                this.addMob(random, world, x + 1, y, z);
            }
            if (random.nextInt(8) == 0) {
                this.addMob(random, world, x - 1, y, z);
            }
            if (random.nextInt(8) == 0) {
                this.addMob(random, world, x, y, z + 1);
            }
            if (random.nextInt(8) == 0) {
                this.addMob(random, world, x, y, z - 1);
            }
            if (random.nextInt(8) == 0) {
                this.addMob(random, world, x + 1, y, z + 1);
            }
            if (random.nextInt(8) == 0) {
                this.addMob(random, world, x - 1, y, z - 1);
            }
            if (random.nextInt(8) == 0) {
                this.addMob(random, world, x - 1, y, z + 1);
            }
            if (random.nextInt(8) == 0) {
                this.addMob(random, world, x + 1, y, z - 1);
            }
            if (random.nextInt(40) == 0) {
                BuilderHelper.addSpawner(random, world, x, y - 1, z, this.properties.mobID);
            }
        }
        else if (random.nextInt(15) == 0) {
            this.addMob(random, world, x, y, z);
        }
    }
    
    public void addMob(final Random random, final World world, final int x, final int y, final int z) {
        int lvl = random.nextInt(5);
        if (random.nextInt(5) == 0) {
            lvl += 5;
        }
        if (random.nextInt(5) == 0) {
            lvl += 5;
        }
        world.setBlock(x, y, z, ChocolateQuest.spawner, 0, 3);
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof BlockMobSpawnerTileEntity) {
            final BlockMobSpawnerTileEntity spawner = (BlockMobSpawnerTileEntity)te;
            spawner.mob = this.properties.mobID;
            spawner.metadata = lvl;
        }
    }
    
    public void addWeaponChest(final Random random, final World world, final int x, final int y, final int z, final int side) {
        BuilderHelper.addWeaponChest(random, world, x, y, z);
    }
    
    public void placeMap(final Random random, final World world, final int x, final int y, final int z, final int side) {
        final ItemStack is = new ItemStack((Item)Items.map, 1, world.getUniqueDataId("map"));
        final String s = "map_" + is.getMetadata();
        final MapData mapdata = new MapData(s);
        world.setItemData(s, (WorldSavedData)mapdata);
        mapdata.scale = (byte)random.nextInt(5);
        this.placeFrame(random, world, mapdata.xCenter = x, y, mapdata.zCenter = z, side, is);
    }
    
    public void placeShied(final Random random, final World world, final int x, final int y, final int z, final int side) {
        final DungeonMonstersBase mobType = RegisterDungeonMobs.mobList.get(this.properties.mobID);
        if (mobType != null) {
            this.placeFrame(random, world, x, y, z, side, new ItemStack(ChocolateQuest.shield, 1, mobType.getFlagId()));
        }
    }
    
    public void placeArmorStand(final Random random, final World world, final int x, final int y, final int z, final int side) {
        int rotation = 0;
        switch (side) {
            case 0: {
                rotation = random.nextInt(360);
            }
            case 1: {
                rotation = -90;
                break;
            }
            case 2: {
                rotation = 90;
                break;
            }
            case 4: {
                rotation = 180;
                break;
            }
        }
        world.setBlock(x, y, z, ChocolateQuest.armorStand);
        final BlockArmorStandTileEntity stand = (BlockArmorStandTileEntity)ChocolateQuest.armorStand.createTileEntity(world, 0);
        if (stand != null) {
            stand.rotation = rotation;
            int lvl;
            for (lvl = 0; lvl < 5 && random.nextInt(Math.max(2, lvl)) == 0; ++lvl) {}
            for (int i = 0; i < 4; ++i) {
                stand.cargoItems[i] = EquipementHelper.getArmor(random, i + 1, lvl);
            }
            this.setTileEntity(world, x, y, z, stand);
        }
    }
    
    public void placeTable(final Random random, final World world, final int x, final int y, final int z, final ItemStack is) {
        world.setBlock(x, y, z, ChocolateQuest.table);
        final BlockAltarTileEntity table = (BlockAltarTileEntity)ChocolateQuest.table.createTileEntity(world, 0);
        table.item = is;
        this.setTileEntity(world, x, y, z, table);
    }
    
    public void placeFrame(final Random random, final World world, int x, final int y, int z, int side, final ItemStack is) {
        switch (side) {
            case 1: {
                --x;
                side = 3;
                break;
            }
            case 2: {
                ++x;
                side = 1;
                break;
            }
            case 3: {
                --z;
                side = 0;
                break;
            }
            case 4: {
                ++z;
                side = 2;
                break;
            }
        }
        final EntityItemFrame e = new EntityItemFrame(world, x, y, z, side);
        if (e.onValidSurface()) {
            e.setDisplayedItem(is);
            if (!world.isRemote) {
                world.spawnEntityInWorld((Entity)e);
            }
        }
    }
    
    public void placePainting(final Random random, final World world, int x, final int y, int z, int side) {
        switch (side) {
            case 1: {
                --x;
                side = 3;
                break;
            }
            case 2: {
                ++x;
                side = 1;
                break;
            }
            case 3: {
                --z;
                side = 0;
                break;
            }
            case 4: {
                ++z;
                side = 2;
                break;
            }
        }
        final EntityPainting e = new EntityPainting(world, x, y, z, side);
        if (e.onValidSurface() && !world.isRemote) {
            world.spawnEntityInWorld((Entity)e);
        }
    }
    
    public void placeBed(final Random random, final World world, final int x, final int y, final int z, final int side) {
        if (side > 4) {
            return;
        }
        int xo = 0;
        int zo = 0;
        int md = 0;
        switch (side) {
            case 1: {
                xo = 1;
                md = 1;
                break;
            }
            case 2: {
                xo = -1;
                md = 3;
                break;
            }
            case 3: {
                zo = 1;
                md = 2;
                break;
            }
            case 4: {
                zo = -1;
                md = 0;
                break;
            }
        }
        world.setBlock(x + xo, y, z + zo, Blocks.bed, md, 3);
        world.setBlock(x, y, z, Blocks.bed, md + 8, 3);
    }
    
    public void placeCake(final Random random, final World world, final int x, final int y, final int z) {
        world.setBlock(x, y, z, Blocks.planks, random.nextInt(5), 3);
        final int md = random.nextInt(5);
        world.setBlock(x, y + 1, z, Blocks.cake, md, 3);
    }
    
    public void placeFlowerPot(final Random random, final World world, final int x, final int y, final int z) {
        world.setBlock(x, y, z, Blocks.planks, random.nextInt(5), 3);
        final int md = random.nextInt(16);
        world.setBlock(x, y + 1, z, Blocks.flower_pot, md, 3);
    }
    
    public void placeFurnace(final Random random, final World world, final int x, final int y, final int z, final int side) {
        world.setBlockToAir(x, y, z);
        world.setBlock(x, y, z, Blocks.furnace);
        final TileEntityFurnace furnace = (TileEntityFurnace)world.getTileEntity(x, y, z);
        if (furnace != null) {
            if (random.nextInt(4) == 0) {
                furnace.setInventorySlotContents(1, new ItemStack(Items.coal, 1 + random.nextInt(15)));
            }
            else if (random.nextInt(4) == 0) {
                furnace.setInventorySlotContents(1, new ItemStack(Items.coal, 1 + random.nextInt(15), 1));
            }
            else if (random.nextInt(20) == 0) {
                furnace.setInventorySlotContents(1, new ItemStack(Items.lava_bucket, 1, 1));
            }
            if (random.nextInt(5) == 0) {
                furnace.setInventorySlotContents(2, new ItemStack(Items.iron_ingot, 1 + random.nextInt(3)));
            }
            else if (random.nextInt(7) == 0) {
                furnace.setInventorySlotContents(2, new ItemStack(Items.gold_ingot, 1 + random.nextInt(3)));
            }
            else if (random.nextInt(15) == 0) {
                furnace.setInventorySlotContents(2, new ItemStack(Items.diamond, 1 + random.nextInt(3)));
            }
        }
    }
    
    public void placeFoodFurnace(final Random random, final World world, final int x, final int y, final int z, final int side) {
        world.setBlockToAir(x, y, z);
        world.setBlock(x, y, z, Blocks.furnace, 0, 3);
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te != null) {
            final TileEntityFurnace furnace = (TileEntityFurnace)te;
            if (random.nextInt(4) == 0) {
                furnace.setInventorySlotContents(1, new ItemStack(Items.coal, 1 + random.nextInt(15)));
            }
            else if (random.nextInt(4) == 0) {
                furnace.setInventorySlotContents(1, new ItemStack(Items.coal, 1 + random.nextInt(15), 1));
            }
            else if (random.nextInt(20) == 0) {
                furnace.setInventorySlotContents(1, new ItemStack(Items.lava_bucket));
            }
            if (random.nextInt(5) == 0) {
                furnace.setInventorySlotContents(2, new ItemStack(Items.porkchop, 1 + random.nextInt(3)));
            }
            else if (random.nextInt(5) == 0) {
                furnace.setInventorySlotContents(2, new ItemStack(Items.fish, 1 + random.nextInt(3)));
            }
            else if (random.nextInt(5) == 0) {
                furnace.setInventorySlotContents(2, new ItemStack(Items.beef, 1 + random.nextInt(3)));
            }
        }
    }
    
    public void setTileEntity(final World world, final int x, final int y, final int z, final TileEntity t) {
        world.setTileEntity(x, y, z, t);
    }
}
