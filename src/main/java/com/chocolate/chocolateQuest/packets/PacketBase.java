package com.chocolate.chocolateQuest.packets;

import java.io.InputStream;
import io.netty.buffer.ByteBufInputStream;
import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public abstract class PacketBase implements IMessage
{
    public void setData(final byte[] bs) {
    }
    
    public abstract void fromBytes(final ByteBuf p0);
    
    public abstract void toBytes(final ByteBuf p0);
    
    public static void writeString(final ByteBuf bytes, final String string) {
        final byte size = (byte)((string.length() > 255) ? 255 : string.length());
        bytes.writeByte(string.length());
        for (byte i = 0; i < size; ++i) {
            bytes.writeChar((int)string.charAt(i));
        }
    }
    
    public static String readString(final ByteBuf bytes) {
        String string = "";
        for (byte size = bytes.readByte(), i = 0; i < size; ++i) {
            string += bytes.readChar();
        }
        return string;
    }
    
    public static void writeTag(final ByteBuf bytes, final NBTTagCompound tag) {
        try {
            bytes.writeBytes(CompressedStreamTools.compress(tag));
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public static NBTTagCompound readTag(final ByteBuf bytes) {
        final ByteBufInputStream byteBuff = new ByteBufInputStream(bytes);
        NBTTagCompound tag = null;
        try {
            tag = CompressedStreamTools.readCompressed((InputStream)byteBuff);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        return tag;
    }
}
