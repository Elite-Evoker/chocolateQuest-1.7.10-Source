package com.chocolate.chocolateQuest.gui;

import net.minecraft.entity.SharedMonsterAttributes;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanDummy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class GuiDummy extends GuiHuman
{
    GuiButtonSlider healthScale;
    GuiButtonSlider dropRight;
    GuiButtonSlider dropHelmet;
    GuiButtonSlider dropBody;
    GuiButtonSlider dropLegs;
    GuiButtonSlider dropFeet;
    GuiButtonSlider dropLeft;
    GuiButtonIconOnOff lockInventory;
    
    public GuiDummy(final EntityHumanBase human, final IInventory par1iInventory, final EntityPlayer playerInventory) {
        super(human, par1iInventory, playerInventory);
        this.combatAINames = new String[] { "Offensive", "Defensive", "Evasive", "Flee", "Backstab" };
    }
    
    @Override
    public void initGui() {
        super.initGui();
        if (this.human instanceof EntityHumanDummy) {
            this.healthScale = new GuiButtonSlider(10, 10, 10, "Health scale", (float)(this.human.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() / 10.0));
            this.buttonList.add(this.healthScale);
        }
        final int desp = 22;
        int pos = 1;
        final int posY = 20;
        this.dropRight = new GuiButtonSlider(10 + pos, 10, posY + pos * desp, "Drop hand R.", this.human.getEquipmentDropChances(0), 1);
        this.buttonList.add(this.dropRight);
        ++pos;
        this.dropLeft = new GuiButtonSlider(10 + pos, 10, posY + pos * desp, "Drop hand L.", this.human.leftHandDropChances, 1);
        this.buttonList.add(this.dropLeft);
        ++pos;
        this.dropHelmet = new GuiButtonSlider(10 + pos, 10, posY + pos * desp, "Drop head", this.human.getEquipmentDropChances(4), 1);
        this.buttonList.add(this.dropHelmet);
        ++pos;
        this.dropBody = new GuiButtonSlider(10 + pos, 10, posY + pos * desp, "Drop body", this.human.getEquipmentDropChances(3), 1);
        this.buttonList.add(this.dropBody);
        ++pos;
        this.dropLegs = new GuiButtonSlider(10 + pos, 10, posY + pos * desp, "Drop legs", this.human.getEquipmentDropChances(2), 1);
        this.buttonList.add(this.dropLegs);
        ++pos;
        this.dropFeet = new GuiButtonSlider(10 + pos, 10, posY + pos * desp, "Drop feet", this.human.getEquipmentDropChances(1), 1);
        this.buttonList.add(this.dropFeet);
        this.lockInventory = new GuiButtonIconOnOff(654321, this.width / 2 - 52, this.height / 2 - 83, 13.0f, 3.0f, 1.0f, 1.0f, "", this.human.inventoryLocked);
        this.buttonList.add(this.lockInventory);
    }
    
    @Override
    public void onGuiClosed() {
        if (this.human.worldObj.isRemote) {
            this.human.partyPositionAngle = this.teamPositionButton.getAngle();
            this.human.partyDistanceToLeader = this.teamPositionButton.getDistance();
            this.human.AIMode = this.passiveAI.selectedMode;
            this.human.AICombatMode = this.combatAI.selectedMode;
            this.human.partyPositionPersistance = true;
            this.human.updateHands();
            if (this.healthScale != null) {
                this.human.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)(this.healthScale.sliderValue * this.healthScale.SLIDER_MAX_VALUE));
            }
            this.human.leftHandDropChances = this.dropLeft.sliderValue;
            this.human.setEquipmentDropChances(0, this.dropRight.sliderValue);
            this.human.setEquipmentDropChances(4, this.dropHelmet.sliderValue);
            this.human.setEquipmentDropChances(3, this.dropBody.sliderValue);
            this.human.setEquipmentDropChances(2, this.dropLegs.sliderValue);
            this.human.setEquipmentDropChances(1, this.dropFeet.sliderValue);
            this.human.inventoryLocked = this.lockInventory.isOn;
        }
        super.onGuiClosed();
    }
}
