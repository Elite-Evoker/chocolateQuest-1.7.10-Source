package com.chocolate.chocolateQuest.quest;

public class DialogActionList
{
    public final Class<DialogAction> dialogClass;
    public final String name;
    
    public DialogActionList(final Class dialogClass, final String name) {
        this.dialogClass = dialogClass;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public DialogAction getNewInstance() {
        try {
            return this.dialogClass.newInstance();
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
