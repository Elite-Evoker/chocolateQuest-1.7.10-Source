package com.chocolate.chocolateQuest.gui;

import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.gui.GuiButton;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.gui.guiParty.PartyManager;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class GuiHuman extends GuiHumanBase
{
    GuiButtonAngle teamPositionButton;
    GuiButtonAIMode combatAI;
    GuiButtonAIMode passiveAI;
    EntityHumanBase human;
    String[] combatAINames;
    boolean owned;
    boolean ownedOriginal;
    final int FOLLOW_BUTTON_ID = 100;
    final String FOLLOW;
    final String LEAVE;
    
    public GuiHuman(final ContainerHumanInventory container, final EntityHumanBase human, final IInventory par1IInventory, final EntityPlayer playerInventory) {
        super(container, par1IInventory, playerInventory);
        this.combatAINames = new String[] { "Offensive", "Defensive", "Evasive", "Flee" };
        this.human = human;
        this.FOLLOW = new ChatComponentTranslation("ai.join.name", new Object[0]).getFormattedText();
        this.LEAVE = new ChatComponentTranslation("ai.leave.name", new Object[0]).getFormattedText();
    }
    
    public GuiHuman(final EntityHumanBase human, final IInventory par1IInventory, final EntityPlayer playerInventory) {
        this(new ContainerHumanInventory((IInventory)playerInventory.inventory, par1IInventory), human, par1IInventory, playerInventory);
    }
    
    public void onGuiClosed() {
        if (this.human.worldObj.isRemote) {
            this.human.partyPositionAngle = this.teamPositionButton.getAngle();
            this.human.partyDistanceToLeader = this.teamPositionButton.getDistance();
            this.human.AIMode = this.passiveAI.selectedMode;
            this.human.AICombatMode = this.combatAI.selectedMode;
            this.human.partyPositionPersistance = true;
            this.human.updateHands();
            this.human.updateOwner = (this.owned != this.ownedOriginal);
            this.human.playerSpeakingTo = null;
            final IMessage packet = this.human.getEntityGUIUpdatePacket(this.player);
            ChocolateQuest.channel.sendPaquetToServer(packet);
            if (this.owned) {
                PartyManager.instance.addMember(this.human);
            }
            else {
                PartyManager.instance.removeMember(this.human);
            }
        }
        super.onGuiClosed();
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int width = (this.width - this.xSize) / 2 + 80;
        final int height = this.height - this.height / 2 - 10;
        this.teamPositionButton = new GuiButtonAngle(10, width - 15, height - 80, "test", 1.0f, this.human);
        this.buttonList.add(this.teamPositionButton);
        this.combatAI = new GuiButtonBattleAIMode(10, width + 72, height - 80, 20, 80, this.combatAINames, this.fontRendererObj, this.human.AICombatMode);
        this.buttonList.add(this.combatAI);
        final String[] names = new String[EnumAiState.values().length];
        for (int i = 0; i < names.length; ++i) {
            names[i] = new ChatComponentTranslation(EnumAiState.values()[i].ainame, new Object[0]).getFormattedText();
        }
        this.passiveAI = new GuiButtonAIMode(10, width + 100, height - 80, 60, 100, names, this.fontRendererObj, this.human.AIMode);
        this.buttonList.add(this.passiveAI);
        final boolean updateOwner = this.human.updateOwner;
        this.ownedOriginal = updateOwner;
        this.owned = updateOwner;
        this.human.updateOwner = false;
        final GuiButton b = new GuiButtonIcon(100, width - 40, height - 100, 9.0f, 2.0f, 6.0f, 1.0f, this.owned ? this.LEAVE : this.FOLLOW);
        this.buttonList.add(b);
    }
    
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 100) {
            this.owned = !this.owned;
            button.displayString = (this.owned ? this.LEAVE : this.FOLLOW);
        }
        super.actionPerformed(button);
    }
    
    @Override
    protected void drawEquipementPanel() {
        this.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        final int width = (this.width - this.xSize) / 2;
        final int height = this.height - this.height / 2 - 86;
        final int x = width - 48;
        final int y = height - 2;
        this.drawTexturedModalRect(x, y, 64, 128, 64, 80);
        Gui.drawRect(x + 3, y + 3, x + 58, y + 76, -16777216);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.drawEquipementPanel();
        GuiHumanBase.drawEntity((EntityLivingBase)this.human, width - 24, height + 60);
    }
}
