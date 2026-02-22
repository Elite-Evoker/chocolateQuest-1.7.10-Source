package com.chocolate.chocolateQuest.utils;

public class Vec4I
{
    public int xCoord;
    public int yCoord;
    public int zCoord;
    public int rot;
    
    public Vec4I(final int x, final int y, final int z, final int rot) {
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
        this.rot = rot;
    }
    
    public Vec4I(final Vec4I vec) {
        this.xCoord = vec.xCoord;
        this.yCoord = vec.yCoord;
        this.zCoord = vec.zCoord;
        this.rot = vec.rot;
    }
}
