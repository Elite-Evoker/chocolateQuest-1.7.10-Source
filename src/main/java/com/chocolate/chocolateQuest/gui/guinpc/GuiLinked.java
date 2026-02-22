package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.gui.GuiButtonItemSearch;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBoxInteger;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;
import org.lwjgl.input.Mouse;
import java.util.Iterator;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import java.util.ArrayList;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.client.gui.GuiTextField;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiLinked extends GuiScreen
{
    GuiScreen prevGui;
    GuiButton frontButton;
    int STATIC_ID;
    GuiButton backButton;
    GuiButton scrollUp;
    GuiButton scrollDown;
    List<GuiTextField> textFieldList;
    int scrollAmmount;
    int maxScrollAmmount;
    boolean hasNavigationMenu;
    
    public GuiLinked() {
        this.STATIC_ID = 1000;
        this.scrollAmmount = 0;
        this.maxScrollAmmount = 100;
        this.hasNavigationMenu = true;
        this.hasNavigationMenu = false;
        this.maxScrollAmmount = 0;
    }
    
    public GuiLinked(final GuiScreen prevGui) {
        this.STATIC_ID = 1000;
        this.scrollAmmount = 0;
        this.maxScrollAmmount = 100;
        this.hasNavigationMenu = true;
        this.prevGui = prevGui;
    }
    
    public GuiLinked(final GuiScreen prevGui, final EntityHumanNPC npc) {
        this(prevGui);
    }
    
    public void initGui() {
        super.initGui();
        this.scrollAmmount = 0;
        this.textFieldList = new ArrayList<GuiTextField>();
        this.backButton = new GuiButtonIcon(this.STATIC_ID, 3, 10, 15.0f, 6.0f, 1.0f, 1.0f, "");
        this.buttonList.add(this.backButton);
        this.scrollUp = new GuiButtonIcon(this.STATIC_ID, 3, 30, 15.0f, 7.0f, 1.0f, 0.5f, "");
        this.buttonList.add(this.scrollUp);
        this.scrollDown = new GuiButtonIcon(this.STATIC_ID, 3, 39, 15.0f, 7.5f, 1.0f, 0.5f, "");
        this.buttonList.add(this.scrollDown);
    }
    
    protected void keyTyped(final char c, final int i) {
        if (i == 1) {
            if (this.frontButton != null) {
                this.setFrontButton(null);
            }
            else {
                this.closeGUI();
            }
            return;
        }
        boolean textBoxFoxused = false;
        for (final GuiTextField field : this.textFieldList) {
            field.textboxKeyTyped(c, i);
            if (field.isFocused()) {
                textBoxFoxused = true;
            }
        }
        if (!textBoxFoxused) {
            if (i == 208 || i == 31) {
                this.scrollDown(this.height / 2);
            }
            if (i == 200 || i == 17) {
                this.scrollUp(this.height / 2);
            }
        }
    }
    
    protected void mouseClicked(final int x, final int y, final int r) {
        if (this.frontButton != null) {
            if (this.frontButton.mousePressed(this.mc, x, y)) {
                this.actionPerformed(this.frontButton);
            }
            if (this.backButton != null && this.backButton.mousePressed(this.mc, x, y)) {
                this.actionPerformed(this.backButton);
            }
            return;
        }
        for (final GuiTextField field : this.textFieldList) {
            field.mouseClicked(x, y, r);
        }
        super.mouseClicked(x, y, r);
    }
    
    public void handleMouseInput() {
        super.handleMouseInput();
        final int k = Mouse.getEventDWheel();
        if (this.frontButton instanceof GuiScrollOptions) {
            if (k > 0) {
                ((GuiScrollOptions)this.frontButton).scrollUp();
            }
            else if (k < 0) {
                ((GuiScrollOptions)this.frontButton).scrollDown();
            }
        }
        else if (k > 0) {
            this.scrollUp(20);
        }
        else if (k < 0) {
            this.scrollDown(20);
        }
    }
    
    public void drawScreen(final int x, final int y, final float fl) {
        this.drawBackground(0);
        for (final GuiTextField field : this.textFieldList) {
            field.drawTextBox();
        }
        super.drawScreen(x, y, fl);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (this.backButton == button) {
            if (this.frontButton != null) {
                this.setFrontButton(null);
            }
            else {
                this.closeGUI();
            }
        }
        if (this.scrollUp == button) {
            this.scrollUp(this.height / 2);
        }
        if (this.scrollDown == button) {
            this.scrollDown(this.height / 2);
        }
    }
    
    protected void setFrontButton(final GuiButton button) {
        if (button == null) {
            this.buttonList.remove(this.frontButton);
            this.frontButton = null;
        }
        else {
            this.frontButton = button;
            this.buttonList.add(this.frontButton);
            this.frontButton.enabled = false;
        }
    }
    
    public void updateScreen() {
        if (this.frontButton != null) {
            this.frontButton.enabled = true;
        }
        super.updateScreen();
    }
    
    protected void scrollUp(int ammount) {
        if (this.scrollAmmount - ammount < 0) {
            ammount = this.scrollAmmount;
        }
        for (final Object o : this.buttonList) {
            final GuiButton b = (GuiButton)o;
            if (b.id != this.STATIC_ID) {
                final GuiButton guiButton = b;
                guiButton.yPosition += ammount;
            }
        }
        for (final GuiTextField guiTextField : this.textFieldList) {
            final GuiTextField field = guiTextField;
            guiTextField.yPosition += ammount;
        }
        this.scrollAmmount -= ammount;
    }
    
    protected void scrollDown(int ammount) {
        if (this.scrollAmmount + ammount >= this.maxScrollAmmount) {
            ammount = this.maxScrollAmmount - this.scrollAmmount;
        }
        for (final Object o : this.buttonList) {
            final GuiButton b = (GuiButton)o;
            if (b.id != this.STATIC_ID) {
                final GuiButton guiButton = b;
                guiButton.yPosition -= ammount;
            }
        }
        for (final GuiTextField guiTextField : this.textFieldList) {
            final GuiTextField field = guiTextField;
            guiTextField.yPosition -= ammount;
        }
        this.scrollAmmount += ammount;
    }
    
    public GuiButtonTextBox addButon(final int x, final int y, final int selector, final int buttonWidth, final int buttonHeight) {
        GuiButtonTextBox button;
        if (selector == 1) {
            button = new GuiButtonTextBoxInteger(0, x, y, buttonWidth, buttonHeight, this.fontRendererObj);
        }
        else if (selector == 3) {
            button = new GuiButtonItemSearch(0, x, y, buttonWidth, buttonHeight * 2, this.fontRendererObj);
            this.buttonList.add(button);
        }
        else if (selector == 4) {
            button = new GuiButtonSoulBottleSearch(0, x, y, buttonWidth, buttonHeight * 2, this.fontRendererObj);
            this.buttonList.add(button);
        }
        else {
            button = new GuiButtonTextBox(0, x, y, buttonWidth, buttonHeight, this.fontRendererObj);
        }
        this.buttonList.add(button);
        this.textFieldList.add(button.textbox);
        return button;
    }
    
    public void closeGUI() {
        this.mc.displayGuiScreen(this.prevGui);
    }
    
    public String[] getNames(final Object[] array) {
        String[] dialogNames;
        if (array != null) {
            dialogNames = new String[array.length];
            for (int i = 0; i < dialogNames.length; ++i) {
                dialogNames[i] = array[i].toString();
            }
        }
        else {
            dialogNames = new String[0];
        }
        return dialogNames;
    }
    
    public String[] getNames(final List list) {
        if (list == null) {
            return new String[0];
        }
        return this.getNames(list.toArray());
    }
}
