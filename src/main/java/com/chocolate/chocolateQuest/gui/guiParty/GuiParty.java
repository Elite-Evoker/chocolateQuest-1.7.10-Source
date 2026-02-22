package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.client.KeyBindings;
import java.util.ArrayList;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketPartyCreation;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import com.chocolate.chocolateQuest.entity.EntityCursor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.gui.GuiScreen;

public class GuiParty extends GuiScreen
{
    MovingObjectPosition playerMop;
    static final int BUTTONS_MEMBER_ID = 0;
    static final int BUTTON_RESET_ID = 1;
    static final int BUTTON_CREATE_NEW_PARTY_ID = 2;
    static final int BUTTON_ALL_MEMBERS_ID = 3;
    static GuiButtonPartyMember buttonAllMembers;
    GuiButton buttonCreate;
    EntityCursor selected;
    
    public void initGui() {
        super.initGui();
        final EntityPlayer player = (EntityPlayer)this.mc.thePlayer;
        this.playerMop = HelperPlayer.getMovingObjectPositionFromPlayer((EntityLivingBase)player, (World)this.mc.theWorld, 120.0);
        if (this.playerMop == null) {
            this.playerMop = new MovingObjectPosition(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0, Vec3.createVectorHelper(0.0, 0.0, 0.0));
        }
        if (this.playerMop.entityHit != null) {
            final EntityCursor cursor = new EntityCursor(player.worldObj, this.playerMop.entityHit, 16711680, this);
            player.worldObj.spawnEntityInWorld((Entity)cursor);
        }
        else {
            final EntityCursor cursor = new EntityCursor(player.worldObj, this.playerMop.blockX + 0.5, this.playerMop.blockY + 1, this.playerMop.blockZ + 0.5, player.rotationYawHead);
            player.worldObj.spawnEntityInWorld((Entity)cursor);
        }
        double dist = 0.0;
        final List<EntityHumanBase> list = PartyManager.instance.getMembers();
        for (final EntityHumanBase e : list) {
            dist = Math.max(dist, e.partyDistanceToLeader);
        }
        final int screenSize = Math.min(this.width - 50, this.height - 15);
        dist = screenSize / 2 / dist;
        for (int i = 0; i < list.size(); ++i) {
            final EntityHumanBase e2 = list.get(i);
            if (e2 != null) {
                GuiButtonPartyMember button;
                if (PartyManager.instance.getButton(i) != null) {
                    button = (GuiButtonPartyMember)PartyManager.instance.getButton(i);
                    button.playerMop = this.playerMop;
                }
                else {
                    final double angleRads = e2.partyPositionAngle * 3.1416 / 180.0;
                    final int x = (int)(Math.sin(angleRads) * e2.partyDistanceToLeader * dist);
                    final int y = (int)(-Math.cos(angleRads) * e2.partyDistanceToLeader * dist);
                    button = new GuiButtonPartyMember(0, this.width / 2 - 50 + x, this.height / 2 - 10 + y, e2);
                    PartyManager.instance.setButton(i, button);
                }
                button.init(this.playerMop);
                this.buttonList.add(button);
                final EntityCursor cursor2 = new EntityCursor(player.worldObj, (Entity)e2, 65280, this);
                player.worldObj.spawnEntityInWorld((Entity)cursor2);
                if (button.isSelected) {
                    this.selected = new EntityCursor(player.worldObj, (Entity)e2, 255, this);
                    player.worldObj.spawnEntityInWorld((Entity)this.selected);
                }
            }
        }
        final EntityHumanBase[] entities = PartyManager.instance.getEntitiesArray();
        if (entities.length > 0) {
            if (GuiParty.buttonAllMembers == null) {
                GuiParty.buttonAllMembers = new GuiButtonPartyMember(3, this.width / 2 - 50, this.height / 2 - 10, entities);
            }
            GuiParty.buttonAllMembers.entity = entities;
            GuiParty.buttonAllMembers.init(this.playerMop);
            this.buttonList.add(GuiParty.buttonAllMembers);
        }
        final GuiButton buttonReset = new GuiButton(1, 0, 0, 40, 12, new ChatComponentTranslation("strings.reset", new Object[0]).getFormattedText());
        this.buttonList.add(buttonReset);
        this.buttonCreate = new GuiButton(2, 0, 12, 40, 12, new ChatComponentTranslation("strings.createparty", new Object[0]).getFormattedText());
        this.buttonList.add(this.buttonCreate);
    }
    
