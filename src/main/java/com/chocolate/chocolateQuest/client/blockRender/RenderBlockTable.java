package com.chocolate.chocolateQuest.client.blockRender;

import net.minecraft.util.IIcon;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.block.BlockAltarTileEntity;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.item.EntityItem;
import com.chocolate.chocolateQuest.client.RenderBanner;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderBlockTable extends TileEntitySpecialRenderer
{
    RenderBanner render;
    EntityItem entityitem;
    
    public RenderBlockTable() {
        this.render = new RenderBanner(0.0f);
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double x, final double y, final double z, final float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        this.bindTexture(TextureMap.locationBlocksTexture);
        final BlockAltarTileEntity te = (BlockAltarTileEntity)tileentity;
        ChocolateQuest.table.setBlockBoundsBasedOnState((IBlockAccess)tileentity.getWorld(), tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
        final Tessellator tessellator = Tessellator.instance;
        final IIcon icon = Blocks.planks.getBlockTextureFromSide(0);
        final double tx0 = icon.getMinU();
        final double tx2 = icon.getMaxU();
        final double ty0 = icon.getMinV();
        final double ty2 = icon.getMaxV();
        final double y2 = 0.9;
        final double y3 = 1.0;
        final double x2 = ChocolateQuest.table.getBlockBoundsMinX();
        final double x3 = ChocolateQuest.table.getBlockBoundsMaxX();
        final double z2 = ChocolateQuest.table.getBlockBoundsMinZ();
        final double z3 = ChocolateQuest.table.getBlockBoundsMaxZ();
        this.drawBox(tessellator, x2, x3, y2, y3, z2, z3, tx0, tx2, ty0, ty2, 1.0f);
        if (ChocolateQuest.table.getBlockBoundsMinY() < 0.1) {
            this.drawBox(tessellator, 0.375, 0.625, 0.0, 0.92, 0.375, 0.625, tx0, tx2, ty0, ty2, 1.0f);
        }
        if (te.item != null) {
            if (this.entityitem == null) {
                this.entityitem = new EntityItem(tileentity.getWorld(), 0.0, 0.0, 0.0, te.item);
                this.entityitem.hoverStart = 0.3f;
            }
            else {
                this.entityitem.rotationYaw = (float)te.rotation;
                this.entityitem.setEntityItemStack(te.item);
            }
            RenderManager.instance.renderEntityWithPosYaw((Entity)this.entityitem, 0.5, 1.0, 0.5, 0.0f, 0.0f);
        }
        GL11.glPopMatrix();
    }
    
    public void drawBox(final Tessellator tessellator, final double x0, final double x1, final double y0, final double y1, final double z0, final double z1, final double tx0, final double tx1, final double ty0, final double ty1, final float b) {
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y0, z0, tx1, ty0);
        tessellator.addVertexWithUV(x0, y1, z0, tx1, ty1);
        tessellator.addVertexWithUV(x1, y1, z0, tx0, ty1);
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(x0, y0, z1, tx0, ty0);
        tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
        tessellator.addVertexWithUV(x1, y1, z1, tx1, ty1);
        tessellator.addVertexWithUV(x0, y1, z1, tx0, ty1);
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(x1, y1, z0, tx1, ty0);
        tessellator.addVertexWithUV(x1, y1, z1, tx0, ty0);
        tessellator.addVertexWithUV(x1, y0, z1, tx0, ty1);
        tessellator.addVertexWithUV(x1, y0, z0, tx1, ty1);
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(x0, y1, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
        tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
        tessellator.addVertexWithUV(x0, y1, z1, tx1, ty0);
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
        tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
        tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
        tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.addVertexWithUV(x0, y1, z1, tx0, ty0);
        tessellator.addVertexWithUV(x1, y1, z1, tx0, ty1);
        tessellator.addVertexWithUV(x1, y1, z0, tx1, ty1);
        tessellator.addVertexWithUV(x0, y1, z0, tx1, ty0);
        tessellator.draw();
    }
}
