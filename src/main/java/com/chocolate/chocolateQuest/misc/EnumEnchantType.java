package com.chocolate.chocolateQuest.misc;

public enum EnumEnchantType
{
    ENCHANT("Enchantment"), 
    BLACKSMITH("Blacksmith"), 
    GUNSMITH("Gunsmith"), 
    STAVES("Staff enchantment");
    
    public String name;
    
    private EnumEnchantType(final String name) {
        this.name = name;
    }
    
    public static String[] getNames() {
        final EnumEnchantType[] states = values();
        final String[] names = new String[states.length];
        for (int i = 0; i < states.length; ++i) {
            names[i] = states[i].name;
        }
        return names;
    }
}
