package com.chocolate.chocolateQuest.gui.guinpc;

import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketSaveNPC;
import net.minecraft.client.gui.GuiButton;
import java.io.File;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.gui.GuiScreen;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.client.gui.GuiTextField;

public class GuiImportNPC extends GuiLinked
{
    GuiTextField textboxFile;
    GuiScrollFiles loadFolder;
    final int LOAD = 0;
    final int SAVE = 1;
    final int IMPORT = 2;
    EntityHumanNPC npc;
    
    public GuiImportNPC(final EntityHumanNPC npc, final GuiScreen screen) {
        super(screen);
        this.hasNavigationMenu = false;
        this.npc = npc;
        this.maxScrollAmmount = 0;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final File file = new File(BDHelper.getAppDir(), BDHelper.getQuestDir() + "npcExport/");
        if (!file.exists()) {
            file.mkdirs();
        }
        final int buttonsWidth = this.width - 40;
        this.loadFolder = new GuiScrollFiles(2, 25, 10, buttonsWidth, this.height - 90, file, this.fontRendererObj, 8);
        this.buttonList.add(this.loadFolder);
        final GuiButton b2 = new GuiButton(0, 25, this.height - 80, buttonsWidth, 20, "Load");
        this.buttonList.add(b2);
        (this.textboxFile = new GuiTextField(this.fontRendererObj, 25, this.height - 50, buttonsWidth, 20)).setMaxStringLength(25);
        this.textFieldList.add(this.textboxFile);
        final GuiButton b3 = new GuiButton(1, 25, this.height - 30, buttonsWidth, 20, "Save");
        this.buttonList.add(b3);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 1) {
            final String name = this.textboxFile.getText();
            final PacketSaveNPC packet = new PacketSaveNPC(this.npc, name);
            ChocolateQuest.channel.sendPaquetToServer((IMessage)packet);
        }
        if (button.id == 0) {
            final File file = this.loadFolder.getSelectedFile();
            if (file != null) {
                final NBTTagCompound tag = BDHelper.readCompressed(file);
                if (tag != null) {
                    final double x = this.npc.posX;
                    final double y = this.npc.posY;
                    final double z = this.npc.posZ;
                    this.npc.readEntityFromSpawnerNBT(tag, (int)this.npc.posX, (int)this.npc.posY, (int)this.npc.posZ);
                    this.npc.setPosition(x, y, z);
                    final PacketEditNPC packet2 = new PacketEditNPC(this.npc, tag);
                    ChocolateQuest.channel.sendPaquetToServer((IMessage)packet2);
                }
            }
        }
        super.actionPerformed(button);
        if (button.id == 2) {
            this.textboxFile.setText(this.loadFolder.getFileString());
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void saveNPC(final NBTTagCompound tag) {
        final String name = this.textboxFile.getText();
        try {
            if (name.length() > 0 && !name.equals(" ")) {
                BDHelper.writeCompressed(tag, new File(BDHelper.getAppDir(), BDHelper.getQuestDir() + "npcExport/" + name));
                this.loadFolder.updateFiles();
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
