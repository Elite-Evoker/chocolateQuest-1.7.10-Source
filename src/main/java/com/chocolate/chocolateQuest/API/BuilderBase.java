package com.chocolate.chocolateQuest.API;

import java.util.Properties;
import net.minecraft.world.World;
import java.util.Random;

public abstract class BuilderBase
{
    public abstract void generate(final Random p0, final World p1, final int p2, final int p3, final int p4);
    
    public abstract void generate(final Random p0, final World p1, final int p2, final int p3, final int p4, final int p5);
    
    public abstract String getName();
    
    public BuilderBase load(final Properties prop) {
        return this;
    }
}
