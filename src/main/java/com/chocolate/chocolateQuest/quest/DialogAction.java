package com.chocolate.chocolateQuest.quest;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTBase;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;

public abstract class DialogAction
{
    public String name;
    public int value;
    public int operator;
    public NBTTagCompound actionTag;
    public List<DialogCondition> conditions;
    public static DialogActionList[] actions;
    public static final int SHOP = 0;
    public static final int ENCHANT = 1;
    public static final int INVENTORY = 2;
    public static final int JOIN_TEAM = 3;
    public static final int NPC_VARIABLE = 4;
    public static final int GLOBAL_VARIABLE = 5;
    public static final int REPUTATION = 6;
    public static final int GIVE_ITEM = 7;
    public static final int CONSUME_ITEM = 8;
    public static final int COMMAND = 9;
    public static final int SET_OWNER = 10;
    public static final int SET_EQUIPEMENT = 11;
    public static final int SET_AI = 12;
    public static final int NBT_READ = 13;
    public static final int SPAWN_MONSTER = 14;
    public static final int KILL_COUNTER = 15;
    public static final int SET_TIMER = 16;
    public static final int SET_CURRENT_POSITION_HOME = 17;
    static final String[] operations;
    
    public DialogAction() {
        this.name = "";
        this.value = 0;
        this.operator = 0;
        this.conditions = null;
    }
    
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
    }
    
    public int operateValue(final int originalValue) {
        switch (this.operator) {
            case 0: {
                return this.value;
            }
            case 1: {
                return originalValue + this.value;
            }
            case 2: {
                return originalValue - this.value;
            }
            default: {
                return this.value;
            }
        }
    }
    
    public String operateName(final EntityPlayer player) {
        if (this.name.contains("@sp")) {
            this.name = this.name.replace("@sp", player.getCommandSenderName());
        }
        return this.name;
    }
    
    public static DialogAction getFromNBT(final NBTTagCompound tag) {
        final int type = tag.getByte("Type");
        try {
            final DialogAction action = DialogAction.actions[type].dialogClass.newInstance();
            action.readFromNBT(tag);
            return action;
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public abstract byte getType();
    
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
        if (this.hasTag() && this.actionTag != null) {
            tag.setTag("ActionTag", (NBTBase)this.actionTag);
        }
        if (this.conditions != null) {
            final NBTTagList list = new NBTTagList();
            for (final DialogCondition condition : this.conditions) {
                final NBTTagCompound conditionTag = new NBTTagCompound();
                condition.writeToNBT(conditionTag);
                list.appendTag((NBTBase)conditionTag);
            }
            tag.setTag("Conditions", (NBTBase)list);
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
        if (this.hasTag() && tag.hasKey("ActionTag")) {
            this.actionTag = (NBTTagCompound)tag.getTag("ActionTag");
        }
        if (tag.hasKey("Conditions")) {
            final NBTTagList list = (NBTTagList)tag.getTag("Conditions");
            for (int optionCount = list.tagCount(), i = 0; i < optionCount; ++i) {
                this.addCondition(DialogCondition.getFromNBT(list.getCompoundTagAt(i)));
            }
        }
    }
    
    public void addCondition(final DialogCondition newDialog) {
        if (this.conditions == null) {
            this.conditions = new ArrayList<DialogCondition>();
        }
        this.conditions.add(newDialog);
    }
    
    public void removeCondition(final DialogCondition condition) {
        this.conditions.remove(condition);
    }
    
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
        return false;
    }
    
    public String getNameForValue() {
        return "Value";
    }
    
    public int getSelectorForValue() {
        return 1;
    }
    
    public boolean hasOperator() {
        return false;
    }
    
    public String getNameForOperator() {
        return "Operation";
    }
    
    public int getSelectorForOperator() {
        return 2;
    }
    
    public String[] getOptionsForOperator() {
        return DialogAction.operations;
    }
    
    public boolean hasTag() {
        return false;
    }
    
    @Override
    public String toString() {
        String s = DialogAction.actions[this.getType()].toString();
        if (this.hasName()) {
            s = s + " | " + this.getNameString();
        }
        if (this.hasOperator()) {
            s = s + " | " + this.getOperatorString(this.operator);
        }
        if (this.hasValue()) {
            s = s + " | " + this.value;
        }
        return s;
    }
    
    public String getNameString() {
        return this.name;
    }
    
    public String getOperatorString(final int operator) {
        return this.getOptionsForOperator()[operator];
    }
    
    public String getValueString(final int operator) {
        return this.getOptionsForOperator()[operator];
    }
    
    public void getSuggestions(final List<String> list) {
    }
    
    public DialogAction copy() {
        final NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return getFromNBT(tag);
    }
    
    static {
        DialogAction.actions = new DialogActionList[] { new DialogActionList(DialogActionShop.class, "Open shop"), new DialogActionList(DialogActionEnchant.class, "Open item upgrade"), new DialogActionList(DialogActionInventory.class, "Open inventory"), new DialogActionList(DialogActionJoinTeam.class, "Join team"), new DialogActionList(DialogActionNPCVariable.class, "NPC variable"), new DialogActionList(DialogActionGlobalVariable.class, "Global variable"), new DialogActionList(DialogActionReputation.class, "Reputation"), new DialogActionList(DialogActionGiveItem.class, "Give item"), new DialogActionList(DialogActionConsumeItem.class, "Consume item"), new DialogActionList(DialogActionCommand.class, "Command"), new DialogActionList(DialogActionSetOwner.class, "Set owner"), new DialogActionList(DialogActionSetItem.class, "Set equipement"), new DialogActionList(DialogActionSetAI.class, "Set AI"), new DialogActionList(DialogActionSetNBT.class, "Load data from NBT"), new DialogActionList(DialogActionSpawnMonster.class, "Spawn monster"), new DialogActionList(DialogActionCreateCounter.class, "Kill counter"), new DialogActionList(DialogActionSetTimer.class, "Timer"), new DialogActionList(DialogActionSetCurrentPositionHome.class, "Set current position as home") };
        operations = new String[] { "SET", "ADD", "SUBSTRACT" };
    }
}
