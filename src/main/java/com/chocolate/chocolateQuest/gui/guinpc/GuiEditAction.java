package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.quest.DialogCondition;
import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import java.util.List;
import com.chocolate.chocolateQuest.gui.GuiButtonDisplayString;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.gui.GuiScreen;
import com.chocolate.chocolateQuest.gui.GuiButtonMultiOptions;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;
import com.chocolate.chocolateQuest.quest.DialogAction;

public class GuiEditAction extends GuiLinked
{
    DialogAction editingAction;
    GuiButtonTextBox textboxName;
    GuiButtonTextBox textboxValue;
    GuiButtonMultiOptions operatorValue;
    final int buttonsWidth;
    final int buttonHeight;
    GuiScrollOptions conditions;
    static final int EDIT_CONDITION_ID = 11;
    static final int REMOVE_CONDITION_ID = 12;
    static final int ADD_CONDITION_ID = 13;
    static final int CURRENT_CONDITION_LIST = 14;
    GuiScrollOptions conditionTypes;
    
    public GuiEditAction(final GuiScreen prevGui, final DialogAction action) {
        super(prevGui);
        this.editingAction = action;
        this.maxScrollAmmount = 10;
        this.buttonsWidth = this.width - 60;
        this.buttonHeight = 20;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int buttonSeparation = 10;
        final int buttonsWidth = this.width - 60;
        final int buttonHeight = 20;
        int yPos = 10;
        GuiButton name = new GuiButtonDisplayString(0, 30, yPos, this.width / 2, 20, BDHelper.StringColor("l") + "Action: " + BDHelper.StringColor("r") + DialogAction.actions[this.editingAction.getType()].name);
        this.buttonList.add(name);
        yPos += buttonHeight + buttonSeparation;
        if (this.editingAction.hasName()) {
            name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, this.editingAction.getNameForName());
            this.buttonList.add(name);
            yPos += buttonHeight;
            this.textboxName = this.addButon(30, yPos, this.editingAction.getSelectorForName(), buttonsWidth, buttonHeight);
            this.textboxName.textbox.setMaxStringLength(Math.max(255, this.editingAction.name.length()));
            this.textboxName.setText(this.editingAction.name);
            yPos += this.textboxName.height + buttonSeparation;
        }
        if (this.editingAction.hasOperator()) {
            name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, this.editingAction.getNameForOperator());
            this.buttonList.add(name);
            yPos += buttonHeight;
            this.operatorValue = new GuiButtonMultiOptions(0, 30, yPos, buttonsWidth, buttonHeight, this.editingAction.getOptionsForOperator(), this.editingAction.operator);
            this.buttonList.add(this.operatorValue);
            yPos += this.operatorValue.height + buttonSeparation;
        }
        if (this.editingAction.hasValue()) {
            name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, this.editingAction.getNameForValue());
            this.buttonList.add(name);
            yPos += buttonHeight;
            this.textboxValue = this.addButon(30, yPos, this.editingAction.getSelectorForValue(), buttonsWidth, buttonHeight);
            this.textboxValue.textbox.setMaxStringLength(255);
            this.textboxValue.textbox.setText(this.editingAction.value + "");
            if (this.textboxName instanceof GuiButtonSoulBottleSearch) {
                ((GuiButtonSoulBottleSearch)this.textboxName).entityTag = this.editingAction.actionTag;
            }
            yPos += this.textboxValue.height + buttonSeparation;
        }
        name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, "Conditions");
        this.buttonList.add(name);
        yPos += buttonHeight;
        final int editX = 25;
        final int smallButtonWidth = 40;
        final int horizontalSeparation = 10;
        final int verticalSeparation = 4;
        final GuiButton actionName = new GuiButtonDisplayString(0, editX, yPos, buttonsWidth, buttonHeight, "Actions");
        this.buttonList.add(actionName);
        final String[] actionNames = this.getNames(this.editingAction.conditions);
        this.conditions = new GuiScrollOptions(14, editX + smallButtonWidth + horizontalSeparation, yPos, this.width - editX * 2 - smallButtonWidth - horizontalSeparation, 80, actionNames, this.fontRendererObj, -1);
        this.buttonList.add(this.conditions);
        final GuiButton addCondition = new GuiButton(13, editX, yPos, smallButtonWidth, buttonHeight, "Add");
        this.buttonList.add(addCondition);
        final GuiButton editCondition = new GuiButton(11, editX, yPos + (verticalSeparation + buttonHeight), smallButtonWidth, buttonHeight, "Edit");
        this.buttonList.add(editCondition);
        final GuiButton removeCondition = new GuiButton(12, editX, yPos + (verticalSeparation + buttonHeight) * 2, smallButtonWidth, buttonHeight, "Remove");
        this.buttonList.add(removeCondition);
        yPos += 100;
        final List<String> suggestions = new ArrayList<String>();
        this.editingAction.getSuggestions(suggestions);
        for (final String s : suggestions) {
            name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, s);
            this.buttonList.add(name);
            yPos += buttonHeight;
        }
        this.maxScrollAmmount = Math.max(0, yPos - this.height);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        if (button.id == 13) {
            final String[] dialogNames = this.getNames(DialogCondition.conditions);
            this.setFrontButton(this.conditionTypes = new GuiScrollOptions(this.STATIC_ID, 30, 30, this.width - 60, this.height - 60, dialogNames, this.fontRendererObj, -1, 12));
        }
        if (button == this.conditionTypes) {
            final DialogCondition action = DialogCondition.conditions[this.conditionTypes.selectedMode].getNewInstance();
            this.editingAction.addCondition(action);
            this.conditionTypes = null;
            this.setFrontButton(null);
            this.conditions.setModeNames(this.getNames(this.editingAction.conditions));
            return;
        }
        if (this.editingAction.conditions != null && this.conditions.selectedMode >= 0 && this.conditions.selectedMode < this.editingAction.conditions.size()) {
            if (button.id == 11) {
                final DialogCondition newCondition = this.editingAction.conditions.get(this.conditions.selectedMode);
                this.mc.displayGuiScreen((GuiScreen)new GuiEditCondition(this, newCondition));
            }
            if (button.id == 12) {
                final DialogCondition newAction = this.editingAction.conditions.get(this.conditions.selectedMode);
                this.editingAction.removeCondition(newAction);
                this.conditions.setModeNames(this.getNames(this.editingAction.conditions));
            }
        }
        this.updateValues();
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        super.keyTyped(c, i);
        if (this.textboxName != null) {
            this.editingAction.name = this.textboxName.getValue();
        }
        if (this.textboxValue != null) {
            try {
                this.editingAction.value = Integer.valueOf(this.textboxValue.getValue());
            }
            catch (final Exception ex) {}
        }
        this.updateValues();
    }
    
    public void updateValues() {
        if (this.textboxName != null) {
            this.editingAction.name = this.textboxName.getValue();
        }
        if (this.textboxValue != null) {
            this.editingAction.value = BDHelper.getIntegerFromString(this.textboxValue.getValue());
        }
        if (this.operatorValue != null) {
            this.editingAction.operator = this.operatorValue.value;
        }
        if (this.textboxName instanceof GuiButtonSoulBottleSearch) {
            this.editingAction.actionTag = ((GuiButtonSoulBottleSearch)this.textboxName).entityTag;
        }
    }
}
