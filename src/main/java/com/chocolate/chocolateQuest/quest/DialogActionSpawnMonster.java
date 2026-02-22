package com.chocolate.chocolateQuest.quest;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.nbt.NBTTagCompound;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.items.ItemSoulBottle;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionSpawnMonster extends DialogAction
{
    static final int NORTH = 0;
    static final int SOUTH = 1;
    static final int EAST = 2;
    static final int WEST = 3;
    static final String[] directions;
    
    @Override
    public void execute(final EntityPlayer player, final EntityHumanNPC npc) {
        final NBTTagCompound tag = this.actionTag;
        if (tag != null) {
            final NBTTagCompound entitytag = tag.getCompoundTag("entity");
            if (entitytag != null) {
                final ChunkCoordinates coords = npc.getHomePosition();
                final Entity entity = ItemSoulBottle.createEntityFromNBT(entitytag, npc.worldObj, coords.posX, coords.posY, coords.posZ);
                if (this.value > 0) {
                    int x = MathHelper.floor_double((double)coords.posX);
                    int y = MathHelper.floor_double((double)coords.posY);
                    int z = MathHelper.floor_double((double)coords.posZ);
                    if (this.operator == 0) {
                        z -= this.value;
                    }
                    if (this.operator == 1) {
                        z += this.value;
                    }
                    if (this.operator == 2) {
                        x += this.value;
                    }
                    if (this.operator == 3) {
                        x -= this.value;
                    }
                    while (npc.worldObj.getBlock(x, y, z).getMaterial() != Material.air && y < 256) {
                        ++y;
                    }
                    entity.setPosition((double)x, (double)y, (double)z);
                }
                if (entity instanceof EntityHumanMob) {
                    final int x = MathHelper.floor_double(entity.posX);
                    final int y = MathHelper.floor_double(entity.posY);
                    final int z = MathHelper.floor_double(entity.posZ);
                    ((EntityHumanMob)entity).setHomeArea(x, y, z, -1);
                    ((EntityHumanMob)entity).saveToSpawner = false;
                }
                if (entity != null) {
                    npc.worldObj.spawnEntityInWorld(entity);
                }
            }
        }
    }
    
    @Override
    public byte getType() {
        return 14;
    }
    
    @Override
    public boolean hasName() {
        return true;
    }
    
    @Override
    public int getSelectorForName() {
        return 4;
    }
    
    @Override
    public boolean hasValue() {
        return true;
    }
    
    @Override
    public String getNameForValue() {
        return "Distance";
    }
    
    @Override
    public boolean hasOperator() {
        return true;
    }
    
    @Override
    public String getNameForOperator() {
        return "Direction";
    }
    
    @Override
    public String[] getOptionsForOperator() {
        return DialogActionSpawnMonster.directions;
    }
    
    @Override
    public boolean hasTag() {
        return true;
    }
    
    @Override
    public String getNameString() {
        return this.name;
    }
    
    @Override
    public void getSuggestions(final List<String> list) {
        list.add("Spawns an entity in the npc home position.");
        list.add("To select an entity you need to have it in you inventory");
        list.add("stored inside a Soul Bottle.");
    }
    
    static {
        directions = new String[] { "North", "South", "East", "West" };
    }
}
