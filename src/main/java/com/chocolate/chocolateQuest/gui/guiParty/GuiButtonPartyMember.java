package com.chocolate.chocolateQuest.gui.guiParty;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.packets.PacketPartyMemberAction;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.gui.GuiHumanBase;
import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonPartyMember extends GuiButton
{
    MovingObjectPosition playerMop;
    EntityHumanBase[] entity;
    ButtonAction[] actions;
    ButtonAction[] allActions;
    boolean extended;
    boolean pressed;
    static final int ENTITY_AVATAR_SEPARATION = 8;
    static final int ICON_SIZE = 16;
    static final int ICON_SEPARATION = 2;
    static final int EXTENSION_BUTTON_X = 90;
    static final int EXTENSION_BUTTON_Y = 9;
    static final int EXTENSION_BUTTON_SIZE = 7;
    int xOffset;
    int yOffset;
    public boolean isSelected;
    boolean holdingButton;
    int buttonHeld;
    boolean renderEntity;
    
    public GuiButtonPartyMember(final int id, final int x, final int y, final EntityHumanBase entity) {
        super(id, x, y, "");
        this.actions = new ButtonAction[4];
        this.allActions = null;
        this.xOffset = 0;
        this.yOffset = 0;
        this.holdingButton = false;
        this.buttonHeld = 0;
        this.renderEntity = true;
        this.entity = new EntityHumanBase[] { entity };
        this.width = 100;
        this.actions[0] = new ButtonAction(0, 0, PartyAction.move.id);
        this.actions[1] = new ButtonAction(0, 0, PartyAction.follow.id);
        this.actions[2] = new ButtonAction(0, 0, PartyAction.formation.id);
        this.actions[3] = new ButtonAction(0, 0, PartyAction.flee.id);
    }
    
    public GuiButtonPartyMember(final int id, final int x, final int y, final EntityHumanBase[] entities) {
        this(id, x, y, entities[0]);
        this.entity = entities;
        this.renderEntity = false;
    }
    
    public void init(final MovingObjectPosition playerMop) {
        this.playerMop = playerMop;
        ButtonAction defaultAction = null;
        if (playerMop.entityHit != null) {
            if (this.getEntity().isSuitableMount(playerMop.entityHit)) {
                defaultAction = new ButtonAction(0, 0, PartyAction.mount.id);
            }
            else {
                defaultAction = new ButtonAction(0, 0, PartyAction.attack.id);
            }
        }
        else {
            defaultAction = new ButtonAction(0, 0, PartyAction.move.id);
        }
        this.actions[0] = defaultAction;
        this.moveActionButtons();
    }
    
    public void moveActionButtons() {
        for (int i = 0; i < this.actions.length; ++i) {
            this.actions[i].xPosition = this.xPosition + 16 + i * 18;
            this.actions[i].yPosition = this.yPosition + 1;
        }
    }
    
    public void drawButton(final Minecraft mc, final int x, final int y) {
        if (this.pressed) {
            this.xPosition = x + this.xOffset;
            this.yPosition = y + this.yOffset;
            this.moveActionButtons();
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        int textureX = 128;
        int textureY = 128;
        if (this.renderEntity) {
            this.drawTexturedModalRect(this.xPosition, this.yPosition, textureX, textureY + 20, this.width, this.height);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, textureX, textureY, (int)(this.width * this.getEntity().getHealth() / this.getEntity().getMaxHealth()), this.height);
        }
        else {
            this.drawTexturedModalRect(this.xPosition, this.yPosition, textureX, textureY + 60, this.width, this.height);
        }
        if (this.isSelected) {
            this.drawTexturedModalRect(this.xPosition, this.yPosition, textureX, textureY + 40, this.width, this.height);
        }
        this.drawSubButtons(this.actions, x, y);
        if (this.isMouseOverExtensionButton(x, y)) {
            GL11.glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
        }
        else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        textureX = 128 + this.width;
        textureY = 128;
        this.drawTexturedModalRect(this.xPosition + 90, this.yPosition + 9, textureX, textureY, 7, 7);
        if (this.extended) {
            this.drawSubButtons(this.allActions, x, y);
        }
        if (this.holdingButton) {
            final ButtonAction button = this.allActions[this.buttonHeld];
            this.drawIcon(224 + button.getIcon(), x, y);
        }
        if (this.renderEntity) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.xPosition + this.width - 8), (float)(this.yPosition + 3), 0.0f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            Minecraft.getMinecraft().fontRendererObj.drawString(this.getEntity().potionCount + "", 0, 0, 16777215);
            GL11.glPopMatrix();
            GuiHumanBase.drawEntity((EntityLivingBase)this.getEntity(), this.xPosition + 8, this.yPosition + this.height - 2, 8.0f);
        }
    }
    
    public boolean mousePressed(final Minecraft mc, final int x, final int y) {
        boolean b = super.mousePressed(mc, x, y);
        if (b) {
            boolean actionPerformed = false;
            for (int i = 0; i < this.actions.length; ++i) {
                if (this.actions[i].isMouseOver(x, y)) {
                    actionPerformed = true;
                }
            }
            if (this.isMouseOverExtensionButton(x, y)) {
                actionPerformed = true;
            }
            if (!actionPerformed) {
                this.pressed = true;
                this.xOffset = this.xPosition - x;
                this.yOffset = this.yPosition - y;
            }
        }
        else if (this.extended) {
            for (int j = 0; j < this.allActions.length; ++j) {
                if (this.allActions[j].isMouseOver(x, y)) {
                    b = true;
                    this.holdingButton = true;
                    this.buttonHeld = j;
                }
            }
        }
        return b;
    }
    
    public void mouseReleased(final int x, final int y) {
        super.mouseReleased(x, y);
        final boolean holdingButton = this.holdingButton;
        this.holdingButton = false;
        if (this.pressed) {
            this.pressed = false;
        }
        else {
            for (int i = 0; i < this.actions.length; ++i) {
                if (this.actions[i].isMouseOver(x, y)) {
                    if (holdingButton && this.allActions != null && i != 0) {
                        final ButtonAction original = this.allActions[this.buttonHeld];
                        this.actions[i] = new ButtonAction(original.xPosition, original.yPosition, original.action.id);
                        this.moveActionButtons();
                        return;
                    }
                    this.sendActionToServer(this.actions[i]);
                }
            }
            if (this.extended) {
                for (int i = 0; i < this.allActions.length; ++i) {
                    if (this.allActions[i].isMouseOver(x, y)) {
                        this.sendActionToServer(this.allActions[i]);
                    }
                }
            }
            if (this.isMouseOverExtensionButton(x, y)) {
                this.extended = !this.extended;
                if (this.extended) {
                    this.allActions = new ButtonAction[PartyAction.actions.size()];
                    for (int i = 0; i < this.allActions.length; ++i) {
                        final int xPos = this.xPosition + i % 6 * 18;
                        final int yPos = this.yPosition + 20 + 18 * (i / 6);
                        this.allActions[i] = new ButtonAction(xPos, yPos, PartyAction.actions.get(i).id);
                    }
                }
                else {
                    this.allActions = null;
                }
            }
        }
    }
    
    public void sendActionToServer(final ButtonAction action) {
        for (final EntityHumanBase e : this.entity) {
            final PacketPartyMemberAction packet = new PacketPartyMemberAction(e, action.action, this.playerMop, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
            ChocolateQuest.channel.sendPaquetToServer((IMessage)packet);
        }
    }
    
    public void drawIcon(final int icon, final int xPos, final int yPos) {
        final int ICONS_PER_ROW = 16;
        this.drawTexturedModalRect(xPos, yPos, icon % ICONS_PER_ROW * 16, icon / ICONS_PER_ROW * 16, 16, 16);
    }
    
    public String getHoveringText(final int x, final int y) {
        for (int i = 0; i < this.actions.length; ++i) {
            if (this.actions[i].isMouseOver(x, y)) {
                return this.actions[i].getName();
            }
        }
        if (this.extended) {
            for (int i = 0; i < this.allActions.length; ++i) {
                if (this.allActions[i].isMouseOver(x, y)) {
                    return this.allActions[i].getName();
                }
            }
        }
        return null;
    }
    
    public boolean isMouseOverExtensionButton(final int x, final int y) {
        return x >= this.xPosition + 90 && y >= this.yPosition + 9 && x < this.xPosition + 90 + 7 && y < this.yPosition + 9 + 7;
    }
    
    public void drawSubButtons(final ButtonAction[] buttons, final int x, final int y) {
        for (int i = 0; i < buttons.length; ++i) {
            if (buttons[i].isMouseOver(x, y)) {
                GL11.glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
            }
            else {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            this.drawIcon(224 + buttons[i].getIcon(), buttons[i].xPosition, buttons[i].yPosition);
        }
    }
    
    public EntityHumanBase getEntity() {
        return this.entity[0];
    }
}
