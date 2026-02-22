package com.chocolate.chocolateQuest.packets;

import net.minecraft.nbt.NBTTagCompound;
import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.item.ItemStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.gui.guinpc.ContainerShop;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.guinpc.ShopRecipe;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketUpdateShopRecipe implements IMessage
{
    int entityID;
    int recipeIndex;
    ShopRecipe recipe;
    ShopRecipe[] trades;
    
    public PacketUpdateShopRecipe() {
    }
    
    public PacketUpdateShopRecipe(final EntityHumanNPC npc, final int recipeIndex) {
        this.entityID = npc.getEntityId();
        this.recipeIndex = recipeIndex;
        if (recipeIndex > -1) {
            this.recipe = npc.getRecipes()[recipeIndex];
        }
        else {
            this.trades = npc.getRecipes();
        }
    }
    
    public void execute(final EntityPlayer player) {
        final Entity entity = player.worldObj.getEntityByID(this.entityID);
        if (entity instanceof EntityHumanNPC) {
            if (this.recipeIndex > -1) {
                ((EntityHumanNPC)entity).setRecipes(this.recipeIndex, this.recipe);
            }
            else {
                ((EntityHumanNPC)entity).setRecipes(this.trades);
            }
            if (player.openContainer instanceof ContainerShop) {
                ((ContainerShop)player.openContainer).shopInventory.updateCargo();
            }
        }
    }
    
    public void fromBytes(final ByteBuf bytes) {
        this.entityID = bytes.readInt();
        this.recipeIndex = bytes.readByte();
        if (this.recipeIndex > -1) {
            this.recipe = this.readRecipe(bytes);
        }
        else {
            final int tradesLength = bytes.readByte();
            this.trades = new ShopRecipe[tradesLength];
            for (int i = 0; i < tradesLength; ++i) {
                this.trades[i] = this.readRecipe(bytes);
            }
        }
    }
    
    public void toBytes(final ByteBuf bytes) {
        bytes.writeInt(this.entityID);
        bytes.writeByte(this.recipeIndex);
        if (this.recipeIndex > -1) {
            this.writeRecipe(bytes, this.recipe);
        }
        else if (this.trades == null) {
            bytes.writeByte(0);
        }
        else {
            bytes.writeByte(this.trades.length);
            for (int i = 0; i < this.trades.length; ++i) {
                this.writeRecipe(bytes, this.trades[i]);
            }
        }
    }
    
    public ShopRecipe readRecipe(final ByteBuf bytes) {
        final ItemStack recipeItem = this.readStackBytes(bytes);
        final int costItems = bytes.readByte();
        final ItemStack[] costItemStacks = new ItemStack[costItems];
        for (int i = 0; i < costItemStacks.length; ++i) {
            costItemStacks[i] = this.readStackBytes(bytes);
        }
        return new ShopRecipe(recipeItem, costItemStacks);
    }
    
    public void writeRecipe(final ByteBuf bytes, final ShopRecipe recipe) {
        this.writeStackBytes(bytes, recipe.tradedItem);
        bytes.writeByte(recipe.costItems.length);
        for (final ItemStack is : recipe.costItems) {
            this.writeStackBytes(bytes, is);
        }
    }
    
    public ItemStack readStackBytes(final ByteBuf bytes) {
        final int length = bytes.readInt();
        if (length > 0) {
            NBTTagCompound data = null;
            final byte[] bData = new byte[length];
            bytes.readBytes(bData);
            try {
                data = CompressedStreamTools.decompress(bData, NBTSizeTracker.INFINITE);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
            final ItemStack is = ItemStack.loadItemStackFromNBT(data);
            return is;
        }
        return null;
    }
    
    public void writeStackBytes(final ByteBuf bytes, final ItemStack itemstack) {
        try {
            final NBTTagCompound data = new NBTTagCompound();
            if (itemstack != null) {
                itemstack.writeToNBT(data);
            }
            final byte[] bData = CompressedStreamTools.compress(data);
            bytes.writeInt(bData.length);
            bytes.writeBytes(bData);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
