package com.chocolate.chocolateQuest.gui.guinpc;

import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.packets.PacketUpdateConversation;
import net.minecraft.client.gui.GuiButton;
import com.chocolate.chocolateQuest.quest.DialogOption;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.client.gui.GuiScreen;

public class GuiNPC extends GuiScreen
{
    String currentName;
    EntityHumanNPC npc;
    EntityPlayer player;
    private DialogOption selectedDialog;
    GuiScrollOptions options;
    final int ANSWER = 100;
    int[] breadcrumbs;
    public static GuiNPC instance;
    GuiButton[] buttonAnswer;
    int EDIT_DIALOG;
    int EDIT_NPC;
    int EDIT_INVENTORY;
    int IMPORT;
    String lang;
    
    public GuiNPC(final EntityHumanNPC e, final EntityPlayer player) {
        this.currentName = "";
        this.EDIT_DIALOG = 0;
        this.EDIT_NPC = 1;
        this.EDIT_INVENTORY = 2;
        this.IMPORT = 3;
        this.npc = e;
        this.player = player;
        GuiNPC.instance = this;
        this.breadcrumbs = new int[0];
        this.selectedDialog = this.npc.conversation;
    }
    
    public void setDialogOption(final DialogOption option, final int[] breadCrumbs) {
        this.selectedDialog = option;
        this.breadcrumbs = breadCrumbs;
        this.selectedDialog.readText(this.lang);
        this.selectedDialog.replaceKeys(this.player.getCommandSenderName(), this.npc);
        String[] newOptions;
        if (this.selectedDialog.options != null) {
            newOptions = new String[this.selectedDialog.options.length];
            for (int i = 0; i < newOptions.length; ++i) {
                this.selectedDialog.options[i].readText(this.lang);
                this.selectedDialog.options[i].replaceKeys(this.player.getCommandSenderName(), this.npc);
                newOptions[i] = this.selectedDialog.options[i].prompt;
            }
        }
        else {
            newOptions = new String[0];
        }
        if (this.options != null) {
            if (newOptions.length == 0) {
                this.options.enabled = false;
            }
            this.options.setModeNames(newOptions);
        }
    }
    
