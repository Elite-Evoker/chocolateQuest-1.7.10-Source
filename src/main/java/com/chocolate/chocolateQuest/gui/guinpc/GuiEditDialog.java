package com.chocolate.chocolateQuest.gui.guinpc;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEditConversation;
import org.lwjgl.input.Keyboard;
import com.chocolate.chocolateQuest.quest.DialogManager;
import java.util.List;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import net.minecraft.client.gui.GuiButton;
import com.chocolate.chocolateQuest.gui.GuiButtonDisplayString;
import net.minecraft.client.gui.GuiScreen;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBoxList;
import net.minecraft.client.gui.GuiTextField;
import com.chocolate.chocolateQuest.quest.DialogOption;

public class GuiEditDialog extends GuiLinked
{
    DialogOption editingDialog;
    GuiScrollOptions dialogs;
    GuiTextField textboxDialogFolder;
    GuiTextField textboxDialogName;
    GuiTextField textboxDialogPrompt;
    final int NUM_OPTIONS_FRONT = 12;
    static final int EDIT_DIALOG_ID = 1;
    static final int REMOVE_DIALOG_ID = 2;
    static final int ADD_DIALOG_ID = 3;
    GuiButtonTextBoxList text;
    static final int TEXT_DOWN = 6;
    static final int TEXT_UP = 7;
    static final int TEXT_SAVE = 8;
    GuiScrollOptions actions;
    static final int EDIT_ACTION_ID = 11;
    static final int REMOVE_ACTION_ID = 12;
    static final int ADD_ACTION_ID = 13;
    GuiScrollOptions actionTypes;
    GuiScrollOptions conditions;
    static final int EDIT_CONDITION_ID = 21;
    static final int REMOVE_CONDITION_ID = 22;
    static final int ADD_CONDITION_ID = 23;
    GuiScrollOptions conditionTypes;
    GuiScrollOptions browseNames;
    int BROWNSE_NAME_SUGGESTION;
    int OPEN_NAME_SUGGESTION;
    static final int CURRENT_CONDITIONS_LIST = 26;
    static final int CURRENT_ACTIONS_LIST = 27;
    static final int CURRENT_DIALOGS_LIST = 28;
    EntityHumanNPC npc;
    static DialogAction clipBoardAction;
    static DialogCondition clipBoardCondition;
    static DialogOption clipBoardDialog;
    static final byte CLIPBOARD_TYPE_DIALOG = 0;
    static final byte CLIPBOARD_TYPE_ACTION = 1;
    static final byte CLIPBOARD_TYPE_CONDITION = 2;
    static final byte CLIPBOARD_TYPE_NONE = -1;
    byte clipboardType;
    String lang;
    
    public GuiEditDialog(final GuiScreen prevGui, final DialogOption option) {
        super(prevGui);
        this.BROWNSE_NAME_SUGGESTION = 24;
        this.OPEN_NAME_SUGGESTION = 25;
        this.clipboardType = -1;
        this.editingDialog = option;
    }
    
