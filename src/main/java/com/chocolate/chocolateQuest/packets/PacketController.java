package com.chocolate.chocolateQuest.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.items.mobControl.ItemController;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketController implements IMessage, IMessageHandler<PacketController, IMessage>
{
    byte type;
    
    public PacketController() {
    }
    
    public PacketController(final byte type) {
        this.type = type;
    }
    
    public IMessage onMessage(final PacketController message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = (EntityPlayer)ctx.getServerHandler().playerEntity;
        message.execute(entityPlayer);
        return null;
    }
    
    public void execute(final EntityPlayer player) {
        final ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null && stack.getItem() instanceof ItemController) {
            switch (this.type) {
                case 0: {
                    ((ItemController)ChocolateQuest.controller).setMode(stack, this.type);
                    break;
                }
                case 1: {
                    ((ItemController)ChocolateQuest.controller).setMode(stack, this.type);
                    break;
                }
                case 2: {
                    ((ItemController)ChocolateQuest.controller).setMode(stack, this.type);
                    break;
                }
                case 3: {
                    if (stack.stackTagCompound != null) {
                        stack.stackTagCompound.removeTag("Entities");
                    }
                    stack.setMetadata(0);
                    break;
                }
            }
        }
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.type = bytes.readByte();
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeByte((int)this.type);
    }
}
