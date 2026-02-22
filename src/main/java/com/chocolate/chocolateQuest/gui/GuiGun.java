package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GuiGun extends GuiPlayerInventory
{
    private static final int ICON_SIZE = 16;
    private static final int ICONS_PER_ROW = 16;
    ItemStack gunItemStack;
    int rows;
    
    public GuiGun(final ItemStack is, final IInventory par1IInventory, final EntityPlayer player) {
        super(new ContainerGun((IInventory)player.inventory, par1IInventory, is), par1IInventory, player);
        this.gunItemStack = is;
        this.rows = (par1IInventory.getSizeInventory() - 1) / 9;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        this.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        final int width = (this.width - this.xSize) / 2;
        final int height = (this.height - this.ySize) / 2;
        final int offset = 1;
        this.drawTexturedModalRect(width, height - 2, 64, 96, this.xSize, 32);
        if (this.rows > 0) {
            final int iconsOffset = 11;
            for (int currentRow = 1; currentRow <= this.rows - 1; ++currentRow) {
                this.drawTexturedModalRect(width, height - 2 + iconsOffset + 17 * currentRow, 64, 96 + iconsOffset, this.xSize, 18);
            }
            this.drawTexturedModalRect(width, height - 2 + iconsOffset + 17 * this.rows, 64, 96 + iconsOffset, this.xSize, 21);
        }
        final ILoadableGun gun = (ILoadableGun)this.gunItemStack.getItem();
        final int AMMO_ICON = gun.getStackIcon(this.gunItemStack);
        int posY = 0;
        for (int i = 0; i < gun.getAmmoLoaderAmmount(this.gunItemStack); ++i) {
            final int x = 10 + width + i * 17 - posY * 9;
            final int y = 10 + height + posY;
            this.drawIcon(AMMO_ICON, x, y);
            if (i % 9 == 8) {
                posY += 17;
            }
        }
    }
    
    @Override
    public int getPlayerInventoryOffset() {
        return 46 + this.rows * 17;
    }
    
    @Override
    public void drawIcon(final int icon, final int xPos, final int yPos) {
        this.drawTexturedModalRect(xPos, yPos, icon % 16 * 16, icon / 16 * 16, 16, 16);
    }
}
