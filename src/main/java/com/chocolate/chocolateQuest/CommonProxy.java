package com.chocolate.chocolateQuest;

import com.chocolate.chocolateQuest.gui.guinpc.ContainerAwakement;
import com.chocolate.chocolateQuest.gui.guinpc.ContainerShop;
import com.chocolate.chocolateQuest.gui.ContainerArmorStand;
import com.chocolate.chocolateQuest.gui.ContainerGun;
import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import com.chocolate.chocolateQuest.gui.ContainerHumanInventory;
import com.chocolate.chocolateQuest.gui.ContainerGolemInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.Entity;
import com.chocolate.chocolateQuest.gui.guiParty.GuiParty;
import com.chocolate.chocolateQuest.gui.GuiMobController;
import com.chocolate.chocolateQuest.gui.guinpc.GuiNPC;
import net.minecraft.client.gui.GuiScreen;
import com.chocolate.chocolateQuest.gui.guinpc.GuiEditDialog;
import com.chocolate.chocolateQuest.gui.guinpc.GuiAwakement;
import com.chocolate.chocolateQuest.gui.guinpc.InventoryAwakement;
import com.chocolate.chocolateQuest.gui.guinpc.GuiShop;
import com.chocolate.chocolateQuest.gui.guinpc.InventoryShop;
import com.chocolate.chocolateQuest.gui.GuiArmorStand;
import com.chocolate.chocolateQuest.gui.GuiGun;
import com.chocolate.chocolateQuest.gui.InventoryGun;
import com.chocolate.chocolateQuest.gui.GuiChestBD;
import com.chocolate.chocolateQuest.block.BlockDungeonChestTileEntity;
import com.chocolate.chocolateQuest.gui.GuiEditor;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanDummy;
import com.chocolate.chocolateQuest.gui.GuiGolem;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.gui.GuiHuman;
import net.minecraft.inventory.IInventory;
import com.chocolate.chocolateQuest.gui.GuiDummy;
import com.chocolate.chocolateQuest.gui.InventoryHuman;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import com.chocolate.chocolateQuest.block.BlockAltarTileEntity;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;
import cpw.mods.fml.common.registry.GameRegistry;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.packets.ChannelHandler;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
    public ChannelHandler channel;
    
    public void register() {
        GameRegistry.registerTileEntity((Class)BlockMobSpawnerTileEntity.class, "CQSpawner");
        this.registerTileEntities();
    }
    
    public void registerRenderInformation() {
    }
    
    public void postInit() {
    }
    
    public void registerTileEntities() {
        GameRegistry.registerTileEntity((Class)BlockArmorStandTileEntity.class, "armorStand");
        GameRegistry.registerTileEntity((Class)BlockBannerStandTileEntity.class, "bannerStand");
        GameRegistry.registerTileEntity((Class)BlockAltarTileEntity.class, "table");
        GameRegistry.registerTileEntity((Class)BlockEditorTileEntity.class, "exporter");
    }
    
    public void registerAudio() {
    }
    
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if (ID == 0) {
            final Entity e = world.getEntityByID(x);
            if (e instanceof EntityHumanNPC) {
                if (player.capabilities.isCreativeMode) {
                    return new GuiDummy((EntityHumanBase)e, (IInventory)new InventoryHuman((EntityHumanBase)e), player);
                }
                return new GuiHuman((EntityHumanBase)e, (IInventory)new InventoryHuman((EntityHumanBase)e), player);
            }
            else {
                if (e instanceof EntityGolemMecha) {
                    return new GuiGolem((EntityHumanBase)e, (IInventory)new InventoryHuman((EntityHumanBase)e), player);
                }
                if (e instanceof EntityHumanDummy) {
                    return new GuiDummy((EntityHumanBase)e, (IInventory)new InventoryHuman((EntityHumanBase)e), player);
                }
                if (e instanceof EntityHumanBase) {
                    return new GuiHuman((EntityHumanBase)e, (IInventory)new InventoryHuman((EntityHumanBase)e), player);
                }
            }
        }
        if (ID == 1) {
            return new GuiEditor(world, x, y, z);
        }
        if (ID == 4) {
            final TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof BlockDungeonChestTileEntity) {
                return new GuiChestBD((IInventory)player.inventory, (IInventory)te);
            }
        }
        if (ID == 3) {
            final ItemStack is = player.getCurrentEquippedItem();
            return new GuiGun(is, (IInventory)new InventoryGun(is, player), player);
        }
        if (ID == 2) {
            final BlockArmorStandTileEntity te2 = (BlockArmorStandTileEntity)world.getTileEntity(x, y, z);
            return new GuiArmorStand(te2, player, (IInventory)te2);
        }
        if (ID == 5) {
            final Entity e = world.getEntityByID(x);
            if (e instanceof EntityHumanNPC) {
                return new GuiShop(new InventoryShop((EntityHumanNPC)e), player);
            }
        }
        if (ID == 7) {
            final Entity e = world.getEntityByID(x);
            if (e instanceof EntityHumanNPC) {
                return new GuiAwakement(new InventoryAwakement((EntityHumanNPC)e), player, y, z);
            }
        }
        if (ID == 6) {
            final Entity e = world.getEntityByID(x);
            final int type = y;
            if (e instanceof EntityHumanNPC) {
                final EntityHumanNPC npc = (EntityHumanNPC)e;
                switch (type) {
                    case 1: {
                        return new GuiEditDialog(null, npc.conversation, npc);
                    }
                    default: {
                        return new GuiNPC(npc, player);
                    }
                }
            }
        }
        if (ID == 8) {
            return new GuiMobController();
        }
        if (ID == 9) {
            return new GuiParty();
        }
        return null;
    }
    
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if (ID == 0) {
            final Entity e = world.getEntityByID(x);
            if (e instanceof EntityGolemMecha) {
                return new ContainerGolemInventory((IInventory)player.inventory, (IInventory)new InventoryHuman((EntityHumanBase)e));
            }
            if (e instanceof EntityHumanBase) {
                return new ContainerHumanInventory((IInventory)player.inventory, (IInventory)new InventoryHuman((EntityHumanBase)e));
            }
        }
        if (ID == 4) {
            final TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof BlockDungeonChestTileEntity) {
                return new ContainerBDChest((IInventory)player.inventory, (IInventory)te);
            }
        }
        if (ID == 3) {
            final ItemStack is = player.getCurrentEquippedItem();
            return new ContainerGun((IInventory)player.inventory, (IInventory)new InventoryGun(is, player), is);
        }
        if (ID == 2) {
            final BlockArmorStandTileEntity te2 = (BlockArmorStandTileEntity)world.getTileEntity(x, y, z);
            return new ContainerArmorStand((IInventory)player.inventory, (IInventory)te2);
        }
        if (ID == 5) {
            final Entity e = world.getEntityByID(x);
            if (e instanceof EntityHumanNPC) {
                return new ContainerShop((IInventory)player.inventory, new InventoryShop((EntityHumanNPC)e));
            }
        }
        if (ID == 7) {
            final Entity e = world.getEntityByID(x);
            if (e instanceof EntityHumanNPC) {
                return new ContainerAwakement((IInventory)player.inventory, new InventoryAwakement((EntityHumanNPC)e), y, z);
            }
        }
        return null;
    }
}
