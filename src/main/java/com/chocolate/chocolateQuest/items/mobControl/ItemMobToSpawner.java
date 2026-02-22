package com.chocolate.chocolateQuest.items.mobControl;

import com.chocolate.chocolateQuest.entity.ai.EntityParty;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemMobToSpawner extends Item
{
    public ItemMobToSpawner() {
        this.setMaxStackSize(1);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.getUnlocalizedName().replace("item.", ""));
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, Entity entity) {
        if (!entity.worldObj.isRemote) {
            if (entity instanceof EntityGolemMecha && entity.riddenByEntity instanceof EntityHumanBase) {
                entity = entity.riddenByEntity;
            }
            final int x = MathHelper.floor_double(entity.posX);
            final int y = MathHelper.floor_double(entity.posY);
            final int z = MathHelper.floor_double(entity.posZ);
            if (player.isSneaking()) {
                player.worldObj.setBlock(x, y, z, Blocks.mob_spawner);
                final TileEntityMobSpawner teSpawner = new TileEntityMobSpawner();
                NBTTagCompound mobData = new NBTTagCompound();
                if (entity.writeToNBTOptional(mobData)) {
                    final MobSpawnerBaseLogic logic = teSpawner.func_145881_a();
                    logic.setEntityName(mobData.getString("id"));
                    mobData = new NBTTagCompound();
                    entity.writeToNBT(mobData);
                    mobData.removeTag("Age");
                    mobData.removeTag("UUIDMost");
                    mobData.removeTag("UUIDLeast");
                    mobData.removeTag("Dimension");
                    mobData.removeTag("Pos");
                    final NBTTagCompound logicTag = new NBTTagCompound();
                    logic.writeToNBT(logicTag);
                    logicTag.setTag("SpawnData", (NBTBase)mobData);
                    logic.readFromNBT(logicTag);
                    player.worldObj.setTileEntity(x, y, z, (TileEntity)teSpawner);
                }
                return true;
            }
            if (entity instanceof EntityHumanBase) {
                return saveToSpawner(x, y, z, (EntityHumanBase)entity);
            }
            final boolean saved = this.saveEntityToSpawner(x, y, z, entity);
            if (saved) {
                entity.setDead();
                return true;
            }
        }
        return false;
    }
    
    public boolean saveEntityToSpawner(final int x, int y, final int z, final Entity entity) {
        final World world = entity.worldObj;
        if (world.getBlock(x, y, z) != Blocks.air) {
            ++y;
        }
        world.setBlock(x, y, z, ChocolateQuest.spawner);
        final BlockMobSpawnerTileEntity te = new BlockMobSpawnerTileEntity();
        final NBTTagCompound tag = new NBTTagCompound();
        final boolean wrote = entity.writeToNBTOptional(tag);
        if (wrote) {
            te.mobNBT = tag;
            te.mob = -1;
            world.setTileEntity(x, y, z, (TileEntity)te);
            return true;
        }
        return false;
    }
    
    public static boolean saveToSpawner(final int x, int y, final int z, final EntityHumanBase human) {
        final World world = human.worldObj;
        if (world.getBlock(x, y, z) != Blocks.air) {
            ++y;
        }
        world.setBlock(x, y, z, ChocolateQuest.spawner);
        final BlockMobSpawnerTileEntity te = new BlockMobSpawnerTileEntity();
        final NBTTagCompound tag = getHumanSaveTagAndKillIt(x, y, z, human);
        te.mobNBT = tag;
        te.mob = -1;
        world.setTileEntity(x, y, z, (TileEntity)te);
        return true;
    }
    
    public static NBTTagCompound getHumanSaveTagAndKillIt(final int x, final int y, final int z, final EntityHumanBase human) {
        final NBTTagCompound tag = new NBTTagCompound();
        human.writeToNBTOptional(tag);
        human.writeEntityToSpawnerNBT(tag, x, y, z);
        final EntityParty p = human.party;
        if (p != null) {
            for (int i = 0; i < p.getMembersLength(); ++i) {
                if (p.getMember(i) != null) {
                    final Entity member = (Entity)p.getMember(i);
                    if (member.ridingEntity != null) {
                        member.ridingEntity.setDead();
                    }
                    member.setDead();
                }
            }
        }
        if (human.ridingEntity != null) {
            human.ridingEntity.setDead();
        }
        human.setDead();
        return tag;
    }
    
    public boolean onItemUse(final ItemStack is, final EntityPlayer player, final World world, final int x, final int y, final int z, final int par7, final float par8, final float par9, final float par10) {
        if (!world.isRemote) {
            final TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof BlockMobSpawnerTileEntity) {
                final BlockMobSpawnerTileEntity teSpawner = (BlockMobSpawnerTileEntity)te;
                teSpawner.spawnEntity();
            }
        }
        return super.onItemUse(is, player, world, x, y, z, par7, par8, par9, par10);
    }
    
    public boolean onBlockStartBreak(final ItemStack itemstack, final int X, final int Y, final int Z, final EntityPlayer player) {
        this.onItemUse(itemstack, player, player.worldObj, X, Y, Z, 0, 0.0f, 0.0f, 0.0f);
        return super.onBlockStartBreak(itemstack, X, Y, Z, player);
    }
}
