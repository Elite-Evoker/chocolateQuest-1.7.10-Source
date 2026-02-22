package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.packets.PacketUpdateConversation;
import java.util.Iterator;
import com.chocolate.chocolateQuest.gui.slot.SlotShop;
import net.minecraft.client.gui.FontRenderer;
import java.util.List;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.item.ItemStack;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketUpdateShopRecipe;
import net.minecraft.inventory.Slot;
import java.io.File;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.gui.GuiButton;
import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiTextField;
import com.chocolate.chocolateQuest.gui.GuiPlayerInventory;

public class GuiShop extends GuiPlayerInventory
{
    InventoryShop shop;
    int selectedSlot;
    int NEW_TRADE;
    int REMOVE_TRADE;
    int LOAD;
    int SAVE;
    GuiTextField textboxFile;
    GuiScrollFiles loadFolder;
    
    public GuiShop(final InventoryShop shop, final EntityPlayer player) {
        super(new ContainerShop((IInventory)player.inventory, shop), (IInventory)shop, player);
        this.NEW_TRADE = 0;
        this.REMOVE_TRADE = 1;
        this.LOAD = 2;
        this.SAVE = 3;
        this.shop = shop;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        if (this.player.capabilities.isCreativeMode) {
            final int width = (this.width - this.xSize) / 2;
            final int height = (this.height - this.ySize) / 2;
            final GuiButton b = new GuiButton(this.NEW_TRADE, width + 110, height + 52, 60, 20, "Add trade");
            this.buttonList.add(b);
            final GuiButton b2 = new GuiButton(this.REMOVE_TRADE, width + 180, height + 52, 60, 20, "Remove");
            this.buttonList.add(b2);
            final File file = new File(BDHelper.getAppDir(), BDHelper.getQuestDir() + "shopRecipes/");
            if (!file.exists()) {
                file.mkdirs();
            }
            final int buttonsWidth = this.width / 4;
            this.loadFolder = new GuiScrollFiles(999, 5, 10, buttonsWidth, 80, file, this.fontRendererObj);
            this.buttonList.add(this.loadFolder);
            final GuiButton b3 = new GuiButton(this.LOAD, 5, 90, buttonsWidth, 20, "Load");
            this.buttonList.add(b3);
            (this.textboxFile = new GuiTextField(this.fontRendererObj, 5, 140, buttonsWidth, 20)).setMaxStringLength(25);
            final GuiButton b4 = new GuiButton(this.SAVE, 5, 160, buttonsWidth, 20, "Save");
            this.buttonList.add(b4);
        }
    }
    
    protected void keyTyped(final char c, final int i) {
        if (this.textboxFile != null) {
            this.textboxFile.textboxKeyTyped(c, i);
            if (i == this.mc.gameSettings.keyBindInventory.getKeyCode() && this.textboxFile.isFocused()) {
                return;
            }
        }
        super.keyTyped(c, i);
    }
    
    protected void mouseClicked(final int x, final int y, final int r) {
        super.mouseClicked(x, y, r);
        if (this.textboxFile != null) {
            this.textboxFile.mouseClicked(x, y, r);
        }
    }
    
