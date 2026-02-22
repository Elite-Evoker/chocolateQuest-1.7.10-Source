package com.chocolate.chocolateQuest.entity.npc;

import java.util.ArrayList;
import net.minecraft.entity.EnumCreatureAttribute;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.misc.EnumRace;
import com.chocolate.chocolateQuest.packets.PacketBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTBase;
import java.util.Iterator;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import com.chocolate.chocolateQuest.packets.PacketEditConversation;
import com.chocolate.chocolateQuest.packets.PacketStartConversation;
import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayerMP;
import com.chocolate.chocolateQuest.packets.ChannelHandler;
import com.chocolate.chocolateQuest.packets.PacketUpdateShopRecipe;
import net.minecraft.world.WorldServer;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanData;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDummyData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import com.chocolate.chocolateQuest.entity.ai.HumanSelector;
import net.minecraft.entity.monster.IMob;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import java.util.List;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import com.chocolate.chocolateQuest.quest.DialogOption;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import com.chocolate.chocolateQuest.gui.guinpc.ShopRecipe;
import net.minecraft.entity.INpc;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class EntityHumanNPC extends EntityHumanBase implements INpc
{
    ShopRecipe[] trades;
    public String texture;
    public boolean hasPlayerTexture;
    public ResourceLocation textureLocationPlayer;
    public int modelType;
    public String name;
    public String displayName;
    public float size;
    public int color;
    public boolean isInvincible;
    public int voice;
    public NBTTagCompound npcVariables;
    public DialogOption conversation;
    DialogOption currentDialog;
    public boolean canTeleport;
    public int repToAttack;
    public int repOnDeath;
    public boolean targetMobs;
    EntityAINearestAttackableTarget aiTargetMonster;
    public int followTime;
    List<TimeCounter> timers;
    
    public EntityHumanNPC(final World world) {
        super(world);
        this.trades = new ShopRecipe[] { new ShopRecipe(new ItemStack(Items.emerald), new ItemStack[] { new ItemStack(Items.apple) }) };
        this.texture = "pirate.png";
        this.hasPlayerTexture = false;
        this.textureLocationPlayer = null;
        this.name = "unnamed";
        this.displayName = "Unknown";
        this.size = 0.5f;
        this.color = 16777215;
        this.isInvincible = false;
        this.conversation = new DialogOption(null, "helloWorld");
        this.canTeleport = false;
        this.repToAttack = 0;
        this.repOnDeath = 10;
        this.targetMobs = true;
        this.followTime = 0;
        this.timers = null;
        if (world != null && !world.isRemote) {
            this.npcVariables = new NBTTagCompound();
        }
        this.shouldDespawn = false;
        this.AIMode = EnumAiState.FOLLOW.ordinal();
        this.AICombatMode = EnumAiCombat.DEFENSIVE.ordinal();
    }
    
    @Override
    public void setAIForCurrentMode() {
        super.setAIForCurrentMode();
        this.setTargetsMobs();
    }
    
    public void setTargetsMobs() {
        if (this.targetMobs && this.aiTargetMonster == null) {
            this.aiTargetMonster = new EntityAINearestAttackableTarget((EntityCreature)this, (Class)IMob.class, 0, true, false, (IEntitySelector)new HumanSelector(this));
            this.targetTasks.addTask(4, (EntityAIBase)this.aiTargetMonster);
        }
        else if (!this.targetMobs && this.aiTargetMonster != null) {
            this.targetTasks.removeTask((EntityAIBase)this.aiTargetMonster);
            this.aiTargetMonster = null;
        }
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.followTime > 0) {
            if (this.followTime == 1) {
                this.setOwner(null);
            }
            --this.followTime;
        }
        if (this.ticksExisted % 2000 == 0) {
            this.heal(1.0f);
        }
        if (this.timers != null) {
            for (int i = 0; i < this.timers.size(); ++i) {
                this.timers.get(i).update();
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float damage) {
        return !this.isInvincible && super.attackEntityFrom(damagesource, damage);
    }
    
    @Override
    protected boolean interact(final EntityPlayer player) {
        if (this.playerSpeakingTo == null) {
            if (this.entityTeam.startsWith("mob") && !player.capabilities.isCreativeMode) {
                return false;
            }
            if (!this.worldObj.isRemote) {
                final String team = this.entityTeam.replace("npc_", "");
                boolean allied = team.equals("npc") || ReputationManager.instance.getPlayerReputation(player.getCommandSenderName(), team) >= this.repToAttack;
                if (!allied && player.getTeam() != null) {
                    allied = this.isOnAlliedTeam(player.getTeam());
                }
                if (player.capabilities.isCreativeMode || allied) {
                    this.setAttackTarget(null);
                    this.openConversation(this.playerSpeakingTo = player);
                }
            }
        }
        return true;
    }
    
    @Override
    public IMessage getEntityGUIUpdatePacket(final EntityPlayer player) {
        if (player.capabilities.isCreativeMode) {
            return (IMessage)new PacketUpdateHumanDummyData(this);
        }
        return (IMessage)new PacketUpdateHumanData(this);
    }
    
    public void openShop(final EntityPlayer player) {
        if (this.worldObj instanceof WorldServer) {
            final IMessage packet = (IMessage)new PacketUpdateShopRecipe(this, -1);
            ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
            player.openGui((Object)ChocolateQuest.instance, 5, this.worldObj, this.getEntityId(), 0, 0);
        }
    }
    
    public void openEnchantment(final EntityPlayer player, final int type, final int level) {
        player.openGui((Object)ChocolateQuest.instance, 7, this.worldObj, this.getEntityId(), type, level);
    }
    
    public void editNPC(final EntityPlayer player) {
        if (player.capabilities.isCreativeMode) {
            final IMessage packet = (IMessage)new PacketEditNPC(this);
            ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
        }
    }
    
    public void openConversation(final EntityPlayer player) {
        this.conversation.setID(0);
        final IMessage packet = (IMessage)new PacketStartConversation(this, new int[0], player);
        ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
    }
    
    public void editConversation(final EntityPlayer player) {
        if (player.capabilities.isCreativeMode) {
            final IMessage packet = (IMessage)new PacketEditConversation(this, new int[0]);
            ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
        }
    }
    
    public void updateConversation(final EntityPlayer player, final int[] selectedStep) {
        final DialogOption option = this.getDialogOption(selectedStep);
        option.execute(player, this);
        final IMessage packet = (IMessage)new PacketStartConversation(this, selectedStep, player);
        ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
    }
    
    public void endConversation() {
        this.playerSpeakingTo = null;
    }
    
    public DialogOption getDialogOption(final int[] selectedStep) {
        DialogOption option = this.conversation;
        for (int i = 0; i < selectedStep.length; ++i) {
            option = option.options[selectedStep[i]];
        }
        return option;
    }
    
    public int executeCommand(String command) {
        if (this.playerSpeakingTo != null) {
            command = command.replace("@sp", this.playerSpeakingTo.getCommandSenderName());
        }
        int returnValue = 0;
        final MinecraftServer minecraftserver = MinecraftServer.getServer();
        if (minecraftserver != null && minecraftserver.isCommandBlockEnabled()) {
            final ICommandManager icommandmanager = minecraftserver.getCommandManager();
            returnValue = icommandmanager.executeCommand((ICommandSender)new npcLogic(this), command);
        }
        return returnValue;
    }
    
    public ShopRecipe[] getRecipes() {
        return this.trades;
    }
    
    public void setRecipes(int i, final ShopRecipe recipe) {
        if (this.trades == null) {
            this.trades = new ShopRecipe[1];
        }
        if (i >= this.trades.length) {
            i = this.trades.length;
            final ShopRecipe[] newTrades = new ShopRecipe[i + 1];
            for (int t = 0; t < newTrades.length - 1; ++t) {
                newTrades[t] = this.trades[t];
            }
            this.trades = newTrades;
        }
        this.trades[i] = recipe;
    }
    
    public void setRecipes(final ShopRecipe[] recipes) {
        this.trades = recipes;
    }
    
    public String getCommandSenderName() {
        return this.displayName;
    }
    
    public void loadFromTag(final NBTTagCompound tag) {
        final double x = this.posX;
        final double y = this.posY;
        final double z = this.posZ;
        final NBTTagCompound tagOriginal = new NBTTagCompound();
        this.writeEntityToNBT(tagOriginal);
        for (final String s : tag.getKeySet()) {
            final NBTBase nbtbase = tag.getTag(s);
            tagOriginal.setTag(s, nbtbase);
        }
        this.readFromNBT(tagOriginal);
        this.setPosition(x, y, z);
    }
    
    public void updateStats(final NBTTagCompound tag) {
        if (tag.hasKey("CustomNameVisible")) {
            this.setAlwaysRenderNameTag(tag.getBoolean("CustomNameVisible"));
        }
        if (tag.hasKey("CanPickUpLoot")) {
            this.setCanPickUpLoot(tag.getBoolean("CanPickUpLoot"));
        }
        if (tag.hasKey("nameID")) {
            this.name = tag.getString("nameID");
        }
        if (tag.hasKey("displayName")) {
            this.displayName = tag.getString("displayName");
        }
        if (tag.hasKey("texture")) {
            this.texture = tag.getString("texture");
        }
        if (tag.hasKey("model")) {
            this.modelType = tag.getInteger("model");
        }
        if (tag.hasKey("sizeModifier")) {
            this.size = tag.getFloat("sizeModifier");
            this.resize();
        }
        if (tag.hasKey("colorMod")) {
            this.color = tag.getInteger("colorMod");
        }
        if (tag.hasKey("playerTexture")) {
            this.hasPlayerTexture = tag.getBoolean("playerTexture");
        }
        if (tag.hasKey("gender")) {
            this.isMale = tag.getBoolean("gender");
        }
        if (tag.hasKey("team")) {
            this.entityTeam = tag.getString("team");
        }
        if (tag.hasKey("isInvincible")) {
            this.isInvincible = tag.getBoolean("isInvincible");
        }
        if (tag.hasKey("targetMobs")) {
            this.targetMobs = tag.getBoolean("targetMobs");
        }
        if (tag.hasKey("teleport")) {
            this.canTeleport = tag.getBoolean("teleport");
        }
        if (tag.hasKey("voice")) {
            this.voice = tag.getInteger("voice");
        }
        if (tag.hasKey("repOnKill")) {
            this.repOnDeath = tag.getInteger("repOnKill");
        }
        if (tag.hasKey("repFriendly")) {
            this.repToAttack = tag.getInteger("repFriendly");
        }
        if (tag.hasKey("health")) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)tag.getInteger("health"));
        }
        if (tag.hasKey("speed")) {
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(tag.getInteger("speed") * 0.001);
        }
        if (tag.hasKey("homeX") && tag.hasKey("homeY") && tag.hasKey("homeZ")) {
            final int x = tag.getInteger("homeX");
            final int y = tag.getInteger("homeY");
            final int z = tag.getInteger("homeZ");
            this.setHomeArea(x, y, z, -1);
        }
        if (tag.hasKey("homeDist")) {
            final int dist = tag.getInteger("homeDist");
            this.setHomeArea(this.getHomePosition().posX, this.getHomePosition().posY, this.getHomePosition().posZ, dist);
        }
    }
    
    public void writeStats(final NBTTagCompound tag, final boolean writeAll) {
        tag.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
        tag.setBoolean("CanPickUpLoot", this.canPickUpLoot());
        tag.setString("nameID", this.name);
        tag.setString("displayName", this.displayName);
        tag.setBoolean("gender", this.isMale);
        tag.setBoolean("playerTexture", this.hasPlayerTexture);
        tag.setInteger("model", this.modelType);
        tag.setString("texture", this.texture);
        tag.setFloat("sizeModifier", this.size);
        tag.setInteger("colorMod", this.color);
        tag.setBoolean("isInvincible", this.isInvincible);
        tag.setBoolean("targetMobs", this.targetMobs);
        tag.setBoolean("teleport", this.canTeleport);
        tag.setInteger("voice", this.voice);
        tag.setInteger("repFriendly", this.repToAttack);
        tag.setInteger("repOnKill", this.repOnDeath);
        if (writeAll) {
            tag.setInteger("homeX", this.getHomePosition().posX);
            tag.setInteger("homeY", this.getHomePosition().posY);
            tag.setInteger("homeZ", this.getHomePosition().posZ);
            tag.setInteger("homeDist", (int)this.getHomeDistance());
            tag.setString("team", this.entityTeam);
            tag.setInteger("health", (int)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue());
            tag.setInteger("speed", (int)(this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() * 1000.0));
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        this.writeShop(nbttagcompound, false);
        if (this.conversation != null) {
            final NBTTagCompound optionTag = new NBTTagCompound();
            this.conversation.writeToNBT(optionTag);
            nbttagcompound.setTag("Conversation", (NBTBase)optionTag);
        }
        this.writeStats(nbttagcompound, false);
        if (this.npcVariables != null) {
            nbttagcompound.setTag("Variables", (NBTBase)this.npcVariables);
        }
        this.setTargetsMobs();
        nbttagcompound.setBoolean("inventoryLocked", this.inventoryLocked);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.readShop(nbttagcompound, false);
        if (nbttagcompound.hasKey("Conversation")) {
            (this.conversation = new DialogOption()).readFromNBT((NBTTagCompound)nbttagcompound.getTag("Conversation"));
        }
        this.updateStats(nbttagcompound);
        if (nbttagcompound.hasKey("Variables")) {
            this.npcVariables = (NBTTagCompound)nbttagcompound.getTag("Variables");
        }
        if (this.timers != null) {
            final NBTTagList list = new NBTTagList();
            for (TimeCounter counter : this.timers) {}
        }
        this.inventoryLocked = nbttagcompound.getBoolean("inventoryLocked");
    }
    
    @Override
    public void writeEntityToSpawnerNBT(final NBTTagCompound nbttagcompound, final int spawnerX, final int spawnerY, final int spawnerZ) {
        super.writeEntityToSpawnerNBT(nbttagcompound, spawnerX, spawnerY, spawnerZ);
        this.writeShop(nbttagcompound, true);
    }
    
    @Override
    public void readEntityFromSpawnerNBT(final NBTTagCompound nbttagcompound, final int spawnerX, final int spawnerY, final int spawnerZ) {
        super.readEntityFromSpawnerNBT(nbttagcompound, spawnerX, spawnerY, spawnerZ);
        this.readShop(nbttagcompound, true);
    }
    
    public void writeShop(final NBTTagCompound nbttagcompound, final boolean addMappings) {
        if (this.trades != null) {
            final NBTTagList list = new NBTTagList();
            for (int i = 0; i < this.trades.length; ++i) {
                if (this.trades[i] != null) {
                    final NBTTagCompound tag = new NBTTagCompound();
                    if (addMappings) {
                        this.trades[i].writeToNBTWithMapping(tag);
                    }
                    else {
                        this.trades[i].writeToNBT(tag);
                    }
                    list.appendTag((NBTBase)tag);
                }
            }
            nbttagcompound.setTag("trades", (NBTBase)list);
        }
    }
    
    public void readShop(final NBTTagCompound tag, final boolean addMapings) {
        if (tag.hasKey("trades")) {
            final NBTTagList list = (NBTTagList)tag.getTag("trades");
            this.trades = new ShopRecipe[list.tagCount()];
            for (int i = 0; i < list.tagCount(); ++i) {
                final ShopRecipe recipe = new ShopRecipe(list.getCompoundTagAt(i), addMapings);
                this.trades[i] = recipe;
            }
        }
    }
    
    @Override
    public void writeSpawnData(final ByteBuf buffer) {
        super.writeSpawnData(buffer);
        buffer.writeInt(this.modelType);
        buffer.writeFloat(this.size);
        buffer.writeInt(this.color);
        buffer.writeBoolean(this.isMale);
        buffer.writeBoolean(this.hasPlayerTexture);
        buffer.writeInt(this.texture.length());
        for (int i = 0; i < this.texture.length(); ++i) {
            buffer.writeChar((int)this.texture.charAt(i));
        }
        PacketBase.writeString(buffer, this.displayName);
        PacketBase.writeString(buffer, this.entityTeam);
    }
    
    @Override
    public void readSpawnData(final ByteBuf additionalData) {
        super.readSpawnData(additionalData);
        this.modelType = additionalData.readInt();
        this.size = additionalData.readFloat();
        this.color = additionalData.readInt();
        this.isMale = additionalData.readBoolean();
        this.hasPlayerTexture = additionalData.readBoolean();
        final int texLength = additionalData.readInt();
        this.texture = "";
        for (int i = 0; i < texLength; ++i) {
            this.texture += additionalData.readChar();
        }
        this.displayName = PacketBase.readString(additionalData);
        this.entityTeam = PacketBase.readString(additionalData);
        this.resize();
    }
    
    public void resize() {
        float sizeBase = 1.8f;
        if (this.modelType == EnumRace.DWARF.ordinal()) {
            sizeBase = 1.4f;
        }
        this.height = sizeBase * (0.5f + this.size);
        this.width = 0.6f * (0.5f + this.size);
    }
    
    @Override
    public boolean isSuitableTargetAlly(final EntityLivingBase entity) {
        if (!(entity instanceof EntityPlayer)) {
            return super.isSuitableTargetAlly(entity);
        }
        if (this.entityTeam.equals("npc")) {
            return true;
        }
        if (this.entityTeam.startsWith("mob_")) {
            return false;
        }
        if (entity.getTeam() != null && !this.isOnAlliedTeam(entity.getTeam())) {
            return false;
        }
        final String teamName = this.entityTeam.replace("npc_", "");
        return ReputationManager.instance.getPlayerReputation(entity.getCommandSenderName(), teamName) >= this.repToAttack;
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (this.repOnDeath > 0 && damageSource.getSourceOfDamage() instanceof EntityPlayer && !this.entityTeam.equals("npc") && !this.entityTeam.startsWith("mob")) {
            final EntityPlayer player = (EntityPlayer)damageSource.getSourceOfDamage();
            if (!player.capabilities.isCreativeMode) {
                final String team = this.entityTeam.replace("npc_", "");
                ReputationManager.instance.addReputation(player, team, -this.repOnDeath);
            }
        }
        super.onDeath(damageSource);
    }
    
    public boolean canBePushed() {
        return !this.isInvincible;
    }
    
    @Override
    public float getSizeModifier() {
        return 1.0f;
    }
    
    @Override
    public boolean canTeleport() {
        return this.canTeleport;
    }
    
    @Override
    public int getInteligence() {
        return 2;
    }
    
    protected String getLivingSound() {
        return EnumVoice.getVoice(this.voice).say;
    }
    
    protected String getHurtSound() {
        return EnumVoice.getVoice(this.voice).hurt;
    }
    
    protected String getDeathSound() {
        return EnumVoice.getVoice(this.voice).death;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        if (this.modelType == EnumRace.SKELETON.ordinal() || this.modelType == EnumRace.SPECTER.ordinal()) {
            return EnumCreatureAttribute.UNDEAD;
        }
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    public int getTimeCounter(final String name) {
        if (this.timers == null) {
            return 0;
        }
        for (final TimeCounter counter : this.timers) {
            if (counter.name.equals(name)) {
                return counter.time;
            }
        }
        return 0;
    }
    
    public void setTimeCounter(final String name, final int value) {
        if (this.timers == null) {
            this.timers = new ArrayList<TimeCounter>();
        }
        for (final TimeCounter counter : this.timers) {
            if (counter.name.equals(name)) {
                counter.time = value;
                return;
            }
        }
        this.timers.add(new TimeCounter(name, value));
    }
}
