package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.packets.PacketUpdateConversation;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.gui.GuiHumanBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketUpdateAwakement;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiButton;
import com.chocolate.chocolateQuest.gui.GuiPlayerInventory;

public class GuiAwakement extends GuiPlayerInventory
{
    InventoryAwakement inventory;
    GuiButtonAwakements[] enchantButtons;
    ContainerAwakement containerSlots;
    GuiButton scrollUp;
    GuiButton scrollDown;
    int UP;
    int DOWN;
    int scrollAmmount;
    int type;
    int maxLevel;
    
    public GuiAwakement(final InventoryAwakement awInv, final EntityPlayer player, final int type, final int level) {
        super(new ContainerAwakement((IInventory)player.inventory, awInv, type, level), (IInventory)awInv, player);
        this.UP = 1000;
        this.DOWN = 1001;
        this.scrollAmmount = 0;
        this.maxLevel = 0;
        this.containerSlots = (ContainerAwakement)this.inventorySlots;
        this.inventory = awInv;
        this.type = type;
        this.maxLevel = level;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int width = (this.width - this.xSize) / 2;
        final int height = (this.height - this.ySize) / 2;
        final int ENCHANTS = 4;
        final int BUTTON_HEIGHT = 20;
        final int BUTTON_WIDTH = 120;
        this.enchantButtons = new GuiButtonAwakements[ENCHANTS];
        for (int i = 0; i < ENCHANTS; ++i) {
            (this.enchantButtons[i] = new GuiButtonAwakements(i, width + 60, height + BUTTON_HEIGHT * i, BUTTON_WIDTH, BUTTON_HEIGHT, " ")).disable();
            this.buttonList.add(this.enchantButtons[i]);
        }
        final int scrollX = width + BUTTON_WIDTH + 44;
        this.scrollUp = new GuiButtonIcon(this.UP, scrollX, height - 8, 15.0f, 7.0f, 1.0f, 0.5f, "");
        this.buttonList.add(this.scrollUp);
        this.scrollUp.visible = false;
        this.scrollDown = new GuiButtonIcon(this.DOWN, scrollX, height + BUTTON_HEIGHT * 4, 15.0f, 7.5f, 1.0f, 0.5f, "");
        this.buttonList.add(this.scrollDown);
        this.scrollDown.visible = false;
    }
    
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        if (button.id < 256) {
            ((ContainerAwakement)this.inventorySlots).enchantItem(button.id);
            final IMessage packet = (IMessage)new PacketUpdateAwakement(button.id);
            ChocolateQuest.channel.sendPaquetToServer(packet);
        }
        else if (button.id == this.UP && this.scrollAmmount > 0) {
            --this.scrollAmmount;
        }
        else if (button.id == this.DOWN) {
            ++this.scrollAmmount;
        }
        this.updateButtons();
    }
    
    @Override
    protected void handleMouseClick(final Slot slot, final int slotID, final int x, final int y) {
        super.handleMouseClick(slot, slotID, x, y);
        this.updateButtons();
    }
    
    public void updateButtons() {
        final ItemStack is = this.inventory.getStackInSlot(0);
        if (is != null) {
            final boolean mode = this.containerSlots.mode;
            final ContainerAwakement containerSlots = this.containerSlots;
            if (mode) {
                this.updateButtonsEnchantment(is);
            }
            else {
                this.updateButtonsAwakement(is);
            }
        }
        else {
            for (int i = 0; i < this.enchantButtons.length; ++i) {
                this.enchantButtons[i].displayString = "";
                this.enchantButtons[i].xpRequired = 0;
                this.enchantButtons[i].enabled = false;
            }
        }
    }
    
    public void updateButtonsEnchantment(final ItemStack is) {
        int count = 0;
        final int expRequired = this.containerSlots.getXPRequiredToEnchantItem();
        for (final Enchantment aw : Enchantment.enchantmentsList) {
            if (aw != null && aw.canApply(is) && EnchantmentHelper.getEnchantmentLevel(aw.effectId, is) < aw.getMaxLevel()) {
                ++count;
            }
        }
        final Enchantment[] enchantments = new Enchantment[count];
        count = 0;
        for (final Enchantment aw2 : Enchantment.enchantmentsList) {
            if (aw2 != null && aw2.canApply(is) && EnchantmentHelper.getEnchantmentLevel(aw2.effectId, is) < aw2.getMaxLevel()) {
                enchantments[count] = aw2;
                ++count;
            }
        }
        this.handleScroll(count);
        for (int i = 0; i < this.enchantButtons.length; ++i) {
            final int index = i + this.scrollAmmount;
            if (index < enchantments.length) {
                final int id = enchantments[index].effectId;
                final int level = EnchantmentHelper.getEnchantmentLevel(enchantments[index].effectId, is) + 1;
                int xp = expRequired + this.containerSlots.getXPRequiredForEnchantment(this.enchantButtons[i].id, level);
                xp -= this.containerSlots.getCatalystRebate(xp);
                this.enchantButtons[i].id = id;
                this.enchantButtons[i].displayString = enchantments[index].getTranslatedName(level);
                this.enchantButtons[i].xpRequired = xp;
                final boolean canEnchant = (this.player.experienceLevel >= expRequired || this.player.capabilities.isCreativeMode) && xp <= this.maxLevel;
                if (!canEnchant) {
                    this.enchantButtons[i].enabled = false;
                }
                else {
                    this.enchantButtons[i].enabled = true;
                }
            }
            else {
                this.enchantButtons[i].disable();
            }
        }
    }
    
    public void updateButtonsAwakement(final ItemStack is) {
        int count = 0;
        final int expRequired = this.containerSlots.getXPRequiredToEnchantItem();
        for (final Awakements aw : Awakements.awekements) {
            if (aw.canBeUsedOnItem(is) && Awakements.getEnchantLevel(is, aw) < aw.getMaxLevel() && aw.canBeAddedByNPC(this.type)) {
                ++count;
            }
        }
        final Awakements[] awakements = new Awakements[count];
        count = 0;
        for (final Awakements aw2 : Awakements.awekements) {
            if (aw2.canBeUsedOnItem(is) && Awakements.getEnchantLevel(is, aw2) < aw2.getMaxLevel() && aw2.canBeAddedByNPC(this.type)) {
                awakements[count] = aw2;
                ++count;
            }
        }
        this.handleScroll(count);
        for (int i = 0; i < this.enchantButtons.length; ++i) {
            final int index = i + this.scrollAmmount;
            if (index < awakements.length) {
                int xp = expRequired + this.containerSlots.getXPRequiredForEnchantment(this.enchantButtons[i].id, Awakements.getEnchantLevel(is, awakements[index]) + 1);
                xp -= this.containerSlots.getCatalystRebate(xp);
                this.enchantButtons[i].id = awakements[index].id;
                this.enchantButtons[i].displayString = awakements[index].getName();
                this.enchantButtons[i].xpRequired = xp;
                final boolean canEnchant = (this.player.experienceLevel >= expRequired || this.player.capabilities.isCreativeMode) && xp <= this.maxLevel;
                if (!canEnchant) {
                    this.enchantButtons[i].enabled = false;
                }
                else {
                    this.enchantButtons[i].enabled = true;
                }
            }
            else {
                this.enchantButtons[i].disable();
            }
        }
    }
    
    public void handleScroll(final int entries) {
        this.scrollDown.visible = true;
        this.scrollUp.visible = true;
        if (this.scrollAmmount + 4 >= entries) {
            this.scrollAmmount = Math.max(0, entries - 4);
            this.scrollDown.visible = false;
        }
        if (this.scrollAmmount == 0) {
            this.scrollUp.visible = false;
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        final int width = (this.width - this.xSize) / 2;
        final int height = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        final int x = 10 + width;
        final int y = 10 + height + 50;
        this.drawTexturedModalRect(width, height, 64, 128, 64, 80);
        this.drawIcon(36, x, y);
        this.drawIcon(86, x + 32, y - 42);
        this.drawIcon(87 + this.type, x + 32, y - 20);
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        final int enchX = x + 50;
        final int enchY = y - 50;
        GuiHumanBase.drawEntity((EntityLivingBase)this.inventory.npc, enchX - 40, enchY + 40);
    }
    
    @Override
    public int getPlayerInventoryOffset() {
        return 100;
    }
    
    public void onGuiClosed() {
        final IMessage packet = (IMessage)new PacketUpdateConversation(1, this.inventory.npc);
        ChocolateQuest.channel.sendPaquetToServer(packet);
        super.onGuiClosed();
    }
}
