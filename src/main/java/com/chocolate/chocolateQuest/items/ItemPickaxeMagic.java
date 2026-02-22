package com.chocolate.chocolateQuest.items;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemPickaxeMagic extends Item
{
    public ItemPickaxeMagic() {
        this.setMaxStackSize(1);
        this.setMaxDurability(2024);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:pickaxeMagic");
    }
    
    public boolean canHarvestBlock(final Block par1Block) {
        return par1Block != Blocks.bedrock;
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = super.getItemAttributeModifiers();
        multimap.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemPickaxeMagic.itemModifierUUID, "Weapon modifier", 10000.0, 0));
        return multimap;
    }
    
    public float getStrVsBlock(final ItemStack par1ItemStack, final Block par2Block) {
        return (par2Block != null && (par2Block.getMaterial() == Material.iron || par2Block.getMaterial() == Material.anvil || par2Block.getMaterial() == Material.rock)) ? 15.0f : 8.0f;
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer player) {
        if (player.isSneaking()) {
            int mode = this.getMode(par1ItemStack) + 1;
            if (mode >= 3) {
                mode = 0;
            }
            this.setMode(par1ItemStack, mode);
            if (!par2World.isRemote) {
                if (mode == 2) {
                    player.addChatMessage((IChatComponent)new ChatComponentText(BDHelper.StringColor("f") + "Pickaxe mode: " + BDHelper.StringColor("3") + " build"));
                }
                else if (mode == 1) {
                    player.addChatMessage((IChatComponent)new ChatComponentText(BDHelper.StringColor("f") + "Pickaxe mode: " + BDHelper.StringColor("3") + " fill"));
                }
                else {
                    player.addChatMessage((IChatComponent)new ChatComponentText(BDHelper.StringColor("f") + "Pickaxe mode: " + BDHelper.StringColor("3") + " mine"));
                }
            }
        }
        return super.onItemRightClick(par1ItemStack, par2World, player);
    }
    
    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ) {
        if (player.isSneaking() && this.getMode(stack) != 0) {
            this.setBlockAndMetadata(stack, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
            return true;
        }
        final int cont = 0;
        final int size = 2;
        if (side == 0) {
            for (int t = -size + 1; t < size; ++t) {
                for (int v = -size + 1; v < size; ++v) {
                    this.destroyAndDropItem(world, x + t, y, z + v, 0, stack, player);
                }
            }
        }
        if (side == 1) {
            for (int t = -size + 1; t < size; ++t) {
                for (int v = -size + 1; v < size; ++v) {
                    this.destroyAndDropItem(world, x + t, y, z + v, 0, stack, player);
                }
            }
        }
        if (side == 2) {
            for (int t = -size + 1; t < size; ++t) {
                for (int v = -size + 1; v < size; ++v) {
                    this.destroyAndDropItem(world, x + t, y + v, z, 0, stack, player);
                }
            }
        }
        if (side == 3) {
            for (int t = -size + 1; t < size; ++t) {
                for (int v = -size + 1; v < size; ++v) {
                    this.destroyAndDropItem(world, x + t, y + v, z, 0, stack, player);
                }
            }
        }
        if (side == 4) {
            for (int t = -size + 1; t < size; ++t) {
                for (int v = -size + 1; v < size; ++v) {
                    this.destroyAndDropItem(world, x, y + t, z + v, 0, stack, player);
                }
            }
        }
        if (side == 5) {
            for (int t = -size + 1; t < size; ++t) {
                for (int v = -size + 1; v < size; ++v) {
                    this.destroyAndDropItem(world, x, y + t, z + v, 0, stack, player);
                }
            }
        }
        stack.damageItem(cont, (EntityLivingBase)player);
        return true;
    }
    
    private void destroyAndDropItem(final World world, final int i, final int j, final int k, final int power, final ItemStack is, final EntityPlayer player) {
        final Block id = world.getBlock(i, j, k);
        if (this.getMode(is) == 2) {
            world.setBlock(i, j, k, this.getBlock(is), this.getMetadata(is), 3);
            is.damageItem(1, (EntityLivingBase)player);
        }
        else if (id != Blocks.air && id != Blocks.bedrock) {
            if (world.isRemote) {
                final int md = world.getBlockMetadata(i, j, k);
                for (int c = 0; c < 8; ++c) {
                    world.spawnParticle("tilecrack_" + id + "_" + 0, i + (ItemPickaxeMagic.itemRand.nextFloat() - 0.5), (double)(j + ItemPickaxeMagic.itemRand.nextFloat() - 0.5f), k + ItemPickaxeMagic.itemRand.nextFloat() - 0.5, 0.0, 0.0, 0.0);
                }
            }
            is.damageItem(1, (EntityLivingBase)player);
            if (this.getMode(is) == 1) {
                world.setBlock(i, j, k, this.getBlock(is), this.getMetadata(is), 3);
            }
            else {
                world.setBlockToAir(i, j, k);
            }
        }
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        return new String("Super Tool!(Creative only)").concat((this.getMode(itemstack) == 2) ? "Build mode" : ((this.getMode(itemstack) == 1) ? "Fill mode" : ""));
    }
    
    public int getMode(final ItemStack is) {
        if (is.stackTagCompound != null) {
            return is.stackTagCompound.getByte("mode");
        }
        return 0;
    }
    
    public void setMode(final ItemStack is, final int i) {
        if (is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
        }
        is.stackTagCompound.setByte("mode", (byte)i);
    }
    
    public void setBlockAndMetadata(final ItemStack is, final Block block, final int md) {
        if (is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
        }
        is.stackTagCompound.setInteger("bl", Block.getIdFromBlock(block));
        is.stackTagCompound.setInteger("md", md);
    }
    
    public Block getBlock(final ItemStack is) {
        if (is.stackTagCompound == null) {
            return Blocks.stone;
        }
        final int id = is.stackTagCompound.getInteger("bl");
        if (id < 1) {
            return Blocks.stone;
        }
        return Block.getBlockById(id);
    }
    
    public int getMetadata(final ItemStack is) {
        if (is.stackTagCompound == null) {
            return 0;
        }
        return is.stackTagCompound.getInteger("md");
    }
}
