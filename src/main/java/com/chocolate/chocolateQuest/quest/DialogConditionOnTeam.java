package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.scoreboard.ScorePlayerTeam;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionOnTeam extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        final int var = 0;
        final ScorePlayerTeam team = player.worldObj.getScoreboard().getPlayersTeam(player.getCommandSenderName());
        return team != null && team.getRegisteredName().equals(this.name);
    }
    
    @Override
    public byte getType() {
        return 5;
    }
    
    @Override
    public String getNameForName() {
        return "Team name";
    }
    
    @Override
    public boolean hasValue() {
        return false;
    }
    
    @Override
    public boolean hasOperator() {
        return false;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