    public void initGui() {
        super.initGui();
        this.lang = this.mc.getLanguageManager().getCurrentLanguage().getLanguageCode();
        if (this.player.capabilities.isCreativeMode) {
            final int height = 20;
            GuiButton option = new GuiButton(this.EDIT_DIALOG, 0, 0, this.width / 6, 20, "Edit dialogs");
            this.buttonList.add(option);
            option = new GuiButton(this.EDIT_NPC, 0, height, this.width / 6, 20, "Edit stats");
            this.buttonList.add(option);
            option = new GuiButton(this.EDIT_INVENTORY, 0, height * 2, this.width / 6, 20, "Inventory");
            this.buttonList.add(option);
            option = new GuiButton(this.IMPORT, 0, height * 3, this.width / 6, 20, "Import/Export");
            this.buttonList.add(option);
        }
        if (this.selectedDialog.options != null) {
            final String[] newOptions = new String[this.selectedDialog.options.length];
            for (int i = 0; i < newOptions.length; ++i) {
                newOptions[i] = this.selectedDialog.options[i].name;
            }
            this.options = new GuiScrollConversation(100, this.width - 140, this.height - this.height / 4, 140, this.height / 4, newOptions, this.fontRendererObj, 0);
            this.buttonList.add(this.options);
        }
        this.setDialogOption(this.selectedDialog, this.breadcrumbs);
    }
    
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        int[] breadcrumbsTemp = null;
        if (button.id == this.EDIT_DIALOG) {
            final IMessage packet = (IMessage)new PacketUpdateConversation(2, this.npc);
            ChocolateQuest.channel.sendPaquetToServer(packet);
        }
        else if (button.id == this.EDIT_NPC) {
            final IMessage packet = (IMessage)new PacketUpdateConversation(3, this.npc);
            ChocolateQuest.channel.sendPaquetToServer(packet);
        }
        else if (button.id == this.IMPORT) {
            this.mc.displayGuiScreen((GuiScreen)new GuiImportNPC(this.npc, this));
        }
        else if (button.id == this.EDIT_INVENTORY) {
            final IMessage packet = (IMessage)new PacketUpdateConversation(4, this.npc);
            ChocolateQuest.channel.sendPaquetToServer(packet);
        }
        else if (this.selectedDialog != null && this.selectedDialog.options != null) {
            final int selectedOption = this.options.selectedMode;
            if (selectedOption < this.selectedDialog.options.length) {
                breadcrumbsTemp = new int[this.breadcrumbs.length + 1];
                for (int i = 0; i < this.breadcrumbs.length; ++i) {
                    breadcrumbsTemp[i] = this.breadcrumbs[i];
                }
                breadcrumbsTemp[this.breadcrumbs.length] = this.selectedDialog.options[selectedOption].id;
            }
        }
        if (breadcrumbsTemp != null) {
            final IMessage packet = (IMessage)new PacketUpdateConversation(breadcrumbsTemp, this.npc);
            ChocolateQuest.channel.sendPaquetToServer(packet);
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void onGuiClosed() {
        GuiNPC.instance = null;
        super.onGuiClosed();
    }
    
    public void drawScreen(final int x, final int y, final float fl) {
        int posY = this.height - this.height / 4;
        final int posX = 5;
        if (this.selectedDialog.text != null) {
            posY = Math.min(posY, this.height - this.selectedDialog.text.length * this.fontRendererObj.FONT_HEIGHT);
        }
        posY = Math.min(posY, posY - (this.fontRendererObj.FONT_HEIGHT + 2) * (this.selectedDialog.text.length - 5));
        drawRect(0, posY, this.width, this.height, -1147561575);
        super.drawScreen(x, y, fl);
        this.drawString(this.fontRendererObj, this.npc.displayName, posX, posY - 4 - this.fontRendererObj.FONT_HEIGHT, 8947967);
        if (this.selectedDialog.text != null) {
            for (int i = 0; i < this.selectedDialog.text.length; ++i) {
                final String currentText = this.selectedDialog.text[i];
                drawTextWithSpecialChars(currentText, posX, posY + i * (this.fontRendererObj.FONT_HEIGHT + 2), this.fontRendererObj, 16777215);
            }
        }
    }
    
    public static void drawTextWithSpecialChars(String currentText, final int posX, final int posY, final FontRenderer fontRendererObj, final int color) {
        if (currentText.contains("[") && currentText.contains("]") && currentText.indexOf("[") < currentText.indexOf("]")) {
            final Minecraft mc = Minecraft.getMinecraft();
            final String s1 = currentText.substring(0, currentText.indexOf("["));
            final String s2 = currentText.substring(currentText.indexOf("]") + 1);
            final String itemName = currentText.substring(currentText.indexOf("[") + 1, currentText.indexOf("]"));
            int x = posX + fontRendererObj.getStringWidth(s1);
            final Object item = Item.itemRegistry.getObject(itemName);
            if (item != null) {
                ItemStack is;
                if (item instanceof Block) {
                    is = new ItemStack((Block)item);
                }
                else {
                    is = new ItemStack((Item)item);
                }
                GuiNPC.itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), is, x, posY - 4);
                x += 16;
                GL11.glDisable(2896);
            }
            currentText = s1;
            drawTextWithSpecialChars(s2, x, posY, fontRendererObj, color);
        }
        fontRendererObj.drawStringWithShadow(currentText, posX, posY, color);
    }
    
    protected void mouseClicked(final int x, final int y, final int button) {
        super.mouseClicked(x, y, button);
    }
    
    protected void keyTyped(final char c, final int i) {
        final int esc = 1;
        if (i == esc) {
            final IMessage packet = (IMessage)new PacketUpdateConversation(1, this.npc);
            ChocolateQuest.channel.sendPaquetToServer(packet);
        }
        super.keyTyped(c, i);
        final int UP = 200;
        final int FORWARD = Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode();
        final int DOWN = 208;
        final int BACK = Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode();
        final int INTRO = 28;
        final int INSERT = 206;
        final int RIGHT = 205;
        final int MOVE_RIGHT = Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode();
        if (this.options != null) {
            if (i == DOWN || i == BACK) {
                if (this.options.selectedMode + 1 < this.options.modeNames.length) {
                    this.options.scrollDown();
                }
            }
            else if (i == UP || i == FORWARD) {
                if (this.options.selectedMode - 1 >= 0) {
                    this.options.scrollUp();
                }
            }
            else if ((i == INTRO || i == INSERT || i == RIGHT || i == MOVE_RIGHT) && this.selectedDialog.options != null && this.options.selectedMode >= 0 && this.options.selectedMode < this.selectedDialog.options.length) {
                this.actionPerformed(this.options);
            }
        }
    }
}
