package com.chocolate.chocolateQuest.builder;

import java.util.Random;

public class Perlin3D
{
    private long seed;
    private Random rand;
    private int frequency;
    
    public Perlin3D(final long seed, final int octave, final Random random) {
        this.seed = seed;
        this.frequency = octave;
        this.rand = new Random();
    }
    
    public double getNoiseAt(final int x, final int y, final int z) {
        final int ymin = (int)Math.floor(y / (double)this.frequency);
        final int ymax = ymin + 1;
        return this.cosineInterpolate((float)this.getNoiseLevelAtPosition(x, ymin, z), (float)this.getNoiseLevelAtPosition(x, ymax, z), (y - ymin * (float)this.frequency) / this.frequency);
    }
    
    private double getNoiseLevelAtPosition(final int x, final int y, final int z) {
        final int xmin = (int)Math.floor(x / (double)this.frequency);
        final int xmax = xmin + 1;
        final int zmin = (int)Math.floor(z / (double)this.frequency);
        final int zmax = zmin + 1;
        return this.cosineInterpolate(this.cosineInterpolate((float)this.getRandomAtPosition(xmin, y, zmin), (float)this.getRandomAtPosition(xmax, y, zmin), (x - xmin * this.frequency) / (float)this.frequency), this.cosineInterpolate((float)this.getRandomAtPosition(xmin, y, zmax), (float)this.getRandomAtPosition(xmax, y, zmax), (x - xmin * this.frequency) / (float)this.frequency), (z - zmin * (float)this.frequency) / this.frequency);
    }
    
    private float cosineInterpolate(final float a, final float b, final float x) {
        final float f = (float)((1.0 - Math.cos(x * 3.141592653589793)) * 0.5);
        return a * (1.0f - f) + b * f;
    }
    
    private float linearInterpolate(final float a, final float b, final float x) {
        return a * (1.0f - x) + b * x;
    }
    
    private double getRandomAtPosition(final int x, final int y, final int z) {
        this.rand.setSeed((long)(10000.0 * (Math.sin(x) + Math.cos(z) + Math.cos(y) + Math.tan((double)this.seed))));
        return this.rand.nextDouble();
    }
}
