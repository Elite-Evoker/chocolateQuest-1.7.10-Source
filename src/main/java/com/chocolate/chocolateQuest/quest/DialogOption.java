package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.ArrayList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import java.util.Iterator;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;

public class DialogOption
{
    public int id;
    public String folder;
    public String name;
    public String prompt;
    public String[] text;
    public DialogOption[] options;
    public List<DialogAction> actions;
    public List<DialogCondition> conditions;
    
    public DialogOption() {
        this.folder = "Quest/lang/default";
        this.name = "";
        this.prompt = "";
    }
    
    public DialogOption(final DialogOption[] options, final String name) {
        this();
        this.options = options;
        this.name = name;
    }
    
    public void setID(final int id) {
        this.id = id;
        if (this.options != null) {
            for (int i = 0; i < this.options.length; ++i) {
                this.options[i].setID(i);
            }
        }
    }
    
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        if (this.actions != null) {
            for (final DialogAction action : this.actions) {
                boolean shouldExecuteAction = true;
                if (action.conditions != null) {
                    for (final DialogCondition condition : action.conditions) {
                        if (!condition.matches(player, npc)) {
                            shouldExecuteAction = false;
                            break;
                        }
                    }
                }
                if (shouldExecuteAction) {
                    action.execute(player, npc);
                }
            }
        }
    }
    
    public void writeToNBT(final NBTTagCompound tag) {
        tag.setString("Name", this.name);
        tag.setString("Folder", this.folder);
        if (this.options != null) {
            final NBTTagList list = new NBTTagList();
            for (final DialogOption option : this.options) {
                final NBTTagCompound optionTag = new NBTTagCompound();
                option.writeToNBT(optionTag);
                list.appendTag((NBTBase)optionTag);
            }
            tag.setTag("Options", (NBTBase)list);
        }
        if (this.actions != null) {
            final NBTTagList list = new NBTTagList();
            for (final DialogAction action : this.actions) {
                final NBTTagCompound actionTag = new NBTTagCompound();
                action.writeToNBT(actionTag);
                list.appendTag((NBTBase)actionTag);
            }
            tag.setTag("Actions", (NBTBase)list);
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
        tag.setInteger("ID", this.id);
    }
    
    public void readFromNBT(final NBTTagCompound tag) {
        this.name = tag.getString("Name");
        this.folder = tag.getString("Folder");
        if (tag.hasKey("Options")) {
            final NBTTagList list = (NBTTagList)tag.getTag("Options");
            final int optionCount = list.tagCount();
            this.options = new DialogOption[optionCount];
            for (int i = 0; i < optionCount; ++i) {
                (this.options[i] = new DialogOption()).readFromNBT(list.getCompoundTagAt(i));
            }
        }
        if (tag.hasKey("Actions")) {
            final NBTTagList list = (NBTTagList)tag.getTag("Actions");
            for (int optionCount = list.tagCount(), i = 0; i < optionCount; ++i) {
                this.addAction(DialogAction.getFromNBT(list.getCompoundTagAt(i)));
            }
        }
        if (tag.hasKey("Conditions")) {
            final NBTTagList list = (NBTTagList)tag.getTag("Conditions");
            for (int optionCount = list.tagCount(), i = 0; i < optionCount; ++i) {
                this.addCondition(DialogCondition.getFromNBT(list.getCompoundTagAt(i)));
            }
        }
        this.id = tag.getInteger("ID");
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public void addDialog(final DialogOption newDialog) {
        if (this.options == null) {
            this.options = new DialogOption[] { newDialog };
        }
        else {
            final DialogOption[] newOptions = new DialogOption[this.options.length + 1];
            for (int i = 0; i < this.options.length; ++i) {
                newOptions[i] = this.options[i];
            }
            newOptions[this.options.length] = newDialog;
            this.options = newOptions;
        }
    }
    
    public void removeDialog(final DialogOption newDialog) {
        if (this.options != null) {
            for (int i = 0; i < this.options.length; ++i) {
                if (newDialog == this.options[i]) {
                    this.options[i] = null;
                }
            }
            final DialogOption[] newOptions = new DialogOption[this.options.length - 1];
            int cont = 0;
            for (int j = 0; j < this.options.length; ++j) {
                if (null != this.options[j]) {
                    newOptions[cont] = this.options[j];
                    ++cont;
                }
            }
            this.options = newOptions;
        }
    }
    
    public void addAction(final DialogAction newDialog) {
        if (this.actions == null) {
            this.actions = new ArrayList<DialogAction>();
        }
        this.actions.add(newDialog);
    }
    
    public void removeAction(final DialogAction newDialog) {
        if (this.actions != null) {
            this.actions.remove(newDialog);
            if (this.actions.isEmpty()) {
                this.actions = null;
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
        if (this.conditions != null) {
            this.conditions.remove(condition);
            if (this.conditions.isEmpty()) {
                this.conditions = null;
            }
        }
    }
    
    public void replaceKeys(final String playerName, final EntityHumanNPC npc) {
        for (int i = 0; i < this.text.length; ++i) {
            this.text[i] = this.replaceKeys(this.text[i], playerName, npc);
        }
        this.prompt = this.replaceKeys(this.prompt, playerName, npc);
    }
    
    public String replaceKeys(String s, final String playerName, final EntityHumanNPC npc) {
        if (s.contains("@sp")) {
            s = s.replaceAll("@sp", playerName);
        }
        if (s.contains("@name")) {
            s = s.replaceAll("@name", npc.getCommandSenderName());
        }
        return s;
    }
    
    public void readText(final String lang) {
        DialogManager.readText(lang, this);
    }
    
    public String getDefaultFileName() {
        return BDHelper.getInfoDir() + this.folder + "/default/" + this.name + ".dialog";
    }
    
    public void saveText() {
        DialogManager.saveText(this);
    }
    
    public DialogOption copy() {
        final NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        final DialogOption option = new DialogOption();
        option.readFromNBT(tag);
        return option;
    }
}
