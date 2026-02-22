package com.chocolate.chocolateQuest.utils;

public class Vec3I
{
    public int xCoord;
    public int yCoord;
    public int zCoord;
    
    public Vec3I(final int x, final int y, final int z) {
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }
    
    public Vec3I(final Vec3I vec) {
        this.xCoord = vec.xCoord;
        this.yCoord = vec.yCoord;
        this.zCoord = vec.zCoord;
    }
}
