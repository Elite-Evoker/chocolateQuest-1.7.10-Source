package com.chocolate.chocolateQuest.command;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.items.swords.ItemBDSword;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandItemElement extends CommandBase
{
    public String getCommandName() {
        return "CQAddElement";
    }
    
    public String getCommandUsage(final ICommandSender icommandsender) {
        return "/CQAddElement \"element\" \"ammount\" with an blade from the mod in the hand \n Default ma";
    }
    
    public void processCommand(final ICommandSender icommandsender, final String[] astring) {
        int value = 4;
        if (astring.length >= 1) {
            value = Integer.parseInt(astring[1]);
        }
        if (astring.length >= 0) {
            final String element = astring[0];
            final EntityPlayer player = (EntityPlayer)icommandsender;
            final ItemStack is = player.getCurrentEquippedItem();
            if (is != null) {
                if (element.equals("blast")) {
                    ((ItemBDSword)is.getItem()).setBlastDamage(value, is);
                }
                if (element.equals("fire")) {
                    ((ItemBDSword)is.getItem()).setFireDamage(value, is);
                }
                if (element.equals("physic")) {
                    ((ItemBDSword)is.getItem()).setPhysicDamage(value, is);
                }
                if (element.equals("magic")) {
                    ((ItemBDSword)is.getItem()).setMagicDamage(value, is);
                }
                if (element.equals("light")) {
                    ((ItemBDSword)is.getItem()).setElementValue(is, Elements.light, value);
                }
                if (element.equals("dark")) {
                    ((ItemBDSword)is.getItem()).setElementValue(is, Elements.darkness, value);
                }
            }
        }
    }
    
    public int compareTo(final Object arg0) {
        return 0;
    }
    
    public List addTabCompletionOptions(final ICommandSender par1iCommandSender, final String[] parArray) {
        final ArrayList list = new ArrayList();
        list.add("physic");
        list.add("magic");
        list.add("blast");
        list.add("fire");
        return list;
    }
}
