package com.chocolate.chocolateQuest.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Random;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSpawnParticlesAround implements IMessage, IMessageHandler<PacketSpawnParticlesAround, IMessage>
{
    public static final byte HEARTS = 0;
    public static final byte CRIT = 1;
    public static final byte FLAME_LAUNCH = 2;
    public static final byte FLAMES = 3;
    public static final byte SPARK = 4;
    public static final byte SMOKE = 5;
    public static final byte WITCH_MAGIC = 6;
    public static final byte FIRE = 100;
    public static final byte BLAST = 101;
    public static final byte MAGIC = 102;
    public static final byte PHYSIC = 103;
    public static final byte LIGHT = 104;
    public static final byte DARK = 105;
    byte type;
    double x;
    double y;
    double z;
    
    public PacketSpawnParticlesAround() {
    }
    
    public PacketSpawnParticlesAround(final byte animType, final double x, final double y, final double z) {
        this.type = animType;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public IMessage onMessage(final PacketSpawnParticlesAround message, final MessageContext ctx) {
        final World world = ChannelHandlerClient.getClientWorld();
        message.execute(world);
        return null;
    }
    
    public void execute(final World world) {
        if (this.type == 2) {
            final Entity e = world.getEntityByID((int)this.x);
            final Random itemRand = new Random();
            if (e != null) {
                float x = (float)(-Math.sin(Math.toRadians(e.rotationYaw)));
                float z = (float)Math.cos(Math.toRadians(e.rotationYaw));
                final float y = (float)(-Math.sin(Math.toRadians(e.rotationPitch)));
                x *= 1.0f - Math.abs(y);
                z *= 1.0f - Math.abs(y);
                for (int i = 0; i < 50; ++i) {
                    final double flameRandomMotion = itemRand.nextDouble() + 0.2;
                    float height = e.height * 0.7f;
                    if (e instanceof EntityPlayer) {
                        height = 0.0f;
                    }
                    EffectManager.spawnParticle(3, e.worldObj, e.posX, e.posY + height, e.posZ, (x + (itemRand.nextDouble() - 0.5) / 3.0) * flameRandomMotion, (y + (itemRand.nextDouble() - 0.5) / 3.0) * flameRandomMotion, (z + (itemRand.nextDouble() - 0.5) / 3.0) * flameRandomMotion);
                }
            }
            return;
        }
        if (this.type >= 100) {
            final Random rand = world.rand;
            final double motion = 0.05;
            final double posVar = 3.0;
            Elements element = Elements.fire;
            if (this.type == 101) {
                element = Elements.blast;
            }
            if (this.type == 102) {
                element = Elements.magic;
            }
            if (this.type == 103) {
                element = Elements.physic;
            }
            if (this.type == 104) {
                element = Elements.light;
            }
            if (this.type == 105) {
                element = Elements.darkness;
            }
            for (int j = 0; j < 8; ++j) {
                EffectManager.spawnElementParticle(0, world, this.x + rand.nextGaussian() / posVar, this.y + rand.nextGaussian() / posVar, this.z + rand.nextGaussian() / posVar, rand.nextGaussian() * motion, rand.nextGaussian() * motion, rand.nextGaussian() * motion, element);
            }
        }
        else {
            String part = null;
            int ammount = 8;
            double motion2 = 0.25;
            double posVar2 = 3.0;
            switch (this.type) {
                case 0: {
                    part = "heart";
                    ammount = 4;
                    break;
                }
                case 1: {
                    part = "crit";
                    break;
                }
                case 3: {
                    part = "flame";
                    motion2 = 0.08;
                    break;
                }
                case 6: {
                    part = "witchMagic";
                    break;
                }
                case 5: {
                    part = "smoke";
                    motion2 = 0.02;
                    posVar2 = 6.0;
                    break;
                }
                case 4: {
                    part = "fireworksSpark";
                    break;
                }
                default: {
                    part = "crit";
                    break;
                }
            }
            final Random rand2 = world.rand;
            for (int k = 0; k < ammount; ++k) {
                world.spawnParticle(part, this.x + rand2.nextGaussian() / posVar2, this.y + rand2.nextGaussian() / posVar2, this.z + rand2.nextGaussian() / posVar2, rand2.nextGaussian() * motion2, rand2.nextGaussian() * motion2, rand2.nextGaussian() * motion2);
            }
        }
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.type = bytes.readByte();
        this.x = bytes.readDouble();
        this.y = bytes.readDouble();
        this.z = bytes.readDouble();
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeByte((int)this.type);
        bytes.writeDouble(this.x);
        bytes.writeDouble(this.y);
        bytes.writeDouble(this.z);
    }
    
    public static byte getParticleFromName(final String name) {
        if (name.equals("smoke")) {
            return 101;
        }
        if (name.equals("flame")) {
            return 3;
        }
        if (name.equals("witchMagic")) {
            return 102;
        }
        if (name.equals("fireworksSpark")) {
            return 103;
        }
        if (name.equals("crit")) {
            return 1;
        }
        if (name.equals("heart")) {
            return 0;
        }
        if (name.equals("fire")) {
            return 100;
        }
        if (name.equals("blast")) {
            return 101;
        }
        if (name.equals("magic")) {
            return 102;
        }
        if (name.equals("physic")) {
            return 103;
        }
        if (name.equals("light")) {
            return 104;
        }
        if (name.equals("dark")) {
            return 105;
        }
        if (name.equals("fireE")) {
            return 100;
        }
        if (name.equals("blastE")) {
            return 101;
        }
        if (name.equals("magicE")) {
            return 102;
        }
        if (name.equals("physicE")) {
            return 103;
        }
        return 0;
    }
}
