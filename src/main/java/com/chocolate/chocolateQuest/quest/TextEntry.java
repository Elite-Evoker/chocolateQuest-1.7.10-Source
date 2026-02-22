package com.chocolate.chocolateQuest.quest;

class TextEntry
{
    public String name;
    public String prompt;
    public String[] text;
    
    public TextEntry() {
    }
    
    public TextEntry(final String name, final String prompt, final String[] text) {
        this.name = name;
        this.prompt = prompt;
        this.text = text;
    }
}
