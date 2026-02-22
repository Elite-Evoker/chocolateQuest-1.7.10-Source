package com.chocolate.chocolateQuest.quest.worldManager;

import net.minecraft.nbt.NBTTagCompound;
import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import java.io.File;
import net.minecraft.world.World;

public class WorldManagerBase
{
    public void read(final World world, final String fileName) {
        final File file = new File(world.getSaveHandler().getWorldDirectory(), fileName);
        if (file.exists()) {
            try {
                final NBTTagCompound tag = CompressedStreamTools.read(file);
                this.readFromNBT(tag);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void save(final World world, final String filePath, final String fileName) {
        final File saveFile = world.getSaveHandler().getWorldDirectory();
        final File file = new File(saveFile, filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        final File managerFile = new File(file, fileName);
        final NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        try {
            CompressedStreamTools.safeWrite(tag, managerFile);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    protected void readFromNBT(final NBTTagCompound tag) {
    }
    
    protected void writeToNBT(final NBTTagCompound tag) {
    }
}
