package com.chocolate.chocolateQuest.quest;

import java.util.List;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionEnchant extends DialogAction
{
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        npc.openEnchantment(player, this.operator, this.value);
    }
    
    @Override
    public byte getType() {
        return 1;
    }
    
    @Override
    public boolean hasName() {
        return false;
    }
    
    @Override
    public boolean hasValue() {
        return true;
    }
    
    @Override
    public String getNameForValue() {
        return "Max level";
    }
    
    @Override
    public String[] getOptionsForOperator() {
        return EnumEnchantType.getNames();
    }
    
    @Override
    public String getNameForOperator() {
        return "Upgrade type";
    }
    
    @Override
    public boolean hasOperator() {
        return true;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("Opens a GUI to enchant items with different enchantments depending on the type");
    }
}
