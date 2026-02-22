package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionScoreValue extends DialogCondition
{
    @Override
    public boolean matches(final EntityPlayer player, final EntityHumanNPC npc) {
        int var = 0;
        final ScoreObjective objective = player.worldObj.getScoreboard().getObjective(this.name);
        if (objective != null) {
            final Score score = player.worldObj.getScoreboard().getValueFromObjective(player.getCommandSenderName(), objective);
            var = score.getScorePoints();
        }
        return this.matches(var, this.value);
    }
    
    @Override
    public byte getType() {
        return 2;
    }
    
    @Override
    public String getNameForName() {
        return "Score name";
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
