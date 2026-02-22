package com.chocolate.chocolateQuest;

import com.chocolate.chocolateQuest.gui.guiParty.PartyManager;
import com.chocolate.chocolateQuest.quest.worldManager.TerrainManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraft.util.FoodStats;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import com.chocolate.chocolateQuest.packets.PacketShieldBlockFromServer;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSwordDefensive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class EventHandlerCQ
{
    @SubscribeEvent
    public void onLivingJump(final LivingEvent.LivingJumpEvent event) {
        final ItemStack is = event.entityLiving.getEquipmentInSlot(1);
        if (is != null) {
            if (is.getItem() == ChocolateQuest.cloudBoots) {
                final EntityLivingBase entity = event.entityLiving;
                if (!entity.isSneaking()) {
                    final EntityLivingBase entityLivingBase = entity;
                    entityLivingBase.motionY += 0.4;
                }
            }
            if (is.getItem() == ChocolateQuest.slimeBoots) {
                final EntityLivingBase entity = event.entityLiving;
                if (!entity.isSneaking()) {
                    final EntityLivingBase entityLivingBase2 = entity;
                    entityLivingBase2.motionY += 0.1;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingHurt(final LivingHurtEvent event) {
        final Entity hitterEntity = event.entity;
        if (hitterEntity instanceof EntityLivingBase) {
            if (hitterEntity instanceof EntityPlayer) {
                final EntityPlayer ep = (EntityPlayer)hitterEntity;
                if (ep.isBlocking() && !event.source.isUnblockable()) {
                    final ItemStack is = ep.getCurrentEquippedItem();
                    if (is != null && is.getItem() instanceof ItemBaseSwordDefensive) {
                        final Entity sourceEntity = event.source.getEntity();
                        if (sourceEntity != null) {
                            final PacketShieldBlockFromServer packet = new PacketShieldBlockFromServer(ep.getEntityId(), sourceEntity.getEntityId());
                            ChocolateQuest.channel.sendToPlayer((IMessage)packet, (EntityPlayerMP)ep);
                        }
                        final int resistLevel = Awakements.getEnchantLevel(is, Awakements.blockStamina) + 1;
                        final FoodStats food = ep.getFoodStats();
                        if (food.getFoodLevel() > 1) {
                            final float ammount = event.ammount / resistLevel / 2.0f;
                            food.addExhaustion(ammount);
                            event.ammount = 0.0f;
                            event.setCanceled(true);
                        }
                        else {
                            event.ammount /= 2 + resistLevel;
                        }
                    }
                }
            }
            final EntityLivingBase el = (EntityLivingBase)hitterEntity;
            for (int i = 0; i < 4; ++i) {
                final ItemStack armor = el.getEquipmentInSlot(1 + i);
                if (armor != null) {
                    if (armor.getItem() instanceof ItemArmorBase) {
                        ((ItemArmorBase)armor.getItem()).onHit(event, armor, el);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingDeath(final LivingDeathEvent event) {
        if (!event.entity.worldObj.isRemote) {
            if (event.source.getEntity() instanceof EntityPlayer) {
                ReputationManager.instance.onEntityKilled(event.entityLiving, (EntityPlayer)event.source.getEntity());
            }
            if (event.source.getEntity() instanceof EntityHumanBase) {
                final EntityHumanBase human = (EntityHumanBase)event.source.getEntity();
                if (human.getOwner() instanceof EntityPlayer) {
                    ReputationManager.instance.onEntityKilled(event.entityLiving, (EntityPlayer)human.getOwner());
                }
            }
        }
    }
    
    @SubscribeEvent
    public void PlayerInteractEvent(final PlayerInteractEvent event) {
        final PlayerInteractEvent.Action action = event.action;
        final PlayerInteractEvent.Action action2 = event.action;
        if (action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {}
    }
    
    @SubscribeEvent
    public void worldLoad(final WorldEvent.Load event) {
        if (!event.world.isRemote) {
            (ReputationManager.instance = new ReputationManager()).read(event.world, "/data/chocolateQuest/chocolateQuest.dat");
            (TerrainManager.instance = new TerrainManager(ChocolateQuest.config.dungeonSeparation)).read(event.world, "/data/chocolateQuest/terrain.dat");
        }
        else {
            PartyManager.instance.restart();
        }
    }
    
    @SubscribeEvent
    public void worldSave(final WorldEvent.Save event) {
        if (!event.world.isRemote) {
            if (ReputationManager.instance != null) {
                ReputationManager.instance.save(event.world, "/data/chocolateQuest", "chocolateQuest.dat");
            }
            if (TerrainManager.instance != null) {
                TerrainManager.instance.save(event.world, "/data/chocolateQuest", "terrain.dat");
            }
        }
    }
    
    @SubscribeEvent
    public void worldUnload(final WorldEvent.Unload event) {
    }
}
