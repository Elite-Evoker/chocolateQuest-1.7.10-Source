package com.chocolate.chocolateQuest.builder.schematic;

import net.minecraft.util.ReportedException;
import net.minecraft.crash.CrashReport;
import java.io.InputStream;
import net.minecraft.nbt.CompressedStreamTools;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.Iterator;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import net.minecraft.entity.EntityHanging;
import net.minecraft.nbt.NBTBase;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTTagCompound;
import java.util.ArrayList;
import net.minecraft.tileentity.TileEntity;
import java.util.List;
import net.minecraft.block.Block;
import java.io.File;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.Map;
import net.minecraft.nbt.NBTTagList;

public class Schematic
{
    public short width;
    public short height;
    public short length;
    short[] blocks;
    byte[] metadata;
    String schematicName;
    int posX;
    int posY;
    int posZ;
    public NBTTagList entities;
    public NBTTagList tileEntities;
    Map<String, Integer> idMap;
    
    public Schematic() {
        this(new File(BDHelper.getAppDir(), BDHelper.getInfoDir() + "Building/test.schematic"));
    }
    
    public Schematic(final File file) {
        this.schematicName = "ChocolateQuest_Schematic";
        this.load(getNBTMap(file));
    }
    
    public Schematic(final int width, final int height, final int length) {
        this.schematicName = "ChocolateQuest_Schematic";
        this.width = (short)width;
        this.height = (short)height;
        this.length = (short)length;
        final int total = width * length * height;
        this.blocks = new short[total];
        this.metadata = new byte[total];
    }
    
    public Schematic(final int width, final int height, final int length, final int posX, final int posY, final int posZ, final String name) {
        this(width, height, length);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.schematicName = name;
    }
    