    protected void moveButtons() {
        final int id = 0;
        double dist = 0.0;
        final List<EntityHumanBase> list = PartyManager.instance.getMembers();
        for (final EntityHumanBase e : list) {
            dist = Math.max(dist, e.partyDistanceToLeader);
        }
        final int screenSize = Math.min(this.width - 50, this.height - 15);
        dist = screenSize / 2 / dist;
        for (int i = 0; i < list.size(); ++i) {
            final EntityHumanBase e2 = list.get(i);
            if (e2 != null && PartyManager.instance.getButton(i) != null) {
                final double angleRads = e2.partyPositionAngle * 3.1416 / 180.0;
                final int x = (int)(Math.sin(angleRads) * e2.partyDistanceToLeader * dist);
                final int y = (int)(-Math.cos(angleRads) * e2.partyDistanceToLeader * dist);
                final GuiButtonPartyMember button = (GuiButtonPartyMember)PartyManager.instance.getButton(i);
                button.xPosition = this.width / 2 - 50 + x;
                button.yPosition = this.height / 2 - 10 + y;
                button.moveActionButtons();
                button.isSelected = false;
            }
        }
    }
    
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        if (button.id == 1) {
            this.moveButtons();
        }
        if (button.id == 2) {
            final EntityHumanBase[] entities = PartyManager.instance.getEntitiesArray();
            if (entities.length > 0) {
                final GuiButtonPartyMember leaderButton = this.getSelectedButton();
                if (leaderButton != null) {
                    final EntityHumanBase leader = leaderButton.getEntity();
                    final int[] newPartyMembers = new int[entities.length - 1];
                    int cont = 0;
                    for (int i = 0; i < entities.length; ++i) {
                        if (entities[i] != leader) {
                            newPartyMembers[cont++] = entities[i].getEntityId();
                            PartyManager.instance.removeMember(entities[i]);
                        }
                    }
                    final PacketPartyCreation packet = new PacketPartyCreation(leader.getEntityId(), newPartyMembers);
                    ChocolateQuest.channel.sendPaquetToServer((IMessage)packet);
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
            }
        }
        if (button.id == 0 || button.id == 3) {
            for (int j = 0; j < this.buttonList.size(); ++j) {
                final GuiButton currentButton = this.buttonList.get(j);
                if (currentButton.id == 0) {
                    ((GuiButtonPartyMember)currentButton).isSelected = false;
                }
            }
            GuiParty.buttonAllMembers.isSelected = false;
            this.setSelectedButton((GuiButtonPartyMember)button);
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void drawScreen(final int x, final int y, final float f) {
        super.drawScreen(x, y, f);
        final ArrayList<String> list = new ArrayList<String>();
        if (x >= this.buttonCreate.xPosition && y >= this.buttonCreate.yPosition && x < this.buttonCreate.xPosition + this.buttonCreate.width && y < this.buttonCreate.yPosition + this.buttonCreate.height) {
            list.add(new ChatComponentTranslation("strings.createpartylong", new Object[0]).getFormattedText());
        }
        for (int i = 0; i < this.buttonList.size(); ++i) {
            final GuiButton b = this.buttonList.get(i);
            if (b instanceof GuiButtonPartyMember) {
                final GuiButtonPartyMember button = (GuiButtonPartyMember)b;
                final String text = ((GuiButtonPartyMember)b).getHoveringText(x, y);
                if (text != null) {
                    list.add(new ChatComponentTranslation(text, new Object[0]).getFormattedText());
                }
            }
        }
        if (!list.isEmpty()) {
            this.drawHoveringText((List)list, x - 10, y + 20, this.fontRendererObj);
        }
    }
    
    protected void keyTyped(final char c, final int keyCode) {
        super.keyTyped(c, keyCode);
        if (KeyBindings.partyKey.getKeyCode() == keyCode) {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }
        else if (keyCode == 16 || keyCode == 17 || keyCode == 18 || keyCode == 19) {
            final int key = keyCode - 16;
            GuiButtonPartyMember button = this.getSelectedButton();
            if (button == null) {
                button = GuiParty.buttonAllMembers;
            }
            button.sendActionToServer(button.actions[key]);
        }
    }
    
    protected void setSelectedButton(final GuiButtonPartyMember button) {
        button.isSelected = true;
        if (this.selected != null) {
            this.selected.forceDead();
        }
        this.selected = null;
        if (button != GuiParty.buttonAllMembers) {
            final EntityHumanBase e = button.getEntity();
            this.selected = new EntityCursor(e.worldObj, (Entity)e, 255, this);
            e.worldObj.spawnEntityInWorld((Entity)this.selected);
        }
    }
    
    protected GuiButtonPartyMember getSelectedButton() {
        if (GuiParty.buttonAllMembers.isSelected) {
            return GuiParty.buttonAllMembers;
        }
        for (int i = 0; i < this.buttonList.size(); ++i) {
            final GuiButton currentButton = this.buttonList.get(i);
            if (currentButton.id == 0 && ((GuiButtonPartyMember)currentButton).isSelected) {
                return (GuiButtonPartyMember)currentButton;
            }
        }
        return null;
    }
}
