package com.chocolate.chocolateQuest.command;

import net.minecraft.server.MinecraftServer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.World;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandReputation extends CommandBase
{
    final int ACTION_TYPE = 0;
    final int PLAYER_NAME = 1;
    final int TEAM_NAME = 2;
    final int AMMOUNT_POSITION = 3;
    String[] NAMES;
    String[] NUMBERS_1_TO_10;
    
    public CommandReputation() {
        this.NAMES = new String[] { "set", "show" };
        this.NUMBERS_1_TO_10 = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
    }
    
    public String getCommandName() {
        return "CQReputation";
    }
    
    public String getCommandUsage(final ICommandSender icommandsender) {
        return "/CQReputation set/show team ammount";
    }
    
    public void processCommand(final ICommandSender icommandsender, final String[] astring) {
        if (astring.length > 0) {
            final World world = icommandsender.getEntityWorld();
            int type = 0;
            if (astring[0].equals(this.NAMES[0])) {
                type = 1;
            }
            String playerName = null;
            if (astring.length > 1) {
                playerName = astring[1];
            }
            String teamName = null;
            if (astring.length > 2) {
                teamName = astring[2];
            }
            if (playerName != null && teamName != null) {
                if (type == 0) {
                    final int rep = ReputationManager.instance.getPlayerReputation(playerName, teamName);
                    String color = "+" + BDHelper.StringColor("2");
                    if (rep < 0) {
                        color = BDHelper.StringColor("4");
                    }
                    icommandsender.addChatMessage((IChatComponent)new ChatComponentText(playerName + " reputation for team " + teamName + " = " + color + rep));
                }
                else if (astring.length > 3) {
                    final int ammount = Integer.parseInt(astring[3]);
                    ReputationManager.instance.setReputation(playerName, teamName, ammount);
                }
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
        String last = "";
        final ArrayList list = new ArrayList();
        if (parArray.length - 1 == 0) {
            last = parArray[parArray.length - 1];
            for (final String s : this.NAMES) {
                if (s.startsWith(last)) {
                    list.add(s);
                }
            }
        }
        else {
            if (parArray.length - 1 == 1) {
                return getListOfStringsMatchingLastWord(parArray, MinecraftServer.getServer().getAllUsernames());
            }
            if (parArray.length - 1 == 2) {
                final String playerName = parArray[1];
            }
        }
        return list;
    }
}
