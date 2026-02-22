package com.chocolate.chocolateQuest.items.mobControl;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.Vec4I;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemTeamEditor extends ItemPathMarker
{
    @Override
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        if (stack.stackTagCompound != null && entity instanceof EntityHumanBase) {
            final EntityHumanBase human = (EntityHumanBase)entity;
            human.standingPosition = new Vec4I(stack.stackTagCompound.getInteger("x0"), stack.stackTagCompound.getInteger("y0"), stack.stackTagCompound.getInteger("z0"), stack.stackTagCompound.getInteger("rot0"));
            if (!player.worldObj.isRemote) {
                player.addChatMessage((IChatComponent)new ChatComponentText("Assigned ward position for " + BDHelper.StringColor("2") + entity.getCommandSenderName()));
            }
        }
        return true;
    }
    
    @Override
    public int getMaxPoints(final ItemStack is) {
        return 1;
    }
}
