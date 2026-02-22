package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import java.util.ArrayList;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import io.netty.buffer.ByteBuf;
import java.util.List;

public class PartyAction
{
    public static List<PartyAction> actions;
    public final byte id;
    public static byte lastID;
    public String name;
    public int icon;
    public static PartyAction move;
    public static PartyAction attack;
    public static PartyAction aggressive;
    public static PartyAction defensive;
    public static PartyAction evasive;
    public static PartyAction flee;
    public static PartyAction follow;
    public static PartyAction formation;
    public static PartyAction stand;
    public static PartyAction sit;
    public static PartyAction stop;
    public static PartyAction mount;
    public static PartyAction unmount;
    
    public PartyAction(final String name, final int icon) {
        this.name = name;
        this.icon = icon;
        final byte lastID = PartyAction.lastID;
        PartyAction.lastID = (byte)(lastID + 1);
        this.id = lastID;
        PartyAction.actions.add(this.id, this);
    }
    
    public void write(final ByteBuf inputStream, final MovingObjectPosition playerMop, final EntityPlayer player) {
    }
    
    public void read(final ByteBuf inputStream) {
    }
    
    public void execute(final EntityHumanBase e) {
    }
    
    static {
        PartyAction.actions = new ArrayList<PartyAction>();
        PartyAction.lastID = 0;
        PartyAction.move = new PartyActionMove("ai.move.name", 0);
        PartyAction.attack = new PartyActionAttack("ai.attack.name", 1);
        PartyAction.aggressive = new PartyActionAICombat("ai.ofensive.name", 2, EnumAiCombat.OFFENSIVE.ordinal());
        PartyAction.defensive = new PartyActionAICombat("ai.defensive.name", 3, EnumAiCombat.DEFENSIVE.ordinal());
        PartyAction.evasive = new PartyActionAICombat("ai.evasive.name", 4, EnumAiCombat.EVASIVE.ordinal());
        PartyAction.flee = new PartyActionAICombat("ai.flee.name", 5, EnumAiCombat.FLEE.ordinal());
        PartyAction.follow = new PartyActionAI("ai.follow.name", 6, EnumAiState.FOLLOW.ordinal());
        PartyAction.formation = new PartyActionAI("ai.formation.name", 7, EnumAiState.FORMATION.ordinal());
        PartyAction.stand = new PartyActionAIWard("ai.ward.name", 8, EnumAiState.WARD.ordinal());
        PartyAction.sit = new PartyActionAI("ai.sit.name", 10, EnumAiState.SIT.ordinal());
        PartyAction.stop = new PartyActionStop("ai.stop.name", 9);
        PartyAction.mount = new PartyActionMount("ai.mount.name", 11);
        PartyAction.unmount = new PartyActionUnmount("ai.unmount.name", 12);
    }
}
