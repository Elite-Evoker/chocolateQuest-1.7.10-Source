package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.Scoreboard;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionJoinTeam extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        final Scoreboard scoreboard = player.worldObj.getScoreboard();
        final Team team = (Team)scoreboard.getTeam(this.name);
        if (team == null) {
            scoreboard.createTeam(this.name);
        }
        scoreboard.func_151392_a(player.getCommandSenderName(), this.name);
    }
    
    @Override
    public byte getType() {
        return 3;
    }
    
    @Override
    public boolean hasName() {
        return true;
    }
    
    @Override
    public boolean hasValue() {
        return false;
    }
    
    @Override
    public String getNameForName() {
        return "Team name";
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("Adds the player to the team with the specified name");
    }
}
