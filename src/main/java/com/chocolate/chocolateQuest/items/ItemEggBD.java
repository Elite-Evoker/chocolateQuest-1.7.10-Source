package com.chocolate.chocolateQuest.items;

import net.minecraft.util.EnumFacing;
import net.minecraft.dispenser.IBlockSource;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import com.chocolate.chocolateQuest.ChocolateQuest;
import java.lang.reflect.Constructor;
import com.chocolate.chocolateQuest.misc.EquipementHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityCreature;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.WorldGeneratorNew;
import java.util.Random;
import java.io.File;
import com.chocolate.chocolateQuest.API.DungeonBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.API.DungeonRegister;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.BlockDispenser;
import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.entity.mob.EntitySpecterBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityWalkerBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityPirateBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityLich;
import com.chocolate.chocolateQuest.entity.mob.EntityNecromancer;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPigZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPirate;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanGremlin;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanWalker;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSpecter;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanDummy;
import net.minecraft.util.IIcon;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.item.Item;

public class ItemEggBD extends Item implements IBehaviorDispenseItem
{
    final int unequipped = 0;
    final int leather = 1;
    final int chain = 2;
    final int gold = 3;
    final int iron = 4;
    final int diamond = 5;
    int OFF_BOSS_HUMAN;
    int OFF_BOSS;
    int OFF_DUNGEON;
    public static final int MICROPHONE = 499;
    int cooldown;
    String[] name;
    String[] bossName;
    IIcon[] bossIcon;
    IIcon[] dungeonIcon;
    MobData[] mobs;
    MobData[] humanBosses;
    MobData[] bosses;
    