    public GuiEditDialog(final GuiScreen prevGui, final DialogOption option, final EntityHumanNPC npc) {
        this(prevGui, option);
        this.npc = npc;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.lang = this.mc.getLanguageManager().getCurrentLanguage().getLanguageCode();
        this.editingDialog.readText(this.lang);
        final int editX = 25;
        int editY = 5;
        final int buttonsWidth = 110;
        final int buttonHeight = 20;
        final int separation = 5;
        final int editXSeparation = 50;
        final int editYSeparation = 22;
        final int smallButtonWidth = 40;
        final int tabWidth = this.width - editX * 2 - smallButtonWidth - editXSeparation * 2;
        GuiButton button = new GuiButtonDisplayString(0, editX, editY, buttonsWidth, buttonHeight, "File");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, editX + buttonsWidth + editXSeparation, editY, buttonsWidth, buttonHeight, "Option name");
        this.buttonList.add(button);
        editY += 15;
        (this.textboxDialogFolder = new GuiTextField(this.fontRendererObj, editX, editY, buttonsWidth, buttonHeight)).setMaxStringLength(64);
        this.textboxDialogFolder.setText(this.editingDialog.folder);
        this.textFieldList.add(this.textboxDialogFolder);
        (this.textboxDialogName = new GuiTextField(this.fontRendererObj, editX + buttonsWidth + editXSeparation, editY, buttonsWidth, buttonHeight)).setMaxStringLength(25);
        this.textboxDialogName.setText(this.editingDialog.name);
        this.textFieldList.add(this.textboxDialogName);
        final GuiButton openNameSuggestions = new GuiButton(this.OPEN_NAME_SUGGESTION, editX + buttonsWidth + editXSeparation + buttonsWidth, editY, buttonHeight, buttonHeight, "+");
        this.buttonList.add(openNameSuggestions);
        editY += 40;
        button = new GuiButtonDisplayString(0, editX, editY, buttonsWidth, buttonHeight, "Display name");
        this.buttonList.add(button);
        editY += 15;
        (this.textboxDialogPrompt = new GuiTextField(this.fontRendererObj, editX, editY, buttonsWidth, buttonHeight)).setMaxStringLength(64);
        this.textboxDialogPrompt.setText(this.editingDialog.prompt);
        this.textFieldList.add(this.textboxDialogPrompt);
        editY += 40;
        final int textWidth = this.width / 6 * 4;
        this.text = new GuiButtonTextBoxList(0, editX, editY, textWidth, 80, this.fontRendererObj, this.editingDialog.text);
        this.buttonList.add(this.text);
        for (final GuiTextField field : this.text.textbox) {
            this.textFieldList.add(field);
        }
        final GuiButton upButton = new GuiButtonIcon(7, editX + textWidth, editY, 14.0f, 3.0f, 1.0f, 1.0f, "");
        this.buttonList.add(upButton);
        final GuiButton downButton = new GuiButtonIcon(6, editX + textWidth, editY + 64, 14.0f, 4.0f, 1.0f, 1.0f, "");
        this.buttonList.add(downButton);
        final GuiButton saveButton = new GuiButtonIcon(8, editX + textWidth, editY + 32, 14.0f, 5.0f, 1.0f, 1.0f, "");
        this.buttonList.add(saveButton);
        button = new GuiButtonDisplayString(0, editX + textWidth, editY + 44, 50, buttonHeight, "Save text");
        this.buttonList.add(button);
        editY += 100;
        final GuiButton answers = new GuiButtonDisplayString(0, editX, editY, buttonsWidth, buttonHeight, "Answers");
        this.buttonList.add(answers);
        final String[] dialogNames = this.getNames(this.editingDialog.options);
        this.dialogs = new GuiScrollOptions(28, editX + buttonsWidth + separation, editY, tabWidth, 80, dialogNames, this.fontRendererObj, -1);
        this.buttonList.add(this.dialogs);
        final GuiButton addDialog = new GuiButton(3, editX, editY + editYSeparation, 40, buttonHeight, "Add");
        this.buttonList.add(addDialog);
        final GuiButton editDialog = new GuiButton(1, editX + editXSeparation, editY + editYSeparation, 40, buttonHeight, "Edit");
        this.buttonList.add(editDialog);
        final GuiButton removeDialog = new GuiButton(2, editX + editXSeparation, editY + editYSeparation * 2, 40, buttonHeight, "Remove");
        this.buttonList.add(removeDialog);
        editY += 100;
        final GuiButton conditionName = new GuiButtonDisplayString(0, editX, editY, buttonsWidth, buttonHeight, "Conditions");
        this.buttonList.add(conditionName);
        final String[] conditionNames = this.getNames(this.editingDialog.conditions);
        this.conditions = new GuiScrollOptions(26, editX + buttonsWidth + separation, editY, tabWidth, 80, conditionNames, this.fontRendererObj, -1);
        this.buttonList.add(this.conditions);
        final GuiButton addCondition = new GuiButton(23, editX, editY + editYSeparation, 40, buttonHeight, "Add");
        this.buttonList.add(addCondition);
        final GuiButton editCondition = new GuiButton(21, editX + editXSeparation, editY + editYSeparation, 40, buttonHeight, "Edit");
        this.buttonList.add(editCondition);
        final GuiButton removeCondition = new GuiButton(22, editX + editXSeparation, editY + editYSeparation * 2, 40, buttonHeight, "Remove");
        this.buttonList.add(removeCondition);
        editY += 100;
        final GuiButton actionName = new GuiButtonDisplayString(0, editX, editY, buttonsWidth, buttonHeight, "Actions");
        this.buttonList.add(actionName);
        final String[] actionNames = this.getNames(this.editingDialog.actions);
        this.actions = new GuiScrollOptions(27, editX + buttonsWidth + separation, editY, tabWidth, 80, actionNames, this.fontRendererObj, -1);
        this.buttonList.add(this.actions);
        final GuiButton addAction = new GuiButton(13, editX, editY + editYSeparation, smallButtonWidth, buttonHeight, "Add");
        this.buttonList.add(addAction);
        final GuiButton editAction = new GuiButton(11, editX + editXSeparation, editY + editYSeparation, smallButtonWidth, buttonHeight, "Edit");
        this.buttonList.add(editAction);
        final GuiButton removeAction = new GuiButton(12, editX + editXSeparation, editY + editYSeparation * 2, smallButtonWidth, buttonHeight, "Remove");
        this.buttonList.add(removeAction);
        editY += 100;
        this.maxScrollAmmount = editY - this.height;
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        if (button.id == 3) {
            final DialogOption newDialog = new DialogOption();
            this.editingDialog.addDialog(newDialog);
            newDialog.folder = this.editingDialog.folder;
            this.mc.displayGuiScreen((GuiScreen)new GuiEditDialog(this, newDialog));
        }
        if (this.editingDialog.options != null && this.dialogs.selectedMode >= 0 && this.dialogs.selectedMode < this.editingDialog.options.length) {
            if (button.id == 1) {
                final DialogOption newDialog = this.editingDialog.options[this.dialogs.selectedMode];
                this.mc.displayGuiScreen((GuiScreen)new GuiEditDialog(this, newDialog));
            }
            if (button.id == 2) {
                final DialogOption newDialog = this.editingDialog.options[this.dialogs.selectedMode];
                this.editingDialog.removeDialog(newDialog);
                this.dialogs.setModeNames(this.getNames(this.editingDialog.options));
            }
        }
        if (button.id == 13) {
            final String[] dialogNames = this.getNames(DialogAction.actions);
            this.setFrontButton(this.actionTypes = new GuiScrollOptions(this.STATIC_ID, 30, 30, this.width - 60, this.height - 60, dialogNames, this.fontRendererObj, -1, 12));
        }
        if (button == this.actionTypes) {
            final DialogAction action = DialogAction.actions[this.actionTypes.selectedMode].getNewInstance();
            this.editingDialog.addAction(action);
            this.actionTypes = null;
            this.setFrontButton(null);
            this.actions.setModeNames(this.getNames(this.editingDialog.actions));
            return;
        }
        if (this.editingDialog.actions != null && this.actions.selectedMode >= 0 && this.actions.selectedMode < this.editingDialog.actions.size()) {
            if (button.id == 11) {
                final DialogAction newAction = this.editingDialog.actions.get(this.actions.selectedMode);
                this.mc.displayGuiScreen((GuiScreen)new GuiEditAction(this, newAction));
            }
            if (button.id == 12) {
                final DialogAction newAction = this.editingDialog.actions.get(this.actions.selectedMode);
                this.editingDialog.removeAction(newAction);
                this.actions.setModeNames(this.getNames(this.editingDialog.actions));
            }
        }
        if (button.id == 23) {
            final String[] dialogNames = this.getNames(DialogCondition.conditions);
            this.setFrontButton(this.conditionTypes = new GuiScrollOptions(this.STATIC_ID, 30, 30, this.width - 60, this.height - 60, dialogNames, this.fontRendererObj, -1, 12));
        }
        if (button == this.conditionTypes) {
            final DialogCondition action2 = DialogCondition.conditions[this.conditionTypes.selectedMode].getNewInstance();
            this.editingDialog.addCondition(action2);
            this.conditionTypes = null;
            this.setFrontButton(null);
            this.conditions.setModeNames(this.getNames(this.editingDialog.conditions));
            return;
        }
        if (this.editingDialog.conditions != null && this.conditions.selectedMode >= 0 && this.conditions.selectedMode < this.editingDialog.conditions.size()) {
            if (button.id == 21) {
                final DialogCondition newCondition = this.editingDialog.conditions.get(this.conditions.selectedMode);
                this.mc.displayGuiScreen((GuiScreen)new GuiEditCondition(this, newCondition));
            }
            if (button.id == 22) {
                final DialogCondition newAction2 = this.editingDialog.conditions.get(this.conditions.selectedMode);
                this.editingDialog.removeCondition(newAction2);
                this.conditions.setModeNames(this.getNames(this.editingDialog.conditions));
            }
        }
        if (button.id == this.OPEN_NAME_SUGGESTION) {
            final String[] names = DialogManager.getOptionNames(this.editingDialog.folder);
            if (names != null) {
                this.setFrontButton(this.browseNames = new GuiScrollOptions(this.STATIC_ID, 30, 30, this.width - 60, this.height - 60, names, this.fontRendererObj, -1, 12));
            }
        }
        if (button == this.browseNames) {
            this.textboxDialogName.setText(this.browseNames.modeNames[this.browseNames.selectedMode]);
            this.onDataChange();
            this.browseNames = null;
            this.setFrontButton(null);
            return;
        }
        if (button.id == 6) {
            this.text.scrollDown();
        }
        if (button.id == 7) {
            this.text.scrollUp();
        }
        if (button.id == 8) {
            this.editingDialog.saveText();
        }
        else if (button.id == 28) {
            this.clipboardType = 0;
        }
        else if (button.id == 27) {
            this.clipboardType = 1;
        }
        else if (button.id == 26) {
            this.clipboardType = 2;
        }
        else {
            this.clipboardType = -1;
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        super.keyTyped(c, i);
        if (this.textboxDialogFolder.isFocused() || this.textboxDialogName.isFocused()) {
            this.editingDialog.folder = this.textboxDialogFolder.getText();
            this.editingDialog.readText(this.lang);
            this.onDataChange();
        }
        else {
            this.editingDialog.text = this.text.getValues();
            this.editingDialog.prompt = this.textboxDialogPrompt.getText();
        }
        if (i == 46 && Keyboard.isKeyDown(29)) {
            if (this.clipboardType == 0 && this.dialogs.selectedMode < this.editingDialog.options.length) {
                GuiEditDialog.clipBoardDialog = this.editingDialog.options[this.dialogs.selectedMode];
            }
            if (this.clipboardType == 1 && this.actions.selectedMode < this.editingDialog.actions.size()) {
                GuiEditDialog.clipBoardAction = this.editingDialog.actions.get(this.actions.selectedMode);
            }
            if (this.clipboardType == 2 && this.actions.selectedMode < this.editingDialog.conditions.size()) {
                GuiEditDialog.clipBoardCondition = this.editingDialog.conditions.get(this.conditions.selectedMode);
            }
        }
        if (i == 47 && Keyboard.isKeyDown(29)) {
            if (this.clipboardType == 0 && GuiEditDialog.clipBoardDialog != null) {
                this.editingDialog.addDialog(GuiEditDialog.clipBoardDialog.copy());
                final String[] names = this.getNames(this.editingDialog.options);
                this.dialogs.setModeNames(names);
            }
            if (this.clipboardType == 1 && GuiEditDialog.clipBoardAction != null) {
                this.editingDialog.addAction(GuiEditDialog.clipBoardAction.copy());
                final String[] names = this.getNames(this.editingDialog.actions);
                this.actions.setModeNames(names);
            }
            if (this.clipboardType == 2 && GuiEditDialog.clipBoardCondition != null) {
                this.editingDialog.addCondition(GuiEditDialog.clipBoardCondition.copy());
                final String[] names = this.getNames(this.editingDialog.conditions);
                this.conditions.setModeNames(names);
            }
        }
    }
    
    protected void onDataChange() {
        this.editingDialog.name = this.textboxDialogName.getText();
        this.editingDialog.readText(this.lang);
        this.text.setValues(this.editingDialog.text);
        this.textboxDialogPrompt.setText(this.editingDialog.prompt);
    }
    
    @Override
    protected void mouseClicked(final int x, final int y, final int r) {
        super.mouseClicked(x, y, r);
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float fl) {
        super.drawScreen(x, y, fl);
    }
    
    @Override
    public void closeGUI() {
        this.mc.displayGuiScreen(this.prevGui);
        if ((this.prevGui == null || this.prevGui instanceof GuiNPC) && this.npc != null) {
            final IMessage packet = (IMessage)new PacketEditConversation(this.npc, new int[0]);
            ChocolateQuest.channel.sendPaquetToServer(packet);
        }
    }
}
