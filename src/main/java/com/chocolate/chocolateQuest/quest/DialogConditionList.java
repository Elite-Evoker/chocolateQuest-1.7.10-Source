package com.chocolate.chocolateQuest.quest;

public class DialogConditionList
{
    public final Class<DialogCondition> conditionClass;
    public final String name;
    
    public DialogConditionList(final Class conditionClass, final String name) {
        this.conditionClass = conditionClass;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public DialogCondition getNewInstance() {
        try {
            return this.conditionClass.newInstance();
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
