package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public abstract class DialogCondition
{
    public String name;
    public int value;
    public int operator;
    public static final int EQUALS = 0;
    public static final int DIFFERENT = 1;
    public static final int GREATER = 2;
    public static final int LESSER = 3;
    public static final String[] OPERATORS_MATHEMATICAL;
    public static final String[] OPERATORS_YES_NO;
    static final int MATHEMATICAL = 0;
    static final int YES_NO = 1;
    public static final byte LOCAL_VARIABLE = 0;
    public static final byte GLOBAL_VARIABLE = 1;
    public static final byte SCORE_VALUE = 2;
    public static final byte REPUTATION = 3;
    public static final byte ITEM_ON_INVENTORY = 4;
    public static final byte ON_TEAM = 5;
    public static final byte TIME = 6;
    public static final byte COMMAND = 7;
    public static final byte KILL_COUNTER = 8;
    public static final byte NEARBY_ENTITY = 9;
    public static final byte TIMER = 10;
    public static DialogConditionList[] conditions;
    
    public DialogCondition() {
        this.name = "";
    }
    
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        return true;
    }
    
    public boolean matches(final int var, final int value) {
        switch (this.operator) {
            case 1: {
                return var != value;
            }
            case 3: {
                return var < value;
            }
            case 2: {
                return var > value;
            }
            default: {
                return var == value;
            }
        }
    }
    
    public boolean matches(final long var, final long value) {
        switch (this.operator) {
            case 1: {
                return var != value;
            }
            case 3: {
                return var < value;
            }
            case 2: {
                return var > value;
            }
            default: {
                return var == value;
            }
        }
    }
    
    public void readFromNBT(final NBTTagCompound tag) {
        if (this.hasName()) {
            this.name = tag.getString("Name");
        }
        if (this.hasValue()) {
            this.value = tag.getInteger("Value");
        }
        if (this.hasOperator()) {
            this.operator = tag.getInteger("Operator");
        }
    }
    
    public void writeToNBT(final NBTTagCompound tag) {
        tag.setByte("Type", this.getType());
        if (this.hasName()) {
            tag.setString("Name", this.name);
        }
        if (this.hasValue()) {
            tag.setInteger("Value", this.value);
        }
        if (this.hasOperator()) {
            tag.setInteger("Operator", this.operator);
        }
    }
    
    public static DialogCondition getFromNBT(final NBTTagCompound tag) {
        final int type = tag.getByte("Type");
        try {
            final DialogCondition condition = DialogCondition.conditions[type].getNewInstance();
            condition.readFromNBT(tag);
            return condition;
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public abstract byte getType();
    
    public boolean hasName() {
        return true;
    }
    
    public String getNameForName() {
        return "Name";
    }
    
    public int getSelectorForName() {
        return 0;
    }
    
    public boolean hasValue() {
        return true;
    }
    
    public String getNameForValue() {
        return "Value";
    }
    
    public int getSelectorForValue() {
        return 1;
    }
    
    public boolean hasOperator() {
        return true;
    }
    
    public String getNameForOperator() {
        return "Operator";
    }
    
    public int getSelectorForOperator() {
        return 0;
    }
    
    public String[] getOptionsForOperator() {
        if (this.getSelectorForOperator() == 0) {
            return DialogCondition.OPERATORS_MATHEMATICAL;
        }
        if (this.getSelectorForOperator() == 1) {
            return DialogCondition.OPERATORS_YES_NO;
        }
        return new String[] { "undefined" };
    }
    
    public String getOperatorString(final int operator) {
        if (this.getSelectorForOperator() == 0) {
            return DialogCondition.OPERATORS_MATHEMATICAL[operator];
        }
        if (this.getSelectorForOperator() == 1) {
            return DialogCondition.OPERATORS_YES_NO[operator];
        }
        return "undefined";
    }
    
    @Override
    public String toString() {
        String s = DialogCondition.conditions[this.getType()].toString();
        if (this.hasName()) {
            s = s + " | " + this.name;
        }
        if (this.hasOperator()) {
            s = s + " | " + this.getOperatorString(this.operator);
        }
        if (this.hasValue()) {
            s = s + " | " + this.value;
        }
        return s;
    }
    
    public void getSuggestions(final List<String> list) {
    }
    
    public DialogCondition copy() {
        final NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return getFromNBT(tag);
    }
    
    static {
        OPERATORS_MATHEMATICAL = new String[] { "EQUALS", "DIFFERENT", "GREATER", "LESSER" };
        OPERATORS_YES_NO = new String[] { "No", "Yes" };
        DialogCondition.conditions = new DialogConditionList[] { new DialogConditionList(DialogConditionLocalVariable.class, "npc variable"), new DialogConditionList(DialogConditionGlobalVariable.class, "Global variable"), new DialogConditionList(DialogConditionScoreValue.class, "Score value"), new DialogConditionList(DialogConditionReputation.class, "Reputation"), new DialogConditionList(DialogConditionItemOnInventory.class, "Item on inventory"), new DialogConditionList(DialogConditionOnTeam.class, "On team"), new DialogConditionList(DialogConditionTime.class, "Time"), new DialogConditionList(DialogConditionCommand.class, "Command"), new DialogConditionList(DialogConditionKillCounter.class, "Kill Counter"), new DialogConditionList(DialogConditionNearbyEntity.class, "Nearby entity"), new DialogConditionList(DialogConditionNPCTimer.class, "Timer") };
    }
}
