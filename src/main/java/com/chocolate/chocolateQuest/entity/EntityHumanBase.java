package com.chocolate.chocolateQuest.entity;

import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import io.netty.buffer.ByteBuf;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import java.util.Iterator;
import com.chocolate.chocolateQuest.entity.ai.AIAnimalMountedByEntity;
import net.minecraft.entity.passive.EntityAnimal;
import com.chocolate.chocolateQuest.utils.MobTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.entity.item.EntityItem;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanData;
import net.minecraft.entity.player.EntityPlayerMP;
import com.chocolate.chocolateQuest.packets.ChannelHandler;
import com.chocolate.chocolateQuest.items.mobControl.ItemController;
import net.minecraft.init.Items;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import net.minecraft.entity.monster.EntityCreeper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.magic.ElementsHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackAggressiveBackstab;
import com.chocolate.chocolateQuest.entity.ai.AIHumanFlee;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackEvasive;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackDefensive;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackAggressive;
import net.minecraft.entity.ai.EntityAIWander;
import com.chocolate.chocolateQuest.entity.ai.AIControlledSit;
import com.chocolate.chocolateQuest.entity.ai.AIControlledWardPosition;
import com.chocolate.chocolateQuest.entity.ai.AIControlledPath;
import com.chocolate.chocolateQuest.entity.ai.AIControlledFormation;
import com.chocolate.chocolateQuest.entity.ai.AIControlledFollowOwner;
import com.chocolate.chocolateQuest.entity.ai.AITargetNearestHurtAlly;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackHeal;
import com.chocolate.chocolateQuest.entity.handHelper.HandEmpty;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import com.chocolate.chocolateQuest.entity.ai.HumanSelector;
import com.chocolate.chocolateQuest.entity.ai.AITargetOwner;
import com.chocolate.chocolateQuest.entity.ai.AITargetParty;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import com.chocolate.chocolateQuest.entity.ai.AIHumanReturnHome;
import com.chocolate.chocolateQuest.entity.ai.AIHumanIdleSit;
import com.chocolate.chocolateQuest.entity.ai.AIHumanIdleTalkClosest;
import com.chocolate.chocolateQuest.entity.ai.AIHumanMount;
import com.chocolate.chocolateQuest.entity.ai.AIHumanGoToPoint;
import com.chocolate.chocolateQuest.entity.ai.AIHumanPotion;
import com.chocolate.chocolateQuest.entity.ai.AISpeakToPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.SharedMonsterAttributes;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import com.chocolate.chocolateQuest.utils.Vec4I;
import net.minecraft.entity.ai.EntityAIBase;
import com.chocolate.chocolateQuest.entity.ai.EntityParty;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.magic.IElementWeak;
import net.minecraft.entity.IEntityOwnable;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.EntityCreature;

public class EntityHumanBase extends EntityCreature implements IEntityAdditionalSpawnData, IEntityOwnable, IElementWeak
{
    public static final byte ANIM = 16;
    public static final byte HUMAN = 0;
    public static final byte ORC = 1;
    public static final byte GOBLIN = 2;
    public static final byte DWARF = 3;
    public static final byte TRITON = 4;
    public int maxStamina;
    protected float randomHeightVariation;
    public boolean isMale;
    public int leftHandSwing;
    public float moveForwardHuman;
    protected int sprintTime;
    protected int exhaustion;
    public int potionCount;
    public ItemStack leftHandItem;
    public float leftHandDropChances;
    public short parryRate;
    public short blockRate;
    protected final float shiedBlockDefense = 0.8f;
    public float accuracy;
    public String entityTeam;
    public int shieldID;
    protected String ownerName;
    private EntityLivingBase owner;
    public EntityParty party;
    public int partyPositionAngle;
    public int partyDistanceToLeader;
    public boolean partyPositionPersistance;
    public boolean addedToParty;
    protected EntityAIBase attackAI;
    protected EntityAIBase controlledAI;
    protected EntityAIBase supportAI;
    protected EntityAIBase supportAITarget;
    public int AIMode;
    public int AICombatMode;
    public Vec4I currentPos;
    public Vec4I standingPosition;
    public Vec4I[] path;
    public HandHelper leftHand;
    public HandHelper rightHand;
    private static final AttributeModifier slowDownModifier;
    public EntityPlayer playerSpeakingTo;
    public boolean updateOwner;
    protected boolean shouldDespawn;
    public boolean inventoryLocked;
    public EntityLivingBase entityToMount;
    protected int physicDefense;
    protected int magicDefense;
    protected int blastDefense;
    protected int fireDefense;
    protected int projectileDefense;
    
    public EntityHumanBase(final World world) {
        super(world);
        this.maxStamina = 60;
        this.isMale = true;
        this.leftHandSwing = 0;
        this.moveForwardHuman = 0.0f;
        this.potionCount = 1;
        this.leftHandDropChances = 0.0f;
        this.parryRate = 0;
        this.blockRate = 0;
        this.entityTeam = "npc";
        this.shieldID = 0;
        this.partyDistanceToLeader = 2;
        this.partyPositionPersistance = false;
        this.addedToParty = false;
        this.AIMode = EnumAiState.FORMATION.ordinal();
        this.AICombatMode = EnumAiCombat.OFFENSIVE.ordinal();
        this.updateOwner = false;
        this.shouldDespawn = true;
        this.inventoryLocked = false;
        this.physicDefense = 0;
        this.magicDefense = 0;
        this.blastDefense = 0;
        this.fireDefense = 0;
        this.projectileDefense = 0;
        this.addAITasks();
        this.stepHeight = 1.0f;
        this.updateHands();
        for (int i = 0; i < this.equipmentDropChances.length; ++i) {
            this.equipmentDropChances[i] = 0.0f;
        }
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
        this.setSize(0.6f, 1.8f);
        this.randomHeightVariation = 1.0f + (this.rand.nextFloat() - 0.6f) / 5.0f;
    }
    
    protected boolean isAIEnabled() {
        return true;
    }
    
