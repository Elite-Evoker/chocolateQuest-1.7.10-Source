package com.chocolate.chocolateQuest.gui.guinpc;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import java.util.ArrayList;
import com.chocolate.chocolateQuest.gui.GuiButtonDisplayString;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.gui.GuiScreen;
import com.chocolate.chocolateQuest.gui.GuiButtonMultiOptions;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;
import com.chocolate.chocolateQuest.quest.DialogCondition;

public class GuiEditCondition extends GuiLinked
{
    DialogCondition editingAction;
    GuiButtonTextBox textboxName;
    GuiButtonTextBox textboxValue;
    GuiButtonMultiOptions operatorValue;
    
    public GuiEditCondition(final GuiScreen prevGui, final DialogCondition action) {
        super(prevGui);
        this.editingAction = action;
        this.maxScrollAmmount = 10;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int buttonsWidth = this.width - 60;
        final int buttonHeight = 20;
        final int buttonSeparation = 10;
        int yPos = 10;
        GuiButton name = new GuiButtonDisplayString(0, 30, yPos, this.width / 2, 20, BDHelper.StringColor("l") + "Condition: " + BDHelper.StringColor("r") + DialogCondition.conditions[this.editingAction.getType()].name);
        this.buttonList.add(name);
        if (this.editingAction.hasName()) {
            yPos += buttonHeight + buttonSeparation;
            name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, this.editingAction.getNameForName());
            this.buttonList.add(name);
            yPos += buttonHeight;
            this.textboxName = this.addButon(30, yPos, this.editingAction.getSelectorForName(), buttonsWidth, buttonHeight);
            this.textboxName.textbox.setMaxStringLength(255);
            this.textboxName.textbox.setText(this.editingAction.name);
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
            yPos += this.textboxValue.height + buttonSeparation;
        }
        final List<String> suggestions = new ArrayList<String>();
        this.editingAction.getSuggestions(suggestions);
        for (final String s : suggestions) {
            name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, s);
            this.buttonList.add(name);
            yPos += buttonHeight;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        this.updateValues();
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        super.keyTyped(c, i);
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
    }
}
