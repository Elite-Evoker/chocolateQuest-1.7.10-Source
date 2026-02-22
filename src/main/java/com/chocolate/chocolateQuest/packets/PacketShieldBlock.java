package com.chocolate.chocolateQuest.packets;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketShieldBlock implements IMessage, IMessageHandler<PacketShieldBlock, IMessage>
{
    int playerID;
    int targetID;
    
    public PacketShieldBlock() {
    }
    
    public PacketShieldBlock(final int playerID, final int targetID) {
        this.playerID = playerID;
        this.targetID = targetID;
    }
    
    public void fromBytes(final ByteBuf inputStream) {
        this.playerID = inputStream.readInt();
        this.targetID = inputStream.readInt();
    }
    
    public void toBytes(final ByteBuf outputStream) {
        outputStream.writeInt(this.playerID);
        outputStream.writeInt(this.targetID);
    }
    
    public void execute(final World world) {
        final Entity e = world.getEntityByID(this.targetID);
        final Entity e2 = world.getEntityByID(this.playerID);
        System.out.println(e + " " + e2);
        System.out.println((e instanceof EntityLivingBase) + " " + (e2 instanceof EntityPlayer));
        if (e instanceof EntityLivingBase && e2 instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)e2;
            final ItemStack is = player.getCurrentEquippedItem();
            final double damage = 3 + Awakements.getEnchantLevel(is, Awakements.parryDamage) * 2;
            final AttributeModifier attack = new AttributeModifier("parryMod", damage, 0);
            player.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(attack);
            player.attackTargetEntityWithCurrentItem(e);
            player.getEntityAttribute(SharedMonsterAttributes.attackDamage).removeModifier(attack);
        }
    }
    
    public IMessage onMessage(final PacketShieldBlock message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = (EntityPlayer)ctx.getServerHandler().playerEntity;
        message.execute(entityPlayer.worldObj);
        return null;
    }
}
