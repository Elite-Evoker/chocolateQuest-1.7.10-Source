package com.chocolate.chocolateQuest.command;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandAwakeEquipement extends CommandBase
{
    public String getCommandName() {
        return "CQAwakeItem";
    }
    
    public String getCommandUsage(final ICommandSender icommandsender) {
        return "/CQAwakeItem with an item from the mod in the hand";
    }
    
    public void processCommand(final ICommandSender icommandsender, final String[] astring) {
        final EntityPlayer player = (EntityPlayer)icommandsender;
        final ItemStack is = player.getCurrentEquippedItem();
        if (is != null) {
            for (final Awakements aw : Awakements.awekements) {
                if (aw.canBeUsedOnItem(is)) {
                    Awakements.addEnchant(is, aw, aw.getMaxLevel());
                }
            }
        }
    }
    
    public int compareTo(final Object arg0) {
        return 0;
    }
    
    public List addTabCompletionOptions(final ICommandSender par1iCommandSender, final String[] parArray) {
        final String last = parArray[parArray.length - 1];
        final ArrayList list = new ArrayList();
        return list;
    }
}
