package com.chocolate.chocolateQuest.quest.worldManager;

import java.util.Collection;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReputationManager extends WorldManagerBase
{
    public static ReputationManager instance;
    Map reputation;
    Map<String, Integer> globals;
    List<KillCounter> killCounters;
    
    public ReputationManager() {
        this.reputation = new HashMap();
        this.globals = new HashMap<String, Integer>();
        this.killCounters = new ArrayList<KillCounter>();
    }
    
    public int getGlobal(final String varName) {
        if (!this.globals.containsKey(varName)) {
            return 0;
        }
        return this.globals.get(varName);
    }
    
    public void setGlobal(final String varName, final int reputation) {
        this.globals.put(varName, reputation);
    }
    
    public void onEntityKilled(final EntityLivingBase entity, final EntityPlayer killer) {
        for (int i = 0; i < this.killCounters.size(); ++i) {
            final KillCounter counter = this.killCounters.get(i);
            if (counter.playerName.equals(killer.getCommandSenderName()) && counter.entityMatchesCounter(entity)) {
                final KillCounter killCounter = counter;
                ++killCounter.killAmmount;
            }
        }
    }
    
    public void addKillCounter(final String name, final String player, final String entity, final String tags) {
        if (this.getKillCounter(name, player) == null) {
            final KillCounter newCounter = new KillCounter(player, name, entity, tags);
            this.killCounters.add(newCounter);
        }
    }
    
    public void removeKillCounter(final String name, final String player) {
        final KillCounter counter = this.getKillCounter(name, player);
        if (counter != null) {
            this.killCounters.remove(counter);
        }
    }
    
    public KillCounter getKillCounter(final String name, final String player) {
        for (final KillCounter killCounter : this.killCounters) {
            if (killCounter.name.equals(name) && killCounter.playerName.equals(player)) {
                return killCounter;
            }
        }
        return null;
    }
    
    public void getCounterNames(final String player, final List<String> list) {
        for (final KillCounter killCounter : this.killCounters) {
            if (killCounter.playerName.equals(player)) {
                list.add(killCounter.name);
            }
        }
    }
    
    public int getKillAmmount(final String name, final String player) {
        for (final KillCounter killCounter : this.killCounters) {
            if (killCounter.name.equals(name) && killCounter.playerName.equals(player)) {
                return killCounter.killAmmount;
            }
        }
        return 0;
    }
    
    public int getPlayerReputation(final String player, final String team) {
        return this.getReputation(player, team).reputation;
    }
    
    public void setReputation(final String player, final String team, final int reputation) {
        final Reputation rep = this.getReputation(player, team);
        rep.reputation = reputation;
    }
    
    public void addReputation(final String player, final String team, final int reputationAddition) {
        final Reputation reputation;
        final Reputation rep = reputation = this.getReputation(player, team);
        reputation.reputation += reputationAddition;
    }
    
    public void addReputation(final EntityPlayer player, final String team, final int reputationAddition) {
        this.addReputation(player.getCommandSenderName(), team, reputationAddition);
        final int total = ReputationManager.instance.getPlayerReputation(player.getCommandSenderName(), team);
        final String repAdditionString = this.colorNumber(reputationAddition);
        final String repTotalString = this.colorNumber(total);
        String message = StatCollector.translateToLocal("strings.reputation");
        message = String.format(team + " " + message, repAdditionString, repTotalString);
        player.addChatMessage((IChatComponent)new ChatComponentText(message));
    }
    
    public Reputation getReputation(final String player, final String team) {
        final Map factionsMap = this.getTeamMap(player, team);
        Object object = factionsMap.get(team);
        if (object == null) {
            object = new Reputation(player, team);
            factionsMap.put(team, object);
        }
        final Reputation reputation = (Reputation)object;
        return reputation;
    }
    
    public String colorNumber(final int number) {
        String color = "+" + BDHelper.StringColor("2");
        if (number < 0) {
            color = BDHelper.StringColor("4");
        }
        color = color.concat(number + BDHelper.StringColor("r"));
        return color;
    }
    
    public Map getTeamMap(final String player, final String team) {
        Map factionsMap = this.reputation.get(player);
        if (factionsMap == null) {
            factionsMap = new HashMap();
            this.reputation.put(player, factionsMap);
        }
        return factionsMap;
    }
    
    @Override
    protected void readFromNBT(final NBTTagCompound tag) {
        NBTTagList list = (NBTTagList)tag.getTag("Reputation");
        if (list != null) {
            for (int i = 0; i < list.tagCount(); ++i) {
                final NBTTagCompound entry = list.getCompoundTagAt(i);
                this.setReputation(entry.getString("Player"), entry.getString("Team"), entry.getInteger("Rep"));
            }
        }
        list = (NBTTagList)tag.getTag("Globals");
        if (list != null) {
            for (int i = 0; i < list.tagCount(); ++i) {
                final NBTTagCompound entry = list.getCompoundTagAt(i);
                final String name = entry.getString("name");
                final int value = entry.getInteger("value");
                this.setGlobal(name, value);
            }
        }
        list = (NBTTagList)tag.getTag("Counters");
        if (list != null) {
            for (int i = 0; i < list.tagCount(); ++i) {
                final NBTTagCompound entry = list.getCompoundTagAt(i);
                final KillCounter counter = new KillCounter();
                counter.readFromNBT(entry);
                this.killCounters.add(counter);
            }
        }
    }
    
    @Override
    protected void writeToNBT(final NBTTagCompound tag) {
        final NBTTagList list = new NBTTagList();
        final Collection<Map.Entry> playersMap = this.reputation.entrySet();
        for (final Map.Entry<String, Map> teamsMap : playersMap) {
            final String playerName = teamsMap.getKey();
            final Collection<Map.Entry> teams = teamsMap.getValue().entrySet();
            for (final Map.Entry<String, Reputation> rep : teams) {
                final String teamName = rep.getKey();
                final NBTTagCompound tagRep = new NBTTagCompound();
                tagRep.setInteger("Rep", rep.getValue().reputation);
                tagRep.setString("Team", teamName);
                tagRep.setString("Player", playerName);
                list.appendTag((NBTBase)tagRep);
            }
        }
        tag.setTag("Reputation", (NBTBase)list);
        final NBTTagList listGlobal = new NBTTagList();
        final Collection<Map.Entry<String, Integer>> globalMap = this.globals.entrySet();
        for (final Map.Entry<String, Integer> global : globalMap) {
            final NBTTagCompound tagGlobal = new NBTTagCompound();
            tagGlobal.setString("name", (String)global.getKey());
            tagGlobal.setInteger("value", (int)global.getValue());
            listGlobal.appendTag((NBTBase)tagGlobal);
        }
        tag.setTag("Globals", (NBTBase)listGlobal);
        final NBTTagList listCounters = new NBTTagList();
        for (final KillCounter counter : this.killCounters) {
            final NBTTagCompound counterTag = new NBTTagCompound();
            counter.writeToNBT(counterTag);
            listCounters.appendTag((NBTBase)counterTag);
        }
        tag.setTag("Counters", (NBTBase)listCounters);
    }
}
