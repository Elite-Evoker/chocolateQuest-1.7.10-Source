package com.chocolate.chocolateQuest.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.packets.PacketEditorGUIClose;
import com.chocolate.chocolateQuest.ChocolateQuest;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import net.minecraft.world.World;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class GuiEditor extends GuiScreen
{
    private GuiTextField textboxSX;
    private GuiTextField textboxSY;
    private GuiTextField textboxHeight;
    private GuiTextField textboxFile;
    private GuiButton buttonOK;
    private GuiButton buttonExit;
    private GuiButton buttonPaste;
    World world;
    int posX;
    int posY;
    int posZ;
    int x;
    int y;
    int z;
    BlockEditorTileEntity block;
    byte ACTION_UPDATE;
    byte ACTION_EXPORT;
    
    public GuiEditor(final World world, final int x, final int y, final int z) {
        this.ACTION_UPDATE = 0;
        this.ACTION_EXPORT = 1;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.block = (BlockEditorTileEntity)world.getTileEntity(x, y, z);
        this.x = this.block.red;
        this.y = this.block.height;
        this.z = this.block.yellow;
        this.world = world;
    }
    
    protected void actionPerformed(final GuiButton guibutton) {
        if (guibutton.displayString == this.buttonOK.displayString) {
            int sx = 15;
            int sz = 15;
            int sy = 15;
            try {
                sx = Integer.parseInt(this.textboxSX.getText().trim());
                sz = Integer.parseInt(this.textboxSY.getText().trim());
                sy = Integer.parseInt(this.textboxHeight.getText().trim());
            }
            catch (final Exception ex) {}
            this.sendPacket(this.ACTION_EXPORT);
        }
        else if (guibutton.displayString == this.buttonExit.displayString) {
            this.sendPacket(this.ACTION_UPDATE);
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (guibutton.displayString == this.buttonPaste.displayString) {}
    }
    
    public void sendPacket(final byte action) {
        try {
            this.block.red = Integer.parseInt(this.textboxSX.getText().trim());
            this.block.yellow = Integer.parseInt(this.textboxSY.getText().trim());
            this.block.height = Integer.parseInt(this.textboxHeight.getText().trim());
        }
        catch (final Exception ex2) {}
        this.block.setName(this.textboxFile.getText());
        final ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
        final DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeByte(0);
            outputStream.writeInt(this.posX);
            outputStream.writeInt(this.posY);
            outputStream.writeInt(this.posZ);
            outputStream.writeInt(this.block.red);
            outputStream.writeInt(this.block.yellow);
            outputStream.writeInt(this.block.height);
            outputStream.writeUTF(this.block.name);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        ChocolateQuest.channel.sendPaquetToServer((IMessage)new PacketEditorGUIClose(this.posX, this.posY, this.posZ, this.block.red, this.block.yellow, this.block.height, this.block.name, action));
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.buttonOK = new GuiButton(0, this.width / 2 - 150, 40, "Export");
        this.buttonList.add(this.buttonOK);
        this.buttonExit = new GuiButton(1, this.width / 2 - 150, 200, "Close");
        this.buttonList.add(this.buttonExit);
        (this.textboxSX = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 70, 20, 20)).setText("" + this.x);
        this.textboxSX.setFocused(true);
        this.textboxSX.setMaxStringLength(3);
        (this.textboxSY = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 100, 20, 20)).setText("" + this.z);
        this.textboxSY.setMaxStringLength(3);
        (this.textboxHeight = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 130, 20, 20)).setText("" + this.y);
        this.textboxHeight.setMaxStringLength(3);
        (this.textboxFile = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 160, 130, 20)).setText(this.block.getName());
        this.textboxFile.setMaxStringLength(20);
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        super.drawScreen(i, j, f);
        this.drawString(this.fontRendererObj, "Size X", this.width / 2 - 160, 70, 16711680);
        this.drawString(this.fontRendererObj, "Size Z", this.width / 2 - 160, 100, 16776960);
        this.drawString(this.fontRendererObj, "Height", this.width / 2 - 160, 130, 16777215);
        this.drawString(this.fontRendererObj, "File name", this.width / 2 - 160, 160, 16777215);
        this.textboxSX.drawTextBox();
        this.textboxSY.drawTextBox();
        this.textboxHeight.drawTextBox();
        this.textboxFile.drawTextBox();
        super.drawScreen(i, j, f);
    }
    
    protected void keyTyped(final char c, final int i) {
        if (this.textboxSX.isFocused()) {
            this.textboxSX.textboxKeyTyped(c, i);
        }
        if (this.textboxSY.isFocused()) {
            this.textboxSY.textboxKeyTyped(c, i);
        }
        if (this.textboxHeight.isFocused()) {
            this.textboxHeight.textboxKeyTyped(c, i);
        }
        if (this.textboxFile.isFocused()) {
            this.textboxFile.textboxKeyTyped(c, i);
        }
        super.keyTyped(c, i);
    }
    
    private void setFalse() {
        this.textboxSX.setFocused(false);
        this.textboxSY.setFocused(false);
        this.textboxHeight.setFocused(false);
        this.textboxFile.setFocused(false);
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        if (j > 70 && j < 90) {
            this.setFalse();
            this.textboxSX.setFocused(true);
        }
        if (j > 100 && j < 120) {
            this.setFalse();
            this.textboxSY.setFocused(true);
        }
        if (j > 130 && j < 150) {
            this.setFalse();
            this.textboxHeight.setFocused(true);
        }
        if (j > 170 && j < 190) {
            this.setFalse();
            this.textboxFile.setFocused(true);
        }
    }
}
