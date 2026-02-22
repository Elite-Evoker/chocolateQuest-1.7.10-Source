package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.JsonToNBT;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionSetNBT extends DialogAction
{
    public DialogActionSetNBT() {
        this.name = "{}";
    }
    
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        try {
            final NBTBase base = JsonToNBT.func_150315_a(this.name);
            final NBTTagCompound tag = (NBTTagCompound)base;
            npc.loadFromTag(tag);
        }
        catch (final NBTException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public byte getType() {
        return 13;
    }
    
    @Override
    public boolean hasName() {
        return true;
    }
    
    @Override
    public String getNameForName() {
        return "NBTData";
    }
    
    @Override
    public boolean hasValue() {
        return false;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
    }
}
