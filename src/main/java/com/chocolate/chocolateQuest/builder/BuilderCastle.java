package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomBoss;
import com.chocolate.chocolateQuest.builder.decorator.RoomsHelper;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import com.chocolate.chocolateQuest.builder.decorator.TowerRound;
import com.chocolate.chocolateQuest.builder.decorator.TowerSquare;
import com.chocolate.chocolateQuest.builder.decorator.EntranceGenerator;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import java.util.Random;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import java.util.Properties;
import com.chocolate.chocolateQuest.builder.decorator.BuildingProperties;
import com.chocolate.chocolateQuest.API.BuilderBase;

public class BuilderCastle extends BuilderBase
{
    final int MIN_ROOM_SIZE = 5;
    int roomSize;
    int maxSize;
    int posY;
    BuildingProperties properties;
    
    public BuilderCastle() {
        this.roomSize = 10;
        this.maxSize = 60;
        this.posY = 64;
    }
    
    @Override
    public BuilderBase load(final Properties prop) {
        this.maxSize = Math.max(30, HelperReadConfig.getIntegerProperty(prop, "maxSize", 60));
        this.roomSize = Math.max(6, HelperReadConfig.getIntegerProperty(prop, "roomSize", 8));
        (this.properties = new BuildingProperties()).load(prop);
        return this;
    }
    
    @Override
    public void generate(final Random random, final World world, int x, int z, final int idMob) {
        x -= this.maxSize / 2;
        z -= this.maxSize / 2;
        this.generate(random, world, x, this.posY, z, idMob);
    }
    
    @Override
    public void generate(final Random random, final World world, final int x, final int y, final int z, final int idMob) {
        this.properties.initialize(random);
        final int offset = 4;
        BuilderHelper.clearArea(random, world, x - offset, y, z - offset, this.maxSize + offset * 2, this.maxSize + offset * 2);
        this.generateCastleStructure(random, world, x, y, z, this.maxSize, this.maxSize);
    }
    
    public void generateCastleStructure(final Random random, final World world, int x, final int y, int z, final int maxSizeX, final int maxSizeZ) {
        final int sizeQuartX = maxSizeX / 4;
        final int sizeQuartZ = maxSizeZ / 4;
        int sizeX = sizeQuartX + random.nextInt(sizeQuartX * 3);
        int sizeZ = sizeQuartZ + random.nextInt(sizeQuartZ * 3);
        final int offsetX = random.nextInt(sizeQuartX);
        final int offsetZ = random.nextInt(sizeQuartZ);
        sizeX = Math.max(this.roomSize, sizeX);
        sizeZ = Math.max(this.roomSize, sizeZ);
        x += offsetX;
        z += offsetZ;
        boolean walkableRoof = false;
        if (Math.min(sizeX, sizeZ) > this.roomSize) {
            this.generateCastleStructure(random, world, x, y + (this.properties.floorHeight + 1) * 2, z, sizeX, sizeZ);
            walkableRoof = true;
        }
        this.generateSquaredStructure(random, world, x, y, z, sizeX, sizeZ, 1, walkableRoof, false);
        this.generateStructureAtSide(random, world, x + sizeX, y, z, sizeX, offsetZ, 1, ForgeDirection.NORTH);
        this.generateStructureAtSide(random, world, x, y, z + sizeZ, sizeX, maxSizeZ - sizeZ - offsetZ, 1, ForgeDirection.SOUTH);
        this.generateStructureAtSide(random, world, x, y, z + sizeZ, offsetX, sizeZ, 1, ForgeDirection.WEST);
        this.generateStructureAtSide(random, world, x + sizeX, y, z, maxSizeX - sizeX - offsetX, sizeZ, 1, ForgeDirection.EAST);
        final EntranceGenerator entranceGenerator = new EntranceGenerator(this.properties);
        entranceGenerator.generateEntance(world, x + sizeX / 2, y, z, ForgeDirection.NORTH);
        entranceGenerator.generateEntance(world, x + sizeX / 2, y, z + sizeZ, ForgeDirection.SOUTH);
        entranceGenerator.generateEntance(world, x, y, z + sizeZ / 2, ForgeDirection.WEST);
        entranceGenerator.generateEntance(world, x + sizeX, y, z + sizeZ / 2, ForgeDirection.EAST);
    }
    