    public ItemEggBD() {
        this.OFF_BOSS_HUMAN = 700;
        this.OFF_BOSS = 800;
        this.OFF_DUNGEON = 900;
        this.cooldown = 0;
        this.mobs = new MobData[] { new MobData(EntityHumanDummy.class, "Dummy", 0), new MobData(EntityHumanSkeleton.class, "Skeleton", 8), new MobData(EntityHumanZombie.class, "Zombie", 7), new MobData(EntityHumanSpecter.class, "Specter", 12), new MobData(EntityHumanWalker.class, "Abyss Walker", 10), new MobData(EntityHumanGremlin.class, "Gremlin", 11), new MobData(EntityHumanPirate.class, "Pirate", 9), new MobData(EntityHumanPigZombie.class, "PigZombie", 3) };
        this.humanBosses = new MobData[] { new MobData(EntityNecromancer.class, "Necromancer", 8), new MobData(EntityLich.class, "Lich", 7), new MobData(EntityPirateBoss.class, "Pirate Boss", 9), new MobData(EntityWalkerBoss.class, "Abyss Walker Boss", 10), new MobData(EntitySpecterBoss.class, "Specter Boss", 12), new MobData(EntityHumanNPC.class, "NPC", 0) };
        this.bosses = new MobData[] { new MobData(EntityTurtle.class, "Turtle", 3), new MobData(EntityGiantBoxer.class, "Monking", 2), new MobData(EntityBull.class, "Bull", 4), new MobData(EntitySlimeBoss.class, "Slime", 1), new MobData(EntitySpiderBoss.class, "Spider", 0), new MobData(EntityGiantBoxer.class, "Giant Zombie", 6), new MobData(EntityWyvern.class, "Dragon", 5) };
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)this);
        this.setHasSubtypes(true);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.bossIcon = new IIcon[7];
        for (int i = 0; i < this.bossIcon.length; ++i) {
            this.bossIcon[i] = iconRegister.registerIcon("chocolatequest:b" + i);
        }
        this.dungeonIcon = new IIcon[12];
        for (int i = 0; i < this.dungeonIcon.length; ++i) {
            this.dungeonIcon[i] = iconRegister.registerIcon("chocolatequest:d" + i);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item itemId, final CreativeTabs table, final List list) {
        for (int i = 0; i < this.mobs.length; ++i) {
            for (int l = 0; l < 6; ++l) {
                list.add(new ItemStack(itemId, 1, i + 100 * l));
            }
        }
        for (int i = 0; i < this.humanBosses.length; ++i) {
            list.add(new ItemStack(itemId, 1, i + this.OFF_BOSS_HUMAN));
        }
        for (int i = 0; i < this.bosses.length; ++i) {
            list.add(new ItemStack(itemId, 1, i + this.OFF_BOSS));
        }
        if (DungeonRegister.dungeonList != null) {
            for (int i = 0; i < DungeonRegister.dungeonList.size(); ++i) {
                list.add(new ItemStack(itemId, 1, this.OFF_DUNGEON + i));
            }
        }
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final MovingObjectPosition movingobjectposition = this.playerPick(entityplayer, world);
        if (movingobjectposition != null) {
            int i;
            int j;
            int k;
            if (movingobjectposition.entityHit != null) {
                i = MathHelper.floor_double(movingobjectposition.entityHit.posX);
                j = MathHelper.floor_double(movingobjectposition.entityHit.posY) + 1;
                k = MathHelper.floor_double(movingobjectposition.entityHit.posZ);
            }
            else {
                i = movingobjectposition.blockX;
                j = movingobjectposition.blockY;
                k = movingobjectposition.blockZ;
                switch (movingobjectposition.sideHit) {
                    case 1: {
                        ++j;
                        break;
                    }
                    case 0: {
                        --j;
                        break;
                    }
                    case 2: {
                        --k;
                        break;
                    }
                    case 3: {
                        ++k;
                        break;
                    }
                    case 5: {
                        ++i;
                        break;
                    }
                    case 4: {
                        --i;
                        break;
                    }
                }
            }
            this.onItemUse(itemstack, entityplayer, world, i, j, k, 0);
        }
        return super.onItemRightClick(itemstack, world, entityplayer);
    }
    
    public MovingObjectPosition playerPick(final EntityPlayer player, final World world) {
        final int dist = 80;
        final Vec3 vec3d = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        final Vec3 vec3d2 = Vec3.createVectorHelper(player.posX + player.getLookVec().xCoord * dist, player.posY + player.getLookVec().yCoord * dist, player.posZ + player.getLookVec().zCoord * dist);
        final MovingObjectPosition movingobjectposition = world.rayTraceBlocks(vec3d, vec3d2);
        final List list = world.getEntitiesWithinAABBExcludingEntity((Entity)player, player.boundingBox.addCoord(player.getLookVec().xCoord * 40.0, player.getLookVec().yCoord * 40.0, player.getLookVec().zCoord * 40.0).expand(1.0, 1.0, 1.0));
        final double d = 0.0;
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity1 = list.get(j);
            if (entity1.canBeCollidedWith()) {
                final float f2 = 0.3f;
                final AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f2, (double)f2, (double)f2);
                final MovingObjectPosition movingobjectposition2 = axisalignedbb.calculateIntercept(vec3d, vec3d2);
                if (movingobjectposition2 != null) {
                    movingobjectposition2.entityHit = entity1;
                    return movingobjectposition2;
                }
            }
        }
        return movingobjectposition;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, final int j, int k, final int l) {
        final int itemDamage = itemstack.getMetadata();
        if (itemDamage >= this.OFF_DUNGEON && !world.isRemote) {
            if (this.cooldown <= 0) {
                DungeonBase dungeon = DungeonRegister.dungeonList.get(itemDamage - this.OFF_DUNGEON);
                if (dungeon != null) {
                    final File file = new File(dungeon.getPath());
                    dungeon = dungeon.readData(file);
                    if (dungeon != null) {
                        final Random random = new Random();
                        final int x = i / 16;
                        final int z = k / 16;
                        i -= Math.abs(i % 16);
                        k -= Math.abs(k % 16);
                        random.setSeed(WorldGeneratorNew.getSeed(world, x, z));
                        BuilderHelper.builderHelper.initialize(3);
                        dungeon.getBuilder().generate(random, world, i, j, k, dungeon.getMobID());
                        BuilderHelper.builderHelper.flush(world);
                        this.cooldown = 100;
                    }
                }
            }
            return true;
        }
        final Entity e = this.getEntity(world, i, j, k, itemstack);
        if (e != null) {
            e.setPosition(i + 0.5, (double)(j + 1), k + 0.5);
            ((EntityCreature)e).setHomeArea(MathHelper.floor_double(e.posX), MathHelper.floor_double(e.posY), MathHelper.floor_double(e.posZ), 20);
            if (!world.isRemote) {
                world.spawnEntityInWorld(e);
            }
            return true;
        }
        return false;
    }
    
    public Entity getEntity(final World world, final int i, final int j, final int k, final ItemStack itemStack) {
        final int itemDamage = itemStack.getMetadata();
        Entity e = null;
        Class currentClass = EntityHumanBase.class;
        final MobData data = this.getDataFromDamage(itemDamage);
        if (data != null) {
            currentClass = data.mobClass;
        }
        try {
            final Constructor<Entity> c = currentClass.getDeclaredConstructor(World.class);
            c.setAccessible(true);
            e = c.newInstance(world);
        }
        catch (final Exception ex) {
            e = (Entity)new EntityHumanBase(world);
        }
        if (itemDamage < this.OFF_BOSS_HUMAN) {
            if (itemStack.stackTagCompound != null) {
                e.readFromNBT(itemStack.stackTagCompound);
            }
            else if (e instanceof EntityHumanBase) {
                final EntityHumanBase entityLiving = (EntityHumanBase)e;
                final int armorType = itemDamage / 100;
                if (armorType != 0) {
                    EquipementHelper.equipHumanRandomly(entityLiving, armorType - 1, EquipementHelper.getRandomType(entityLiving, 5));
                }
            }
        }
        return e;
    }
    
    public void onUpdate(final ItemStack par1ItemStack, final World par2World, final Entity par3Entity, final int par4, final boolean par5) {
        if (this.cooldown > 0) {
            --this.cooldown;
        }
        super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        final int i = itemstack.getMetadata();
        if (i >= this.OFF_DUNGEON) {
            final DungeonBase dungeon = DungeonRegister.dungeonList.get(i - this.OFF_DUNGEON);
            if (dungeon != null) {
                return dungeon.getName();
            }
        }
        final int itemDamage = itemstack.getMetadata();
        String info = "";
        if (i < this.OFF_BOSS) {
            final int armorType = itemDamage / 100;
            switch (armorType) {
                case 1: {
                    info = " Leather";
                    break;
                }
                case 2: {
                    info = " Chain";
                    break;
                }
                case 3: {
                    info = " Gold";
                    break;
                }
                case 4: {
                    info = " Iron";
                    break;
                }
                case 5: {
                    info = " Diamond";
                    break;
                }
            }
        }
        final MobData data = this.getDataFromDamage(itemDamage);
        if (data != null) {
            return data.name + info;
        }
        return "????";
    }
    
    public IIcon getIconFromDamage(final int itemDamage) {
        if (itemDamage == 499) {
            return this.dungeonIcon[11];
        }
        if (itemDamage >= this.OFF_DUNGEON) {
            final DungeonBase dungeon = DungeonRegister.dungeonList.get(itemDamage - this.OFF_DUNGEON);
            if (dungeon != null) {
                if (dungeon.getIcon() < this.dungeonIcon.length) {}
                return this.dungeonIcon[dungeon.getIcon()];
            }
        }
        final MobData data = this.getDataFromDamage(itemDamage);
        if (data == null) {
            return this.dungeonIcon[0];
        }
        if (itemDamage < this.OFF_BOSS) {
            return ChocolateQuest.shield.getIconFromDamage(data.color);
        }
        return this.bossIcon[data.color];
    }
    
    public MobData getDataFromDamage(int itemDamage) {
        if (itemDamage >= this.OFF_BOSS) {
            itemDamage -= this.OFF_BOSS;
            if (itemDamage < this.bosses.length) {
                return this.bosses[itemDamage];
            }
        }
        else if (itemDamage >= this.OFF_BOSS_HUMAN) {
            itemDamage -= this.OFF_BOSS_HUMAN;
            if (itemDamage < this.bosses.length) {
                return this.humanBosses[itemDamage];
            }
        }
        else {
            itemDamage %= 100;
            if (itemDamage < this.mobs.length) {
                return this.mobs[itemDamage];
            }
        }
        return null;
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        if (stack.getMetadata() >= this.OFF_BOSS_HUMAN) {
            if (stack.stackTagCompound != null) {
                final Entity e = player.worldObj.getEntityByID(stack.stackTagCompound.getInteger("EntityID"));
                if (e instanceof EntityLiving) {
                    if (entity instanceof EntityLiving) {
                        ((EntityLiving)e).setAttackTarget((EntityLivingBase)entity);
                    }
                }
                else {
                    if (!player.worldObj.isRemote) {
                        player.addChatMessage((IChatComponent)new ChatComponentText("Assigned entity not found, left click to assign a new entity"));
                    }
                    stack.stackTagCompound = null;
                }
                return true;
            }
            if (entity instanceof EntityLiving) {
                (stack.stackTagCompound = new NBTTagCompound()).setInteger("EntityID", entity.getEntityId());
                stack.stackTagCompound.setString("name", entity.getCommandSenderName());
                if (!player.worldObj.isRemote) {
                    player.addChatMessage((IChatComponent)new ChatComponentText("Assigned " + BDHelper.StringColor("2") + entity.getCommandSenderName() + BDHelper.StringColor("f") + " to this item"));
                    player.addChatMessage((IChatComponent)new ChatComponentText("Left click to another entity to start a fight!"));
                }
                return true;
            }
        }
        if (entity instanceof EntityHumanBase) {
            final NBTTagCompound mobData = new NBTTagCompound();
            entity.writeToNBT(mobData);
            (stack.stackTagCompound = mobData).removeTag("Age");
            mobData.removeTag("UUIDMost");
            mobData.removeTag("UUIDLeast");
            mobData.removeTag("Dimension");
            mobData.removeTag("Pos");
            mobData.removeTag("Attributes");
            player.addChatMessage((IChatComponent)new ChatComponentText("Saved entity data to this item"));
            return true;
        }
        return false;
    }
    
    public ItemStack dispense(final IBlockSource iblocksource, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.getFacingDirection(iblocksource.getBlockMetadata());
        final double d0 = iblocksource.getX() + enumfacing.getFrontOffsetX();
        final double d2 = iblocksource.getYInt() + 0.2f;
        final double d3 = iblocksource.getZ() + enumfacing.getFrontOffsetZ();
        final Entity e = this.getEntity(iblocksource.getWorld(), (int)d0, (int)d2, (int)d3, itemstack);
        if (e != null) {
            e.setPosition(d0, d2, d3);
            if (!iblocksource.getWorld().isRemote) {
                iblocksource.getWorld().spawnEntityInWorld(e);
            }
        }
        --itemstack.stackSize;
        return itemstack;
    }
}
