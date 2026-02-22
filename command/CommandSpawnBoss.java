package com.chocolate.chocolateQuest.command;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantZombie;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandSpawnBoss extends CommandBase
{
    final int NAME_POSITION = 0;
    final int LVL_POSITION = 1;
    final int POS_X_POSITION = 2;
    final int POS_Y_POSITION = 3;
    final int POS_Z_POSITION = 4;
    String[] NAMES;
    String[] NUMBERS_1_TO_10;
    
    public CommandSpawnBoss() {
        this.NAMES = new String[] { "bull", "turtle", "spider", "monking", "slime", "giantZombie" };
        this.NUMBERS_1_TO_10 = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
    }
    
    public String getCommandName() {
        return "CQSpawnBoss";
    }
    
    public String getCommandUsage(final ICommandSender icommandsender) {
        return "/CQSpawnBoss Boss_Name Boss_Size(1-10) positionX position_Y position_Z";
    }
    
    public void processCommand(final ICommandSender icommandsender, final String[] astring) {
        if (astring.length > 0) {
            EntityBaseBoss boss = null;
            final World world = icommandsender.getEntityWorld();
            double lvl = 1.0;
            if (astring.length > 1) {
                lvl = Double.parseDouble(astring[1]);
            }
            final String name = astring[0];
            if (name.equals("monking")) {
                boss = new EntityGiantBoxer(world);
            }
            else if (name.equals("slime")) {
                boss = new EntitySlimeBoss(world);
            }
            else if (name.equals("spider")) {
                boss = new EntitySpiderBoss(world);
            }
            else if (name.equals("turtle")) {
                boss = new EntityTurtle(world);
            }
            else if (name.equals("bull")) {
                boss = new EntityBull(world);
            }
            else if (name.equals("giantZombie")) {
                boss = new EntityGiantZombie(world);
            }
            int posX = icommandsender.getCommandSenderPosition().posX;
            int posY = icommandsender.getCommandSenderPosition().posY;
            int posZ = icommandsender.getCommandSenderPosition().posZ;
            if (astring.length > 2) {
                final String s = astring[2];
                posX = this.getCoordinate(s, posX);
            }
            if (astring.length > 3) {
                final String s = astring[3];
                posY = this.getCoordinate(s, posY);
            }
            if (astring.length > 4) {
                final String s = astring[4];
                posZ = this.getCoordinate(s, posZ);
            }
            if (boss != null) {
                boss.setPosition(posX - 0.5, posY + 0.5, posZ - 0.5);
                boss.setMonsterScale((float)lvl);
                world.spawnEntityInWorld((Entity)boss);
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
        else if (parArray.length - 1 == 1) {
            for (final String s : this.NUMBERS_1_TO_10) {
                list.add(s);
            }
        }
        else if (parArray.length <= 5) {
            list.add("~");
        }
        return list;
    }
    
    public int getCoordinate(String s, int pos) {
        if (s.startsWith("~")) {
            s = s.substring(1);
            if (s.length() == 0) {
                s = "0";
            }
        }
        else {
            pos = 0;
        }
        pos += Integer.parseInt(s);
        return pos;
    }
}