    public void generateStructureAtSide(final Random random, final World world, int x, final int y, int z, int maxSizeX, int maxSizeZ, final int floors, final ForgeDirection side) {
        if (maxSizeX <= 0 || maxSizeZ <= 0) {
            return;
        }
        final int sizeX = Math.max(random.nextInt(maxSizeX), this.roomSize);
        final int sizeZ = Math.max(random.nextInt(maxSizeZ), this.roomSize);
        final boolean addStairs = false;
        int emptyAreaLength = 0;
        if (side.offsetX != 0) {
            emptyAreaLength = maxSizeX;
        }
        else if (side.offsetZ != 0) {
            emptyAreaLength = maxSizeZ;
        }
        if (emptyAreaLength > this.roomSize + this.roomSize / 2) {
            this.generateSquaredStructure(random, world, x, y, z, sizeX, sizeZ, floors, false, false, addStairs);
            final int doorOffsetX = side.offsetZ * sizeZ / 2;
            final int doorOffsetZ = side.offsetX * sizeX / 2;
            for (int h = 0; h <= floors; ++h) {
                this.properties.doors.generate(random, world, x + doorOffsetX, y + 1 + (this.properties.floorHeight + 1) * h, z + doorOffsetZ, side);
            }
            this.properties.doors.generateSquared(random, world, x + doorOffsetX, y + 1 + (this.properties.floorHeight + 1) * (floors + 1), z + doorOffsetZ, side);
            emptyAreaLength = 0;
            if (side.offsetX != 0) {
                emptyAreaLength = maxSizeX - sizeX;
            }
            else if (side.offsetZ != 0) {
                emptyAreaLength = maxSizeZ - sizeZ;
            }
            if (side.offsetX < 0) {
                x += sizeX;
            }
            if (side.offsetZ < 0) {
                z += sizeZ;
            }
            final int offsetX = side.offsetX * sizeX;
            final int offsetZ = side.offsetZ * sizeZ;
            if (side.offsetX != 0) {
                maxSizeX -= sizeX;
            }
            else if (side.offsetZ != 0) {
                maxSizeZ -= sizeZ;
            }
            if (emptyAreaLength > this.roomSize) {
                this.generateStructureAtSide(random, world, x + offsetX, y, z + offsetZ, sizeX, sizeZ, floors, side);
            }
            else if (emptyAreaLength > 5) {
                if (side.offsetX != 0) {
                    maxSizeZ = sizeZ;
                }
                else if (side.offsetZ != 0) {
                    maxSizeX = sizeX;
                }
                this.generateTowersAtSide(random, world, x + offsetX, y, z + offsetZ, maxSizeX, maxSizeZ, floors, side);
            }
        }
        else {
            if (emptyAreaLength <= 5) {
                return;
            }
            this.generateTowersAtSide(random, world, x, y, z, maxSizeX, maxSizeZ, floors, side);
        }
    }
    
    public void generateTowersAtSide(final Random random, final World world, final int x, final int y, final int z, final int maxSizeX, final int maxSizeZ, final int floors, final ForgeDirection side) {
        int emptyAreaLength = 0;
        if (side.offsetX != 0) {
            emptyAreaLength = maxSizeX;
        }
        else if (side.offsetZ != 0) {
            emptyAreaLength = maxSizeZ;
        }
        int width = 0;
        if (side.offsetZ != 0) {
            width = maxSizeX;
        }
        else if (side.offsetX != 0) {
            width = maxSizeZ;
        }
        if (width < this.roomSize * 2) {
            width = Math.max(5, Math.min(emptyAreaLength, width));
            final TowerSquare tower = random.nextBoolean() ? new TowerSquare(this.properties) : new TowerRound(this.properties);
            tower.configure(floors, width);
            final int offsetX = side.offsetZ * maxSizeX / 2;
            final int offsetZ = side.offsetX * maxSizeZ / 2;
            tower.buildTower(random, world, x + offsetX, y, z + offsetZ, side);
        }
        else {
            width = Math.max(6, Math.min(emptyAreaLength, width));
            int offsetX2 = side.offsetZ * (maxSizeX - 3);
            int offsetZ2 = side.offsetX * (maxSizeZ - 3);
            TowerSquare tower2 = random.nextBoolean() ? new TowerSquare(this.properties) : new TowerRound(this.properties);
            tower2.configure(floors, width);
            tower2.buildTower(random, world, x + offsetX2, y, z + offsetZ2, side);
            offsetX2 = side.offsetZ * 3;
            offsetZ2 = side.offsetX * 3;
            tower2 = (random.nextBoolean() ? new TowerSquare(this.properties) : new TowerRound(this.properties));
            tower2.configure(floors, width);
            tower2.buildTower(random, world, x + offsetX2, y, z + offsetZ2, side);
        }
    }
    
    public void generatePagoda(final Random random, final World world, final int x, final int y, final int z, final int sizeX, final int sizeZ) {
        final int stepSize = 4;
        final int stepSize2 = 2;
        final int chapelFloors = 3;
        final int xPos = x;
        final int yPos = y;
        final int zPos = z;
        final int floorHeight = this.properties.floorHeight;
        boolean boss = true;
        for (int i = chapelFloors; i >= 0; --i) {
            if (i > 1) {
                if (i * stepSize > sizeX - this.roomSize) {
                    continue;
                }
                if (i * stepSize > sizeZ - this.roomSize) {
                    continue;
                }
            }
            final int X = xPos + stepSize2 * i;
            final int Y = yPos + (floorHeight + 1) * i;
            final int Z = zPos + stepSize2 * i;
            final int sX = sizeX - stepSize * i;
            final int sZ = sizeZ - stepSize * i;
            this.generateSquaredStructure(random, world, X, Y, Z, sX, sZ, 0, !boss, boss);
            boss = false;
            this.properties.roof.roofPyramid(world, X - stepSize2, Y + floorHeight - 1, Z - stepSize2, sX + stepSize, sZ + stepSize, stepSize2);
        }
    }
    
