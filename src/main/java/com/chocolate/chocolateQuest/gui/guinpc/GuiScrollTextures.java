package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import java.io.File;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScrollTextures extends GuiScrollOptions
{
    File file;
    public File[] files;
    public String prevFile;
    
    public GuiScrollTextures(final int id, final int posX, final int posY, final int width, final int height, final File file, final FontRenderer font) {
        this(id, posX, posY, width, height, file, font, 4);
    }
    
    public GuiScrollTextures(final int id, final int posX, final int posY, final int width, final int height, final File file, final FontRenderer font, final int values) {
        super(id, posX, posY, width, height, font);
        this.prevFile = "";
        this.MAX_ENTRIES_FINAL = values;
        this.file = file;
        this.files = file.listFiles();
        if (this.files != null && this.files.length > 0) {
            this.modeNames = new String[this.files.length];
            for (int i = 0; i < this.files.length; ++i) {
                this.modeNames[i] = this.files[i].getName();
                if (this.files[i].isDirectory()) {
                    final StringBuilder sb = new StringBuilder();
                    final String[] modeNames = this.modeNames;
                    final int n = i;
                    modeNames[n] = sb.append(modeNames[n]).append("/").toString();
                }
            }
        }
        this.setModeNames(this.modeNames);
    }
    
    public void updateFiles() {
        this.files = this.file.listFiles();
        if (this.files != null && this.files.length > 0) {
            this.modeNames = new String[this.files.length];
            for (int i = 0; i < this.files.length; ++i) {
                this.modeNames[i] = this.files[i].getName();
            }
        }
    }
    
    public File getSelectedFile() {
        if (this.selectedMode >= 0 && this.selectedMode < this.files.length) {
            return this.files[this.selectedMode];
        }
        return null;
    }
    
    public String getFileString() {
        final File file = this.getSelectedFile();
        if (file == null) {
            return null;
        }
        if (file.isDirectory()) {
            return null;
        }
        return this.prevFile + file.getName();
    }
    
    @Override
    public boolean mousePressed(final Minecraft par1Minecraft, final int par2, final int par3) {
        final boolean pressed = super.mousePressed(par1Minecraft, par2, par3);
        if (this.files.length > this.selectedMode && this.files[this.selectedMode].isDirectory()) {
            this.prevFile = this.prevFile + this.files[this.selectedMode].getName() + "/";
            this.file = this.files[this.selectedMode];
            this.updateFiles();
            return false;
        }
        return pressed;
    }
}
