package com.chocolate.chocolateQuest.API;

import com.chocolate.chocolateQuest.builder.BuilderBlockStoneJungle;
import com.chocolate.chocolateQuest.builder.BuilderBlockOldStoneBrick;
import com.chocolate.chocolateQuest.builder.BuilderBlockDataBiomeTop;
import com.chocolate.chocolateQuest.builder.BuilderBlockDataBiomeFiller;
import net.minecraft.block.Block;
import java.util.StringTokenizer;
import java.util.Properties;

public class HelperReadConfig
{
    static BuilderBlockData biomeFiller;
    static BuilderBlockData biomeTop;
    static BuilderBlockData crackedBrick;
    static BuilderBlockData jungleBrick;
    
    public static boolean getBooleanProperty(final Properties prop, final String name, final boolean defaultValue) {
        String s = prop.getProperty(name);
        if (s == null) {
            return defaultValue;
        }
        s = s.trim();
        return s.equals("true");
    }
    
    public static int getIntegerProperty(final Properties prop, final String name, final int defaultValue) {
        String s = prop.getProperty(name);
        if (s == null) {
            return defaultValue;
        }
        int ret = defaultValue;
        try {
            s = s.trim();
            ret = Integer.parseInt(s);
        }
        finally {}
        return ret;
    }
    
    public static int getIntegerProperty(final Properties prop, final String name, final int defaultValue, final int minValue, final int maxValue) {
        int i = getIntegerProperty(prop, name, defaultValue);
        i = Math.max(minValue, i);
        i = Math.min(maxValue, i);
        return i;
    }
    
    public static int[] getIntegerArray(final Properties prop, final String name, final int defaultValue) {
        final String s = prop.getProperty(name);
        int[] ids = null;
        if (s != null) {
            final StringTokenizer stkn = new StringTokenizer(s, ",");
            ids = new int[stkn.countTokens()];
            for (int i = 0; i < ids.length; ++i) {
                final String nextToken = stkn.nextToken().trim();
                ids[i] = Integer.parseInt(nextToken);
            }
        }
        if (ids == null) {
            ids = new int[] { defaultValue };
        }
        return ids;
    }
    
    public static BuilderBlockData getBlock(final Properties prop, final String name, final BuilderBlockData defaultValue) {
        Block block = null;
        int metadata = 0;
        final String s = prop.getProperty(name);
        if (s != null) {
            if (s.startsWith("@")) {
                if (s.equals("@biome_top")) {
                    return HelperReadConfig.biomeTop;
                }
                if (s.equals("@biome_filler")) {
                    return HelperReadConfig.biomeFiller;
                }
                if (s.equals("@brick_mossy")) {
                    return HelperReadConfig.jungleBrick;
                }
                if (s.equals("@brick_cracked")) {
                    return HelperReadConfig.crackedBrick;
                }
            }
            final StringTokenizer stkn = new StringTokenizer(s, ",");
            block = Block.getBlockFromName(((String)stkn.nextElement()).trim());
            if (stkn.hasMoreElements()) {
                final String md = (String)stkn.nextElement();
                if (md != null) {
                    metadata = Integer.parseInt(md.trim());
                }
            }
        }
        if (block == null) {
            return defaultValue;
        }
        final BuilderBlockData b = new BuilderBlockData(block, metadata);
        return b;
    }
    
    static {
        HelperReadConfig.biomeFiller = new BuilderBlockDataBiomeFiller();
        HelperReadConfig.biomeTop = new BuilderBlockDataBiomeTop();
        HelperReadConfig.crackedBrick = new BuilderBlockOldStoneBrick();
        HelperReadConfig.jungleBrick = new BuilderBlockStoneJungle();
    }
}