    @Override
    protected void handleMouseClick(final Slot slot, final int slotID, final int x, final int y) {
        if (slotID >= 36 && slotID < 54) {
            this.selectedSlot = slotID - 36;
        }
        if (this.shop.trades != null && this.selectedSlot > this.shop.trades.length) {
            this.selectedSlot = this.shop.trades.length;
        }
        if (this.textboxFile != null) {
            this.textboxFile.mouseClicked(x, y, 1);
        }
        super.handleMouseClick(slot, slotID, x, y);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == this.NEW_TRADE && this.shop.setShopRecipe(this.selectedSlot)) {
            final IMessage packet = (IMessage)new PacketUpdateShopRecipe(this.shop.human, this.selectedSlot);
            ChocolateQuest.channel.sendPaquetToServer(packet);
        }
        if (button.id == this.REMOVE_TRADE && this.shop.trades != null && this.selectedSlot < this.shop.trades.length && this.shop.trades.length > 0) {
            this.shop.removeShopRecipe(this.selectedSlot);
            final IMessage packet = (IMessage)new PacketUpdateShopRecipe(this.shop.human, -1);
            ChocolateQuest.channel.sendPaquetToServer(packet);
        }
        if (button.id == this.SAVE) {
            final NBTTagCompound tag = new NBTTagCompound();
            final NBTTagList list = new NBTTagList();
            for (int i = 0; i < this.shop.trades.length; ++i) {
                final NBTTagCompound tag2 = new NBTTagCompound();
                this.shop.trades[i].writeToNBTWithMapping(tag2);
                list.appendTag((NBTBase)tag2);
            }
            tag.setTag("Recipes", (NBTBase)list);
            try {
                final String name = this.textboxFile.getText();
                if (name.length() > 0 && !name.equals(" ")) {
                    BDHelper.writeCompressed(tag, new File(BDHelper.getAppDir(), BDHelper.getQuestDir() + "shopRecipes/" + name + ".trade"));
                    this.loadFolder.updateFiles();
                }
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
        if (button.id == this.LOAD) {
            final File file = this.loadFolder.getSelectedFile();
            if (file != null) {
                final NBTTagCompound tag3 = BDHelper.readCompressed(file);
                if (tag3 != null) {
                    final NBTTagList list2 = (NBTTagList)tag3.getTag("Recipes");
                    final int recipeAmmount = list2.tagCount();
                    if (recipeAmmount > 0) {
                        final ShopRecipe[] recipes = new ShopRecipe[recipeAmmount];
                        for (int j = 0; j < recipeAmmount; ++j) {
                            (recipes[j] = new ShopRecipe(null, null)).readFromNBTWithMapping(list2.getCompoundTagAt(j));
                        }
                        this.shop.human.setRecipes(recipes);
                        this.shop.updateCargo();
                        final IMessage packet2 = (IMessage)new PacketUpdateShopRecipe(this.shop.human, -1);
                        ChocolateQuest.channel.sendPaquetToServer(packet2);
                    }
                }
            }
        }
        super.actionPerformed(button);
    }
    
    protected void drawHoveringText(final List list, final int x, final int y, final FontRenderer font) {
        boolean draw = true;
        int listWidth = 0;
        for (final String s : list) {
            final int l = font.getStringWidth(s);
            if (l > listWidth) {
                listWidth = l;
            }
        }
        Slot slotUnderMouse = null;
        final int px = x - (this.width - this.xSize) / 2;
        final int py = y - (this.height - this.ySize) / 2;
        for (int i1 = 36; i1 < 54; ++i1) {
            final Slot slot = this.inventorySlots.inventorySlots.get(i1);
            if (slot.xDisplayPosition < px && slot.xDisplayPosition + 16 > px && slot.yDisplayPosition < py && slot.yDisplayPosition + 16 > py) {
                slotUnderMouse = slot;
            }
        }
        if (slotUnderMouse instanceof SlotShop) {
            final SlotShop slot2 = (SlotShop)slotUnderMouse;
            final ShopRecipe recipe = slot2.getRecipe();
            if (recipe != null) {
                list.add("");
                list.add("");
                draw = false;
                super.drawHoveringText(list, x, y, font);
                final int listHeight = 2 + (list.size() - 1) * 10;
                final ItemStack[] recipeCost = recipe.costItems;
                int x_off = 10;
                final int y_off = listHeight - 20;
                for (int j = 0; j < recipeCost.length; ++j) {
                    final ItemStack is = recipeCost[j];
                    GuiShop.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), is, x + x_off, y + y_off);
                    if (is.stackSize > 1) {
                        GuiShop.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), is, x + x_off, y + y_off, "" + is.stackSize);
                    }
                    x_off += 18;
                }
            }
        }
        if (draw) {
            super.drawHoveringText(list, x, y, font);
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        this.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        final int width = (this.width - this.xSize) / 2;
        final int height = (this.height - this.ySize) / 2;
        final int iconsOffset = 11;
        this.drawTexturedModalRect(width, height - 2, 64, 96, this.xSize, 32);
        this.drawTexturedModalRect(width, height - 2 + iconsOffset + 17, 64, 96 + iconsOffset, this.xSize, 21);
        final int AMMO_ICON = 36;
        int posY = 0;
        for (int i = 0; i < 18; ++i) {
            final int x = 10 + width + i * 17 - posY * 9;
            final int y = 10 + height + posY;
            this.drawIcon(AMMO_ICON, x, y);
            if (i % 9 == 8) {
                posY += 17;
            }
        }
        if (this.player.capabilities.isCreativeMode) {
            for (int i = 0; i < 4; ++i) {
                int x = 10 + width + i * 17;
                final int y = 10 + height + 44;
                if (i > 0) {
                    x += 16;
                }
                int icon = 68;
                if (i > 0) {
                    icon = 52;
                }
                this.drawIcon(icon, x, y);
            }
            final int x2 = 10 + width + this.selectedSlot % 9 * 17;
            final int y2 = 10 + height + this.selectedSlot / 9 * 17;
            this.drawIcon(68, x2, y2);
            if (this.textboxFile != null) {
                this.textboxFile.drawTextBox();
            }
        }
    }
    
    @Override
    public int getPlayerInventoryOffset() {
        return 80;
    }
    
    public void onGuiClosed() {
        final IMessage packet = (IMessage)new PacketUpdateConversation(1, this.shop.human);
        ChocolateQuest.channel.sendPaquetToServer(packet);
        super.onGuiClosed();
    }
}
