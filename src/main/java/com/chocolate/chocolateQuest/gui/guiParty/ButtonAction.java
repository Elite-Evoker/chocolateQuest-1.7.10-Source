package com.chocolate.chocolateQuest.gui.guiParty;

class ButtonAction
{
    public int xPosition;
    public int yPosition;
    public PartyAction action;
    
    public ButtonAction(final int x, final int y, final int action) {
        this.xPosition = x;
        this.yPosition = y;
        this.action = PartyAction.actions.get(action);
    }
    
    public int getIcon() {
        return this.action.icon;
    }
    
    public String getName() {
        return this.action.name;
    }
    
    public boolean isMouseOver(final int x, final int y) {
        final int size = 16;
        final boolean b = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + size && y < this.yPosition + size;
        return b;
    }
}