    public void generateSquaredStructure(final Random random, final World world, final int x, final int y, final int z, final int sizeX, final int sizeZ, final int floors, final boolean walkableRoof, final boolean boss) {
        this.generateSquaredStructure(random, world, x, y, z, sizeX, sizeZ, floors, walkableRoof, boss, true);
    }
    
    public void generateSquaredStructure(final Random random, final World world, final int x, final int y, final int z, final int sizeX, final int sizeZ, final int floors, final boolean walkableRoof, final boolean boss, final boolean addStairs) {
        final int roomsX = Math.max(1, sizeX / this.roomSize);
        final int roomsZ = Math.max(1, sizeZ / this.roomSize);
        final int roomSizeX = sizeX / roomsX;
        final int roomSizeZ = sizeZ / roomsZ;
        final int lastRoomOffsetX = sizeX - roomsX * roomSizeX;
        final int lastRoomOffsetZ = sizeZ - roomsZ * roomSizeZ;
        int currentY = y;
        final int floorHeight = this.properties.floorHeight;
        for (int i = 0; i <= sizeX; ++i) {
            for (int j = 0; j < sizeZ; ++j) {
                this.properties.floor.generateFloor(world, x + i, currentY, z + j);
            }
        }
        for (int currentfloor = 0; currentfloor <= floors; ++currentfloor) {
            for (int k = 0; k <= sizeX; ++k) {
                for (int l = 0; l < sizeZ; ++l) {
                    this.properties.wallBlock.placeBlock(world, x + k, currentY + floorHeight, z + l, random);
                    if (currentfloor != floors) {
                        this.properties.floor.generateFloor(world, x + k, currentY + floorHeight + 1, z + l);
                    }
                    else {
                        this.properties.wallBlock.placeBlock(world, x + k, currentY + floorHeight + 1, z + l, random);
                    }
                }
            }
            ++currentY;
            for (int k = 0; k <= sizeX; ++k) {
                for (int l = -1; l <= floorHeight; ++l) {
                    this.properties.wallBlock.placeBlock(world, x + k, currentY + l, z, random);
                    this.properties.wallBlock.placeBlock(world, x + k, currentY + l, z + sizeZ, random);
                }
                if (k > 0 && k < sizeX) {
                    this.properties.window.generateWindowX(world, x + k, currentY, z);
                    this.properties.window.generateWindowX(world, x + k, currentY, z + sizeZ);
                }
            }
            for (int k = 0; k <= sizeZ; ++k) {
                for (int l = -1; l <= floorHeight; ++l) {
                    this.properties.wallBlock.placeBlock(world, x, currentY + l, z + k, random);
                    this.properties.wallBlock.placeBlock(world, x + sizeX, currentY + l, z + k, random);
                }
                if (k > 0 && k < sizeZ) {
                    this.properties.window.generateWindowZ(world, x, currentY, z + k);
                    this.properties.window.generateWindowZ(world, x + sizeX, currentY, z + k);
                }
            }
            RoomBase[][] roomsArray = new RoomBase[roomsX][roomsZ];
            roomsArray = RoomsHelper.getRoomsArray(roomsArray, this.properties, random, floorHeight, roomSizeX, roomSizeZ, addStairs);
            for (int m = 0; m < roomsArray.length; ++m) {
                final RoomBase room = roomsArray[m][roomsArray[0].length - 1];
                if (room != null) {
                    final RoomBase roomBase = room;
                    roomBase.sizeZ += lastRoomOffsetZ;
                }
            }
            for (int m = 0; m < roomsArray[0].length; ++m) {
                final RoomBase room = roomsArray[roomsArray.length - 1][m];
                if (room != null) {
                    final RoomBase roomBase2 = room;
                    roomBase2.sizeX += lastRoomOffsetX;
                }
            }
            if (currentfloor == floors && boss) {
                roomsArray[0][0] = new RoomBoss().copyDataFrom(roomsArray[0][0]);
            }
            RoomsHelper.buildRooms(world, random, roomsArray, x, currentY, z, this.properties);
            currentY += floorHeight;
        }
        this.properties.roof.generateRoof(world, x, currentY + 1, z, sizeX, sizeZ, walkableRoof);
    }
    
    @Override
    public String getName() {
        return "castle";
    }
}
