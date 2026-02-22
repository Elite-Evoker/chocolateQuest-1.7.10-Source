package com.chocolate.chocolateQuest.builder.decorator;

import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.block.Block;

public class DecoratorFloor
{
    int decoBlock1;
    int decoBlock2;
    int floorType;
    public Block block;
    
    public DecoratorFloor(final Random random) {
        this.floorType = 0;
        this.block = Blocks.stained_hardened_clay;
        this.decoBlock1 = random.nextInt(16);
        this.decoBlock2 = random.nextInt(16);
        if (this.decoBlock1 == this.decoBlock2) {
            ++this.decoBlock2;
            if (this.decoBlock2 > 15) {
                this.decoBlock2 = 0;
            }
        }
        this.floorType = random.nextInt(6);
    }
    
    public void generateFloor(final World world, final int x, final int y, final int z) {
        switch (this.floorType) {
            case 1: {
                this.cross(world, x, y, z);
                break;
            }
            case 2: {
                this.diamond(world, x, y, z);
                break;
            }
            case 3: {
                this.cross(world, x, y, z);
                break;
            }
            case 4: {
                this.diamond(world, x, y, z);
                break;
            }
            case 5: {
                this.flat(world, x, y, z);
                break;
            }
            default: {
                this.chess(world, x, y, z);
                break;
            }
        }
    }
    
    public void flat(final World world, final int x, final int y, final int z) {
        world.setBlock(x, y, z, this.block, this.decoBlock1, 3);
    }
    
    public void chess(final World world, final int x, final int y, final int z) {
        final int ax = Math.abs(x);
        final int az = Math.abs(z);
        if (ax % 2 == az % 2) {
            world.setBlock(x, y, z, this.block, this.decoBlock1, 3);
        }
        else {
            world.setBlock(x, y, z, this.block, this.decoBlock2, 3);
        }
    }
    
    public void square(final World world, final int x, final int y, final int z) {
        if (x % 2 == z % 2) {
            world.setBlock(x, y, z, this.block, this.decoBlock1, 3);
        }
        else {
            world.setBlock(x, y, z, this.block, this.decoBlock2, 3);
        }
    }
    
    public void cross(final World world, final int x, final int y, final int z) {
        final int ax = Math.abs(x);
        final int az = Math.abs(z);
        if (ax % 3 == 1 || az % 3 == 1) {
            world.setBlock(x, y, z, this.block, this.decoBlock1, 3);
        }
        else {
            world.setBlock(x, y, z, this.block, this.decoBlock2, 3);
        }
    }
    
    public void diamond(final World world, final int x, final int y, final int z) {
        final int ax = Math.abs(x);
        final int az = Math.abs(z);
        if (ax % 4 + az % 4 == 4 || ax % 4 == az % 4) {
            world.setBlock(x, y, z, this.block, this.decoBlock1, 3);
        }
        else {
            world.setBlock(x, y, z, this.block, this.decoBlock2, 3);
        }
    }
}
