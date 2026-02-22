package com.chocolate.chocolateQuest.packets;

import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;

public class PacketUpdateHumanDummyData extends PacketUpdateHumanData
{
    double maxHealth;
    float dropRight;
    float dropHelmet;
    float dropBody;
    float dropLegs;
    float dropFeet;
    float dropLeft;
    boolean lockedInventory;
    
    public PacketUpdateHumanDummyData() {
        this.maxHealth = 1.0;
    }
    
    public PacketUpdateHumanDummyData(final EntityHumanBase e) {
        super(e);
        this.maxHealth = 1.0;
        final EntityHumanBase human = e;
        this.maxHealth = human.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
        this.dropLeft = human.leftHandDropChances;
        this.dropRight = e.getEquipmentDropChances(0);
        this.dropHelmet = e.getEquipmentDropChances(4);
        this.dropBody = e.getEquipmentDropChances(3);
        this.dropLegs = e.getEquipmentDropChances(2);
        this.dropFeet = e.getEquipmentDropChances(1);
        this.lockedInventory = human.inventoryLocked;
    }
    
    @Override
    public void fromBytes(final ByteBuf inputStream) {
        super.fromBytes(inputStream);
        this.maxHealth = inputStream.readDouble();
        this.dropLeft = inputStream.readFloat();
        this.dropRight = inputStream.readFloat();
        this.dropHelmet = inputStream.readFloat();
        this.dropBody = inputStream.readFloat();
        this.dropLegs = inputStream.readFloat();
        this.dropFeet = inputStream.readFloat();
        this.lockedInventory = inputStream.readBoolean();
    }
    
    @Override
    public void toBytes(final ByteBuf outputStream) {
        super.toBytes(outputStream);
        outputStream.writeDouble(this.maxHealth);
        outputStream.writeFloat(this.dropLeft);
        outputStream.writeFloat(this.dropRight);
        outputStream.writeFloat(this.dropHelmet);
        outputStream.writeFloat(this.dropBody);
        outputStream.writeFloat(this.dropLegs);
        outputStream.writeFloat(this.dropFeet);
        outputStream.writeBoolean(this.lockedInventory);
    }
    
    @Override
    public void execute(final World world) {
        super.execute(world);
        if (this.human != null) {
            this.human.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.maxHealth);
            final EntityHumanBase dummy = this.human;
            dummy.leftHandDropChances = this.dropLeft;
            dummy.setEquipmentDropChances(0, this.dropRight);
            dummy.setEquipmentDropChances(4, this.dropHelmet);
            dummy.setEquipmentDropChances(3, this.dropBody);
            dummy.setEquipmentDropChances(2, this.dropLegs);
            dummy.setEquipmentDropChances(1, this.dropFeet);
            this.human.inventoryLocked = this.lockedInventory;
        }
    }
}
