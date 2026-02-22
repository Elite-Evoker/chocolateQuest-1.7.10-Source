package com.chocolate.chocolateQuest.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler
{
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (KeyBindings.partyKey.isPressed()) {
            final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
            player.openGui((Object)ChocolateQuest.instance, 9, player.worldObj, 0, 0, 0);
        }
    }
}