    protected void addAITasks() {
        this.getNavigator().setEnterDoors(true);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new AISpeakToPlayer(this));
        this.tasks.addTask(1, (EntityAIBase)new AIHumanPotion(this));
        this.tasks.addTask(1, (EntityAIBase)new AIHumanGoToPoint(this));
        this.tasks.addTask(1, (EntityAIBase)new AIHumanMount(this, 1.0f, false));
        this.setAIForCurrentMode();
        this.tasks.addTask(7, (EntityAIBase)new AIHumanIdleTalkClosest(this, EntityHumanBase.class, 8.0f));
        this.tasks.addTask(8, (EntityAIBase)new AIHumanIdleSit(this));
        this.tasks.addTask(6, (EntityAIBase)new AIHumanReturnHome(this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.targetTasks.addTask(2, (EntityAIBase)new AITargetParty(this));
        this.targetTasks.addTask(3, (EntityAIBase)new AITargetOwner(this));
        this.targetTasks.addTask(4, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true, false, (IEntitySelector)new HumanSelector(this)));
        this.targetTasks.addTask(4, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityHumanBase.class, 0, true, false, (IEntitySelector)new HumanSelector(this)));
    }
    
    public void updateHands() {
        this.rightHand = HandHelper.getHandHelperForItem(this, this.getEquipmentInSlot(0));
        this.leftHand = HandHelper.getHandHelperForItem(this, this.getLeftHandItem());
        if (this.rightHand instanceof HandEmpty) {
            this.rightHand = new HandHelper(this, null);
        }
    }
    
    public void setAIForCurrentMode() {
        this.updateHands();
        if (this.supportAI != null) {
            this.tasks.removeTask(this.supportAI);
            this.targetTasks.removeTask(this.supportAITarget);
        }
        if (this.isHealer()) {
            this.supportAI = new AIHumanAttackHeal(this, 1.0f, false);
            this.tasks.addTask(2, this.supportAI);
            this.supportAITarget = (EntityAIBase)new AITargetNearestHurtAlly(this, EntityLivingBase.class);
            this.targetTasks.addTask(0, this.supportAITarget);
        }
        if (this.controlledAI != null) {
            this.tasks.removeTask(this.controlledAI);
        }
        int priority = 4;
        if (this.AIMode == EnumAiState.FOLLOW.ordinal()) {
            this.controlledAI = new AIControlledFollowOwner(this, 5.0f, 50.0f);
        }
        else if (this.AIMode == EnumAiState.FORMATION.ordinal()) {
            this.controlledAI = new AIControlledFormation(this);
        }
        else if (this.AIMode == EnumAiState.PATH.ordinal()) {
            this.controlledAI = new AIControlledPath(this);
        }
        else if (this.AIMode == EnumAiState.WARD.ordinal()) {
            this.controlledAI = new AIControlledWardPosition(this);
        }
        else if (this.AIMode == EnumAiState.SIT.ordinal()) {
            this.controlledAI = new AIControlledSit(this);
            priority = 2;
        }
        else if (this.AIMode == EnumAiState.WANDER.ordinal()) {
            this.controlledAI = (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0);
        }
        if (this.controlledAI != null) {
            this.tasks.addTask(priority, this.controlledAI);
        }
        if (this.attackAI != null) {
            this.tasks.removeTask(this.attackAI);
        }
        if (this.AICombatMode == EnumAiCombat.OFFENSIVE.ordinal()) {
            this.attackAI = new AIHumanAttackAggressive(this, EntityLivingBase.class, 1.0f, false);
        }
        else if (this.AICombatMode == EnumAiCombat.DEFENSIVE.ordinal()) {
            this.attackAI = new AIHumanAttackDefensive(this, 1.0f);
        }
        else if (this.AICombatMode == EnumAiCombat.EVASIVE.ordinal()) {
            this.attackAI = new AIHumanAttackEvasive(this, 1.0f);
        }
        else if (this.AICombatMode == EnumAiCombat.FLEE.ordinal()) {
            this.attackAI = new AIHumanFlee(this, 1.0f);
        }
        else if (this.AICombatMode == EnumAiCombat.BACKSTAB.ordinal()) {
            this.attackAI = new AIHumanAttackAggressiveBackstab(this, 1.0f, false);
        }
        if (this.attackAI != null) {
            this.tasks.addTask(3, this.attackAI);
        }
    }
    
    public void onLivingUpdate() {
        if (this.party != null && this.party.getLeader() == this) {
            this.party.update();
        }
        if (this.haveShied()) {
            final IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            attributeinstance.removeModifier(EntityHumanBase.slowDownModifier);
            if (this.isDefending()) {
                attributeinstance.applyModifier(EntityHumanBase.slowDownModifier);
            }
        }
        if (!this.worldObj.isRemote) {
            if (this.isSprinting()) {
                --this.sprintTime;
                if (this.sprintTime <= 0) {
                    this.setSprinting(false);
                }
                this.exhaustion = 20;
            }
            else if (this.exhaustion > 0) {
                --this.exhaustion;
            }
        }
        for (int i = 0; i < 4; ++i) {
            final ItemStack is = this.getEquipmentInSlot(i + 1);
            if (is != null && is.getItem() instanceof ItemArmorBase) {
                ((ItemArmorBase)is.getItem()).onUpdateEquiped(is, this.worldObj, (EntityLivingBase)this);
            }
        }
        this.rightHand.onUpdate();
        this.leftHand.onUpdate();
        this.updateArmSwingProgress();
        super.onLivingUpdate();
        if (this.isSitting() && this.ticksExisted % 200 == 0) {
            this.heal(1.0f);
        }
    }
    
    protected void updateArmSwingProgress() {
        super.updateArmSwingProgress();
        if (this.leftHandSwing > 0) {
            --this.leftHandSwing;
        }
    }
    
    public void swingLeftHand() {
        if (this.leftHandSwing <= 0) {
            this.leftHandSwing = 10;
            if (this.worldObj instanceof WorldServer) {
                final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)1);
                ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
            }
        }
    }
    
    public void swingHand(final HandHelper hand) {
        if (hand == this.rightHand) {
            this.swingItem();
        }
        else {
            this.swingLeftHand();
        }
    }
    
    public boolean isSwingInProgress(final HandHelper hand) {
        if (hand == this.rightHand) {
            return this.isSwingInProgress;
        }
        return this.leftHandSwing > 0;
    }
    
    public void setAiming(final HandHelper hand, final boolean aiming) {
        if (hand.isAiming() != aiming) {
            if (hand == this.rightHand) {
                this.toogleAimRight();
            }
            else {
                this.toogleAimLeft();
            }
        }
    }
    
    public boolean isAiming() {
        return this.rightHand.isAiming() || this.leftHand.isAiming();
    }
    
    public void stopAiming() {
        if (this.rightHand.isAiming()) {
            this.toogleAimRight();
        }
        if (this.leftHand.isAiming()) {
            this.toogleAimLeft();
        }
    }
    
    public void toogleAimRight() {
        this.rightHand.setAiming(!this.rightHand.isAiming());
        if (this.worldObj instanceof WorldServer) {
            final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)2);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
        }
    }
    
    public void toogleAimLeft() {
        this.leftHand.setAiming(!this.leftHand.isAiming());
        if (this.worldObj instanceof WorldServer) {
            final PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)3);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
        }
    }
    
    public boolean writeToNBTOptional(final NBTTagCompound nbttagcompound) {
        return (this.party == null || this.writePartyToNBT(nbttagcompound)) && super.writeToNBTOptional(nbttagcompound);
    }
    
    public boolean writePartyToNBT(final NBTTagCompound nbttagcompound) {
        if (this.party != null && this.party.getLeader().isEntityEqual((Entity)this)) {
            this.party.saveToNBT(nbttagcompound);
            return true;
        }
        return false;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("PotionCount", this.potionCount);
        if (this.getLeftHandItem() != null) {
            final NBTTagCompound itemNBT = new NBTTagCompound();
            nbttagcompound.setTag("leftHand", (NBTBase)this.getLeftHandItem().writeToNBT(itemNBT));
        }
        nbttagcompound.setFloat("dropLeft", this.leftHandDropChances);
        if (this.owner instanceof EntityPlayer) {
            nbttagcompound.setString("ownerName", this.ownerName);
        }
        else if (this.ownerName != null) {
            nbttagcompound.setString("ownerName", this.ownerName);
        }
        nbttagcompound.setString("team", this.entityTeam);
        if (this.partyPositionPersistance) {
            nbttagcompound.setInteger("leaderDist", this.partyDistanceToLeader);
            nbttagcompound.setInteger("partyPos", this.partyPositionAngle);
        }
        nbttagcompound.setInteger("AIMode", this.AIMode);
        if (this.standingPosition != null) {
            nbttagcompound.setBoolean("standing", true);
            nbttagcompound.setInteger("standX", this.standingPosition.xCoord);
            nbttagcompound.setInteger("standY", this.standingPosition.yCoord);
            nbttagcompound.setInteger("standZ", this.standingPosition.zCoord);
            nbttagcompound.setInteger("standRotation", this.standingPosition.rot);
        }
        if (this.path != null) {
            nbttagcompound.setInteger("pathPoints", this.path.length);
            for (int p = 0; p < this.path.length; ++p) {
                nbttagcompound.setInteger("pathX" + p, this.path[p].xCoord);
                nbttagcompound.setInteger("pathY" + p, this.path[p].yCoord);
                nbttagcompound.setInteger("pathZ" + p, this.path[p].zCoord);
            }
        }
        nbttagcompound.setInteger("AIMode", this.AIMode);
        nbttagcompound.setInteger("AICombat", this.AICombatMode);
        nbttagcompound.setBoolean("addedToParty", this.addedToParty);
        nbttagcompound.setBoolean("Despawn", this.shouldDespawn);
        final ChunkCoordinates coords = this.getHomePosition();
        nbttagcompound.setInteger("homeX", coords.posX);
        nbttagcompound.setInteger("homeY", coords.posY);
        nbttagcompound.setInteger("homeZ", coords.posZ);
        nbttagcompound.setInteger("homeDist", (int)this.getHomeDistance());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        if (nbttagcompound.hasKey("PotionCount")) {
            this.potionCount = nbttagcompound.getInteger("PotionCount");
        }
        if (nbttagcompound.hasKey("dropLeft")) {
            this.leftHandDropChances = nbttagcompound.getFloat("dropLeft");
        }
        if (nbttagcompound.hasKey("leftHand")) {
            final NBTTagCompound item = (NBTTagCompound)nbttagcompound.getTag("leftHand");
            if (item != null) {
                this.leftHandItem = ItemStack.loadItemStackFromNBT(item);
            }
            else {
                this.leftHandItem = null;
            }
        }
        if (nbttagcompound.hasKey("team")) {
            this.entityTeam = nbttagcompound.getString("team");
        }
        if (nbttagcompound.hasKey("ownerName")) {
            this.ownerName = nbttagcompound.getString("ownerName");
            if (this.ownerName == "") {
                this.ownerName = null;
            }
            if (this.ownerName != null) {
                this.setOwner((EntityLivingBase)this.worldObj.getPlayerEntityByName(this.ownerName));
            }
        }
        if (nbttagcompound.hasKey("leaderDist")) {
            this.partyPositionPersistance = true;
            this.partyDistanceToLeader = nbttagcompound.getInteger("leaderDist");
        }
        if (nbttagcompound.hasKey("partyPos")) {
            this.partyPositionPersistance = true;
            this.partyPositionAngle = nbttagcompound.getInteger("partyPos");
        }
        if (nbttagcompound.hasKey("standing") && nbttagcompound.getBoolean("standing")) {
            final int x = nbttagcompound.getInteger("standX");
            final int y = nbttagcompound.getInteger("standY");
            final int z = nbttagcompound.getInteger("standZ");
            final int rotY = nbttagcompound.getInteger("standRotation");
            this.standingPosition = new Vec4I(x, y, z, rotY);
        }
        if (nbttagcompound.hasKey("pathPoints")) {
            final int points = nbttagcompound.getInteger("pathPoints");
            if (points > 0) {
                this.path = new Vec4I[points];
                for (int p = 0; p < points; ++p) {
                    this.path[p] = new Vec4I(nbttagcompound.getInteger("pathX" + p), nbttagcompound.getInteger("pathY" + p), nbttagcompound.getInteger("pathZ" + p), 0);
                }
            }
        }
        if (nbttagcompound.hasKey("AIMode")) {
            this.AIMode = nbttagcompound.getInteger("AIMode");
        }
        if (nbttagcompound.hasKey("AICombat")) {
            this.AICombatMode = nbttagcompound.getInteger("AICombat");
        }
        this.setAIForCurrentMode();
        if (nbttagcompound.hasKey("Party") && nbttagcompound.getTag("Party") != null && this.party == null) {
            (this.party = new EntityParty()).tryToAddNewMember(this);
            this.party.readFromNBT(nbttagcompound);
        }
        if (nbttagcompound.hasKey("addedToParty")) {
            this.addedToParty = nbttagcompound.getBoolean("addedToParty");
        }
        if (nbttagcompound.hasKey("Despawn")) {
            this.shouldDespawn = nbttagcompound.getBoolean("Despawn");
        }
        final int x = nbttagcompound.getInteger("homeX");
        final int y = nbttagcompound.getInteger("homeY");
        final int z = nbttagcompound.getInteger("homeZ");
        int f = -1;
        if (nbttagcompound.hasKey("homeDist")) {
            f = nbttagcompound.getInteger("homeDist");
        }
        this.setHomeArea(x, y, z, f);
    }
    
    public void writeEntityToSpawnerNBT(final NBTTagCompound nbttagcompound, final int spawnerX, final int spawnerY, final int spawnerZ) {
        this.writePartyToNBT(nbttagcompound);
        final Vec4I currentStand = this.standingPosition;
        final Vec4I[] currentPath = this.path;
        final double tempPosX = this.posX;
        final double tempPosY = this.posY;
        final double tempPosZ = this.posZ;
        this.posX -= spawnerX;
        this.posY -= spawnerY;
        this.posZ -= spawnerZ;
        if (this.standingPosition != null) {
            this.standingPosition = new Vec4I(currentStand.xCoord - spawnerX, currentStand.yCoord - spawnerY, currentStand.zCoord - spawnerZ, currentStand.rot);
        }
        if (this.path != null) {
            this.path = new Vec4I[this.path.length];
            for (int p = 0; p < this.path.length; ++p) {
                this.path[p] = new Vec4I(currentPath[p].xCoord - spawnerX, currentPath[p].yCoord - spawnerY, currentPath[p].zCoord - spawnerZ, currentPath[p].rot);
            }
        }
        this.writeEntityToNBT(nbttagcompound);
        if (this.ridingEntity != null) {
            this.writeMountToNBT(nbttagcompound);
        }
        final ChunkCoordinates coords = this.getHomePosition();
        nbttagcompound.setInteger("homeX", coords.posX - spawnerX);
        nbttagcompound.setInteger("homeY", coords.posY - spawnerY);
        nbttagcompound.setInteger("homeZ", coords.posZ - spawnerZ);
        nbttagcompound.setInteger("homeDist", (int)this.getHomeDistance());
        this.posX += spawnerX;
        this.posY += spawnerY;
        this.posZ += spawnerZ;
        this.standingPosition = currentStand;
        this.path = currentPath;
        this.saveMappingsToNBT(nbttagcompound);
    }
    
    public void saveMappingsToNBT(final NBTTagCompound nbttagcompound) {
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < 5; ++i) {
            String s = "";
            if (this.getEquipmentInSlot(i) != null) {
                s = Item.itemRegistry.getNameForObject((Object)this.getEquipmentInSlot(i).getItem());
            }
            list.appendTag((NBTBase)new NBTTagString(s));
        }
        String s2 = "";
        if (this.getLeftHandItem() != null) {
            s2 = Item.itemRegistry.getNameForObject((Object)this.getLeftHandItem().getItem());
        }
        list.appendTag((NBTBase)new NBTTagString(s2));
        nbttagcompound.setTag("EquipementMap", (NBTBase)list);
    }
    
    public void readEntityFromSpawnerNBT(final NBTTagCompound nbttagcompound, final int spawnerX, final int spawnerY, final int spawnerZ) {
        final NBTBase equipementMap = nbttagcompound.getTag("EquipementMap");
        if (equipementMap != null) {
            final NBTBase equipementTag = nbttagcompound.getTag("Equipment");
            if (equipementTag != null) {
                final NBTTagList list = (NBTTagList)equipementTag;
                final NBTTagList listMap = (NBTTagList)equipementMap;
                for (int i = 0; i < list.tagCount(); ++i) {
                    final int id = list.getCompoundTagAt(i).getShort("id");
                    if (id != 0) {
                        final Item item = (Item)Item.itemRegistry.getObject(listMap.getStringTagAt(i));
                        if (item != null) {
                            final short newID = (short)Item.getIdFromItem(item);
                            list.getCompoundTagAt(i).setShort("id", newID);
                        }
                    }
                }
                final int LEFT_HAND = 5;
                final NBTTagCompound itemNBT = (NBTTagCompound)nbttagcompound.getTag("leftHand");
                if (itemNBT != null) {
                    final int id2 = itemNBT.getShort("id");
                    if (id2 != 0) {
                        final Item item2 = (Item)Item.itemRegistry.getObject(listMap.getStringTagAt(5));
                        if (item2 != null) {
                            final short newID2 = (short)Item.getIdFromItem(item2);
                            itemNBT.setShort("id", newID2);
                        }
                    }
                }
            }
        }
        this.readFromNBT(nbttagcompound);
        if (this.standingPosition != null) {
            final Vec4I current = this.standingPosition;
            this.standingPosition = new Vec4I(current.xCoord + spawnerX, current.yCoord + spawnerY, current.zCoord + spawnerZ, current.rot);
        }
        if (this.path != null) {
            for (int p = 0; p < this.path.length; ++p) {
                final Vec4I current2 = this.path[p];
                this.path[p] = new Vec4I(current2.xCoord + spawnerX, current2.yCoord + spawnerY, current2.zCoord + spawnerZ, current2.rot);
            }
        }
        final int x = nbttagcompound.getInteger("homeX") + spawnerX;
        final int y = nbttagcompound.getInteger("homeY") + spawnerY;
        final int z = nbttagcompound.getInteger("homeZ") + spawnerZ;
        final int f = nbttagcompound.getInteger("homeDist");
        this.setHomeArea(x, y, z, f);
        this.posX += spawnerX;
        this.posY += spawnerY;
        this.posZ += spawnerZ;
        this.shouldDespawn = false;
    }
    
    public void setAttackTarget(final EntityLivingBase par1EntityLivingBase) {
        if (par1EntityLivingBase != null) {
            if (!this.isOnSameTeam(par1EntityLivingBase)) {
                if (this.party != null) {
                    this.party.addAggroToTarget(par1EntityLivingBase, 1);
                }
            }
            else if (!this.isHealer() && !this.isSuitableMount((Entity)par1EntityLivingBase)) {
                return;
            }
        }
        super.setAttackTarget(par1EntityLivingBase);
    }
    
    protected boolean canDespawn() {
        return this.shouldDespawn;
    }
    
    protected void despawnEntity() {
        super.despawnEntity();
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    public double getMountedYOffset() {
        if (this.ridingEntity instanceof EntityHorse) {
            return -0.4;
        }
        return super.getMountedYOffset();
    }
    
    public ItemStack getLeftHandItem() {
        return this.leftHandItem;
    }
    
    public void setLeftHandItem(final ItemStack is) {
        this.leftHandItem = is;
    }
    
    public void moveEntityWithHeading(final float par1, float par2) {
        if (this.moveForwardHuman != 0.0f) {
            par2 = this.moveForwardHuman;
        }
        super.moveEntityWithHeading(par1, par2);
    }
    
    public float getSizeModifier() {
        return this.randomHeightVariation;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, float damage) {
        boolean rangedAttack = damagesource.isProjectile();
        if (!rangedAttack && damagesource.getEntity() instanceof EntityLivingBase) {
            final double d = this.getDistanceSqToEntity(damagesource.getEntity());
            if (d > 36.0) {
                rangedAttack = true;
                damagesource.setProjectile();
            }
        }
        damage = ElementsHelper.getAmmountDecreased(this, damage, damagesource);
        if (this.isDefending()) {
            double angle = 0.0;
            if (damagesource.getEntity() != null) {
                for (angle = damagesource.getEntity().rotationYaw - this.rotationYaw; angle > 360.0; angle -= 360.0) {}
                while (angle < 0.0) {
                    angle += 360.0;
                }
                angle = Math.abs(angle - 180.0);
            }
            if (!damagesource.isUnblockable() && angle < 120.0) {
                if (this.hurtTime == 0) {
                    this.worldObj.playSoundAtEntity((Entity)this, "mob.blaze.hit", 1.0f, 1.0f);
                }
                if (damagesource.isProjectile()) {
                    return true;
                }
                if (this.rand.nextInt(100) < this.blockRate) {
                    if (this.rand.nextInt(100) < this.parryRate && !damagesource.isProjectile() && !damagesource.isExplosion() && damagesource.getEntity() instanceof EntityLivingBase && !rangedAttack) {
                        this.attackEntityAsMob(damagesource.getEntity());
                        this.swingLeftHand();
                    }
                    this.hurtTime = 10;
                    return false;
                }
                damage *= 0.8f;
            }
        }
        else if (this.haveShied()) {
            this.toogleBlocking();
        }
        if (damagesource.getEntity() instanceof EntityLivingBase) {
            final EntityLivingBase entity = (EntityLivingBase)damagesource.getEntity();
            if (!this.isOnSameTeam(entity) && !this.worldObj.isRemote) {
                if (this.getAttackTarget() != null && this.getDistanceToEntity((Entity)this.getAttackTarget()) > this.getDistanceToEntity((Entity)entity)) {
                    this.setAttackTarget(entity);
                }
                if (this.party != null) {
                    this.party.addAggroToTarget(entity, (int)damage);
                }
            }
            if (this.isAiming() && this.canAimBeCanceled() && !damagesource.isProjectile()) {
                this.attackTime = this.getAttackSpeed();
                this.stopAiming();
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(EntityHumanBase.slowDownModifier);
            }
        }
        return super.attackEntityFrom(damagesource, damage);
    }
    
    public void applyEntityCollision(final Entity par1Entity) {
        if (!this.worldObj.isRemote && this.getLeader() != null && par1Entity instanceof EntityHumanBase && !((EntityHumanBase)par1Entity).isOnSameTeam((EntityLivingBase)this)) {
            this.setAttackTarget((EntityLivingBase)par1Entity);
        }
        super.applyEntityCollision(par1Entity);
    }
    
    public boolean attackEntityWithRangedAttack(final Entity entity, final float f) {
        boolean flagRight = false;
        boolean flagLeft = false;
        if (this.leftHand.isRanged()) {
            flagLeft = this.leftHand.attackWithRange(entity, f);
        }
        if (this.rightHand.isRanged()) {
            flagRight = this.rightHand.attackWithRange(entity, f);
        }
        return flagLeft || flagRight;
    }
    
    public void attackEntity(final EntityLivingBase entity) {
        if (entity.hurtTime <= 0) {
            if (this.rightHand.attackTime <= 0) {
                this.rightHand.attackEntity((Entity)entity);
            }
            else if (this.leftHand.attackTime <= 0) {
                this.leftHand.attackEntity((Entity)entity);
            }
            if (this.haveShied() && this.isDefending()) {
                this.setDefending(false);
            }
        }
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        return this.attackEntityAsMob(entity, this.getEquipmentInSlot(0));
    }
    
    public boolean attackEntityAsMob(final Entity entity, final ItemStack weapon) {
        float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        if (weapon != this.getEquipmentInSlot(0)) {
            damage = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue();
            damage += (float)BDHelper.getWeaponDamage(weapon);
        }
        if (entity instanceof EntityCreeper) {
            damage += 1000.0f;
        }
        float knockBackAmount = 0.0f;
        if (weapon != null) {
            if (weapon.getItem() instanceof ItemBaseDagger) {
                final double d = this.posX - entity.posX;
                final double d2 = this.posY - entity.posY;
                final double d3 = this.posZ - entity.posZ;
                float angle;
                for (angle = (float)Math.atan2(d, d3), angle = this.rotationYaw - entity.rotationYaw; angle > 360.0f; angle -= 360.0f) {}
                while (angle < 0.0f) {
                    angle += 360.0f;
                }
                angle = Math.abs(angle - 180.0f);
                if (Math.abs(angle) > 130.0f) {
                    damage *= 2.5f;
                    if (!this.worldObj.isRemote) {
                        final PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround((byte)1, entity.posX, entity.posY + 1.0, entity.posZ);
                        ChocolateQuest.channel.sendToAllAround(entity, (IMessage)packet, 64);
                    }
                }
            }
            if (weapon.getItem() instanceof ItemBaseSpear) {
                knockBackAmount = 1.3f;
                if (entity.ridingEntity != null && this.rand.nextInt(10) == 0) {
                    entity.mountEntity((Entity)null);
                }
            }
        }
        final boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), damage);
        if (flag && weapon != null) {
            final int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, weapon);
            if (j > 0) {
                entity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * j * 0.5f), 0.1, (double)(MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * j * 0.5f));
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
            }
            final int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, weapon);
            if (k > 0) {
                entity.setFire(k * 4);
            }
        }
        if (knockBackAmount > 0.0f) {
            entity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * knockBackAmount * 0.5f), 0.1, (double)(MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * knockBackAmount * 0.5f));
        }
        return flag;
    }
    
    public boolean canAttackClass(final Class par1Class) {
        return EntityGhast.class != par1Class && par1Class != EntityReferee.class;
    }
    
    public float getEyeHeight() {
        if (this.isSitting()) {
            return super.getEyeHeight() - 0.5f;
        }
        return super.getEyeHeight();
    }
    
    public double getDistanceToAttack() {
        return Math.max(this.rightHand.getDistanceToStopAdvancing(), this.leftHand.getDistanceToStopAdvancing());
    }
    
    protected boolean interact(final EntityPlayer player) {
        if (player.getCurrentEquippedItem() != null) {
            final ItemStack is = player.getCurrentEquippedItem();
            if (is.getItem() == Items.name_tag || is.getItem() instanceof ItemController) {
                return super.interact(player);
            }
        }
        if (this.playerSpeakingTo == null) {
            this.openInventory(this.playerSpeakingTo = player);
            return true;
        }
        return false;
    }
    
    public void openInventory(final EntityPlayer player) {
        if (this.worldObj instanceof WorldServer) {
            this.updateOwner = (this.getOwner() == this.playerSpeakingTo);
            final IMessage packet = this.getEntityGUIUpdatePacket(player);
            ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
        }
        if (!this.worldObj.isRemote) {
            player.openGui((Object)"chocolateQuest", 0, this.worldObj, this.getEntityId(), 0, 0);
        }
    }
    
    public IMessage getEntityGUIUpdatePacket(final EntityPlayer player) {
        return (IMessage)new PacketUpdateHumanData(this);
    }
    
    public float getEquipmentDropChances(final int i) {
        return this.equipmentDropChances[i];
    }
    
    public void setEquipmentDropChances(final int i, final float value) {
        this.equipmentDropChances[i] = value;
    }
    
    public boolean isWithinHomeDistance(final int x, final int y, final int z) {
        if (this.ownerName != null) {
            return true;
        }
        if (this.standingPosition != null) {
            final float maximumHomeDistance = this.getHomeDistance();
            return maximumHomeDistance == -1.0f || this.getDistanceSq((double)this.standingPosition.xCoord, (double)this.standingPosition.yCoord, (double)this.standingPosition.zCoord) < maximumHomeDistance * maximumHomeDistance;
        }
        return super.isWithinHomeDistance(x, y, z);
    }
    
    public float getHomeDistance() {
        return this.getMaximumHomeDistance();
    }
    
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (this.party != null) {
            this.party.removeMember(this);
        }
        if (this.getLeftHandItem() != null && !this.worldObj.isRemote && this.leftHandDropChances > 0.0f && this.rand.nextFloat() <= this.leftHandDropChances) {
            final ItemStack is = this.getLeftHandItem();
            final EntityItem eItem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, is);
            this.worldObj.spawnEntityInWorld((Entity)eItem);
        }
    }
    
    public void onSpawn() {
        this.updateHands();
    }
    
    public int getHandAngle(final HandHelper hand) {
        if (hand == this.leftHand) {
            return -30;
        }
        return 30;
    }
    
    public boolean canSee(final EntityLivingBase entity) {
        boolean flag = true;
        if (flag) {
            if (entity.ridingEntity != null || entity.isSprinting()) {
                flag = true;
            }
            else {
                final double rotDiff = BDHelper.getAngleBetweenEntities((Entity)this, (Entity)entity);
                final double entityRot = MathHelper.wrapAngleTo180_double((double)this.rotationYawHead);
                double rot = MathHelper.wrapAngleTo180_double(entityRot - rotDiff);
                rot = Math.abs(rot);
                final float lightLevel = entity.worldObj.getLightBrightness(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));
                final double dist = entity.getDistanceSqToEntity((Entity)this) / 16.0;
                final double value = (rot + dist + (entity.isSneaking() ? 40 : 0)) * (2.2 - lightLevel);
                flag = (value < 130.0);
            }
        }
        return flag;
    }
    
    public boolean isSuitableTargetAlly(final EntityLivingBase entity) {
        if (entity == null) {
            return false;
        }
        if (this.isOnAlliedTeam(entity.getTeam())) {
            return true;
        }
        if (this.isSuitableMount((Entity)entity)) {
            if (entity.riddenByEntity == null) {
                return true;
            }
            if (entity.riddenByEntity instanceof EntityLivingBase) {
                return this.isOnSameTeam((EntityLivingBase)entity.riddenByEntity);
            }
        }
        return false;
    }
    
    public boolean canTeleport() {
        return false;
    }
    
    public int getInteligence() {
        return 4;
    }
    
    public void startSprinting() {
        if (!this.isSprinting() && this.exhaustion <= 0) {
            this.setSprinting(true);
            this.sprintTime = this.maxStamina;
        }
    }
    
    public boolean canSprint() {
        return true;
    }
    
    public boolean canBlock() {
        return this.haveShied();
    }
    
    public boolean haveShied() {
        return this.leftHand.canBlock();
    }
    
    public void toogleBlocking() {
        if (this.haveShied()) {
            this.setDefending(!this.isDefending());
        }
        else {
            this.setDefending(false);
        }
    }
    
    public boolean canAimBeCanceled() {
        return false;
    }
    
    public boolean isHealer() {
        return this.rightHand.isHealer() || this.leftHand.isHealer();
    }
    
    public boolean isRanged() {
        return this.rightHand.isRanged() || this.leftHand.isRanged();
    }
    
    public boolean isTwoHanded() {
        return this.rightHand.isTwoHanded();
    }
    
    public void setCaptain(final boolean captain) {
        this.setAnimFlag(0, captain);
    }
    
    public boolean isCaptain() {
        return this.getAnimFlag(0);
    }
    
    public boolean isDefending() {
        return this.getAnimFlag(2);
    }
    
    public void setDefending(final boolean flag) {
        if (this.haveShied()) {
            this.setAnimFlag(2, flag);
        }
    }
    
    public boolean isSitting() {
        return this.getAnimFlag(3);
    }
    
    public void setSitting(final boolean flag) {
        this.setAnimFlag(3, flag);
    }
    
    public boolean isSpeaking() {
        return this.getAnimFlag(4);
    }
    
    public void setSpeaking(final boolean flag) {
        this.setAnimFlag(4, flag);
    }
    
    public boolean isEating() {
        return this.getAnimFlag(5);
    }
    
    public void setEating(final boolean flag) {
        this.setAnimFlag(5, flag);
    }
    
    public boolean isSneaking() {
        return this.getAnimFlag(6);
    }
    
    public void setSneaking(final boolean flag) {
        this.setAnimFlag(6, flag);
    }
    
    protected boolean getAnimFlag(final int index) {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1 << index) != 0x0;
    }
    
    protected void setAnimFlag(final int index, final boolean result) {
        final byte b = this.dataWatcher.getWatchableObjectByte(16);
        if (result) {
            this.dataWatcher.updateObject(16, (Object)(byte)(b | 1 << index));
        }
        else {
            this.dataWatcher.updateObject(16, (Object)(byte)(b & ~(1 << index)));
        }
    }
    
    public double getAttackRangeBonus() {
        return this.rightHand.getAttackRangeBonus();
    }
    
    public double getMaxRangeForAttack() {
        return this.rightHand.getAttackRangeBonus();
    }
    
    public EntityLivingBase getOwner() {
        if (this.owner == null && this.ownerName != null) {
            this.setOwner((EntityLivingBase)this.worldObj.getPlayerEntityByName(this.ownerName));
        }
        return this.owner;
    }
    
    public void setOwner(final EntityLivingBase entity) {
        this.setOwner(entity, false);
    }
    
    public void setOwner(final EntityLivingBase entity, final boolean removePlayerOwnage) {
        if (entity instanceof EntityHumanBase) {
            final EntityHumanBase human = (EntityHumanBase)entity;
            if (human.getOwner() == this) {
                return;
            }
        }
        else if (entity instanceof EntityPlayer) {
            this.ownerName = ((EntityPlayer)entity).getCommandSenderName();
        }
        else if (entity == null) {
            this.ownerName = null;
        }
        this.owner = entity;
    }
    
    public EntityLivingBase getLeader() {
        if (this.party != null) {
            if (this.party.getLeader() != this) {
                return (EntityLivingBase)this.party.getLeader();
            }
        }
        return this.owner;
    }
    
    public boolean tryPutIntoPArty(final EntityHumanBase newMember) {
        if (this.party == null) {
            (this.party = new EntityParty()).tryToAddNewMember(this);
        }
        return this.party.tryToAddNewMember(newMember);
    }
    
    public void setInParty(final EntityParty party, final int angle, final int dist) {
        this.party = party;
        if (!this.partyPositionPersistance) {
            this.partyPositionAngle = angle;
            this.partyDistanceToLeader = dist;
            if (this.isCaptain()) {
                this.AICombatMode = EnumAiCombat.OFFENSIVE.ordinal();
            }
            if (this.isRanged() || this.isHealer()) {
                if (this.isRanged()) {
                    this.partyDistanceToLeader += 2;
                }
                this.AICombatMode = EnumAiCombat.EVASIVE.ordinal();
            }
            else if (this.haveShied() || (angle < 45 && angle > -45)) {
                this.AICombatMode = EnumAiCombat.DEFENSIVE.ordinal();
            }
            this.setAIForCurrentMode();
        }
    }
    
    public void setOutOfParty() {
        this.party = null;
    }
    
    public Team getTeam() {
        final EntityLivingBase entitylivingbase = this.getOwner();
        if (entitylivingbase instanceof EntityPlayer && entitylivingbase.getTeam() != null) {
            return entitylivingbase.getTeam();
        }
        return new MobTeam(this.entityTeam);
    }
    
    public boolean isOnAlliedTeam(final Team team) {
        if (this.getTeam() != null && team != null) {
            if (this.getTeam().isSameTeam(team)) {
                return true;
            }
            final String ownerTeamName = this.getTeam().getRegisteredName();
            final String entityTeamName = team.getRegisteredName();
            if (entityTeamName.startsWith("mob") || ownerTeamName.startsWith("mob")) {
                return false;
            }
            if (entityTeamName.startsWith("npc") || ownerTeamName.startsWith("npc")) {
                return true;
            }
        }
        return false;
    }
    
    public int getAttackSpeed() {
        return 20;
    }
    
    public int getLeadershipValue() {
        return 0;
    }
    
    public int getTeamID() {
        return this.shieldID;
    }
    
    public ItemStack getDiamondArmorForSlot(final int slot) {
        switch (slot) {
            case 3: {
                return new ItemStack((Item)Items.diamond_chestplate);
            }
            case 2: {
                return new ItemStack((Item)Items.diamond_leggings);
            }
            case 1: {
                return new ItemStack((Item)Items.diamond_boots);
            }
            default: {
                return new ItemStack((Item)Items.diamond_helmet);
            }
        }
    }
    
    public ItemStack getIronArmorForSlot(final int slot) {
        switch (slot) {
            case 3: {
                return new ItemStack((Item)Items.iron_chestplate);
            }
            case 2: {
                return new ItemStack((Item)Items.iron_leggings);
            }
            case 1: {
                return new ItemStack((Item)Items.iron_boots);
            }
            default: {
                return new ItemStack((Item)Items.iron_helmet);
            }
        }
    }
    
    public void colorArmor(final ItemStack is, final int color) {
        final NBTTagCompound tag = (is.stackTagCompound == null) ? new NBTTagCompound() : is.stackTagCompound;
        final NBTTagCompound tagDisplay = new NBTTagCompound();
        tagDisplay.setInteger("color", color);
        tag.setTag("display", (NBTBase)tagDisplay);
        is.stackTagCompound = tag;
    }
    
    public boolean shouldRenderCape() {
        return false;
    }
    
    public ItemStack getHeldItem() {
        if (this.isEating()) {
            return new ItemStack(ChocolateQuest.potion);
        }
        if (this.rightHand != null) {
            return this.rightHand.getItem();
        }
        return super.getHeldItem();
    }
    
    public ItemStack getHeldItemLeft() {
        if (this.leftHand != null) {
            return this.leftHand.getItem();
        }
        return super.getHeldItem();
    }
    
    public void mountEntity(final Entity e) {
        super.mountEntity(e);
        if (this.ridingEntity != null && this.isSuitableMount(this.ridingEntity) && e instanceof EntityAnimal) {
            final EntityLiving entityTarget = (EntityLiving)this.ridingEntity;
            boolean hasRiddenTask = false;
            for (final Object task : entityTarget.tasks.taskEntries) {
                if (task instanceof AIAnimalMountedByEntity) {
                    hasRiddenTask = true;
                }
            }
            if (!hasRiddenTask) {
                entityTarget.tasks.addTask(1, (EntityAIBase)new AIAnimalMountedByEntity(entityTarget, 1.0f));
            }
        }
    }
    
    public boolean isSuitableMount(final Entity entity) {
        return entity instanceof EntityHorse || (entity instanceof EntityGolemMecha && this.isOnSameTeam((EntityLivingBase)entity));
    }
    
    protected Entity getMount() {
        return (Entity)new EntityHorse(this.worldObj);
    }
    
    public void setMountAI() {
        if (this.ridingEntity != null && this.isSuitableMount(this.ridingEntity) && this.ridingEntity instanceof EntityHorse) {
            final EntityHorse horse = (EntityHorse)this.ridingEntity;
            horse.setTamedBy((EntityPlayer)new DummyChocolate(this.worldObj));
            final EntityCreature entityTarget = (EntityCreature)this.ridingEntity;
            boolean hasRiddenTask = false;
            for (final Object task : entityTarget.tasks.taskEntries) {
                if (task instanceof AIAnimalMountedByEntity) {
                    hasRiddenTask = true;
                }
            }
            if (!hasRiddenTask) {
                entityTarget.tasks.addTask(1, (EntityAIBase)new AIAnimalMountedByEntity((EntityLiving)entityTarget, 1.0f));
            }
        }
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (Object)0);
    }
    
    public int getTalkInterval() {
        return 450;
    }
    
    public void readSpawnData(final ByteBuf additionalData) {
        NBTTagCompound data = null;
        try {
            this.AICombatMode = additionalData.readInt();
            this.AIMode = additionalData.readInt();
            this.partyPositionAngle = additionalData.readInt();
            this.partyDistanceToLeader = additionalData.readInt();
            final int length = additionalData.readInt();
            if (length > 0) {
                final byte[] bData = new byte[length];
                additionalData.readBytes(bData);
                data = CompressedStreamTools.decompress(bData, NBTSizeTracker.INFINITE);
                final ItemStack is = ItemStack.loadItemStackFromNBT(data);
                if (is != null) {
                    this.leftHandItem = is;
                }
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        this.updateHands();
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        try {
            buffer.writeInt(this.AICombatMode);
            buffer.writeInt(this.AIMode);
            buffer.writeInt(this.partyPositionAngle);
            buffer.writeInt(this.partyDistanceToLeader);
            final NBTTagCompound data = new NBTTagCompound();
            if (this.getLeftHandItem() != null) {
                this.getLeftHandItem().writeToNBT(data);
            }
            final byte[] bData = CompressedStreamTools.compress(data);
            buffer.writeInt(bData.length);
            buffer.writeBytes(bData);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        this.onSpawn();
    }
    
    public void setCurrentItemOrArmor(final int par1, final ItemStack par2ItemStack) {
        if (par1 == 5) {
            this.leftHandItem = par2ItemStack;
            this.updateHands();
            return;
        }
        super.setCurrentItemOrArmor(par1, par2ItemStack);
        if (par1 == 0) {
            this.updateHands();
        }
    }
    
    public String func_152113_b() {
        return this.ownerName;
    }
    
    public int getPhysicDefense() {
        return this.physicDefense;
    }
    
    public int getMagicDefense() {
        return this.magicDefense;
    }
    
    public int getBlastDefense() {
        return this.blastDefense;
    }
    
    public int getFireDefense() {
        return this.fireDefense;
    }
    
    public int getProjectileDefense() {
        return this.projectileDefense;
    }
    
    static {
        slowDownModifier = new AttributeModifier("Human speed mod", -0.2, 1).setSaved(false);
    }
}
