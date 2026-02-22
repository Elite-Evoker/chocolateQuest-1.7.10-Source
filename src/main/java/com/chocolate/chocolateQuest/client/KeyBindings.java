package com.chocolate.chocolateQuest.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings
{
    public static KeyBinding partyKey;
    
    public static void init() {
        ClientRegistry.registerKeyBinding(KeyBindings.partyKey = new KeyBinding("key.pong", 33, "key.categories.chocolatequest"));
    }
}
