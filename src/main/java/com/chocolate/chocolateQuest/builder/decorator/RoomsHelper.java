package com.chocolate.chocolateQuest.builder.decorator;

import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomStairs;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomLibrary;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomKitchen;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomBedRoom;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomDinning;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomJail;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomStorage;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomArmory;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomFlag;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomEnchantment;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomBlackSmith;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomAlchemy;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomLibraryBig;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomNetherPortal;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomEndPortal;
import java.util.Random;

public class RoomsHelper
{
    static final int NORTH = 0;
    static final int EAST = 2;
    static final int SOUTH = 1;
    static final int WEST = 3;
    
    public static RoomBase getRoom(final Random random, final int sizeX, final int sizeZ, final BuildingProperties data) {
        final RoomBase room = getRandomRoomType(random);
        room.configure(sizeX, sizeZ, data);
        return room;
    }
    
    public static RoomBase getRandomRoomType(final Random random) {
        if (random.nextInt(400) == 0) {
            return new RoomEndPortal();
        }
        if (random.nextInt(100) == 0) {
            return new RoomNetherPortal();
        }
        if (random.nextInt(60) == 0) {
            return new RoomLibraryBig();
        }
        if (random.nextInt(60) == 0) {
            return new RoomAlchemy();
        }
        if (random.nextInt(60) == 0) {
            return new RoomBlackSmith();
        }
        if (random.nextInt(60) == 0) {
            return new RoomEnchantment();
        }
        if (random.nextInt(60) == 0) {
            return new RoomFlag();
        }
        if (random.nextInt(60) == 0) {
            return new RoomArmory();
        }
        if (random.nextInt(60) == 0) {
            return new RoomStorage();
        }
        if (random.nextInt(60) == 0) {
            return new RoomJail();
        }
        if (random.nextInt(60) == 0) {
            return new RoomDinning();
        }
        switch (random.nextInt(5)) {
            case 0: {
                return new RoomBedRoom();
            }
            case 1: {
                return new RoomKitchen();
            }
            case 2: {
                return new RoomLibrary();
            }
            default: {
                return new RoomBase();
            }
        }
    }
    
    public static void buildRooms(final World world, final Random random, final RoomBase[][] rooms, final int x, final int y, final int z, final BuildingProperties props) {
        final int roomsX = rooms.length;
        final int roomsZ = rooms[0].length;
        final int roomSizeX = rooms[0][0].sizeX;
        final int roomSizeZ = rooms[0][0].sizeZ;
        final int floorHeight = props.floorHeight;
        for (int currentRoomX = 0; currentRoomX < roomsX; ++currentRoomX) {
            for (int currentRoomZ = 0; currentRoomZ < roomsZ; ++currentRoomZ) {
                final int posX = currentRoomX * roomSizeX + x;
                final int posZ = currentRoomZ * roomSizeZ + z;
                final RoomBase room = rooms[currentRoomX][currentRoomZ];
                if (room != null) {
                    room.decorate(random, world, posX, y, posZ);
                }
            }
        }
    }
    
    public static RoomBase[][] getRoomsArray(final RoomBase[][] rooms, final BuildingProperties data, final Random random, final int height, final int sizeX, final int sizeZ, final boolean addStairs) {
        final int roomsX = rooms.length;
        final int roomsZ = rooms[0].length;
        for (int i = 0; i < roomsX; ++i) {
            for (int k = 0; k < roomsZ; ++k) {
                rooms[i][k] = getRoom(random, sizeX, sizeZ, data);
                if (i == 0) {
                    rooms[i][k].wallEast = false;
                }
                if (i == roomsX - 1) {
                    rooms[i][k].wallWest = false;
                }
                if (k == 0) {
                    rooms[i][k].wallNorth = false;
                }
                if (k == roomsZ - 1) {
                    rooms[i][k].wallSouth = false;
                }
            }
        }
        for (int i = 0; i < roomsX; ++i) {
            for (int k = 0; k < roomsZ; ++k) {
                if (i > 0 && rooms[i][k].getType() == rooms[i - 1][k].getType()) {
                    rooms[i][k].decorateEast = false;
                    rooms[i][k].wallEast = false;
                }
                if (i < roomsX - 1 && rooms[i][k].getType() == rooms[i + 1][k].getType()) {
                    rooms[i][k].decorateWest = false;
                    rooms[i][k].wallWest = false;
                }
                if (k > 0 && rooms[i][k].getType() == rooms[i][k - 1].getType()) {
                    rooms[i][k].decorateNorth = false;
                    rooms[i][k].wallNorth = false;
                }
                if (k < roomsZ - 1 && rooms[i][k].getType() == rooms[i][k + 1].getType()) {
                    rooms[i][k].decorateSouth = false;
                    rooms[i][k].wallSouth = false;
                }
            }
        }
        addDoorToRoom(rooms, 0, 0, random);
        final int x = random.nextInt(roomsX);
        final int z = random.nextInt(roomsZ);
        if (addStairs) {
            rooms[x][z] = new RoomStairs().copyDataFrom(rooms[x][z]);
        }
        return rooms;
    }
    
    public static boolean addDoorToRoom(final RoomBase[][] rooms, final int x, final int z, final Random random) {
        int side = random.nextInt(4);
        final int rotation = random.nextBoolean() ? 1 : -1;
        rooms[x][z].doorSet = true;
        for (int cont = 0; cont < 4; ++cont) {
            if (addDoorToSide(rooms, x, z, side)) {
                int nx = x;
                int nz = z;
                if (side == 0) {
                    nz = z - 1;
                    rooms[x][z].doorNorth = true;
                    rooms[x][z - 1].doorSouth = true;
                }
                else if (side == 1) {
                    nz = z + 1;
                    rooms[x][z].doorSouth = true;
                    rooms[x][z + 1].doorNorth = true;
                }
                else if (side == 2) {
                    nx = x - 1;
                    rooms[x][z].doorEast = true;
                    rooms[x - 1][z].doorWest = true;
                }
                else if (side == 3) {
                    nx = x + 1;
                    rooms[x][z].doorWest = true;
                    rooms[x + 1][z].doorEast = true;
                }
                addDoorToRoom(rooms, nx, nz, random);
            }
            side += rotation;
            if (side < 0) {
                side = 3;
            }
            if (side > 3) {
                side = 0;
            }
        }
        return true;
    }
    
    public static boolean addDoorToSide(final RoomBase[][] rooms, final int x, final int z, final int side) {
        if ((z == 0 && side == 0) || (z == rooms[0].length - 1 && side == 1) || (x == 0 && side == 2) || (x == rooms.length - 1 && side == 3)) {
            return false;
        }
        int nx = x;
        int nz = z;
        if (side == 0) {
            nz = z - 1;
        }
        else if (side == 1) {
            nz = z + 1;
        }
        else if (side == 2) {
            nx = x - 1;
        }
        else if (side == 3) {
            nx = x + 1;
        }
        return !rooms[nx][nz].doorSet;
    }
}
