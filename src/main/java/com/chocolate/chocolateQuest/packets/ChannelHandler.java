package com.chocolate.chocolateQuest.packets;

import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ChannelHandler
{
    public static final SimpleNetworkWrapper INSTANCE;
    protected static int id;
    
    public static void init() {
        ChannelHandler.INSTANCE.registerMessage((Class)PacketEditorGUIClose.class, (Class)PacketEditorGUIClose.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketUpdateHumanDataFromClient.class, (Class)PacketUpdateHumanData.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketUpdateHumanDummyDataFromClient.class, (Class)PacketUpdateHumanDummyData.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketUpdateAwakement.class, (Class)PacketUpdateAwakement.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketUpdateShopRecipeFromClient.class, (Class)PacketUpdateShopRecipe.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketUpdateConversation.class, (Class)PacketUpdateConversation.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketEditConversationFromClient.class, (Class)PacketEditConversation.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketEditNPCFromClient.class, (Class)PacketEditNPC.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketController.class, (Class)PacketController.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketSaveNPCFromClient.class, (Class)PacketSaveNPC.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketPartyMemberAction.class, (Class)PacketPartyMemberAction.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketPartyCreation.class, (Class)PacketPartyCreation.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketShieldBlock.class, (Class)PacketShieldBlock.class, ChannelHandler.id++, Side.SERVER);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketEntityAnimation.class, (Class)PacketEntityAnimation.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketAttackToXYZ.class, (Class)PacketAttackToXYZ.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketSpawnParticlesAround.class, (Class)PacketSpawnParticlesAround.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketHookImpact.class, (Class)PacketHookImpact.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketUpdateHumanDataFromServer.class, (Class)PacketUpdateHumanData.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketUpdateHumanDummyDataFromServer.class, (Class)PacketUpdateHumanDummyData.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketUpdateShopRecipeFromServer.class, (Class)PacketUpdateShopRecipe.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketStartConversation.class, (Class)PacketStartConversation.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketEditConversation.class, (Class)PacketEditConversation.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketEditNPCFromServer.class, (Class)PacketEditNPC.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketSaveNPCFromServer.class, (Class)PacketSaveNPC.class, ChannelHandler.id++, Side.CLIENT);
        ChannelHandler.INSTANCE.registerMessage((Class)PacketShieldBlockFromServer.class, (Class)PacketShieldBlockFromServer.class, ChannelHandler.id++, Side.CLIENT);
    }
    
    public void sendPaquetToServer(final IMessage packet) {
        ChannelHandler.INSTANCE.sendToServer(packet);
    }
    
    public void sendToAllAround(final Entity entity, final IMessage packet) {
        this.sendToAllAround(entity, packet, 32);
    }
    
    public void sendToAllAround(final Entity entity, final IMessage packet, final int range) {
        this.sendToAllAround(packet, new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, (double)range));
    }
    
    public void sendToAllAround(final IMessage packet, final NetworkRegistry.TargetPoint point) {
        ChannelHandler.INSTANCE.sendToAllAround(packet, point);
    }
    
    public void sendToPlayer(final IMessage packet, final EntityPlayerMP player) {
        ChannelHandler.INSTANCE.sendTo(packet, player);
    }
    
    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("chocolateQuest".toLowerCase());
        ChannelHandler.id = 0;
    }
}
