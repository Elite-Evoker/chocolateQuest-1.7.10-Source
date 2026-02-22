package com.chocolate.chocolateQuest.builder;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.API.BuilderBlockData;

public class BuilderBlockStoneJungle extends BuilderBlockData
{
    public BuilderBlockStoneJungle() {
        super(Blocks.stonebrick);
    }
    
    @Override
    public void placeBlock(final World world, final int x, final int y, final int z, final Random random) {
        world.setBlock(x, y, z, this.id, (int)((random.nextInt(3) == 0) ? 1 : 0), 3);
    }
}
