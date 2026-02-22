package com.chocolate.chocolateQuest.command;

import net.minecraft.server.MinecraftServer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.World;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandKillCounterDelete extends CommandBase
{
    final int NAME = 1;
    final int PLAYER_NAME = 0;
    final int TYPE = 2;
    
    public String getCommandName() {
        return "CQKillCounter";
    }
    
    public String getCommandUsage(final ICommandSender icommandsender) {
        return "/CQAddKillCounter player_name counter_name show/delete";
    }
    
    public void processCommand(final ICommandSender icommandsender, final String[] astring) {
        if (astring.length > 0) {
            final World world = icommandsender.getEntityWorld();
            String playerName = null;
            if (astring.length > 0) {
                playerName = astring[0];
            }
            String counterName = astring[1];
            if (astring.length > 1) {
                counterName = astring[1];
            }
            boolean delete = false;
            if (astring.length > 2 && (astring[2].equals("delete") || astring[2].equals("DELETE"))) {
                delete = true;
            }
            if (delete) {
                ReputationManager.instance.removeKillCounter(counterName, playerName);
            }
            else {
                icommandsender.addChatMessage((IChatComponent)new ChatComponentText(counterName + " : " + ReputationManager.instance.getKillAmmount(counterName, playerName)));
            }
        }
    }
    
    public int compareTo(final Object arg0) {
        return 0;
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender arg0) {
        return super.canCommandSenderUseCommand(arg0);
    }
    
    public int getRequiredPermissionLevel() {
        return super.getRequiredPermissionLevel();
    }
    
    public List addTabCompletionOptions(final ICommandSender par1iCommandSender, final String[] parArray) {
        final String last = "";
        final ArrayList list = new ArrayList();
        if (parArray.length - 1 == 0) {
            return getListOfStringsMatchingLastWord(parArray, MinecraftServer.getServer().getAllUsernames());
        }
        if (parArray.length - 1 == 1) {
            ReputationManager.instance.getCounterNames(parArray[0], list);
        }
        else if (parArray.length - 1 == 2) {
            list.add("SHOW");
            list.add("DELETE");
        }
        return list;
    }
}
