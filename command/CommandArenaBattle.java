package com.chocolate.chocolateQuest.command;

import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSpecter;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.misc.EquipementHelper;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.entity.EntityReferee;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import java.util.ArrayList;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandArenaBattle extends CommandBase
{
    final int LVL_POSITION = 0;
    final int PLAYER_POSITION = 1;
    String[] NUMBERS_1_TO_10;
    final int TRAINING = 0;
    final int SMALL_BULL = 1;
    
    public CommandArenaBattle() {
        this.NUMBERS_1_TO_10 = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
    }
    
    public String getCommandName() {
        return "CQGenerateArenaBattle";
    }
    
    public String getCommandUsage(final ICommandSender icommandsender) {
        return "/CQGenerateArenaBattle Battle_level player1 player2...";
    }
    
    public void processCommand(final ICommandSender icommandsender, final String[] astring) {
        if (astring.length > 0) {
            final World world = icommandsender.getEntityWorld();
            int lvl = 1;
            if (astring.length > 0) {
                lvl = Integer.parseInt(astring[0]);
            }
            final List<EntityLivingBase> playersList = new ArrayList<EntityLivingBase>();
            if (astring.length > 1) {
                for (int i = 1; i < astring.length; ++i) {
                    final EntityPlayer player = world.getPlayerEntityByName(astring[i]);
                    if (player != null) {
                        playersList.add((EntityLivingBase)player);
                    }
                }
            }
            if (playersList.isEmpty()) {
                final EntityPlayer player2 = world.getPlayerEntityByName(icommandsender.getCommandSenderName());
                if (player2 != null) {
                    playersList.add((EntityLivingBase)player2);
                }
            }
            final int posX = icommandsender.getCommandSenderPosition().posX;
            final int posY = icommandsender.getCommandSenderPosition().posY;
            final int posZ = icommandsender.getCommandSenderPosition().posZ;
            int playerCount = 0;
            for (final EntityLivingBase e : playersList) {
                ++playerCount;
                final EntityPlayer player3 = (EntityPlayer)e;
                player3.capabilities.isCreativeMode = false;
                player3.sendPlayerAbilities();
                player3.setHealth(player3.getMaxHealth());
                player3.getFoodStats().setFoodLevel(20);
                this.addPlayerEquipement(player3, lvl);
            }
            final List<EntityLivingBase> mobList = this.getMobs(world, lvl, posX, posY, posZ, playerCount);
            for (final EntityLivingBase e2 : mobList) {
                world.spawnEntityInWorld((Entity)e2);
                ((EntityLiving)e2).setAttackTarget((EntityLivingBase)playersList.get(world.rand.nextInt(playersList.size())));
            }
            final EntityReferee referee = new EntityReferee(world, playersList, mobList);
            if (referee != null) {
                referee.setPosition(posX - 0.5, posY + 0.5, posZ - 0.5);
                world.spawnEntityInWorld((Entity)referee);
            }
        }
    }
    
    public List<EntityLivingBase> getMobs(final World world, final int lvl, final int posX, final int posY, final int posZ, final int playerCount) {
        final List<EntityLivingBase> mobList = new ArrayList<EntityLivingBase>();
        final int DIST = 15;
        final int MOB_LVL = 1;
        if (lvl == 1) {
            final EntityBull bull = new EntityBull(world);
            bull.setMonsterScale(1.0f);
            bull.setPosition((double)posX, (double)posY, (double)(posZ + 15));
            mobList.add((EntityLivingBase)bull);
        }
        else {
            final EntityHumanSkeleton skeleton = new EntityHumanSkeleton(world);
            skeleton.setPosition((double)(posX + 15), (double)posY, (double)posZ);
            EquipementHelper.equipHumanRandomly(skeleton, 1, 2);
            mobList.add((EntityLivingBase)skeleton);
            final EntityHumanSpecter specter = new EntityHumanSpecter(world);
            specter.setPosition((double)posX, (double)posY, (double)(posZ + 15));
            EquipementHelper.equipHumanRandomly(specter, 1, 8);
            mobList.add((EntityLivingBase)specter);
            final EntityHumanZombie zombie = new EntityHumanZombie(world);
            zombie.setPosition((double)(posX - 15), (double)posY, (double)posZ);
            EquipementHelper.equipHumanRandomly(zombie, 1, 3);
            mobList.add((EntityLivingBase)zombie);
        }
        return mobList;
    }
    
    public void addPlayerEquipement(final EntityPlayer player, final int lvl) {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            player.inventory.setInventorySlotContents(i, (ItemStack)null);
        }
        EquipementHelper.equipEntity((EntityLivingBase)player, 1);
        player.inventory.setInventorySlotContents(0, new ItemStack(ChocolateQuest.diamondSwordAndShield));
        player.inventory.setInventorySlotContents(1, new ItemStack(ChocolateQuest.diamondDagger));
        player.inventory.setInventorySlotContents(2, new ItemStack(ChocolateQuest.diamondSpear));
        player.inventory.setInventorySlotContents(3, new ItemStack(ChocolateQuest.diamondBigsword));
        player.inventory.setInventorySlotContents(8, new ItemStack(ChocolateQuest.potion, 64, 0));
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
        final String last = parArray[parArray.length - 1];
        final ArrayList list = new ArrayList();
        if (parArray.length - 1 == 0) {
            for (final String s : this.NUMBERS_1_TO_10) {
                list.add(s);
            }
        }
        else {
            for (final Object object : par1iCommandSender.getEntityWorld().playerEntities) {
                final EntityPlayer p = (EntityPlayer)object;
                if (p.getCommandSenderName().startsWith(last)) {
                    list.add(p.getCommandSenderName());
                }
            }
        }
        return list;
    }
}
