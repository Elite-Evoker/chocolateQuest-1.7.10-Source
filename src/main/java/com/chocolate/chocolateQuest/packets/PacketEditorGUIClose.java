package com.chocolate.chocolateQuest.packets;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import net.minecraft.tileentity.TileEntity;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import net.minecraft.util.MathHelper;
import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.util.AxisAlignedBB;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.world.World;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.List;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketEditorGUIClose implements IMessage, IMessageHandler<PacketEditorGUIClose, IMessage>
{
    int x;
    int y;
    int z;
    int width;
    int height;
    int length;
    String text;
    byte action;
    List<Coords> coords;
    
    public PacketEditorGUIClose() {
    }
    
    public PacketEditorGUIClose(final int posX, final int posY, final int posZ, final int width, final int length, final int height, final String name, final byte action) {
        this.x = posX;
        this.y = posY;
        this.z = posZ;
        this.width = width;
        this.length = length;
        this.height = height;
        this.text = name;
        this.action = action;
    }
    
    public void fromBytes(final ByteBuf inputStream) {
        this.x = inputStream.readInt();
        this.y = inputStream.readInt();
        this.z = inputStream.readInt();
        this.width = inputStream.readInt();
        this.length = inputStream.readInt();
        this.height = inputStream.readInt();
        this.text = PacketBase.readString(inputStream);
        this.action = inputStream.readByte();
    }
    
    public void toBytes(final ByteBuf outputStream) {
        outputStream.writeInt(this.x);
        outputStream.writeInt(this.y);
        outputStream.writeInt(this.z);
        outputStream.writeInt(this.width);
        outputStream.writeInt(this.length);
        outputStream.writeInt(this.height);
        PacketBase.writeString(outputStream, this.text);
        outputStream.writeByte((int)this.action);
    }
    
    public IMessage onMessage(final PacketEditorGUIClose message, final MessageContext ctx) {
        final EntityPlayer entityPlayer = (EntityPlayer)ctx.getServerHandler().playerEntity;
        message.execute(entityPlayer);
        return null;
    }
    
    public void execute(final EntityPlayer player) {
        final World world = player.worldObj;
        if (world.getBlock(this.x, this.y, this.z) == ChocolateQuest.exporter) {
            final BlockEditorTileEntity eb = (BlockEditorTileEntity)world.getTileEntity(this.x, this.y, this.z);
            eb.red = this.width;
            eb.yellow = this.length;
            eb.height = this.height;
            eb.name = this.text;
        }
        if (this.action == 1) {
            this.putEntitiesIntoSpawners(world, this.x, this.y, this.z, this.width, this.height, this.length);
            copy(world, this.x, this.y, this.z, this.width, this.height, this.length, this.text);
            this.spawnEntitiesFromSpawners(world);
        }
    }
    
    public void putEntitiesIntoSpawners(final World world, final int x, final int y, final int z, final int sx, final int sy, final int sz) {
        final List<EntityHumanBase> humans = world.getEntitiesWithinAABB((Class)EntityHumanBase.class, AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + sx), (double)(y + sy), (double)(z + sz)));
        this.coords = new ArrayList<Coords>();
        for (final EntityHumanBase human : humans) {
            if (!human.isDead) {
                if (human.party != null) {
                    if (human.party.getLeader() != human) {
                        continue;
                    }
                    this.putEntityIntoSpawner(human);
                }
                else {
                    this.putEntityIntoSpawner(human);
                }
            }
        }
    }
    
    public void putEntityIntoSpawner(final EntityHumanBase human) {
        final int x = MathHelper.floor_double(human.posX);
        final int y = MathHelper.floor_double(human.posY);
        final int z = MathHelper.floor_double(human.posZ);
        this.coords.add(new Coords(x, y, z));
        ItemMobToSpawner.saveToSpawner(x, y, z, human);
    }
    
    public void spawnEntitiesFromSpawners(final World world) {
        for (final Coords coord : this.coords) {
            final TileEntity te = world.getTileEntity(coord.x, coord.y, coord.z);
            if (te instanceof BlockMobSpawnerTileEntity) {
                final BlockMobSpawnerTileEntity teSpawner = (BlockMobSpawnerTileEntity)te;
                teSpawner.spawnEntity();
                world.setBlockToAir(coord.x, coord.y, coord.z);
            }
        }
    }
    
    public static void copy(final World world, int i, final int j, int k, final int sx, final int sy, final int sz, final String name) {
        ++i;
        ++k;
        final Schematic data = new Schematic(sx, sy, sz, i, j, k, name);
        final int cont = 0;
        for (int iX = 0; iX < sx; ++iX) {
            for (int iY = 0; iY < sy; ++iY) {
                for (int iZ = 0; iZ < sz; ++iZ) {
                    final int x = iX + i;
                    final int y = iY + j;
                    final int z = iZ + k;
                    data.setBlock(x, y, z, world.getBlock(x, y, z));
                    data.setBlockMetadata(x, y, z, (byte)world.getBlockMetadata(x, y, z));
                    final TileEntity te = world.getTileEntity(x, y, z);
                    if (te != null) {
                        data.addTileEntity(te);
                    }
                }
            }
        }
        final List<Entity> li = world.getEntitiesWithinAABB((Class)Entity.class, AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + sx), (double)(j + sy), (double)(k + sz)));
        for (int r = 0; r < li.size(); ++r) {
            final Entity e = li.get(r);
            if (!(e instanceof EntityPlayer) && !(e instanceof EntityBat)) {
                data.addEntity(e);
            }
        }
        final File file = new File(Minecraft.getMinecraft().mcDataDir, "config/Chocolate/Building/test/" + name + ".schematic");
        data.save(file);
    }
}
