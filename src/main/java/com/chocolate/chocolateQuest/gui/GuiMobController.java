package com.chocolate.chocolateQuest.gui;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.client.Minecraft;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketController;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.GuiScreen;

public class GuiMobController extends GuiScreen
{
    final int MOVE = 0;
    final int WARD_POSITION = 1;
    final int TEAM_EDITOR = 2;
    final int CLEAR = 3;
    final int DO_NOTHING = 999;
    
    public void initGui() {
        super.initGui();
        final int x = 10;
        final int buttonSeparation = 4;
        final int buttonsWidth = 150;
        final int buttonHeight = 20;
        int yPos = 0;
        final GuiButton name = new GuiButtonDisplayString(999, x, yPos, buttonsWidth, buttonHeight, "Modes");
        this.buttonList.add(name);
        yPos += buttonHeight;
        GuiButton button = new GuiButton(0, x, yPos, buttonsWidth, buttonHeight, StatCollector.translateToLocal("item.move.name") + " (Q)");
        this.buttonList.add(button);
        yPos += buttonHeight + buttonSeparation;
        button = new GuiButton(1, x, yPos, buttonsWidth, buttonHeight, StatCollector.translateToLocal("item.ward.name") + " (W)");
        this.buttonList.add(button);
        yPos += buttonHeight + buttonSeparation;
        button = new GuiButton(2, x, yPos, buttonsWidth, buttonHeight, StatCollector.translateToLocal("item.team.name") + " (E)");
        this.buttonList.add(button);
        yPos += buttonHeight + buttonSeparation + 10;
        button = new GuiButton(3, x, yPos, buttonsWidth, buttonHeight, StatCollector.translateToLocal("item.reset.name") + " (R)");
        this.buttonList.add(button);
    }
    
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        this.actionPerformed(button.id);
    }
    
    public void actionPerformed(final int id) {
        if (id == 0 || id == 1 || id == 2 || id == 3) {
            final IMessage packet = (IMessage)new PacketController((byte)id);
            ChocolateQuest.channel.sendPaquetToServer(packet);
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        }
    }
    
    protected void keyTyped(final char c, final int i) {
        super.keyTyped(c, i);
        if (i == 16) {
            this.actionPerformed(0);
            this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Set mode: move"));
        }
        if (i == 17) {
            this.actionPerformed(1);
            this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Set mode: guard"));
        }
        if (i == 18) {
            this.actionPerformed(2);
            this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Set mode: team editor"));
        }
        if (i == 19) {
            this.actionPerformed(3);
            this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Cleared entities"));
        }
    }
}
