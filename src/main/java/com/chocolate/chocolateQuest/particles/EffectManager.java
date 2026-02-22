package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class EffectManager
{
    public static final int slimeFog = 0;
    public static final int bubble = 1;
    public static final int liquid_Drop = 2;
    public static final int flame = 3;
    public static final int dust = 4;
    public static final int cloud = 5;
    public static final int vanilla_cloud = 6;
    public static final int dust_walker = 7;
    public static final int element = 0;
    public static final int element_tornado = 1;
    public static final int element_smoke = 2;
    
    public static void spawnParticle(final int particle, final World worldObj, final double x, final double y, final double z) {
        spawnParticle(particle, worldObj, x, y, z, 0.0, 0.0, 0.0);
    }
    
    public static void spawnParticle(final int particle, final World worldObj, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        switch (particle) {
            case 0: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EffectFog(worldObj, x, y, z, mx, my, mz));
                return;
            }
            case 1: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EffectBubble(worldObj, x, y, z, mx, my, mz));
                return;
            }
            case 2: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EffectLiquidDrip(worldObj, x, y, z, mx, my, mz));
                return;
            }
            case 3: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EffectFlame(worldObj, x, y, z, mx, my, mz));
                return;
            }
            case 4: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new ColoredDust(worldObj, x, y, z, mx, my, mz));
                return;
            }
            case 5: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EffectCloud(worldObj, x, y, z, mx, my, mz));
                return;
            }
            case 6: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EffectSmoke(worldObj, x, y, z, mx, my, mz));
                return;
            }
            case 7: {
                if (shouldSpawnParticle()) {
                    Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new ColoredDust(worldObj, x, y, z, mx, my, mz));
                }
            }
            default: {}
        }
    }
    
    public static void spawnElementParticle(final int particle, final World worldObj, final double x, final double y, final double z, final double mx, final double my, final double mz, final Elements element) {
        final int elementType = element.ordinal();
        switch (particle) {
            case 1: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EffectElementTornado(worldObj, x, y, z, mx, my, mz, elementType));
                return;
            }
            case 2: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EffectSmokeElement(worldObj, x, y, z, mx, my, mz, element));
                return;
            }
            default: {
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EffectElement(worldObj, x, y, z, mx, my, mz, elementType));
            }
        }
    }
    
    public static boolean shouldSpawnParticle() {
        return Minecraft.getMinecraft().gameSettings.particleSetting == 0;
    }
}