    public void setPosition(final int posX, final int posY, final int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
    
    public Block getBlock(final int x, final int y, final int z) {
        final int index = y * this.width * this.length + z * this.width + x;
        return Block.getBlockById((int)this.blocks[index]);
    }
    
    public int getBlockMetadata(final int x, final int y, final int z) {
        final int index = y * this.width * this.length + z * this.width + x;
        return this.metadata[index];
    }
    
    public List<TileEntity> getTileEntities() {
        if (this.tileEntities != null) {
            final ArrayList<TileEntity> list = new ArrayList<TileEntity>();
            for (int i = 0; i < this.tileEntities.tagCount(); ++i) {
                final NBTTagCompound tag = this.tileEntities.getCompoundTagAt(i);
                final TileEntity te = TileEntity.createAndLoadEntity(tag);
                if (te != null) {
                    final int x = te.xCoord;
                    final int y = te.yCoord;
                    final int z = te.zCoord;
                    te.xCoord = x + this.posX;
                    te.yCoord = y + this.posY;
                    te.zCoord = z + this.posZ;
                    list.add(te);
                }
            }
            return list;
        }
        return null;
    }
    
    public NBTTagList getTileEntitiesTag() {
        return this.tileEntities;
    }
    
    public List<Entity> getEntities(final World world) {
        if (this.entities != null) {
            final ArrayList<Entity> list = new ArrayList<Entity>();
            for (int i = 0; i < this.entities.tagCount(); ++i) {
                Entity e = (Entity)new EntityEgg(world);
                final int entityID = e.getEntityId();
                e = null;
                final NBTTagCompound tag = this.entities.getCompoundTagAt(i);
                e = EntityList.createEntityFromNBT(tag, world);
                if (e != null) {
                    if (e instanceof EntityPainting) {
                        final EntityPainting ep = (EntityPainting)e;
                        e = (Entity)new EntityPainting(world, ep.field_146063_b + this.posX, ep.field_146064_c + this.posY, ep.field_146062_d + this.posZ, ep.hangingDirection);
                        ((EntityPainting)e).art = ep.art;
                    }
                    else if (e instanceof EntityItemFrame) {
                        final EntityItemFrame ep2 = (EntityItemFrame)e;
                        e = (Entity)new EntityItemFrame(world, ep2.field_146063_b + this.posX, ep2.field_146064_c + this.posY, ep2.field_146062_d + this.posZ, ep2.hangingDirection);
                        if (ep2.getDisplayedItem() != null) {
                            ((EntityItemFrame)e).setDisplayedItem(ep2.getDisplayedItem());
                        }
                    }
                    else {
                        e.setPosition(e.posX + this.posX, e.posY + this.posY, e.posZ + this.posZ);
                        if (e instanceof EntityCreature) {
                            final EntityCreature creature = (EntityCreature)e;
                            if (creature.hasHome()) {
                                final ChunkCoordinates home = creature.getHomePosition();
                                creature.setHomeArea(home.posX + this.posX, home.posY + this.posY, home.posZ + this.posZ, (int)creature.getMaximumHomeDistance());
                            }
                        }
                    }
                    e.chunkCoordX = MathHelper.floor_double(e.posX % 16.0);
                    e.chunkCoordY = MathHelper.floor_double(e.posY % 16.0);
                    e.chunkCoordZ = MathHelper.floor_double(e.posZ % 16.0);
                    e.setEntityId(entityID);
                    list.add(e);
                }
            }
            return list;
        }
        return null;
    }
    
    public void setBlock(int x, int y, int z, final Block block) {
        x -= this.posX;
        y -= this.posY;
        z -= this.posZ;
        final int index = y * this.width * this.length + z * this.width + x;
        this.blocks[index] = (short)GameData.blockRegistry.getId((Object)block);
    }
    
    public void setBlockMetadata(int x, int y, int z, final byte metaData) {
        x -= this.posX;
        y -= this.posY;
        z -= this.posZ;
        final int index = y * this.width * this.length + z * this.width + x;
        this.metadata[index] = metaData;
    }
    
    public void setBlockAndMetadata(int x, int y, int z, final Block block, final byte metaData) {
        x -= this.posX;
        y -= this.posY;
        z -= this.posZ;
        final int index = y * this.width * this.length + z * this.width + x;
        this.blocks[index] = (short)GameData.blockRegistry.getId((Object)block);
        this.metadata[index] = metaData;
    }
    
    public void addTileEntity(final TileEntity te) {
        if (this.tileEntities == null) {
            this.tileEntities = new NBTTagList();
        }
        final int x = te.xCoord;
        final int y = te.yCoord;
        final int z = te.zCoord;
        te.xCoord = x - this.posX;
        te.yCoord = y - this.posY;
        te.zCoord = z - this.posZ;
        final NBTTagCompound data = new NBTTagCompound();
        te.writeToNBT(data);
        this.tileEntities.appendTag((NBTBase)data);
        te.xCoord = x;
        te.yCoord = y;
        te.zCoord = z;
    }
    
    public void addEntity(final Entity e) {
        if (this.entities == null) {
            this.entities = new NBTTagList();
        }
        if (e instanceof EntityHanging) {
            final EntityHanging entityHanging;
            final EntityHanging ef = entityHanging = (EntityHanging)e;
            entityHanging.field_146063_b -= this.posX;
            final EntityHanging entityHanging2 = ef;
            entityHanging2.field_146064_c -= this.posY;
            final EntityHanging entityHanging3 = ef;
            entityHanging3.field_146062_d -= this.posZ;
        }
        else {
            e.setPosition(e.posX - this.posX, e.posY - this.posY, e.posZ - this.posZ);
        }
        if (e instanceof EntityCreature) {
            final EntityCreature creature = (EntityCreature)e;
            if (creature.hasHome()) {
                final ChunkCoordinates home = creature.getHomePosition();
                final int distance = (int)creature.getMaximumHomeDistance();
                creature.setHomeArea(home.posX - this.posX, home.posY - this.posY, home.posZ - this.posZ, distance);
            }
        }
        final NBTTagCompound data = new NBTTagCompound();
        e.writeToNBTOptional(data);
        this.entities.appendTag((NBTBase)data);
        if (e instanceof EntityHanging) {
            final EntityHanging entityHanging4;
            final EntityHanging ef2 = entityHanging4 = (EntityHanging)e;
            entityHanging4.field_146063_b += this.posX;
            final EntityHanging entityHanging5 = ef2;
            entityHanging5.field_146064_c += this.posY;
            final EntityHanging entityHanging6 = ef2;
            entityHanging6.field_146062_d += this.posZ;
        }
        else {
            e.setPosition(e.posX + this.posX, e.posY + this.posY, e.posZ + this.posZ);
        }
        if (e instanceof EntityCreature) {
            final EntityCreature creature2 = (EntityCreature)e;
            if (creature2.hasHome()) {
                final ChunkCoordinates home2 = creature2.getHomePosition();
                final int distance2 = (int)creature2.getMaximumHomeDistance();
                creature2.setHomeArea(home2.posX + this.posX, home2.posY + this.posY, home2.posZ + this.posZ, distance2);
            }
        }
    }
    
    public void load(final NBTTagCompound schematic) {
        this.width = schematic.getShort("Width");
        this.height = schematic.getShort("Height");
        this.length = schematic.getShort("Length");
        final int total = this.width * this.length * this.height;
        this.blocks = new short[total];
        this.metadata = new byte[total];
        final byte[] blockBytes = schematic.getByteArray("Blocks");
        byte[] addedBytes = null;
        if (schematic.hasKey("Add")) {
            addedBytes = schematic.getByteArray("Add");
        }
        for (int i = 0; i < total; ++i) {
            short currentID = (short)(blockBytes[i] & 0xFF);
            if (addedBytes != null) {
                currentID ^= (short)(addedBytes[i] << 8);
            }
            this.blocks[i] = currentID;
        }
        this.metadata = schematic.getByteArray("Data");
        this.entities = schematic.getTagList("Entities", (int)schematic.getId());
        this.tileEntities = schematic.getTagList("TileEntities", (int)schematic.getId());
        final NBTTagList idMappingNBT = schematic.getTagList("IDMapping", (int)schematic.getId());
        if (idMappingNBT != null && idMappingNBT.tagCount() > 0) {
            this.loadMappings(idMappingNBT);
            this.translateToLocal();
        }
    }
    
    public void save(final File file) {
        final NBTTagCompound data = new NBTTagCompound();
        data.setShort("Width", this.width);
        data.setShort("Height", this.height);
        data.setShort("Length", this.length);
        data.setString("Materials", "Alpha");
        data.setString("Name", this.schematicName);
        final byte[] vanilaBlockIds = new byte[this.blocks.length];
        final byte[] addedBits = new byte[this.blocks.length];
        for (int i = 0; i < this.blocks.length; ++i) {
            vanilaBlockIds[i] = (byte)(this.blocks[i] & 0xFF);
            addedBits[i] = (byte)((this.blocks[i] & 0xF00) >> 8);
        }
        data.setByteArray("Blocks", vanilaBlockIds);
        data.setByteArray("Add", addedBits);
        data.setByteArray("Data", this.metadata);
        if (this.tileEntities != null) {
            data.setTag("TileEntities", (NBTBase)this.tileEntities);
        }
        if (this.entities != null) {
            data.setTag("Entities", (NBTBase)this.entities);
        }
        data.setTag("IDMapping", this.saveMappings());
        try {
            final FileOutputStream fos = new FileOutputStream(file);
            writeCompressed((NBTBase)data, fos);
            fos.close();
        }
        catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (final IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public void translateToLocal() {
        final HashMap<Integer, String> tMap = new HashMap<Integer, String>();
        for (final String key : this.idMap.keySet()) {
            tMap.put(this.idMap.get(key), key);
        }
        for (int i = 0; i < this.blocks.length; ++i) {
            if (this.blocks[i] != 0) {
                final int currentID = this.blocks[i];
                if (this.idMap.containsValue(currentID)) {
                    final Block block = Block.getBlockFromName((String)tMap.get(currentID));
                    if (block != null) {
                        this.blocks[i] = (short)Block.getIdFromBlock(block);
                    }
                    else {
                        this.blocks[i] = 0;
                    }
                }
            }
        }
    }
    
    public void loadMappings(final NBTTagList list) {
        this.idMap = new HashMap<String, Integer>();
        for (int i = 0; i < list.tagCount(); ++i) {
            final NBTTagCompound tag = list.getCompoundTagAt(i);
            this.idMap.put(tag.getString("ItemType"), tag.getInteger("ItemId"));
        }
    }
    
    public NBTBase saveMappings() {
        this.idMap = new HashMap<String, Integer>();
        for (int i = 0; i < this.blocks.length; ++i) {
            if (!this.idMap.containsValue(this.blocks[i])) {
                final Block block = Block.getBlockById((int)this.blocks[i]);
                final String name = Block.blockRegistry.getNameForObject((Object)block);
                this.idMap.put(name, (int)this.blocks[i]);
            }
        }
        final NBTTagList list = new NBTTagList();
        for (final String key : this.idMap.keySet()) {
            final NBTTagCompound tag = new NBTTagCompound();
            tag.setString("ItemType", key);
            tag.setInteger("ItemId", (int)this.idMap.get(key));
            list.appendTag((NBTBase)tag);
        }
        return (NBTBase)list;
    }
    
    public static void writeCompressed(final NBTBase tagCompound, final FileOutputStream file) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(file));
        try {
            final Method method = ReflectionHelper.findMethod((Class)NBTTagCompound.class, (Object)null, new String[] { "func_150298_a", "a" }, new Class[] { String.class, NBTBase.class, DataOutput.class });
            method.invoke(null, "Schematic", tagCompound, dataOutputStream);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        finally {
            dataOutputStream.close();
        }
    }
    
    public static NBTTagCompound getNBTMap(final File file) {
        try {
            final FileInputStream fos = new FileInputStream(file);
            final NBTBase base = (NBTBase)CompressedStreamTools.readCompressed((InputStream)fos);
            if (base instanceof NBTTagCompound) {
                fos.close();
                return (NBTTagCompound)base;
            }
            BDHelper.println("Found corrupted better dungeons template :" + file.getPath() + ", Skipping generation.");
            return null;
        }
        catch (final FileNotFoundException e) {
            final CrashReport crashreport = CrashReport.makeCrashReport((Throwable)e, "File not found at better dungeons mod, file: " + file.getPath());
            throw new ReportedException(crashreport);
        }
        catch (final IOException e2) {
            final CrashReport crashreport = CrashReport.makeCrashReport((Throwable)e2, "Error reading file at better dungeons mod, file: " + file.getPath());
            throw new ReportedException(crashreport);
        }
    }
}
